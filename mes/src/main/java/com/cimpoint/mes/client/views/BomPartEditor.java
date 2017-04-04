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

import com.cimpoint.common.views.GridRecordEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

public class BomPartEditor extends GridRecordEditor {

	private DynamicForm dynamicForm;
	private SelectItem partItem;
	private SelectItem revisionItem;
	private TextItem quantity;
	private SelectItem unitOfMesureItem;
	
	public BomPartEditor() {
		super("PartEditor", 210, 170);
		
		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		vStack.setBorder("1px solid LightGray");
		
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight(170);
		dynamicForm.setWidth(210);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setLeft(30);
		
		partItem = new SelectItem("part", "Part");
		partItem.setWidth(200);
		partItem.setRequired(true);
		
		revisionItem = new SelectItem("revision", "Revision");
		revisionItem.setWidth(200);
		revisionItem.setRequired(true);

		quantity = new TextItem("quantity", "Quantity");
		quantity.setWidth(200);
		quantity.setRequired(true);
		
		unitOfMesureItem = new SelectItem("unitOfMesure", "UoM");
		unitOfMesureItem.setWidth(200);
		unitOfMesureItem.setRequired(true);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { partItem, revisionItem, quantity, unitOfMesureItem, getDefaultActionButtonsCanvasItem() });
		
		vStack.setLayoutMargin(20);
		vStack.addMember(dynamicForm);
		
		super.addItem(vStack);
	}

	@Override
	public void onOKButtionClicked(ClickEvent event) {
		
	}

	@Override
	public void onCancelButtionClicked(ClickEvent event) {
		this.destroy();
	}

}
