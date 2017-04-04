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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.CloseWindowCallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.ClientObjectUtils;
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class StepEditor extends GridRecordEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SelectItem typeSelectItem;
	private SelectItem classRuleSelectItem;
	private SelectItem operationSelectItem;
	private StepEditorSectionStack sectionStack;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();	
	private RoutingEditor routingEditor;
	private ListGrid grid;
	
	private static final String PACKAGE_CLASS_RULE = "com.cimpoint.mes.server.rules.";
	
	public StepEditor(RoutingEditor routingEditor, CSectionStack routingEditorSectionStack, ListGrid grid, Step selStep) {
		super(routingEditor.getEditorContainerView(), "Step Editor", 500); //auto height with fixed dynamicForm height
		
		this.routingEditor = routingEditor;
		this.grid = grid;
		
		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		vStack.setBorder("1px solid LightGray");
		
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight("500px");
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setLeft(30);
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		
		typeSelectItem = new SelectItem("type", "Type");
		typeSelectItem.setWidth(200);
		typeSelectItem.setRequired(true);
		
		classRuleSelectItem = new SelectItem("classRule", "Class Rule");
		classRuleSelectItem.setWidth(200);
		classRuleSelectItem.setRequired(true);
		classRuleSelectItem.setCellHeight(30);
		
		operationSelectItem = new SelectItem("operation", "Operation");
		operationSelectItem.setWidth(200);
		
		sectionStack = new StepEditorSectionStack("500px", "*");		
		sectionStack.addNameDescriptionGridSection("workCenters", "Work Centers");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		//end form
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, typeSelectItem, classRuleSelectItem, 
				operationSelectItem, sectionStackCI, getDefaultActionButtonsCanvasItem() });
		
		vStack.setLayoutMargin(20);
		vStack.addMember(dynamicForm);
				
		super.addItem(vStack);
				
		if (selStep == null)
			resetForm();
		else
			setForm(selStep);
	}

	@Override
	public void onOKButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
		
		final String routingName = routingEditor.getRoutingName();
		final String routingRev = routingEditor.getRoutingRevision();
		final String stepName = nameTextItem.getValueAsString();
		final String description = descTextItem.getValueAsString();
		final StepType stepType = StepType.valueOf(typeSelectItem.getValueAsString());		
		final String ruleClassName =  PACKAGE_CLASS_RULE + classRuleSelectItem.getValueAsString();
		final Set<WorkCenter> workCenters = sectionStack.getGridSectionDataAsWorkCenterSet("workCenters");
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		final StepEditor thisEditor = this;
		
		/*routingController.findRoutingByNameAndRevision(routingName, routingRev, new CloseWindowCallbackHandler<Routing>(thisEditor) {
			@Override
			public void onSuccess(final Routing routing) {
				routingController.findOperationByName(operationSelectItem.getValueAsString(), new CloseWindowCallbackHandler<Operation>(thisEditor) {
					@Override
					public void onSuccess(final Operation operation) {
						routingController.findStepByName(routingName, stepName, new CloseWindowCallbackHandler<Step>(thisEditor) {
							@Override
							public void onSuccess(final Step step) {
								if (step == null) {
									Step newStep = ClientObjectUtils.Steps.newStep(stepType, stepName, description, routing, operation, workCenters,
											ruleClassName, customAttributes);
									routingEditor.addStep(grid, newStep);
									
									destroy(); //close the dialog
								}
								else {
									step.setDescription(description);
									step.setType(stepType);
									step.setRuleClassName(ruleClassName);
									step.setOperation(operation);
									step.setWorkCenters(workCenters);
									step.setCustomAttributes(customAttributes);
									
									routingEditor.updateStep(grid, step);
									
									destroy(); //close the dialog				
								}						
							}
						});			
					}	
				});
			}	
		});		*/
	}
	
	@Override
	public void onCancelButtionClicked(ClickEvent event) {
		this.destroy();
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter step name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		showStepTypes();
		
		String[] ruleClassNames = new String[] {
				"DefaultAutoQueuingStepRule",	
				"DefaultAutoStartStepRule"		
			};
		classRuleSelectItem.setValueMap(ruleClassNames);
				
		routingController.findAllOperationNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> opeNames) {
				if (opeNames != null && !opeNames.isEmpty()) {
					opeNames.add("");
					String[] sortedSiteNames = Utils.toSortedStringArray(opeNames);
					operationSelectItem.setValueMap(sortedSiteNames);	
				}
			}			
		});
		
		sectionStack.setGridData("workCenters", new ListGridRecord[0]);
		
		sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);
	}
	
	public String getStep() {
		return nameTextItem.getValueAsString();
	}
	
	public String getOperation() {
		return operationSelectItem.getValueAsString();
	}
	
	//there is only 1 entry and 1 exit step
	private void showStepTypes() {
		if (routingEditor.getEntryStep() != null && routingEditor.getExitStep() != null) {
			typeSelectItem.setValueMap(StepType.Inner.toString());
		}
		else if (routingEditor.getEntryStep() != null && routingEditor.getExitStep() == null) {
			typeSelectItem.setValueMap(StepType.Inner.toString(), StepType.End.toString());
		}
		else if (routingEditor.getEntryStep() == null && routingEditor.getExitStep() != null) {
			typeSelectItem.setValueMap(StepType.Start.toString(), StepType.Inner.toString());
		}
		else {
			typeSelectItem.setValueMap(StepType.Start.toString(), StepType.Inner.toString(), StepType.End.toString());
		}
	}
	
	private void setForm(Step step) {
		if (step != null) {
			nameTextItem.setValue(step.getName());
			nameTextItem.setRequired(false);
			nameTextItem.setAttribute("readOnly", true);
			descTextItem.setValue(step.getDescription());			
			showStepTypes();			
			typeSelectItem.setValue(step.getType());
			String className = step.getRuleClassName();
			if (className.lastIndexOf(".") != -1) {
				className = className.substring(className.lastIndexOf(".") + 1, className.length());
			}
			classRuleSelectItem.setValue(className);
			try {
				Operation operation = step.getOperation();
				if (operation != null) {
					operationSelectItem.setValue(operation.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// get list of work centers and set to work center grid
			Set<WorkCenter> workCenters = step.getWorkCenters();
			sectionStack.setGridWorkCentersData("workCenters", workCenters);
		}
	}

	public String getFromStepName() {
		return nameTextItem.getValueAsString();
	}

	public String getFromOperationName() {
		return operationSelectItem.getValueAsString();
	}
}
