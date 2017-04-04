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

import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.UnitFilter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/unitService")
public interface UnitService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static UnitServiceAsync instance;
		public static UnitServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(UnitService.class);
			}
			return instance;
		}
	}
	
	public void enableCache(boolean cacheable);
    public boolean isCacheEnabled();
    
    public String getNextUnitNumber() throws Exception;
    public String getInitUnitNumber();
    public void setInitUnitNumber(String unitNumber);

    public EUnit findUnitByNumber(String unitNumber) throws Exception;
    public Set<EUnit> findUnits(UnitFilter unitFiler) throws Exception;
    public Set<String> findUnitNumbersByLotNumber(String lotNumber)throws Exception;
    
    public EUnit queueUnit(EUnit lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception;
   	public EUnit startUnit(EUnit lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception;
   	public EUnit completeUnit(EUnit lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception;	
   	public EUnit pauseUnit(EUnit lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception;	
   	
    public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo) throws Exception;
    public Set<EUnit> createUnits(String[] unitSerialNumbers, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo) throws Exception;
    public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, MESTrxInfo trxInfo) throws Exception;
    public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit parentUnit, MESTrxInfo trxInfo) throws Exception;
    public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, EUnit parentUnit, MESTrxInfo trxInfo) throws Exception;
    public void saveUnit(EUnit unit, MESTrxInfo trxInfo) throws Exception;
    public void changeStatus(EUnit unit, String newStatus, MESTrxInfo trxInfo) throws Exception;
    public void changeState(EUnit unit, String newState, MESTrxInfo trxInfo) throws Exception;
}
