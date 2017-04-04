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
package com.cimpoint.mes.client.views;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.views.EditorSectionStack.StepRecord;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class RoutingEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private TextItem revisionItem;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();		
	private RoutingEditorSectionStack sectionStack;
	private ListingPanel listingPanel;
	private Step entryStep;
	private Step exitStep;
	
	@Override
	public String getTitle() {
		return "Routing Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {
		viewCreated = false;
	}

	private void createView() {
		if (viewCreated) return;
		
		this.setWidth(624);
		
		VStack vStack = new VStack();
		vStack.setLeft(30);
		vStack.setHeight100();
		
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight("100%");
		dynamicForm.setWrapItemTitles(false);
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		
		revisionItem = new TextItem("revision", "Revision");
		revisionItem.setWidth(200);
		revisionItem.setLength(255);
		revisionItem.setRequired(true);
		
		sectionStack = new RoutingEditorSectionStack(this, "500px", "*");		
		sectionStack.addStepGridSection("steps", "Steps");		
		sectionStack.addTransitionsGridSection("transitions", "Transitions");
        sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");        
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, revisionItem, sectionStackCI, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
	}
	
	@Override
	public void onNewButtionClicked(ClickEvent event) {
		resetForm();
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate() || !validateStepConnections())
			return;
		
		final String name = nameTextItem.getValueAsString();
		final String description = descTextItem.getValueAsString();
		final String revision = revisionItem.getValueAsString();
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		final Step entryStep = getEntryStep();
		/*routingController.findRoutingByNameAndRevision(name, revision, new CallbackHandler<Routing>() {

			@Override
			public void onSuccess(final Routing routing) {
				if ( routing == null ) {
					final String comment = "create new routing";
					
					routingController.createRouting(name, description, revision, entryStep, customAttributes, comment, new CallbackHandler<Routing>() {
						@Override
						public void onSuccess(Routing routing) {
							if (routing != null) {
								//String nodeName = routing.getName() + "Rev" + routing.getRevision();
								//String text = routing.getName() + " - Rev" + routing.getRevision();
								String nodeName = routing.getName();
								String text = routing.getName();
								listingPanel.addSectionTreeNode("routingsSection", nodeName, text, "pieces/16/piece_green.png");
							}
						}
					});
					
				} else {
					routing.setEntryStep(entryStep);
					routing.setDescription(description);
					routing.setCustomAttributes(customAttributes);
					Set<Step> steps = sectionStack.getGridSectionDataAsStepSet("steps");
					routing.setSteps(steps);
					String comment = "Update routing";
										
					routingController.saveRouting(routing, comment, new CallbackHandler<Void>() {
						public void onSuccess(Void result) {
							try {
								String nodeName = routing.getName();
								String text = routing.getName();
								listingPanel.updateSectionTreeNode("routingsSection", nodeName, text, "pieces/16/piece_green.png");
							} catch (Exception ex) {
								MESApplication.showError(ex.getMessage());
							}
						}						
					});
				}
			}
		});		*/
	}

	private boolean validateStepConnections() {
		Set<Step> steps = getSteps();
		if (steps != null && !steps.isEmpty()) {
			for (Step s: steps) {
				if ((s.getType() == StepType.Inner) && (s.getIncomingTransitions() == null || s.getIncomingTransitions().isEmpty() ||
						s.getOutgoingTransitions() == null || s.getOutgoingTransitions().isEmpty())) {
					MESApplication.showOKMessage("Inner step '" + s.getName() + "' is not completely connected.");
					return false;
				}
				else if ((s.getType() == StepType.Start) && (s.getOutgoingTransitions() == null || s.getOutgoingTransitions().isEmpty())) {
					MESApplication.showOKMessage("Entry step '" + s.getName() + "' is not completely connected.");
					return false;
				}
				else if ((s.getType() == StepType.End) && (s.getIncomingTransitions() == null || s.getIncomingTransitions().isEmpty())) {
					MESApplication.showOKMessage("Exit step '" + s.getName() + "' is not completely connected.");
					return false;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		this.setErrorMessage("Remove button clicked");
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Routing...")) {
			resetForm();
		}
		else {			
			String[] tokens = treeNodeText.split(" - Rev");
			String routingName = tokens[0].trim();
			String revision = tokens[1].trim();
			/*routingController.findRoutingByNameAndRevision(routingName, revision, new CallbackHandler<Routing>() {
				public void onSuccess(Routing routing) {
					setForm(routing);
				}				
			});*/			
		}
	}
	
	private void resetForm() {
		this.entryStep = null;
		this.exitStep = null;
		
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter routing name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		revisionItem.clearValue();
		revisionItem.setPrompt("Enter a revision");
		
		sectionStack.setGridData("steps", new ListGridRecord[0]);
		sectionStack.setGridData("transitions", new ListGridRecord[0]);
		sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);		
	}

	public String getRoutingName() {
		return nameTextItem.getValueAsString();
	}
	
	public void addStep(ListGrid grid, Step step) {
		sectionStack.addGridStepData("steps", step);
		updateEntryAndExitSteps();			
	}
		
	public void updateStep(ListGrid grid, Step step) {
		sectionStack.updateGridStepData("steps", step);
		updateEntryAndExitSteps();	
	}
	
	private void updateEntryAndExitSteps() {
		this.entryStep = null;
		this.exitStep = null;
		
		ListGridRecord[] stepRecords = sectionStack.getGridData("steps");
		if (stepRecords != null) {
			for (ListGridRecord r: stepRecords) {
				StepRecord sr = (StepRecord)r;
				Step s = sr.getStep();
				if (s.getType() == StepType.Start) {
					this.entryStep = s;
					break;
				}
			}
			
			for (ListGridRecord r: stepRecords) {
				StepRecord sr = (StepRecord)r;
				Step s = sr.getStep();
				if (s.getType() == StepType.End) {
					this.exitStep = s;
					break;
				}
			}
		}
	}
	
	public void removeStep(ListGrid grid, Step step) {
		sectionStack.removeGridStepData("steps", step);
	}
	
	public void addTransition(ListGrid grid, Transition newTransition) {
		sectionStack.addGridTransitionData("transitions", newTransition);
	}
	
	public void updateTransition(ListGrid grid, Transition transition) {
		sectionStack.updateGridTransitionData("transitions", transition);
	}
	
	public void removeTransition(ListGrid grid, Transition transition) {
		sectionStack.removeGridTransitionData("transitions", transition);
	}

	public Set<Step> getSteps() {
		return sectionStack.getGridSectionDataAsStepSet("steps");
	}
	
	public Set<Transition> getTransitions() {
		return sectionStack.getGridSectionDataAsTransitionSet("transitions");
	}
	
	public Set<String> getStepNames() {
		Set<Step> steps = sectionStack.getGridSectionDataAsStepSet("steps");
		Set<String> names = new HashSet<String>();
		for (Step s: steps) {
			names.add(s.getName());
		}
		return names;
	}

	public Step findStepByName(String stepName) {
		Set<Step> steps = getSteps();
		for (Step s: steps) {
			if (s.getName().equals(stepName)) {
				return s;
			}
		}
		return null;
	}
	
	public Transition findTransitionByName(String transName) {
		Set<Transition> transitions = getTransitions();
		for (Transition t: transitions) {
			if (t.getName().equals(transName)) {
				return t;
			}
		}
		return null;
	}	
	
	private void setForm(final Routing routing) {
		resetForm();
		
		if ( routing != null ) {
			nameTextItem.setValue(routing.getName());			
			descTextItem.setValue(routing.getDescription());			
			//revisionItem.setValue(routing.getRevision());	
			
			//loading steps
			Set<Step> steps = routing.getSteps();
			this.entryStep = routing.getEntryStep();
			this.exitStep = routing.getExitStep();			
			sectionStack.setGridStepsData("steps", steps);
			
			//load transitions
			Set<Transition> trans = new HashSet<Transition>();
			for (Step step : steps) {
				Set<Transition> outs = step.getOutgoingTransitions();
				trans.addAll(outs);
			}
			sectionStack.setGridTransitionsData("transitions", trans);
			
			sectionStack.setGridCustomAttributesData("customAttributes", routing.getCustomAttributes());			
		}
	}
	
	public boolean validateForm() {
		return dynamicForm.validate();
	}

	public String getRoutingRevision() {
		return revisionItem.getValueAsString();
	}

	public Step getEntryStep() {
		return entryStep;
	}

	public void setEntryStep(Step entryStep) {
		this.entryStep = entryStep;
	}

	public Step getExitStep() {
		return exitStep;
	}

	public void setExitStep(Step exitStep) {
		this.exitStep = exitStep;
	}
}
