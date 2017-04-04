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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/customCodeService")
public interface CustomCodeService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static CustomCodeServiceAsync instance;
		public static CustomCodeServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(CustomCodeService.class);
			}
			return instance;
		}
	}
	
	//----------------- Application -------------------
	public EApplication createApplication(String name, String version, String category, String description, ELayout layout, TrxInfo trxInfo) throws Exception;
	public EApplication findApplicationByName(String name) throws Exception;
	public void saveApplication(EApplication form, TrxInfo trxInfo) throws Exception;
	public Set<EApplication> findAllApplications() throws Exception;
	public Set<String> findAllApplicationNames() throws Exception;
	public void removeApplication(Long id, TrxInfo trxInfo) throws Exception;
	
	//----------------- Form --------------------------	
	public EForm createForm(String name, Set<EFormField> formFields, TrxInfo trxInfo) throws Exception;
	public EForm findFormByName(String name) throws Exception;
	public void saveForm(EForm form, TrxInfo trxInfo) throws Exception;
	public Set<EForm> findAllForms() throws Exception;
	public Set<String> findAllFormNames() throws Exception;
	public void removeForm(Long id, TrxInfo trxInfo) throws Exception;
}
