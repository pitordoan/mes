/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.common.services;

import java.util.Set;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.UnitOfMeasure;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EMfgBom;
import com.cimpoint.mes.common.entities.EMfgBomItem;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.PartFilter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BillOfMaterialServiceAsync {
	// part
	public void findPartById(long id, AsyncCallback<EPart> callback);
	public void findPartByNumber(String number, AsyncCallback<EPart> callback);
	public void findPartByNameAndRevision(String number, String revision, AsyncCallback<EPart> callback);
	public void findAllParts(AsyncCallback<Set<EPart>> callback);
	public void findAllPartNumbers(AsyncCallback<Set<String>> callback);	
	public void findAllPartNameWithRevisions(AsyncCallback<Set<String>> callback);	
	public void findPartNumbers(PartFilter filter, AsyncCallback<Set<String>> callback);	
	public void findParts(PartFilter filter, AsyncCallback<Set<EPart>> callback);	
	public void findRevisionsByPartName(String partName, AsyncCallback<Set<String>> callback);
	public void createPart(String partName, String desc, String partRevision, String quantiy, 
							MESConstants.Object.UnitOfMeasure unitOfMeasure, MESConstants.Object.PartCatergory category,
							Set<EBom> boms, String startDate, String endDate, MESTrxInfo trxInfo, AsyncCallback<EPart> callback);
	public void savePart(EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removePart(EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	
	// BOM
	public void findBomById(long id, AsyncCallback<EBom> callback);
	public void findBomByName(String name, AsyncCallback<EBom> callback);
	public void findAllBoms(AsyncCallback<Set<EBom>> callback);
	public void findAllBomNames(AsyncCallback<Set<String>> callback);
	public void findBomByNameAndRevision(String name, String revision, AsyncCallback<EBom> callback);
	public void createBom(String name, String desc, String revision, String startDate, String endDate, Set<EBomItem> bomItems, MESTrxInfo trxInfo, AsyncCallback<EBom> callback);
	public void saveBom(EBom bom, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removeBom(EBom bom, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	
	// MfgBom
	public void findMfgBomById(long id, AsyncCallback<EMfgBom> callback);
	public void findMfgBomByName(String name, AsyncCallback<EMfgBom> callback);
	public void findAllMfgBoms(AsyncCallback<Set<EMfgBom>> callback);
	public void findAllMfgBomNames(AsyncCallback<Set<String>> callback);
	public void findMfgBomByNameAndRevision(String name, String revision, AsyncCallback<EMfgBom> callback);
	public void createMfgBom(String name, String desc, String revision, String startDate, String endDate, Set<EMfgBomItem> mfgBomItems, MESTrxInfo trxInfo, AsyncCallback<EMfgBom> callback);
	public void saveMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removeMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
