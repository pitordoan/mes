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

import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EDictionary_CustomFieldSerializer {
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EDictionary instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setType((MESConstants.Dictionary.Type) streamReader.readObject());
			instance.setLocalizedStrings((Set<ELocalizedString>) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EDictionary instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getDescription());
			streamWriter.writeObject(instance.getType());
			Set<ELocalizedString> strs = instance.getLocalizedStrings();
			if (strs == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<ELocalizedString>(strs));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	