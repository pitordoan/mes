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
package com.cimpoint.mes.client.controllers;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.MESTypeConverter.EntityToClientObjectCallback;
import com.cimpoint.mes.client.objects.Component;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.LotFilter;
import com.cimpoint.mes.common.services.LotService;
import com.cimpoint.mes.common.services.LotServiceAsync;

public class LotController extends AppController {

	private LotServiceAsync lotService;
	
	public LotController() {
		lotService = LotService.Util.getInstance();
	}

	public void findLotNumbersByWorkOrderItemNumber(String workOrderItemNumber, CallbackHandler<Set<String>> callback) {
		lotService.findLotNumbersByWorkOrderItemNumber(workOrderItemNumber, callback);
	}

	public void findLotsByWorkOrderItemNumber(String workOrderItemNumber, CallbackHandler<Set<Lot>> callback) {
		lotService.findLotsByWorkOrderItemNumber(workOrderItemNumber, new  MESTypeConverter.EntityToClientObjectSetCallback<ELot, Lot>(Lot.class, callback));
	}
	
	public void findLotByNumber(String lotNumber, CallbackHandler<Lot> callback) {
		this.lotService.findLotByNumber(lotNumber, new MESTypeConverter.EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	public void findLots(LotFilter lotFilter, CallbackHandler<Set<Lot>> callback) {
		this.lotService.findLots(lotFilter, new  MESTypeConverter.EntityToClientObjectSetCallback<ELot, Lot>(Lot.class, callback));
	}

	public void findSplittedLots(String originalLotNumber, CallbackHandler<Set<Lot>> callback) {
		this.lotService.findSplittedLots(originalLotNumber, new  MESTypeConverter.EntityToClientObjectSetCallback<ELot, Lot>(Lot.class, callback));
	}

	public void getTransitionAttributes(ELot lot, CallbackHandler<ETransitionAttributes> callback){
		lotService.getTransitionAttributes(lot, callback);
	}
	
	public void createDiscreteLot(String lotNumber, String workOrderNumber, Set<Unit> units, String partNumber, String partRevision, 
			CustomAttributes customAttributes, String comment, CallbackHandler<Lot> callback) {
		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EUnit> eunits =  MESTypeConverter.toEntitySet(units);
		this.lotService.createDiscreteLot(lotNumber, workOrderNumber, eunits, partNumber, partRevision, customAttributes, trxInfo,
				new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}
	

	public void createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
			String partNumber, String partRevision, CustomAttributes customAttributes, String comment, CallbackHandler<Lot> callback) {
		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EUnit> eunits =  MESTypeConverter.toEntitySet(units);
		this.lotService.createDiscreteLot(lotNumber, workOrderNumber, workOrderItemNumber, eunits, 
				partNumber, partRevision, customAttributes, trxInfo,
				new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}
	
	public void createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, 
			Set<EUnit> units, String partNumber, String partRevision, String stateRuleClassName, CustomAttributes customAttributes, 
			String comment, CallbackHandler<Lot> callback) {
		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EUnit> eunits =  MESTypeConverter.toEntitySet(units);
		this.lotService.createDiscreteLot(lotNumber, workOrderNumber, workOrderItemNumber, eunits, partNumber, partRevision, 
						stateRuleClassName, customAttributes, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}
	
	public void createDiscreteLot(String originalLotNumber, String splittedLotNumber, 
			String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, String partNumber, String partRevision,  
			String stateRuleClassName, CustomAttributes customAttributes, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EUnit> eunits =  MESTypeConverter.toEntitySet(units);
		this.lotService.createDiscreteLot(originalLotNumber, splittedLotNumber, workOrderNumber, workOrderItemNumber, eunits, partNumber, partRevision,
				stateRuleClassName, customAttributes, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}
	
	public void createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, String componentName, String componentRevision,
			String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.createProcessLot(lotNumber, workOrderNumber, quantity, componentName, componentRevision, trxInfo,
				new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	public void createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, String componentName, String componentRevision,
			String stateRuleClassName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.createProcessLot(lotNumber, workOrderNumber, quantity, componentName, componentRevision, stateRuleClassName, trxInfo,
				new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	public void createProcessLot(String originalLotNumber, String splittedLotNumber, String workOrderNumber, Quantity quantity, String componentName,
			String componentRevision, String stateRuleClassName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.createProcessLot(originalLotNumber, splittedLotNumber, workOrderNumber, quantity, componentName, componentRevision,
				stateRuleClassName, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	@Deprecated
	public void queueLot(Lot lot, String stepName, String workCenterName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.queueLot(lot.toEntity(), stepName, workCenterName, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	@Deprecated
	public void startLot(Lot lot, String stepName, String workCenterName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.startLot(lot.toEntity(), stepName, workCenterName, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class, callback));
	}

	@Deprecated
	public void completeLot(Lot lot, String stepName, String workCenterName, String transitionName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.completeLot(lot.toEntity(), stepName, workCenterName, transitionName, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class,
				callback));
	}

	@Deprecated
	public void pauseLot(Lot lot, String stepName, String workCenterName, String transitionName, String comment, CallbackHandler<Lot> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.pauseLot(lot.toEntity(), stepName, workCenterName, transitionName, trxInfo, new EntityToClientObjectCallback<ELot, Lot>(Lot.class,
				callback));
	}

	public void saveLot(Lot lot, String comment, CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.saveLot(lot.toEntity(), trxInfo, callback);
	}

	@Deprecated
	public void changeLotStatus(Lot lot, String newStatus, String comment, CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.changeLotStatus(lot.toEntity(), newStatus, trxInfo, callback);
	}

	@Deprecated
	public void changeLotState(Lot lot, String newState, String comment, CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.changeLotState(lot.toEntity(), newState, trxInfo, callback);
	}

	public void changeLotOperation(Lot lot, Operation newOperation, String comment, CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.lotService.changeLotOperation(lot.toEntity(), newOperation.toEntity(), trxInfo, callback);
	}

	public void splitLot(Lot elot, Quantity[] splitQuantities, String comment, CallbackHandler<Set<Lot>> callback) {

	}

	public void findPartConsumptions(Long lotId, CallbackHandler<Set<EConsumption>> callback) {
		// TODO Auto-generated method stub

	}

	public void consumePart(Lot lot, Part part) {
		// TODO Auto-generated method stub

	}

	public void scrapPart(Lot lot, Part part) {
		// TODO Auto-generated method stub

	}

	public void findComponentConsumptions(Long id, CallbackHandler<Set<EConsumption>> callback) {
		// TODO Auto-generated method stub

	}

	public void consumeComponent(Lot lot, Component component) {
		// TODO Auto-generated method stub

	}

	public void scrapComponent(Lot lot, Component component) {
		// TODO Auto-generated method stub

	}

	/*public void joinRouting(Lot lot, String routingName, String stepName, String optWorkCenterName, 
			boolean stepFlowEnforcement, String comment, final CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().joinRouting(lot.toEntity().getId(), MESConstants.Object.Type.Lot,
				routingName, stepName, optWorkCenterName, stepFlowEnforcement, comment, callback);
	}*/

	/*public void startRoutingLot(Lot lot, String routingName, String stepName) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/*public void transact(final Lot lot, String stepName, String optWorkCenterName, String stepStatusName, 
			String lotStatus, String optTransitionName, String optReason, String optComment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(optComment);
		this.lotService.transact(lot.toEntity(), stepName, optWorkCenterName, stepStatusName, lotStatus, 
				optTransitionName, optReason, trxInfo, callback);
	}*/
}
