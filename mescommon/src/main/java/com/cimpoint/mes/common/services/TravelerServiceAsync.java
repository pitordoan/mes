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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TravelerServiceAsync {
	public void findTravelerByWorkOrderNumber(String name, AsyncCallback<ETraveler> callback);
    public void createTraveler(String workOrderNumber, MESTrxInfo trxInfo, AsyncCallback<ETraveler> callback);    
    public void saveTraveler(ETraveler traveler, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
