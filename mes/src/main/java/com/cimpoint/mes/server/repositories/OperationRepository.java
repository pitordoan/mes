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

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.OperationFilter;

@Repository("operationRepository")
public class OperationRepository extends JpaRepository<Long, EOperation> {
	
	@Autowired
	private EntityManager entityManager;

	public OperationRepository() {
		super(EOperation.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EOperation createOperation(String name, String description, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		EOperation e = new EOperation(name, description, customAttributes);
		this.create(e, trxInfo);
		return e;
	}
	
	@Transactional 
	public void remove(Long id, TrxInfo trxInfo) {
		//detach steps from operation so they don't get removed when the operation is removed
		EOperation e = entityManager.find(entityClass, id);
		Set<EStep> steps = e.getSteps();
		if (steps != null) {
			for (EStep step : steps) {
				step.setOperation(null);
			}
		}
		e.setSteps(null);
		super.remove(e, trxInfo);
	}
	
	@Transactional 
	public EOperation update(EOperation operation, TrxInfo trxInfo) {
		// re-attach steps before saving operation
		Set<EStep> steps = operation.getSteps();
		if ( steps != null) {
			for (EStep step : steps) {
				step = this.entityManager.merge(step);
				step.setOperation(operation);
			}
		}
		operation = super.update(operation, trxInfo);
		return operation;
	}
	
	public Set<String> findOperationNames(OperationFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
}
