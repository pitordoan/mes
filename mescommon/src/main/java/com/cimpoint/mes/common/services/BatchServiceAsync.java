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
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BatchServiceAsync {
	public void findBatchByName(String name, AsyncCallback<EBatch> callback);
	public void findBatchNumbersByWorkOrderItemNumber(String workOrderItemNumber, AsyncCallback<Set<String>> callback);
	public void findBatchNumbersByWorkOrderNumber(String workOrderNumber, AsyncCallback<Set<String>> callback);

	public void createBatch(String name, EWorkCenter workCenter, MESTrxInfo trxInfo, AsyncCallback<EBatch> callback);
	public void saveBatch(EBatch batch, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
