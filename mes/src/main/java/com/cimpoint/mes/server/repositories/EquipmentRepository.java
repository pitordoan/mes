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
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.EquipmentFilter;

@Repository("equipmentRepository")
public class EquipmentRepository extends JpaRepository<Long, EEquipment> {
	
	@Autowired
	private EntityManager entityManager;

	public EquipmentRepository() {
		super(EEquipment.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	public Set<String> findEquipmentNames(EquipmentFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
	
	public Set<EEquipment> findEquipments(EquipmentFilter filter) {
		Set<EEquipment> eqs = super.find(filter);
		return eqs;
	}
	
	@Transactional
	public EEquipment createEquipment(String name, String description, EWorkCenter workCenter, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		EEquipment e = new EEquipment(name, description, workCenter, customAttributes);
		this.create(e, trxInfo);
		return e;
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		EEquipment e = entityManager.find(entityClass, id);
		
		// detach from workcenter 
		EWorkCenter wc = e.getWorkCenter();
		Set<EEquipment> equipments = wc.getEquipments();
		if (equipments != null) {
			equipments.remove(e);
		}
		e.setWorkCenter(null);
		
		super.remove(e, trxInfo);
	}
}
