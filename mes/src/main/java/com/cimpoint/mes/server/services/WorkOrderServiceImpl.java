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
package com.cimpoint.mes.server.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EControlManager;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.WorkOrderService;
import com.cimpoint.mes.server.repositories.ControlManagerRepository;
import com.cimpoint.mes.server.repositories.WorkOrderRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("workOrderService")
public class WorkOrderServiceImpl extends RemoteServiceServlet implements WorkOrderService {
	private static final long serialVersionUID = -2154209646574143075L;

	@Autowired
	private WorkOrderRepository workOrderRep;
	
	@Autowired
	private ControlManagerRepository controlManagerRep;

	@PostConstruct
	public void initialize() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}
	
	@Override
	public String getNextWorkOrderNumber() throws Exception {
		return workOrderRep.getNextWorkOrderNumber();
	}

	@Override
	public String getNextWorkOrderItemNumber() throws Exception {
		return workOrderRep.getNextWorkOrderItemNumber();
	}

	@Override
	public String getInitWorkOrderNumber() {
		return workOrderRep.getInitWorkOrderNumber();
	}

	@Override
	public void setInitWorkOrderNumber(String woNumber) {
		workOrderRep.setInitWorkOrderNumber(woNumber);
	}

	@Override
	public String getInitWorkOrderItemNumber() {
		return workOrderRep.getInitWorkOrderItemNumber();
	}

	@Override
	public void setInitWorkOrderItemNumber(String woiNumber) {
		workOrderRep.setInitWorkOrderItemNumber(woiNumber);
	}

	@Override
	public EWorkOrder findWorkOrderById(long workOrderId) throws Exception {
		return this.workOrderRep.findById(workOrderId);
	}

	@Override
	public EWorkOrder findWorkOrderByNumber(String workOrderNumber) throws Exception {
		EWorkOrder re = this.workOrderRep.findByNumber(workOrderNumber);
		return re;
	}

	@Override
	public EWorkOrderItem findWorkOrderItemByNumber(String workOrderItemNumber) throws Exception {
		return workOrderRep.findWorkOrderItemByNumber(workOrderItemNumber);
	}

	@Override
	public void saveWorkOrder(EWorkOrder workOrder, MESTrxInfo trxInfo) throws Exception {

	}

	@Override
	public void saveItem(EWorkOrderItem workOrderItem, MESTrxInfo trxInfo) throws Exception {
	}

	@Override
	public EWorkOrderItem newWorkOrderItem(String workOrderItemNumber, EPart part) throws Exception {
		return new EWorkOrderItem(workOrderItemNumber, part);
	}

	@Override
	public EWorkOrder createWorkOrder(String workOrderNumber, Set<EWorkOrderItem> workOrderItems, MESTrxInfo trxInfo, MESConstants.Object.UnitOfMeasure unitOfMeasure) throws Exception {
		return workOrderRep.createWorkOrder(workOrderNumber, workOrderItems, trxInfo, unitOfMeasure);
	}

	@Override
	public void removeWorkOrder(Long id, MESTrxInfo trxInfo) throws Exception {
		workOrderRep.remove(id, trxInfo);
	}
	
	//for control manager
	@Override
	public Set<EControlManager> findAllControls() throws Exception {
		return controlManagerRep.findAll();
	}

	@Override
	public void saveControl(EControlManager control) throws Exception {
		controlManagerRep.saveControl(control);
	}

	@Override
	public void removeControl(EControlManager control) throws Exception {
		controlManagerRep.removeControl(control);
	}

	@Override
	public Set<String> findWorkOrderItemNumbersByWorkOrderNumber(String workOrderNumber)
			throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(workOrderRep.findWorkOrderItemsByWorkOrderNumber(workOrderNumber));
		return result;
	}
}
