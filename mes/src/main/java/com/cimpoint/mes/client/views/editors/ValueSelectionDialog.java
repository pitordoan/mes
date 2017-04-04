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

import java.util.Arrays;
import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

public class ValueSelectionDialog extends Dialog { // Window {
	private DynamicForm contentForm;
	private SelectItem si;
	protected List<String> data;
	private String valueTitle;
	
	private final int BUTTON_WIDTH = 80;

	public ValueSelectionDialog(Canvas parent, String dlgTitle, String valueTitle, int width, int height) {
		this(parent, dlgTitle, valueTitle, null, width, height);
		this.valueTitle = valueTitle;
	}
	
	public ValueSelectionDialog(Canvas parent, String dlgTitle, String valueTitle, String msg, int width, int height) {
		super(parent, dlgTitle, msg, width, height);
		this.valueTitle = valueTitle;
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
				if (Arrays.asList(commitButtons).contains(btn.getID())) {
					String selection = si.getValueAsString();
					if (selection != null) {
						if (callback != null) {
							Response resp = new Response(button, selection);
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

	public void show(String[] data, Response.Button[] buttons, CallbackHandler<Response> callback) {
		if (data != null && data.length > 0) {
			si.setValueMap(data);
		}

		for (Response.Button btn: buttons) {
			newButton(btn, callback);	
		}
		
		si.setName("'" + this.valueTitle + "'");
		si.setTitle(this.valueTitle);
		
		super.show();
	}
	
	public void show(List<String> data, Response.Button[] buttons, CallbackHandler<Response> callback) {
		si.setName("'" + this.valueTitle + "'");
		si.setTitle(this.valueTitle);
		
		if (data != null) {
			show(data.toArray(new String[data.size()]), buttons, callback);
		}
		else
			show(new String[]{}, buttons, callback);
	}
	
	@Override
	protected Canvas getContentCanvas() {
		if (contentForm == null) {
			contentForm = new DynamicForm();
			contentForm.setMargin(10);
			si = new SelectItem();
			si.setWidth("100%");
			contentForm.setFields(new FormItem[] {si});
		}
		return contentForm;
	}
	
	public static class Response {
		public static enum Button {
			OK, Cancel, Save, Do_Not_Save, Yes, No
		};

		private Button button;
		private String returnData;

		public Response(Button button, String selection) {
			this.setButton(button);
			this.returnData = selection;
		}
		
		public String getReturnData() {
			return returnData;
		}

		public void setReturnData(String returnData) {
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
