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
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.UnitFilter;
import com.cimpoint.mes.common.services.UnitService;
import com.cimpoint.mes.common.services.UnitServiceAsync;

public class UnitController extends AppController {

	private UnitServiceAsync unitService;

	public UnitController() {
		unitService = UnitService.Util.getInstance();
	}

	@Override
	public void init(final CallbackHandler<Void> callback) {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void enableCache(boolean cacheable, CallbackHandler<Void> callback) {

	}

	public void isCacheEnabled(CallbackHandler<Boolean> callback) {

	}

	public void getNextUnitNumber(CallbackHandler<String> callback) {

	}

	public void getInitUnitNumber(CallbackHandler<String> callback) {

	}

	public void setInitUnitNumber(String unitNumber, CallbackHandler<Void> callback) {

	}

	public void findUnitByNumber(String unitNumber, CallbackHandler<Unit> callback) {
		unitService.findUnitByNumber(unitNumber,  new MESTypeConverter.EntityToClientObjectCallback<EUnit, Unit>(Unit.class, callback));
	}

	public void findUnits(UnitFilter unitFiler, CallbackHandler<Set<Unit>> callback) {
		unitService.findUnits(unitFiler, new MESTypeConverter.EntityToClientObjectSetCallback<EUnit, Unit>(Unit.class, callback));
	}

	public void findUnitNumbersByLotNumber(String lotNumber, CallbackHandler<Set<String>> callback) {
		unitService.findUnitNumbersByLotNumber(lotNumber, callback);
	}

	public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo,
			CallbackHandler<EUnit> callback) {
		unitService.createUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, trxInfo, callback);
	}

	public void createUnits(String[] unitSerialNumbers, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo,
			CallbackHandler<Set<EUnit>> callback) {
		unitService.createUnits(unitSerialNumbers, workOrderNumber, partNumber, partRevision, trxInfo, callback);
	}

	public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, MESTrxInfo trxInfo,
			CallbackHandler<EUnit> callback) {
		unitService.createUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, lot, trxInfo, callback);
	}

	public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit parentUnit,
			MESTrxInfo trxInfo, CallbackHandler<EUnit> callback) {
		
	}

	public void createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, EUnit parentUnit,
			MESTrxInfo trxInfo, CallbackHandler<EUnit> callback) {
		
	}

	public void saveUnit(EUnit unit, MESTrxInfo trxInfo, CallbackHandler<Void> callback) {
		
	}

	public void changeStatus(EUnit unit, String newStatus, MESTrxInfo trxInfo, CallbackHandler<Void> callback) {
		
	}

	public void changeState(EUnit unit, String newState, MESTrxInfo trxInfo, CallbackHandler<Void> callback) {
		
	}

	public void serializeUnit(String unitSerialNumber, Long id, String materialName, String materialRevision, CallbackHandler<Unit> callback) {
		// TODO Auto-generated method stub
		
	}

	public void serializeUnit(String[] unitSerialNumbers, Long id, String materialName, String materialRevision, CallbackHandler<Set<Unit>> callback) {
		// TODO Auto-generated method stub
		
	}
}
