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

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EDataSet;
import com.cimpoint.mes.common.entities.MESTrxInfo;

@Repository("attributeRepository")
public class AttributeRepository extends JpaRepository<Long, EAttribute> {
	
	@Autowired
	private EntityManager entityManager;

	public AttributeRepository() {
		super(EAttribute.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public EAttribute createAttribute(Long dataSetId, String attributeName, String value, MESTrxInfo trxInfo) throws Exception {
		EDataSet dataSet = getEntityManager().find(EDataSet.class, dataSetId);
		EAttribute e = new EAttribute(dataSet, attributeName, value);
		this.create(e, trxInfo);
		
		return e;
	}
}
