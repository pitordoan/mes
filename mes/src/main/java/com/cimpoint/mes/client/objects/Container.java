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
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EContainer;

public class Container extends ClientObject<EContainer> implements Named, Persistable, Transactable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Container(EContainer e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Container)
            return this.getName().equals(((Container) obj).getName()); 
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
		// TODO Auto-generated method stub
		return null;
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
	
	/*public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNumber(String number) {
		// TODO Auto-generated method stub
		
	}

	public void save() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void save(TransactionInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Step getStep() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Operation getOperation() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatus() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}

	public String getState() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setState(String state) {
		entity.setState(state);
	}
	
	public Quantity getQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setQuantity(Quantity quantity) {
		// TODO Auto-generated method stub
		
	}

	public Part getPart() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPart(Part part) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public WorkOrder getWorkOrder() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWorkOrder(WorkOrder workOrder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public StateRule getStateRule() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStateRule(StateRule stateRule) {
		// TODO Auto-generated method stub
		
	}

	public void startRouting(String routingName) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void startRouting(String routingName, String stepName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void start(String stepName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void start(String stepName, TransactionInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void complete(String stepName, String transitionName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void complete(String stepName, String transitionName, TransactionInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getState(Step atStep) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransitionPath() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastModifyUserName() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusTimeDecode() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getStatusTime() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStateTimeDecode() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getStateTime() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getComment() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Routing getRouting() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartConsumption> getPartConsumptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComponentConsumption> getComponentConsumptions()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/

}

