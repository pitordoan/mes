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
package com.cimpoint.mes.client.objects;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HistoryRecord extends ListGridRecord {

	public HistoryRecord(String lastModifiedTime, String user, String prodLine, String routing, String workCenter, String step) {
		setAttribute("last_modified_time", lastModifiedTime);
		setAttribute("user", user);
		setAttribute("prodLine", prodLine);
		setAttribute("routing", routing);
		setAttribute("workCenter", workCenter);
		setAttribute("step", step);
	}

	public String getLastModifiedTime() {
		return getAttribute("lastModifiedTime");
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		setAttribute("last_modified_time", lastModifiedTime);
	}

	public String getUser() {
		return getAttribute("user");
	}

	public void setUser(String user) {
		setAttribute("user", user);
	}

	public String getProdLine() {
		return getAttribute("prodLine");
	}

	public void setProdLine(String prodLine) {
		setAttribute("prodLine", prodLine);
	}

	public String getRouting() {
		return getAttribute("routing");
	}

	public void setRouting(String routing) {
		setAttribute("routing", routing);
	}

	public String getWorkCenter() {
		return getAttribute("workCenter");
	}

	public void setWorkCenter(String workCenter) {
		setAttribute("workCenter", workCenter);
	}

	public String getStep() {
		return getAttribute("step");
	}

	public void setStep(String step) {
		setAttribute("step", step);
	}
}

