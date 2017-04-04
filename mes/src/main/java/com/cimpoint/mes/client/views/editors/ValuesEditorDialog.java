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
import java.util.HashMap;
import java.util.Map;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Utils;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class ValuesEditorDialog extends Dialog {
	protected DynamicForm form;
	private final int BUTTON_WIDTH = 80;

	public ValuesEditorDialog(Canvas parent, String title, int width, int height) {
		this(parent, title, null, width, height);
	}
	
	public ValuesEditorDialog(Canvas parent, String title, String msg, int width, int height) {
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
				Map<String, String> valuesMap = null;
				if (Arrays.asList(commitButtons).contains(btn.getID())) {
					valuesMap = getValuesMap();
					if (!valuesMap.isEmpty()) {
						if (callback != null) {
							Response resp = new Response(button, valuesMap);
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

	protected Map<String, String> getValuesMap() {
		Map<String, String> valuesMap = new HashMap<String, String>();
		
		FormItem[] fields = form.getFields();
		if (fields != null) {
			for (FormItem field: fields) {
				TextItem f = (TextItem) field;
				String title = f.getTitle();
				String value = f.getValueAsString();
				valuesMap.put(title, value);
			}
		}
		
		return valuesMap;
	}

	public void show(Map<String, String> data, Response.Button[] buttons, CallbackHandler<Response> callback) {
		if (data != null && data.size() > 0) {
			FormItem[] items = new FormItem[data.size()];
			form.setCellPadding(3);
			
			String[] titles = Utils.toSortedStringArray(data.keySet());
			int i=0;			
			for (String title: titles) {
				String name = title.replaceAll(" ", "");
				String value = data.get(title);
				TextItem ti = new TextItem(name, title);
				ti.setValue(value);
				items[i++] = ti;
			}
			form.setFields(items);
		}

		for (Response.Button btn: buttons) {
			newButton(btn, callback);	
		}
		this.show();
	}

	public static class Response {
		public static enum Button {
			OK, Cancel, Save, Do_Not_Save, Yes, No
		};

		private Button button;
		private Map<String, String> returnData;

		public Response(Button button, Map<String, String> returnData) {
			this.setButton(button);
			this.returnData = returnData;
		}
		
		public Map<String, String> getReturnData() {
			return returnData;
		}

		public void setReturnData(Map<String, String> returnData) {
			this.returnData = returnData;
		}

		public Button getButton() {
			return button;
		}

		public void setButton(Button button) {
			this.button = button;
		}

	}

	@Override
	protected Canvas getContentCanvas() {
		if (form == null) {
			form = new DynamicForm();
			form.setMargin(10);	
		}
		return form;
	}
}
