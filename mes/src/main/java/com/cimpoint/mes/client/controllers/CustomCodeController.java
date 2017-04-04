/**
 * 
 */
package com.cimpoint.mes.client.controllers;

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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.services.CustomCodeService;
import com.cimpoint.mes.common.services.CustomCodeServiceAsync;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

public class CustomCodeController extends AppController {

	private CustomCodeServiceAsync customCodeService;

	public CustomCodeController() {
		customCodeService = CustomCodeService.Util.getInstance();
	}

	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void findAllApplications(final CallbackHandler<Set<EApplication>> callback) {
		customCodeService.findAllApplications(new CallbackHandler<Set<EApplication>>() {
			@Override
			public void onSuccess(Set<EApplication> result) {
				callback.onSuccess(result);
			}
		});
	}

	public void findAllApplicationNames(final CallbackHandler<Set<String>> callback) {
		customCodeService.findAllApplicationNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> appNames) {
				callback.onSuccess(appNames);
			}
		});
	}

	public void findAllForms(final CallbackHandler<Set<EForm>> callback) {
		customCodeService.findAllForms(new CallbackHandler<Set<EForm>>() {
			@Override
			public void onSuccess(Set<EForm> forms) {
				callback.onSuccess(forms);
			}
		});
	}

	public void loadFormModule(String moduleName, final CallbackHandler<Canvas> callback) {
		RPCRequest request = new RPCRequest();
		request.setEvalResult(true);
		request.setData(moduleName);
		request.setActionURL("/CustomCodeServlet?action=GetModule");
		request.setHttpMethod("GET");
		request.setShowPrompt(false);
		
		RPCManager.sendRequest(request, new RPCCallback() {
			public void execute(RPCResponse response, Object rawData, RPCRequest request) {
				callback.onSuccess((Canvas) rawData);
			}
		});
	}
}
