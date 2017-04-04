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

package com.cimpoint.mes.server.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.UnitOfMeasure;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.TestUtils;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.ECheckList;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.ELayout;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.AreaFilter;
import com.cimpoint.mes.common.filters.LotFilter;
import com.cimpoint.mes.common.filters.UnitFilter;
import com.cimpoint.mes.common.services.BatchService;
import com.cimpoint.mes.common.services.ContainerService;
import com.cimpoint.mes.common.services.CustomCodeService;
import com.cimpoint.mes.server.repositories.CheckListRepository;
import com.cimpoint.mes.server.servlets.testrules.TestAutoQueueStepRule;
import com.cimpoint.mes.server.servlets.testrules.TestStepDAutoStartAndCompleteSplitRule;
import com.cimpoint.mes.server.servlets.testrules.TestStepFAutoStartAndCompleteRule;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ServicesTest {

	/*@Autowired
	private MesService mesService;
	*/
	@Autowired
	private LotServiceImpl lotService;
	
	@Autowired
	private WorkOrderServiceImpl workOrderService;
	
	@Autowired
	private PartRecipeServiceImpl partService;
	
	@Autowired
	private UnitServiceImpl unitService;
	
	@Autowired
	private RoutingServiceImpl routingService;
		
	@Autowired
	private DataCollectionServiceImpl dataCollectionService;
	
	@Autowired
	private TravelerServiceImpl travelerService;

	@Autowired
	private ConsumptionServiceImpl consumptionService; 
	
	@Autowired
	private CheckListRepository checkListRepository; 

	@Autowired
	private CustomCodeService customCodeService;
	
	@Autowired
	private ContainerService containerService;
	
	@Autowired
	private BatchService batchService;

	@Test
	@Rollback(true)
	public void createECheckList(){
		try {
			ECheckList result = checkListRepository.createCheckList("LI1", "description", 1, "OK", null, null, this.newTrxInfo("create EChecklist"));
			Assert.assertNotNull(result.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Before
	public void prepareData() throws Exception {
//		lotService = mesService.getLotService();
//		workOrderService = mesService.getWorkOrderService();
//		partService = mesService.getPartService();
		
		//createPart();
		//createWorkOrder();
		//createLot();		
	}
		
	private MESTrxInfo newTrxInfo(String comment) {
		return TestUtils.newTrxInfo(comment);
	}
	
	@Test
	@Rollback(true)
	public void createPart() throws Exception {
		EPart part = partService.findPartByNumberAndRevision("1001", "A");
		if (part == null) {
			MESTrxInfo trxInfo = this.newTrxInfo("test create part");
			part = partService.createPart("1001", "A", trxInfo);			
		}
		
		Long id = part.getId();		
		Assert.assertNotNull(id);
		
		EPart p = partService.findPartByNumberAndRevision("1001", "A");
		Assert.assertNotNull(p.getId());
		
		/*ELot lot = lotService.findLotByNumber("5");
		Assert.assertNotNull(lot.getId());
		LotFilter lotFilter = new LotFilter();
		lotFilter.whereWorkOrderItemNumber().isLike("5");
		Set<ELot> lots = lotService.findLots(lotFilter);
		System.out.println(lots.size());
		
		Set<String> all = unitService.findUnitNumbersByLotNumber("4");
		System.out.println(all.size());*/
	}
	
	@Test()
	@DependsOn("createPart")
	@Rollback(true)
	public void createWorkOrder() throws Exception {		
		String woNumber = "1234";
		String woiNumber = "2233";
		EWorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
		if (wo == null) {
			EPart part = partService.findPartByNumberAndRevision("1001", "A");
			if (part == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create part");
				part = partService.createPart("1001", "A", trxInfo);			
			}
			EWorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);		
			MESTrxInfo trxInfo = this.newTrxInfo("test work order");
			wo = workOrderService.createWorkOrder(woNumber, TestUtils.newHashSet(woItem), trxInfo, MESConstants.Object.UnitOfMeasure.Each);
			
			Set<EWorkOrderItem> items = wo.getItems();
			for (EWorkOrderItem item: items) {
				System.out.println("woi = " + item.getNumber());
			}
		}
		
		wo = workOrderService.findWorkOrderByNumber(woNumber);
		
		Long id = wo.getId();		
		Assert.assertNotNull(id);
	}
	
	@Test()
	public void testServicesSearch()  throws Exception {		
		
		//Set<String> wois = workOrderService.findWorkOrderItemNumbersByWorkOrderNumber("1234");
		//Assert.assertEquals(1234, wois.size());
		
		//Set<String> lots = lotService.findLotNumbersByWorkOrderItemNumber("1");
		//Assert.assertNotNull(lots);
		
		
	}

	@Test()
	@Rollback(true)
	public void createBatch() throws Exception {		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create batch");
		EWorkCenter wc = routingService.findWorkCenterByName("wc1");
		EBatch batch1 = batchService.createBatch("Batch1", wc, trxInfo);
		EBatch batch2 = batchService.createBatch("Batch2", wc, trxInfo);
		Assert.assertNotNull(batch1);
		Assert.assertNotNull(batch2);
	}

	@Test()
	@DependsOn("createBatch")
	@Rollback(true)
	public void createContainer() throws Exception {		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create containers");
		EBatch batch = batchService.findBatchByName("Batch1");
		Set<EBatch> batches = new HashSet<EBatch>();
		
		LotFilter lotFiler = new LotFilter();
		lotFiler.whereWorkOrderItemNumber().isLike("1");
		Set<ELot> lots = lotService.findLots(lotFiler);
		Assert.assertNotNull(lots);
		
		UnitFilter unitFiler = new UnitFilter();
		unitFiler.whereLotNumber().isLike("3");
		Set<EUnit> units = unitService.findUnits(unitFiler);
		
		EContainer container = containerService.createContainer("Computer_Sciences", "Sciences", batches, lots, units, trxInfo);
		Assert.assertNotNull(container);
	}
	
	@Test()
	@DependsOn("createWorkOrder")
	@Rollback(true)
	public void createLot() throws Exception {
		/*String lotNumber = "4321";
		ELot lot = lotService.findLotByNumber(lotNumber);
		if (lot == null) {
			EPart part = partService.findPartByNumberAndRevision("1001", "A");
			if (part == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create part");
				part = partService.createPart("1001", "A", trxInfo);
			}
			
			EPart unitPart = partService.findPartByNumberAndRevision("1111", "B");
			if (unitPart == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create part for unit");
				unitPart = partService.createPart("1111", "B", trxInfo);
			}
			
			String woNumber = "1234";
			String woiNumber = "2233";
			EWorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
			if (wo == null) {
				EWorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);		
				MESTrxInfo trxInfo = this.newTrxInfo("test work order");
				wo = workOrderService.createWorkOrder(woNumber, TestUtils.newHashSet(woItem), trxInfo);
			}
			
			String unitSerialNumber1 = "124321";
			String unitSerialNumber2 = "124322";
			EUnit unit1 = unitService.findUnitByNumber(unitSerialNumber1);
			if (unit1 == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create unit");
				unit1 = unitService.createUnit(unitSerialNumber1, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit1 id = " + unit1.getId());
			}
			
			EUnit unit2 = unitService.findUnitByNumber(unitSerialNumber2);
			if (unit2 == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create unit 2");
				unit2 = unitService.createUnit(unitSerialNumber2, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit2 id = " + unit2.getId());
			}
			
			MESTrxInfo trxInfo = this.newTrxInfo("test create lot");
			CustomAttributes customAttributes = null;
			lot = lotService.createDiscreteLot(lotNumber, wo.getNumber(), TestUtils.newHashSet(unit1, unit2), "1000", "A", customAttributes, trxInfo);			
		}
		Long id = lot.getId();		
		Assert.assertNotNull(id);
		lotService.startLot(lot, "entry", "wc2", this.newTrxInfo("test create lot"));*/
//		System.out.println("Created: " + lot.getCreatedTime().toString() + " Last Modified " + lot.getFinishedTime() 
//				+ " size " + lot.getUnits().size()); //+  lot.getLastModifiedTime().toString(), lot.getLastModifyUserName(), 
		//lot.getQuantity().asInteger(), lot.toEntity().getUnitOfMeasure().toString(), lot.getDiscretePart().getNumber(), lot.getStatus()
	}
	
	@Test()
	@Rollback(true)
	public void createSiteNoArea() throws Exception {
		String name = "site100";
		String description = "desc100";
		Set<EArea> optAreas = null;
		CustomAttributes customAttributes = null;
		MESTrxInfo trxInfo = this.newTrxInfo("create site with null areas");
		ESite site = routingService.createSite(name, description, optAreas, customAttributes, trxInfo);
		
		System.out.println("Successfully create site " + site.getName() + " without area");
	}
	
	@Test()
	@Rollback(true)
	public void createSiteWithArea() throws Exception {
		String name = "site200";
		String description = "desc200";
		CustomAttributes customAttributes = null;
		
		EArea area1 = routingService.createArea("area100", "area100 desc", null, null, null, null);
		EArea area2 = routingService.createArea("area200", "area200 desc", null, null, null, null);
		Set<EArea> areas = new HashSet<EArea>();
		areas.add(area1);
		areas.add(area2);
		MESTrxInfo trxInfo = this.newTrxInfo("create site with areas");
		ESite site = routingService.createSite(name, description, areas, customAttributes, trxInfo);
		
		System.out.println("Successfully create site " + site.getName() + " with 2 areas");
		
		for (EArea a: site.getAreas()) {
			System.out.println("area: " + a.getName() + " - " + a.getDescription() + " - site: " + a.getSite().getName());			
		}
	}
	
	@Test()
	@Rollback(true)
	public void filterArea() throws Exception {
		String name = "site100";
		String description = "desc100";
		Set<EArea> optAreas = null;
		CustomAttributes customAttributes = null;
		MESTrxInfo trxInfo = this.newTrxInfo("create site with null areas");
		ESite site = routingService.createSite(name, description, optAreas, customAttributes, trxInfo);		
		Assert.assertNotNull(site);
				
		EArea area1 = routingService.createArea("area100", "area100 with site", site, null, null, null);
		EArea area2 = routingService.createArea("area200", "area200 w/o site", null, null, null, null);
		Assert.assertNotNull(area1);
		Assert.assertNotNull(area2);
		
		AreaFilter filter = new AreaFilter();
		filter.whereSite().isNull();
		Set<String> areaNames = routingService.findAreaNames(filter);
//		Assert.assertTrue(areaNames.size() == 1);
	}
		
	@Test
	@DependsOn("createPart")
	@Rollback(true)
	public void persistMaterialIO() throws Exception{
		/*ELot lot = lotService.findLotByNumber("5");
		Assert.assertNotNull(lot.getId());
		
		EPart part = partService.findPartByNumberAndRevision("1001", "A");
		if (part == null) {
			MESTrxInfo trxInfo = this.newTrxInfo("test create part");
			part = partService.createPart("1001", "A", trxInfo);			
		}
		Assert.assertNotNull(part.getId());
		
		MESTrxInfo trxInfo = this.newTrxInfo("consumePart MaterialIO");
		consumptionService.consumePart(lot, part, trxInfo);*/
	}
	
	private void createAreas() throws Exception {
		ESite site1 = routingService.findSiteByName("site200");		
		
		for (int i=1; i<=1; i++) {
			String areaName = "area100" + i;
			String areaDescription = areaName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing area");	
			Set<EProductionLine> productionLines = null;
			
			EArea area = routingService.createArea(areaName, areaDescription, site1, productionLines, customAttributes, trxInfo);	
			
			System.out.println("Create area '" + area.getName() + "' and assign it to site1 successfully.");
		}	
	}
	
	private void createProductionLines() throws Exception {
		EArea area1 = routingService.findAreaByName("area1001");		
		
		for (int i=1; i<=1; i++) {
			String pdlName = "pdl100" + i;
			String pdlDescription = pdlName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing production line");	
			Set<EWorkCenter> workCenters = null;
			
			EProductionLine pdl = routingService.createProductionLine(pdlName, pdlDescription, area1, workCenters, customAttributes, trxInfo);	
			
			System.out.println("Create production line '" + pdl.getName() + "' and assign it to area1 successfully.");
		}	
	}
	
	private void createWorkCenters() throws Exception {
		EProductionLine pdl1 = routingService.findProductionLineByName("pdl1001");		
		
		for (int i=1; i<=1; i++) {
			String wcName = "wc100" + i;
			String wcDescription = wcName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing work center");	
			EArea area = pdl1.getArea();
			Set<EEquipment> equipments = null;
			
			EWorkCenter wc = routingService.createWorkCenter(wcName, wcDescription, area, equipments, pdl1, customAttributes, trxInfo);
			
			System.out.println("Create work center '" + wc.getName() + "' and assign it to production line pdl1 successfully.");
		}	
	}
	
	private void createOperations() throws Exception {
		for (int i=1; i<=1; i++) {
			String opName = "op100" + i;
			String opDescription = opName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing operation");
			
			EOperation op = routingService.createOperation(opName, opDescription, customAttributes, trxInfo);
			System.out.println("Create operation '" + op.getName() + "' successfully.");
		}	
	}

	private void createEquipments() throws Exception {
		EWorkCenter workCenter1 = routingService.findWorkCenterByName("wc1001");
		
		for (int i=1; i<=1; i++) {
			String eqName = "eq100" + i;
			String eqDescription = eqName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing equipment");
			
			EEquipment eq = routingService.createEquipment(eqName, eqDescription, workCenter1, customAttributes, trxInfo);
			System.out.println("Create equipment '" + eq.getName() + "' successfully.");
		}	
	}
	
	/*private void createRoutings() throws Exception {
		createAreas();
		createProductionLines();
		createWorkCenters();
		createOperations();
		createEquipments();
		
		Set<EWorkCenter> optWorkCenters1 = new HashSet<EWorkCenter>();
		Set<EWorkCenter> optWorkCenters2 = new HashSet<EWorkCenter>();
		
		for (int i=1; i<=2; i++) {
			String wcName = "wc100" + i;
			EWorkCenter wc = routingService.findWorkCenterByName(wcName);
			optWorkCenters1.add(wc);
		}
		
		for (int i=3; i<=5; i++) {
			String wcName = "wc100" + i;
			EWorkCenter wc = routingService.findWorkCenterByName(wcName);
			optWorkCenters2.add(wc);
		}
		
		EOperation operA = routingService.createOperation("operA1", "desc", null, null);
		EOperation operB = routingService.createOperation("operB1", "desc", null, null);	
		EOperation operC = routingService.createOperation("operC1", "desc", null, null);	
		EOperation operD = routingService.createOperation("operD1", "desc", null, null);	
		EOperation operE = routingService.createOperation("operE1", "desc", null, null);	
		EOperation operF = routingService.createOperation("operF1", "desc", null, null);	
		
		EStep entryStep = MESServiceUtils.Routings.newQueuingEntryStep("entry1", "desc", optWorkCenters1, null);		
		EStep exitStep = MESServiceUtils.Routings.newQueuingExitStep("exit1", "desc", optWorkCenters2, null);		
		EStep stepA = MESServiceUtils.Routings.newAutoStartInnerStep("stepA1", "desc", operA, optWorkCenters1, null);		
		EStep stepB = MESServiceUtils.Routings.newAutoStartInnerStep("stepB1", "desc", operB, optWorkCenters1, null);		
		//InstructionStep wis1 = routingService.newInstructionStep("WI step 1");
		//InstructionStep wis2 = routingService.newInstructionStep("WI step 1");
		//final WorkInstruction wi = routingService.newWorkInstruction("opBWI", new InstructionStep[] {wis1, wis2});
		EStep stepC = MESServiceUtils.Routings.newInnerStep("stepC1", "desc", operC, optWorkCenters2, TestAutoQueueStepRule.class.getName(), null);		
		EStep stepD = MESServiceUtils.Routings.newInnerStep("stepD1", "desc", operD, optWorkCenters2, TestStepDAutoStartAndCompleteSplitRule.class.getName(), null);		
		EStep stepE = MESServiceUtils.Routings.newAutoStartInnerStep("stepE1", "desc", operE, optWorkCenters1, null);		
		EStep stepF = MESServiceUtils.Routings.newInnerStep("stepF1", "desc", operF, optWorkCenters1, TestStepFAutoStartAndCompleteRule.class.getName(), null);
		
		ETransition toStepATransition = MESServiceUtils.Routings.newTransition("ToStepA1", null);			
		ETransition toStepBTransition = MESServiceUtils.Routings.newTransition("ToStepB1", null);			
		ETransition toStepCTransition = MESServiceUtils.Routings.newTransition("ToStepC1", null);
		ETransition toStepDTransition = MESServiceUtils.Routings.newTransition("ToStepD1", null);
		ETransition toStepETransition = MESServiceUtils.Routings.newTransition("ToStepE1", new Quantity(1, UnitOfMeasure.Each), null, null);
		ETransition toStepFTransition = MESServiceUtils.Routings.newTransition("ToStepF1", new Quantity(2, UnitOfMeasure.Each), null, null);
		ETransition fromStepEToExitStepTransition = MESServiceUtils.Routings.newTransition("fromStepEToExitStepTransition", null);
		ETransition fromStepFToExitStepTransition = MESServiceUtils.Routings.newTransition("fromStepFToExitStepTransition", null);
		
		//ECheckList 1 = Pass, 0 = Fail
		ECheckList checkList1 = new ECheckList("LI1", "Perform task1", 1, "comments", null, null);
		ECheckList checkList2 = new ECheckList("LI2", "Perform task2", 1, "comments", null, null);
		
		entryStep.setCheckList(checkList1);
		stepA.setCheckList(checkList2);
		//																                                                          -> stepE (auto start)            -> 
		//entryStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start & complete & split)   			                      exitStep (queue)
		//																                                                          -> stepF (auto start & complete) -> 
		entryStep.connects(stepA, toStepATransition);
		stepA.connects(stepB, toStepBTransition);
		stepB.connects(stepC, toStepCTransition);
		stepC.connects(stepD, toStepDTransition);
		stepD.connects(stepE, toStepETransition);
		stepD.connects(stepF, toStepFTransition);
		stepE.connects(exitStep, fromStepEToExitStepTransition);
		stepF.connects(exitStep, fromStepFToExitStepTransition);
								
		CustomAttributes customAttributes = null;
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing routing");
		ERouting routing = routingService.createRouting("routingA1", "desc", "A", entryStep, customAttributes, trxInfo);
		
		System.out.println("Create routing '" + routing.getName() + "' successfully.");
	}*/
	
	@Test
	@Rollback(true)
	public void createForm() throws Exception {
		String name = "testForm1";
		EFormField formField1 = MESServiceUtils.Forms.newTextField("textField1", "Name", 100, 20, 1, true, true, 50);
		EFormField formField2 = MESServiceUtils.Forms.newTextField("textField2", "Description", 300, 20, 1, true, true, 255);
		EFormField formField3 = MESServiceUtils.Forms.newSelectField("textField3", "Option", 100, 20,  1, true, true);
		EFormField[] fieldArr = new EFormField[] {formField1, formField2, formField3};
		Set<EFormField> fieldSet = new HashSet<EFormField>();
		fieldSet.addAll(Arrays.asList(fieldArr));
		
		TrxInfo trxInfo = Utils.newTrxInfo("create test form");
		EForm form = customCodeService.createForm(name, fieldSet, trxInfo);	
		
		System.out.println("Create form '" + form.getName() + "' successfully");
	}
	
	@Test
	@Rollback(true)
	public void createApplication() throws Exception {
		String name = "testApp1";
		String description = "desc1";
		ELayout layout = MESServiceUtils.Forms.newVLayout();
				
		EFormField formField1 = MESServiceUtils.Forms.newTextField("textField1", "Name", 100, 20, 1, true, true, 50);
		EFormField formField2 = MESServiceUtils.Forms.newTextField("textField2", "Description", 300, 20, 1, true, true, 255);
		EFormField formField3 = MESServiceUtils.Forms.newSelectField("textField3", "Option", 100, 20,  1, true, true);
		
		EForm form = MESServiceUtils.Forms.newForm("testForm1");		
		form.addFormField(formField1);
		form.addFormField(formField2);
		form.addFormField(formField3);
		
		layout.addForm(form);
		
		TrxInfo trxInfo = Utils.newTrxInfo("create test application");
		EApplication app = customCodeService.createApplication(name, "1.0.0", "shopFloorControlSection", description, layout, trxInfo);	
		app = customCodeService.findApplicationByName(name);
		
		System.out.println("Create application '" + app.getName() + "' successfully");
	}
	
	private void createLots() throws Exception {
		/*String lotNumber = "4321";
		ELot lot = lotService.findLotByNumber(lotNumber);
		if (lot == null) {
			EPart part = partService.findPartByNumberAndRevision("1001", "A");
			if (part == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create part");
				part = partService.createPart("1001", "A", trxInfo);
			}
			
			EPart unitPart = partService.findPartByNumberAndRevision("1111", "B");
			if (unitPart == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create part for unit");
				unitPart = partService.createPart("1111", "B", trxInfo);
			}
			
			String woNumber = "1234";
			String woiNumber = "2233";
			EWorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
			if (wo == null) {
				EWorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);		
				MESTrxInfo trxInfo = this.newTrxInfo("test work order");
				wo = workOrderService.createWorkOrder(woNumber, TestUtils.newHashSet(woItem), trxInfo);
			}
			
			String unitSerialNumber1 = "124321";
			String unitSerialNumber2 = "124322";
			EUnit unit1 = unitService.findUnitByNumber(unitSerialNumber1);
			if (unit1 == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create unit");
				unit1 = unitService.createUnit(unitSerialNumber1, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit1 id = " + unit1.getId());
			}
			
			EUnit unit2 = unitService.findUnitByNumber(unitSerialNumber2);
			if (unit2 == null) {
				MESTrxInfo trxInfo = this.newTrxInfo("test create unit 2");
				unit2 = unitService.createUnit(unitSerialNumber2, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit2 id = " + unit2.getId());
			}
			
			MESTrxInfo trxInfo = this.newTrxInfo("test create lot");
			CustomAttributes customAttributes = null;
			lot = lotService.createDiscreteLot(lotNumber, wo.getNumber(), TestUtils.newHashSet(unit1, unit2), "1000", "A", customAttributes, trxInfo);			
		}*/
	}
	
	@Test()
	@Rollback(true)
	public void routeObjects() throws Exception {
		/*createRoutings();
		createLots();
		
		String lotNumber = "4321";
		ELot lot = lotService.findLotByNumber(lotNumber);
		String workCenterName = "wc1001";
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("create test lot");
		ETransitionAttributes trsnAttrs = routingService.routeLot(lot, "routingA1", "entry1", workCenterName, trxInfo);
		
		System.out.println("Test routing for lot '" + lot.getNumber());
		
		trsnAttrs = routingService.findTransitionAttributes(lot.getId(), MESConstants.Object.Type.Lot);
		if (trsnAttrs != null) {
			System.out.println("Lot TrnsAttr site: '" + trsnAttrs.getSiteName());		
			System.out.println("Lot TrnsAttr area: '" + trsnAttrs.getAreaName());	
			System.out.println("Lot TrnsAttr prd line: '" + trsnAttrs.getProductionLineName());	
			System.out.println("Lot TrnsAttr wc: '" + trsnAttrs.getWorkCenterName());	
			System.out.println("Lot TrnsAttr routing: '" + trsnAttrs.getRoutingName());
			System.out.println("Lot TrnsAttr step: '" + trsnAttrs.getStepName());
			System.out.println("Lot TrnsAttr operation: '" + trsnAttrs.getOperationName());
			System.out.println("Lot TrnsAttr last modifier: '" +trsnAttrs.getLastModifier());		
			System.out.println("Lot TrnsAttr step status: '" + trsnAttrs.getCurrentStepStatus());	
		}*/
	}
	
	/*@Test()
	@Rollback(true)
	public void stateRule() throws Exception {
		String lotNumber = "4321";
		ELot lot = lotService.findLotByNumber(lotNumber);
		if (lot == null) {
			EPart part = partService.findPartByNumberAndRevision("1000", "A");
			if (part == null) {
				TrxInfo trxInfo = this.newTrxInfo("test create part");
				part = partService.createPart("1000", "A", trxInfo);
			}
			
			EPart unitPart = partService.findPartByNumberAndRevision("1111", "B");
			if (unitPart == null) {
				TrxInfo trxInfo = this.newTrxInfo("test create part for unit");
				unitPart = partService.createPart("1111", "B", trxInfo);
			}
			
			String woNumber = "1234";
			String woiNumber = "2233";
			EWorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
			if (wo == null) {
				EWorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);		
				TrxInfo trxInfo = this.newTrxInfo("test work order");
				wo = workOrderService.createWorkOrder(woNumber, TestUtils.newHashSet(woItem), trxInfo);
			}
			
			String unitSerialNumber1 = "124321";
			String unitSerialNumber2 = "124322";
			EUnit unit1 = unitService.findUnitByNumber(unitSerialNumber1);
			if (unit1 == null) {
				TrxInfo trxInfo = this.newTrxInfo("test create unit");
				unit1 = unitService.createUnit(unitSerialNumber1, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit1 id = " + unit1.getId());
			}
			
			EUnit unit2 = unitService.findUnitByNumber(unitSerialNumber2);
			if (unit2 == null) {
				TrxInfo trxInfo = this.newTrxInfo("test create unit 2");
				unit2 = unitService.createUnit(unitSerialNumber2, woNumber, unitPart.getNumber(), unitPart.getRevision(), trxInfo);
				System.out.println("unit2 id = " + unit2.getId());
			}
			
			//TestSteRule
			//EntryStep -> Blending
			//stepB -> Mixing
			//stepD -> Pouring
			//ExitStep -> Shipping
			TrxInfo trxInfo = this.newTrxInfo("test create lot");
			lot = lotService.createDiscreteLot(lotNumber, wo.getNumber(), TestUtils.newHashSet(unit1, unit2), "1000", "A", TestStateRule.class.getName(), trxInfo);
			
			//            																											  -> stepE (auto start)            -> 
			//entryStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start & complete & split)   			                      exitStep (queue)
			//      														                                                          -> stepF (auto start & complete) -> 
			createRouting();
			
			
			lot.startRouting("routingA"); //stepA is auto started -> auto start the lot
			Assert.assertEquals("Blending", lot.getState());
			
			lot.complete("stepA", "toStepBTransition"); //should be auto started at stepB
			Assert.assertEquals("Mixing", lot.getState());
			
			lot.complete("stepB", "toStepCTransition");
			lot.start("stepC"); //stepC is not auto started 
			lot.complete("stepC", "toStepDTransition"); //lot completed at operation opC and auto started and completed at stepD and split the lot into 2 other lots
			List<Lot> splittedLots = lot.getSplittedLots();
			Assert.assertEquals(2, splittedLots.size()); 
			
			Lot splittedLot1 = splittedLots.get(0);
			Assert.assertEquals("Pouring", lot.getState());
			
			splittedLot1.complete("stepE", "fromStepEToExitStepTransition");
			Assert.assertEquals("Pouring", lot.getState());		
		}
	}
	
	private ERouting createRouting() throws Exception {
		TrxInfo trxInfo1 = this.newTrxInfo("test create site1");
		ESite site1 = locationService.createSite("site1", trxInfo1);
		
		TrxInfo trxInfo2 = this.newTrxInfo("test create area1");
		EArea area1 = locationService.createArea("area1", site1, trxInfo2);
		
		TrxInfo trxInfo3 = this.newTrxInfo("test create wc1");
		EWorkCenter wc1 = locationService.createWorkCenter("wc1", area1, trxInfo3);
		
		TrxInfo trxInfo4 = this.newTrxInfo("test create wc2");
		EWorkCenter wc2 = locationService.createWorkCenter("wc2", area1, trxInfo4);
		
		TrxInfo trxInfo5 = this.newTrxInfo("test create site1");
		EProductionLine prodLine1 = routingService.createProductionLine("prodLine1", TestUtils.newHashSet(wc1, wc2), trxInfo5);
		
		TrxInfo trxInfo6 = this.newTrxInfo("test create site1");
		EProductionLine prodLine2 = routingService.createProductionLine("prodLine2", new EWorkCenter[] {wc2}, trxInfo6);
		
		EEquipment eq1 = equipmentService.createEquipment("equipment1", wc1);
		EEquipment eq2 = equipmentService.createEquipment("equipment2", wc1);
		EEquipment eq3 = equipmentService.createEquipment("equipment3", wc2);
					
		EOperation operA = routingService.createOperation("operA", prodLine1, wc1, null);
		EOperation operB = routingService.createOperation("operB", prodLine2, wc2, new EEquipment[] {eq1, eq2});
		EOperation operC = routingService.createOperation("operC", prodLine2, wc2, new EEquipment[] {eq3});
		EOperation operD = routingService.createOperation("operD", prodLine2, wc2, new EEquipment[] {eq3});
		EOperation operE = routingService.createOperation("operE", prodLine2, wc2, new EEquipment[] {eq3});
		EOperation operF = routingService.createOperation("operF", prodLine2, wc2, new EEquipment[] {eq3});
		
		EStep entryStep = routingService.newQueuingEntryStep();
		
		EStep exitStep = routingService.newQueuingExitStep();
		
		EStep stepA = routingService.newAutoStartInnerStep("stepA", "operA");	
		
		EStep stepB = routingService.newAutoStartInnerStep("stepB", "operB");
		
		//InstructionStep wis1 = routingService.newInstructionStep("WI step 1");
		//InstructionStep wis2 = routingService.newInstructionStep("WI step 1");
		//final WorkInstruction wi = routingService.newWorkInstruction("opBWI", new InstructionStep[] {wis1, wis2});
		EStep stepC = routingService.newInnerStep("stepC", "operC", "TestAutoQueueStepRule");  
		
		EStep stepD = routingService.newAutoStartAndCompleteInnerStep("stepD", "operD", "TestStepDAutoStartAndCompleteSplitRule");
		
		EStep stepE = routingService.newAutoStartInnerStep("stepE", "operE");
		
		EStep stepF = routingService.newAutoStartAndCompleteInnerStep("stepF", "operF", "TestStepFAutoStartAndCompleteRule");
		
		ETransition toStepATransition = routingService.newTransition("toStepATransition");			
		ETransition toStepBTransition = routingService.newTransition("toStepBTransition");			
		ETransition toStepCTransition = routingService.newTransition("toStepCTransition");
		ETransition toStepDTransition = routingService.newTransition("toStepDTransition");
		ETransition toStepETransition = routingService.newTransition("toStepETransition", new Quantity(1, UnitOfMeasure.Each));
		ETransition toStepFTransition = routingService.newTransition("toStepFTransition", new Quantity(2, UnitOfMeasure.Each));
		ETransition fromStepEToExitStepTransition = routingService.newTransition("fromStepEToExitStepTransition");
		ETransition fromStepFToExitStepTransition = routingService.newTransition("fromStepFToExitStepTransition");
		
		//																                                                          -> stepE (auto start)            -> 
		//entryStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start & complete & split)   			                      exitStep (queue)
		//																                                                          -> stepF (auto start & complete) -> 
		entryStep.connects(stepA, toStepATransition);
		stepA.connects(stepB, toStepBTransition);
		stepB.connects(stepC, toStepCTransition);
		stepC.connects(stepD, toStepDTransition);
		stepD.connects(stepE, toStepETransition);
		stepD.connects(stepF, toStepFTransition);
		stepE.connects(exitStep, fromStepEToExitStepTransition);
		stepF.connects(exitStep, fromStepFToExitStepTransition);
								
		ERouting routing = routingService.createRouting("routingA", entryStep);
		
		return routing;
	}
	
	@Test()
	@Rollback(true)
	public void createUnit() throws Exception {
		String unitSerialNumber = "124321";
		String unitSerialNumber2 = "124322";
		Unit unit = unitService.findUnitBySerialNumber(unitSerialNumber);
		if (unit == null) {
			Part part = partService.findPartByNumberAndRevision("1000", "A");
			if (part == null) {
				part = partService.createPart("1000", "A");
			}
			
			String woNumber = "1234";
			String woiNumber = "2233";
			WorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
			if (wo == null) {
				WorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);			
				wo = workOrderService.createWorkOrder(woNumber, new WorkOrderItem[] {woItem});
			}
			
			unit = unitService.createUnit(unitSerialNumber, woNumber, part.getNumber(), part.getRevision());
			System.out.println("unit id = " + unit.getId());
			
			Unit unit2 = unitService.createUnit(unitSerialNumber2, woNumber, "1001", "A", unit);
			System.out.println("unit2 id = " + unit2.getId());
			System.out.println("unit2's parent unit id = " + unit2.getParentUnit().getId());
			
		}
		
		Long id = unit.getId();		
		Assert.assertNotNull(id);
	}
	
	@Test
	@Rollback(true)
	public void testRouting() throws Exception {
		Plant plant1 = locationService.createPlant("plant1");
		Site site1 = locationService.createSite("site1", plant1);
		Area area1 = locationService.createArea("area1", site1);
		WorkCenter wc1 = locationService.createWorkCenter("wc1", area1);
		WorkCenter wc2 = locationService.createWorkCenter("wc2", area1);
		ProductionLine prodLine1 = productionLineService.createProductionLine("prodLine1", new WorkCenter[] {wc1, wc2});
		ProductionLine prodLine2 = productionLineService.createProductionLine("prodLine2", new WorkCenter[] {wc2});
		
		Equipment eq1 = equipmentService.createEquipment("equipment1", wc1);
		Equipment eq2 = equipmentService.createEquipment("equipment2", wc1);
		Equipment eq3 = equipmentService.createEquipment("equipment3", wc2);
					
		Operation operA = operationService.createOperation("operA", prodLine1, wc1, null);
		Operation operB = operationService.createOperation("operB", prodLine2, wc2, new Equipment[] {eq1, eq2});
		Operation operC = operationService.createOperation("operC", prodLine2, wc2, new Equipment[] {eq3});
		Operation operD = operationService.createOperation("operD", prodLine2, wc2, new Equipment[] {eq3});
		Operation operE = operationService.createOperation("operE", prodLine2, wc2, new Equipment[] {eq3});
		Operation operF = operationService.createOperation("operF", prodLine2, wc2, new Equipment[] {eq3});
		
		Step entryStep = routingService.newQueuingEntryStep();
		
		Step exitStep = routingService.newQueuingExitStep();
		
		Step stepA = routingService.newAutoStartInnerStep("stepA", "operA");	
		
		Step stepB = routingService.newAutoStartInnerStep("stepB", "operB");
		
		//InstructionStep wis1 = routingService.newInstructionStep("WI step 1");
		//InstructionStep wis2 = routingService.newInstructionStep("WI step 1");
		//final WorkInstruction wi = routingService.newWorkInstruction("opBWI", new InstructionStep[] {wis1, wis2});
		Step stepC = routingService.newInnerStep("stepC", "operC", TestAutoQueueStepRule.class.getName());  
		
		Step stepD = routingService.newAutoStartAndCompleteInnerStep("stepD", "operD", TestStepDAutoStartAndCompleteSplitRule.class.getName());
		
		Step stepE = routingService.newAutoStartInnerStep("stepE", "operE");
		
		Step stepF = routingService.newAutoStartAndCompleteInnerStep("stepF", "operF", TestStepFAutoStartAndCompleteRule.class.getName());
		
		Transition toStepATransition = routingService.newTransition("toStepATransition");			
		Transition toStepBTransition = routingService.newTransition("toStepBTransition");			
		Transition toStepCTransition = routingService.newTransition("toStepCTransition");
		Transition toStepDTransition = routingService.newTransition("toStepDTransition");
		Transition toStepETransition = routingService.newTransition("toStepETransition", new Quantity(1, UnitOfMeasure.Each));
		Transition toStepFTransition = routingService.newTransition("toStepFTransition", new Quantity(2, UnitOfMeasure.Each));
		Transition fromStepEToExitStepTransition = routingService.newTransition("fromStepEToExitStepTransition");
		Transition fromStepFToExitStepTransition = routingService.newTransition("fromStepFToExitStepTransition");
		
		//																                                                          -> stepE (auto start)            -> 
		//entryStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start & complete & split)   			                      exitStep (queue)
		//																                                                          -> stepF (auto start & complete) -> 
		entryStep.connects(stepA, toStepATransition);
		stepA.connects(stepB, toStepBTransition);
		stepB.connects(stepC, toStepCTransition);
		stepC.connects(stepD, toStepDTransition);
		stepD.connects(stepE, toStepETransition);
		stepD.connects(stepF, toStepFTransition);
		stepE.connects(exitStep, fromStepEToExitStepTransition);
		stepF.connects(exitStep, fromStepFToExitStepTransition);
								
		Routing routing = routingService.createRouting("routingA", entryStep);
		
		System.out.println("routing name: " + routing.getName());
		
		for (Step op: routing.getSteps()) {
			System.out.println("routing step: " + op.getName());
		}
		
		System.out.println("Operation A plant: " + operA.getWorkCenter().getArea().getSite().getPlant().getName());
		System.out.println("Operation A site: " + operA.getWorkCenter().getArea().getSite().getName());
		System.out.println("Operation A area: " + operA.getWorkCenter().getArea().getName());
		System.out.println("Operation A work center: " + operA.getWorkCenter().getName());
		System.out.println("Operation A production line: " + operA.getProductionLine().getName());
		for (Equipment eq: operA.getEquipments()) {
			System.out.println("Operation A equipment: " + eq.getName());
		}
		
		//lot transactions
		String lotNumber = "4321";
		Lot lot = lotService.findLotByNumber(lotNumber);
		if (lot == null) {
			Part part = partService.findPartByNumberAndRevision("1000", "A");
			if (part == null) {
				part = partService.createPart("1000", "A");
			}
			
			Part unitPart = partService.findPartByNumberAndRevision("1111", "B");
			if (unitPart == null) {
				unitPart = partService.createPart("1111", "B");
			}
			
			String woNumber = "1234";
			String woiNumber = "2233";
			WorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
			if (wo == null) {
				WorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, part);			
				wo = workOrderService.createWorkOrder(woNumber, new WorkOrderItem[] {woItem});
			}
			
			String unitSerialNumber1 = "124321";
			String unitSerialNumber2 = "124322";
			Unit unit1 = unitService.findUnitBySerialNumber(unitSerialNumber1);
			if (unit1 == null) {
				unit1 = unitService.createUnit(unitSerialNumber1, woNumber, unitPart.getNumber(), unitPart.getRevision());
				System.out.println("unit1 id = " + unit1.getId());
			}
			
			Unit unit2 = unitService.findUnitBySerialNumber(unitSerialNumber2);
			if (unit2 == null) {
				unit2 = unitService.createUnit(unitSerialNumber2, woNumber, unitPart.getNumber(), unitPart.getRevision());
				System.out.println("unit2 id = " + unit2.getId());
			}
			
			lot = lotService.createDiscreteLot(lotNumber, wo.getNumber(), new Unit[] {unit1, unit2});
			Assert.assertEquals("Created", lot.getState());
		}
		
		//																										                  -> stepE (auto start)            -> 
		//entryStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start & complete & split)   			                      exitStep (queue)
		//																                                                          -> stepF (auto start & complete) -> 

		lot.startRouting("routingA"); //stepA is auto started -> auto start the lot
		Assert.assertEquals("Started", lot.getStatus());
		
		lot.complete("stepA", "toStepBTransition"); //should be auto started at stepB
		Assert.assertEquals("Started", lot.getStatus());
		Assert.assertEquals("operB", lot.getOperation().getName());
		
		lot.complete("stepB", "toStepCTransition");
		Assert.assertEquals("Queued", lot.getStatus());
		Assert.assertEquals("operC", lot.getOperation().getName());
		
		lot.start("stepC"); //stepC is not auto started 
		Assert.assertEquals("Started", lot.getStatus());
		
		lot.complete("stepC", "toStepDTransition"); //lot completed at operation opC and auto started and completed at stepD and split the lot into 2 other lots
		List<Lot> splittedLots = lot.getSplittedLots();
		Assert.assertEquals(2, splittedLots.size()); 
		
		Lot splittedLot1 = splittedLots.get(0);
		Assert.assertEquals("operE", splittedLot1.getOperation().getName()); 
		Assert.assertEquals("Started", splittedLot1.getStatus()); //1st splitted lot auto started at stepE
		
		splittedLot1.complete("stepE", "fromStepEToExitStepTransition");
		Assert.assertEquals("ExitQueuingStep", splittedLot1.getStep().getName()); 
		Assert.assertEquals("Queued", splittedLot1.getStatus()); //complete the 1nd splitted lot at stepE and it is auto queued at ExitStep
		
		Lot splittedLot2 = splittedLots.get(1);
		Assert.assertEquals("operF", splittedLot2.getOperation().getName()); 
		Assert.assertEquals("ExitQueuingStep", splittedLot2.getStep().getName()); 
		Assert.assertEquals("Queued", splittedLot2.getStatus()); //2nd splitted lot auto started and completed at stepF and queued at ExitStep		
	}
	
	@Test
	@Rollback(true)
	public void testDataSet() throws Exception {
		//test dc1
		String dcName = "dc1";
		DataSet dc = dataCollectionService.createDataSet(dcName, new String[] {"ReworkFlag", "CustomerName", "AccountID"});
		dc.setAttributeValue("CustomerName", "customer1");
		dc.save();
		
		DataSet tdc = dataCollectionService.findDataSetByName(dcName);
		Assert.assertEquals("customer1", tdc.getAttributeValueAsString("CustomerName"));
	}
	
	@Test
	@Rollback(true)
	public void testVerticalDataSet() throws Exception {
		//test dc1
		String vdcName = "dc1";
		VerticalDataSet vdc = dataCollectionService.createVerticalDataSet(vdcName, new String[] {"ReworkFlag", "CustomerName", "AccountID"});
		Attribute attr = vdc.getAttribute("CustomerName");
		attr.setValue("customer1");
		attr.save();
		
		VerticalDataSet tdc = dataCollectionService.findDataSetByName(vdcName);
		Assert.assertEquals("customer1", tdc.getAttribute("CustomerName").getValue());
		
		Attribute newAttr = tdc.addAttribute("NewAttr");
		newAttr.setValue("new value");
		tdc.save();
		
		tdc = dataCollectionService.findDataSetByName(vdcName);
		Assert.assertEquals("new value", tdc.getAttribute("NewAttr").getValue());
	}
	
	
	@Test
	@Rollback(true)
	public void testTraveler() throws Exception {
		Routing routing = createRouting();
		
		Part orderedPart = partService.createPart("1011", "A");
		
		String woNumber = "1234";
		String woiNumber = "2233";
		WorkOrder wo = workOrderService.findWorkOrderByNumber(woNumber);
		WorkOrderItem woItem = workOrderService.newWorkOrderItem(woiNumber, orderedPart);			
		wo = workOrderService.createWorkOrder(woNumber, new WorkOrderItem[] {woItem});

		String unitSerialNumber1 = "124321";
		String unitSerialNumber2 = "124322";
		Unit unit1 = unitService.findUnitBySerialNumber(unitSerialNumber1);
		if (unit1 == null) {
			unit1 = unitService.createUnit(unitSerialNumber1, woNumber, orderedPart.getNumber(), orderedPart.getRevision());
			System.out.println("unit1 id = " + unit1.getId());
		}
		
		Unit unit2 = unitService.findUnitBySerialNumber(unitSerialNumber2);
		if (unit2 == null) {
			unit2 = unitService.createUnit(unitSerialNumber2, woNumber, orderedPart.getNumber(), orderedPart.getRevision());
			System.out.println("unit2 id = " + unit2.getId());
		}
		
		String lotNumber1 = "12345";
		String lotNumber2 = "12346";
		lotService.createDiscreteLot(lotNumber1, wo.getNumber(), new Unit[] {unit1});
		lotService.createDiscreteLot(lotNumber2, wo.getNumber(), new Unit[] {unit2});
		
		Traveler traveler = travelerService.createTraveler(wo.getNumber());
		List<Lot> lots = traveler.getLots();
		for (Lot l: lots) {
			System.out.println("Lot in the traveler: " + l.getNumber());
			l.startRouting("routingA");
		}
		
		List<Unit> units = traveler.getUnits();
		for (Unit u: units) {
			System.out.println("Unit in the traveler: " + u.getSerialNumber());			
		}
		
		Part part1 = partService.createPart("1001", "A");
		Part part2 = partService.createPart("1002", "B");
		
		lots.get(0).consumePart(part1);
		lots.get(1).consumePart(part2);
		
		List<PartConsumption> pcs1 = lots.get(0).getPartConsumptions();
		for (PartConsumption c: pcs1) {
			System.out.println("Consumed part: " + c.getConsumptionType().toString() + ", " + 
			c.getPart().getNumber() + ", " + c.getPart().getRevision() + ", " + c.getStep().getName() + ", " + c.getTime().toString());
		}
		
		List<PartConsumption> pcs2 = lots.get(1).getPartConsumptions();
		for (PartConsumption c: pcs2) {
			System.out.println("Consumed part: " + c.getConsumptionType().toString() + ", " + 
			c.getPart().getNumber() + ", " + c.getPart().getRevision() + ", " + c.getStep().getName() + ", " + c.getTime().toString());
		}
		
		List<PartConsumption> pcs = traveler.getPartConsumptions();
		for (PartConsumption c: pcs) {
			System.out.println("Consumed part by traveler: " + c.getConsumptionType().toString() + ", " + 
			c.getPart().getNumber() + ", " + c.getPart().getRevision() + ", " + c.getStep().getName() + ", " + c.getTime().toString());
		}
	}*/
}
