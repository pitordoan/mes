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
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.SiteFilter;

@Repository("siteRepository")
public class SiteRepository extends JpaRepository<Long, ESite> {
	
	@Autowired
	private EntityManager entityManager;

	public SiteRepository() {
		super(ESite.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	public Set<ESite> findSites(SiteFilter filter) {
		Set<ESite> sites = super.find(filter);
		return sites;
	}

	public Set<String> findSiteNames(SiteFilter filter) {
		Set<String> siteNames = super.findAttribute("Name", filter);
		return siteNames;
	}
	
	@Transactional
	public ESite createSite(String name, String description, Set<EArea> optAreas, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		//re-attach areas to the persistence manager before setting them to site
		Set<EArea> areas = null;
		if (optAreas != null) {
			areas = new HashSet<EArea>();
			for (EArea area: optAreas) {
				EArea a = this.entityManager.merge(area);
				areas.add(a);
			}
		}	
				
		ESite e = new ESite(name, description, areas, customAttributes);
		create(e, trxInfo);		
		return e;
	}
	
	@Transactional 
	public void remove(Long id, TrxInfo trxInfo) {
		//detach areas from site so they don't get removed when the site is removed
		ESite e = entityManager.find(entityClass, id);
		Set<EArea> areas = e.getAreas();
		if (areas != null) {
			for (EArea a: areas) {
				a.setSite(null);
			}
		}
		e.setAreas(null);
		
		super.remove(e, trxInfo);
	}
	
	@Transactional 
	public ESite update(ESite site, TrxInfo trxInfo) {
		//re-attach areas to site before saving site
		Set<EArea> areas = site.getAreas();
		if (areas != null) {
			for (EArea a: areas) {
				a = this.entityManager.merge(a);
				a.setSite(site);
			}
		}
		site = super.update(site, trxInfo);
		return site;
	}
}
