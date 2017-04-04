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

import com.cimpoint.mes.client.objects.WorkCenter;


public class WorkCentersProperty extends ModelProperty {

	public WorkCentersProperty(String property) {
		super(ModelProperty.Type.Text, property);
	}
	
	public void setValue(Set<WorkCenter> workCenter) {
		super.setValue(workCenter);
	}
	
	@SuppressWarnings("unchecked")
	public Set<WorkCenter> getValue() {
		return (Set<WorkCenter>) super.getValue();
	}
}
