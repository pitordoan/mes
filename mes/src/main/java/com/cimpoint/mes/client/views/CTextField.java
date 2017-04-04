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

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
/**
 * @author Tai Huynh
 * */
public class CTextField extends DynamicForm{

	private boolean isActive;
	private TextItem txtContent;
	
	public CTextField(){
		super();
	}
	
	public CTextField(String title) {
		super();
		isActive = true;
		createView(title);
	}

	
	public void createView(String title){
		txtContent = new TextItem();
		txtContent.setShowTitle(false);
		txtContent.setTitle(title);
		this.setFields(new FormItem[] {txtContent}); 
	}
	
	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	
	public TextItem getTxtContent() {
		return txtContent;
	}


	public void setTxtContent(TextItem txtContent) {
		this.txtContent = txtContent;
	}


}
