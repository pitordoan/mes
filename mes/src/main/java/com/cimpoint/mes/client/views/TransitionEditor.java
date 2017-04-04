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
 *     Duy Chung - Edit implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import java.util.Set;

import com.cimpoint.common.CloseWindowCallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.DictionaryController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.ClientObjectUtils;
import com.cimpoint.mes.client.objects.Dictionary;
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.TransitionRecord;
import com.cimpoint.mes.common.entities.Quantity;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VStack;

public class TransitionEditor extends GridRecordEditor {

	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private DictionaryController dictionaryController = MESApplication.getMESControllers().getDictionaryController();
	private SelectItem fromStepSelectItem;
	private TextItem fromOperation;
	private SelectItem toStepSelectItem;
	private TextItem toOperation;
	private TextItem transitionName;
	private ReasonsItem reasonItems;
	private ListGrid grid;
	private DynamicForm dynamicForm;
	private RoutingEditor routingEditor;
	
	public TransitionEditor(RoutingEditor routingEditor,boolean isEdit, CSectionStack sectionStack, ListGrid grid) {
		super(routingEditor.getEditorContainerView(), "Transition Editor", 510, 170);
		
		this.routingEditor = routingEditor;
		this.grid = grid;
		
		TitleOrientation titleOrientation = TitleOrientation.TOP;
		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		vStack.setBorder("1px solid LightGray");

		// start form
		final RoutingEditor froutingEditor = routingEditor;
		
		dynamicForm = new DynamicForm();
		dynamicForm.setWidth(510);
		dynamicForm.setHeight(130);
		dynamicForm.setItemLayout(FormLayoutType.TABLE);
		dynamicForm.setTitleOrientation(titleOrientation);
		dynamicForm.setNumCols(2);
		dynamicForm.setWrapItemTitles(false);

		fromStepSelectItem = new SelectItem("fromStep", "From Step");
		fromStepSelectItem.setWidth(250);
		fromStepSelectItem.setAlign(Alignment.LEFT);
		fromStepSelectItem.setRequired(true);
		fromStepSelectItem.addChangedHandler(new ChangedHandler() {
			// set value for to operation when to step is selected
			@Override
			public void onChanged(ChangedEvent event) {
				String stepName = fromStepSelectItem.getValueAsString();
				Step step = froutingEditor.findStepByName(stepName);
				if (step != null) {
					Operation operation = step.getOperation();					
					if (operation != null) {
						fromOperation.setValue(operation.getName());
					}
				}
			}
		});

		fromOperation = new TextItem("fromOperation", "From Operation");
		fromOperation.setWidth(250);
		fromOperation.setAlign(Alignment.LEFT);
		fromOperation.setAttribute("readOnly", true);
		
		toStepSelectItem = new SelectItem("toStep", "To Step");
		toStepSelectItem.setWidth(250);
		toStepSelectItem.setAlign(Alignment.LEFT);
		toStepSelectItem.setRequired(true);
		toStepSelectItem.addChangedHandler(new ChangedHandler() {
			// set value for to operation when to step is selected
			@Override
			public void onChanged(ChangedEvent event) {
				String stepName = toStepSelectItem.getValueAsString();
				Step step = froutingEditor.findStepByName(stepName);
				if (step != null) {
					Operation operation = step.getOperation();					
					if (operation != null) {
						toOperation.setValue(operation.getName());
					}
				}
			}
		});
		
		toOperation = new TextItem("toOperation", "To Operation");
		toOperation.setWidth(250);
		toOperation.setWidth(250);
		toOperation.setAlign(Alignment.LEFT);
		toOperation.setAttribute("readOnly", true);
		
		transitionName = new TextItem("transitionName", "Transition Name");
		transitionName.setRequired(true);
		transitionName.setColSpan(2);
		transitionName.setWidth(500);

		reasonItems = new ReasonsItem("reasons", "Reasons");
		reasonItems.setAttribute("readOnly", true);
		reasonItems.setRequired(true);
		reasonItems.setWidth(500);
		reasonItems.setColSpan(2);
		reasonItems.setValue("ReasonList1"); //TODO fetch me from the system
		
		dynamicForm.setCellPadding(5);
		CanvasItem ci = getDefaultActionButtonsCanvasItem();
		ci.setColSpan(2);
		dynamicForm.setFields(new FormItem[] { fromStepSelectItem, toStepSelectItem, fromOperation, 
				toOperation, transitionName, reasonItems, ci });

		vStack.setLayoutMargin(20);
		vStack.addMember(dynamicForm);

		addItem(vStack);
		
		if (isEdit) {
			TransitionRecord r = (TransitionRecord)grid.getSelectedRecord();			
			setForm(r.getTransition());
		}
		else {
			resetForm();
		}
	}

