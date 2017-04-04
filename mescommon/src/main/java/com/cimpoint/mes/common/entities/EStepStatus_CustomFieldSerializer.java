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

import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EStepStatus_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EStepStatus instance) {
		try {
			try {
				instance.setId(streamReader.readLong());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				instance.setId(id);
			}
			
			instance.setName(streamReader.readString());
			instance.setStarting(streamReader.readBoolean());
			instance.setEnding(streamReader.readBoolean());
			instance.setNextDefaultStatusName(streamReader.readString());	
			instance.setSteps((Set<EStep>) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EStepStatus instance) {
		try {
			try {
				streamWriter.writeLong(instance.getId());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			streamWriter.writeString(instance.getName());
			streamWriter.writeBoolean(instance.isStarting());	
			streamWriter.writeBoolean(instance.isEnding());
			
			Set<EStep> steps = (Set<EStep>) instance.getSteps();
			if (steps == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EStep>(steps));			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}