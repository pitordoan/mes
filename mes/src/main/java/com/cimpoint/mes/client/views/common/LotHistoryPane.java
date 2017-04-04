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
package com.cimpoint.mes.client.views.common;

import java.util.ArrayList;
import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LotHistoryPane extends DetailsPane {
	private LotHistoryGrid lotDetailsGrid;
	
	public LotHistoryPane() {
		lotDetailsGrid = new LotHistoryGrid();
		this.addChild(lotDetailsGrid);
	}
	
	@Override
	public void loadData(Object lot) {
		lotDetailsGrid.loadData((Lot) lot);
	}
		
	private class LotHistoryGrid extends ListGrid {
		
		private final String[][] fields = {
				{"lotNumber", "Lot Name", "AutoFit"},
				{"prodLine", "Prod. Line", "AutoFit"},
				{"workCenter", "Work Center", "AutoFit"},
				{"routing", "Routing", "AutoFit"},
				{"step", "Step", "AutoFit"},
				{"operation", "Operation", "AutoFit"},
				
				{"status", "Status", "AutoFit"},
				{"quantity", "Qty", "AutoFit"},
				{"uom", "UoM", "AutoFit"},
				{"partNumber", "Part No.", "AutoFit"},
				{"partRevision", "Part Rev.", "AutoFit"}
		};
		
		public LotHistoryGrid() {
			setWidth100();
			setHeight100();
			setShowAllRecords(true);
			
			List<ListGridField> fieldList = new ArrayList<ListGridField>();
			for (String[] fieldAttrs: fields) {
				String name = fieldAttrs[0];
				String title = fieldAttrs[1];
				String width = fieldAttrs[2];
				
				ListGridField field = new ListGridField(name, title);
				field.setCanDragResize(true);
				/*if (width.equals("AutoFit")) {
					field.setAutoFitWidth(true);
				}
				else {
					field.setWidth(width);
				}*/
				
				fieldList.add(field);	
				
				//setSort(new SortSpecifier[] { new SortSpecifier(name, SortDirection.ASCENDING) });
			}

			ListGridField[] fieldArr = fieldList.toArray(new ListGridField[fieldList.size()]);
			setFields(fieldArr);
			
			sort(0, SortDirection.ASCENDING);
			
			//setData(new LotRecord[] {lotRecord});
		}
		
		public void loadData(Lot lot) {
			LotRecord lotRecord = new LotRecord();
			lotRecord.loadData(lot);
		}
		
		private class LotRecord extends ListGridRecord {
			
			public void loadData(final Lot lot) {
				if (lot != null) {				
					/*lot.getTransitionHistory(new CallbackHandler<TransitionHistory>() {
						
					});*/
					
					lot.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {
						@Override
						public void onSuccess(TransitionAttributes attrs) {
							setAttribute("lotNumber", lot.getNumber());
							setAttribute("quantity", lot.getQuantity().asString());
							setAttribute("partNumber", lot.getMaterialName());
							setAttribute("partRevision", lot.getMaterialRevision());
							setAttribute("uom", lot.getUnitOfMeasure());
							
							if (attrs != null) {
								setAttribute("prodLine", attrs.getProductionLineName());
								setAttribute("workCenter", attrs.getWorkCenterName());
								setAttribute("routing", attrs.getRoutingName());
								setAttribute("step", attrs.getStepName());
								setAttribute("operation", attrs.getOperationName());
								setAttribute("status", attrs.getStepStatus());						
							}
							
							setData(new ListGridRecord[] {LotRecord.this});
						}				
					});
				}
				else {
					setAttribute("lotNumber", "");
					setAttribute("quantity", "");
					setAttribute("partNumber", "");
					setAttribute("partRevision", "");
					setAttribute("uom", "");
					
					setAttribute("prodLine", "");
					setAttribute("workCenter", "");
					setAttribute("routing", "");
					setAttribute("step", "");
					setAttribute("operation", "");
					setAttribute("status", "");
					
					setData(new ListGridRecord[] {LotRecord.this});
				}
			}
		}
	}
}