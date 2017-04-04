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
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.controllers.WorkOrderController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.Numbered;
import com.cimpoint.mes.common.entities.Quantiative;
import com.cimpoint.mes.common.entities.Quantity;

public class WorkOrder extends ClientObject<EWorkOrder> implements Numbered, Persistable, Quantiative {

	private WorkOrderController woController = MESApplication.getMESControllers().getWorkOrderController();
	
	public WorkOrder(EWorkOrder e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof WorkOrder)
            return this.getNumber().equals(((WorkOrder) obj).getNumber()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getNumber().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public void setNumber(String woNumber) {
		entity.setNumber(woNumber);		
	}

	public String getNumber() {
		return entity.getNumber();
	}

	public Set<WorkOrderItem> getItems() {
		return  MESTypeConverter.toClientObjectSet(entity.getItems(), WorkOrderItem.class);
	}

	public void saveItem(WorkOrderItem workOrderItem, String comment, CallbackHandler<Void> callback) throws Exception {
		woController.saveItem(workOrderItem, comment, callback);		
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		woController.saveWorkOrder(this, comment, callback);
	}


	public Quantity getQuantity() {
		return new Quantity(entity.getQuantity(), entity.getUnitOfMeasure());
	}

	public void setQuantity(Quantity qty) {
		entity.setQuantity(qty.asString());
	}
	
	public Date getPromisedShipTime() {
		return entity.getPromisedShipTime();
	}
	
	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return entity.getUnitOfMeasure();
	}

	public String getTypeHierarchy() {
		return entity.getTypeHierarchy();
	}
}
