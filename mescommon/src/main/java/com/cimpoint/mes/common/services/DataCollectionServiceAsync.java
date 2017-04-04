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
package com.cimpoint.mes.common.services;

import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EDataSet;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataCollectionServiceAsync {
	public void findDataSetByName(String name, AsyncCallback<EDataSet> callback);
	public void createDataSet(String name, String[] attributeNames, MESTrxInfo trxInfo, AsyncCallback<EDataSet> callback);
    public void createDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType, MESTrxInfo trxInfo, AsyncCallback<EDataSet> callback);
    public void createDataAttribute(Long dataSetId, String attributeName, String value, MESTrxInfo trxInfo, AsyncCallback<EAttribute> callback);
    public void saveDataSet(EDataSet dataSet, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void saveAttribute(EAttribute attribute, AsyncCallback<Void> callback);
	public void saveAttribute(EAttribute attribute, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
