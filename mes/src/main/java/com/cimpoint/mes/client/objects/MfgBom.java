/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.objects;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.common.entities.EMfgBom;

public class MfgBom extends ClientObject<EMfgBom> implements Named, Persistable {

	public MfgBom(EMfgBom e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof MfgBom)
            return this.getName().equals(((MfgBom) obj).getName()); 
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

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public Long getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}
	
	public String getDescription() {
		return entity.getDescription();
	}
	
}
