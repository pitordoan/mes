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

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.ELayout;
import com.cimpoint.mes.common.services.CustomCodeService;
import com.cimpoint.mes.server.repositories.ApplicationRepository;
import com.cimpoint.mes.server.repositories.FormRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("customCodeService")
public class CustomCodeServiceImpl extends RemoteServiceServlet implements CustomCodeService {

	private static final long serialVersionUID = 5940211930049229974L;

	@Autowired
	private ApplicationRepository appRep;
	
	@Autowired
	private FormRepository formRep;

	@PostConstruct
	public void initialize() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	//------------------- Application -----------------------
	@Override
	public EApplication createApplication(String name, String version, String category, String description, ELayout layout, TrxInfo trxInfo) throws Exception {
		return appRep.createApplication(name, version, category, description, layout, trxInfo);
	}

	@Override
	public EApplication findApplicationByName(String name) throws Exception {
		return appRep.findByName(name);
	}

	@Override
	public void saveApplication(EApplication form, TrxInfo trxInfo) throws Exception {
		appRep.update(form, trxInfo);
	}

	@Override
	public Set<EApplication> findAllApplications() throws Exception {
		return appRep.findAll();
	}

	@Override
	public Set<String> findAllApplicationNames() throws Exception {
		return appRep.findAllNames();
	}

	@Override
	public void removeApplication(Long id, TrxInfo trxInfo) throws Exception {
		appRep.remove(id, trxInfo);
	}
	
	//--------------------- Form --------------------------
	@Override
	public EForm createForm(String name, Set<EFormField> formFields, TrxInfo trxInfo) throws Exception {
		return formRep.createForm(name, formFields, trxInfo);
	}

	@Override
	public EForm findFormByName(String name) throws Exception {
		return formRep.findByName(name);
	}

	@Override
	public void saveForm(EForm form, TrxInfo trxInfo) throws Exception {
		formRep.update(form, trxInfo);
	}

	@Override
	public Set<EForm> findAllForms() throws Exception {
		return formRep.findAll();
	}
	
	@Override
	public Set<String> findAllFormNames() throws Exception {
		return formRep.findAllNames();
	}

	@Override
	public void removeForm(Long id, TrxInfo trxInfo) throws Exception {
		formRep.remove(id, trxInfo);
	}
}
