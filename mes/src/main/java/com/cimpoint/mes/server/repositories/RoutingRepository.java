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
import javax.persistence.FlushModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.RoutingFilter;

@Repository("routingRepository")
public class RoutingRepository extends JpaRepository<Long, ERouting> {
	
	@Autowired
	private EntityManager entityManager;

	public RoutingRepository() {
		super(ERouting.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	public Set<String> findRoutingNames(RoutingFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
		
	@Transactional
	public ERouting createRouting(String name, String description, 
			EStep startStep, EStep endStep, Set<EStep> steps, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		this.setFlushMode(FlushModeType.COMMIT);
		
		ERouting routing = new ERouting(name, description, startStep, endStep, steps, customAttributes);	
		
		startStep.setRouting(routing);
		endStep.setRouting(routing);
		
		try {
			if (steps != null && !steps.isEmpty()) {
				Set<EStep> mergedSteps = new HashSet<EStep>();
				for (EStep step: steps) {	
					if (step != null && step.getId() != null && step.getId() != 0) {
						step = this.getEntityManager().merge(step);
						mergedSteps.add(step);
						step.setRouting(routing);
					}
				}				
				
				routing.setSteps(mergedSteps);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		routing = this.create(routing, trxInfo);	
		
		this.flush();

		return routing;
	}
}
