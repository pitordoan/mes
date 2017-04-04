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
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.ETransitionHistory;

@Repository("transitionAttributesRepository")
public class TransitionAttributesRepository extends JpaRepository<Long, ETransitionAttributes> {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private TransitionHistoryRepository trsnHistoryRep;

	public TransitionAttributesRepository() {
		super(ETransitionAttributes.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	public ETransitionAttributes findTransitionAttributes(long objectId, MESConstants.Object.Type objectType) {
		try {
			Query qry = entityManager.createQuery("select o from " + ETransitionAttributes.class.getName() + " o where o.objectId = ?1 AND o.objectType = ?2")
				.setParameter(1, objectId)
				.setParameter(2, objectType);			
				ETransitionAttributes e = (ETransitionAttributes) qry.getSingleResult();
			return e;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Transactional
	public void createTransitionAttributes(ETransitionAttributes trsnAttributes, TrxInfo trxInfo) {
		this.create(trsnAttributes, trxInfo);
	}

	@Transactional
	public void updateTransitionAttributes(ETransitionAttributes trsnAttributes, TrxInfo trxInfo) {
		trsnAttributes = this.update(trsnAttributes, trxInfo);
		
		ETransitionHistory trsnHist = new ETransitionHistory(trsnAttributes);
		trsnHistoryRep.update(trsnHist, trxInfo);
	}
}
