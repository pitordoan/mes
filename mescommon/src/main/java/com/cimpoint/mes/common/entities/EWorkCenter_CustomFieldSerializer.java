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
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EWorkCenter_CustomFieldSerializer {
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EWorkCenter instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setArea((EArea) streamReader.readObject());
			instance.setProductionLine((EProductionLine) streamReader.readObject());
			instance.setEquipments((Set<EEquipment>) streamReader.readObject());
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EWorkCenter instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());
			streamWriter.writeObject(instance.getArea());
			streamWriter.writeObject(instance.getProductionLine());
			Set<EEquipment> equipments = instance.getEquipments();
			if (equipments == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EEquipment>(equipments));
			streamWriter.writeObject(instance.getCustomAttributes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	