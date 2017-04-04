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
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.ProductionLineFilter;

@Repository("productionLineRepository")
public class ProductionLineRepository extends JpaRepository<Long, EProductionLine> {

	@Autowired
	private EntityManager entityManager;

	public ProductionLineRepository() {
		super(EProductionLine.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	@Transactional
	public EProductionLine createProductionLine(String name, String description, EArea optArea, Set<EWorkCenter> optWorkCenters,
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		// re-attach work centers to the persistence manager before setting them to prod line
		Set<EWorkCenter> workCenters = null;
		if (optWorkCenters != null) {
			workCenters = new HashSet<EWorkCenter>();
			for (EWorkCenter wc: optWorkCenters) {
				EWorkCenter e = this.entityManager.merge(wc);
				workCenters.add(e);
			}
		}
		
		EProductionLine e = new EProductionLine(name, description, optArea, workCenters, customAttributes);
		
		this.create(e, trxInfo);
		return e;
	}

	public Set<EProductionLine> findProductionLines(ProductionLineFilter filter) {
		Set<EProductionLine> prodLines = super.find(filter);
		return prodLines;
	}

	public Set<String> findProductionLineNames(ProductionLineFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		// detach work centers from production line so they don't get removed when the
		// production line is removed
		EProductionLine prdl = entityManager.find(entityClass, id);
		Set<EWorkCenter> wcs = prdl.getWorkCenters();
		if (wcs != null) {
			for (EWorkCenter wc : wcs) {
				wc.setProductionLine(null);
			}
		}
		prdl.setWorkCenters(null);

		super.remove(prdl, trxInfo);
	}

	@Transactional
	public EProductionLine update(EProductionLine productionLine, TrxInfo trxInfo) {
		Set<EWorkCenter> wcs = productionLine.getWorkCenters();
		if (wcs != null) {
			Set<EWorkCenter> mergedWorkCenters = new HashSet<EWorkCenter>();
			for (EWorkCenter wc : wcs) {
				EWorkCenter ewc = this.entityManager.merge(wc);
				ewc.setProductionLine(productionLine);
				mergedWorkCenters.add(ewc);
			}
		}
		productionLine = super.update(productionLine, trxInfo);
		return productionLine;
	}
}
