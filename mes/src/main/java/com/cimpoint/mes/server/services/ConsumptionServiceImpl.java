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
package com.cimpoint.mes.server.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.ConsumptionService;
import com.cimpoint.mes.server.repositories.ConsumptionRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("consumptionService")
public class ConsumptionServiceImpl extends RemoteServiceServlet implements ConsumptionService {
	private static final long serialVersionUID = -282874221463815962L;

	@Autowired
	private ConsumptionRepository consumptionRepository;

	public Set<EConsumption> findAll(){
		return consumptionRepository.findAll();
	}

	@Override
	public Set<EConsumption> findConsumption(MESConstants.Consumption.Type consumptionType, MESConstants.Object.Type objectType, Long objectId)
			throws Exception {
		return consumptionRepository.findConsumptions(consumptionType, objectType, objectId);
	}

	@Override
	public Set<EConsumption> findConsumption(MESConstants.Object.Type objectType, Long objectId)
			throws Exception {
		return consumptionRepository.findConsumptions(objectType, objectId);
	}

	@Override
	public void producePart(ELot lot, EPart part, MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Produced, part, lot, trxInfo);
	}

	@Override
	public void consumePart(ELot lot, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, part, lot, trxInfo);
	}

	@Override
	public void scrapPart(ELot lot, EPart part, MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, part, lot, trxInfo);
	}
	
	@Override
	public void consumePart(EContainer container, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, part, container, trxInfo);
	}

	@Override
	public void consumePart(EBatch batch, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, part, batch, trxInfo);
	}

	@Override
	public void consumePart(EUnit unit, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, part, unit, trxInfo);
	}

	@Override
	public void scrapPart(EContainer container, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, part, container, trxInfo);
	}

	@Override
	public void scrapPart(EBatch batch, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, part, batch, trxInfo);
	}

	@Override
	public void scrapPart(EUnit unit, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, part, unit, trxInfo);
	}

	@Override
	public void producePart(EContainer container, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Produced, part, container, trxInfo);
	}

	@Override
	public void producePart(EBatch batch, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Produced, part, batch, trxInfo);
	}

	@Override
	public void producePart(EUnit unit, EPart part, MESTrxInfo trxInfo)
			throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Produced, part, unit, trxInfo);
	}

	@Override
	public void consumeComponent(EContainer container, EComponent component,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, component, container, trxInfo);
	}

	@Override
	public void consumeComponent(EComponent component, EBatch batch,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, component, batch, trxInfo);
	}

	@Override
	public void consumeComponent(EComponent component, ELot lot,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Consumed, component, lot, trxInfo);
		
	}

	@Override
	public void scrapComponent(EComponent component, EContainer container,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, component, container, trxInfo);
	}

	@Override
	public void scrapComponent(EComponent component, EBatch batch,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, component, batch, trxInfo);
	}

	@Override
	public void scrapComponent(EComponent component, ELot lot,
			MESTrxInfo trxInfo) throws Exception {
		consumptionRepository.createConsumption(MESConstants.Consumption.Type.Scrapped, component, lot, trxInfo);
	}
}
