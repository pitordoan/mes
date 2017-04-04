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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/containerService")
public interface ContainerService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ContainerServiceAsync instance;
		public static ContainerServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ContainerService.class);
			}
			return instance;
		}
	}
	
	public void enableCache(boolean cacheable);
    public boolean isCacheEnabled();
    
    public String getNextContainerNumber() throws Exception;
    public String getInitContainerNumber();
    public void setInitContainerNumber(String number);

    public EContainer findContainerByNumber(String number) throws Exception;
    public Set<EContainer> findContainers(ContainerFilter Filer) throws Exception;
    public Set<String> findContainerNumbersByWorkOrderItemNumber(String workOrderItemNumber)throws Exception;
    public Set<String> findContainerNumbersByWorkOrderNumber(String workOrderNumber)throws Exception;
	
    public EContainer createContainer(String name, String category, Set<EBatch> batches, 
			Set<ELot> lots, Set<EUnit> units, MESTrxInfo trxInfo) throws Exception;
    public void saveContainer(EContainer container, MESTrxInfo trxInfo) throws Exception;
    public void changeStatus(EContainer container, String newStatus, MESTrxInfo trxInfo) throws Exception;
    public void changeState(EContainer container, String newState, MESTrxInfo trxInfo) throws Exception;
}
