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

import com.cimpoint.common.views.CSectionStack;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ItemEditorSectionStack extends EditorSectionStack {

	public ItemEditorSectionStack(String width, String height) {
		super(width, height);
	}

	public void addLocaleValueGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_CELL;
		boolean expanded = false;
		ListGridField localeField = new ListGridField("locale", "Locale");
		localeField.setCanEdit(false);
		ListGridField valueField = new ListGridField("value", "Value");
		this.addGridSection(name, title, new ListGridField[] { localeField, valueField }, buttonsBitwise, expanded);
	}
}
