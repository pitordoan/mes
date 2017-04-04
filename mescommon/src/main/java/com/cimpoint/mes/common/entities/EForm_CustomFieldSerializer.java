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

public class EForm_CustomFieldSerializer {
	
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EForm instance) {
		try {
			Long id = streamReader.readLong();
			if (id == 0L) id = null;
			instance.setId(id);
			instance.setName(streamReader.readString());
			instance.setFormFields((Set<EFormField>) streamReader.readObject());
			instance.setLayout((ELayout) streamReader.readObject());
			instance.setModuleName(streamReader.readString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EForm instance) {
		try {
			Long id = instance.getId();
			if (id == null) id = 0L;
			streamWriter.writeLong(id);
			streamWriter.writeString(instance.getName());
			
			Set<EFormField> formFields = instance.getFormFields();
			if (formFields == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EFormField>(formFields));
			
			streamWriter.writeObject(instance.getLayout());
			streamWriter.writeString(instance.getModuleName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	