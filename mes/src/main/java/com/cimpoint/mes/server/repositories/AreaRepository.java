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
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.AreaFilter;

@Repository("areaRepository")
public class AreaRepository extends JpaRepository<Long, EArea> {

	@Autowired
	private EntityManager entityManager;

	public AreaRepository() {
		super(EArea.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	@Transactional
	public EArea createArea(String name, String description, ESite site, Set<EProductionLine> optProductionLines, CustomAttributes customAttributes,
			MESTrxInfo trxInfo) throws Exception {
		// re-attach production lines to the persistence manager before setting them to area
		Set<EProductionLine> prodLines = null;
		if (optProductionLines != null) {
			prodLines = new HashSet<EProductionLine>();
			for (EProductionLine pdl : optProductionLines) {
				EProductionLine e = this.entityManager.merge(pdl);
				prodLines.add(e);
			}
		}
		
		EArea e = new EArea(name, description, site, prodLines, customAttributes);
		this.create(e, trxInfo);
		return e;
	}

	public Set<EArea> findAreas(AreaFilter filter) {
		Set<EArea> areas = super.find(filter);
		return areas;
	}

	public Set<String> findAreaNames(AreaFilter filter) {
		Set<String> areaNames = super.findAttribute("Name", filter);
		return areaNames;
	}

	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		// detach production lines from area so they don't get removed when the
		// area is removed
		EArea area = entityManager.find(entityClass, id);
		Set<EProductionLine> prodLines = area.getProductionLines();
		if (prodLines != null) {
			for (EProductionLine pdl : prodLines) {
				pdl.setArea(null);
			}
		}
		area.setProductionLines(null);

		super.remove(area, trxInfo);
	}

	@Transactional
	public EArea update(EArea area, TrxInfo trxInfo) {
		// re-attach production lines to area before saving it
		Set<EProductionLine> prodLines = area.getProductionLines();
		if (prodLines != null) {
			Set<EProductionLine> mergedProdLines = new HashSet<EProductionLine>();
			for (EProductionLine pdl : prodLines) {
				EProductionLine e = this.entityManager.merge(pdl);
				e.setArea(area);
				mergedProdLines.add(e);
			}
			area.setProductionLines(mergedProdLines);
		}
		
		area = super.update(area, trxInfo);
		return area;
	}
}
