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
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;

public class OperationEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	
	public OperationEditor() {
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
		
		routingController.findOperationByName(eqName, new CallbackHandler<Operation>() {
			@Override
			public void onSuccess(Operation op) {
				designerTreeGrid.clear();				
				propName.setValue(op.getName());
				propDesc.setValue(op.getDescription());
				propCustomAttributes.setCustomAttributes(op.getCustomAttributes());
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
		
		MESApplication.showYesNoCancelMessage("Save Operation", "Operation " + 
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
		saveOperation(eqName, desc, customAttributes, callback);
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
		updateOperationName(oldName, newName, callback);
	}
		
	private void saveOperation(final String opName, final String desc, 
			final CustomAttributes customAttributes, final CallbackHandler<Void> callback) {
		routingController.findOperationByName(opName, new CallbackHandler<Operation>() {
			public void onSuccess(final Operation op) {
				op.setName(opName);
				op.setDescription(desc);
				op.setCustomAttributes(customAttributes);
				String comment = "Update operation";
				routingController.saveOperation(op, comment, callback);
			}
		});
	}
	
	private void updateOperationName(final String oldName, final String newName, final CallbackHandler<Void> callback) {
		routingController.findOperationByName(oldName, new CallbackHandler<Operation>() {
			public void onSuccess(final Operation op) {
				op.setName(newName);
				String comment = "Update operation name";
				routingController.saveOperation(op, comment, callback);
			}
		});
	}
}
