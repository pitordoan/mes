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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EDataSet;

public class DataSet extends ClientObject<EDataSet> implements Named, Persistable {

	public DataSet(EDataSet e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof DataSet)
            return this.getName().equals(((DataSet) obj).getName()); 
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
	
	public String[] getAttributeNames() throws Exception {
		return entity.getAttributeNames().split("|");
	}
	
	public Attribute getAttribute(String name) throws Exception {
		Set<EAttribute> attrs = entity.getAttributes();
		for (EAttribute e: attrs) {
			if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
				return  MESTypeConverter.toClientObject(e, Attribute.class);
			}
		}
		return null;
	}
	
	public void setAttributeValue(String name, Object value) throws Exception {
		EAttribute attr = null;
		Set<EAttribute> attrs = entity.getAttributes();
		if (attrs != null) {
			for (EAttribute e: attrs) {
				if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
					attr = e;
					break;
				}
			}
		}
		
		if (attr != null) {
			String strValue = toStringValue(value);
			attr.setValue(strValue);
		}
		else {
			throw new Exception("Attribute not existed for name: " + name);
		}
	}
	
	public void addAttribute(String name, Object value, CallbackHandler<Attribute> callback) {
		EAttribute attr = null;
		Set<EAttribute> attrs = entity.getAttributes();
		if (attrs != null) {
			for (EAttribute e: attrs) {
				if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
					attr = e;
				}
			}
		}
		
		if (attr == null) {
			try {
				String strValue = this.toStringValue(value);
				String comment = null;
				MESApplication.getMESControllers().getDataCollectionController().createDataAttribute(this.getId(), name, strValue, comment, callback);
			} catch (Exception ex) {
				callback.onFailure(ex);
			}
		}
		else {
			callback.onFailure(new Exception("Attribute already existed for name: " + name));
		}
	}
	
	public Byte getAttributeValueAsByte(String name) throws Exception {
		return getAttributeValue(name, Byte.class);
	}
	
	public Short getAttributeValueAsShort(String name) throws Exception {
		return getAttributeValue(name, Short.class);
	}
	
	public Float getAttributeValueAsFloat(String name) throws Exception {
		return getAttributeValue(name, Float.class);
	}
	
	public Character getAttributeValueAsCharacter(String name) throws Exception {
		return getAttributeValue(name, Character.class);
	}
	
	public Boolean getAttributeValueAsBoolean(String name) throws Exception {
		return getAttributeValue(name, Boolean.class);
	}
	
	public Integer getAttributeValueAsInteger(String name) throws Exception {
		return getAttributeValue(name, Integer.class);
	}
	
	public String getAttributeValueAsString(String name) throws Exception {
		return getAttributeValue(name, String.class);
	}
	
	public Long getAttributeValueAsLong(String name) throws Exception {
		return getAttributeValue(name, Long.class);
	}
	
	public Double getAttributeValueAsDouble(String name) throws Exception {
		return getAttributeValue(name, Double.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getAttributeValue(String name, Class<T> valueClass) throws Exception {
		EAttribute attr = null;
		Set<EAttribute> attrs = entity.getAttributes();
		for (EAttribute e: attrs) {
			if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
				attr = e;
			}
		}
		
		if (attr != null) {
			String strValue = attr.getValue();
		
			if (valueClass == Byte.class) {
				return (T) Byte.valueOf(strValue);
			} else if (valueClass == Short.class) {
				return (T) Short.valueOf(strValue);
			} else if (valueClass == Float.class) {
				return (T) Float.valueOf(strValue);
			} else if (valueClass == Character.class) {
				return (T) Character.valueOf(strValue.charAt(0));
			} else if (valueClass == Boolean.class) {
				return (T) Boolean.valueOf(strValue);
			} else if (valueClass == Integer.class) {
				return (T) Integer.valueOf(strValue);
			} else if (valueClass == String.class) {
				return (T) strValue;
			} else if (valueClass == Long.class) {
				return (T) Long.valueOf(strValue);
			} else if (valueClass == Double.class) {
				return (T) Double.valueOf(strValue);
			} else {
				throw new Exception("Only Primitive types are supported.");
			}
		}
		else {
			throw new Exception("Attribute not existed for name: " + name);
		}
	}
	
	private String toStringValue(Object value) throws Exception {	
		try {
			return String.valueOf(value);
		} catch (Exception ex) {
			throw new Exception("Only primitive data type are supported.");
		}
	}

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getDataCollectionController().saveDataSet(this, comment, callback);
	}
}
