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


import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.common.entities.EMfgBomItem;

public class MfgBomItem extends ClientObject<EMfgBomItem> implements Named, Persistable {

	public MfgBomItem(EMfgBomItem e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof MfgBomItem)
            return this.getName().equals(((MfgBomItem) obj).getName()); 
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
	
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public Long getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public Long getContainerPartId() {
		return entity.getContainerPartId();
	}
	
	public Long getPartId() {
		return entity.getPartId();
	}
	
	public String getPartRevision() {
		return entity.getPartRevision();
	}
	
	public Long getConsumptionRoutingId() {
		return entity.getConsumptionRoutingId();
	}

	public String getConsumptionRoutingRevision() {
		return entity.getConsumptionRoutingRevision();
	}
	
	public Long getConsumptionStepId() {
		return entity.getConsumptionStepId();
	}
	
	public String getConsumptionQty() {
		return entity.getConsumptionQuantity();
	}
	
	public String getConsumptionUoM() {
		return entity.getConsumptionUnitOfMeasure();
	}
	
	public Long getProduceRoutingId() {
		return entity.getProduceRoutingId();
	}
	
	public String getProduceRoutingRev() {
		return entity.getProduceRoutingRevision();
	}
	
	public Long getProduceStepId() {
		return entity.getProduceStepId();
	}
	
	public String getProduceQty() {
		return entity.getProduceQuantity();
	}
	
	public String getProduceUoM() {
		return entity.getProduceUnitOfMeasure();
	}
	
}
