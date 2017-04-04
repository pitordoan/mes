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

import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.ContainerFilter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContainerServiceAsync {
	public void enableCache(boolean cacheable, AsyncCallback<Void> callback);
    public void isCacheEnabled(AsyncCallback<Boolean> callback);
    
    public void getNextContainerNumber(AsyncCallback<String> callback);
    public void getInitContainerNumber(AsyncCallback<String> callback);
    public void setInitContainerNumber(String number, AsyncCallback<Void> callback);

    public void findContainerByNumber(String number, AsyncCallback<EContainer> callback);
    public void findContainers(ContainerFilter Filer, AsyncCallback<Set<EContainer>> callback);
    public void findContainerNumbersByWorkOrderItemNumber(String workOrderItemNumber, AsyncCallback<Set<String>> callback);
    public void findContainerNumbersByWorkOrderNumber(String workOrderNumber, AsyncCallback<Set<String>> callback);
	
    public void createContainer(String name, String category, Set<EBatch> batches, 
			Set<ELot> lots, Set<EUnit> units, MESTrxInfo trxInfo, AsyncCallback<EContainer> callback);
    public void saveContainer(EContainer container, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void changeStatus(EContainer container, String newStatus, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void changeState(EContainer container, String newState, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
