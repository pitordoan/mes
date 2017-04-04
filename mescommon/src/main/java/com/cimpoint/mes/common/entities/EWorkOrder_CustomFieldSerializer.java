package com.cimpoint.mes.common.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EWorkOrder_CustomFieldSerializer {

	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EWorkOrder instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setNumber(streamReader.readString());
			instance.setLockVersion(streamReader.readLong());
			instance.setItems((Set<EWorkOrderItem>) streamReader.readObject());
			instance.setTrxAttributes((ETrxAttributes)streamReader.readObject());
			instance.setQuantity(streamReader.readString());
			instance.setUnitOfMeasure(MESConstants.Object.UnitOfMeasure.valueOf(streamReader.readString()));
			instance.setPromisedShipTime((Date) streamReader.readObject());
			instance.setCustomAttributes((CustomAttributes) streamReader.readObject());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void serialize(SerializationStreamWriter streamWriter, EWorkOrder instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getNumber());
			streamWriter.writeLong(instance.getLockVersion());
			Set<EWorkOrderItem> items = instance.getItems();
			if (items == null) streamWriter.writeObject(null);
			else streamWriter.writeObject(new HashSet<EWorkOrderItem>(items));
			streamWriter.writeObject(instance.getAtrxAttributes());
			streamWriter.writeString(instance.getQuantity().toString());
			streamWriter.writeString(instance.getUnitOfMeasure().toString());
			streamWriter.writeObject(instance.getPromisedShipTime());
			streamWriter.writeObject(instance.getCustomAttributes());
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}	
}
