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
import com.cimpoint.mes.common.entities.EMfgBom;
import com.cimpoint.mes.common.entities.EMfgBomItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("mfgBomRepository")
public class MfgBomRepository extends JpaRepository<Long, EMfgBom> {

	@Autowired
	private EntityManager entityManager;
	
	protected MfgBomRepository() {
		super(EMfgBom.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EMfgBom createMfgBom(String name, String desc, String revision, String startDate, String endDate, Set<EMfgBomItem> mfgBomItems, MESTrxInfo trxInfo) {
		// re-attach bom-items before saving
		Set<EMfgBomItem> items = new HashSet<EMfgBomItem>();
		if (mfgBomItems != null) {
			for (EMfgBomItem e : mfgBomItems) {
				e = this.entityManager.merge(e);
				items.add(e);
			}
		}
		
		EMfgBom e = new EMfgBom(name, desc, revision, startDate, endDate, items);
		create(e, trxInfo);
		return e;
	}
	
	public EMfgBom findMfgBomByNameAndRevision(String name, String revision) throws Exception {
		try {
			return (EMfgBom) getEntityManager().createQuery("select o from EMfgBom o where o.name = ?1 and o.revision = ?2")
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
	public EMfgBom update(EMfgBom mfgBom, TrxInfo trxInfo) {
		Set<EMfgBomItem> items = mfgBom.getMfgBomItems();
		if (items != null) {
			for (EMfgBomItem item : items) {
				item = entityManager.merge(item);
				item.setMfgBom(mfgBom);
			}
		}
		
		mfgBom = super.update(mfgBom, trxInfo);
		return mfgBom;
	}
	
	@Transactional
	public void remove(Long id, TrxInfo trxInfo) {
		//detach areas from site so they don't get removed when the site is removed
		EMfgBom e = entityManager.find(entityClass, id);
		Set<EMfgBomItem> mfgBomItems = e.getMfgBomItems();
		if (mfgBomItems != null) {
			for (EMfgBomItem a: mfgBomItems) {
				a.setMfgBom(null);
			}
		}
		e.setMfgBomItems(null);
		
		super.remove(e, trxInfo);
	}
}
