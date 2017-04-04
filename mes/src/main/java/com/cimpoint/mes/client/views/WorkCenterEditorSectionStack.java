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
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.common.filters.EquipmentFilter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WorkCenterEditorSectionStack extends EditorSectionStack {

	public WorkCenterEditorSectionStack(String width, String height) {
		super(width, height);
	}
	
	@Override
	public void onGridMultiRecordsEditorEnter(final String sectionName, final ListGrid grid, final CallbackHandler<EditorRecord[]> callback) {
		if (sectionName.equals("equipments")) {
			EquipmentFilter filter = new EquipmentFilter();
			filter.OR();
			filter.whereWorkCenter().isNull();
			MESApplication.getMESControllers().getRoutingController().findEquipmentNames(filter, new CallbackHandler<Set<String>>() {
				@Override
				public void onSuccess(Set<String> eqNames) {
					ListGridRecord[] records = grid.getRecords();	
					List<EditorRecord> eRecords = new ArrayList<EditorRecord>();
					List<String> names = new ArrayList<String>();
					
					for (ListGridRecord r: records) {
						EquipmentRecord sr = (EquipmentRecord) r;
						String name = sr.getName();
						names.add(name);
						eRecords.add(new EditorRecord(name, true));
					}
					
					if (eqNames != null && !eqNames.isEmpty()) {
						for (String eqName: eqNames) {
							if (!names.contains(eqName)) {
								names.add(eqName);
								eRecords.add(new EditorRecord(eqName, false));
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
		if (sectionName.equals("equipments")) {
			if (button == ConfirmButton.OK) {
				//remove all from grid
				ListGridRecord[] recordArr = grid.getRecords();
				for (ListGridRecord r: recordArr) {
					grid.removeData(r);
				}
				
				EquipmentFilter excludeFilter = new EquipmentFilter();
				excludeFilter.OR();		
				
				final EquipmentFilter includeFilter = new EquipmentFilter();
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
					MESApplication.getMESControllers().getRoutingController().findEquipments(excludeFilter, new CallbackHandler<Set<Equipment>>() {
						@Override
						public void onSuccess(final Set<Equipment> eqs) {
							if (eqs != null && !eqs.isEmpty()) {
								for (Equipment eq: eqs) {
									eq.setWorkCenter(null);
								}
								
								MESApplication.getMESControllers().getRoutingController().saveEquipments(eqs, "Unset equipment from work center", new CallbackHandler<Void>() {
									@Override
									public void onSuccess(Void result) {
										MESApplication.getMESControllers().getRoutingController().clearCache(Equipment.class); //clear cache
									}
								}); 
							}						
						}
					});
				}
				
				if (includeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findEquipments(includeFilter, new CallbackHandler<Set<Equipment>>() {
						@Override
						public void onSuccess(Set<Equipment> eqs) {
							if (eqs != null && !eqs.isEmpty()) {
								// add to grid the included ones
								Set<Equipment> sortedEqs = new TreeSet<Equipment>(new Comparator<Equipment>() {
									@Override
									public int compare(Equipment o1, Equipment o2) {
										return Utils.compareStrings(o1.getName(), o2.getName());
									}									
								});
								
								sortedEqs.addAll(eqs);
								
								for (Equipment eq : sortedEqs) {
									EquipmentRecord er = new EquipmentRecord(eq);
									grid.getRecordList().add(er);
								}
							}
						}
					}); 
				}
			}
		}
	}
}
