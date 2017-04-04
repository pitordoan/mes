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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.filters.UnitFilter;

@Repository("unitRepository")
public class UnitRepository extends JpaRepository<Long, EUnit> {
	private String initUnitSerialNumber = "1";

	@Autowired
	private EntityManager entityManager;

	public UnitRepository() {
		super(EUnit.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision) throws Exception {
		EUnit Unit = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision);
		getEntityManager().persist(Unit);
		return Unit;
	}

	public Set<EUnit> createUnits(String[] unitSerialNumbers,
			String workOrderNumber, String partNumber, String partRevision) throws Exception {
		Set<EUnit> units = new HashSet<EUnit>();
		for (String sn: unitSerialNumbers) {
			EUnit unit = createUnit(sn, workOrderNumber, partNumber, partRevision);
			units.add(unit);
		}
				
		return units;
	}
	
	@Transactional
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot) throws Exception {
		EUnit Unit = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, lot);
		getEntityManager().persist(Unit);
		return Unit;
	}
	
	@Transactional
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit unit) throws Exception {
		EUnit Unit = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, unit);
		getEntityManager().persist(Unit);
		return Unit;
	}
	
	@Transactional
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, EUnit unit) throws Exception {
		EUnit Unit = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, lot, unit);
		getEntityManager().persist(Unit);
		return Unit;
	}
	
	@Transactional
	public EUnit updateUnit(EUnit Unit) {
		getEntityManager().merge(Unit);
		return Unit;
	}
		
	public EUnit findUnitById(long id) {
		return (EUnit) getEntityManager().find(EUnit.class, id);
	}
	
	public EUnit findUnitBySerialNumber(String unitSerialNumber) throws Exception {
		try {
			EUnit Unit = (EUnit) getEntityManager().createQuery("select o from EUnit o where o.serialNumber = ?1")
					.setParameter(1, unitSerialNumber)
					.getSingleResult();
			return Unit;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public String getNextUnitSerialNumber() throws Exception {
		Query qry = getEntityManager().createQuery("select o from EUnit o order by o.serialNumber desc").setMaxResults(1);
		try {
			EUnit Unit = (EUnit) qry.getSingleResult();
			return String.valueOf(Integer.parseInt(Unit.getSerialNumber()) + 1);
		} catch (NoResultException ex) {
			return this.getInitUnitSerialNumber();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getInitUnitSerialNumber() {
		return this.initUnitSerialNumber;
	}

	public void setInitUnitSerialNumber(String unitSerialNumber) {
		this.initUnitSerialNumber = unitSerialNumber;
	}

	public Set<EUnit> findUnits(UnitFilter unitFilter) throws Exception {
		return find(unitFilter);
	}

	@SuppressWarnings("unchecked")
	public List<EUnit> findUnitsByWorkOrderNumber(String workOrderNumber) throws Exception {
		try {
			List<EUnit> list = (List<EUnit>) getEntityManager().createQuery("select o from EUnit o where o.workOrderNumber = ?1")
					.setParameter(1, workOrderNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<EUnit>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<String> findUnitsByLotNumber(String lotNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.serialNumber from EUnit o where o.lot.number = ?1")
					.setParameter(1, lotNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getTimeDecoder(Date clientTime, String clientTimezoneId) {
		SimpleDateFormat timeFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		timeFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utcTime = timeFormater.format(clientTime);
		return "UTC-" + utcTime + "-" + clientTimezoneId;
	}
}
