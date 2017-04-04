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
package com.cimpoint.mes.common.services;

import java.util.Set;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EControlManager;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkOrderServiceAsync {
	//for control manager dialog
	public void findAllControls(AsyncCallback<Set<EControlManager>> callback);
	public void saveControl(EControlManager control, AsyncCallback<Void> callback);
	public void removeControl(EControlManager control, AsyncCallback<Void> callback);
	
	
	
	public void getNextWorkOrderNumber(AsyncCallback<String> callback);
    public void getNextWorkOrderItemNumber(AsyncCallback<String> callback);
    public void getInitWorkOrderNumber(AsyncCallback<String> callback);
    public void setInitWorkOrderNumber(String woNumber, AsyncCallback<Void> callback);
    public void getInitWorkOrderItemNumber(AsyncCallback<String> callback);
    public void setInitWorkOrderItemNumber(String woiNumber, AsyncCallback<Void> callback);
    
    
    public void findWorkOrderById(long workOrderId, AsyncCallback<EWorkOrder> callback);
    public void findWorkOrderByNumber(String workOrderNumber, AsyncCallback<EWorkOrder> callback);
    public void findWorkOrderItemNumbersByWorkOrderNumber(String workOrderNumber, AsyncCallback<Set<String>> callback);
    
    public void createWorkOrder(String workOrderNumber, Set<EWorkOrderItem> workOrderItems, MESTrxInfo trxInfo, MESConstants.Object.UnitOfMeasure unitOfMeasure, AsyncCallback<EWorkOrder> callback);
    public void removeWorkOrder(Long id, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void findWorkOrderItemByNumber(String workOrderItemNumber, AsyncCallback<EWorkOrderItem> callback);
    
    public void saveWorkOrder(EWorkOrder workOrder, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void saveItem(EWorkOrderItem workOrderItem, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    
    public void newWorkOrderItem(String workOrderItemNumber, EPart part, AsyncCallback<EWorkOrderItem> callback);
}
