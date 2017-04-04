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

import com.cimpoint.mes.common.entities.EWorkInstruction;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/workInstructionService")
public interface WorkInstructionService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static WorkInstructionServiceAsync instance;
		public static WorkInstructionServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(WorkInstructionService.class);
			}
			return instance;
		}
	}
	
	public EWorkInstruction findWorkInstructionById(Long id);
	public EWorkInstruction findWorkInstructionByName(String name) throws Exception;
    public EWorkInstruction createWorkInstruction(String name, MESTrxInfo trxInfo) throws Exception;
    public void saveWorkInstruction(EWorkInstruction wi, MESTrxInfo trxInfo) throws Exception;
}
