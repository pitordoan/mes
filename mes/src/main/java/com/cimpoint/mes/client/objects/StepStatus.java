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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.entities.EStepStatus;

public class StepStatus extends ClientObject<EStepStatus> implements Named, Persistable {
	
	public StepStatus(EStepStatus e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof StepStatus)
            return this.getName().equals(((StepStatus) obj).getName()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getName().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getName();
	}
	
	public void setName(String name) {
		entity.setName(name);
	}
			
	/*public Set<Step> getSteps() {
		return MESTypeConverter.toClientObjectSet(entity.getSteps(), Step.class); 
	}*/
	
	public boolean isStarting() {
		return entity.isStarting();
	}
	
	public boolean isEnding() {
		return entity.isEnding();
	}
	
	public String getNextDefaultStatusName() {
		return entity.getNextDefaultStatusName();
	}
	
	@Override
	public void save(String comment, final CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveStepStatus(this, comment, callback);
	}
}
