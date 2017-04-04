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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/dataCollectionService")
public interface DataCollectionService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static DataCollectionServiceAsync instance;
		public static DataCollectionServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(DataCollectionService.class);
			}
			return instance;
		}
	}
	
	public EDataSet findDataSetByName(String name) throws Exception;
	public EDataSet createDataSet(String name, String[] attributeNames, MESTrxInfo trxInfo) throws Exception;
    public EDataSet createDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType, MESTrxInfo trxInfo) throws Exception;
    public EAttribute createDataAttribute(Long dataSetId, String attributeName, String value, MESTrxInfo trxInfo) throws Exception;
    public void saveDataSet(EDataSet dataSet, MESTrxInfo trxInfo) throws Exception;
    public void saveAttribute(EAttribute attribute) throws Exception;
	public void saveAttribute(EAttribute attribute, MESTrxInfo trxInfo) throws Exception;
}
