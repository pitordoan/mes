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

import com.cimpoint.mes.common.entities.ETraveler;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.TravelerService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("travelerService")
public class TravelerServiceImpl extends RemoteServiceServlet implements TravelerService {
	private static final long serialVersionUID = 4482948716258223802L;

	@Override
	public ETraveler findTravelerByWorkOrderNumber(String name)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ETraveler createTraveler(String workOrderNumber, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTraveler(ETraveler traveler, MESTrxInfo trxInfo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
