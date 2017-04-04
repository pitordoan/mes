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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EWorkInstruction;

@Repository("workInstructionRepository")
public class WorkInstructionRepository extends JpaRepository<Long, EWorkInstruction> {
	
	@Autowired
	private EntityManager entityManager;

	public WorkInstructionRepository() {
		super(EWorkInstruction.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
}
