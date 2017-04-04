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
//--------------------------------------------------------------------------------------
// Copyright (c) 2011 All Right Reserved, http://www.cimpoint.com
//
// This source is subject to the CIMPiont Permissive License.
// Please see the License.txt file for more information.
// All other rights reserved.
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// @author pitor
//--------------------------------------------------------------------------------------

package com.cimpoint.mes.client.objects;

import java.util.Date;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.Quantiative;
import com.cimpoint.mes.common.entities.Quantity;

public class WorkOrderItem extends ClientObject<EWorkOrderItem> implements Persistable, Quantiative  {

	public WorkOrderItem(EWorkOrderItem e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof WorkOrderItem)
            return this.getNumber().equals(((WorkOrderItem) obj).getNumber()); 
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

	public String getNumber() {
		return entity.getNumber();
	}

	public void setNumber(String itemName) {
		entity.setNumber(itemName);
	}

	public void getPart(CallbackHandler<Part> callback) throws Exception {
		String partNumber = entity.getPartNumber();
		String partRevision = entity.getPartRevision();
		MESApplication.getMESControllers().getPartRecipeController().findPartByNumberAndRevision(partNumber, partRevision, callback);
	}

	public void setPart(Part part) {
		entity.setPartNumber(part.getName());
		entity.setPartRevision(part.getRevision());
	}

	public WorkOrder getWorkOrder() {
		return  MESTypeConverter.toClientObject(entity.getWorkOrder(), WorkOrder.class);
	}

	public void setWorkOrder(WorkOrder wo) {
		EWorkOrder ewo = wo.toEntity();
		entity.setWorkOrder(ewo);
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getWorkOrderController().saveItem(this, comment, callback);
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
