package com.cimpoint.mes.server.repositories;

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
import com.cimpoint.mes.common.entities.ECheckList;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.CheckListFilter;

@Repository("checkListRepository")
public class CheckListRepository extends JpaRepository<Long, ECheckList> {
	@Autowired
	private EntityManager entityManager;
	
	protected CheckListRepository() {
		super(ECheckList.class);
	}

	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}

	public Set<ECheckList> findSteps(CheckListFilter filter) {
		Set<ECheckList> checkList = super.find(filter);
		return checkList;
	}

	public Set<String> findStepNames(CheckListFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}

	public ECheckList findStepByName(String checkListName) {
		try {
			Query qry = getEntityManager().createQuery("select o from ECheckList o where o.name = ?1")
					.setParameter(1, checkListName)
					.setMaxResults(1);
			ECheckList checkList = (ECheckList) qry.getSingleResult();
			return checkList;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public ECheckList createCheckList(String name, String description, int result, String comment,  
									ECheckList parentChecklist, Set<ECheckList> childCheckLists, MESTrxInfo trxInfo) throws Exception {
		ECheckList e = new ECheckList(name, description, result, comment, parentChecklist, childCheckLists);
		this.create(e, trxInfo);
		return e;
	}
	
	@Transactional 
	public void remove(Long id, TrxInfo trxInfo) {
		super.remove(id, trxInfo);
	}
	
}
