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
import com.cimpoint.mes.client.objects.Dictionary;
import com.cimpoint.mes.common.services.DictionaryService;
import com.cimpoint.mes.common.services.DictionaryServiceAsync;

public class DictionaryController extends AppController {

	private DictionaryServiceAsync dictionaryService;

	public DictionaryController() {
		dictionaryService = DictionaryService.Util.getInstance();
	}
	
	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void findDictionaryByName(String dictionaryName, final CallbackHandler<Dictionary> callback) {
		callback.onSuccess(null); //TODO implement me
	}

}
