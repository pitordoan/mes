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
package com.cimpoint.mes.client.views.wot;

import java.util.Iterator;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Constants;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.views.common.ComponentView;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class ControlPanel extends ComponentView<ControlPanel.EventHandler> {
	private boolean registeredHandlers = false;
	private SelectItem selectStepStatus;
	private SelectItem selectTransition;
	private SelectItem selectReason;
	private TransitionAttributes currentTransitionAttrs;
	private Step currentStep;
	
	public ControlPanel(String width, String height) {
		this.setWidth(width);
		this.setHeight(height);
		this.setEdgeSize(1);
		this.setShowEdges(true);  
		this.setMembersMargin(10); 

		StaticTextItem staticTxtStatus = new StaticTextItem();
		staticTxtStatus.setName("txtStepStatus");
		staticTxtStatus.setTitle("Next Step Status");
		staticTxtStatus.setShowTitle(false);
		staticTxtStatus.setDefaultValue("Step Status");
		selectStepStatus = new SelectItem("stepStatus", "Step Status");
		selectStepStatus.setWidth("100%");
		selectStepStatus.setShowTitle(false);

		StaticTextItem staticTxtTransition = new StaticTextItem();
		staticTxtTransition.setName("txtTransition");
		staticTxtTransition.setTitle("Next Transition");
		staticTxtTransition.setShowTitle(false);
		staticTxtTransition.setDefaultValue("Transition");
		selectTransition = new SelectItem("transition", "Transition");
		selectTransition.setWidth("100%");
		selectTransition.setShowTitle(false);
		selectTransition.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Set<Transition> transitions = currentStep.getOutgoingTransitions();
				if (transitions != null) {
					Iterator<Transition> it = transitions.iterator();
					while (it.hasNext()) {
						Transition trsn = it.next();
						if (trsn.getName().equals(selectTransition.getValueAsString())) {
							String[] reasons = trsn.getReasons();
							selectReason.setValueMap(reasons);
							break;
						}
					}
				}
			}			
		});
		

		StaticTextItem staticTxtReason = new StaticTextItem();
		staticTxtReason.setName("txtReason");
		staticTxtReason.setTitle("Reason");
		staticTxtReason.setShowTitle(false);
		staticTxtReason.setDefaultValue("Reason");
		selectReason = new SelectItem("reason", "Reason");
		selectReason.setWidth("100%");
		selectReason.setShowTitle(false);

		StaticTextItem staticConmment = new StaticTextItem();
		staticConmment.setName("txtstaticConmment");
		staticConmment.setTitle("staticConmment");
		staticConmment.setShowTitle(false);
		staticConmment.setDefaultValue("Comments");
		TextAreaItem txtAreaConmment = new TextAreaItem("Comments");
		txtAreaConmment.setWidth("100%");
		txtAreaConmment.setShowTitle(false);

		ButtonItem btnTrack = new ButtonItem("Track");
		btnTrack.setWidth("100%");
		btnTrack.setHeight(Constants.HEIGHT_BUTTON);
		
		//VStack vStack = new VStack();
		//vStack.setMembersMargin(5);
		//vStack.setLayoutMargin(5);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setSize("100%", "100%");
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setNumCols(1);
		dynamicForm.setMargin(5);

		dynamicForm.setFields(new FormItem[] {staticTxtStatus, selectStepStatus, staticTxtTransition, selectTransition, 
				staticTxtReason, selectReason, staticConmment, txtAreaConmment, btnTrack});
		//vStack.addMember(dynamicForm);
		//this.addMember(vStack);
		
		this.addMember(dynamicForm);
		
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				registerHandlers();
			}			
		});
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		
		MESApplication.getWOTrackingView().getSearchPanel().addEventHandler(new SearchPanel.EventHandler(ControlPanel.class) {
			@Override
			public void onSearchFound(Class<?> handlerClass, Object object) {
				if (handlerClass == ControlPanel.class) {
					prepareTrackingObjectByType(object);
				}
			}			
		});
		
		MESApplication.getWOTrackingView().getDataPanel().addEventHandler(new DataPanel.EventHandler(ControlPanel.class) {
			@Override
			public void onTabSelected(Class<?> handlerClass, String tabName) {
				//showDataOnTab(activeTab);
			}

			@Override
			public void onTabDeselected(Class<?> handlerClass, String tabName) {				
			}	
		});
		
		MESApplication.getWOTrackingView().getObjectHierarachyPanel().addEventHandler(new ObjectHierarchyPanel.EventHandler(ControlPanel.class) {
			@Override
			public void onObjectSelected(Class<?> handlerClass, Object object, MESConstants.Object.Type objectType, String objectName) {
				if (handlerClass == ControlPanel.class) {
					prepareTrackingObjectByType(object);
				}
			}			
		});
		
		registeredHandlers = true;
	}
	
	private void prepareTrackingObjectByType(Object object) {
		if (object instanceof WorkOrder) {
			prepareWorkOrderTracking((WorkOrder) object);
		}
		else if (object instanceof Lot) {
			prepareLotTracking((Lot) object);
		}
		else if (object instanceof Unit) {
			prepareUnitTracking((Unit) object);
		}
		else if (object instanceof Batch) {
			prepareBatchTracking((Batch) object);
		}
		else if (object instanceof Container) {
			prepareContainerTracking((Container) object);
		}
	}
	
	private void prepareContainerTracking(Container object) {
		// TODO Auto-generated method stub
		
	}

	private void prepareBatchTracking(Batch object) {
		// TODO Auto-generated method stub
		
	}

	private void prepareUnitTracking(Unit object) {
		
	}

	private void prepareLotTracking(Lot object) {
		object.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {
			@Override
			public void onSuccess(TransitionAttributes attrs) {
				populateData(attrs);
			}			
		});
	}

	private void prepareWorkOrderTracking(WorkOrder object) {
		/*object.getTransitionAttributes(new CallbackHandler<TransitionAttributes>() {
			@Override
			public void onSuccess(TransitionAttributes attrs) {
				populateData(attrs);
			}			
		});*/
	}
	
	private void populateData(TransitionAttributes attrs) {
		if (attrs != null) {
			currentTransitionAttrs = attrs;
			
			String routingName = attrs.getRoutingName();
			String stepName = attrs.getStepName();
			if (routingName != null && stepName != null) {
				MESApplication.getMESControllers().getRoutingController().findStepByName(routingName, stepName, new CallbackHandler<Step>() {
					@Override
					public void onSuccess(Step step) {
						if (step != null) {
							currentStep = step;
							String[] statuses = step.getStepStatusNames();
							selectStepStatus.setValueMap(statuses);
							
							Set<Transition> transitions = step.getOutgoingTransitions();
							if (transitions != null) {
								String[] trsnArr = new String[transitions.size()];
								int i=0;
								Iterator<Transition> it = transitions.iterator();
								while (it.hasNext()) {
									Transition trsn = it.next();
									trsnArr[i++] = trsn.getName();
								}

								selectTransition.setValueMap(trsnArr);
							}
						}
					}						
				});
			}
		}
	}

	public static abstract class EventHandler {
		public abstract void onObjectTracked(Object object);
	}
}
