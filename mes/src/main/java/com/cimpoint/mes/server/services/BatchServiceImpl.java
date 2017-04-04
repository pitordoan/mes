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

import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.BatchService;
import com.cimpoint.mes.server.repositories.BatchRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("batchService")
public class BatchServiceImpl extends RemoteServiceServlet implements BatchService {
	private static final long serialVersionUID = -2358078047769719583L;

	@Autowired
	private BatchRepository batchRepository;

	@PostConstruct
	public void initialize() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public EBatch findBatchByName(String name) throws Exception {
		return batchRepository.findByName(name);
	}

	@Override
	public EBatch createBatch(String name, EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception {
		return batchRepository.createBatch(name, trxInfo);
	}

	@Override
	public void saveBatch(EBatch batch, MESTrxInfo trxInfo) throws Exception {
		batchRepository.create(batch, trxInfo);
	}

	@Override
	public Set<String> findBatchNumbersByWorkOrderItemNumber(
			String workOrderItemNumber) throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(batchRepository.findBatchsByWorkOrderItemNumber(workOrderItemNumber));
		return result;
	}

	@Override
	public Set<String> findBatchNumbersByWorkOrderNumber(String workOrderNumber)
			throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(batchRepository.findBatchByWorkOrderNumber(workOrderNumber));
		return result;
	}
}
