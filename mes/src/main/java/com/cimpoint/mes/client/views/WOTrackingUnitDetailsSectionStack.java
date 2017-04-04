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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CGrid.ConfirmButton;
import com.cimpoint.common.views.CGrid.EditorRecord;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.CustomAttributeRecord;
import com.cimpoint.mes.client.objects.HistoryRecord;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.UnitDetailRecord;
import com.cimpoint.mes.common.MESUtils;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;

public class WOTrackingUnitDetailsSectionStack extends CSectionStack {

	private Unit unit;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();

	public WOTrackingUnitDetailsSectionStack(String width, String height) {
		super(width, height);
		
		this.addSectionHeaderClickHandler(new SectionHeaderClickHandler() {
			
			@Override
			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
				String title = event.getSection().getTitle();
				if(WOTrackingDetails.CURRENT_TITLE.equals(title)) {
					setGridUnitDetailsData(WOTrackingDetails.CURRENT_ID);
				} else if (WOTrackingDetails.HISTORY_TITLE.equals(title)) {
					setGridHistoryData(WOTrackingDetails.HISTORY_ID);
				} else if (WOTrackingDetails.CUSTOME_ATTRIBUTES_TITLE.equals(title)) {
					setCustomAttributeData(WOTrackingDetails.CUSTOME_ATTRIBUTES_ID);
				}
			}
		});
	}

	public void addUnitSection(String gridName, String title) {
		ListGridField gridFieldUnitNumber = new ListGridField("unitNumber", "Unit Number");
		ListGridField gridFieldPrdLine = new ListGridField("prodLine", "Prod. Line");
		ListGridField gridFieldWorkCenter = new ListGridField("workCenter", "WorkCenter");
		ListGridField gridFieldRouting = new ListGridField("routing", "Routing");
		ListGridField gridFieldStep = new ListGridField("step", "Step");
		ListGridField gridFieldOperation = new ListGridField("operation", "Operation");
		ListGridField gridFieldStatus = new ListGridField("status", "Status");
		ListGridField gridFieldPartNumber = new ListGridField("partNumber", "Part Number");
		ListGridField gridFieldPartRevision = new ListGridField("partRevision", "Part Rev");

		this.addGridSection(gridName, title, new ListGridField[] { gridFieldUnitNumber, gridFieldPrdLine, gridFieldWorkCenter, gridFieldRouting, gridFieldStep, gridFieldOperation,
				gridFieldStatus, gridFieldPartNumber, gridFieldPartRevision}, 0, true);
	}

	public void addHistorySection(String gridName, String title) {
		ListGridField gridFieldLasModified = new ListGridField("last_modified_time", "Last Modified Time");
		ListGridField gridFieldUser = new ListGridField("user", "User");
		ListGridField gridFieldPrdLine = new ListGridField("prodLine", "Production Line");
		ListGridField gridFieldRouting = new ListGridField("routing", "Routing");
		ListGridField gridFieldWorkCenter = new ListGridField("workCenter", "Work Center");
		ListGridField gridFieldStep = new ListGridField("step", "Step");

		this.addGridSection(gridName, title, new ListGridField[] { gridFieldLasModified, gridFieldUser, 
				gridFieldPrdLine, gridFieldRouting, gridFieldWorkCenter, gridFieldStep}, 0, false);
	}

	public void addCustomAttributesSection(String gridName, String title) {
		ListGridField gridFiedCustomAttribute = new ListGridField("customAttribute", "Custom Attribute");
		ListGridField gridFieldLastModifiedTime = new ListGridField("last_modified_time", "Last Modified Time");
		ListGridField gridFieldUser = new ListGridField("user", "User");
		ListGridField gridFieldValue = new ListGridField("value", "Value");
		this.addGridSection(gridName, title, new ListGridField[] { gridFiedCustomAttribute, gridFieldUser, gridFieldLastModifiedTime, gridFieldValue}, 0, false);
	}

	public void setGridUnitDetailsData(final String gridName) {

		if (unit != null) {
			routingController.getTransitionAttributesForLot(unit.getId(), new CallbackHandler<TransitionAttributes>() {
				@Override
				public void onSuccess(TransitionAttributes trsnAttrs) {
					String unitNumber = MESUtils.Strings.value(unit.getSerialNumber());
					String prdLine = MESUtils.Strings.value(trsnAttrs.getProductionLineName());
					String workCenter = MESUtils.Strings.value(trsnAttrs.getWorkCenterName());
					String routing = MESUtils.Strings.value(trsnAttrs.getRoutingName());
					String stepName = MESUtils.Strings.value(trsnAttrs.getStepName());
					String operationName = MESUtils.Strings.value(trsnAttrs.getOperationName());
					String status = MESUtils.Strings.value(trsnAttrs.getStepStatus());

					String partNumber = unit.toEntity().getPartNumber();
					String partRev = unit.toEntity().getPartRevision();

					UnitDetailRecord record = new UnitDetailRecord(unitNumber, prdLine, workCenter, routing, stepName, 
							operationName, status, partNumber, partRev);

					
					WOTrackingUnitDetailsSectionStack.super.setGridData(gridName, new UnitDetailRecord[] {record});
				}

				@Override
				public void onFailure(Throwable t) {
					String unitNumber = MESUtils.Strings.value(unit.getSerialNumber());
					String prdLine = "";
					String workCenter = "";
					String routing = "";
					String stepName = "";
					String operationName = "";					
					String status = "";
					String partNumber = unit.toEntity().getPartNumber();
					String partRev = unit.toEntity().getPartRevision();

					UnitDetailRecord record = new UnitDetailRecord(unitNumber, prdLine, workCenter, routing, stepName, 
							operationName, status, partNumber, partRev);

					WOTrackingUnitDetailsSectionStack.super.setGridData(gridName, new UnitDetailRecord[] {record});
				}
			});
		}

	}

	public void setGridHistoryData(final String gridName) {
		unit.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {

			@Override
			public void onSuccess(TransitionAttributes result) {
				if(result != null) {
					String prdLine = "";
					String routing = "";
					String workCenter = "";
					String step = "";
					String strLastModifiedTime = "";
					String strLastModifiedUser = "";


					prdLine = result.getProductionLineName();
					routing = result.getRoutingName();
					workCenter = result.getWorkCenterName();
					step = result.getStepName();
					strLastModifiedTime = MESUtils.Dates.toString(result.getStateTime());
					strLastModifiedUser = result.getLastModifier();
					HistoryRecord[] data = new HistoryRecord[1];
					data[0] = new HistoryRecord(strLastModifiedTime, MESUtils.Strings.value(strLastModifiedUser), MESUtils.Strings.value(prdLine),
							MESUtils.Strings.value(routing), MESUtils.Strings.value(workCenter), MESUtils.Strings.value(step));
					WOTrackingUnitDetailsSectionStack.super.setGridData(gridName, data);
				}
			}
		});

	}

	public void setCustomAttributeData(final String gridName) {
		unit.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {

			@Override
			public void onSuccess(TransitionAttributes result) {
				if(result != null) {
					CustomAttributeRecord[] data = new CustomAttributeRecord[1];
					CustomAttributes attribute = unit.toEntity().getCustomAttributes();
					String customAttribute = "";
					if (attribute != null) {
						customAttribute = unit.toEntity().getCustomAttributes().toString();
					}
					try {
						String strLastModifiedTime = MESUtils.Dates.toString(unit.getLastModifiedTime());
						// TODO: put the value to colum Value
						data[0] = new CustomAttributeRecord(MESUtils.Strings.value(customAttribute), strLastModifiedTime, MESUtils.Strings.value(unit
								.getLastModifyUserName()), "");
						
						WOTrackingUnitDetailsSectionStack.super.setGridData(gridName, data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}


	public ListGridRecord getSelectedUnitRecord(String gridName) {
		return  this.getGridSelectedRecord(gridName);
	}
	
	
	@Override
	public void onGridMultiRecordsEditorEnter(String sectionName,
			ListGrid grid, CallbackHandler<EditorRecord[]> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridMultiRecordsEditorExit(String sectionName, ListGrid grid,
			EditorRecord[] records, ConfirmButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridCellEditorEnter(String sectionName, ListGrid grid,
			int row, int col, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridCellEditorExit(String sectionName, ListGrid grid,
			int row, int col, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridRecordAdd(String section, ListGrid grid,
			CallbackHandler<ListGridRecord> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridRecordRemove(ListGrid grid,
			ListGridRecord selectedRecord,
			CallbackHandler<MessageButton> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid,
			GridRecordEditor editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GridRecordEditor getGridRecordEditor(String sectionName,
			ListGrid grid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
