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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class ELot_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, ELot instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setNumber(streamReader.readString());
			instance.setOriginalNumber(streamReader.readString());
			instance.setType(MESConstants.Object.LotType.valueOf(streamReader.readString()));
			instance.setWorkOrderNumber(streamReader.readString());
			instance.setWorkOrderItemNumber(streamReader.readString());
			instance.setContainer((EContainer) streamReader.readObject());
			instance.setProcessBatch((EBatch) streamReader.readObject());
			instance.setUnits((Set<EUnit>) streamReader.readObject());
			instance.setLockVersion(streamReader.readLong());
			instance.setQuantity(streamReader.readString());
			instance.setUnitOfMeasure((MESConstants.Object.UnitOfMeasure) streamReader.readObject());
			instance.setMaterialName(streamReader.readString());
			instance.setMaterialRevision(streamReader.readString());
			instance.setCreator(streamReader.readString());
			instance.setCreatedTimeDecoder(streamReader.readString());
			instance.setCreatedTime((Date) streamReader.readObject());
			instance.setFinishedTimeDecoder(streamReader.readString());
			instance.setFinishedTime((Date) streamReader.readObject());
			instance.setClosedTimeDecoder(streamReader.readString());
			instance.setClosedTime((Date) streamReader.readObject());
			instance.setPromisedShipTimeDecoder(streamReader.readString());
			instance.setPromisedShipTime((Date) streamReader.readObject());
			instance.setFinishedTimeDecoder(streamReader.readString());
			instance.setFinishedTime((Date) streamReader.readObject());
			instance.setShippedTimeDecoder(streamReader.readString());
			instance.setShippedTime((Date) streamReader.readObject());
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
			//instance.setTransitionAttributes((ETransitionAttributes) streamReader.readObject());
			instance.setSplittedLots((Set<ELot>) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void serialize(SerializationStreamWriter streamWriter, ELot instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getNumber());
			streamWriter.writeString(instance.getOriginalNumber());
			streamWriter.writeString(instance.getType().toString());
			streamWriter.writeString(instance.getWorkOrderNumber());
			streamWriter.writeString(instance.getWorkOrderItemNumber());
			streamWriter.writeObject(instance.getContainer());
			streamWriter.writeObject(instance.getProcessBatch());			
			Set<EUnit> units = instance.getUnits();
			if (units == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EUnit>(units));
			streamWriter.writeLong(instance.getLockVersion());
			streamWriter.writeString(instance.getQuantity());
			streamWriter.writeObject(instance.getUnitOfMeasure());
			streamWriter.writeString(instance.getMaterialName());
			streamWriter.writeString(instance.getMaterialRevision());
			streamWriter.writeString(instance.getCreator());
			streamWriter.writeString(instance.getCreatedTimeDecoder());
			streamWriter.writeObject(instance.getCreatedTime());
			streamWriter.writeString(instance.getFinishedTimeDecoder());
			streamWriter.writeObject(instance.getFinishedTime());			
			streamWriter.writeString(instance.getClosedTimeDecoder());
			streamWriter.writeObject(instance.getClosedTime());
			streamWriter.writeString(instance.getPromisedShipTimeDecoder());
			streamWriter.writeObject(instance.getPromisedShipTime());
			streamWriter.writeString(instance.getFinishedTimeDecoder());
			streamWriter.writeObject(instance.getFinishedTime());
			streamWriter.writeString(instance.getShippedTimeDecoder());
			streamWriter.writeObject(instance.getShippedTime());
			streamWriter.writeObject(instance.getCustomAttributes());
			//streamWriter.writeObject(instance.getTransitionAttributes());
			streamWriter.writeObject(instance.getSplittedLots());
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}	
}
	