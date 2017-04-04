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
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.PartFilter;

@Repository("partRepository")
public class PartRepository extends JpaRepository<Long, EPart> {
	
	@Autowired
	private EntityManager entityManager;

	public PartRepository() {
		super(EPart.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EPart createPart(String partName, String desc, String partRevision, String quantiy, 
			MESConstants.Object.UnitOfMeasure unitOfMeasure, MESConstants.Object.PartCatergory category,
			Set<EBom> boms, String startDate, String endDate, MESTrxInfo trxInfo)  throws Exception {
		// re-attach boms before saving
		Set<EBom> bomList = null;
		if (boms != null) {
			bomList = new HashSet<EBom>();
			for(EBom bom : boms) {
				EBom e = this.entityManager.merge(bom);
				bomList.add(e);
			}
		}
		
		EPart e = new EPart(partName, desc, partRevision, quantiy, unitOfMeasure, category, bomList, startDate, endDate);
		this.create(e, trxInfo);
		return e;
	}
	
	public EPart findPartByNameAndRevision(String partName, String partRevision) throws Exception {
		try {
			return (EPart) getEntityManager().createQuery("select o from EPart o where o.name = ?1 and o.revision = ?2")
					.setParameter(1, partName)
					.setParameter(2, partRevision)
					.getSingleResult();				
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> findRevisionsByPartName(String partName) throws Exception {
		try {
			Set<String> strSet = new HashSet<String>();

			Query query = entityManager.createQuery("SELECT e FROM EPart e where e.name = ?1");
			query.setParameter(1, partName);
			List<EPart> parts = (List<EPart>) query.getResultList();
			if (parts != null && parts.size() > 0) {
				for (EPart e : parts) {
					strSet.add(e.getRevision());
				}
			}
			return strSet;
		} catch (NoResultException ex) {
			return new HashSet<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new HashSet<String>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<EPart> findParts(PartFilter filter) throws Exception {
		//TODO filter
		Query query = this.getEntityManager().createQuery("SELECT p FROM EPart p");
	    return (Set<EPart>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> findAllPartNameWithRevisions() throws Exception {
		try {
			Set<String> strSet = new HashSet<String>();

			Query qry = entityManager.createQuery("SELECT e FROM EPart e");
			List<EPart> parts = (List<EPart>) qry.getResultList();
			if (parts != null && parts.size() > 0) {
				for (EPart e : parts) {
					strSet.add(e.getName() + MESConstants.REV_FOR_TEXT + e.getRevision());
				}
			}
			return strSet;
		} catch (NoResultException ex) {
			return new HashSet<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new HashSet<String>();
		}
	}
	
	
	@Transactional 
	public EPart update(EPart part, TrxInfo trxInfo) {
		// re-attach before saving part
//		Set<EBom> boms = part.getBoms();
//		if ( boms != null ) {
//			for (EBom bom : boms) {
//				Set<EPart> parts  = bom.getParts();
//				if (parts != null) {
//					for (EPart p : parts) {
//						if (p.getNumber().equals(part.getNumber()) && p.getRevision().equals(part.getRevision())) {
//							parts.remove(p);
//							break;
//						}
//					}
//					parts.add(part);
//				}
//				
//			}
//		}
		
		return super.update(part, trxInfo);
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		EPart entity = entityManager.find(entityClass, id);
		super.remove(entity, trxInfo);
	}

}
