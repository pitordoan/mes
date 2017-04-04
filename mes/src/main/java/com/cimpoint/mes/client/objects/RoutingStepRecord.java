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
package com.cimpoint.mes.client.objects;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RoutingStepRecord extends ListGridRecord {
	
	public RoutingStepRecord() {}
	
	public String getToStep() {
		return getAttribute("to");
	}
	
	public void setToStep(String name) {
		setAttribute("to", name);
	}
	
	public void setFromStep(String name) {
		setAttribute("from", name);
	}

	public String getFromStep() {
		return getAttribute("from");
	}
	
	public String getTransition() {
		return getAttribute("transition");
	}
	
	public void setTransition(String name) {
		setAttribute("transition", name);
	}
	
	public String getReason() {
		return getAttribute("reasons");
	}
	
	public void setReasons(String reasons) {
		setAttribute("reasons", reasons);
	}
}
