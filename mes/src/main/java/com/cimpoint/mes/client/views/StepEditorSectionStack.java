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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.views.CGrid.ConfirmButton;
import com.cimpoint.common.views.CGrid.EditorRecord;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.filters.WorkCenterFilter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StepEditorSectionStack extends EditorSectionStack {
	
	public StepEditorSectionStack(String width, String height) {
		super(width, height);
	}
	
	public void addTransitionGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE | CSectionStack.BUTTON_ADD | CSectionStack.BUTTON_REMOVE;
		boolean expanded = false;
		ListGridField nameField = new ListGridField("name", "Name");
		ListGridField toStepField = new ListGridField("toStep", "To Step");
		ListGridField reasonsField = new ListGridField("reasonsField", "Reasons");
		this.addGridSection(name, title, new ListGridField[] { nameField, toStepField, reasonsField }, buttonsBitwise, expanded);
	}

	// Edit transition
	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid, GridRecordEditor editor) {
		/*if (sectionName.equals("transitions")) {
			TransitionEditor transitionEditor = new TransitionEditor(true, stepEditor, this, grid);
			transitionEditor.show();
		}*/
	}
	
	@Override
	public GridRecordEditor getGridRecordEditor(String sectionName, ListGrid grid) {
		/*if (sectionName.equals("transitions")) {
			return new TransitionEditor(false, stepEditor, this, grid);
		}*/
		
		return null;
	}

	// New Transition
	@Override
	public void onGridRecordAdd(String sectionName, ListGrid grid,
			CallbackHandler<ListGridRecord> callback) {
		/*if (sectionName.equals("transitions")) {
			TransitionEditor transitionEditor = new TransitionEditor(false, stepEditor, this, grid);
			transitionEditor.show();
		}*/
	}

	@Override
	public void onGridMultiRecordsEditorEnter(String sectionName, final ListGrid grid,final CallbackHandler<EditorRecord[]> callback) {
		// load all work centers related with step
		if (sectionName.equals("workCenters")) {
			WorkCenterFilter filter = new WorkCenterFilter();
			filter.OR();
			filter.whereStep().isNull();
			MESApplication.getMESControllers().getRoutingController().findAllWorkCenterNames(new CallbackHandler<Set<String>>() {

				@Override
				public void onSuccess(Set<String> workCenterNames) {
					ListGridRecord[] records = grid.getRecords();	
					List<EditorRecord> eRecords = new ArrayList<EditorRecord>();
					List<String> names = new ArrayList<String>();
					
					// work center belong to step
					for (ListGridRecord r: records) {
						WorkCenterRecord wc = (WorkCenterRecord) r;
						String name = wc.getName();
						names.add(name);
						eRecords.add(new EditorRecord(name, true));
					}
					
					// exclude workcenter not belong to step
					if (workCenterNames != null && !workCenterNames.isEmpty()) {
						for (String wcName : workCenterNames) {
							if (!names.contains(wcName)) {
								names.add(wcName);
								eRecords.add(new EditorRecord(wcName, false));
							}
						}
					}
					
					callback.onSuccess(eRecords.toArray(new EditorRecord[eRecords.size()]));	
				}
			});
		}
	}

	
	@Override
	public void onGridMultiRecordsEditorExit(String sectionName, final ListGrid grid, EditorRecord[] records, ConfirmButton button) {
		if (sectionName.equals("workCenters")) {
			if (button == ConfirmButton.OK) {
				// remove records from grid
				ListGridRecord[] recordArr = grid.getRecords();
				for (ListGridRecord r : recordArr) {
					grid.removeData(r);
				}
				
				WorkCenterFilter excludeFilter = new WorkCenterFilter();
				excludeFilter.OR();
				
				WorkCenterFilter includeFilter = new WorkCenterFilter();
				includeFilter.OR();
				
				for (EditorRecord r: records) {
					if (r.isSelected()) {
						includeFilter.whereName().isEqual(r.getValue());
					}
					else {
						excludeFilter.whereName().isEqual(r.getValue());
					}
				}
				
				// add work centers to step
				if (includeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findWorkCenters(includeFilter, new CallbackHandler<Set<WorkCenter>>() {
						@Override
						public void onSuccess(final Set<WorkCenter> workCenters) {
							if (workCenters != null && !workCenters.isEmpty()) {								
								Set<WorkCenter> sortedWCs = new TreeSet<WorkCenter>(new Comparator<WorkCenter>() {
									@Override
									public int compare(WorkCenter o1, WorkCenter o2) {
										return Utils.compareStrings(o1.getName(), o2.getName());
									}									
								});
								
								sortedWCs.addAll(workCenters);
								
								for (WorkCenter wc : sortedWCs) {
									WorkCenterRecord wcr = new WorkCenterRecord(wc);
									grid.getRecordList().add(wcr);
								}
							}						
						}
					});
				}
			}
		}		
	}
	
}
