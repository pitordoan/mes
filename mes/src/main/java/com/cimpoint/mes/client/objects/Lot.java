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
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.controllers.PartRecipeController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.Location;
import com.cimpoint.mes.common.entities.Numbered;
import com.cimpoint.mes.common.entities.Quantiative;
import com.cimpoint.mes.common.entities.Quantity;

public class Lot extends ClientObject<ELot> implements Numbered, Persistable, Quantiative, Transactable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private PartRecipeController partRecipeController = MESApplication.getMESControllers().getPartRecipeController();
	private WorkOrder workOrder;
	
	public Lot(ELot e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Lot)
            return this.getNumber().equals(((Lot) obj).getNumber()); 
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

	public void setNumber(String number) {
		entity.setNumber(number);
	}

	/*public void getDiscretePart(final CallbackHandler<Part> callback) {
		String partNumber = entity.getMaterialName();
		String partRev = entity.getMaterialRevision();
		if (partNumber != null && partRev != null) {
			partRecipeController.findPartByNumberAndRevision(partNumber, partRev, callback);
		}
	}

	public void setDiscretePart(Part part) throws Exception {
		entity.setMaterialName(part.getNumber());
		entity.setMaterialRevision(part.getRevision());
	}*/
	
	public String getMaterialName() {
		return entity.getMaterialName();
	}
	
	public String getMaterialRevision() {
		return entity.getMaterialRevision();
	}
	
	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return entity.getUnitOfMeasure();
	}
	
	public void getWorkOrder(final CallbackHandler<WorkOrder> callback) {
		if (this.workOrder == null) {
			MESApplication.getMESControllers().getWorkOrderController()
			.findWorkOrderByNumber(entity.getWorkOrderNumber(), new CallbackHandler<WorkOrder>() {
				@Override
				public void onSuccess(WorkOrder wo) {
					Lot.this.workOrder = wo;
					callback.onSuccess(wo);
				}				
			});
		}
	}
	
	public String getWorkOrderItemNumber() {
		return entity.getWorkOrderItemNumber();
	}
	
	public void getWorkOrderItem(final CallbackHandler<WorkOrderItem> callback) {
		final String woiNumber = entity.getWorkOrderItemNumber();
		if (woiNumber == null || woiNumber.isEmpty()) callback.onSuccess(null);
		
		getWorkOrder(new CallbackHandler<WorkOrder>() {
			@Override
			public void onSuccess(WorkOrder wo) {
				WorkOrderItem foundWOI = null;
				if (wo != null) {
					Set<WorkOrderItem> wois = wo.getItems();
					if (wois != null) {
						for (WorkOrderItem woi: wois) {
							if (woi.getNumber().equals(woiNumber)) {
								foundWOI = woi;
								break;
							}
						}
					}
				}
				
				callback.onSuccess(foundWOI);
			}			
		});
	}

	public Quantity getQuantity() {
		return new Quantity(entity.getQuantity(), entity.getUnitOfMeasure());
	}

	public void setQuantity(Quantity qty) {
		entity.setQuantity(qty.asString());
	}

	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getLotController().saveLot(this, comment, callback);
	}

	@Deprecated
	public void start(String stepName, String workCenterName, String comment, CallbackHandler<Lot> callback) {
		MESApplication.getMESControllers().getLotController().startLot(this, stepName, workCenterName, comment, callback);
	}

	@Deprecated
	public void complete(String stepName, String workCenterName, String transitionName, String comment, CallbackHandler<Lot> callback) throws Exception {
		MESApplication.getMESControllers().getLotController().completeLot(this, stepName, workCenterName, transitionName, comment, callback);
	}

	public void split(Quantity[] splitQuantities, String comment, CallbackHandler<Set<Lot>> callback) {
		MESApplication.getMESControllers().getLotController().splitLot(this, splitQuantities, comment, callback);
	}

	public MESConstants.Object.LotType getType() throws Exception {
		return entity.getType();
	}

	public Component getProcessComponent() throws Exception {
		return  MESTypeConverter.toClientObject(entity.getProcessComponent(), Component.class);
	}

	public void setProcessComponent(Component component) throws Exception {
		entity.setMaterialName(component.getName());
		entity.setMaterialRevision(component.getRevision());
	}

	public Set<Unit> getUnits() throws Exception {
		Set<EUnit> eunits = entity.getUnits();
		return  MESTypeConverter.toClientObjectSet(eunits, Unit.class);
	}

	public void getSplittedLots(CallbackHandler<Set<Lot>> callback) {
		MESApplication.getMESControllers().getLotController().findSplittedLots(this.getNumber(), callback);
	}

	public CustomAttributes getCustomAttributes() throws Exception {
		return entity.getCustomAttributes();
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

	public Date getCreationTime() throws Exception {
		// TODO Auto-generated method stub
		return ((ELot)entity).getCreatedTime();
	}

	public Date getLastModifiedTime() throws Exception {
		// TODO Auto-generated method stub
		return ((ELot)entity).getFinishedTime();
	}

	public String getComment() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void serializeUnit(String unitSerialNumber, CallbackHandler<Unit> callback) throws Exception {
		MESApplication.getMESControllers().getUnitController().serializeUnit(unitSerialNumber, 
				this.getId(), this.getMaterialName(), this.getMaterialRevision(), callback);
	}

	public void serializeUnits(String[] unitSerialNumbers, CallbackHandler<Set<Unit>> callback) throws Exception {
		MESApplication.getMESControllers().getUnitController().serializeUnit(unitSerialNumbers, this.getId(), 
				this.getMaterialName(), this.getMaterialRevision(), callback);
	}

	public void getPartConsumptions(CallbackHandler<Set<EConsumption>> callback) throws Exception {
		MESApplication.getMESControllers().getLotController().findPartConsumptions(this.getId(), callback);
	}

	public void consumePart(Part part) throws Exception {
		MESApplication.getMESControllers().getLotController().consumePart(this, part);
	}

	public void scrapPart(Part part) throws Exception {
		MESApplication.getMESControllers().getLotController().scrapPart(this, part);
	}

	public void getComponentConsumptions(CallbackHandler<Set<EConsumption>> callback) throws Exception {
		MESApplication.getMESControllers().getLotController().findComponentConsumptions(this.getId(), callback);
	}

	public void consumeComponent(Component component) throws Exception {
		MESApplication.getMESControllers().getLotController().consumeComponent(this, component);
	}

	public void scrapComponent(Component component) throws Exception {
		MESApplication.getMESControllers().getLotController().scrapComponent(this, component);
	}

	public Set<Lot> getSplittedLots(){
		return MESTypeConverter.toClientObjectSet(entity.getSplittedLots(), Lot.class);
	}

	public String getTypeHierarchy() {
		return entity.getTypeHierarchy();
	}
	
	@Override
	public void joinRouting(String routingName, String stepName, String optWorkCenterName,
			boolean stepFlowEnforcement, String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().joinRouting(entity.getId(), MESConstants.Object.Type.Lot, 
				routingName, stepName, optWorkCenterName, stepFlowEnforcement, comment, callback);
	}

	@Override
	public void transact(String stepName, String workCenterName, String stepStatusName, String lotStatus, String optTransitionName, String optReason,
			String optComment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().transact(this, stepName, 
				workCenterName, stepStatusName, lotStatus, optTransitionName, optReason, optComment, callback);					
	}
	
	@Override
	public void findTransitionAttributes(CallbackHandler<TransitionAttributes> callback) {
		this.routingController.findTransitionAttributes(this, callback);
	}
}
