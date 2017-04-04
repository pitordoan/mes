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

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("stepStatusRepository")
public class StepStatusRepository extends JpaRepository<Long, EStepStatus> {
	
	@Autowired
	private EntityManager entityManager;

	public StepStatusRepository() {
		super(EStepStatus.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	public EStepStatus findStepStatusByName(String stepStatusName) {
		try {
			Query qry = getEntityManager().createQuery("select o from EStepStatus o where o.name = ?1")
					.setParameter(1, stepStatusName)
					.setMaxResults(1);
			EStepStatus step = (EStepStatus) qry.getSingleResult();
			return step;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public EStepStatus creatStepStatus(String name, boolean isStarting, boolean isEnding, 
			String nextDefaultStatusName, MESTrxInfo trxInfo) throws Exception {
		EStepStatus e = new EStepStatus(name, isStarting, isEnding, nextDefaultStatusName);
		this.create(e, trxInfo);
		return e;
	}
		
	@Transactional 
	public void remove(Long id, TrxInfo trxInfo) {
		EStepStatus e = entityManager.find(entityClass, id);
		//e.setSteps(null); //detach steps
		super.remove(e, trxInfo);
	}
}
