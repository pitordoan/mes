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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EConsumption;

public class PartConsumption extends ClientObject<EConsumption> implements Persistable {

	public PartConsumption(EConsumption e) {
		this.entity = e;
	}

	public MESConstants.Consumption.Type getConsumptionType() throws Exception {
		return entity.getConsumptionType();
	}

	public void setConsumptionType(MESConstants.Consumption.Type type) {
		entity.setConsumptionType(type);
	}

	public Date getTime() throws Exception {
		return entity.getTime();
	}

	public Step getStep() throws Exception {
		return MESApplication.getMESControllers().getRoutingController().findStepByRoutingNameAndStepName(entity.getRoutingName(), entity.getStepName());
	}

	public void setStep(Step step) throws Exception {
		entity.setStepName(step.getName());
	}

	public Part getPart() throws Exception {
		return MESApplication.getMESControllers().getPartRecipeController().findPartById(entity.getMaterialId());
	}

	public void setPart(Part part) {
		entity.setMaterialId(part.getId());
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}
}
