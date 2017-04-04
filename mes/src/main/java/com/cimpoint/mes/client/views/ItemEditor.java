/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

public class ItemEditor extends PropertiesEditor {

	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private SelectItem localeGroupItem;
	private boolean viewCreated = false;
	private ListingPanel listingPanel;
	private ItemEditorSectionStack sectionStack;
	
	@Override
	public String getTitle() {
		return "Item Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();			
		callback.onSuccess((Void)null);
	}

	
	private void createView() {
		if (viewCreated) {
			sectionStack.reset();
			return;
		}
		
		this.setWidth(624);
		
		VStack vStack = new VStack();
		vStack.setLeft(30);
		vStack.setHeight100();

		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight("100%");
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setPadding(5);  
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		localeGroupItem = new SelectItem("localeGroup", "Locale Group");
		localeGroupItem.setWidth(200);
		localeGroupItem.setRequired(true);
		
		sectionStack = new ItemEditorSectionStack("500px", "*");
		sectionStack.addLocaleValueGridSection("localeGrid", "Locale");
		CanvasItem canvasItem = sectionStack.toCanvasItem("Localized Value");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, localeGroupItem, canvasItem, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
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
		
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		// TODO Auto-generated method stub
		
	}

}
