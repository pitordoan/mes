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

public class LotDetailRecord extends ListGridRecord{

	public LotDetailRecord(String lotNumber, String prodLine, String workCenter, String routing, String step, String operation, String status,
			String qty, String uoM, String partNumber, String partRevision) {
		setAttribute("lotNumber", lotNumber);
		setAttribute("prodLine", prodLine);
		setAttribute("workCenter", workCenter);
		setAttribute("routing", routing);
		setAttribute("step", step);
		setAttribute("operation", operation);
		setAttribute("status", status);
		setAttribute("qty", qty);
		setAttribute("uoM", uoM);
		setAttribute("partNumber", partNumber);
		setAttribute("partRevision", partRevision);
	}

	public String getLotNumber() {
		return getAttribute("lotNumber");
	}

	public void setLotNumber(String lotNumber) {
		setAttribute("lotNumber", lotNumber);
	}

	public String getProdLine() {
		return getAttribute("prodLine");
	}

	public void setProdLine(String prodLine) {
		setAttribute("prodLine", prodLine);
	}

	public String getWorkCenter() {
		return getAttribute("workCenter");
	}

	public void setWorkCenter(String workCenter) {
		setAttribute("workCenter", workCenter);
	}

	public String getRouting() {
		return getAttribute("routing");
	}

	public void setRouting(String routing) {
		setAttribute("routing", routing);
	}

	public String getStep() {
		return getAttribute("step");
	}

	public void setStep(String step) {
		setAttribute("step", step);
	}

	public String getOperation() {
		return getAttribute("operation");
	}

	public void setOperation(String operation) {
		setAttribute("operation", operation);
	}

	public String getStatus() {
		return getAttribute("status");
	}

	public void setStatus(String status) {
		setAttribute("status", status);
	}

	public String getQty() {
		return getAttribute("qty");
	}

	public void setQty(String qty) {
		setAttribute("qty", qty);
	}

	public String getUoMnTime() {
		return getAttribute("uoM");
	}

	public void setUoM(String uoM) {
		setAttribute("uoM", uoM);
	}

	public String getPartNumber() {
		return getAttribute("partNumber");
	}

	public void setPartNumber(String partNumber) {
		setAttribute("partNumber", partNumber);
	}

	public String getPartRevision() {
		return getAttribute("partRevision");
	}

	public void setPartRevision(String partRevision) {
		setAttribute("partRevision", partRevision);
	}
}
