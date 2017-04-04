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

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.ERecipe;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.PartFilter;
import com.cimpoint.mes.common.services.PartRecipeService;
import com.cimpoint.mes.server.repositories.PartRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("partRecipeService")
public class PartRecipeServiceImpl extends RemoteServiceServlet implements PartRecipeService {
	private static final long serialVersionUID = 436617627719226477L;

	@Autowired
	private PartRepository partRep;

	@Override
	public void enableCache(boolean cacheable) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCacheEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EPart createPart(String partNumber, String partRevision, MESTrxInfo trxInfo) throws Exception {
//		return partRep.createPart(partNumber, partRevision, trxInfo);
		return partRep.createPart(partNumber, null, partRevision, null, null, null, null, null, null, trxInfo);
	}

	@Override
	public Set<EPart> findParts(PartFilter filter) throws Exception {
		return partRep.findParts(filter);
	}

	@Override
	public Set<EPart> findAllParts() throws Exception {
		return partRep.findAll();
	}

	@Override
	public EPart findPartById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EPart findPartByNumber(String partNumber) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EPart findPartByNumberAndRevision(String partNumber, String partRevision) throws Exception {
		return partRep.findPartByNameAndRevision(partNumber, partRevision);
	}

	@Override
	public void removePart(EPart part, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public EBom getBom() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void savePart(EPart part, MESTrxInfo trxInfo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ERecipe findRecipeByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EComponent findComponentById(Long componentId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EComponent findComponentByNameAndRevision(String componentName,
			String componentRevision) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ERecipe createRecipe(String name, List<EComponent> components, MESTrxInfo trxInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRecipe(ERecipe recipe, MESTrxInfo trxInfo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
