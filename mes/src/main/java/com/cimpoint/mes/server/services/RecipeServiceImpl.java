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

import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.ERecipe;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.RecipeService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("recipeService")
public class RecipeServiceImpl extends RemoteServiceServlet implements RecipeService {
	private static final long serialVersionUID = -4255237463643570149L;

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
