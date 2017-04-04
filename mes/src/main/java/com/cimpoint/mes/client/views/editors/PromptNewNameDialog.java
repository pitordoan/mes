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

import com.cimpoint.common.CallbackHandler;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class PromptNewNameDialog extends Window {
	private DynamicForm form;
	private HLayout buttonsLayout;
	private TextItem newNameTextItem;
	private final int BUTTON_WIDTH = 80;
	private ModelConfigView modelConfigView;
	private Label msgLabel;
	private VLayout layout;
	private LayoutSpacer layoutSpacer;
	
	public PromptNewNameDialog(String title, ModelConfigView modelConfigView) {
		this.modelConfigView = modelConfigView;
		
		this.setTitle(title);
		this.setShowMaximizeButton(false);
		this.setShowMinimizeButton(false);
		this.setShowCloseButton(false);
		this.setIsModal(true);
		this.setShowModalMask(false);
		this.setAutoSize(true);
		this.centerInPage();
		this.setStyleName("cimtrack-Window");

		layout = new VLayout();
		layout.setWidth(300);
		layout.setBorder("1px solid LightGray");

		msgLabel = new Label();
		msgLabel.setWidth100();
		msgLabel.setAutoHeight();
		msgLabel.setMargin(10);
		msgLabel.setWrap(true);
				
		form = new DynamicForm();
		form.setWidth100();
		form.setColWidths(0, "*");
		
		layoutSpacer = new LayoutSpacer();
		layoutSpacer.setWidth("100%");
		layoutSpacer.setHeight(20);
		layout.addMember(layoutSpacer);

		buttonsLayout = new HLayout();
		buttonsLayout.setWidth("100%");
		buttonsLayout.setAlign(Alignment.CENTER);
		buttonsLayout.setMembersMargin(20);

		layout.addMember(form);
		layout.addMember(buttonsLayout);
		layout.addMember(layoutSpacer);

		this.addItem(layout);
	}

	private void newButton(final Response.Button button, final CallbackHandler<Response> callback) {
		final String caption = button.toString();
		final IButton btn = new IButton(caption);
		btn.setWidth(BUTTON_WIDTH);
		btn.setShowRollOver(true);
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean proceed = true;
				if (callback != null) {
					String newName = null;
					if (button == Response.Button.OK) {
						newName = newNameTextItem.getValueAsString();
						proceed = validateName(newName);
					}
					
					if (proceed) {
						Response resp = new Response(button, newName);
						destroy(); // close the dialog
						callback.onSuccess(resp);
					}
				}
				else {
					destroy(); // close the dialog
				}
			}
		});

		buttonsLayout.addMember(btn);
	}
	
	private boolean validateName(String name) {
		String msg = null;
		
		if (name == null || name.isEmpty()) {
			msg = "Please enter a name";
		}
		else if (modelConfigView.isModelExisted(name)) {
			msg = name + " already exists"; 
		}
		
		if (msg != null) {
			msgLabel.setContents("<span style=\"color: red;\">" + msg + "</span>");
			layout.removeMember(layout.getMember(0));
			layout.addMember(msgLabel, 0);
			layout.addMember(layoutSpacer);
		}
		
		return (msg == null);
	}

	public void show(String prompt, CallbackHandler<Response> callback) {
		newNameTextItem = new TextItem("newName", prompt);
		newNameTextItem.setWrapTitle(false);
		form.setMargin(10);
		form.setCellPadding(3);
		form.setColWidths("*", 100);
		form.setFields(newNameTextItem);
		
		newButton(Response.Button.OK, callback);
		newButton(Response.Button.Cancel, callback);
		this.show();
	}

	public static class Response {
		public static enum Button {
			OK, Cancel
		};

		private Button button;
		private String returnData;

		public Response(Button button, String returnData) {
			this.setButton(button);
			this.returnData = returnData;
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
