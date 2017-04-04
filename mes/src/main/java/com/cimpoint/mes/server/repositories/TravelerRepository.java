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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.ETraveler;

@Repository("travelerRepository")
public class TravelerRepository extends JpaRepository<Long, ETraveler> {
	
	@Autowired
	private EntityManager entityManager;

	public TravelerRepository() {
		super(ETraveler.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public ETraveler createTraveler(String workOrderNumber) throws Exception {
		ETraveler traveler = new ETraveler(workOrderNumber);
		getEntityManager().persist(traveler);
		return traveler;
	}
	
	@Transactional
	public ETraveler updateTraveler(ETraveler traveler) throws Exception {
		getEntityManager().merge(traveler);
		return traveler;
	}
		
	public ETraveler findTravelerById(long id) throws Exception {
		return (ETraveler) getEntityManager().find(ETraveler.class, id);
	}
	
	public ETraveler findTravelerByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			ETraveler traveler = (ETraveler) getEntityManager()
					.createQuery("select o from ETraveler o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber).getSingleResult();
			return traveler;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
