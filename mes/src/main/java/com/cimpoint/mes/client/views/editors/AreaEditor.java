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
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;
import com.cimpoint.mes.client.views.editors.ModelTreeGrid.NodeType;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.cimpoint.mes.common.filters.ProductionLineFilter;
import com.smartgwt.client.widgets.tree.TreeNode;

public class AreaEditor extends ModelEditor {	
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	private TreeNode prodLinesTreeNode;
	
	public AreaEditor() {
		super(new RoutingResourceDesigner());
		routingController = MESApplication.getMESControllers().getRoutingController();
		designerTreeGrid = ((RoutingResourceDesigner) super.getModelDesigner()).getModelTreeGrid();
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes);
	}
	
	@Override
	public void onInitialize(final String areaName) {
		if (initialized) return;
		
		routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
			@Override
			public void onSuccess(Area area) {
				designerTreeGrid.clear();
				
				propName.setValue(area.getName());
				propDesc.setValue(area.getDescription());
				propCustomAttributes.setCustomAttributes(area.getCustomAttributes());
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.RootModel, areaName, areaName, "pieces/16/site.png");

				String areaPath = area.getName();
				Set<ProductionLine> prodLines = SiteEditor.displayProductionLines(area, areaPath, designerTreeGrid, true);
				if (prodLines != null && prodLines.size() > 0) {
					for (ProductionLine pdl : prodLines) {
						String prodLinePath = area.getName() + "/prodLines/" + pdl.getName();
						Set<WorkCenter> wcs = SiteEditor.displayWorkCenters(pdl, prodLinePath, designerTreeGrid, false);
						if (wcs != null && wcs.size() > 0) {
							for (WorkCenter wc : wcs) {
								String wcPath = area.getName() + "/prodLines/" + wc.getProductionLine().getName()
										+ "/workCenters/" + wc.getName();
								SiteEditor.displayEquipments(wc, wcPath, designerTreeGrid, false);
							}
						}
					}
				}
				else {
					prodLinesTreeNode = designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.EditableProductionLines, 
							areaName, "prodLines", "Production Lines (0)", "pieces/16/area.png");
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
		
		MESApplication.showYesNoCancelMessage("Save Area", "Area " + getName() + " has not been saved yet.<br/>Would you like to save it?", 
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
		.add(new ChainAction<Set<ProductionLine>>() {
			@Override
			public void invoke(final Object[] inputData, final ChainCallbackHandler<Set<ProductionLine>> callback) {
				getProductionLines(callback);
			}	
		})
		.add(new ChainAction<Area>() {
			@Override
			public void invoke(Object[] inputData, ChainCallbackHandler<Area> callback) {
				String areaName = propName.getValueAsString();
				String desc = propDesc.getValueAsString();
				Set<ProductionLine> prodLines = getReturnData(0);		
				CustomAttributes customAttributes = propCustomAttributes.getCustomAttributes();
				
				saveArea(areaName, desc, prodLines, customAttributes, callback);
			}			
		})
		.runSequential(callback);
	}

	@Override
	public void onAddButtionClicked(CallbackHandler<Void> callback) {
		final TreeNode selNode = designerTreeGrid.findSelectedTreeNode();
		if (selNode != null) {
			ModelTreeGrid.NodeType nodeType = ModelTreeGrid.NodeType.valueOf(selNode.getAttribute("Type"));		
			if (nodeType == ModelTreeGrid.NodeType.EditableProductionLines) {
				addProductionLines(selNode, callback);
			}
		}
	}

	@Override
	public void onRemoveButtionClicked(CallbackHandler<Void> callback) {
		final String areaName = super.getName();
		final TreeNode[] selNodes = designerTreeGrid.findSelectedTreeNodes();
		if (selNodes != null && selNodes.length > 0) {		
			if (selNodes[0].getAttribute("Type").equals(NodeType.ProductionLine.toString())) {
				TreeNode parentNode = designerTreeGrid.getTree().getParent(selNodes[0]);
				ModelTreeGrid.NodeType parentNodeType = ModelTreeGrid.NodeType.valueOf(parentNode.getAttribute("Type"));		
				if (parentNodeType == ModelTreeGrid.NodeType.EditableProductionLines) {
					removeProductionLines(selNodes, areaName, callback);
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
		updateAreaName(oldName, newName, callback);
	}
	
	private void addProductionLines(final TreeNode selParentNode, final CallbackHandler<Void> callback) {
		ProductionLineFilter filter = new ProductionLineFilter();
		filter.OR();
		filter.whereArea().isNull();
		
		MESApplication.getMESControllers().getRoutingController().findProductionLineNames(filter, new CallbackHandler<Set<String>>() {
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
						showValuesPickerDialog("Select Production Line(s)", availNames, new CallbackHandler<Response>() {
							@Override
							public void onSuccess(Response result) {
								final Set<String> selNames = result.getReturnData();
								addProductionLines(selNames, selParentNode, callback);
							}							
						});
					}
					else {
						MESApplication.showOKMessage("No available production line to add", null);
						callback.onSuccess(null);
					}
				}
				else {
					callback.onSuccess(null);
				}
			}
		});
	}
	
	private void addProductionLines(final Set<String> prodLineNames, TreeNode selParentNode, final CallbackHandler<Void> callback) {
		if (prodLineNames == null) {
			callback.onSuccess(null);
			return;
		}
	
		final String areaName = super.getName();
		final String selParentPath = designerTreeGrid.getPathToNode(selParentNode);
		
		//update GUI
		for (String name: prodLineNames) {
			designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.ProductionLine, 
					selParentPath, name, name, "pieces/16/area.png");
		}
		
		String newParentTitle = null;
		TreeNode parentNode = designerTreeGrid.findTreeNode(selParentPath);
		String parentNodeType = parentNode.getAttribute("Type");
		if (parentNodeType != null && parentNodeType.equals(NodeType.EditableProductionLines.toString())) {
			newParentTitle = "Production Lines (" + designerTreeGrid.getChildNodeCount(parentNode) + ")";
		}
		parentNode.setAttribute("Title", newParentTitle);
		designerTreeGrid.updateData(parentNode);
		//designerTreeGrid.openTreeNode(parentNode);	
		
		//update DB
		routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
			public void onSuccess(final Area area) {
				ProductionLineFilter filter = new ProductionLineFilter();
				filter.OR();
				filter.whereName().isInList(prodLineNames.toArray(new String[prodLineNames.size()]));
				routingController.findProductionLines(filter, new CallbackHandler<Set<ProductionLine>>() {
					@Override
					public void onSuccess(Set<ProductionLine> prodLines) {
						if (prodLines != null) {
							for (ProductionLine prodLine : prodLines) {
								area.addProductionLine(prodLine);
							}
						}
						area.save("Add production lines", callback);
					}
				});
			}
		});	
	}
	
	public void removeProductionLines(final TreeNode[] selNodes, String areaName, final CallbackHandler<Void> callback) {
		final Set<String> prodLineNames = new HashSet<String>();
		for (TreeNode node: selNodes) {
			String name = node.getAttribute("ModelName");
			prodLineNames.add(name);
		}
			
		routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
			public void onSuccess(final Area area) {
				ProductionLineFilter filter = new ProductionLineFilter();
				filter.OR();
				filter.whereName().isInList(prodLineNames.toArray(new String[prodLineNames.size()]));
				routingController.findProductionLines(filter, new CallbackHandler<Set<ProductionLine>>() {
					@Override
					public void onSuccess(Set<ProductionLine> prodLines) {
						//remove from DB
						if (prodLines != null) {
							for (ProductionLine prodLine : prodLines) {
								area.removeProductionLine(prodLine);
							}
							area.save("Remove production lines", callback);
						}
						
						//remove from GUI
						designerTreeGrid.removeSelectedTreeNodes();		
						TreeNode prodLinesNode = designerTreeGrid.getTree().getParent(selNodes[0]);
						prodLinesNode.setAttribute("Title", "Production Lines (" + designerTreeGrid.getChildNodeCount(prodLinesNode) + ")");
						designerTreeGrid.updateData(prodLinesNode);	
					}
				});
			}
		});
	}
	
	private void getProductionLines(final ChainCallbackHandler<Set<ProductionLine>> callback) {
		if (prodLinesTreeNode != null) {
			TreeNode[] childNodes = designerTreeGrid.getTree().getChildren(prodLinesTreeNode);
			if (childNodes != null && childNodes.length > 0) {
				String[] names = new String[childNodes.length];
				int i=0;
				for (TreeNode n : childNodes) {
					String name = n.getAttribute("ModelName");
					names[i++] = name;
				}

				ProductionLineFilter filter = new ProductionLineFilter();
				filter.OR();
				filter.whereName().isInList(names);
				routingController.findProductionLines(filter, new CallbackHandler<Set<ProductionLine>>() {
					@Override
					public void onSuccess(Set<ProductionLine> result) {
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
	
	private void saveArea(final String areaName, final String desc, final Set<ProductionLine> prodLines, 
			final CustomAttributes customAttributes, final ChainCallbackHandler<Area> callback) {
		routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
			public void onSuccess(final Area area) {
				area.setName(areaName);
				area.setDescription(desc);
				area.setProductionLines(prodLines);
				area.setCustomAttributes(customAttributes);
				String comment = "Update area";
				routingController.saveArea(area, comment, new CallbackHandler<Void>() {
					public void onSuccess(Void result) {
						callback.onSuccess(area);
					}
				});
			}
		});
	}
	
	private void updateAreaName(final String oldAreaName, final String newAreaName, final CallbackHandler<Void> callback) {
		routingController.findAreaByName(oldAreaName, new CallbackHandler<Area>() {
			public void onSuccess(final Area area) {
				area.setName(newAreaName);
				String comment = "Update area name";
				routingController.saveArea(area, comment, callback);
			}
		});
	}
}
