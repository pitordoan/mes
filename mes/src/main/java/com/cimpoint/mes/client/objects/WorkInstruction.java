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
package com.cimpoint.mes.client.objects;

import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.common.entities.EWorkInstruction;

public class WorkInstruction extends ClientObject<EWorkInstruction> implements Named, Persistable {

	public WorkInstruction(EWorkInstruction e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof WorkInstruction)
            return this.getName().equals(((WorkInstruction) obj).getName()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getName().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getName();
	}
	
	public void setName(String name) {
		entity.setName(name);
	}	

	public List<WorkInstruction> getChildWorkInstructions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMappedState() throws Exception {
		return entity.getMappedState();
	}

	public void setMappedState(String state) {
		entity.setMappedState(state);
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}
}
