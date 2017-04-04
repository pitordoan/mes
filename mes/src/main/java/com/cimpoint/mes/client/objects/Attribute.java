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
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.common.entities.EAttribute;

public class Attribute extends ClientObject<EAttribute> implements Named, Persistable {

	public Attribute(EAttribute e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Attribute)
            return this.getName().equals(((Attribute) obj).getName()); 
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
		
	public String getName() {
		return entity.getName();
	}

	public void setName(String name) {
		entity.setName(name);
	}

	public String getValue() {
		return entity.getValue();
	}

	public void setValue(String value) {
		entity.setValue(value);
	}
	
	public Byte getValueAsByte() throws Exception {
		return Byte.valueOf(getValue());
	}
	
	public Short getValueAsShort() throws Exception {
		return Short.valueOf(getValue());
	}
	
	public Float getValueAsFloat() throws Exception {
		return Float.valueOf(getValue());
	}
	
	public Character getValueAsCharacter() throws Exception {
		String value = getValue();
		if (value != null && value.length() > 0)
			return Character.valueOf(value.charAt(0));
		else
			throw new Exception("Can't convert value to Character type");
	}
	
	public Boolean getValueAsBoolean() throws Exception {
		return Boolean.valueOf(getValue());
	}
	
	public Integer getValueAsInteger() throws Exception {
		return Integer.valueOf(getValue());
	}
	
	public String getValueAsString() throws Exception {
		return String.valueOf(getValue());
	}
	
	public Long getValueAsLong() throws Exception {
		return Long.valueOf(getValue());
	}
	
	public Double getValueAsDouble() throws Exception {
		return Double.valueOf(getValue());
	}

	public Long getId() {
		return entity.getId();
	}
	
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getDataCollectionController().saveAttribute(this, comment, callback);
	}
}
