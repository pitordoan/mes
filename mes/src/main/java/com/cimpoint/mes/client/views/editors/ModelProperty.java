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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.cimpoint.common.Utils;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class ModelProperty {
	public static enum Type {Text, Select, Check, Popup};
	
	private Type type;
	private String property;
	private ModelPropertyEditor editor;
	private FormItem guiComponent;
	private boolean isReadOnly = false;
	private boolean isRequired = false;
	private boolean isVisible = true;

	public ModelProperty(Type type, String property, ModelPropertyEditor editor) {
		this.type = type;
		this.property = property;
		this.editor = editor;
		this.editor.setModelProperty(this);
	}
	
	public ModelProperty(Type type, String property) {
		this.type = type;
		this.property = property;
	}
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return this.guiComponent.getValue();
	}

	public void setValue(Object value) {
		this.guiComponent.setValue(value);
	}

	public ModelPropertyEditor getEditor() {
		return editor;
	}

	public void setEditor(ModelPropertyEditor editor) {
		this.editor = editor;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String getValueAsString() {
		Object value = this.guiComponent.getValue();
		if (value == null) return null;
		return String.valueOf(value);
	}
	
	public void setValueMap(Map<String, String> valuesMap) {
		setValue(null);
		if (valuesMap != null && !valuesMap.isEmpty()) {
			String[] names = Utils.toSortedStringArray(valuesMap.keySet());
			StringBuilder buffer = new StringBuilder();
			for (String name: names) {
				String value = valuesMap.get(name);
				if (value == null) value = "";
				buffer.append(name);
				buffer.append(":");
				buffer.append(value);
				buffer.append(",");
			}
			String values = buffer.toString().substring(0, buffer.length()-1);
			setValue(values);
		}
	}
	
	public Map<String, String> getValueAsMap() {
		Map<String, String> map = null;		
		String values = getValueAsString();
		if (values != null && !values.isEmpty()) {
			map = new HashMap<String, String>();
			String[] attrValuePair = values.split(",");
			if (attrValuePair != null) {
				for (String attrValue: attrValuePair) {
					String[] pair = attrValue.split(":");
					if (pair != null && pair.length == 2) {
						map.put(pair[0].trim(), pair[1].trim());
					}
					else {
						map.put(pair[0].trim(), "");
					}
				}
			}
		}
		
		return map;
	}
	
	public void setValueSet(Set<String> values) {
		setValue(null);
		if (values != null && !values.isEmpty()) {
			String[] names = Utils.toSortedStringArray(values);
			StringBuilder buffer = new StringBuilder();
			for (String name: names) {
				buffer.append(name);
				buffer.append(",");
			}
			String svalues = buffer.toString().substring(0, buffer.length()-1);
			setValue(svalues);
		}
	}
	
	public String[] getValueAsArray() {
		String values = getValueAsString();
		if (values != null && !values.isEmpty()) {
			String[] attrValuePair = values.split(",");
			return attrValuePair;
		}
		
		return new String[] {};
	}

	public void setGUIComponent(FormItem guiComponent) {
		this.guiComponent = guiComponent;		
	}
	
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isReadOnly() {
		return this.isReadOnly;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	public boolean isRequired() {
		return this.isRequired;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void hide() {
		this.isVisible = false;
		
		if (this.guiComponent != null) {
			this.guiComponent.hide();
		}
	}

	public void show() {
		this.isVisible = true;

		if (this.guiComponent != null) {
			this.guiComponent.show();
		}
	}
}
