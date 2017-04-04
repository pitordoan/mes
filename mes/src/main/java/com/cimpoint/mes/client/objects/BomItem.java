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
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.ESite;

public class BomItem extends ClientObject<EBomItem> implements Named, Persistable {

	public BomItem(EBomItem bomItem) {
		this.entity = bomItem;
//		this.partName = partName;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof BomItem)
            return this.getName().equals(((BomItem) obj).getName()); 
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
//		return this.partName;
		return entity.getPartName();
	}

	public String getRevision() {
		return entity.getPartRevision();
	}
	
	public String getNameRevision() {
		return getName() + " - Rev" + getRevision();
	}
	
	@Override
	public void setName(String name) {
//		this.partName = name;
		entity.setPartName(name);
	}
	
	public Long getPartId() {
		return entity.getPartId();
	}
	
	public Long getContainerPartId() {
		return entity.getContainerPartId();
	}
	
	public String getContainerName() {
		return entity.getContainerName();
	}
	
	public void setContainerName(String name) {
		entity.setContainerName(name);
	}
	
	public String getContainerRevision() {
		return entity.getContainerRevision();
	}
	
	public void setContainerRevision(String revision) {
		entity.setContainerRevision(revision);
	}
	
	public void setBom(Bom bom) {
		EBom eBom = (bom != null)? bom.toEntity() : null;
		entity.setBom(eBom);
	}
	
}
