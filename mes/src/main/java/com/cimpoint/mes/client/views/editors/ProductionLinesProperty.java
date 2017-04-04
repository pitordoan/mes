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

import java.util.Set;

import com.cimpoint.mes.client.objects.ProductionLine;

public class ProductionLinesProperty extends ModelProperty {

	public ProductionLinesProperty(String property) {
		super(ModelProperty.Type.Text, property);
	}
	
	public void setValue(Set<ProductionLine> productionLines) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public Set<ProductionLine> getValue() {
		return (Set<ProductionLine>) super.getValue();
	}
}
