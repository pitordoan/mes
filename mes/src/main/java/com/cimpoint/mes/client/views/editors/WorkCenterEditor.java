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
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;
import com.cimpoint.mes.client.views.editors.ModelTreeGrid.NodeType;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.cimpoint.mes.common.filters.EquipmentFilter;
import com.smartgwt.client.widgets.tree.TreeNode;

public class WorkCenterEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	private TreeNode equipmentTreeNode;
	
	public WorkCenterEditor() {
		super(new RoutingResourceDesigner());
		routingController = MESApplication.getMESControllers().getRoutingController();
		designerTreeGrid = ((RoutingResourceDesigner) super.getModelDesigner()).getModelTreeGrid();
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes);
	}
	
	@Override
	public void onInitialize(final String wcName) {
		if (initialized) return;
		
		routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {
			@Override
			public void onSuccess(WorkCenter wc) {
				designerTreeGrid.clear();
				
				propName.setValue(wc.getName());
				propDesc.setValue(wc.getDescription());
				propCustomAttributes.setCustomAttributes(wc.getCustomAttributes());
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.RootModel, wcName, wcName, "pieces/16/site.png");

				String wcPath = wc.getName();
				Set<Equipment> eqs = SiteEditor.displayEquipments(wc, wcPath, designerTreeGrid, true);
				if (eqs == null || eqs.isEmpty()) {
					equipmentTreeNode = designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.EditableEquipments, 
							wcName, "equipments", "Equipments (0)", "pieces/16/area.png");
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
		
		MESApplication.showYesNoCancelMessage("Save Work Center", "Work Center " + 
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
		.add(new ChainAction<Set<Equipment>>() {
			@Override
			public void invoke(final Object[] inputData, final ChainCallbackHandler<Set<Equipment>> callback) {
				getEquipments(callback);
			}	
		})
		.add(new ChainAction<WorkCenter>() {
			@Override
			public void invoke(Object[] inputData, ChainCallbackHandler<WorkCenter> callback) {
				String wcName = propName.getValueAsString();
				String desc = propDesc.getValueAsString();
				Set<Equipment> eqs = getReturnData(0);		
				CustomAttributes customAttributes = propCustomAttributes.getCustomAttributes();
				
				saveWorkCenter(wcName, desc, eqs, customAttributes, callback);
			}			
		})
		.runSequential(callback);
	}

	@Override
	public void onAddButtionClicked(CallbackHandler<Void> callback) {
		final TreeNode selNode = designerTreeGrid.findSelectedTreeNode();
		if (selNode != null) {
			ModelTreeGrid.NodeType nodeType = ModelTreeGrid.NodeType.valueOf(selNode.getAttribute("Type"));		
			if (nodeType == ModelTreeGrid.NodeType.EditableEquipments) {
				addEquipments(selNode, callback);
			}
		}
	}

	@Override
	public void onRemoveButtionClicked(CallbackHandler<Void> callback) {
		final String wcName = super.getName();
		final TreeNode[] selNodes = designerTreeGrid.findSelectedTreeNodes();
		if (selNodes != null && selNodes.length > 0) {		
			if (selNodes[0].getAttribute("Type").equals(NodeType.Equipment.toString())) {
				TreeNode parentNode = designerTreeGrid.getTree().getParent(selNodes[0]);
				ModelTreeGrid.NodeType parentNodeType = ModelTreeGrid.NodeType.valueOf(parentNode.getAttribute("Type"));		
				if (parentNodeType == ModelTreeGrid.NodeType.EditableEquipments) {
					removeEquipments(selNodes, wcName, callback);
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
		updateWorkCenterName(oldName, newName, callback);
	}
	
	private void addEquipments(final TreeNode selParentNode, final CallbackHandler<Void> callback) {
		EquipmentFilter filter = new EquipmentFilter();
		filter.OR();
		filter.whereWorkCenter().isNull();
		
		MESApplication.getMESControllers().getRoutingController().findEquipmentNames(filter, new CallbackHandler<Set<String>>() {
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
						showValuesPickerDialog("Select Equipment(s)", availNames, new CallbackHandler<Response>() {
							@Override
							public void onSuccess(Response result) {
								final Set<String> selNames = result.getReturnData();
								addEquipments(selNames, selParentNode, callback);
							}							
						});
					}
					else {
						MESApplication.showOKMessage("No available equipment to add", null);
						callback.onSuccess(null);
					}
				}
				else {
					callback.onSuccess(null);
				}
			}
		});
	}
	
	private void addEquipments(final Set<String> eqNames, TreeNode selParentNode, final CallbackHandler<Void> callback) {
		if (eqNames == null) {
			callback.onSuccess(null);
			return;
		}
	
		final String wcName = super.getName();
		final String selParentPath = designerTreeGrid.getPathToNode(selParentNode);
		
		//update GUI
		for (String name: eqNames) {
			designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.Equipment, 
					selParentPath, name, name, "pieces/16/area.png");
		}
		
		String newParentTitle = null;
		TreeNode parentNode = designerTreeGrid.findTreeNode(selParentPath);
		String parentNodeType = parentNode.getAttribute("Type");
		if (parentNodeType != null && parentNodeType.equals(NodeType.EditableEquipments.toString())) {
			newParentTitle = "Equipments (" + designerTreeGrid.getChildNodeCount(parentNode) + ")";
		}
		parentNode.setAttribute("Title", newParentTitle);
		designerTreeGrid.updateData(parentNode);
		//designerTreeGrid.openTreeNode(parentNode);	
		
		//update DB
		routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {
			public void onSuccess(final WorkCenter wc) {
				EquipmentFilter filter = new EquipmentFilter();
				filter.OR();
				filter.whereName().isInList(eqNames.toArray(new String[eqNames.size()]));
				routingController.findEquipments(filter, new CallbackHandler<Set<Equipment>>() {
					@Override
					public void onSuccess(Set<Equipment> eqs) {
						if (eqs != null) {
							for (Equipment eq: eqs) {
								wc.addEquipment(eq);
							}
						}
						wc.save("Add equipments", callback);
					}
				});
			}
		});	
	}
	
	public void removeEquipments(final TreeNode[] selNodes, String wcName, final CallbackHandler<Void> callback) {
		final Set<String> eqNames = new HashSet<String>();
		for (TreeNode node: selNodes) {
			String name = node.getAttribute("ModelName");
			eqNames.add(name);
		}
			
		routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {
			public void onSuccess(final WorkCenter wc) {
				EquipmentFilter filter = new EquipmentFilter();
				filter.OR();
				filter.whereName().isInList(eqNames.toArray(new String[eqNames.size()]));
				routingController.findEquipments(filter, new CallbackHandler<Set<Equipment>>() {
					@Override
					public void onSuccess(Set<Equipment> eqs) {
						//remove from DB
						if (eqs != null) {
							for (Equipment eq: eqs) {
								wc.removeEquipment(eq);
							}
							wc.save("Remove equipments", callback);
						}
						
						//remove from GUI
						designerTreeGrid.removeSelectedTreeNodes();		
						TreeNode eqNode = designerTreeGrid.getTree().getParent(selNodes[0]);
						eqNode.setAttribute("Title", "Equipments (" + designerTreeGrid.getChildNodeCount(eqNode) + ")");
						designerTreeGrid.updateData(eqNode);	
					}
				});
			}
		});
	}
	
	private void getEquipments(final ChainCallbackHandler<Set<Equipment>> callback) {
		if (equipmentTreeNode != null) {
			TreeNode[] childNodes = designerTreeGrid.getTree().getChildren(equipmentTreeNode);
			if (childNodes != null && childNodes.length > 0) {
				String[] names = new String[childNodes.length];
				int i=0;
				for (TreeNode n : childNodes) {
					String name = n.getAttribute("ModelName");
					names[i++] = name;
				}

				EquipmentFilter filter = new EquipmentFilter();
				filter.OR();
				filter.whereName().isInList(names);
				routingController.findEquipments(filter, new CallbackHandler<Set<Equipment>>() {
					@Override
					public void onSuccess(Set<Equipment> result) {
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
	
	private void saveWorkCenter(final String wcName, final String desc, final Set<Equipment> eqs, 
			final CustomAttributes customAttributes, final ChainCallbackHandler<WorkCenter> callback) {
		routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {
			public void onSuccess(final WorkCenter wc) {
				wc.setName(wcName);
				wc.setDescription(desc);
				wc.setEquipments(eqs);
				wc.setCustomAttributes(customAttributes);
				String comment = "Update work center";
				routingController.saveWorkCenter(wc, comment, new CallbackHandler<Void>() {
					public void onSuccess(Void result) {
						callback.onSuccess(wc);
					}
				});
			}
		});
	}
	
	private void updateWorkCenterName(final String oldName, final String newName, final CallbackHandler<Void> callback) {
		routingController.findWorkCenterByName(oldName, new CallbackHandler<WorkCenter>() {
			public void onSuccess(final WorkCenter wc) {
				wc.setName(newName);
				String comment = "Update work center name";
				routingController.saveWorkCenter(wc, comment, new CallbackHandler<Void>() {
					public void onSuccess(Void result) {
						callback.onSuccess(null);
					}
				});
			}
		});
	}
}
