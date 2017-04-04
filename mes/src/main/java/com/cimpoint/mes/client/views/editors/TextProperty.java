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

public class TextProperty extends ModelProperty {

	public TextProperty(String property) {
		super(ModelProperty.Type.Text, property);
	}
	
	public TextProperty(String property, ModelPropertyEditor propertyEditor) {
		super(ModelProperty.Type.Text, property, propertyEditor);
	}
	
	public String getValue() {
		return super.getValueAsString();
	}
	
	public void setValue(String value) {
		super.setValue(value);
	}
}
