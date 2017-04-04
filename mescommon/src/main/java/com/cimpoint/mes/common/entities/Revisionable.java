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
package com.cimpoint.mes.common.entities;

import java.util.Date;

public interface Revisionable {
	public String getRevision();
    public void setRevision(String revision);
    public Date getEffectiveStartDate();
    public void setEffectiveStartDate(Date effectiveStartDate);
    public Date getEffectiveEndDate();
    public void setEffectiveEndDate(Date effectiveEndDate);
    public boolean isCurrent();
    public boolean isEffective();
}
