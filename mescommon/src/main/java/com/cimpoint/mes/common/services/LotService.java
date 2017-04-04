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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/lotService")
public interface LotService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static LotServiceAsync instance;
		public static LotServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(LotService.class);
			}
			return instance;
		}
	}
	
	public void enableCache(boolean cacheable);
    public boolean isCacheEnabled();
    
    public String getNextLotNumber() throws Exception;
    public String getInitLotNumber();
    public void setInitLotNumber(String lotNumber);

    public ELot findLotByNumber(String lotNumber) throws Exception;
    public Set<ELot> findLots(LotFilter lotFiler) throws Exception;
	public Set<ELot> findSplittedLots(String originalLotNumber) throws Exception;
	public Set<String> findLotNumbersByWorkOrderItemNumber(String workOrderItemNumber) throws Exception;
	public Set<ELot> findLotsByWorkOrderItemNumber(String workOrderItemNumber) throws Exception;
	
	public ELot createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
	    		String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
		
    public ELot createDiscreteLot(String lotNumber, String workOrderNumber, Set<EUnit> units, 
    		String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	
    public ELot createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
			String partNumber, String partRevision, String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	
    public ELot createDiscreteLot(String originalLotNumber, String splittedLotNumber, 
			String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, String partNumber, String partRevision,  
			String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	
    public ELot createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, 
			String componentName, String componentRevision, MESTrxInfo trxInfo) throws Exception;
	
    public ELot createProcessLot(String lotNumber, String workOrderNumber, Quantity quantity, 
			String componentName, String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo) throws Exception;
	
    public ELot createProcessLot(String originalLotNumber, String splittedLotNumber, String workOrderNumber, 
			Quantity quantity, String componentName, String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo) throws Exception;
	
    @Deprecated public ELot queueLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ELot startLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ELot completeLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception;	
    @Deprecated public ELot pauseLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception;	
	
	//public void transact(ELot lot, String stepName, String optWorkCenterName, String stepStatusName, String lotStatus, String optTransitionName, String optReason,
	//		MESTrxInfo trxInfo) throws Exception;
	
	public void saveLot(ELot lot, MESTrxInfo trxInfo) throws Exception;
	@Deprecated public void changeLotStatus(ELot lot, String newStatus, MESTrxInfo trxInfo) throws Exception;
	@Deprecated public void changeLotState(ELot lot, String newState, MESTrxInfo trxInfo) throws Exception;
    public void changeLotOperation(ELot lot, EOperation newOperation, MESTrxInfo trxInfo) throws Exception;
	public Set<ELot> splitLot(ELot elot, Set<Quantity> splitQuantities, MESTrxInfo trxInfo) throws Exception;
	
	public ETransitionAttributes getTransitionAttributes(ELot lot)throws Exception;
	
	//public void joinRouting(ELot entity, String routingName, String stepName, boolean stepFlowEnforcement, MESTrxInfo trxInfo);
}
