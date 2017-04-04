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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;

public class EquipmentEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	
	public EquipmentEditor() {
		super(new RoutingResourceDesigner());
		routingController = MESApplication.getMESControllers().getRoutingController();
		designerTreeGrid = ((RoutingResourceDesigner) super.getModelDesigner()).getModelTreeGrid();
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes);
	}
	
	@Override
	public void onInitialize(final String eqName) {
		if (initialized) return;
		
		routingController.findEquipmentByName(eqName, new CallbackHandler<Equipment>() {
			@Override
			public void onSuccess(Equipment eq) {
				designerTreeGrid.clear();				
				propName.setValue(eq.getName());
				propDesc.setValue(eq.getDescription());
				propCustomAttributes.setCustomAttributes(eq.getCustomAttributes());
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.RootModel, eqName, eqName, "pieces/16/site.png");
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
		
		MESApplication.showYesNoCancelMessage("Save Equipment", "Equipment " + 
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
		String eqName = propName.getValueAsString();
		String desc = propDesc.getValueAsString();
		CustomAttributes customAttributes = propCustomAttributes.getCustomAttributes();		
		saveEquipment(eqName, desc, customAttributes, callback);
	}

	@Override
	public void onAddButtionClicked(CallbackHandler<Void> callback) {		
	}

	@Override
	public void onRemoveButtionClicked(CallbackHandler<Void> callback) {		
	}

	@Override
	public void onRefreshButtionClicked(CallbackHandler<Void> callback) {
		initialized = false;
		onInitialize(getName());
		callback.onSuccess(null);
	}

	@Override
	public void onModelNameChanged(String oldName, String newName, CallbackHandler<Void> callback) {
		updateEquipmentName(oldName, newName, callback);
	}
		
	private void saveEquipment(final String eqName, final String desc, 
			final CustomAttributes customAttributes, final CallbackHandler<Void> callback) {
		routingController.findEquipmentByName(eqName, new CallbackHandler<Equipment>() {
			public void onSuccess(final Equipment eq) {
				eq.setName(eqName);
				eq.setDescription(desc);
				eq.setCustomAttributes(customAttributes);
				String comment = "Update equipment";
				routingController.saveEquipment(eq, comment, callback);
			}
		});
	}
	
	private void updateEquipmentName(final String oldName, final String newName, final CallbackHandler<Void> callback) {
		routingController.findEquipmentByName(oldName, new CallbackHandler<Equipment>() {
			public void onSuccess(final Equipment eq) {
				eq.setName(newName);
				String comment = "Update equipment name";
				routingController.saveEquipment(eq, comment, callback);
			}
		});
	}
}
