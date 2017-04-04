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

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EDictionary;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;

@Repository("transitionRepository")
public class TransitionRepository extends JpaRepository<Long, ETransition> {
	
	@Autowired
	private EntityManager entityManager;

	public TransitionRepository() {
		super(ETransition.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
			
	/*@Transactional
	public ETransition createTransition(ERouting routing, ETransition transition) throws Exception {
		routing.getTransitions().add(transition);
		transition.setRouting(routing);
		getEntityManager().persist(transition);
		return transition;
	}*/
	
	@Transactional
	public ETransition createTransition(String transitionName, Quantity transferQuantity, EDictionary reasonDictionary, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		ETransition transition = new ETransition(transitionName, transferQuantity, reasonDictionary, customAttributes);
		this.create(transition, trxInfo);
		return transition;
	}
	
	@Transactional
	public ETransition createTransition(ETransition transition, MESTrxInfo trxInfo) throws Exception {
		this.create(transition, trxInfo);
		return transition;
	}
	
	public ETransition findTransitionByName(String routingName, String transitionName) {
		try {
			Query qry = getEntityManager().createQuery("select o from ETransition o where o.routingName = ?1 and o.name = ?2")
					.setParameter(1, routingName)
					.setParameter(2, transitionName)
					.setMaxResults(1);
			ETransition trsn = (ETransition) qry.getSingleResult();
			return trsn;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
