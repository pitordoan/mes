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
package com.cimpoint.mes.common.services;

import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.LotFilter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LotServiceAsync {
	public void enableCache(boolean cacheable, AsyncCallback<Void> callback);
    public void isCacheEnabled(AsyncCallback<Boolean> callback);
    
    public void getNextLotNumber(AsyncCallback<String> callback);
    public void getInitLotNumber(AsyncCallback<String> callback);
    public void setInitLotNumber(String lotNumber, AsyncCallback<Void> callback);

    public void findLotByNumber(String lotNumber, AsyncCallback<ELot> callback);
    public void findLots(LotFilter lotFiler, AsyncCallback<Set<ELot>> callback);
	public void findSplittedLots(String originalLotNumber, AsyncCallback<Set<ELot>> callback);
	public void findLotNumbersByWorkOrderItemNumber(String workOrderItemNumber, AsyncCallback<Set<String>> callback);
	public void findLotsByWorkOrderItemNumber(String workOrderItemNumber, AsyncCallback<Set<ELot>> callback);
	
	public void createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
	    		String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
		
    public void createDiscreteLot(String lotNumber, String workOrderNumber, Set<EUnit> units, 
    		String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    public void createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
			String partNumber, String partRevision, String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    public void createDiscreteLot(String originalLotNumber, String splittedLotNumber, 
			String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, String partNumber, String partRevision,  
			String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    public void createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, 
			String componentName, String componentRevision, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    public void createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, 
			String componentName, String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    public void createProcessLot(String originalLotNumber, String splittedLotNumber, String workOrderNumber, 
			Quantity quantity, String componentName, String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
	
    @Deprecated public void queueLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
    @Deprecated public void startLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);
    @Deprecated public void completeLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);	
    @Deprecated public void pauseLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo, AsyncCallback<ELot> callback);	
	
	//public void transact(ELot lot, String stepName, String optWorkCenterName, String stepStatusName, String lotStatus, String optTransitionName, String optReason,
	//		MESTrxInfo trxInfo) throws Exception;
	
	public void saveLot(ELot lot, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	@Deprecated public void changeLotStatus(ELot lot, String newStatus, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	@Deprecated public void changeLotState(ELot lot, String newState, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void changeLotOperation(ELot lot, EOperation newOperation, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void splitLot(ELot elot, Set<Quantity> splitQuantities, MESTrxInfo trxInfo, AsyncCallback<Set<ELot>> callback);
	
	public void getTransitionAttributes(ELot lot, AsyncCallback<ETransitionAttributes> callback);
	
	//public void joinRouting(ELot entity, String routingName, String stepName, boolean stepFlowEnforcement, MESTrxInfo trxInfo);
}
