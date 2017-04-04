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
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.Location;

public class Unit extends ClientObject<EUnit> implements Persistable, Transactable {

	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Unit(EUnit e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Unit)
            return this.getSerialNumber().equals(((Unit) obj).getSerialNumber()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getSerialNumber().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public String getSerialNumber() {
		return entity.getSerialNumber();
	}

	public void setSerialNumber(String serialNumber) {
		entity.setSerialNumber(serialNumber);
	}

	public void getPart(CallbackHandler<Part> callback) {
		String partNumber = entity.getPartNumber();
		String partRevision = entity.getPartRevision();
		MESApplication.getMESControllers().getPartRecipeController().findPartByNumberAndRevision(partNumber, partRevision, callback);
	}

	public void setPart(Part part) throws Exception {
		entity.setPartNumber(part.getName());
		entity.setPartRevision(part.getRevision());
	}

	public void getWorkOrder(CallbackHandler<WorkOrder> callback) {
		MESApplication.getMESControllers().getWorkOrderController().findWorkOrderByNumber(entity.getWorkOrderNumber(), callback);
	}
	
	public String getStatus() throws Exception {
		return entity.getStatus();
	}

	public void setStatus(String status) {
		entity.setStatus(status);
	}

	public String getState() throws Exception {
		return entity.getState();
	}

	public void setState(String state) {
		entity.setState(state);
	}

	public Lot getLot() {
		ELot elot = entity.getLot();
		return  MESTypeConverter.toClientObject(elot, Lot.class);
	}

	public Unit getParentUnit() throws Exception {
		EUnit eunit = entity.getParentUnit();
		return  MESTypeConverter.toClientObject(eunit, Unit.class);
	}

	public Set<Unit> getChildUnits() throws Exception {
		Set<EUnit> eunits = entity.getChildUnits();
		return  MESTypeConverter.toClientObjectSet(eunits, Unit.class);
	}

	public String getCategory() throws Exception {
		return entity.getCategory();
	}

	public MESConstants.Object.UnitType getType() throws Exception {
		return entity.getType();
	}

	public Step getStep() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Operation getOperation() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void startRouting(String routingName) throws Exception {
		// TODO Auto-generated method stub

	}

	public void startRouting(String routingName, String stepName) throws Exception {
		// TODO Auto-generated method stub

	}

	public void start(String stepName, String comment) throws Exception {
		// TODO Auto-generated method stub

	}

	public void complete(String stepName, String transitionName, String comment) throws Exception {
		// TODO Auto-generated method stub

	}

	public String getState(Step atStep) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

	public Routing getRouting() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<PartConsumption> getPartConsumptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<ComponentConsumption> getComponentConsumptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}
	
	public MESConstants.Object.UnitOfMeasure getUoM() throws Exception{
		return this.getLot().toEntity().getUnitOfMeasure();
	}
	
	public Date getLastModifiedTime(){
		return this.entity.getAtrxAttributes().getTrxTime();
	}

	public String getTypeHierarchy() {
		return entity.getTypeHierarchy();
	}

	@Override
	public void joinRouting(String routingName, String stepName, String optWorkCenterName,
			boolean stepFlowEnforcement, String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().joinRouting(entity.getId(), MESConstants.Object.Type.Unit, 
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
