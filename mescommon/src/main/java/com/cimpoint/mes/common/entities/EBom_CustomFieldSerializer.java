package com.cimpoint.mes.common.entities;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EBom_CustomFieldSerializer {

	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EBom instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setRevision(streamReader.readString());
			instance.setStartDate(streamReader.readString());
			instance.setEndDate(streamReader.readString());
			instance.setBomItems((Set<EBomItem>)streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, EBom instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());	
			streamWriter.writeString(instance.getDescription());	
			streamWriter.writeString(instance.getRevision());
			streamWriter.writeString(instance.getStartDate());
			streamWriter.writeString(instance.getEndDate());
			
			Set<EBomItem> bomItems = instance.getBomItems();
			if (bomItems == null) {
				streamWriter.writeObject(null);
			} else {
				streamWriter.writeObject(new HashSet<EBomItem>(bomItems));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
