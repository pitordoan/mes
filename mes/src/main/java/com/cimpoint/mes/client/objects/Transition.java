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
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.Quantity;

public class Transition extends ClientObject<ETransition> implements Named, Persistable {

	public Transition(ETransition e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Transition)
            return this.getName().equals(((Transition) obj).getName()); 
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
	
	public String getRoutingName() {
		return entity.getRoutingName();
	}

	public Step getFromStep() throws Exception {
		EStep estep = entity.getFromStep();
		return  MESTypeConverter.toClientObject(estep, Step.class);
	}

	public void setFromStep(Step step) {
		EStep estep = step.toEntity();
		entity.setFromStep(estep);		
	}

	public Step getToStep() throws Exception {
		EStep estep = entity.getToStep();
		return  MESTypeConverter.toClientObject(estep, Step.class);
	}

	public void setToStep(Step step) {
		EStep estep = step.toEntity();
		entity.setToStep(estep);	
	}

	public Quantity getTransferQuantity() throws Exception {
		return new Quantity(entity.getTransferQuantity(), entity.getTransferUnitOfMeasure());
	}
	
	//TODO fetch reasons from dictionary
	public String[] getReasons() {
		return new String[] {"OK", "Passed", "Failed", "Proceed", "Rework"};
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveTransition(this, comment, callback);
	}
}

