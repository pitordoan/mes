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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkInstructionServiceAsync {
	public void findWorkInstructionById(Long id, AsyncCallback<EWorkInstruction> callback);
	public void findWorkInstructionByName(String name, AsyncCallback<EWorkInstruction> callback);
    public void createWorkInstruction(String name, MESTrxInfo trxInfo, AsyncCallback<EWorkInstruction> callback);
    public void saveWorkInstruction(EWorkInstruction wi, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
