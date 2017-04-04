package com.cimpoint.mes.common.entities;

import java.util.Date;

import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class EWorkOrderItem_CustomFieldSerializer {

	@SuppressWarnings("unchecked")
	public static void deserialize(SerializationStreamReader streamReader, EWorkOrderItem instance) {
		try {
			instance.setId(streamReader.readLong());
			instance.setNumber(streamReader.readString());
			instance.setLockVersion(streamReader.readLong());
			instance.setPartNumber(streamReader.readString());
			instance.setPartRevision(streamReader.readString());
			instance.setWorkOrder((EWorkOrder) streamReader.readObject());
			instance.setTrxAttributes((ETrxAttributes) streamReader.readObject());
			instance.setQuantity(streamReader.readString());
			instance.setUnitOfMeasure((MESConstants.Object.UnitOfMeasure) streamReader.readObject());
			instance.setPromisedShipTime((Date) streamReader.readObject());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void serialize(SerializationStreamWriter streamWriter, EWorkOrderItem instance) {
		try {
			streamWriter.writeLong(instance.getId());
			streamWriter.writeString(instance.getNumber());
			streamWriter.writeLong(instance.getLockVersion());
			streamWriter.writeString(instance.getPartNumber());
			streamWriter.writeString(instance.getPartRevision());
			streamWriter.writeObject(instance.getWorkOrder());
			streamWriter.writeObject(instance.getAtrxAttributes());
			streamWriter.writeObject(instance.getQuantity());
			streamWriter.writeObject(instance.getUnitOfMeasure());
			streamWriter.writeObject(instance.getPromisedShipTime());
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}	
}
