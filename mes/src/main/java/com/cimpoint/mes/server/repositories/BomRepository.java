/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.server.repositories;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("bomRepository")
public class BomRepository extends JpaRepository<Long, EBom> {

	@Autowired
	private EntityManager entityManager;

	public BomRepository() {
		super(EBom.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EBom createBom(String name, String desc, String revision, String startDate, String endDate, Set<EBomItem> bomItems, MESTrxInfo trxInfo) {
		// re-attach bom-items before saving
		Set<EBomItem> items = new HashSet<EBomItem>();
		if (bomItems != null) {
			for (EBomItem e : bomItems) {
				e = this.entityManager.merge(e);
				items.add(e);
			}
		}
		
		EBom e = new EBom(name, desc, revision, startDate, endDate, items);
		create(e, trxInfo);
		return e;
	}
	
	public EBom findBomByNameAndRevision(String name, String revision) throws Exception {
		try {
			return (EBom) getEntityManager().createQuery("select o from EBom o where o.name = ?1 and o.revision = ?2")
					.setParameter(1, name)
					.setParameter(2, revision)
					.getSingleResult();				
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Transactional
	public EBom update(EBom bom, TrxInfo trxInfo) {
		Set<EBomItem> items = bom.getBomItems();
		if (items != null) {
			for (EBomItem item : items) {
				item = entityManager.merge(item);
				item.setBom(bom);
			}
		}
		
		bom = super.update(bom, trxInfo);
		return bom;
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		//detach areas from site so they don't get removed when the site is removed
		EBom e = entityManager.find(entityClass, id);
		Set<EBomItem> bomItems = e.getBomItems();
		if (bomItems != null) {
			for (EBomItem a: bomItems) {
				a.setBom(null);
			}
		}
		e.setBomItems(null);
		
		super.remove(e, trxInfo);
	}
}
