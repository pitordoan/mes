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

import com.cimpoint.common.Constants;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EFormFieldProperty_CustomFieldSerializer {
	
	public static void deserialize(SerializationStreamReader streamReader, EFormFieldProperty instance) {
		try {
			Long id = streamReader.readLong();
			if (id == 0L) id = null;
			instance.setId(id);
			instance.setProperty(Constants.Form.FieldProperty.valueOf(streamReader.readString()));
			instance.setValue(streamReader.readString());
			instance.setCustomCodeClassPath(streamReader.readString());
			instance.setCustomCodeFunctionName(streamReader.readString());
			instance.setFormField((EFormField) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EFormFieldProperty instance) {
		try {
			Long id = instance.getId();
			if (id == null) id = 0L;
			streamWriter.writeLong(id);
			streamWriter.writeString(instance.getProperty().toString());
			streamWriter.writeString(instance.getValue());
			streamWriter.writeString(instance.getCustomCodeClassPath());
			streamWriter.writeString(instance.getCustomCodeFunctionName());
			streamWriter.writeObject(instance.getFormField());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
	