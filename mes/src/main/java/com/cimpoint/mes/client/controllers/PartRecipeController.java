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
package com.cimpoint.mes.client.controllers;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.MESTypeConverter.EntityToClientObjectCallback;
import com.cimpoint.mes.client.objects.Component;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.services.PartRecipeService;
import com.cimpoint.mes.common.services.PartRecipeServiceAsync;

public class PartRecipeController extends AppController {

	private PartRecipeServiceAsync partService;
	private Set<Part> parts;
	
	public PartRecipeController() {
		partService = PartRecipeService.Util.getInstance();
	}

	public void findAllParts(final CallbackHandler<Set<Part>> callback) {
		this.partService.findAllParts(new  MESTypeConverter.EntityToClientObjectSetCallback<EPart, Part>(Part.class, callback));
	}
	
	public void findPartByNumberAndRevision(String partNumber, String partRevision, CallbackHandler<Part> callback) {
		this.partService.findPartByNumberAndRevision(partNumber, partRevision, new EntityToClientObjectCallback<EPart, Part>(Part.class, callback));
	}

	public Component findComponentByNameAndRevision(String materialName, String materialRevision) {
		// TODO Auto-generated method stub
		return null;
	}

	public Part findPartById(Long materialId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Component findComponentById(Long materialId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

}
