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

import com.cimpoint.common.Constants;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class ELayout_CustomFieldSerializer {	
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, ELayout instance) {
		try {
			Long id = streamReader.readLong();
			if (id == 0L) id = null;
			instance.setId(id);
			instance.setLayoutType(Constants.Form.LayoutType.valueOf(streamReader.readString()));
			instance.setForms((Set<EForm>) streamReader.readObject());
			instance.setParentLayout((ELayout) streamReader.readObject());
			instance.setChildLayouts((Set<ELayout>) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, ELayout instance) {
		try {
			Long id = instance.getId();
			if (id == null) id = 0L;
			streamWriter.writeLong(id);
			streamWriter.writeString(instance.getLayoutType().toString());
			
			Set<EForm> forms = instance.getForms();
			if (forms == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EForm>(forms));
			
			streamWriter.writeObject(instance.getParentLayout());	
			
			Set<ELayout> childLayouts = instance.getChildLayouts();
			if (childLayouts == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<ELayout>(childLayouts));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	