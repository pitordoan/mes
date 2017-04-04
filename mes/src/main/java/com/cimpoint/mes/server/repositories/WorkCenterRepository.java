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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.WorkCenterFilter;

@Repository("workCenterRepository")
public class WorkCenterRepository extends JpaRepository<Long, EWorkCenter> {
	
	@Autowired
	private EntityManager entityManager;

	public WorkCenterRepository() {
		super(EWorkCenter.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
		
	@Transactional
	public EWorkCenter createWorkCenter(String name, String description, EArea optArea, 
			Set<EEquipment> optEquipments, EProductionLine optProductionLine, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		Set<EEquipment> equipments = new HashSet<EEquipment>();
		if (optEquipments != null) {
			for (EEquipment wc: optEquipments) {
				EEquipment e = this.entityManager.merge(wc);
				equipments.add(e);
			}
		}
		
		EWorkCenter e = new EWorkCenter(name, description, optArea, equipments, optProductionLine, customAttributes);
		this.create(e, trxInfo);
		return e;
	}
	
	public Set<EWorkCenter> findWorkCenters(WorkCenterFilter filter) {
		Set<EWorkCenter> wcs = super.find(filter);
		return wcs;
	}

	public Set<String> findWorkCenterNames(WorkCenterFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		EWorkCenter wc = entityManager.find(entityClass, id);
		
		Set<EEquipment> equipments = wc.getEquipments();
		if (equipments != null) {
			for (EEquipment eq: equipments) {
				eq.setWorkCenter(null);
			}
		}
		wc.setEquipments(null);
		
		Set<EStep> steps = wc.getSteps();
		if (steps != null) {
			for (EStep s: steps) {
				Set<EWorkCenter> wcs = s.getWorkCenters();
				if (wcs != null) {
					wcs.remove(this);
				}
			}
		}
		wc.setSteps(null);

		super.remove(wc, trxInfo);
	}

	@Transactional
	public EWorkCenter update(EWorkCenter workCenter, TrxInfo trxInfo) {
		Set<EEquipment> eqs = workCenter.getEquipments();
		if (eqs != null) {
			for (EEquipment eq : eqs) {
				eq = this.entityManager.merge(eq);
				eq.setWorkCenter(workCenter);
			}
		}
		
		Set<EStep> steps = workCenter.getSteps();
		if (steps != null) {
			for (EStep s : steps) {
				s = this.entityManager.merge(s);
				Set<EWorkCenter> wcs = s.getWorkCenters();
				if (wcs != null) {
					for (EWorkCenter wc: wcs) {
						if (wc.getName().equals(workCenter.getName())) {
							wcs.remove(wc);
							break;
						}
					}
					wcs.add(workCenter);
				}
			}
		}
		
		workCenter = super.update(workCenter, trxInfo);
		return workCenter;
	}
}
