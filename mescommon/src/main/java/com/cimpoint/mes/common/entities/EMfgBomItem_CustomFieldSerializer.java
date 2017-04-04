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
package com.cimpoint.mes.common.entities;

import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EMfgBomItem_CustomFieldSerializer {
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EMfgBomItem instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setContainerPartId(streamReader.readLong());
			instance.setContainerName(streamReader.readString());
			instance.setContainerRevision(streamReader.readString());
			instance.setPartId(streamReader.readLong());
			instance.setPartName(streamReader.readString());
			instance.setPartRevision(streamReader.readString());
			instance.setConsumptionType((MESConstants.Consumption.Type)streamReader.readObject());
			instance.setConsumptionRoutingId(streamReader.readLong());
			instance.setConsumptionRoutingName(streamReader.readString());
			instance.setConsumptionRoutingRevision(streamReader.readString());
			instance.setConsumptionStepId(streamReader.readLong());
			instance.setConsumptionStepName(streamReader.readString());
			instance.setConsumptionQuantity(streamReader.readString());
			instance.setConsumptionUnitOfMeasure(streamReader.readString());
			instance.setProduceRoutingId(streamReader.readLong());
			instance.setProduceRoutingName(streamReader.readString());
			instance.setProduceRoutingRevision(streamReader.readString());
			instance.setProduceStepId(streamReader.readLong());
			instance.setProduceStepName(streamReader.readString());
			instance.setProduceQuantity(streamReader.readString());
			instance.setProduceUnitOfMeasure(streamReader.readString());
			instance.setMfgBom((EMfgBom)streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EMfgBomItem instance) {
		try {
			
			streamWriter.writeLong(instance.getId());
			streamWriter.writeLong(instance.getContainerPartId());
			streamWriter.writeString(instance.getContainerName());
			streamWriter.writeString(instance.getContainerRevision());
			streamWriter.writeLong(instance.getPartId());
			streamWriter.writeString(instance.getPartName());
			streamWriter.writeString(instance.getPartRevision());
			streamWriter.writeObject(instance.getConsumptionType());
			streamWriter.writeLong(instance.getConsumptionRoutingId());
			streamWriter.writeString(instance.getConsumptionRoutingName());
			streamWriter.writeString(instance.getConsumptionRoutingRevision());
			streamWriter.writeLong(instance.getConsumptionStepId());
			streamWriter.writeString(instance.getConsumptionStepName());
			streamWriter.writeString(instance.getConsumptionQuantity());
			streamWriter.writeString(instance.getConsumptionUnitOfMeasure());
			streamWriter.writeLong(instance.getProduceRoutingId());
			streamWriter.writeString(instance.getProduceRoutingName());
			streamWriter.writeString(instance.getProduceRoutingRevision());
			streamWriter.writeLong(instance.getProduceStepId());
			streamWriter.writeString(instance.getProduceStepName());
			streamWriter.writeString(instance.getProduceQuantity());
			streamWriter.writeString(instance.getProduceUnitOfMeasure());
			streamWriter.writeObject(instance.getMfgBom());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
