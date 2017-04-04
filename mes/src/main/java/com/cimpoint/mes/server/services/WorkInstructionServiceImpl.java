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

import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.entities.EWorkInstruction;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.WorkInstructionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("workInstructionService")
public class WorkInstructionServiceImpl extends RemoteServiceServlet implements WorkInstructionService {
	private static final long serialVersionUID = -7937230469738612922L;

	@Override
	public EWorkInstruction findWorkInstructionById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EWorkInstruction findWorkInstructionByName(String name)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EWorkInstruction createWorkInstruction(String name, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveWorkInstruction(EWorkInstruction wi, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
