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
package com.cimpoint.mes.client.controllers;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.objects.Attribute;
import com.cimpoint.mes.client.objects.DataSet;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EDataSet;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.DataCollectionService;
import com.cimpoint.mes.common.services.DataCollectionServiceAsync;

public class DataCollectionController extends AppController {

	private DataCollectionServiceAsync dataCollectionService = DataCollectionService.Util.getInstance();
	
	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void findDataSetByName(String name, CallbackHandler<DataSet> callback) {
		this.dataCollectionService.findDataSetByName(name, new MESTypeConverter.EntityToClientObjectCallback<EDataSet, DataSet>(DataSet.class, callback));
	}
	
	public void createDataSet(String name, String[] attributeNames, String comment, CallbackHandler<DataSet> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.dataCollectionService.createDataSet(name, attributeNames, trxInfo, 
				new MESTypeConverter.EntityToClientObjectCallback<EDataSet, DataSet>(DataSet.class, callback));
	}
	
    public void createDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType, 
    		String comment, CallbackHandler<DataSet> callback) {
    	MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.dataCollectionService.createDataSet(name, attributeNames, associatedObjectId, associatedObjectType, trxInfo, 
				new MESTypeConverter.EntityToClientObjectCallback<EDataSet, DataSet>(DataSet.class, callback));
    }
    
    public void createDataAttribute(Long dataSetId, String name, String value, String comment, CallbackHandler<Attribute> callback) {
    	MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.dataCollectionService.createDataAttribute(dataSetId, name, value, trxInfo, 
				new MESTypeConverter.EntityToClientObjectCallback<EAttribute, Attribute>(Attribute.class, callback));
    }
    
    public void saveDataSet(DataSet dataSet, String comment, CallbackHandler<Void> callback) {
    	EDataSet e = dataSet.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.dataCollectionService.saveDataSet(e, trxInfo, callback);
    }
    
	public void saveAttribute(Attribute attribute, String comment, CallbackHandler<Void> callback) {
		EAttribute e = attribute.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.dataCollectionService.saveAttribute(e, trxInfo, callback);
	}
}
