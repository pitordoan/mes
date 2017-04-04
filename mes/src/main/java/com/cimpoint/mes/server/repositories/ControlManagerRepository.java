package com.cimpoint.mes.server.repositories;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EControlManager;

@Repository("controlMangerRepository")
public class ControlManagerRepository extends JpaRepository<Long, EControlManager> {

	@Autowired
	private EntityManager entityManager;

	public ControlManagerRepository() {
		super(EControlManager.class);
	}


	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	
	public Set<EControlManager> findAllControls() throws Exception {
		return super.findAll();
	}

	public void saveControl(EControlManager control) throws Exception {
		// TODO Auto-generated method stub
		this.create(control);
	}

	public void removeControl(EControlManager control) throws Exception {
		
	}
}

