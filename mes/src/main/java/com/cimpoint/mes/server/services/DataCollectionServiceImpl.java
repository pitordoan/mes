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
package com.cimpoint.mes.server.services;

import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EDataSet;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.DataCollectionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("dataCollectionService")
public class DataCollectionServiceImpl extends RemoteServiceServlet implements DataCollectionService {
	private static final long serialVersionUID = 1433249274707794723L;

	@Override
	public EDataSet findDataSetByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EDataSet createDataSet(String name, String[] attributeNames, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EDataSet createDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType, MESTrxInfo trxInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EAttribute createDataAttribute(Long dataSetId, String attributeName, String value, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDataSet(EDataSet dataSet, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAttribute(EAttribute attribute, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAttribute(EAttribute attribute) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
