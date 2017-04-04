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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.Quantity;

public class Component extends ClientObject<EComponent> implements Named, Persistable {

	public Component(EComponent e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Component)
            return this.getName().equals(((Component) obj).getName()); 
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

	public Quantity getQuantity() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRevision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}
}

