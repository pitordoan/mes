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
import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.Location;
import com.cimpoint.mes.common.entities.Numbered;
import com.cimpoint.mes.common.entities.Quantity;

public class Batch extends ClientObject<EBatch> implements Numbered, Persistable, Transactable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Batch(EBatch e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Batch)
            return this.getNumber().equals(((Batch) obj).getNumber()); 
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
		return this.entity.getId();
	}

	
	public String getNumber() {
		return this.entity.getName();
	}

	
	public void setNumber(String number) {
		this.entity.setName(number);
	}

	
	public WorkOrder getWorkOrder() throws Exception {
		return null;
	}

	
	public Step getStep() throws Exception {
		return null;
	}

	
	public Operation getOperation() throws Exception {
		return null;
	}

	
	public String getStatus() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getState() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setState(String state) {
		// TODO Auto-generated method stub
		
	}

	
	public void startRouting(String routingName, String comment) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void start(String stepName, String comment) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void complete(String stepName, String transitionName, String comment)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public Location getLocation() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getTransitionPath() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getLastModifyUserName() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getStatusTimeDecode() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Date getStatusTime() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getStateTimeDecode() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Date getStateTime() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getComment() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Quantity getQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setQuantity(Quantity quantity) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Lot> getLots() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Routing getRouting() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<PartConsumption> getPartConsumptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<ComponentConsumption> getComponentConsumptions()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void joinRouting(String routingName, String stepName, String optWorkCenterName,
			boolean stepFlowEnforcement, String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().joinRouting(entity.getId(), MESConstants.Object.Type.Lot, 
				routingName, stepName, optWorkCenterName, stepFlowEnforcement, comment, callback);
	}

	@Override
	public void transact(String stepName, String workCenterName, String stepStatusName, String objectStatus, String optTransitionName, String optReason,
			String optComment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().transact(this, stepName, 
				workCenterName, stepStatusName, objectStatus, optTransitionName, optReason, optComment, callback);					
	}

	@Override
	public void findTransitionAttributes(CallbackHandler<TransitionAttributes> callback) {
		this.routingController.findTransitionAttributes(this, callback);
	}
}

