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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UnitServiceAsync {
	public void enableCache(boolean cacheable, AsyncCallback<Void> callback);
    public void isCacheEnabled(AsyncCallback<Boolean> callback);
    
    public void getNextUnitNumber(AsyncCallback<String> callback);
    public void getInitUnitNumber(AsyncCallback<String> callback);
    public void setInitUnitNumber(String unitNumber, AsyncCallback<Void> callback);

    public void findUnitByNumber(String unitNumber, AsyncCallback<EUnit> callback);
    public void findUnits(UnitFilter unitFiler, AsyncCallback<Set<EUnit>> callback);
    public void findUnitNumbersByLotNumber(String lotNumber, AsyncCallback<Set<String>> callback);
    
    public void queueUnit(EUnit lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
   	public void startUnit(EUnit lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
   	public void completeUnit(EUnit lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);	
   	public void pauseUnit(EUnit lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);	
   	
    public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
    public void createUnits(String[] unitSerialNumbers, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo, AsyncCallback<Set<EUnit>> callback);
    public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
    public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit parentUnit, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
    public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, EUnit parentUnit, MESTrxInfo trxInfo, AsyncCallback<EUnit> callback);
    public void saveUnit(EUnit unit, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void changeStatus(EUnit unit, String newStatus, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void changeState(EUnit unit, String newState, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
