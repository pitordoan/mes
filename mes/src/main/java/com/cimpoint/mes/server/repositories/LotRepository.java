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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.LotFilter;

@Repository("lotRepository")
public class LotRepository extends JpaRepository<Long, ELot> {
	private String initLotNumber = "1";

	@Autowired
	private EntityManager entityManager;
	
	public LotRepository() {
		super(ELot.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public ELot createDiscreteLot(String originalLotNumber, String lotNumber, String state, String workOrderNumber, String workOrderItemNumber,
			Set<EUnit> units, String partNumber, String partRevision, String stateRuleClassName, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		ELot lot = null;
		if(workOrderItemNumber == null) {
			lot = new ELot(MESConstants.Object.LotType.Discrete, originalLotNumber, 
					lotNumber, workOrderNumber, units, partNumber, partRevision, customAttributes);
		} else {
			lot = new ELot(MESConstants.Object.LotType.Discrete, originalLotNumber, 
					lotNumber, workOrderNumber, workOrderItemNumber, units, partNumber, partRevision, customAttributes);
		}
		
		/*ETransitionAttributes trsnAttrs = newTransitionAttributes(MESConstants.Object.Type.Lot, trxInfo);
		trsnAttrs.setStatus("Created");
		trsnAttrs.setState(state);
		trsnAttrs.setStateRuleClassName(stateRuleClassName);*/
		this.create(lot, trxInfo);
		
		return lot;
	}
		
	@Transactional
	public ELot createProcessLot(String originalNumber, String lotNumber, String state, String workOrderNumber, 
			Quantity amount, String componentName, String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo) throws Exception {
		ELot lot = new ELot(MESConstants.Object.LotType.Process, originalNumber, lotNumber, workOrderNumber, amount);

		/*ETransitionAttributes trsnAttrs = newTransitionAttributes(MESConstants.Object.Type.Lot, trxInfo);
		trsnAttrs.setStatus("Created");
		trsnAttrs.setState(state);
		trsnAttrs.setStateRuleClassName(stateRuleClassName);*/
		this.create(lot, trxInfo);
		
		return lot;
	}
	
	public String getNextLotNumber() throws Exception {
		Query qry = getEntityManager().createQuery("select o from ELot o order by o.number desc").setMaxResults(1);
		try {
			ELot lot = (ELot) qry.getSingleResult();
			return String.valueOf(Integer.parseInt(lot.getNumber()) + 1);
		} catch (NoResultException ex) {
			return this.getInitLotNumber();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getInitLotNumber() {
		return this.initLotNumber;
	}

	public void setInitLotNumber(String lotNumber) {
		this.initLotNumber = lotNumber;
	}

	public Set<ELot> findLots(LotFilter lotFiler) throws Exception {
		return find(lotFiler);
	}

	@SuppressWarnings("unchecked")
	public Set<ELot> findSplittedLots(String originalLotNumber) throws Exception {
		try {
			Set<ELot> lots = (Set<ELot>) getEntityManager().createQuery("select o from ELot o where o.originalNumber = ?1")
					.setParameter(1, originalLotNumber)
					.getResultList();
			return lots;
		} catch (NoResultException ex) {
			return new HashSet<ELot>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public Set<ELot> findLotsByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			Set<ELot> list = (Set<ELot>) getEntityManager().createQuery("select o from ELot o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new HashSet<ELot>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findLotNumbersByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from ELot o where o.workOrderItemNumber = ?1")
					.setParameter(1, workOrderItemNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ELot> findLotsByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		try {
			List<ELot> list = (List<ELot>) getEntityManager().createQuery("select o from ELot o where o.workOrderItemNumber = ?1")
					.setParameter(1, workOrderItemNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<ELot>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	/*public void createTransitionAttributes(ETransitionAttributes trsnAttributes) {
		entityManager.persist(trsnAttributes);
	}
	
	public void updateTransitionAttributes(ETransitionAttributes trsnAttributes) {
		trsnAttributes = entityManager.merge(trsnAttributes);
	}*/
}
