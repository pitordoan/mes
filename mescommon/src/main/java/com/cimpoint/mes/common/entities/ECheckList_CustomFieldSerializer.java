package com.cimpoint.mes.common.entities;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class ECheckList_CustomFieldSerializer {
	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, ECheckList instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setName(streamReader.readString());
			instance.setDescription(streamReader.readString());
			instance.setResult(streamReader.readInt());
			instance.setComment(streamReader.readString());
			instance.setParentCheckList((ECheckList)streamReader.readObject());
			instance.setChildCheckLists((Set<ECheckList>)streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void serialize(SerializationStreamWriter streamWriter, ECheckList instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getName());	
			streamWriter.writeString(instance.getDescription());	
			streamWriter.writeInt(instance.getResult());
			streamWriter.writeString(instance.getComment());
			streamWriter.writeObject(instance.getParentCheckList());
			
			Set<ECheckList> childCheckLists = (Set<ECheckList>) instance.getChildCheckLists();
			if (childCheckLists == null) 
				streamWriter.writeObject(null);
			else 
				streamWriter.writeObject(new HashSet<ECheckList>(childCheckLists));
					
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
