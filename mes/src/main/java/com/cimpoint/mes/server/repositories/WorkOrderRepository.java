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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("workOrderRepository")
public class WorkOrderRepository extends JpaRepository<Long, EWorkOrder> {
	private String initWONumber = "1";
	private String initWOItemNumber = "1";

	@Autowired
	private EntityManager entityManager;

	public WorkOrderRepository() {
		super(EWorkOrder.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EWorkOrder createWorkOrder(String workOrderNumber, Set<EWorkOrderItem> workOrderItems, MESTrxInfo trxInfo, MESConstants.Object.UnitOfMeasure unitOfMeasure) throws Exception {
		EWorkOrder e = new EWorkOrder(workOrderNumber, workOrderItems, unitOfMeasure);
		this.create(e, trxInfo);
		return e;
	}
	
	@Transactional
	public void updateWorkOrder(EWorkOrder workOrder) throws Exception {
		getEntityManager().merge(workOrder);
	}
		
	@Transactional
	public void updateWorkOrderItem(EWorkOrderItem workOrderItem) throws Exception {
		getEntityManager().merge(workOrderItem.getWorkOrder());
	}
	
	public EWorkOrder findWorkOrderById(long id) {
		return (EWorkOrder) getEntityManager().find(EWorkOrder.class, id);
	}
	
	public EWorkOrder findWorkOrderByNumber(String woNumber) throws Exception {
		try {
			EWorkOrder wo = (EWorkOrder) getEntityManager().createQuery("select o from EWorkOrder o  where o.number = ?1")
					.setParameter(1, woNumber)
					.getSingleResult();
			return wo;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public EWorkOrderItem findWorkOrderItemByNumber(String workOrderItemNumber) throws Exception {
		try {
			EWorkOrderItem woi = (EWorkOrderItem) getEntityManager().createQuery("select o from EWorkOrderItem o  where o.number = ?1")
					.setParameter(1, workOrderItemNumber)
					.getSingleResult();
			return woi;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> findWorkOrderItemsByWorkOrderNumber(String woNumber) throws Exception {
		try {
			List<String> list = (List<String>) getEntityManager().createQuery("select o.number from EWorkOrderItem o  where o.workOrder.number = ?1")
					.setParameter(1, woNumber)
					.getResultList();
			return list;
		} catch (NoResultException ex) {
			return new ArrayList<String>();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public String getNextWorkOrderNumber() throws Exception {
		Query qry = getEntityManager().createQuery("select o from EWorkOrder o order by o.number desc").setMaxResults(1);
		try {
			EWorkOrder wo = (EWorkOrder) qry.getSingleResult();
			return String.valueOf(Integer.parseInt(wo.getNumber()) + 1);
		} catch (NoResultException ex) {
			return this.getInitWorkOrderNumber();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getNextWorkOrderItemNumber() throws Exception {

		Query qry = getEntityManager().createQuery("select o from EWorkOrderItem o order by o.number desc").setMaxResults(1);
		try {
			EWorkOrderItem woi = (EWorkOrderItem) qry.getSingleResult();
			return String.valueOf(Integer.parseInt(woi.getNumber()) + 1);
		} catch (NoResultException ex) {
			return this.getInitWorkOrderNumber();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public String getInitWorkOrderNumber() {
		return initWONumber;
	}

	public void setInitWorkOrderNumber(String woNumber) {
		this.initWONumber = woNumber;
	}

	public String getInitWorkOrderItemNumber() {
		return initWOItemNumber;
	}

	public void setInitWorkOrderItemNumber(String woiNumber) {
		initWOItemNumber = woiNumber;
	}
	
	public void removeWorkOrder(EWorkOrder workOrder) throws Exception {
		this.entityManager.remove(workOrder);
	}
}
