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

public class EProductionLine_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EProductionLine instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setArea((EArea) streamReader.readObject());
			instance.setWorkCenters((Set<EWorkCenter>) streamReader.readObject());
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EProductionLine instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());
			streamWriter.writeObject(instance.getArea());
			Set<EWorkCenter> wcs = instance.getWorkCenters();
			if (wcs == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EWorkCenter>(wcs));
			streamWriter.writeObject(instance.getCustomAttributes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	