	@Override
	public void onOKButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;

		final String trsnName = transitionName.getValueAsString();
		final String fromStepName = fromStepSelectItem.getValueAsString();
		final String toStepName = toStepSelectItem.getValueAsString();
		final Quantity transferQuantity = null;
		final CustomAttributes customAttributes = null;
		final String reasonsName = reasonItems.getValueAsString(); 
		final TransitionEditor transitionEditor = this;
		
		routingController.findTransitionByName(routingEditor.getRoutingName(), trsnName, new CloseWindowCallbackHandler<Transition>(transitionEditor) {
			@Override
			public void onSuccess(final Transition transition) {
				final Step fromStep = routingEditor.findStepByName(fromStepName);
				final Step toStep = routingEditor.findStepByName(toStepName);
				dictionaryController.findDictionaryByName(reasonsName, new CloseWindowCallbackHandler<Dictionary>(transitionEditor) {
					@Override
					public void onSuccess(final Dictionary reasons) {
						if (transition == null) {
							Transition newTrsn = ClientObjectUtils.Transitions.newTransition(trsnName, transferQuantity, reasons, customAttributes);
							fromStep.connects(toStep, newTrsn);
							routingEditor.addTransition(grid, newTrsn);
											
							destroy(); //close the dialog
						}
						else {
							fromStep.connects(toStep, transition);
							routingEditor.updateTransition(grid, transition);
							
							destroy(); //close the dialog
						}	
					}		
				});
			}
		});			
	}

	@Override
	public void onCancelButtionClicked(ClickEvent event) {
		this.destroy();
	}

	private class ReasonsItem extends TextItem {

		public ReasonsItem(String name, String title) {
			super(name, title);
			
			FormItemIcon icon = new FormItemIcon();
			icon.setHeight(BUTTON_HEIGHT);
			icon.setWidth(BUTTON_HEIGHT);
			setIcons(icon);
			
			addIconClickHandler(new IconClickHandler() {
				@Override
				public void onIconClick(com.smartgwt.client.widgets.form.fields.events.IconClickEvent event) {
					DictionaryEditor reasonEditor = new DictionaryEditor();
					reasonEditor.show();
				}
			});

		}
	}
	
	// load data
	private void resetForm() {
		Set<String> stepNames = routingEditor.getStepNames();
		showFromStepNames(stepNames);
		showToStepNames(stepNames);
	}
	
	// for editing transition
	private void setForm(Transition transition) {
		if (transition !=  null) {
			try {
				Step fromStep = transition.getFromStep();
				Step toStep = transition.getToStep();
				
				fromStepSelectItem.setValue(fromStep.getName());
				fromStepSelectItem.setRequired(false);
				fromStepSelectItem.setAttribute("readOnly", true);
				Set<String> stepNames = routingEditor.getStepNames();
				showToStepNames(stepNames);
				toStepSelectItem.setValue(toStep.getName());
				transitionName.setValue(transition.getName());
				transitionName.setAttribute("readOnly", true);
				
				if (fromStep.getOperation() != null) {
					fromOperation.setValue(fromStep.getOperation().getName());
				}
				
				if (toStep.getOperation() != null) {
					toOperation.setValue(toStep.getOperation().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void showFromStepNames(Set<String> stepNames) {
		if (stepNames != null && !stepNames.isEmpty()) {
			stepNames.add("");
			String[] sortedStepNames = Utils.toSortedStringArray(stepNames);
			fromStepSelectItem.setValueMap(sortedStepNames);
		} else {
			fromStepSelectItem.setValueMap((String)null);
		}
	}
	
	private void showToStepNames(Set<String> stepNames) {
		if (stepNames != null && !stepNames.isEmpty()) {
			stepNames.add("");
			String[] sortedStepNames = Utils.toSortedStringArray(stepNames);
			toStepSelectItem.setValueMap(sortedStepNames);
		} else {
			toStepSelectItem.setValueMap((String)null);
		}
	}
}
