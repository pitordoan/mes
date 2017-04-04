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

public class BomItemRecord extends ListGridRecord {
	
	public String getPartName() {
		return getAttributeAsString("partName");
	}
	
	public void setPartName(String name) {
		setAttribute("partName", name);
	}
	
	public String getRevision() {
		return getAttributeAsString("revision");
	}
	
	public void setRevision(String revision) {
		setAttribute("revision", revision);
	}
	
}
