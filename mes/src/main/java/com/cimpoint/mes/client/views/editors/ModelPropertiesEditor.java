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
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ModelPropertiesEditor extends VLayout {
	private DynamicForm form;
	private ModelProperty[] props;
	
	public ModelPropertiesEditor(final ModelEditor modelEditor, String width) {
		this.setWidth(width);
		this.setStyleName("cimtrack-Window");
		this.setBorder("1px solid LightGray");

		form = new DynamicForm();
		form.setWidth100();
		form.setCellPadding(3);
		form.setWrapItemTitles(false);
		form.setPadding(5);

		form.addItemChangedHandler(new ItemChangedHandler() {
			@Override
			public void onItemChanged(ItemChangedEvent event) {
				modelEditor.setEditing(true);
			}
		});

		this.addMember(form);
	}
	
	public void setProperties(ModelProperty... props) {
		this.props = props;
		
		List<FormItem> fields = new ArrayList<FormItem>();
		for (final ModelProperty prop : props) {
			FormItem item = null;
			String name = prop.getProperty().replaceAll(" ", "");
			String title = prop.getProperty();
				
			if (prop.getType() == ModelProperty.Type.Text) {
				item = new TextItem(name, title);
			} else if (prop.getType() == ModelProperty.Type.Select) {
				item = new SelectItem(name, title);
			} else if (prop.getType() == ModelProperty.Type.Check) {
				item = new CheckboxItem(name, title);
			} else if (prop.getType() == ModelProperty.Type.Popup) {
				item = new TextItem(name, title);
			}
			
			if (prop.isReadOnly()) {
				item.addKeyPressHandler(new KeyPressHandler() {
					@Override
					public void onKeyPress(KeyPressEvent event) {
						event.cancel(); // disable editing
					}
				});	
			}
			
			if (prop.isRequired()) {
				item.setRequired(true);
			}
			
			if (prop.isVisible()) {
				item.setVisible(true);
			}
			else {
				item.setVisible(false);
			}
			
			if (prop.getEditor() != null) {
				FormItemIcon formItemIcon = new FormItemIcon();  
	            item.setIcons(formItemIcon);  
	            final FormItem fItem = item;
	            fItem.addIconClickHandler(new IconClickHandler() {  
	                public void onIconClick(IconClickEvent event) {  
	                    ModelPropertyEditor editor = prop.getEditor();
	                    editor.show();
	                }  
	            });  
			}
			
			prop.setGUIComponent(item);
			fields.add(item);
		}

		form.setFields(fields.toArray(new FormItem[fields.size()]));
	}
	
	public boolean validateProperties() {
		return form.validate();
	}
			
	public ModelProperty getProperty(String name) {
		if (this.props != null) {
			for (ModelProperty p: this.props) {
				if (p.getProperty().equals(name)) {
					return p;
				}
			}
		}
		throw new RuntimeException("Property name not found: " + name);
	}
}
