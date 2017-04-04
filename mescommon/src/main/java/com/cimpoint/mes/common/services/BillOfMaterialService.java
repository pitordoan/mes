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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/billOfMaterialService")
public interface BillOfMaterialService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BillOfMaterialServiceAsync instance;
		public static BillOfMaterialServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(BillOfMaterialService.class);
			}
			return instance;
		}
	}
	
	// part
	public EPart findPartById(long id) throws Exception;
	public EPart findPartByNumber(String number) throws Exception;
	public EPart findPartByNameAndRevision(String number, String revision) throws Exception;
	public Set<EPart> findAllParts() throws Exception;
	public Set<String> findAllPartNumbers() throws Exception;	
	public Set<String> findAllPartNameWithRevisions() throws Exception;	
	public Set<String> findPartNumbers(PartFilter filter) throws Exception;	
	public Set<EPart> findParts(PartFilter filter) throws Exception;	
	public Set<String> findRevisionsByPartName(String partName) throws Exception;
	public EPart createPart(String partName, String desc, String partRevision, String quantiy, 
							MESConstants.Object.UnitOfMeasure unitOfMeasure, MESConstants.Object.PartCatergory category,
							Set<EBom> boms, String startDate, String endDate, MESTrxInfo trxInfo) throws Exception;
	public void savePart(EPart part, MESTrxInfo trxInfo) throws Exception;
	public void removePart(EPart part, MESTrxInfo trxInfo) throws Exception;
	
	// BOM
	public EBom findBomById(long id) throws Exception;
	public EBom findBomByName(String name) throws Exception;
	public Set<EBom> findAllBoms() throws Exception;
	public Set<String> findAllBomNames() throws Exception;
	public EBom findBomByNameAndRevision(String name, String revision) throws Exception;
	public EBom createBom(String name, String desc, String revision, String startDate, String endDate, Set<EBomItem> bomItems, MESTrxInfo trxInfo) throws Exception;
	public void saveBom(EBom bom, MESTrxInfo trxInfo) throws Exception;
	public void removeBom(EBom bom, MESTrxInfo trxInfo) throws Exception;
	
	// MfgBom
	public EMfgBom findMfgBomById(long id) throws Exception;
	public EMfgBom findMfgBomByName(String name) throws Exception;
	public Set<EMfgBom> findAllMfgBoms() throws Exception;
	public Set<String> findAllMfgBomNames() throws Exception;
	public EMfgBom findMfgBomByNameAndRevision(String name, String revision) throws Exception;
	public EMfgBom createMfgBom(String name, String desc, String revision, String startDate, String endDate, Set<EMfgBomItem> mfgBomItems, MESTrxInfo trxInfo) throws Exception;
	public void saveMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo) throws Exception;
	public void removeMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo) throws Exception;
}
