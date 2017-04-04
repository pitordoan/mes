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

import com.cimpoint.mes.common.entities.ETraveler;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/travelerService")
public interface TravelerService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TravelerServiceAsync instance;
		public static TravelerServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TravelerService.class);
			}
			return instance;
		}
	}
	
	public ETraveler findTravelerByWorkOrderNumber(String name) throws Exception;
    public ETraveler createTraveler(String workOrderNumber, MESTrxInfo trxInfo) throws Exception;    
    public void saveTraveler(ETraveler traveler, MESTrxInfo trxInfo) throws Exception;
}
