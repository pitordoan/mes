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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/batchService")
public interface BatchService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BatchServiceAsync instance;
		public static BatchServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(BatchService.class);
			}
			return instance;
		}
	}
	
	public EBatch findBatchByName(String name) throws Exception;
	public Set<String> findBatchNumbersByWorkOrderItemNumber(String workOrderItemNumber)throws Exception;
	public Set<String> findBatchNumbersByWorkOrderNumber(String workOrderNumber)throws Exception;

	public EBatch createBatch(String name, EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception;
	public void saveBatch(EBatch batch, MESTrxInfo trxInfo) throws Exception;
}
