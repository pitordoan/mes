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
import com.cimpoint.mes.common.MESConstants.Object.PartCatergory;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.Quantity;

public class Part extends ClientObject<EPart> implements Named, Persistable {

	public Part(EPart e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Part)
            return this.getName().equals(((Part) obj).getName()); 
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

	public String getRevision() {
		return entity.getRevision();
	}

	public void setRevision(String revision) {
		entity.setRevision(revision);
	}

	public String getDescription() {
		return entity.getDescription();
	}
	
	public void setDescription(String description) {
		entity.setDescription(description);
	}
	
	public String getUoM(){
		if(entity.getUnitOfMeasure() == null)
			return "";
		return entity.getUnitOfMeasure().toString();
	}
	
	/*public Bom getBom() throws Exception {
		return this.getAppController().getPartRecipeController().getBom();
	}*/

	public Part getParentPart() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Part> getChildParts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Quantity getQuantity() {
		return new Quantity(entity.getQuantity(), entity.getUnitOfMeasure());
	}

	public void setQuantity(Quantity quantity) {
		entity.setQuantity(quantity.asString());
	}

	public String getEffectiveStartDate() {
		return entity.getStartDate();
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		entity.setStartDate(effectiveStartDate);
	}

	public String getEffectiveEndDate() {
		return entity.getEndDate();
	}

	public void setEffectiveEndDate(String effectiveEndDate) {
		entity.setEndDate(effectiveEndDate);
	}

	public boolean isCurrent() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEffective() {
		// TODO Auto-generated method stub
		return false;
	}

	public Step getConsumingStep() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}

	public PartCatergory getCategory() {
		return entity.getCategory();
	}
	
	public void setCategory(PartCatergory category) {
		entity.setCategory(category);
	}
}
