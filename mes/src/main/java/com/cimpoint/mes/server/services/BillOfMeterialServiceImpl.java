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
package com.cimpoint.mes.server.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.mes.common.MESConstants.Object.PartCatergory;
import com.cimpoint.mes.common.MESConstants.Object.UnitOfMeasure;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EMfgBom;
import com.cimpoint.mes.common.entities.EMfgBomItem;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.PartFilter;
import com.cimpoint.mes.common.services.BillOfMaterialService;
import com.cimpoint.mes.server.repositories.BomRepository;
import com.cimpoint.mes.server.repositories.MfgBomRepository;
import com.cimpoint.mes.server.repositories.PartRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("billOfMaterialService")
public class BillOfMeterialServiceImpl extends RemoteServiceServlet implements BillOfMaterialService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PartRepository partRepository;
	
	@Autowired
	private BomRepository bomRepository;

	@Autowired
	private MfgBomRepository mfgBomRepository;

	@Override
	public EPart findPartById(long id) throws Exception {
		return partRepository.findById(id);
	}

	@Override
	public EPart findPartByNumber(String number) throws Exception {
		return partRepository.findByNumber(number);
	}

	@Override
	public EPart findPartByNameAndRevision(String name, String revision) throws Exception {
		return partRepository.findPartByNameAndRevision(name, revision);
	}

	@Override
	public Set<EPart> findAllParts() throws Exception {
		return partRepository.findAll();
	}

	@Override
	public Set<String> findAllPartNumbers() throws Exception {
		return partRepository.findAllNumbers();
	}

	@Override
	public Set<String> findAllPartNameWithRevisions() throws Exception {
		return partRepository.findAllPartNameWithRevisions();
	}

	@Override
	public Set<String> findPartNumbers(PartFilter filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<EPart> findParts(PartFilter filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void savePart(EPart part, MESTrxInfo trxInfo) throws Exception {
		partRepository.update(part, trxInfo);
	}

	@Override
	public void removePart(EPart part, MESTrxInfo trxInfo) throws Exception {
		partRepository.remove(part.getId(), trxInfo);
	}

	@Override
	public EBom findBomById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EBom findBomByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<EBom> findAllBoms() throws Exception {
		return bomRepository.findAll();
	}

	@Override
	public Set<String> findAllBomNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EPart createPart(String partName, String desc, String partRevision, String quantiy, UnitOfMeasure unitOfMeasure, PartCatergory category,
			Set<EBom> boms, String startDate, String endDate, MESTrxInfo trxInfo) throws Exception {
		return partRepository.createPart(partName, desc, partRevision, quantiy, unitOfMeasure, category, boms, startDate, endDate, trxInfo);
	}

	@Override
	public Set<String> findRevisionsByPartName(String partName) throws Exception {
		return partRepository.findRevisionsByPartName(partName);
	}

	@Override
	public EBom createBom(String name, String desc, String revision, String startDate, String endDate, Set<EBomItem> bomItems, MESTrxInfo trxInfo)
			throws Exception {
		return bomRepository.createBom(name, desc, revision, startDate, endDate, bomItems, trxInfo);
	}

	@Override
	public EBom findBomByNameAndRevision(String name, String revision) throws Exception {
		return bomRepository.findBomByNameAndRevision(name, revision);
	}

	@Override
	public void saveBom(EBom bom, MESTrxInfo trxInfo) throws Exception {
		bomRepository.update(bom, trxInfo);
	}

	@Override
	public void removeBom(EBom bom, MESTrxInfo trxInfo) throws Exception {
		bomRepository.remove(bom.getId(), trxInfo);
	}

	@Override
	public EMfgBom findMfgBomById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EMfgBom findMfgBomByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<EMfgBom> findAllMfgBoms() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findAllMfgBomNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EMfgBom findMfgBomByNameAndRevision(String name, String revision)
			throws Exception {
		return mfgBomRepository.findMfgBomByNameAndRevision(name, revision);
	}

	@Override
	public EMfgBom createMfgBom(String name, String desc, String revision,
			String startDate, String endDate, Set<EMfgBomItem> mfgBomItems,
			MESTrxInfo trxInfo) throws Exception {
		return mfgBomRepository.createMfgBom(name, desc, revision, startDate, endDate, mfgBomItems, trxInfo);
	}

	@Override
	public void saveMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo) throws Exception {
		mfgBomRepository.update(mfgBom, trxInfo);
	}

	@Override
	public void removeMfgBom(EMfgBom mfgBom, MESTrxInfo trxInfo)
			throws Exception {
		mfgBomRepository.remove(mfgBom.getId(), trxInfo);
		
	}

}
