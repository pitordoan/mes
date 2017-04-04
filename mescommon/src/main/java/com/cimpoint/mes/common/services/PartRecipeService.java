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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/partRecipeService")
public interface PartRecipeService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static PartRecipeServiceAsync instance;
		public static PartRecipeServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(PartRecipeService.class);
			}
			return instance;
		}
	}
	
	public void enableCache(boolean cacheable);
    public boolean isCacheEnabled();
    
    //part
    public EPart createPart(String partNumber, String partRevision, MESTrxInfo trxInfo) throws Exception;
    public Set<EPart> findParts(PartFilter filter) throws Exception;
    public Set<EPart> findAllParts() throws Exception;
    public EPart findPartById(Long id) throws Exception;
    public EPart findPartByNumber(String partNumber) throws Exception;
    public EPart findPartByNumberAndRevision(String partNumber, String partRevision) throws Exception;
    public void removePart(EPart part, MESTrxInfo trxInfo) throws Exception;
	public EBom getBom() throws Exception;
	public void savePart(EPart part, MESTrxInfo trxInfo) throws Exception;
	
	//recipe
	public ERecipe findRecipeByName(String name) throws Exception;
	public EComponent findComponentById(Long componentId) throws Exception;
	public EComponent findComponentByNameAndRevision(String componentName, String componentRevision) throws Exception;
    public ERecipe createRecipe(String name, List<EComponent> components, MESTrxInfo trxInfo) throws Exception;
    public void saveRecipe(ERecipe recipe, MESTrxInfo trxInfo) throws Exception;
}
