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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.CGrid.ConfirmButton;
import com.cimpoint.common.views.CGrid.EditorRecord;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.ConsumptionController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EConsumption;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WOTrackingConsumptionsStack extends CSectionStack {

	private ConsumptionController consumptionController = MESApplication.getMESControllers().getConsumptionController();
	
	public WOTrackingConsumptionsStack(String width, String height) {
		super(width, height);
		addConsumptionsSection("consumption", "");
	}

	public void addConsumptionsSection(String gridName, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE | CSectionStack.BUTTON_ADD | CSectionStack.BUTTON_REMOVE;
		final ListGridField gridFieldType = new ListGridField("type", "Type");
		final ListGridField gridFieldPart = new ListGridField("part", "Part");
		final ListGridField gridFieldRev = new ListGridField("rev", "Rev");
		final ListGridField gridFieldSerialNumber = new ListGridField("serialNumber", "Serial Number");
		final ListGridField gridFieldQty = new ListGridField("qty", "Qty");
		final ListGridField gridFieldUoM = new ListGridField("uoM", "UoM");

		this.addGridSection(gridName, title, new ListGridField[] { gridFieldType, gridFieldPart, 
				gridFieldRev, gridFieldSerialNumber, gridFieldQty, gridFieldUoM}, buttonsBitwise, true);
	}

	public void loadData(MESConstants.Object.Type objectType, Long objectId) {
		consumptionController.findConsumption(objectType, objectId, new CallbackHandler<Set<EConsumption>>() {
			
			@Override
			public void onSuccess(Set<EConsumption> result) {
				if(result != null) {
					//set data to here
					for (EConsumption eConsumption : result) {
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
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
	
    class ConsumptionDetailRecord extends ListGridRecord{

		public ConsumptionDetailRecord(String type, String part, String rev, String serialNumber, String qty, String uoM) {
			setAttribute("type", type);
			setAttribute("part", part);
			setAttribute("rev", rev);
			setAttribute("serialNumber", serialNumber);
			setAttribute("qty", qty);
			setAttribute("uoM", uoM);
		}

		public String getType() {
			return getAttribute("type");
		}

		public void setType(String type) {
			setAttribute("type", type);
		}

		public String getPart() {
			return getAttribute("part");
		}

		public void setPart(String part) {
			setAttribute("part", part);
		}
		
		public String getRev() {
			return getAttribute("rev");
		}

		public void setRev(String rev) {
			setAttribute("rev", rev);
		}
		
		public String getSerialNumber() {
			return getAttribute("serialNumber");
		}

		public void setSerialNumber(String serialNumber) {
			setAttribute("serialNumber", serialNumber);
		}

		public String getQty() {
			return getAttribute("qty");
		}

		public void setQty(String qty) {
			setAttribute("qty", qty);
		}

		public String getUoMnTime() {
			return getAttribute("uoM");
		}

		public void setUoM(String uoM) {
			setAttribute("uoM", uoM);
		}

	}
}
