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

import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EBomItem_CustomFieldSerializer {
	
	public static void deserialize(SerializationStreamReader streamReader, EBomItem instance) {
		try {
			try {
				instance.setId(streamReader.readLong());
			} catch (Exception e) {
				Long id = new Long(0);
				instance.setId(id);
			}
			
			try {
				instance.setContainerPartId(streamReader.readLong());
			} catch (Exception e) {
				Long id = new Long(0);
				instance.setContainerPartId(id);
			}
			
			instance.setPartId(streamReader.readLong());
			instance.setPartRevision(streamReader.readString());
			instance.setContainerRevision(streamReader.readString());
			instance.setPartName(streamReader.readString());
			instance.setContainerName(streamReader.readString());
			instance.setBom((EBom)streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EBomItem instance) {
		try {
			try {
				streamWriter.writeLong(instance.getId());
			} catch (Exception e) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			try {
				streamWriter.writeLong(instance.getContainerPartId());
			} catch (Exception e) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			streamWriter.writeLong(instance.getPartId());
			streamWriter.writeString(instance.getPartRevision());
			streamWriter.writeString(instance.getContainerRevision());
			streamWriter.writeString(instance.getPartName());
			streamWriter.writeString(instance.getContainerName());
			streamWriter.writeObject(instance.getBom());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
