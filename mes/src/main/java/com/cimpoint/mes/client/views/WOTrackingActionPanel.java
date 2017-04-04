/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Constants;
import com.cimpoint.common.Utils;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.LotController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.LotDetailRecord;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.UnitDetailRecord;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class WOTrackingActionPanel extends VLayout{

	//	private List<CButton> listControlButtons;
	private boolean isActive;
	private SelectItem selectStepStatus;
	private SelectItem selectTransition;
	private SelectItem selectReason;
	private TextAreaItem txtAreaConmment;
	private Button btnTrack;
	private DynamicForm dynamicForm;

	private WOTrackingView<?> containerView;
	//private CButton  btnManage;

	private RoutingController routingController;
	private LotController lotController;

	private Lot lot;
	private LotDetailRecord selectedLotDetail;

	private Unit unit;
	private UnitDetailRecord selectedUnitDetail;

	private WOTrackingDetails detail;

	public WOTrackingActionPanel(WOTrackingView<?> containerView, int elementMargin) {
		super(elementMargin);
		this.containerView = containerView;
		isActive = true;
		createView();
	}

	public WOTrackingActionPanel() {
		super();
		createView();
	}


	public void createView(){
		routingController = MESApplication.getMESControllers().getRoutingController();
		lotController = MESApplication.getMESControllers().getLotController();
		this.setShowEdges(true);  
		this.setWidth("140px");  
		this.setMembersMargin(10);  
		this.setLayoutMargin(10);

		StaticTextItem staticTxtStatus = new StaticTextItem();
		staticTxtStatus.setName("txtStepStatus");
		staticTxtStatus.setTitle("Next Step Status");
		staticTxtStatus.setShowTitle(false);
		staticTxtStatus.setDefaultValue("Step Status");
		selectStepStatus = new SelectItem("stepStatus", "Step Status");
		selectStepStatus.setShowTitle(false);

		StaticTextItem staticTxtTransition = new StaticTextItem();
		staticTxtTransition.setName("txtTransition");
		staticTxtTransition.setTitle("Next Transition");
		staticTxtTransition.setShowTitle(false);
		staticTxtTransition.setDefaultValue("Transition");
		selectTransition = new SelectItem("transition", "Transition");
		selectTransition.setShowTitle(false);

		StaticTextItem staticTxtReason = new StaticTextItem();
		staticTxtReason.setName("txtReason");
		staticTxtReason.setTitle("Reason");
		staticTxtReason.setShowTitle(false);
		staticTxtReason.setDefaultValue("Reason");
		selectReason = new SelectItem("reason", "Reason");
		selectReason.setShowTitle(false);

		StaticTextItem staticConmment = new StaticTextItem();
		staticConmment.setName("txtstaticConmment");
		staticConmment.setTitle("staticConmment");
		staticConmment.setShowTitle(false);
		staticConmment.setDefaultValue("Comments");
		txtAreaConmment = new TextAreaItem("Comments");
		txtAreaConmment.setShowTitle(false);

		ButtonItem btnTrack = new ButtonItem("Track");
		btnTrack.setWidth(140);
		btnTrack.setHeight(Constants.HEIGHT_BUTTON);
		btnTrack.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				if(selectedLotDetail == null) {
					MESApplication.showError("Please select one lot in lot details to track.");
					return;
				}

				String stepName = selectedLotDetail.getAttributeAsString("step");
				String wcName = selectedLotDetail.getAttributeAsString("workCenter");
				String status = selectStepStatus.getValueAsString();

				//queued -> started
				//started -> completed
				if(MESConstants.Object.StepStatus.Started.toString().equals(status)) {
					lotController.startLot(lot, stepName, wcName, txtAreaConmment.getValueAsString(), new CallbackHandler<Lot>() {
						@Override
						public void onSuccess(Lot lot) {
							containerView.refreshData(lot);
							containerView.displayTransactionMessage("Lot " + lot.getNumber() + " started successfully");
							resetAll();
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							MESApplication.showError("Fail to start lot number: " + lot.getNumber());
						}
					}); 
				} else if(MESConstants.Object.StepStatus.Completed.toString().equals(status)) {
					String selectTransitionName = selectTransition.getValueAsString();
					lotController.completeLot(lot, stepName, wcName, selectTransitionName, txtAreaConmment.getValueAsString(), new CallbackHandler<Lot>() {

						@Override
						public void onSuccess(Lot result) {
							containerView.refreshData(lot);
							containerView.displayTransactionMessage("Lot " + lot.getNumber() + " completed successfully");
							resetAll();
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							MESApplication.showError("Fail to complete lot number: " + lot.getNumber());
						}
					});
				} else if (MESConstants.Object.StepStatus.Paused.toString().equals(status)) {
					String selectTransitionName = selectTransition.getValueAsString();
					lotController.pauseLot(lot, stepName, wcName, selectTransitionName, txtAreaConmment.getValueAsString(), new CallbackHandler<Lot>() {

						@Override
						public void onSuccess(Lot result) {
							containerView.refreshData(lot);
							containerView.displayTransactionMessage("Lot " + lot.getNumber() + " pause successfully");
							resetAll();
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
							MESApplication.showError("Fail to pause lot number: " + lot.getNumber());
						}
					});
				}
			}
		});


		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		//		vStack.setBorder("1px solid LightGray");
		vStack.setLayoutMargin(5);

		dynamicForm = new DynamicForm();
		dynamicForm.setSize("100%", "100%");
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setNumCols(1);

		dynamicForm.setFields(new FormItem[] {staticTxtStatus, selectStepStatus, staticTxtTransition, selectTransition, staticTxtReason, selectReason, staticConmment, txtAreaConmment, btnTrack});
		vStack.addMember(dynamicForm);
		this.addMember(vStack);
	}

	public void loadLotData(LotDetailRecord record) {
		if (lot != null) {
			routingController.getTransitionAttributesForLot(lot.getId(), new CallbackHandler<TransitionAttributes>() {
				@Override
				public void onSuccess(TransitionAttributes trsnAttrs) {
					if(trsnAttrs != null) {

						String stepName = trsnAttrs.getStepName();
						final String status = MESUtils.Strings.value(trsnAttrs.getStepStatus());

						if (stepName != null) {
							routingController.findStepByName(trsnAttrs.getRoutingName(), stepName, new CallbackHandler<Step>() {
								@Override
								public void onSuccess(Step step) {
									Set<Transition> outgoingTransitions = step.getOutgoingTransitions();
									List<String> transitionNames = new ArrayList<String>();
									for (Transition t: outgoingTransitions) {
										transitionNames.add(t.getName());
									}

									if (transitionNames.size() > 0) {
										Utils.sortStringList(transitionNames);

										String[] names = (String[]) transitionNames.toArray(new String[transitionNames.size()]);
										selectTransition.setValueMap(names);

										if (names.length == 1) {
											selectTransition.setValue(names[0]);
										}
									}
									else {
										selectTransition.setValueMap((String[]) null);
									}

									loadStepStatus(status);

									selectReason.setValueMap(createReasons());
									selectReason.setDefaultToFirstOption(true);
									selectReason.setValue(MESConstants.Object.Reason.OK.toString());
								}							
							});
						}
					}
				}

				@Override
				public void onFailure(Throwable t) {
					MESApplication.showError("Fail to show details of lot number: " + lot.getNumber());
				}
			});

		}
	}

	public void loadUnitData(UnitDetailRecord record) {
		if (unit != null) {
			routingController.getTransitionAttributesForUnit(unit.getId(), new CallbackHandler<TransitionAttributes>() {
				@Override
				public void onSuccess(TransitionAttributes trsnAttrs) {
					if(trsnAttrs != null) {

						String stepName = trsnAttrs.getStepName();
						final String status = MESUtils.Strings.value(trsnAttrs.getStepStatus());

						if (stepName != null) {
							routingController.findStepByName(trsnAttrs.getRoutingName(), stepName, new CallbackHandler<Step>() {
								@Override
								public void onSuccess(Step step) {
									Set<Transition> outgoingTransitions = step.getOutgoingTransitions();
									List<String> transitionNames = new ArrayList<String>();
									for (Transition t: outgoingTransitions) {
										transitionNames.add(t.getName());
									}

									if (transitionNames.size() > 0) {
										Utils.sortStringList(transitionNames);

										String[] names = (String[]) transitionNames.toArray(new String[transitionNames.size()]);
										selectTransition.setValueMap(names);

										if (names.length == 1) {
											selectTransition.setValue(names[0]);
										}
									}
									else {
										selectTransition.setValueMap((String[]) null);
									}

									loadStepStatus(status);

									selectReason.setValueMap(createReasons());
									selectReason.setDefaultToFirstOption(true);
									selectReason.setValue(MESConstants.Object.Reason.OK.toString());
								}							
							});
						}
					}
				}

				@Override
				public void onFailure(Throwable t) {
					MESApplication.showError("Fail to show details of lot number: " + unit.getSerialNumber());
				}
			});

		}
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public UnitDetailRecord getSelectedUnitDetail() {
		return selectedUnitDetail;
	}

	public void setSelectedUnitDetail(UnitDetailRecord selectedUnitDetail) {
		this.selectedUnitDetail = selectedUnitDetail;
	}

	public WOTrackingDetails getDetail() {
		return detail;
	}

	public void setDetail(WOTrackingDetails detail) {
		this.detail = detail;
	}

	public boolean isActive() {
		return isActive;
	}

	public LotDetailRecord getSelectedLotDetail() {
		return selectedLotDetail;
	}

	public void setSelectedLotDetail(LotDetailRecord selectedLotDetail) {
		this.selectedLotDetail = selectedLotDetail;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	private void resetAll(){
		selectedLotDetail = null;

		selectTransition.setValue("");
		selectReason.setValue("");
		selectStepStatus.setValue("");

		selectTransition.setValueMap((String[]) null);
		selectReason.setValueMap((String[]) null);
		selectStepStatus.setValueMap((String[]) null);
		selectTransition.redraw();
		selectReason.redraw();
		selectStepStatus.redraw();
	}

	private void loadStepStatus(String currentStatus) {
		List<String> statuses = new ArrayList<String>();
		if(MESConstants.Object.StepStatus.Queued.toString().equals(currentStatus)) {
			statuses.add(MESConstants.Object.StepStatus.Started.toString());
		} else if(MESConstants.Object.StepStatus.Started.toString().equals(currentStatus)) {
			statuses.add(MESConstants.Object.StepStatus.Completed.toString());
			statuses.add(MESConstants.Object.StepStatus.Paused.toString());
		} else if(MESConstants.Object.StepStatus.Paused.toString().equals(currentStatus)) {
			statuses.add(MESConstants.Object.StepStatus.Completed.toString());
		} 

		if(statuses.size() > 0) {
			Utils.sortStringList(statuses);
			String[] statusData = (String[])statuses.toArray(new String[statuses.size()]);
			selectStepStatus.setValueMap(statusData);
			selectStepStatus.setValue(statusData[0]);
			selectStepStatus.setDefaultToFirstOption(true);
		} else {
			selectStepStatus.setValueMap((String[]) null);
		}
	}

	/**
	 * @TODO: this is a hard code. will be replaced by the DB Object in near future
	 * */
	public static String[] createReasons() {
		String[] reasons = new String[2];
		reasons[0] = MESConstants.Object.Reason.OK.toString();
		reasons[1] = MESConstants.Object.Reason.FAIL.toString();

		return reasons;
	}

}
