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

import java.util.Iterator;
import java.util.Map;

import com.cimpoint.common.entities.CustomAttributes;

public class CustomAttributesProperty extends ModelProperty {

	public CustomAttributesProperty(String property, ModelPropertyEditor propertyEditor) {
		super(ModelProperty.Type.Popup, property, propertyEditor);
		setReadOnly(true);
	}
		
	public CustomAttributes getCustomAttributes() {
		Map<String, String> valuesMap = this.getValueAsMap();
		if (valuesMap == null) return null;
		CustomAttributes ca = new CustomAttributes();
		Iterator<String> it = valuesMap.keySet().iterator();
		while (it.hasNext()) {
			String name = it.next();
			String value = valuesMap.get(name);
			ca.setAttribute(name, value);
		}
		
		return ca;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		StringBuilder buffer = new StringBuilder();
		for (int i=1; i<=10; i++) {
			String attr = "Attribute" + i;
			buffer.append(attr);
			buffer.append(":");			
			String value = (customAttributes != null)? customAttributes.getAttribute(attr) : "";
			if (value == null) value = "";
			buffer.append(value);
			buffer.append(",");
		}
		
		String values = buffer.toString().substring(0, buffer.length()-1);
		super.setValue(values);
	}

}
