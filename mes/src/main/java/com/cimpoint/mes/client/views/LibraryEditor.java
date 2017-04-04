/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     duy - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;
import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.PropertiesEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;

public class LibraryEditor extends PropertiesEditor {


	private DynamicForm dynamicForm;
	private TextItem txtName;
	private TextItem txtDescription;
	private UploadItem txtUpload;
	
	@Override
	public String getTitle() {
		return "Library Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNewButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		if (!dynamicForm.validate())
			return;
		
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub

	}
	
	private void createView() {
		dynamicForm = new DynamicForm();
		
		txtName = new TextItem("name", "Name");
		txtName.setRequired(true);
		
		txtDescription = new TextItem("description", "Description");
		txtDescription.setWidth(250);
		
		txtUpload = new UploadItem("upload", "Jar File");
		txtUpload.setRequired(true);
		txtUpload.setHeight(35);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { txtName, txtDescription, txtUpload });
		addChild(dynamicForm);
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		// TODO Auto-generated method stub
	}

}
