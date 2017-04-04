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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.ContainerFilter;
import com.cimpoint.mes.common.services.ContainerService;
import com.cimpoint.mes.server.repositories.ContainerRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("containerService")
public class ContainerServiceImpl extends RemoteServiceServlet implements ContainerService {
	private static final long serialVersionUID = -5561553822633129331L;

	@Autowired
	private ContainerRepository containerRepository;
	
	@Override
	public void enableCache(boolean cacheable) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCacheEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNextContainerNumber() throws Exception {
		return containerRepository.getNextContainerNumber();
	}

	@Override
	public String getInitContainerNumber() {
		return containerRepository.getInitContainerNumber();
	}

	@Override
	public void setInitContainerNumber(String number) {
		containerRepository.setInitContainerNumber(number);
	}

	@Override
	public EContainer findContainerByNumber(String number) throws Exception {
		return containerRepository.findByNumber(number);
	}

	@Override
	public Set<EContainer> findContainers(ContainerFilter filter) throws Exception {
		return null;
	}

	@Override
	public void saveContainer(EContainer container, MESTrxInfo trxInfo) throws Exception {
		containerRepository.create(container, trxInfo);
	}

	@Override
	public void changeStatus(EContainer container, String newStatus, MESTrxInfo trxInfo) throws Exception {
	}

	@Override
	public void changeState(EContainer container, String newState, MESTrxInfo trxInfo) throws Exception {
		
	}

	@Override
	public EContainer createContainer(String name, String category, Set<EBatch> batches, Set<ELot> lots, Set<EUnit> units, MESTrxInfo trxInfo)
			throws Exception {
		return containerRepository.createContainer(name, category, batches, lots, units, trxInfo);
	}

	@Override
	public Set<String> findContainerNumbersByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(containerRepository.findContainersByWorkOrderItemNumber(workOrderItemNumber));
		return result;
	}
	
	@Override
	public Set<String> findContainerNumbersByWorkOrderNumber(String workOrderNumber) throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(containerRepository.findContainerByWorkOrderNumber(workOrderNumber));
		return result;
	}	
}
