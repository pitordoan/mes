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
package com.cimpoint.mes.server.servlets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.PartCatergory;
import com.cimpoint.mes.common.MESConstants.Object.UnitOfMeasure;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.ECheckList;
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
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.services.BillOfMaterialService;
import com.cimpoint.mes.common.services.ContainerService;
import com.cimpoint.mes.common.services.CustomCodeService;
import com.cimpoint.mes.common.services.LotService;
import com.cimpoint.mes.common.services.RoutingService;
import com.cimpoint.mes.common.services.UnitService;
import com.cimpoint.mes.common.services.WorkOrderService;
//import com.cimpoint.mes.server.services.MESServiceUtils;
//import com.cimpoint.mes.server.servlets.testrules.TestAutoQueueStepRule;

@Service("mesConfigService")
public class MESConfigServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static boolean initialized = false;

	@Autowired
	private RoutingService routingService;
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private WorkOrderService woService;

	@Autowired
	private ContainerService containerService;
	
//	@Autowired
//	private PartRecipeService partRecipeService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private BillOfMaterialService billOfMaterialService;
	
	@Autowired
	private CustomCodeService customCodeService;

	@PostConstruct
	public void initialize() throws Exception {
		if (!initialized) {
			//initTestRouting();
			//initTestTrxObjects();
			/*initTestBOM();
			initTestApplicationAndForms();*/
			initialized = true;
		}
	}

	@PreDestroy
	public void destroy() {
	}

	private void initTestRouting() throws ServletException {
		System.out.println("MESConfigServlet initTestRouting - starts");
		try {
			createSites();
			createAreas();
			createProductionLines();
			createWorkCenters();
			createOperations();
			createEquipments();
			//createRoutings();
			System.out.println("MESConfigServlet initTestRouting - completes successfully");
		} catch (Exception ex) {
			System.out.println("MESConfigServlet initTestRouting - fails");
			throw new ServletException("Failed to initialize TestRouting", ex);
		}
	}
	
	private void initTestTrxObjects() throws ServletException {
		System.out.println("MESConfigServlet initTestTrxObjects - starts");
		try {
			createWorkOrders();
			createLots();
			System.out.println("MESConfigServlet initTestTrxObjects - completes successfully");
		} catch (Exception ex) {
			System.out.println("MESConfigServlet initTestTrxObjects - fails");
			throw new ServletException("Failed to initialize TestTrxObjects", ex);
		}
	}
	
	private void initTestBOM() throws ServletException {
		System.out.println("MESConfigServlet initTestBOM - starts");
		try {
			createPartAndBoms();
			System.out.println("MESConfigServlet initTestBOM - completes successfully");
		} catch (Exception ex) {
			System.out.println("MESConfigServlet initTestBOM - fails");
			throw new ServletException("Failed to initialize initTestBOM", ex);
		}
	}
	
	
	private void createPartAndBoms() throws Exception {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing parts");
		
		EPart computerA = billOfMaterialService.createPart("Computer", "computer", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart computerB = billOfMaterialService.createPart("Computer", "computer", "B", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart mainBoard = billOfMaterialService.createPart("MBoard", "main board", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart cpu = billOfMaterialService.createPart("CPU", "cpu", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart cpuFan = billOfMaterialService.createPart("CPUFan", "cpuFan", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart mouse = billOfMaterialService.createPart("Mouse", "mouse", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart keyboard = billOfMaterialService.createPart("Keyboard", "keyboard", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart caseA = billOfMaterialService.createPart("Case", "case", "A", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		EPart caseB = billOfMaterialService.createPart("Case", "case", "B", null, null, MESConstants.Object.PartCatergory.Catergory1, null, "03/23/2012",
				"03/23/2012", trxInfo);
		
		EBomItem computerItem = new EBomItem(null, "", "",computerA.getId(), "A", computerA.getName());
		EBomItem caseItem = new EBomItem(computerA.getId(), computerA.getRevision(), computerA.getName(), caseA.getId(), "A", caseA.getName());
		EBomItem cpuItem = new EBomItem(caseA.getId(), caseA.getRevision(), caseA.getName(), cpu.getId(), "A", cpu.getName());
		
		Set<EBomItem> list = new HashSet<EBomItem>();
		list.add(computerItem);
		list.add(caseItem);
		list.add(cpuItem);
		
		EBom e = billOfMaterialService.createBom("laptop", "Produce laptop", "A", "03/31/2012", "04/30/2012", list, trxInfo);
				
		System.out.println("Create Bom '" + e.getName() + "-" + e.getRevision() + "' successfully");
	}
	
	
	
	private void createSites() throws Exception {
		for (int i=1; i<=5; i++) {
			String name = "site" + i;
			String description = name + " description";
			
			Set<EArea> optAreas = null;
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing site");
			ESite site = routingService.createSite(name, description, optAreas, customAttributes, trxInfo);
			
			System.out.println("Create site '" + site.getName() + "' successfully.");
		}
	}
	
	private void createAreas() throws Exception {
		ESite site1 = routingService.findSiteByName("site1");		
		
		for (int i=1; i<=5; i++) {
			String areaName = "area" + i;
			String areaDescription = areaName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing area");	
			Set<EProductionLine> productionLines = null;
			
			EArea area = routingService.createArea(areaName, areaDescription, site1, productionLines, customAttributes, trxInfo);	
			
			System.out.println("Create area '" + area.getName() + "' and assign it to site1 successfully.");
		}	
		
		for (int i=6; i<=10; i++) {
			String areaName = "area" + i;
			String areaDescription = areaName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing area");	
			ESite site = null;
			Set<EProductionLine> productionLines = null;
			
			EArea area = routingService.createArea(areaName, areaDescription, site, productionLines, customAttributes, trxInfo);	
			
			System.out.println("Create area '" + area.getName() + "' and assign it to no site successfully.");
		}
	}
	
	private void createProductionLines() throws Exception {
		EArea area1 = routingService.findAreaByName("area1");		
		
		for (int i=1; i<=5; i++) {
			String pdlName = "pdl" + i;
			String pdlDescription = pdlName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing production line");	
			Set<EWorkCenter> workCenters = null;
			
			EProductionLine pdl = routingService.createProductionLine(pdlName, pdlDescription, area1, workCenters, customAttributes, trxInfo);	
			
			System.out.println("Create production line '" + pdl.getName() + "' and assign it to area1 successfully.");
		}	
		
		for (int i=6; i<=10; i++) {
			String pdlName = "pdl" + i;
			String pdlDescription = pdlName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing production line");	
			Set<EWorkCenter> workCenters = null;
			
			EProductionLine pdl = routingService.createProductionLine(pdlName, pdlDescription, area1, workCenters, customAttributes, trxInfo);	
			
			System.out.println("Create production line '" + pdl.getName() + "' and assign it to no area successfully.");
		}
	}
	
	private void createWorkCenters() throws Exception {
		EProductionLine pdl1 = routingService.findProductionLineByName("pdl1");		
		
		for (int i=1; i<=5; i++) {
			String wcName = "wc" + i;
			String wcDescription = wcName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing work center");	
			EArea area = pdl1.getArea();
			Set<EEquipment> equipments = null;
			
			EWorkCenter wc = routingService.createWorkCenter(wcName, wcDescription, area, equipments, pdl1, customAttributes, trxInfo);
			
			System.out.println("Create work center '" + wc.getName() + "' and assign it to production line pdl1 successfully.");
		}	
		
		EArea area2 = routingService.findAreaByName("area2");		
		
		for (int i=6; i<=10; i++) {
			String wcName = "wc" + i;
			String wcDescription = wcName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing work center");	
			Set<EEquipment> equipments = null;
			EProductionLine pdl = null;
			
			EWorkCenter wc = routingService.createWorkCenter(wcName, wcDescription, area2, equipments, pdl, customAttributes, trxInfo);
			
			System.out.println("Create work center '" + wc.getName() + "' and assign it to no production line but area2 successfully.");
		}
	}
	
	private void createOperations() throws Exception {
		for (int i=1; i<=5; i++) {
			String opName = "op" + i;
			String opDescription = opName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing operation");
			
			EOperation op = routingService.createOperation(opName, opDescription, customAttributes, trxInfo);
			System.out.println("Create operation '" + op.getName() + "' successfully.");
		}	
		
		for (int i=6; i<=10; i++) {
			String opName = "op" + i;
			String opDescription = opName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing operation");
			
			EOperation op = routingService.createOperation(opName, opDescription, customAttributes, trxInfo);
			System.out.println("Create operation '" + op.getName() + "' successfully.");
		}	
	}

	private void createEquipments() throws Exception {
		EWorkCenter workCenter1 = routingService.findWorkCenterByName("wc1");
		
		for (int i=1; i<=5; i++) {
			String eqName = "eq" + i;
			String eqDescription = eqName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing equipment");
			
			EEquipment eq = routingService.createEquipment(eqName, eqDescription, workCenter1, customAttributes, trxInfo);
			System.out.println("Create equipment '" + eq.getName() + "' successfully.");
		}	
		
		for (int i=6; i<=10; i++) {
			String eqName = "eq" + i;
			String eqDescription = eqName + " description";			
			CustomAttributes customAttributes = null;
			MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing equipment");
			EWorkCenter workCenter2 = null;
			
			EEquipment eq = routingService.createEquipment(eqName, eqDescription, workCenter2, customAttributes, trxInfo);
			System.out.println("Create equipment '" + eq.getName() + "' successfully.");
		}	
	}

	/*private void createRoutings() throws Exception {
		Set<EWorkCenter> optWorkCenters1 = new HashSet<EWorkCenter>();
		Set<EWorkCenter> optWorkCenters2 = new HashSet<EWorkCenter>();
		
		for (int i=1; i<=2; i++) {
			String wcName = "wc" + i;
			EWorkCenter wc = routingService.findWorkCenterByName(wcName);
			optWorkCenters1.add(wc);
		}
		
		for (int i=3; i<=5; i++) {
			String wcName = "wc" + i;
			EWorkCenter wc = routingService.findWorkCenterByName(wcName);
			optWorkCenters2.add(wc);
		}
		
		EOperation operA = routingService.createOperation("operA", "desc", null, null);
		EOperation operB = routingService.createOperation("operB", "desc", null, null);	
		EOperation operC = routingService.createOperation("operC", "desc", null, null);	
		EOperation operD = routingService.createOperation("operD", "desc", null, null);	
		EOperation operE = routingService.createOperation("operE", "desc", null, null);	
		EOperation operF = routingService.createOperation("operF", "desc", null, null);	
		
		EStepStatus queuedStepStatus = MESServiceUtils.Routings.newStartStepStatus("Queued", "Started");
		EStepStatus startedStepStatus = MESServiceUtils.Routings.newStepStatus("Started", "Completed");
		EStepStatus completedStepStatus = MESServiceUtils.Routings.newEndStepStatus("Completed");
		Set<EStepStatus> qscStepStatuses = new HashSet<EStepStatus>();
		qscStepStatuses.add(queuedStepStatus);
		qscStepStatuses.add(startedStepStatus);
		qscStepStatuses.add(completedStepStatus);
				
		//EStep startStep = MESServiceUtils.Routings.newQueuingstartStep("start", "desc", optWorkCenters1, null);	
		EStep startStep = MESServiceUtils.Routings.newAutoStartStep("start", "desc", null, optWorkCenters1, qscStepStatuses, null);
		//ECheckList 1 = Pass, 0 = Fail
		ECheckList checkList1 = new ECheckList("LI1", "Perform task1", 1, "comments", null, null);
		
		Set<ECheckList> dependentCheckLists = new HashSet<ECheckList>();
		ECheckList checkList11 = new ECheckList("LI1a", "Perform task1", 1, "comments", null, null);
		ECheckList checkList12 = new ECheckList("LI2a", "Perform task1", 1, "comments", null, null);
		ECheckList checkList13 = new ECheckList("LI3a", "Perform task1", 1, "comments", null, null);

		dependentCheckLists.add(checkList11);
		dependentCheckLists.add(checkList12);
		dependentCheckLists.add(checkList13);
		
		checkList1.setChildCheckLists(dependentCheckLists);
		
		ECheckList checkList2 = new ECheckList("LI2", "Perform task2", 1, "comments", null, null);
		
		startStep.setCheckList(checkList1);
		
		EStep endStep = MESServiceUtils.Routings.newAutoEndStep("end", "desc", null, optWorkCenters2, qscStepStatuses, null);	
		EStep stepA = MESServiceUtils.Routings.newManualInnerStep("stepA", "desc", operA, optWorkCenters1, qscStepStatuses, null);	
		stepA.setCheckList(checkList2);
		
		EStep stepB = MESServiceUtils.Routings.newManualInnerStep("stepB", "desc", operB, optWorkCenters1, qscStepStatuses, null);	
		EStep stepC = MESServiceUtils.Routings.newInnerStep("stepC", "desc", operC, optWorkCenters2, qscStepStatuses, TestAutoQueueStepRule.class.getName(), null);	
		EStep stepD = MESServiceUtils.Routings.newManualInnerStep("stepD", "desc", operD, optWorkCenters1, qscStepStatuses, null);	
		EStep stepE = MESServiceUtils.Routings.newManualInnerStep("stepE", "desc", operE, optWorkCenters1, qscStepStatuses, null);
		EStep stepF = MESServiceUtils.Routings.newManualInnerStep("stepF", "desc", operF, optWorkCenters1, qscStepStatuses, null);
		
		ETransition toStepATransition = MESServiceUtils.Routings.newTransition("ToStepA", null);			
		ETransition toStepBTransition = MESServiceUtils.Routings.newTransition("ToStepB", null);			
		ETransition toStepCTransition = MESServiceUtils.Routings.newTransition("ToStepC", null);
		ETransition toStepDTransition = MESServiceUtils.Routings.newTransition("ToStepD", null);
		ETransition toStepETransition = MESServiceUtils.Routings.newTransition("ToStepE", new Quantity(1, UnitOfMeasure.Each), null, null);
		ETransition toStepFTransition = MESServiceUtils.Routings.newTransition("ToStepF", new Quantity(2, UnitOfMeasure.Each), null, null);
		ETransition fromStepEToendStepTransition = MESServiceUtils.Routings.newTransition("fromStepEToendStepTransition", null);
		ETransition fromStepFToendStepTransition = MESServiceUtils.Routings.newTransition("fromStepFToendStepTransition", null);
		
		//																                                        -> stepE (auto start) -> 
		//startStep (queue) -> stepA (auto start) -> stepB (auto start) -> stepC (queue) -> stepD (auto start) 				              endStep (queue)
		//																                                        -> stepF (auto start) -> 
		startStep.connects(stepA, toStepATransition);
		stepA.connects(stepB, toStepBTransition);
		stepB.connects(stepC, toStepCTransition);
		stepC.connects(stepD, toStepDTransition);
		stepD.connects(stepE, toStepETransition);
		stepD.connects(stepF, toStepFTransition);
		stepE.connects(endStep, fromStepEToendStepTransition);
		stepF.connects(endStep, fromStepFToendStepTransition);
								
		CustomAttributes customAttributes = null;
		MESTrxInfo trxInfo = MESUtils.newTrxInfo("Create testing routing");
		ERouting routing = routingService.createRouting("routingA", "desc", startStep, customAttributes, trxInfo);
		
		toStepATransition = routingService.findTransitionByName("routingA", toStepATransition.getName());
		if (toStepATransition != null) {
			EStep toStep = toStepATransition.getToStep();
			if (toStep != null) {
				System.out.println("to step '" + toStep.getName());
			}
		}
		
		Set<EStep> steps = routingService.findSteps("routingA");
		for (EStep step: steps) {
			if (step.getStepStatuses() == null || step.getStepStatuses().isEmpty()) {
				System.out.println("********* no step statuses returned for step " + step.getName());
			}
			for (EStepStatus ss: step.getStepStatuses()) {
				System.out.println("Step, status: " + step.getName() + ", " + ss.getName());
			}
		}
		
		System.out.println("Create routing '" + routing.getName() + "' successfully.");
		
		EStep startStep1 = MESServiceUtils.Routings.newManualStartStep("start", "desc", null, optWorkCenters1, qscStepStatuses, null);		
		EStep endStep1 = MESServiceUtils.Routings.newAutoEndStep("end", "desc", null, optWorkCenters1, qscStepStatuses, null);		
		ETransition toStepATransition1 = MESServiceUtils.Routings.newTransition("Toend", null);			
		startStep1.connects(endStep1, toStepATransition1);
								
		CustomAttributes customAttributes1 = null;
		MESTrxInfo trxInfo1 = MESUtils.newTrxInfo("Create testing routing");
		ERouting routing1 = routingService.createRouting("routingA2", "desc", startStep1, customAttributes1, trxInfo1);
		
		toStepATransition = routingService.findTransitionByName("routingA2", toStepATransition1.getName());
		if (toStepATransition != null) {
			EStep toStep = toStepATransition.getToStep();
			if (toStep != null) {
				System.out.println("to step '" + toStep.getName());
			}
		}
				
		System.out.println("Create routing '" + routing1.getName() + routing1.getId() + "' successfully.");
	}*/

	private void createWorkOrders() throws Exception {
//		EPart part = partRecipeService.createPart("1000", "A", MESUtils.newTrxInfo("create test part"));
		EPart part = billOfMaterialService.createPart("1000", "part 1000", "A", null, null, PartCatergory.Catergory1, null, "01/01/2012", "01/01/2012", MESUtils.newTrxInfo("create test part"));
				
		for (int i=1; i<=100; i++) {
			String workOrderNumber = String.valueOf(i);
			Set<EWorkOrderItem> woiSet = new HashSet<EWorkOrderItem>();
			for (int j=1; j<=10; j++) {
				String workOrderItemNumber = workOrderNumber + String.valueOf(j);
				EWorkOrderItem woi = woService.newWorkOrderItem(workOrderItemNumber, part);
				woiSet.add(woi);
			}
			
			EWorkOrder wo = woService.createWorkOrder(workOrderNumber, woiSet, MESUtils.newTrxInfo("create test work order"),  MESConstants.Object.UnitOfMeasure.Each);
			System.out.println("Create work order '" + wo.getNumber() + "' successfully.");
		}
	}
	
	private void createContainer() throws Exception {		
		
	}
	
	private void createLots() throws Exception {		
		int x = 1;
		
		for (int i=1; i<=10; i++) {
			String workOrderNumber = String.valueOf(i);
			EWorkOrder wo = woService.findWorkOrderByNumber(workOrderNumber);
			Set<EWorkOrderItem> woItems = wo.getItems();
			Iterator<EWorkOrderItem> it = woItems.iterator();
			
			for (int j=1; j<=5; j++) {
//				EPart unitPart = partRecipeService.createPart(i + x + "1200", "A", MESUtils.newTrxInfo("create test part or unit"));
				EPart unitPart = billOfMaterialService.createPart(i + x + "1200", i + x + "1200", "A", null, null, PartCatergory.Catergory1, null, "01/01/2012", "01/01/2012", MESUtils.newTrxInfo("create test part or unit"));
				Set<EUnit> unitSet = new HashSet<EUnit>();
				
				for (int k=1; k<=10; k++) {
					String unitSerialNumber = i + x + String.valueOf(k);
					MESTrxInfo trxInfo = MESUtils.newTrxInfo("create test unit");
					EUnit unit = unitService.createUnit(unitSerialNumber, workOrderNumber, unitPart.getName(), unitPart.getRevision(), trxInfo);
					unitSet.add(unit);
				}
				
				String lotNumber = String.valueOf(x++);
				CustomAttributes customAttributes = null;
				MESTrxInfo trxInfo = MESUtils.newTrxInfo("create test lot");
				String partNumber = "1000"; //create with WOI
				String partRev = "A";
				
				String workOrderItemNumber = it.next().getNumber();
				for (EUnit eUnit : unitSet) {
					eUnit.setWorkOrderItemNumber(workOrderItemNumber);
				}
				ELot lot = lotService.createDiscreteLot(lotNumber, workOrderNumber, workOrderItemNumber, unitSet, partNumber, partRev, customAttributes, trxInfo);
				
				for (EUnit eUnit : unitSet) {
					eUnit.setLot(lot);
					MESTrxInfo tmpTrx = MESUtils.newTrxInfo("update Lot to Unit");
					unitService.saveUnit(eUnit, tmpTrx);
				}
				MESTrxInfo trxInfo2 = MESUtils.newTrxInfo("create test lot");
				String workCenterName = "wc2";
				boolean stepFlowEnforcement = true;
				//ETransitionAttributes trsnAttrs = routingService.routeLot(lot, "routingA", "start", workCenterName, trxInfo2);
				
				
				ERouting routingA = routingService.findRoutingByName("routingA");
				Set<EStep> steps = routingA.getSteps();
				//Set<EStep> steps = routingService.findSteps("routingA");
				for (EStep step: steps) {
					if (step.getName().equals("start")) {
						if (step.getStepStatuses() == null || step.getStepStatuses().isEmpty()) {
							System.out.println("------ no step statuses returned for step " + step.getName());
						}
					}
				}
				
				if (steps != null) {
					for (EStep step: steps) {
						Set<EWorkCenter> workCenters = step.getWorkCenters();
						if (workCenters == null || workCenters.isEmpty()) {
							System.out.println("No work center(s) defined for step name '" + step.getName() + "' of routingA");
						}
						
						for (EWorkCenter wc: workCenters) {
							Set<EStep> wcSteps = wc.getSteps();
							if (wcSteps == null || wcSteps.isEmpty()) {
								System.out.println("No step(s) persisted for work center name '" + wc.getName() + "'");
							}
						}
						
						Set<EStepStatus> stepStatuses = step.getStepStatuses();
						if (stepStatuses == null || stepStatuses.isEmpty()) {
							System.out.println("No step status(es) defined for step name '" + step.getName() + "' of routingA");
						}
						
						/*EStepStatus startStepStatus = step.getStartStepStatus();
						if (startStepStatus == null) {
							throw new Exception("Start step status is not defined for step name '" + step.getName() + "' of routing " + routingName);
						}*/				
					}
				}
				
				routingService.joinRouting(lot.getId(), MESConstants.Object.Type.Lot, "routingA", "start", 
						workCenterName, stepFlowEnforcement, trxInfo2);
								
				System.out.println("Create lot '" + lot.getNumber() + "' successfully. in WO number " + lot.getWorkOrderNumber() + " and in WOI number '" + lot.getWorkOrderItemNumber() + "'");
				
				//trsnAttrs = routingService.findTransitionAttributes(lot.getId(), MESConstants.Object.Type.Lot);
				/*if (trsnAttrs != null) {
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
		}
	}	
	
	/*private void initTestApplicationAndForms() throws ServletException {
		System.out.println("MESConfigServlet initTestForms - starts");
		try {
			createForm();
			createApplication();
			System.out.println("MESConfigServlet initTestApplicationAndForms - completes successfully");
		} catch (Exception ex) {
			System.out.println("MESConfigServlet initTestApplicationAndForms - fails");
			throw new ServletException("Failed to initialize initTestForms", ex);
		}
	}

	private void createForm() throws Exception {
		String name = "form1";
		EFormField formField1 = MESServiceUtils.Forms.newTextField("textField1", "Name", 100, 20, 1, true, true, 50);
		EFormField formField2 = MESServiceUtils.Forms.newTextField("textField2", "Description", 300, 20, 1, true, true, 255);
		EFormField formField3 = MESServiceUtils.Forms.newSelectField("textField3", "Option", 100, 20,  1, true, true);
		EFormField[] fieldArr = new EFormField[] {formField1, formField2, formField3};
		Set<EFormField> fieldSet = new HashSet<EFormField>();
		fieldSet.addAll(Arrays.asList(fieldArr));
		
		TrxInfo trxInfo = Utils.newTrxInfo("create test form");
		EForm form = customCodeService.createForm(name, fieldSet, trxInfo);	
		
		System.out.println("Create form '" + form.getName() + "' successfully");
	}*/
	
	/*private void createApplication() throws Exception {
		String name = "app1";
		String description = "desc1";
		ELayout layout = MESServiceUtils.Forms.newVLayout();
				
		EFormField formField1 = MESServiceUtils.Forms.newTextField("textField1", "Name", 100, 20, 1, true, true, 50);
		EFormField formField2 = MESServiceUtils.Forms.newTextField("textField2", "Description", 300, 20, 1, true, true, 255);
		EFormField formField3 = MESServiceUtils.Forms.newSelectField("textField3", "Option", 100, 20,  1, true, true);
		
		EForm form = MESServiceUtils.Forms.newForm("form2");		
		form.addFormField(formField1);
		form.addFormField(formField2);
		form.addFormField(formField3);
		
		layout.addForm(form);
		
		TrxInfo trxInfo = Utils.newTrxInfo("create test application");
		EApplication app = customCodeService.createApplication(name, "1.0.0", "Applications", description, layout, trxInfo);	
		
		EApplication newApp = customCodeService.findApplicationByName(name);
		
		Set<EApplication> apps = customCodeService.findAllApplications();
		for (EApplication a: apps) {
			Set<EForm> forms = a.getLayout().getForms();
			System.out.println(forms.size());
		}
		
		System.out.println("Create application '" + app.getName() + "' successfully");
	}*/
}
