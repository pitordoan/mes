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
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.EFormFieldProperty;
import com.cimpoint.mes.common.entities.ELayout;

@Repository("applicationRepository")
public class ApplicationRepository extends JpaRepository<Long, EApplication> {
		
	@Autowired
	private EntityManager entityManager;
		
	public ApplicationRepository() {
		super(EApplication.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public EApplication createApplication(String name, String version, String category, String description, ELayout layout, TrxInfo trxInfo) throws Exception {
		ELayout topLevelLayout = null;
		createLayout(layout, topLevelLayout);		
		if (topLevelLayout == null) topLevelLayout = layout;
		EApplication e = new EApplication(name, version, category, description, topLevelLayout);	
		this.create(e, trxInfo);
		return e;
	}
	
	private void createLayout(ELayout layout, ELayout topLevelLayout) {
		Set<EForm> forms = layout.getForms();
		if (forms != null) {
			for (EForm form: forms) {
				Set<EFormField> formFields = form.getFormFields();
				if (formFields != null) {
					for (EFormField field: formFields) {
						Set<EFormFieldProperty> props = field.getFormFieldProperties();
						if (props != null) {
							for (EFormFieldProperty prop: props) {
								prop.setFormField(field);
								entityManager.persist(prop);
							}
						}
						field.setForm(form);
						entityManager.persist(field);
					}
				}
				form.setLayout(layout);
				entityManager.persist(form);
			}
		}
		
		if (topLevelLayout == null) {
			topLevelLayout = layout;
		}
		
		Set<ELayout> childLayouts = layout.getChildLayouts();
		if (childLayouts != null) {
			for (ELayout childLayout: childLayouts) {
				childLayout.setParentLayout(layout);
				createLayout(childLayout, topLevelLayout);
			}
		}
	}
}
