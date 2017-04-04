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
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class ETransition_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, ETransition instance) {
		try {
			try {
				instance.setId(streamReader.readLong());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				instance.setId(id);
			}
			
			instance.setName(streamReader.readString());
			instance.setTransferQuantity(streamReader.readString());
			instance.setTransferUnitOfMeasure((MESConstants.Object.UnitOfMeasure) streamReader.readObject());
			instance.setRoutingName(streamReader.readString());
			instance.setToStep((EStep) streamReader.readObject());
			instance.setFromStep((EStep) streamReader.readObject());	
			
			try {
				instance.setReasonDictionaryId(streamReader.readLong());
			}
			catch (Exception ex) {
				Long reasonDictionaryId = new Long(0);
				instance.setReasonDictionaryId(reasonDictionaryId);				
			}
			
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, ETransition instance) {
		try {
			try {
				streamWriter.writeLong(instance.getId());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getTransferQuantity());	
			streamWriter.writeObject(instance.getTransferUnitOfMeasure());		
			streamWriter.writeString(instance.getRoutingName());
			streamWriter.writeObject(instance.getToStep());
			streamWriter.writeObject(instance.getFromStep());
						
			try {
				streamWriter.writeLong(instance.getReasonDictionaryId());
			}
			catch (Exception ex) {
				Long reasonDictionaryId = new Long(0);
				streamWriter.writeLong(reasonDictionaryId);
			}
			
			streamWriter.writeObject(instance.getCustomAttributes());				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}