/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     pitor - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views.editors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.ChainAction;
import com.cimpoint.common.ChainCall;
import com.cimpoint.common.ChainCall.ChainCallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;
import com.cimpoint.mes.client.views.editors.ModelTreeGrid.NodeType;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.cimpoint.mes.common.filters.WorkCenterFilter;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProductionLineEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	private TreeNode workCentersTreeNode;
	
	public ProductionLineEditor() {
		super(new RoutingResourceDesigner());
		routingController = MESApplication.getMESControllers().getRoutingController();
		designerTreeGrid = ((RoutingResourceDesigner) super.getModelDesigner()).getModelTreeGrid();
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes);
	}
	
	@Override
	public void onInitialize(final String prodLineName) {
		if (initialized) return;
		
		routingController.findProductionLineByName(prodLineName, new CallbackHandler<ProductionLine>() {
			@Override
			public void onSuccess(ProductionLine prodLine) {
				designerTreeGrid.clear();
				
				propName.setValue(prodLine.getName());
				propDesc.setValue(prodLine.getDescription());
				propCustomAttributes.setCustomAttributes(prodLine.getCustomAttributes());
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.RootModel, prodLineName, prodLineName, "pieces/16/site.png");

				String prodLinePath = prodLine.getName();
				Set<WorkCenter> wcs = SiteEditor.displayWorkCenters(prodLine, prodLinePath, designerTreeGrid, true);
				if (wcs != null && wcs.size() > 0) {
					for (WorkCenter wc : wcs) {
						String wcPath = prodLinePath + "/workCenters/" + wc.getName();
						SiteEditor.displayEquipments(wc, wcPath, designerTreeGrid, false);
					}
				}
				else {
					workCentersTreeNode = designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.EditableWorkCenters, 
							prodLineName, "prodLines", "Work Centers (0)", "pieces/16/area.png");
				}
				
				initialized = true;
			}
		});
	}
	
	@Override
	public void onClose(final CallbackHandler<Boolean> callback) {
		if (!this.isEditing()) {
			callback.onSuccess(true);
			return;
		}
		
		MESApplication.showYesNoCancelMessage("Save Production Line", "Production Line " + 
				getName() + " has not been saved yet.<br/>Would you like to save it?", 
				350, 150, new CallbackHandler<MessageButton>() {
			@Override
			public void onSuccess(MessageButton button) {
				if (button == MessageButton.Yes) {
					onSaveButtionClicked(new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							callback.onSuccess(true);
						}						
					});
				}
				else if (button == MessageButton.No) {
					callback.onSuccess(true);
				}
				else {
					callback.onSuccess(false);
				}
			}			
		});
	}

	@Override
	public void onSaveButtionClicked(CallbackHandler<Void> callback) {
		if (!this.getPropertiesEditor().validateProperties())
			return;

		new ChainCall()
		.add(new ChainAction<Set<WorkCenter>>() {
			@Override
			public void invoke(final Object[] inputData, final ChainCallbackHandler<Set<WorkCenter>> callback) {
				getWorkCenters(callback);
			}	
		})
		.add(new ChainAction<ProductionLine>() {
			@Override
			public void invoke(Object[] inputData, ChainCallbackHandler<ProductionLine> callback) {
				String prodLineName = propName.getValueAsString();
				String desc = propDesc.getValueAsString();
				Set<WorkCenter> wcs = getReturnData(0);		
				CustomAttributes customAttributes = propCustomAttributes.getCustomAttributes();
				
				saveProductionLine(prodLineName, desc, wcs, customAttributes, callback);
			}			
		})
		.runSequential(callback);
	}

	@Override
	public void onAddButtionClicked(CallbackHandler<Void> callback) {
		final TreeNode selNode = designerTreeGrid.findSelectedTreeNode();
		if (selNode != null) {
			ModelTreeGrid.NodeType nodeType = ModelTreeGrid.NodeType.valueOf(selNode.getAttribute("Type"));		
			if (nodeType == ModelTreeGrid.NodeType.EditableWorkCenters) {
				addWorkCenters(selNode, callback);
			}
		}
	}

	@Override
	public void onRemoveButtionClicked(CallbackHandler<Void> callback) {
		final String prodLineName = super.getName();
		final TreeNode[] selNodes = designerTreeGrid.findSelectedTreeNodes();
		if (selNodes != null && selNodes.length > 0) {		
			if (selNodes[0].getAttribute("Type").equals(NodeType.WorkCenter.toString())) {
				TreeNode parentNode = designerTreeGrid.getTree().getParent(selNodes[0]);
				ModelTreeGrid.NodeType parentNodeType = ModelTreeGrid.NodeType.valueOf(parentNode.getAttribute("Type"));		
				if (parentNodeType == ModelTreeGrid.NodeType.EditableWorkCenters) {
					removeWorkCenters(selNodes, prodLineName, callback);
				}
			}	
		}
		else {
			callback.onSuccess(null);
		}
	}

	@Override
	public void onRefreshButtionClicked(CallbackHandler<Void> callback) {
		initialized = false;
		onInitialize(getName());
		callback.onSuccess(null);
	}

	@Override
	public void onModelNameChanged(String oldName, String newName, CallbackHandler<Void> callback) {
		updateProductionLineName(oldName, newName, callback);
	}
	
	private void addWorkCenters(final TreeNode selParentNode, final CallbackHandler<Void> callback) {
		WorkCenterFilter filter = new WorkCenterFilter();
		filter.OR();
		filter.whereProductionLine().isNull();
		
		MESApplication.getMESControllers().getRoutingController().findWorkCenterNames(filter, new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> names) {	
				if (names != null && names.size() > 0) {
					List<String> availNames = new ArrayList<String>();
					for (String name: names) {
						if (!designerTreeGrid.containsTreeNodeName(name)) {
							availNames.add(name);
						}
					}				
					
					if (availNames.size() > 0) {
						showValuesPickerDialog("Select Work Center(s)", availNames, new CallbackHandler<Response>() {
							@Override
							public void onSuccess(Response result) {
								final Set<String> selNames = result.getReturnData();
								addWorkCenters(selNames, selParentNode, callback);
							}							
						});
					}
					else {
						MESApplication.showOKMessage("No available work center to add", null);
						callback.onSuccess(null);
					}
				}
				else {
					callback.onSuccess(null);
				}
			}
		});
	}
	
	private void addWorkCenters(final Set<String> wcNames, TreeNode selParentNode, final CallbackHandler<Void> callback) {
		if (wcNames == null) {
			callback.onSuccess(null);
			return;
		}
	
		final String prodLineName = super.getName();
		final String selParentPath = designerTreeGrid.getPathToNode(selParentNode);
		
		//update GUI
		for (String name: wcNames) {
			designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.WorkCenter, 
					selParentPath, name, name, "pieces/16/area.png");
		}
		
		String newParentTitle = null;
		TreeNode parentNode = designerTreeGrid.findTreeNode(selParentPath);
		String parentNodeType = parentNode.getAttribute("Type");
		if (parentNodeType != null && parentNodeType.equals(NodeType.EditableWorkCenters.toString())) {
			newParentTitle = "Work Centers (" + designerTreeGrid.getChildNodeCount(parentNode) + ")";
		}
		parentNode.setAttribute("Title", newParentTitle);
		designerTreeGrid.updateData(parentNode);
		//designerTreeGrid.openTreeNode(parentNode);	
		
		//update DB
		routingController.findProductionLineByName(prodLineName, new CallbackHandler<ProductionLine>() {
			public void onSuccess(final ProductionLine prodLine) {
				WorkCenterFilter filter = new WorkCenterFilter();
				filter.OR();
				filter.whereName().isInList(wcNames.toArray(new String[wcNames.size()]));
				routingController.findWorkCenters(filter, new CallbackHandler<Set<WorkCenter>>() {
					@Override
					public void onSuccess(Set<WorkCenter> wcs) {
						if (wcs != null) {
							for (WorkCenter wc : wcs) {
								prodLine.addWorkCenter(wc);
							}
						}
						prodLine.save("Add work centers", callback);
					}
				});
			}
		});	
	}
	
	public void removeWorkCenters(final TreeNode[] selNodes, String prodLineName, final CallbackHandler<Void> callback) {
		final Set<String> wcNames = new HashSet<String>();
		for (TreeNode node: selNodes) {
			String name = node.getAttribute("ModelName");
			wcNames.add(name);
		}
			
		routingController.findProductionLineByName(prodLineName, new CallbackHandler<ProductionLine>() {
			public void onSuccess(final ProductionLine prodLine) {
				WorkCenterFilter filter = new WorkCenterFilter();
				filter.OR();
				filter.whereName().isInList(wcNames.toArray(new String[wcNames.size()]));
				routingController.findWorkCenters(filter, new CallbackHandler<Set<WorkCenter>>() {
					@Override
					public void onSuccess(Set<WorkCenter> wcs) {
						//remove from DB
						if (wcs != null) {
							for (WorkCenter wc : wcs) {
								prodLine.removeWorkCenter(wc);
							}
							prodLine.save("Remove work centers", callback);
						}
						
						//remove from GUI
						designerTreeGrid.removeSelectedTreeNodes();		
						TreeNode wcNode = designerTreeGrid.getTree().getParent(selNodes[0]);
						wcNode.setAttribute("Title", "Work Centers (" + designerTreeGrid.getChildNodeCount(wcNode) + ")");
						designerTreeGrid.updateData(wcNode);	
					}
				});
			}
		});
	}
	
	private void getWorkCenters(final ChainCallbackHandler<Set<WorkCenter>> callback) {
		if (workCentersTreeNode != null) {
			TreeNode[] childNodes = designerTreeGrid.getTree().getChildren(workCentersTreeNode);
			if (childNodes != null && childNodes.length > 0) {
				String[] names = new String[childNodes.length];
				int i=0;
				for (TreeNode n : childNodes) {
					String name = n.getAttribute("ModelName");
					names[i++] = name;
				}

				WorkCenterFilter filter = new WorkCenterFilter();
				filter.OR();
				filter.whereName().isInList(names);
				routingController.findWorkCenters(filter, new CallbackHandler<Set<WorkCenter>>() {
					@Override
					public void onSuccess(Set<WorkCenter> result) {
						callback.onSuccess(result);
					}
				});
			}
			else {
				callback.onSuccess(null);
			}
		}
		else {
			callback.onSuccess(null);	
		}
	}
	
	private void saveProductionLine(final String prodLineName, final String desc, final Set<WorkCenter> wcs, 
			final CustomAttributes customAttributes, final ChainCallbackHandler<ProductionLine> callback) {
		routingController.findProductionLineByName(prodLineName, new CallbackHandler<ProductionLine>() {
			public void onSuccess(final ProductionLine prodLine) {
				prodLine.setName(prodLineName);
				prodLine.setDescription(desc);
				prodLine.setWorkCenters(wcs);
				prodLine.setCustomAttributes(customAttributes);
				String comment = "Update production line";
				routingController.saveProductionLine(prodLine, comment, new CallbackHandler<Void>() {
					public void onSuccess(Void result) {
						callback.onSuccess(prodLine);
					}
				});
			}
		});
	}
	
	private void updateProductionLineName(final String oldProdLineName, final String newProdLineName, final CallbackHandler<Void> callback) {
		routingController.findProductionLineByName(oldProdLineName, new CallbackHandler<ProductionLine>() {
			public void onSuccess(final ProductionLine prodLine) {
				prodLine.setName(newProdLineName);
				String comment = "Update production line name";
				routingController.saveProductionLine(prodLine, comment, callback);
			}
		});
	}
}
