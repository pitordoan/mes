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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EWorkCenter;

public class Equipment extends ClientObject<EEquipment> implements Named, Persistable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Equipment(EEquipment e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Equipment)
            return this.getName().equals(((Equipment) obj).getName()); 
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

	public String getDescription() {
		return entity.getDescription();
	}
	
	public void setDescription(String description) {
		entity.setDescription(description);
	}
	
	public void getOperation(CallbackHandler<Operation> callback) {
		routingController.findOperationById(entity.getId(), callback);
	}
	
	public void getWorkCenter(CallbackHandler<WorkCenter> callback) {
		MESApplication.getMESControllers().getRoutingController().findWorkCenterById(entity.getWorkCenter().getId(), callback);
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveEquipment(this, comment, callback);
	}

	public void setWorkCenter(WorkCenter workCenter) {
		EWorkCenter ewc = (workCenter != null)? workCenter.toEntity() : null;
		entity.setWorkCenter(ewc);
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
}
