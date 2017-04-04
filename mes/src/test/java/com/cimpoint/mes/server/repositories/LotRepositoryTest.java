/*******************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     admin - initial implemenation
 ******************************************************************************/

package com.cimpoint.mes.server.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LotRepositoryTest {

	//@Autowired
	//private MESService mesService;
	
	//@Autowired
	//private LotService lotService;
	
	//@Autowired
	//private WorkOrderService workOrderService;
	
	@Autowired
	private PartRepository partRespository;

	@Before
	public void prepareData() throws Exception {
//		lotService = mesService.getLotService();
//		workOrderService = mesService.getWorkOrderService();
//		partService = mesService.getPartService();
		
		//createPart();
		//createWorkOrder();
		//createLot();
	}
	
	@Test
	public void createPart() throws Exception {
		/*Part part = partRespository.findPartByNumber("1000");
		if (part == null) {
			part = partRespository.createPart("1000", "A");			
		}
		
		Long id = part.getId();		
		Assert.assertNotNull(id);*/
	}
	
	/*public void createWorkOrder() throws Exception {		
		WorkOrder wo = workOrderService.findWorkOrderByNumber("1");
		if (wo == null) {
			Part part = partService.findPartByNumber("1000");
			CreateWorkOrderParameter param = workOrderService.newCreateWorkOrderParameter();
			WorkOrderItem woItem = workOrderService.newWorkOrderItem();
			woItem.setNumber("1");
			woItem.setPart(part);
			param.setWorkOrderNumber("1");
			param.setWorkOrderItems(Arrays.asList(woItem));
			wo = workOrderService.createWorkOrder(param);
		}
		
		Long id = wo.getId();		
		Assert.assertNotNull(id);
	}

	public void createLot() throws Exception {
		Lot lot = lotService.findLotByNumber("1");
		if (lot == null) {
			Part part = partService.findPartByNumber("1000");
			WorkOrder wo = workOrderService.findWorkOrderByNumber("1");
			CreateLotParameter param = lotService.newCreateLotParameter();
			param.setLotNumber("1");
			param.setPart(part);
			param.setWorkOrderItem(wo.getItems().get(0));
			lot = lotService.createLot(param);
		}
		Long id = lot.getId();		
		Assert.assertNotNull(id);
	}

	@Test
	public void updateLot() throws Exception {
		SaveLotParameter param = lotService.newSaveLotParameter();
		param.setComment("test");
		Lot lot = lotService.findLotByNumber("1");
		lotService.updateLot(lot, param);
		String lotNo = lot.getNumber();
		Assert.assertNotNull(lotNo);
		
		WorkOrderItem woi = lot.getWorkOrderItem();
		System.out.println("woi: " + woi.getNumber());

		//Location loc = lot.getLocation();
		//System.out.println(loc.getOperationName());
		
		lot.save(param);
	}

	@Test()
	@DependsOn("updateLot")
	public void findLot() throws Exception {
		Lot lot = lotService.findLotByNumber("1");		
		Assert.assertNotNull("Lot not found!", lot);
	}*/
}
