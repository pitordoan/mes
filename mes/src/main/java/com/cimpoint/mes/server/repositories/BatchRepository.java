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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("batchRepository")
public class BatchRepository extends JpaRepository<Long, EBatch> {
	
	@Autowired
	private EntityManager entityManager;

	public BatchRepository() {
		super(EBatch.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EBatch createBatch(String name, MESTrxInfo trxInfo) throws Exception {
		EBatch batch = new EBatch(name);
		this.create(batch, trxInfo);
		return batch;
	}
	
	@SuppressWarnings("unchecked")
	public Set<EBatch> findBatchesByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			Set<EBatch> list = (Set<EBatch>) getEntityManager()
					.createQuery("select o from EBatch o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new HashSet<EBatch>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}	
	

	@SuppressWarnings("unchecked")
	public List<String> findBatchsByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from EBatch o where o.workOrderItemNumber = ?1")
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
	public List<String> findBatchByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from EBatch o where o.workOrderNumber = ?1")
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
