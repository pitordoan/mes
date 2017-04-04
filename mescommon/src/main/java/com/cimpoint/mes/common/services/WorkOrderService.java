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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/workOrderService")
public interface WorkOrderService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static WorkOrderServiceAsync instance;
		public static WorkOrderServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(WorkOrderService.class);
			}
			return instance;
		}
	}
	
	//for control manager dialog
	public Set<EControlManager> findAllControls() throws Exception;
	public void saveControl(EControlManager control) throws Exception;
	public void removeControl(EControlManager control) throws Exception;
	
	
	
	public String getNextWorkOrderNumber() throws Exception;
    public String getNextWorkOrderItemNumber() throws Exception;
    public String getInitWorkOrderNumber();
    public void setInitWorkOrderNumber(String woNumber);
    public String getInitWorkOrderItemNumber();
    public void setInitWorkOrderItemNumber(String woiNumber);
    
    
    public EWorkOrder findWorkOrderById(long workOrderId) throws Exception;
    public EWorkOrder findWorkOrderByNumber(String workOrderNumber) throws Exception;
    public Set<String> findWorkOrderItemNumbersByWorkOrderNumber(String workOrderNumber)throws Exception;
    
    public EWorkOrder createWorkOrder(String workOrderNumber, Set<EWorkOrderItem> workOrderItems, MESTrxInfo trxInfo, MESConstants.Object.UnitOfMeasure unitOfMeasure) throws Exception;
    public void removeWorkOrder(Long id, MESTrxInfo trxInfo) throws Exception;
    public EWorkOrderItem findWorkOrderItemByNumber(String workOrderItemNumber) throws Exception;
    
    public void saveWorkOrder(EWorkOrder workOrder, MESTrxInfo trxInfo) throws Exception;
    public void saveItem(EWorkOrderItem workOrderItem, MESTrxInfo trxInfo) throws Exception;
    
    public EWorkOrderItem newWorkOrderItem(String workOrderItemNumber, EPart part) throws Exception;
}
