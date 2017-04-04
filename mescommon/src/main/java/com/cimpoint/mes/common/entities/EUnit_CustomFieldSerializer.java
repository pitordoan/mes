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

package com.cimpoint.mes.common.entities;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EUnit_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EUnit instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setSerialNumber(streamReader.readString());
			instance.setWorkOrderNumber(streamReader.readString());
			instance.setWorkOrderItemNumber(streamReader.readString());
			instance.setLot((ELot) streamReader.readObject());
			instance.setParentUnit((EUnit) streamReader.readObject());
			instance.setContainer((EContainer) streamReader.readObject());
			instance.setChildUnits((Set<EUnit>) streamReader.readObject());
			instance.setLockVersion(streamReader.readLong());
			instance.setPartNumber(streamReader.readString());
			instance.setPartRevision(streamReader.readString());
			instance.setLocationId(streamReader.readLong());
			instance.setStatus(streamReader.readString());
			instance.setState(streamReader.readString());
			instance.setCategory(streamReader.readString());
			instance.setType(MESConstants.Object.UnitType.valueOf(streamReader.readString()));
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void serialize(SerializationStreamWriter streamWriter, EUnit instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getSerialNumber());
			streamWriter.writeString(instance.getWorkOrderNumber());
			streamWriter.writeString(instance.getWorkOrderItemNumber());
			streamWriter.writeObject(instance.getLot());
			streamWriter.writeObject(instance.getParentUnit());
			streamWriter.writeObject(instance.getContainer());
			Set<EUnit> units = (Set<EUnit>) instance.getChildUnits();
			if (units == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EUnit>(units));
			
//			streamWriter.writeLong(instance.getLockVersion());
			try {
				streamWriter.writeLong(instance.getLockVersion());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			streamWriter.writeString(instance.getPartNumber());
			streamWriter.writeString(instance.getPartRevision());
			
//			streamWriter.writeLong(instance.getLocationId());
			
			try {
				streamWriter.writeLong(instance.getLocationId());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			streamWriter.writeString(instance.getStatus());
			streamWriter.writeString(instance.getState());
			streamWriter.writeString(instance.getCategory());
			streamWriter.writeString(instance.getType().toString());
			streamWriter.writeObject(instance.getCustomAttributes());
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}
}