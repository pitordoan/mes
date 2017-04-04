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
import com.cimpoint.mes.common.entities.EOperation;

//Operation is a location
public class Operation extends ClientObject<EOperation> implements Named, Persistable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Operation(EOperation e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Operation)
            return this.getName().equals(((Operation) obj).getName()); 
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
		
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		routingController.saveOperation(this, comment, callback);
	}

	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
	/*public WorkInstruction getWorkInstruction() throws Exception {
		Long wiId = entity.getWorkInstructionId();
		if (wiId != null) {
			return MESApplication.getAppControllers().getLocationController().findWorkInstructionById(wiId);
		}
		return null;
	}*/
}
