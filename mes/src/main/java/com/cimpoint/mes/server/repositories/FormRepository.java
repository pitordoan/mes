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
package com.cimpoint.mes.server.repositories;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;

@Repository("formRepository")
public class FormRepository extends JpaRepository<Long, EForm> {
		
	@Autowired
	private EntityManager entityManager;
		
	public FormRepository() {
		super(EForm.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public EForm createForm(String name, Set<EFormField> formFields, TrxInfo trxInfo) throws Exception {
		EForm e = new EForm(name, formFields);			
		this.create(e, trxInfo);
		return e;
	}
}
