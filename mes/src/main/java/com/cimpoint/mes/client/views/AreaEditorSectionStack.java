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
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.common.filters.ProductionLineFilter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AreaEditorSectionStack extends EditorSectionStack {

	public AreaEditorSectionStack(String width, String height) {
		super(width, height);
	}
	
	@Override
	public void onGridMultiRecordsEditorEnter(final String sectionName, final ListGrid grid, final CallbackHandler<EditorRecord[]> callback) {
		if (sectionName.equals("productionLines")) {
			ProductionLineFilter filter = new ProductionLineFilter();
			filter.OR();
			filter.whereArea().isNull();
			MESApplication.getMESControllers().getRoutingController().findProductionLineNames(filter, new CallbackHandler<Set<String>>() {
				@Override
				public void onSuccess(Set<String> prodLineNames) {
					ListGridRecord[] records = grid.getRecords();	
					List<EditorRecord> eRecords = new ArrayList<EditorRecord>();
					List<String> names = new ArrayList<String>();
					
					for (ListGridRecord r: records) {
						ProductionLineRecord ar = (ProductionLineRecord) r;
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
		if (sectionName.equals("productionLines")) {
			if (button == ConfirmButton.OK) {
				//remove all from grid
				ListGridRecord[] recordArr = grid.getRecords();
				for (ListGridRecord r: recordArr) {
					grid.removeData(r);
				}
				
				ProductionLineFilter excludeFilter = new ProductionLineFilter();
				excludeFilter.OR();		
				
				final ProductionLineFilter includeFilter = new ProductionLineFilter();
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
					MESApplication.getMESControllers().getRoutingController().findProductionLines(excludeFilter, new CallbackHandler<Set<ProductionLine>>() {
						@Override
						public void onSuccess(final Set<ProductionLine> prodLines) {
							if (prodLines != null && !prodLines.isEmpty()) {
								for (ProductionLine prdLine: prodLines) {
									prdLine.setArea(null);
								}
								
								MESApplication.getMESControllers().getRoutingController().saveProductionLines(prodLines, "Unset area from production line", new CallbackHandler<Void>() {
									@Override
									public void onSuccess(Void result) {
										MESApplication.getMESControllers().getRoutingController().clearCache(ProductionLine.class); //clear cache
									}
								}); 
							}						
						}
					});
				}
				
				if (includeFilter.getSearchConstraints().size() > 1) {
					MESApplication.getMESControllers().getRoutingController().findProductionLines(includeFilter, new CallbackHandler<Set<ProductionLine>>() {
						@Override
						public void onSuccess(Set<ProductionLine> prodLines) {
							if (prodLines != null && !prodLines.isEmpty()) {
								// add to grid the included ones
								Set<ProductionLine> sortedPdls = new TreeSet<ProductionLine>(new Comparator<ProductionLine>() {
									@Override
									public int compare(ProductionLine o1, ProductionLine o2) {
										return Utils.compareStrings(o1.getName(), o2.getName());
									}									
								});
								
								sortedPdls.addAll(prodLines);
								
								for (ProductionLine prodLine : sortedPdls) {
									ProductionLineRecord pdlr = new ProductionLineRecord(prodLine);
									grid.getRecordList().add(pdlr);
								}
							}
						}
					}); 
				}
			}
		}
	}
}
