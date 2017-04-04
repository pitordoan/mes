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
package com.cimpoint.mes.client.objects;
import java.util.Date;

import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.ETransitionAttributes;

public class TransitionAttributes extends ClientObject<ETransitionAttributes> {

	public TransitionAttributes(ETransitionAttributes e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof TransitionAttributes)
            return this.getObjectId().equals(((TransitionAttributes) obj).getObjectId()) &&
            		(this.getObjectType() == ((TransitionAttributes) obj).getObjectType());
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getObjectId().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}
	
	public Long getObjectId() {
		return entity.getObjectId();
	}
	
	public MESConstants.Object.Type getObjectType() {
		return entity.getObjectType();
	}
	
	public String getStepStatus() {
		return entity.getStepStatus();
	}
	
	public String getObjectStatus() {
		return entity.getObjectStatus();
	}
		
	public String getState() {
		return entity.getState();
	}
	
	public String getStateRuleClassName() {
		return entity.getStateRuleClassName();
	}
	
	public String getSiteName() {
		return entity.getSiteName();
	}
		
	public String getAreaName() {
		return entity.getAreaName();
	}
		
	public String getRoutingName() {
		return entity.getRoutingName();
	}
	
	public String getProductionLineName() {
		return entity.getProductionLineName();
	}
	
	public String getWorkCenterName() {
		return entity.getWorkCenterName();
	}
		
	public String getStepName() {
		return entity.getStepName();
	}
	
	public String getOperationName() {
		return entity.getOperationName();
	}
		
	public String getTransitionPath() {
		return entity.getTransitionPath();
	}
		
	public String getLastModifier() {
		return entity.getLastModifier();
	}
		
	public String getStatusTimeDecoder() {
		return entity.getStatusTimeDecoder();
	}
		
	public Date getStatusTime() {
		return entity.getStatusTime();
	}
	
	public String getStateTimeDecoder() {
		return entity.getStateTimeDecoder();
	}
	
	public Date getStateTime() {
		return entity.getStateTime();
	}
	
	public String getComment() {
		return entity.getComment();
	}
	
	public boolean getStepFlowEnforcement() {
		return entity.getStepFlowEnforcement();
	}
}

