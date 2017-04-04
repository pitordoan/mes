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

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("containerRepository")
public class ContainerRepository extends JpaRepository<Long, EContainer> {
	private String initNumber = "1";

	@Autowired
	private EntityManager entityManager;

	public ContainerRepository() {
		super(EContainer.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	@Transactional
	public EContainer createContainer(String name, String category, Set<EBatch> batches, Set<ELot> lots, Set<EUnit> units, MESTrxInfo trxInfo) throws Exception {
		EContainer e = new EContainer(name, category, batches, lots, units);
		this.create(e, trxInfo);
		return e;
	}

	public String getNextContainerNumber() throws Exception {
		Query qry = getEntityManager().createQuery("select o from EContainer o order by o.number desc").setMaxResults(1);
		try {
			EContainer c = (EContainer) qry.getSingleResult();
			return String.valueOf(Integer.parseInt(c.getNumber()) + 1);
		} catch (NoResultException ex) {
			return this.getInitContainerNumber();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getInitContainerNumber() {
		return this.initNumber;
	}

	public void setInitContainerNumber(String number) {
		this.initNumber = number;
	}

	@SuppressWarnings("unchecked")
	public Set<EContainer> findContainersByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			Set<EContainer> list = (Set<EContainer>) getEntityManager().createQuery("select o from EContainer o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber).getResultList();
			return list;
		} catch (NoResultException ex) {
			return new HashSet<EContainer>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> findContainersByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from EContainer o where o.workOrderItemNumber = ?1")
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
	public List<String> findContainerByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from EContainer o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
}
