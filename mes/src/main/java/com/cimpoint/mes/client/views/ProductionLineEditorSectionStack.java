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
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.filters.WorkCenterFilter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductionLineEditorSectionStack extends EditorSectionStack {

	public ProductionLineEditorSectionStack(String width, String height) {
		super(width, height);
	}

	@Override
	public void onGridMultiRecordsEditorEnter(final String sectionName, final ListGrid grid, final CallbackHandler<EditorRecord[]> callback) {
		if (sectionName.equals("workCenters")) {
			WorkCenterFilter filter = new WorkCenterFilter();
			filter.OR();
			filter.whereProductionLine().isNull();
			MESApplication.getMESControllers().getRoutingController().findWorkCenterNames(filter, new CallbackHandler<Set<String>>() {
				@Override
				public void onSuccess(Set<String> prodLineNames) {
					ListGridRecord[] records = grid.getRecords();	
					List<EditorRecord> eRecords = new ArrayList<EditorRecord>();
					List<String> names = new ArrayList<String>();
					
					for (ListGridRecord r: records) {
						WorkCenterRecord ar = (WorkCenterRecord) r;
						String name = ar.getName();
						names.add(name);
						eRecords.add(new EditorRecord(name, true));
					}
					
					if (prodLineNames != null && !prodLineNames.isEmpty()) {
						for (String prdlName: prodLineNames) {
							if (!names.contains(prdlName)) {
								names.add(prdlName);
								eRecords.add(new EditorRecord(prdlName, false));
							}
						}
					}
										
					callback.onSuccess(eRecords.toArray(new EditorRecord[eRecords.size()]));						
				}				
			});
		}
	}

	@Override
	public void onGridMultiRecordsEditorExit(final String sectionName, final ListGrid grid, final EditorRecord[] records, final ConfirmButton button) {
		if (sectionName.equals("workCenters")) {
			if (button == ConfirmButton.OK) {
				//remove all from grid
				ListGridRecord[] recordArr = grid.getRecords();
				for (ListGridRecord r: recordArr) {
					grid.removeData(r);
				}
				
				WorkCenterFilter excludeFilter = new WorkCenterFilter();
				excludeFilter.OR();		
				
				final WorkCenterFilter includeFilter = new WorkCenterFilter();
				includeFilter.OR();
				
				for (EditorRecord r: records) {
					if (r.isSelected()) {
						includeFilter.whereName().isEqual(r.getValue());
					}
					else {
						excludeFilter.whereName().isEqual(r.getValue());
					}
				}
				
				if (excludeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findWorkCenters(excludeFilter, new CallbackHandler<Set<WorkCenter>>() {
						@Override
						public void onSuccess(final Set<WorkCenter> workCenters) {
							if (workCenters != null && !workCenters.isEmpty()) {
								for (WorkCenter prdLine: workCenters) {
									prdLine.setArea(null);
								}
								
								MESApplication.getMESControllers().getRoutingController().saveWorkCenters(workCenters, "Unset production line from work center", new CallbackHandler<Void>() {
									@Override
									public void onSuccess(Void result) {
										MESApplication.getMESControllers().getRoutingController().clearCache(WorkCenter.class); //clear cache
									}
								}); 
							}						
						}
					});
				}
				
				if (includeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findWorkCenters(includeFilter, new CallbackHandler<Set<WorkCenter>>() {
						@Override
						public void onSuccess(Set<WorkCenter> workCenters) {
							if (workCenters != null && !workCenters.isEmpty()) {
								// add to grid the included ones
								Set<WorkCenter> sortedWCs = new TreeSet<WorkCenter>(new Comparator<WorkCenter>() {
									@Override
									public int compare(WorkCenter o1, WorkCenter o2) {
										return Utils.compareStrings(o1.getName(), o2.getName());
									}									
								});
								
								sortedWCs.addAll(workCenters);
								
								for (WorkCenter workCenter : sortedWCs) {
									WorkCenterRecord r = new WorkCenterRecord(workCenter);
									grid.getRecordList().add(r);
								}
							}
						}
					}); 
				}
			}
		}
	}
		
}
