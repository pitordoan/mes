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
import java.util.Set;

import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.ERecipe;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.PartFilter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PartRecipeServiceAsync {
	public void enableCache(boolean cacheable, AsyncCallback<Void> callback);
    public void isCacheEnabled(AsyncCallback<Boolean> callback);
    
    //part
    public void createPart(String partNumber, String partRevision, MESTrxInfo trxInfo, AsyncCallback<EPart> callback);
    public void findParts(PartFilter filter, AsyncCallback<Set<EPart>> callback);
    public void findAllParts(AsyncCallback<Set<EPart>> callback);
    public void findPartById(Long id, AsyncCallback<EPart> callback);
    public void findPartByNumber(String partNumber, AsyncCallback<EPart> callback);
    public void findPartByNumberAndRevision(String partNumber, String partRevision, AsyncCallback<EPart> callback);
    public void removePart(EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void getBom(AsyncCallback<EBom> callback);
	public void savePart(EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	
	//recipe
	public void findRecipeByName(String name, AsyncCallback<ERecipe> callback);
	public void findComponentById(Long componentId, AsyncCallback<EComponent> callback);
	public void findComponentByNameAndRevision(String componentName, String componentRevision, AsyncCallback<EComponent> callback);
    public void createRecipe(String name, List<EComponent> components, MESTrxInfo trxInfo, AsyncCallback<ERecipe> callback);
    public void saveRecipe(ERecipe recipe, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
