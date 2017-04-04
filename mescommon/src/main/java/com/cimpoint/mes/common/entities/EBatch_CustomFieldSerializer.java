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
import com.cimpoint.common.entities.ETrxAttributes;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EBatch_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EBatch instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setLockVersion(streamReader.readLong());
			instance.setWorkOrderNumber(streamReader.readString());
			instance.setWorkOrderItemNumber(streamReader.readString());
			instance.setContainer((EContainer) streamReader.readObject());
			instance.setLots((Set<ELot>) streamReader.readObject());
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
			//instance.setTrxAttributes((ETrxAttributes) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EBatch instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeLong(instance.getLockVersion());
			streamWriter.writeString(instance.getWorkOrderNumber());
			streamWriter.writeString(instance.getWorkOrderItemNumber());
			streamWriter.writeObject(instance.getContainer());
			Set<ELot> lots = instance.getLots();
			if (lots == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<ELot>(lots));
			streamWriter.writeObject(instance.getCustomAttributes());
			//streamWriter.writeObject(instance.getTrxAttributes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
