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
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.LotDetailRecord;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;

public class WOTrackingLotDetailsSectionStack extends CSectionStack {

	private Lot lot;

	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();

	public WOTrackingLotDetailsSectionStack(String width, String height) {
		super(width, height);
		this.addSectionHeaderClickHandler(new SectionHeaderClickHandler() {
			
			@Override
			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
				String title = event.getSection().getTitle();
				if(WOTrackingDetails.CURRENT_TITLE.equals(title)) {
					setGridLotDetailsData(WOTrackingDetails.CURRENT_ID);
				} else if (WOTrackingDetails.HISTORY_TITLE.equals(title)) {
					setGridHistoryData(WOTrackingDetails.HISTORY_ID);
				} else if (WOTrackingDetails.CUSTOME_ATTRIBUTES_TITLE.equals(title)) {
					setCustomAttributeData(WOTrackingDetails.CUSTOME_ATTRIBUTES_ID);
				}
			}
		});
		
	}

	
	public ListGridRecord getSelectedLotRecord(String gridName) {
		return  this.getGridSelectedRecord(gridName);
	}
	
	@Override
	public void onGridMultiRecordsEditorEnter(String sectionName, ListGrid grid, CallbackHandler<EditorRecord[]> callback) {
	}

	@Override
	public void onGridMultiRecordsEditorExit(String sectionName, ListGrid grid,	EditorRecord[] records, ConfirmButton button) {
	}

	@Override
	public void onGridCellEditorEnter(String sectionName, ListGrid grid, int row, int col, Object value) {
	}

	@Override
	public void onGridCellEditorExit(String sectionName, ListGrid grid,
			int row, int col, Object newValue) {
	}

	@Override
	public void onGridRecordAdd(String section, ListGrid grid, CallbackHandler<ListGridRecord> callback) {
	}

	@Override
	public void onGridRecordRemove(ListGrid grid, ListGridRecord selectedRecord, CallbackHandler<MessageButton> callback) {
	}

	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid, GridRecordEditor editor) {
	}

	@Override
	public GridRecordEditor getGridRecordEditor(String sectionName,	ListGrid grid) {
		return null;
	}
	
	public void addLotSection(String gridName, String title) {
		boolean expanded = true;

		final ListGridField gridFieldLotNumber = new ListGridField("lotNumber", "Lot Number");
		final ListGridField gridFieldPrdLine = new ListGridField("prodLine", "Prod. Line");
		final ListGridField gridFieldWorkCenter = new ListGridField("workCenter", "WorkCenter");
		final ListGridField gridFieldRouting = new ListGridField("routing", "Routing");
		final ListGridField gridFieldStep = new ListGridField("step", "Step");
		final ListGridField gridFieldOperation = new ListGridField("operation", "Operation");
		final ListGridField gridFieldStatus = new ListGridField("status", "Status");
		final ListGridField gridFieldQty = new ListGridField("qty", "Qty");
		final ListGridField gridFieldUoM = new ListGridField("uoM", "UoM");
		final ListGridField gridFieldPartNumber = new ListGridField("partNumber", "Part Number");
		final ListGridField gridFieldPartRevision = new ListGridField("partRevision", "Part Rev");

		this.addGridSection(gridName, title, new ListGridField[] { gridFieldLotNumber, gridFieldPrdLine, gridFieldWorkCenter, gridFieldRouting, gridFieldStep, gridFieldOperation,
				gridFieldStatus, gridFieldQty, gridFieldUoM, gridFieldPartNumber, gridFieldPartRevision}, 0, expanded);
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

	public void setGridLotDetailsData(final String gridName) {

		if (lot != null) {
			routingController.getTransitionAttributesForLot(lot.getId(), new CallbackHandler<TransitionAttributes>() {
				@Override
				public void onSuccess(TransitionAttributes trsnAttrs) {
					String lotNumber = MESUtils.Strings.value(lot.getNumber());
					String prdLine = MESUtils.Strings.value(trsnAttrs.getProductionLineName());
					String workCenter = MESUtils.Strings.value(trsnAttrs.getWorkCenterName());
					String routing = MESUtils.Strings.value(trsnAttrs.getRoutingName());
					String stepName = MESUtils.Strings.value(trsnAttrs.getStepName());
					String operationName = MESUtils.Strings.value(trsnAttrs.getOperationName());
					String status = MESUtils.Strings.value(trsnAttrs.getStepStatus());

					String qty = lot.getQuantity().asString();
					String uoM = lot.getQuantity().getUnitOfMeasure().name();
					String partNumber = lot.getMaterialName();
					String partRev = lot.getMaterialRevision();
					//					Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE;
					LotDetailRecord record = new LotDetailRecord(lotNumber, prdLine, workCenter, routing, stepName, 
							operationName, status, qty, uoM, partNumber, partRev);

					WOTrackingLotDetailsSectionStack.super.setGridData(gridName, new LotDetailRecord[] {record});
				}

				@Override
				public void onFailure(Throwable t) {
					String lotNumber = MESUtils.Strings.value(lot.getNumber());
					String prdLine = "";
					String workCenter = "";
					String routing = "";
					String stepName = "";
					String operationName = "";					
					String status = "";
					String qty = lot.getQuantity().asString();
					String uoM = lot.getQuantity().getUnitOfMeasure().name();
					String partNumber = lot.getMaterialName();
					String partRev = lot.getMaterialRevision();

					LotDetailRecord record = new LotDetailRecord(lotNumber, prdLine, workCenter, routing, stepName, 
							operationName, status, qty, uoM, partNumber, partRev);

					WOTrackingLotDetailsSectionStack.super.setGridData(gridName, new LotDetailRecord[] {record});
				}
			});
		}

	}

	public void setGridHistoryData(final String gridName) {
		lot.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {

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
					WOTrackingLotDetailsSectionStack.super.setGridData(gridName, data);
				}
			}
		});

	}

	public void setCustomAttributeData(final String gridName) {
		lot.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {

			@Override
			public void onSuccess(TransitionAttributes result) {
				if(result != null) {
					CustomAttributeRecord[] data = new CustomAttributeRecord[1];
					CustomAttributes attribute = lot.toEntity().getCustomAttributes();
					String customAttribute = "";
					if (attribute != null) {
						customAttribute = lot.toEntity().getCustomAttributes().toString();
					}
					try {
						String strLastModifiedTime = MESUtils.Dates.toString(lot.getLastModifiedTime());
						// TODO: put the value to colum Value
						data[0] = new CustomAttributeRecord(MESUtils.Strings.value(customAttribute), strLastModifiedTime, MESUtils.Strings.value(lot
								.getLastModifyUserName()), "");
						
						WOTrackingLotDetailsSectionStack.super.setGridData(gridName, data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

}
