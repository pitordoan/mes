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
package com.cimpoint.mes.client.views.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ValuesPickerDialog extends Dialog { // Window {
	private ListGrid grid;
	protected List<String> data;
	
	private final int BUTTON_WIDTH = 80;

	public ValuesPickerDialog(Canvas parent, String title, int width, int height) {
		this(parent, title, null, width, height);
	}
	
	public ValuesPickerDialog(Canvas parent, String title, String msg, int width, int height) {
		super(parent, title, msg, width, height);
	}
	
	protected void newButton(final Response.Button button, final CallbackHandler<Response> callback) {
		String caption = button.toString().replaceAll("_", " ");
		final IButton btn = new IButton(caption);
		btn.setWidth(BUTTON_WIDTH);
		btn.setShowRollOver(true);
		btn.setID(button.toString());
		final String commitButtons[] = {Response.Button.OK.toString(), Response.Button.Save.toString(), Response.Button.Yes.toString()};
		
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Set<String> selections = null;
				if (Arrays.asList(commitButtons).contains(btn.getID())) {
					selections = getSelections();
					if (!selections.isEmpty()) {
						if (callback != null) {
							Response resp = new Response(button, selections);
							callback.onSuccess(resp);
						}
						destroy(); // close the dialog on commit and having selected value(s)						
					}
				}
				else {
					if (callback != null) {
						Response resp = new Response(button, null);
						callback.onSuccess(resp);
					}
					destroy(); // close the dialog on Cancel
				}
			}
		});

		super.addButton(btn);
	}

	protected Set<String> getSelections() {
		Set<String> selections = new HashSet<String>();
		ListGridRecord[] records = grid.getRecords();
		if (records != null) {
			for (ListGridRecord r: records) {
				if (grid.isSelected(r)) {
					selections.add(r.getAttribute("name"));
				}
			}
		}
		
		return selections;
	}

	public void show(String[] data, Response.Button[] buttons, CallbackHandler<Response> callback) {
		if (data != null && data.length > 0) {
			Arrays.sort(data);
			List<ListGridRecord> records = new ArrayList<ListGridRecord>(); 
			for (String s: data) {
				ListGridRecord r = new ListGridRecord();
				r.setAttribute("name", s);
				records.add(r);
			}
			grid.setData(records.toArray(new ListGridRecord[records.size()]));
		}

		for (Response.Button btn: buttons) {
			newButton(btn, callback);	
		}
		super.show();
	}
	
	public void show(List<String> data, Response.Button[] buttons, CallbackHandler<Response> callback) {
		if (data != null) {
			show(data.toArray(new String[data.size()]), buttons, callback);
		}
		else
			show(new String[]{}, buttons, callback);
	}
	
	@Override
	protected Canvas getContentCanvas() {
		if (grid == null) {
			grid = new ListGrid();
			grid.setMargin(10);	
			grid.setShowAllRecords(true);  
			grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			ListGridField nameField = new ListGridField("name", "Name"); 
			grid.setFields(nameField);
		}
		return grid;
	}
	
	public static class Response {
		public static enum Button {
			OK, Cancel, Save, Do_Not_Save, Yes, No
		};

		private Button button;
		private Set<String> returnData;

		public Response(Button button, Set<String> returnData) {
			this.setButton(button);
			this.returnData = returnData;
		}
		
		public Set<String> getReturnData() {
			return returnData;
		}

		public void setReturnData(Set<String> returnData) {
			this.returnData = returnData;
		}

		public Button getButton() {
			return button;
		}

		public void setButton(Button button) {
			this.button = button;
		}
	}
}
