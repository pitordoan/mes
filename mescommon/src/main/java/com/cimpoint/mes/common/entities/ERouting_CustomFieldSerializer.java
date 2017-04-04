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

public class ERouting_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, ERouting instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());	
			instance.setStartStepId(streamReader.readLong());
			instance.setEndStepId(streamReader.readLong());
			instance.setSteps((Set<EStep>) streamReader.readObject());	
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, ERouting instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());
			
			Long entryStepId = (instance.getStartStepId() != null)? instance.getStartStepId() : new Long(0);
			streamWriter.writeLong(entryStepId);
			
			Long exitStepId = (instance.getStartStepId() != null)? instance.getEndStepId() : new Long(0);
			streamWriter.writeLong(exitStepId);
			
			Set<EStep> steps = (Set<EStep>) instance.getSteps();
			if (steps == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EStep>(steps));
			streamWriter.writeObject(instance.getCustomAttributes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}