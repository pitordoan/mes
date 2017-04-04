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

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EPart_CustomFieldSerializer {
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EPart instance) {
		try {
			instance.setId(streamReader.readLong());			
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setRevision(streamReader.readString());
			instance.setQuantity(streamReader.readString());
			instance.setUnitOfMeasure((MESConstants.Object.UnitOfMeasure) streamReader.readObject());
			instance.setCategory((MESConstants.Object.PartCatergory) streamReader.readObject());
			instance.setStartDate(streamReader.readString());
			instance.setEndDate(streamReader.readString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EPart instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());
			streamWriter.writeString(instance.getRevision());
			streamWriter.writeString(instance.getQuantity());
			streamWriter.writeObject(instance.getUnitOfMeasure());
			streamWriter.writeObject(instance.getCategory());
			
			streamWriter.writeString(instance.getStartDate());
			streamWriter.writeString(instance.getEndDate());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
