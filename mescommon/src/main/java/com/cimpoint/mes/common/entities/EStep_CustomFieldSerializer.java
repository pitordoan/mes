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

public class EStep_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EStep instance) {
		try {
			try {
				instance.setId(streamReader.readLong());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				instance.setId(id);
			}
			
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setType((MESConstants.Object.StepType) streamReader.readObject());
			instance.setStatus(streamReader.readString());
			instance.setIncomingTransitions((Set<ETransition>) streamReader.readObject());
			instance.setOutgoingTransitions((Set<ETransition>) streamReader.readObject());
			instance.setWorkCenters((Set<EWorkCenter>) streamReader.readObject());			
			instance.setRuleClassName(streamReader.readString());
			instance.setOperation((EOperation) streamReader.readObject());
			instance.setCheckList((ECheckList) streamReader.readObject());
			instance.setRouting((ERouting) streamReader.readObject());			
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EStep instance) {
		try {
			try {
				streamWriter.writeLong(instance.getId());
			}
			catch (Exception ex) {
				Long id = new Long(0);
				streamWriter.writeLong(id);
			}
			
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());	
			streamWriter.writeObject(instance.getType());
			streamWriter.writeString(instance.getStatus());
			
			Set<ETransition> inTrnss = (Set<ETransition>) instance.getIncomingTransitions();
			if (inTrnss == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<ETransition>(inTrnss));
			
			Set<ETransition> outTrnss = (Set<ETransition>) instance.getOutgoingTransitions();
			if (outTrnss == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<ETransition>(outTrnss));	
		
			
			Set<EWorkCenter> wcs = (Set<EWorkCenter>) instance.getWorkCenters();
			if (wcs == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EWorkCenter>(wcs));	
			
			streamWriter.writeString(instance.getRuleClassName());
			streamWriter.writeObject(instance.getOperation());
			streamWriter.writeObject(instance.getCheckList());
			streamWriter.writeObject(instance.getRouting());
			streamWriter.writeObject(instance.getCustomAttributes());				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}