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

import java.util.Set;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.ELayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomCodeServiceAsync {
	//----------------- Application -------------------
	public void createApplication(String name, String version, String category, String description, ELayout layout, TrxInfo trxInfo, AsyncCallback<EApplication> callback);
	public void findApplicationByName(String name, AsyncCallback<EApplication> callback);
	public void saveApplication(EApplication form, TrxInfo trxInfo, AsyncCallback<Void> callback);
	public void findAllApplications(AsyncCallback<Set<EApplication>> callback);
	public void findAllApplicationNames(AsyncCallback<Set<String>> callback);
	public void removeApplication(Long id, TrxInfo trxInfo, AsyncCallback<Void> callback);
	
	//----------------- Form --------------------------	
	public void createForm(String name, Set<EFormField> formFields, TrxInfo trxInfo, AsyncCallback<EForm> callback);
	public void findFormByName(String name, AsyncCallback<EForm> callback);
	public void saveForm(EForm form, TrxInfo trxInfo, AsyncCallback<Void> callback);
	public void findAllForms(AsyncCallback<Set<EForm>> callback);
	public void findAllFormNames(AsyncCallback<Set<String>> callback);
	public void removeForm(Long id, TrxInfo trxInfo, AsyncCallback<Void> callback);
}
