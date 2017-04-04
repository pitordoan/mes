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
package com.cimpoint.mes.client.controllers;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.objects.WorkOrderItem;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EControlManager;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.WorkOrderService;
import com.cimpoint.mes.common.services.WorkOrderServiceAsync;

public class WorkOrderController extends AppController {

	private WorkOrderServiceAsync workOrderService;
	
	public WorkOrderController() {
		workOrderService = WorkOrderService.Util.getInstance();
	}
	
	public void findWorkOrderById(long workOrderId, CallbackHandler<WorkOrder> callback) {
		this.workOrderService.findWorkOrderById(workOrderId, new  MESTypeConverter.EntityToClientObjectCallback<EWorkOrder, WorkOrder>(WorkOrder.class, callback));
	}
	
    public void findWorkOrderByNumber(String workOrderNumber, CallbackHandler<WorkOrder> callback) {
    	this.workOrderService.findWorkOrderByNumber(workOrderNumber, new  MESTypeConverter.EntityToClientObjectCallback<EWorkOrder, WorkOrder>(WorkOrder.class, callback));
    }
    
    public void createWorkOrder(String workOrderNumber, Set<EWorkOrderItem> workOrderItems, String comment, MESConstants.Object.UnitOfMeasure uom, CallbackHandler<WorkOrder> callback) {
    	MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
    	Set<EWorkOrderItem> ewis =  MESTypeConverter.toEntitySet(workOrderItems);
		this.workOrderService.createWorkOrder(workOrderNumber, ewis, trxInfo, uom,
				new  MESTypeConverter.EntityToClientObjectCallback<EWorkOrder, WorkOrder>(WorkOrder.class, callback));
    }
    
    public void removeWorkOrder(Long workOrderId, String comment, CallbackHandler<Void> callback) {
    	MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
    	this.workOrderService.removeWorkOrder(workOrderId, trxInfo, new VoidCallback(callback));
    }
    
    public void findWorkOrderItemByNumber(String workOrderItemNumber, CallbackHandler<WorkOrderItem> callback) {
    	this.workOrderService.findWorkOrderItemByNumber(workOrderItemNumber, new  MESTypeConverter.EntityToClientObjectCallback<EWorkOrderItem, 
    			WorkOrderItem>(WorkOrderItem.class, callback));
    }

	public void saveItem(WorkOrderItem workOrderItem, String comment, CallbackHandler<Void> callback) {
		EWorkOrderItem e = workOrderItem.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.workOrderService.saveItem(e, trxInfo, callback);
	}

	public void saveWorkOrder(WorkOrder workOrder, String comment, CallbackHandler<Void> callback) {
		EWorkOrder e = workOrder.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.workOrderService.saveWorkOrder(e, trxInfo, callback);
	}

	public void findAllControls(CallbackHandler<Set<EControlManager>> callback){
		this.workOrderService.findAllControls(callback);
	}
	
	public void saveControl(EControlManager control, CallbackHandler<Void> callback){
		this.workOrderService.saveControl(control, callback);
	}
	
	public void removeControl(EControlManager control, CallbackHandler<Void> callback){
		this.workOrderService.removeControl(control, callback);
	}
	
	
	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
