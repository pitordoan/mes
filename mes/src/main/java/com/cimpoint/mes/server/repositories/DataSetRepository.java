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

import java.util.HashSet;
import java.util.Set;

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

@Repository("dataSetRepository")
public class DataSetRepository extends JpaRepository<Long, EDataSet> {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private AttributeRepository attributeRep;

	public DataSetRepository() {
		super(EDataSet.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
		
	@Transactional
	public EDataSet createDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType, MESTrxInfo trxInfo) throws Exception {
		EDataSet dataSet = new EDataSet(name, attributeNames, associatedObjectId, associatedObjectType);
		String attrNamesString = toAttributeNamesString(attributeNames);
		dataSet.setAttributeNames(attrNamesString);
		
		this.create(dataSet, trxInfo);
		
		if (attributeNames != null) {
			Set<EAttribute> eattrs = new HashSet<EAttribute>();
			for (String attr: attributeNames) {
				EAttribute e = attributeRep.createAttribute(dataSet.getId(), attr, (String) null, trxInfo);		
				eattrs.add(e);
			}	
			
			dataSet.setAttributes(eattrs);			
		}
		
		return dataSet;
	}
		
	private String toAttributeNamesString(String[] attributeNames) throws Exception {
		String attrNames = "";
		for (String attr: attributeNames) {
			attrNames += attr + ",";
		}
		
		if (attrNames.length() > 1) {
			attrNames = attrNames.substring(0, attrNames.length()-1);
		}
		
		if (attrNames.length() > 4000) {
			throw new Exception("Total lenth of all attribute names should not exceed 4000 characters.");
		}
		
		return attrNames;
	}
}
