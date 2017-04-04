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

package com.cimpoint.mes.server.repositories;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("consumptionRepository")
public class ConsumptionRepository extends JpaRepository<Long, EConsumption> {
	
	@Autowired
	private EntityManager entityManager;

	public ConsumptionRepository() {
		super(EConsumption.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	@SuppressWarnings("unchecked")
	public Set<EConsumption> findConsumptions(MESConstants.Consumption.Type consumptionType, 
			MESConstants.Object.Type objectType, Long objectId) throws Exception {
		try {
			Set<EConsumption> consumptions = (HashSet<EConsumption>) getEntityManager()
					.createQuery("select o from EConsumption o where o.consumptionType = ?1 and o.objectType = ?2 and o.objectId = ?3")
					.setParameter(1, consumptionType.toString())
					.setParameter(2, objectType.toString())
					.setParameter(3, objectId)
					.getResultList();
			return consumptions;
		} catch (NoResultException ex) {
			return new HashSet<EConsumption>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<EConsumption> findConsumptions(MESConstants.Object.Type objectType, Long objectId) throws Exception {
		try {
			Set<EConsumption> consumptions = (HashSet<EConsumption>) getEntityManager()
					.createQuery("select o from EConsumption o where o.objectType = ?1 and o.objectId = ?2")
					.setParameter(1, objectType.toString())
					.setParameter(2, objectId)
					.getResultList();
			return consumptions;
		} catch (NoResultException ex) {
			return new HashSet<EConsumption>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EContainer container, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, part, container);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EBatch batch, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, part, batch);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EPart part, ELot lot, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, part, lot);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EUnit unit, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, part, unit);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, EContainer container, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, component, container);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, EBatch batch, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, component, batch);
		this.create(e, trxInfo);
		return e;
	}	
	
	public EConsumption createConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, ELot lot, MESTrxInfo trxInfo) {
		EConsumption e = new EConsumption(consumptionType, component, lot);
		this.create(e, trxInfo);
		return e;
	}	
}
