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

public class EFormField_CustomFieldSerializer {
		
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EFormField instance) {
		try {
			Long id = streamReader.readLong();
			if (id == 0L) id = null;
			instance.setId(id);
			instance.setName(streamReader.readString());
			instance.setLabel(streamReader.readString());
			instance.setForm((EForm) streamReader.readObject());
			instance.setColumnSpan(streamReader.readInt());
			instance.setStartNewRow(streamReader.readBoolean());
			instance.setEndCurrentRow(streamReader.readBoolean());	
			instance.setFieldType(Constants.Form.FieldType.valueOf(streamReader.readString()));
			instance.setFormFieldProperties((Set<EFormFieldProperty>) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EFormField instance) {
		try {
			Long id = instance.getId();
			if (id == null) id = 0L;
			streamWriter.writeLong(id);
			streamWriter.writeString(instance.getName());
			streamWriter.writeString(instance.getLabel());
			streamWriter.writeObject(instance.getForm());			
			streamWriter.writeInt(instance.getColumnSpan());
			streamWriter.writeBoolean(instance.getStartNewRow());
			streamWriter.writeBoolean(instance.getEndCurrentRow());			
			streamWriter.writeString(instance.getFieldType().toString());
			
			Set<EFormFieldProperty> formFieldProps = instance.getFormFieldProperties();
			if (formFieldProps == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EFormFieldProperty>(formFieldProps));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	