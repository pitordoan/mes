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

import java.util.List;

import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.ERecipe;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecipeServiceAsync {
	public void findRecipeByName(String name, AsyncCallback<ERecipe> callback);
	public void findComponentById(Long componentId, AsyncCallback<EComponent> callback);
	public void findComponentByNameAndRevision(String componentName, String componentRevision, AsyncCallback<EComponent> callback);
    public void createRecipe(String name, List<EComponent> components, MESTrxInfo trxInfo, AsyncCallback<ERecipe> callback);
    public void saveRecipe(ERecipe recipe, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
