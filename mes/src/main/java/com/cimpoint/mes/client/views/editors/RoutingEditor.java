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
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.views.designers.DrawStep;
import com.cimpoint.mes.client.views.designers.DrawTransition;
import com.cimpoint.mes.client.views.designers.RoutingDesigner;
import com.cimpoint.mes.client.views.designers.RoutingDesigner.SelectionHandler;

public class RoutingEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private NameProperty propStepName;
	private TextProperty propStepRule;
	private NameProperty propTrsnName;
	private TextProperty propTrsnReasons;
	private RoutingDesigner routingDesigner;
	private DrawStep entryStepWidget;
	private DrawStep exitStepWidget;
	
	public RoutingEditor() {
		super(new RoutingDesigner());
		
		routingController = MESApplication.getMESControllers().getRoutingController();
		routingDesigner = (RoutingDesigner) super.getModelDesigner();
		
		routingDesigner.setSelectionHandler(new SelectionHandler() {
			@Override
			public void onStepSelected(DrawStep drawStep) {
				propTrsnName.hide();
				propTrsnReasons.hide();
				propStepName.show();
				propStepRule.show();	
				propStepName.setValue(drawStep.getName());
				propStepRule.setValue(drawStep.getStepRule());
			}

			@Override
			public void onTransitionSelected(DrawTransition drawTransition) {
				propStepName.hide();
				propStepRule.hide();				
				propTrsnName.show();
				propTrsnReasons.show();
				propTrsnName.setValue(drawTransition.getName());
				propTrsnReasons.setValueSet(drawTransition.getReasons());
			}			
		});
		
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		
		propStepName = new NameProperty("Step Name", new NamePropertyEditor(this));
		propStepName.setVisible(false);
		propStepRule = new TextProperty("Step Rule", new StepRulePropertyEditor(this));	
		propStepRule.setReadOnly(true);
		propStepRule.setVisible(false);
		
		propTrsnName = new NameProperty("Transition Name", new NamePropertyEditor(this));
		propTrsnName.setVisible(false);
		propTrsnReasons = new TextProperty("Transition Reasons", new TransitionReasonsPropertyEditor(this));		
		propTrsnReasons.setReadOnly(true);
		propTrsnReasons.setVisible(false);
		
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes, propStepName, propStepRule, propTrsnName, propTrsnReasons);
	}
	
	@Override
	public void onInitialize(final String routingName) {
		if (initialized) return;
		
		routingController.findRoutingByName(routingName, new CallbackHandler<Routing>() {
			@Override
			public void onSuccess(Routing r) {
				propName.setValue(r.getName());
				propDesc.setValue(r.getDescription());
				propCustomAttributes.setCustomAttributes(r.getCustomAttributes());
				
				//TODO test
				entryStepWidget = routingDesigner.newStartStep(50, 50);
				exitStepWidget = routingDesigner.newEndStep(routingDesigner.getWidth() - 100, 50); 
								
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
		
		MESApplication.showYesNoCancelMessage("Save Routing", "Routing " + 
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
		saveRouting(eqName, desc, customAttributes, callback);
	}

	@Override
	public void onAddButtionClicked(CallbackHandler<Void> callback) {		
	}

	@Override
	public void onRemoveButtionClicked(CallbackHandler<Void> callback) {
		//TODO add remove code
		callback.onSuccess(null);
	}

	@Override
	public void onRefreshButtionClicked(CallbackHandler<Void> callback) {
		initialized = false;
		onInitialize(getName());
		callback.onSuccess(null);
	}

	@Override
	public void onModelNameChanged(String oldName, String newName, CallbackHandler<Void> callback) {
		updateRoutingName(oldName, newName, callback);
	}
			
	private void saveRouting(final String opName, final String desc, 
			final CustomAttributes customAttributes, final CallbackHandler<Void> callback) {
		routingController.findRoutingByName(opName, new CallbackHandler<Routing>() {
			public void onSuccess(final Routing r) {
				r.setName(opName);
				r.setDescription(desc);
				r.setCustomAttributes(customAttributes);
				String comment = "Update routing";
				routingController.saveRouting(r, comment, callback);
			}
		});
	}
	
	private void updateRoutingName(final String oldName, final String newName, final CallbackHandler<Void> callback) {
		routingController.findRoutingByName(oldName, new CallbackHandler<Routing>() {
			public void onSuccess(final Routing r) {
				r.setName(newName);
				String comment = "Update routing name";
				routingController.saveRouting(r, comment, callback);
			}
		});
	}
}
