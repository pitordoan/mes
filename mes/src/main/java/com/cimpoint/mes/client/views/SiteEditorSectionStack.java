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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.views.CGrid.ConfirmButton;
import com.cimpoint.common.views.CGrid.EditorRecord;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.common.filters.AreaFilter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SiteEditorSectionStack extends EditorSectionStack {

	public SiteEditorSectionStack(String width, String height) {
		super(width, height);
	}
	
	public Set<Area> getGridSectionDataAsAreaSet(String sectionName) {
		if (sectionName.equals("areas")) {
			ListGridRecord[] records = super.getGridData(sectionName);
			if (records != null) {
				Set<Area> areas = new HashSet<Area>();
				for (ListGridRecord r: records) {
					AreaRecord ar = (AreaRecord) r;
					Area a = ar.getArea();
					if (a != null) {
						areas.add(a);
					}
				}
				return areas;
			}
		}
		
		return null;
	}
	
	@Override
	public void onGridMultiRecordsEditorEnter(final String sectionName, final ListGrid grid, final CallbackHandler<EditorRecord[]> callback) {
		if (sectionName.equals("areas")) {
			AreaFilter filter = new AreaFilter();
			filter.OR();
			filter.whereSite().isNull();
			MESApplication.getMESControllers().getRoutingController().findAreaNames(filter, new CallbackHandler<Set<String>>() {
				@Override
				public void onSuccess(Set<String> areaNames) {
					ListGridRecord[] records = grid.getRecords();	
					List<EditorRecord> eRecords = new ArrayList<EditorRecord>();
					List<String> names = new ArrayList<String>();
					
					for (ListGridRecord r: records) {
						AreaRecord ar = (AreaRecord) r;
						String name = ar.getName();
						names.add(name);
						eRecords.add(new EditorRecord(name, true));
					}
					
					if (areaNames != null && !areaNames.isEmpty()) {
						for (String areaName: areaNames) {
							if (!names.contains(areaName)) {
								names.add(areaName);
								eRecords.add(new EditorRecord(areaName, false));
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
		if (sectionName.equals("areas")) {
			if (button == ConfirmButton.OK) {
				//remove all from grid
				ListGridRecord[] recordArr = grid.getRecords();
				for (ListGridRecord r: recordArr) {
					grid.removeData(r);
				}
				
				AreaFilter excludeFilter = new AreaFilter();
				excludeFilter.OR();		
				
				final AreaFilter includeFilter = new AreaFilter();
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
					MESApplication.getMESControllers().getRoutingController().findAreas(excludeFilter, new CallbackHandler<Set<Area>>() {
						@Override
						public void onSuccess(final Set<Area> areas) {
							if (areas != null && !areas.isEmpty()) {
								for (Area area: areas) {
									area.setSite(null);
								}
								
								MESApplication.getMESControllers().getRoutingController().saveAreas(areas, "Unset site from area", new CallbackHandler<Void>() {
									@Override
									public void onSuccess(Void result) {
										MESApplication.getMESControllers().getRoutingController().clearCache(Area.class); //clear cache
									}
								}); 
							}						
						}
					});
				}
				
				if (includeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findAreas(includeFilter, new CallbackHandler<Set<Area>>() {
						@Override
						public void onSuccess(Set<Area> areas) {
							if (areas != null && !areas.isEmpty()) {
								// add to grid the included ones
								Set<Area> sortedAreas = new TreeSet<Area>(new Comparator<Area>() {
									@Override
									public int compare(Area o1, Area o2) {
										return Utils.compareStrings(o1.getName(), o2.getName());
									}									
								});
								
								sortedAreas.addAll(areas);
								
								for (Area area : sortedAreas) {
									AreaRecord ar = new AreaRecord(area);
									grid.getRecordList().add(ar);
								}
							}
						}
					}); 
				}
			}
		}
	}
		
	/*@Override
	public void onGridCellEditorEnter(String sectionName, final ListGrid grid, final int row, final int col, final Object value) {
		if (sectionName.equals("areas")) {
			MESApplication.getMESControllers().getRoutingController().findAllAreas(new CallbackHandler<Set<Area>>() {
				@Override
				public void onSuccess(Set<Area> areas) {
					if (areas != null) {
						SelectItem nameFieldEditor = new SelectItem(); 
						List<String> areaNameList = new ArrayList<String>();
						for (Area area: areas) {
							if (area.getSite() == null) {
								if (col == 0) {
									areaNameList.add(area.getName());
								}
							}
						}
						
						if (areaNameList.size() > 0) {
							Utils.sortStringList(areaNameList);
							nameFieldEditor.setValueMap(areaNameList.toArray(new String[areaNameList.size()]));
							grid.getField(col).setEditorType(nameFieldEditor);
						}
					}
				}				
			});
		}
	}
	
	@Override
	public void onGridCellEditorExit(String sectionName, final ListGrid grid, final int row, final int col, final Object newValue) {
		if (sectionName.equals("areas")) {
			if (newValue != null) {
				String areaName = (String) newValue;
				MESApplication.getMESControllers().getRoutingController().findAreaByName(areaName, new CallbackHandler<Area>() {
					@Override
					public void onSuccess(Area area) {
						if (area != null) {
							AreaRecord record = (AreaRecord) grid.getRecord(row);
							record.setArea(area);
						}
					}					
				});
			}
		}
	}
		
	@Override
	public void onGridRecordAdd(String sectionName, final ListGrid grid, final CallbackHandler<ListGridRecord> callback) {
		if (sectionName.equals("areas")) {
			MESApplication.getMESControllers().getRoutingController().findAllAreas(new CallbackHandler<Set<Area>>() {
				@Override
				public void onSuccess(Set<Area> areas) {
					AreaRecord record = null;
					
					if (areas != null) {
						RecordList records = grid.getRecordList();
						SelectItem nameFieldEditor = new SelectItem(); 
						List<String> areaNameList = new ArrayList<String>();
						for (Area area: areas) {
							if (area.getSite() == null && records.find("name", area.getName()) == null) {
								areaNameList.add(area.getName());
							}
						}
						
						//set editor for each editable column
						if (areaNameList.size() > 0) {
							Utils.sortStringList(areaNameList);
							nameFieldEditor.setValueMap(areaNameList.toArray(new String[areaNameList.size()]));
							grid.getField(0).setEditorType(nameFieldEditor);
							
							record = new AreaRecord();
						}
					}					
					
					callback.onSuccess(record);
				}				
			});
		}
		else {
			super.onGridRecordAdd(sectionName, grid, callback);
		}
	}*/
	
}
