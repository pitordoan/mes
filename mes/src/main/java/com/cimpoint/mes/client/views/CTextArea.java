package com.cimpoint.mes.client.views;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

public class CTextArea extends DynamicForm{

	private boolean isActive;
	private TextAreaItem txtAreaContent;
	
	public CTextArea(String title) {
		super();
		isActive = true;
		createView(title);
	}
	
	public void createView(String title){
		txtAreaContent = new TextAreaItem();
		txtAreaContent.setShowTitle(false);
		txtAreaContent.setTitle(title);
		this.setFields(new FormItem[] {txtAreaContent}); 
	}
	
	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public TextAreaItem getTxtAreaContent() {
		return txtAreaContent;
	}


	public void setTxtAreaContent(TextAreaItem txtAreaContent) {
		this.txtAreaContent = txtAreaContent;
	}


}
