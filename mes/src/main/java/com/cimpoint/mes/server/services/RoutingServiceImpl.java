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
package com.cimpoint.mes.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.services.UserService;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.MESConstants.Object.Type;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.EDictionary;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.AreaFilter;
import com.cimpoint.mes.common.filters.EquipmentFilter;
import com.cimpoint.mes.common.filters.OperationFilter;
import com.cimpoint.mes.common.filters.ProductionLineFilter;
import com.cimpoint.mes.common.filters.RoutingFilter;
import com.cimpoint.mes.common.filters.SiteFilter;
import com.cimpoint.mes.common.filters.StepFilter;
import com.cimpoint.mes.common.filters.WorkCenterFilter;
import com.cimpoint.mes.common.services.RoutingService;
import com.cimpoint.mes.server.repositories.AreaRepository;
import com.cimpoint.mes.server.repositories.EquipmentRepository;
import com.cimpoint.mes.server.repositories.OperationRepository;
import com.cimpoint.mes.server.repositories.ProductionLineRepository;
import com.cimpoint.mes.server.repositories.RoutingRepository;
import com.cimpoint.mes.server.repositories.SiteRepository;
import com.cimpoint.mes.server.repositories.StepRepository;
import com.cimpoint.mes.server.repositories.StepStatusRepository;
import com.cimpoint.mes.server.repositories.TransitionAttributesRepository;
import com.cimpoint.mes.server.repositories.TransitionRepository;
import com.cimpoint.mes.server.repositories.WorkCenterRepository;
import com.cimpoint.mes.server.rules.DefaultAutoStartStepRule;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("routingService")
public class RoutingServiceImpl extends RemoteServiceServlet implements RoutingService {
	private static final long serialVersionUID = 4343685623299701110L;
	private final String NL = System.getProperty("line.separator");

	@Autowired
	private SiteRepository siteRep;
	
	@Autowired
	private AreaRepository areaRep;
	
	@Autowired
	private WorkCenterRepository workCenterRep;
	
	@Autowired
	private StepRepository stepRep;
	
	@Autowired
	private StepStatusRepository stepStatusRep;

	@Autowired
	private TransitionRepository transitionRep;

	@Autowired
	private RoutingRepository routingRep;

	@Autowired
	private OperationRepository operationRep;
	
	@Autowired
	private ProductionLineRepository productionLineRep;
	
	@Autowired
	private EquipmentRepository equipmentRep;
	
	@Autowired
	private TransitionAttributesRepository transitionAttributesRep;
	
	@Autowired
	private TransitionAttributesRepository trsnAttrsRepository;
	
	@Autowired
	private UserService userService;
	
	//------------ All ------------------
	@Override
	public Map<String, List<String>> findAllModelNames() throws Exception {
		Map<String, List<String>> buffer = new HashMap<String, List<String>>();
		
		Set<String> siteNames = findAllSiteNames();
		buildBuffer("Sites", siteNames, buffer);
		
		Set<String> areaNames = findAllAreaNames();
		buildBuffer("Areas", areaNames, buffer);
		
		Set<String> productionLineNames = findAllProductionLineNames();
		buildBuffer("Production Lines", productionLineNames, buffer);
		
		Set<String> workCenterNames = findAllWorkCenterNames();
		buildBuffer("Work Centers", workCenterNames, buffer);
		
		Set<String> equipmentNames = findAllEquipmentNames();
		buildBuffer("Equipments", equipmentNames, buffer);
		
		Set<String> routingNames = findAllRoutingNames();
		buildBuffer("Routings", routingNames, buffer);
		
		return buffer;
	}
	
	@Override
	public String findAllModelNamesAsJSON() throws Exception {
		StringBuilder buffer = new StringBuilder();
		buffer.append("[" + NL);
		
		Set<String> siteNames = findAllSiteNames();
		buildJSONBuffer(siteNames, buffer);
		
		Set<String> areaNames = findAllAreaNames();
		buildJSONBuffer(areaNames, buffer);
		
		Set<String> productionLineNames = findAllProductionLineNames();
		buildJSONBuffer(productionLineNames, buffer);
		
		Set<String> workCenterNames = findAllWorkCenterNames();
		buildJSONBuffer(workCenterNames, buffer);
		
		Set<String> equipmentNames = findAllEquipmentNames();
		buildJSONBuffer(equipmentNames, buffer);
		
		Set<String> routingNames = findAllRoutingNames();
		buildJSONBuffer(routingNames, buffer);
		
		//remove the last commas
		if (buffer.length() > 1 && buffer.substring(buffer.length() - 1, buffer.length()).equals(",")) {
			buffer.replace(buffer.length()-1, buffer.length(), "");
		}
		
		buffer.append("]" + NL);
		
		return buffer.toString();
	}
	
	private void buildBuffer(String category, Set<String> names, Map<String, List<String>> buffer) {
		List<String> list = new ArrayList<String>();		
		if (names != null) {
			for (String name: names) {
				list.add(name);				
			}
		}
		
		buffer.put(category, list);
	}
	
	private void buildJSONBuffer(Set<String> names, StringBuilder buffer) {
		if (names != null) {
			for (String name: names) {
				buffer.append(NL + "{");
				buffer.append("\"name\":");
				buffer.append("\"" + name + "\"");
				buffer.append("}," + NL);				
			}
		}
	}
	
	//------------- site ----------------
	@Override
	public ESite findSiteById(long id) throws Exception {
		return siteRep.findById(id);
	}

	@Override
	public ESite findSiteByName(String name) throws Exception {
		return siteRep.findByName(name);
	}

	@Override
	public Set<ESite> findAllSites() throws Exception {
		return siteRep.findAll();
	}

	@Override
	public Set<String> findAllSiteNames() throws Exception {
		return siteRep.findAllNames();
	}
	
	@Override
	public Set<String> findSiteNames(SiteFilter filter) throws Exception {
		return siteRep.findSiteNames(filter);
	}

	@Override
	public Set<ESite> findSites(SiteFilter filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ESite createSite(String name, String description, Set<EArea> optAreas, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return siteRep.createSite(name, description, optAreas, customAttributes, trxInfo);
	}

	@Override
	public void saveSite(ESite site, MESTrxInfo trxInfo) throws Exception {
		siteRep.update(site, trxInfo);
	}
	
	@Deprecated
	@Override
	public void removeSite(ESite site, MESTrxInfo trxInfo) throws Exception {
		siteRep.remove(site.getId(), trxInfo);
	}
	
	@Override
	public void removeSite(String siteName, MESTrxInfo trxInfo) throws Exception {
		ESite site = siteRep.findByName(siteName);
		if (site != null) {
			siteRep.remove(site.getId(), trxInfo);
		}
	}

	//------------------ area --------------
	@Override
	public EArea findAreaById(long id) throws Exception {
		return areaRep.findById(id);
	}

	@Override
	public EArea findAreaByName(String name) throws Exception {
		return areaRep.findByName(name);
	}

	@Override
	public Set<EArea> findAllAreas() throws Exception {
		return areaRep.findAll();
	}
	
	@Override
	public Set<String> findAllAreaNames() throws Exception {
		return areaRep.findAllNames();
	}

	@Override
	public Set<EArea> findAreas(AreaFilter filter) throws Exception {
		return areaRep.findAreas(filter);
	}

	@Override
	public Set<String> findAreaNames(AreaFilter filter) throws Exception {
		return areaRep.findAreaNames(filter);
	}
	
	@Override
	public EArea createArea(String name, String description, ESite site, Set<EProductionLine> productionLines, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return areaRep.createArea(name, description, site, productionLines, customAttributes, trxInfo);
	}

	@Override
	public void saveArea(EArea area, MESTrxInfo trxInfo) throws Exception {
		areaRep.update(area, trxInfo);
	}
	
	@Override
	public void saveAreas(Set<EArea> areas, MESTrxInfo trxInfo) throws Exception {
		if (areas != null) {
			for (EArea a: areas) {
				saveArea(a, trxInfo);
			}
		}
	}
	
	@Deprecated
	@Override
	public void removeArea(EArea area, MESTrxInfo trxInfo) throws Exception {
		areaRep.remove(area.getId(), trxInfo);
	}
	
	@Override
	public void removeArea(String areaName, MESTrxInfo trxInfo) throws Exception {
		EArea area = areaRep.findByName(areaName);
		if (area != null) {
			areaRep.remove(area.getId(), trxInfo);
		}
	}

	//-------------- work center ---------------
	@Override
	public EWorkCenter findWorkCenterById(long id) throws Exception {
		return workCenterRep.findById(id);
	}

	@Override
	public EWorkCenter findWorkCenterByName(String name) throws Exception {
		return workCenterRep.findByName(name);
	}

	@Override
	public Set<EWorkCenter> findAllWorkCenters() throws Exception {
		return workCenterRep.findAll();
	}

	@Override
	public Set<String> findAllWorkCenterNames() throws Exception {
		return workCenterRep.findAllNames();
	}
	
	@Override
	public Set<String> findWorkCenterNames(WorkCenterFilter filter) throws Exception {
		return workCenterRep.findWorkCenterNames(filter);
	}

	@Override
	public Set<EWorkCenter> findWorkCenters(WorkCenterFilter filter) throws Exception {
		return workCenterRep.findWorkCenters(filter);
	}

	@Override
	public void saveWorkCenters(Set<EWorkCenter> workCenters, MESTrxInfo trxInfo) throws Exception {
		if (workCenters != null) {
			for (EWorkCenter wc: workCenters) {
				saveWorkCenter(wc, trxInfo);
			}
		}		
	}
	
	@Override
	public void saveWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception {
		workCenterRep.update(workCenter, trxInfo);
	}

	@Override
	public EWorkCenter createWorkCenter(String name, String description, EArea area, 
			Set<EEquipment> equipments, EProductionLine optProductionLine, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return workCenterRep.createWorkCenter(name, description, area, equipments, optProductionLine, customAttributes, trxInfo);
	}

	@Deprecated
	@Override
	public void removeWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception {
		workCenterRep.remove(workCenter.getId(), trxInfo);
	}
	
	@Override
	public void removeWorkCenter(String workCenterName, MESTrxInfo trxInfo) throws Exception {
		EWorkCenter wc = workCenterRep.findByName(workCenterName);
		if (wc != null) {
			workCenterRep.remove(wc.getId(), trxInfo);
		}
	}
		
	//-------------- step --------------------
	@Override
	public EStep findStepById(Long id) throws Exception {
		return stepRep.findById(id);
	}
	
	@Override
	public EStep findStepByName(String routingName, String stepName) throws Exception {
		return stepRep.findStepByName(routingName, stepName);
	}
	
	@Override
	public Set<EStep> findSteps(String routingName) throws Exception {
		return stepRep.findSteps(routingName);
	}
	
	@Override
	public Set<String> findStepNames(String routingName) throws Exception {
		return stepRep.findStepNames(routingName);
	}

	@Override
	public Set<String> findStepNames(StepFilter filter) throws Exception {
		return stepRep.findStepNames(filter);
	}

	@Override
	public Set<EStep> findSteps(StepFilter filter) throws Exception {
		return stepRep.findSteps(filter);
	}
	
	@Override
	public EStep createStep(StepType type, String name, String description, ERouting optRouting, 
			EOperation optOperation, Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		boolean hasStartStatus = false;
		for (EStepStatus ss: stepStatuses) {
			if (ss.isStarting()) {
				hasStartStatus = true;
				break;
			}
		}
		
		if (!hasStartStatus) {
			throw new Exception("Start step status is not defined.");
		}
		
		boolean hasEndStatus = false;
		for (EStepStatus ss: stepStatuses) {
			if (ss.isEnding()) {
				hasEndStatus = true;
				break;
			}
		}
		
		if (!hasEndStatus) {
			throw new Exception("End step status is not defined.");
		}
		
		return stepRep.createStep(type, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, 
			customAttributes, trxInfo);
	}

	@Override
	public void saveStep(EStep step, MESTrxInfo trxInfo) throws Exception {
		stepRep.update(step, trxInfo);
	}
	
	@Override
	public void saveSteps(Set<EStep> steps, MESTrxInfo trxInfo) throws Exception {
		if (steps != null) {
			for (EStep s: steps) {
				saveStep(s, trxInfo);
			}
		}
	}
	
	@Deprecated
	@Override
	public void removeStep(EStep step, MESTrxInfo trxInfo) throws Exception {
		stepRep.remove(step.getId(), trxInfo);
	}	
	
	@Override
	public void removeStep(String stepName, MESTrxInfo trxInfo) throws Exception {
		EStep step = stepRep.findByName(stepName);
		if (step != null) {
			stepRep.remove(step.getId(), trxInfo);
		}
	}
	
	//------------------ step status ----------------
	@Override
	public EStepStatus findStepStatusById(Long id) throws Exception {
		return stepStatusRep.findById(id);
	}

	@Override
	public EStepStatus findStepStatusByName(String stepStatusName) throws Exception {
		return stepStatusRep.findByName(stepStatusName);
	}

	@Override
	public EStepStatus createStepStatus(String name, boolean isStarting, boolean isEnding, String nextDefaultStatusName, MESTrxInfo trxInfo)
			throws Exception {
		return stepStatusRep.creatStepStatus(name, isStarting, isEnding, nextDefaultStatusName, trxInfo);
	}

	@Override
	public void saveStepStatus(EStepStatus stepStatus, MESTrxInfo trxInfo) throws Exception {
		stepStatusRep.update(stepStatus, trxInfo);
	}

	@Override
	public void removeStepStatus(String stepStatusName, MESTrxInfo trxInfo) throws Exception {
		EStepStatus ss = stepStatusRep.findByName(stepStatusName);
		if (ss != null) {
			stepStatusRep.remove(ss, trxInfo);
		}
	}
	
	//------------------ routing --------------
	@Override
	public ERouting findRoutingById(Long id) throws Exception {
		return routingRep.findById(id);
	}
	
	@Override
	public Set<ERouting> findAllRoutings() throws Exception {
		return routingRep.findAll();
	}
	
	@Override
	public Set<String> findAllRoutingNames() throws Exception {
		return routingRep.findAllNames();
	}	

	/*@Deprecated
	@Override
	public Set<String> findAllRoutingNameWithRevisions() throws Exception {
		return routingRep.findAllRoutingNameWithRevisions();
	}*/
		
	/*@Deprecated
	@Override
	public ERouting findRoutingByNameAndRevision(String name, String revision) throws Exception {
		return routingRep.findByAttributes(new String[] {"name", "revision"}, name, revision);
	}*/

	@Deprecated
	@Override
	public Set<ERouting> findRoutingsByName(String name) throws Exception {
		return routingRep.findAllByName(name);
	}

	@Override
	public ERouting findRoutingByName(String name) throws Exception {
		return routingRep.findByName(name);
	}
	
	@Override
	public void saveRouting(ERouting routing, MESTrxInfo trxInfo) throws Exception {
		EStep entryStep = null;
		Set<EStep> allSteps = routing.getSteps();
		for (EStep s: allSteps) {
			if (s.getType() == StepType.Start) {
				entryStep = s;
				break;
			}
		}
		
		if (entryStep != null) {
			allSteps = new HashSet<EStep>();
			allSteps.add(entryStep);
			buildSteps(entryStep, allSteps);	//include exit step
			createOrUpdateDependencies(routing.getName(), allSteps, trxInfo);
			routing.setSteps(allSteps);
			routingRep.update(routing, trxInfo);
		}
		else {
			throw new Exception("Entry step is not found");
		}
	}
	
	@Deprecated
	@Override
	public void removeRouting(ERouting routing, MESTrxInfo trxInfo)
			throws Exception {
		routingRep.remove(routing.getId(), trxInfo);
	}
	
	@Override
	public void removeRouting(String routingName, MESTrxInfo trxInfo) throws Exception {
		ERouting r = routingRep.findByName(routingName);
		if (r != null) {
			routingRep.remove(r.getId(), trxInfo);
		}
	}
	
	/*@Deprecated
	@Override
	public ERouting createRouting(String routingName, String description, String revision, EStep entryStep, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		EStep exitStep = null;
		Set<EStep> allSteps = null;
		
		if (entryStep != null) {				
			allSteps = new HashSet<EStep>();
			allSteps.add(entryStep);
			buildSteps(entryStep, allSteps);	//include exit step
			
			for (EStep step: allSteps) {			
				if (exitStep == null && step.getType() == StepType.Exit) {
					exitStep = step;
					break;
				}
			}
			
			if (exitStep == null) {
				throw new Exception("Exit step is missing or there is no transition to exit step found.");			
			}
			
			createOrUpdateDependencies(routingName, allSteps, trxInfo);
		}
		
		ERouting routing = routingRep.createRouting(routingName, description, revision, entryStep, exitStep, allSteps, customAttributes, trxInfo);
		return routing;
	}*/
	
	@Override
	public ERouting createRouting(String routingName, String description, EStep startStep, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		EStep exitStep = null;
		Set<EStep> allSteps = null;
		
		if (startStep != null) {				
			allSteps = new HashSet<EStep>();
			allSteps.add(startStep);
			buildSteps(startStep, allSteps);	//include end step
			
			for (EStep step: allSteps) {			
				if (exitStep == null && step.getType() == StepType.End) {
					exitStep = step;
					break;
				}
			}
			
			if (exitStep == null) {
				throw new Exception("End step is missing or there is no transition to end step found.");			
			}
			
			allSteps = createOrUpdateDependencies(routingName, allSteps, trxInfo);
		}
		
		ERouting routing = routingRep.createRouting(routingName, description, startStep, exitStep, allSteps, customAttributes, trxInfo);
		
		Set<EStep> steps = routing.getSteps();
		if (steps != null) {
			for (EStep step: steps) {
				Set<EWorkCenter> workCenters = step.getWorkCenters();
				if (workCenters == null || workCenters.isEmpty()) {
					throw new Exception("No work center(s) persisted for step name '" + step.getName() + "' of routing " + routingName);
				}
				else {
					for (EWorkCenter wc: workCenters) {
						if (wc.getSteps() == null || wc.getSteps().isEmpty()) {
							throw new Exception("No steps(s) persisted for work center name '" + wc.getName());
						}
					}
				}
				
				Set<EStepStatus> stepStatuses = step.getStepStatuses();
				if (stepStatuses == null || stepStatuses.isEmpty()) {
					throw new Exception("No step status(es) defined for step name '" + step.getName() + "' of routing " + routingName);
				}
				else {
					for (EStepStatus ss: stepStatuses) {
						if (ss.getSteps() == null || ss.getSteps().isEmpty()) {
							throw new Exception("No steps(s) persisted for step status name '" + ss.getName());
						}
					}
				}
				
				Set<ETransition> incomingTransitions = step.getIncomingTransitions();
				if (incomingTransitions == null || incomingTransitions.isEmpty()) {
					throw new Exception("No incoming transition(s) defined for step name '" + step.getName() + "' of routing " + routingName);
				}
				else {
					for (ETransition trsn: incomingTransitions) {
						if (trsn.getToStep() == null) {
							throw new Exception("No toStep persisted for transition name '" + trsn.getName());
						}
					}
				}
				
				Set<ETransition> outgoingTransitions = step.getOutgoingTransitions();
				if (outgoingTransitions == null || outgoingTransitions.isEmpty()) {
					throw new Exception("No outgoing transition(s) defined for step name '" + step.getName() + "' of routing " + routingName);
				}
				else {
					for (ETransition trsn: outgoingTransitions) {
						if (trsn.getFromStep() == null) {
							throw new Exception("No fromStep persisted for transition name '" + trsn.getName());
						}
					}
				}
			}
		}
		
		return routing;
	}
		
	private Set<EStep> createOrUpdateDependencies(String routingName, Set<EStep> allSteps, MESTrxInfo trxInfo) throws Exception {
		Set<EStep> createdSteps = new HashSet<EStep>();
		
		// create steps w/o child transitions and step statuses
		for (EStep step : allSteps) {
			if (step.getId() == null || step.getId() == 0L) {
				step.setId(null); //fix the dummy value set by the custom serializer
			}
			
			if (step.getId() == null) {
				step = stepRep.createStep(step, trxInfo);
			}
			else {
				step = stepRep.update(step, trxInfo);
			}
			
			createdSteps.add(step);
		}
		
		return createdSteps;
	}
		
	public Set<ETransition> getAllTransitions(Set<EStep> steps) {
		Map<String, ETransition> transitions = new HashMap<String, ETransition>();
		
		for (EStep step: steps) {
			Set<ETransition> outgoingTransitions = step.getOutgoingTransitions();
			if (outgoingTransitions != null && !outgoingTransitions.isEmpty()) {
				for (ETransition trsn: outgoingTransitions) {
					if (transitions.containsKey(trsn.getName())) {
						transitions.get(trsn.getName()).setFromStep(step);						
					}
					else {
						trsn.setFromStep(step);
						transitions.put(trsn.getName(), trsn);
					}
				}
			}
			
			Set<ETransition> incomingTransitions = step.getIncomingTransitions();
			if (incomingTransitions != null && !incomingTransitions.isEmpty()) {
				for (ETransition trsn: incomingTransitions) {
					if (transitions.containsKey(trsn.getName())) {
						transitions.get(trsn.getName()).setToStep(step);						
					}
					else {
						trsn.setToStep(step);
						transitions.put(trsn.getName(), trsn);
					}
				}
			}			
		}
		
		return new HashSet<ETransition>(transitions.values());
	}
		
	private void buildSteps(EStep step, Set<EStep> steps) {
		Set<ETransition> outGoingTransitions = step.getOutgoingTransitions();
		if (outGoingTransitions != null && !outGoingTransitions.isEmpty()) {
			for (ETransition t: outGoingTransitions) {
				EStep toStep = t.getToStep();
				if (toStep != null) {
					steps.add(toStep);
					buildSteps(toStep, steps);
				}
			}
		}
	}

	//--------------- transition --------------------
	@Override
	public ETransition findTransitionById(Long id) throws Exception {
		return transitionRep.findById(id);
	}

	@Override
	public ETransition findTransitionByName(String routingName, String transitionName) throws Exception {
		return transitionRep.findTransitionByName(routingName, transitionName);
	}
	
	@Override
	public Set<ETransition> findAllTransitions() {
		return transitionRep.findAll();
	}
	
	@Override
	public Set<String> findAllTransitionNames() {
		return transitionRep.findAllNames();
	}
	
	@Override
	public void saveTransition(ETransition transition, MESTrxInfo trxInfo) throws Exception {
		transitionRep.update(transition, trxInfo);
	}

	@Deprecated
	@Override
	public void removeTransition(ETransition transition, MESTrxInfo trxInfo)
			throws Exception {
		transitionRep.remove(transition.getId(), trxInfo);
	}
	
	@Override
	public void removeTransition(String transitionName, MESTrxInfo trxInfo) throws Exception {
		ETransition trsn = transitionRep.findByName(transitionName);
		if (trsn != null) {
			transitionRep.remove(trsn.getId(), trxInfo);
		}
	}
	
	//------------------ operation -------------------------
	@Override
	public EOperation findOperationById(long id) throws Exception {
		return operationRep.findById(id);
	}

	@Override
	public EOperation findOperationByName(String name) throws Exception {
		return operationRep.findByName(name);
	}

	@Override
	public Set<EOperation> findAllOperations() throws Exception {
		return operationRep.findAll();
	}
	
	@Override
	public Set<String> findAllOperationNames() throws Exception {
		return operationRep.findAllNames();
	}
	
	@Override
	public Set<String> findOperationNames(OperationFilter filter) throws Exception {
		return operationRep.findOperationNames(filter);
	}
	
	@Override
	public EOperation createOperation(String name, String description, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return operationRep.createOperation(name, description, customAttributes, trxInfo);
	}

	@Override
	public void saveOperation(EOperation operation, MESTrxInfo trxInfo) throws Exception {
		//TODO trxInfo
		operationRep.update(operation, trxInfo);
	}

	@Deprecated
	@Override
	public void removeOperation(EOperation operation, MESTrxInfo trxInfo)
			throws Exception {
		operationRep.remove(operation.getId(), trxInfo);		
	}

	@Override
	public void removeOperation(String operationName, MESTrxInfo trxInfo)
			throws Exception {
		EOperation op = operationRep.findByName(operationName);
		if (op != null) {
			operationRep.remove(op.getId(), trxInfo);
		}	
	}
	
	//--------------- production line ---------------
	@Override
	public EProductionLine findProductionLineById(long id) throws Exception {
		return productionLineRep.findById(id);
	}

	@Override
	public EProductionLine findProductionLineByName(String name) throws Exception {
		return productionLineRep.findByName(name);
	}

	@Override
	public Set<EProductionLine> findAllProductionLines() throws Exception {
		return productionLineRep.findAll();
	}
	
	@Override
	public Set<String> findAllProductionLineNames() throws Exception {
		return productionLineRep.findAllNames();
	}
		
	@Override
	public Set<String> findProductionLineNames(ProductionLineFilter filter) throws Exception {
		return productionLineRep.findProductionLineNames(filter);
	}

	@Override
	public Set<EProductionLine> findProductionLines(ProductionLineFilter filter) throws Exception {
		return productionLineRep.findProductionLines(filter);
	}

	@Override
	public void saveProductionLines(Set<EProductionLine> prodLines, MESTrxInfo trxInfo) throws Exception {
		if (prodLines != null) {
			for (EProductionLine e: prodLines) {
				saveProductionLine(e, trxInfo);
			}
		}
	}
	
	@Override
	public void saveProductionLine(EProductionLine productionLine, MESTrxInfo trxInfo) throws Exception {
		productionLineRep.update(productionLine, trxInfo);
	}

	@Override
	public EProductionLine createProductionLine(String name, String description, EArea optArea, Set<EWorkCenter> optWorkCenters, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return productionLineRep.createProductionLine(name, description, optArea, optWorkCenters, customAttributes, trxInfo);
	}

	@Deprecated
	@Override
	public void removeProductionLine(EProductionLine productionLine,
			MESTrxInfo trxInfo) throws Exception {
		productionLineRep.remove(productionLine.getId(), trxInfo);
	}
	
	@Override
	public void removeProductionLine(String productionLineName, MESTrxInfo trxInfo)
			throws Exception {
		EProductionLine prodLine = productionLineRep.findByName(productionLineName);
		if (prodLine != null) {
			productionLineRep.remove(prodLine.getId(), trxInfo);
		}	
	}

	//----------------- equipment ----------------------
	@Override
	public EEquipment findEquipmentById(long id) throws Exception {
		return equipmentRep.findById(id);
	}

	@Override
	public EEquipment findEquipmentByName(String name) throws Exception {
		return equipmentRep.findByName(name);
	}

	@Override
	public Set<EEquipment> findAllEquipments() throws Exception {
		return equipmentRep.findAll();
	}

	@Override
	public Set<String> findAllEquipmentNames() throws Exception {
		return equipmentRep.findAllNames();
	}

	@Override
	public Set<String> findEquipmentNames(EquipmentFilter filter) throws Exception {
		return equipmentRep.findEquipmentNames(filter);
	}

	@Override
	public Set<EEquipment> findEquipments(EquipmentFilter filter) throws Exception {
		return equipmentRep.findEquipments(filter);
	}
	
	@Override
	public EEquipment createEquipment(String equipmentName, String description, EWorkCenter workCenter, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return equipmentRep.createEquipment(equipmentName, description, workCenter, customAttributes, trxInfo);
	}

	@Override
	public void saveEquipment(EEquipment equipment, MESTrxInfo trxInfo) throws Exception {
		equipmentRep.update(equipment, trxInfo);
	}

	@Deprecated
	@Override
	public void removeEquipment(EEquipment equipment, MESTrxInfo trxInfo) throws Exception {
		equipmentRep.remove(equipment.getId(), trxInfo);
	}

	@Override
	public void removeEquipment(String equipmentName, MESTrxInfo trxInfo)
			throws Exception {
		EEquipment eq = equipmentRep.findByName(equipmentName);
		if (eq != null) {
			equipmentRep.remove(eq.getId(), trxInfo);
		}	
	}
	
	@Override
	public void saveEquipments(Set<EEquipment> equipments, MESTrxInfo trxInfo) throws Exception {
		if (equipments != null) {
			for (EEquipment e: equipments) {
				saveEquipment(e, trxInfo);
			}
		}
	}

	@Override
	public ETransition createTransition(String name, Quantity transferQuantity,
			EDictionary reasonDictionary, CustomAttributes customAttributes,
			MESTrxInfo trxInfo) throws Exception {
		return transitionRep.createTransition(name, transferQuantity, reasonDictionary, customAttributes, trxInfo);
	}

	//-------------------------- routing -----------------------------------
	/*@Override
	public Set<String> findRevisionsByRoutingName(String routingName) throws Exception {
		return routingRep.findRevisionsByRoutingName(routingName);
	}*/

	@Override
	public Set<String> findRoutingNames(RoutingFilter filter) throws Exception {
		return routingRep.findRoutingNames(filter);
	}
	
	@Deprecated
	@Override
	public ETransitionAttributes route(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception {
		ERouting routing = routingRep.findByName(routingName);
		if (routing == null) {
			throw new Exception("Routing by name is not found: " + routingName);
		}

		EStep step = null;
		if (stepName == null) {
			step = stepRep.findById(routing.getStartStepId());
			if (step == null) {
				throw new Exception("The routing doesn't have an entry step");
			}
		} else {
			step = stepRep.findByName(stepName);
			if (step == null) {
				throw new Exception("Step by name is not found: " + stepName);
			}
		}

		ETransitionAttributes trsnAttrs = transitionAttributesRep.findTransitionAttributes(objectId, objectType);
		boolean foundTrsnAttrs = true;
		if (trsnAttrs == null) {
			trsnAttrs = new ETransitionAttributes(objectId, objectType);
			foundTrsnAttrs = false;
		}
		
		trsnAttrs.setObjectId(objectId);
		trsnAttrs.setObjectType(objectType);
		trsnAttrs.setRoutingName(routingName);
		trsnAttrs.setStepName(stepName);
		
		if (workCenterName != null && workCenterName.length() > 0) {
			EWorkCenter atWorkCenter = workCenterRep.findByName(workCenterName);
			if (atWorkCenter != null) {
				String opName = null;
				if (step.getOperation() != null) {
					EOperation operation = operationRep.findById(step.getOperation().getId());
					opName = operation.getName();
				}

				EProductionLine prodLine = atWorkCenter.getProductionLine();
				String prodLineName = (prodLine != null) ? prodLine.getName() : null;
				String areaName = (prodLine != null && prodLine.getArea() != null) ? prodLine.getArea().getName() : null;
				String siteName = (prodLine != null && prodLine.getArea() != null && prodLine.getArea().getSite() != null) ? prodLine.getArea()
						.getSite().getName() : null;
				trsnAttrs.setSiteName(siteName);
				trsnAttrs.setAreaName(areaName);
				trsnAttrs.setWorkCenterName(atWorkCenter.getName());
				trsnAttrs.setProductionLineName(prodLineName);
				trsnAttrs.setOperationName(opName);
			}
		}

		String userName = routingRep.getAuthenticatedUserName();
		if (userName == null) userName = "Anonymous";
		trsnAttrs.setLastModifier(userName);

		if (step.getRuleClassName() != null && step.getRuleClassName().equals(DefaultAutoStartStepRule.class.getName())) {
			trsnAttrs.setStepStatus(MESConstants.Object.StepStatus.Started.toString());
		} else {
			trsnAttrs.setStepStatus(MESConstants.Object.StepStatus.Queued.toString());
		}

		if (trxInfo != null) {
			Date trxTime = trxInfo.getTime();
			trsnAttrs.setStatusTime(trxTime);
			trsnAttrs.setStatusTimeDecoder(routingRep.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));
			trsnAttrs.setComment(trxInfo.getComment());
		}
		
		if (foundTrsnAttrs) {
			this.transitionAttributesRep.updateTransitionAttributes(trsnAttrs, trxInfo);
		}
		else {
			this.transitionAttributesRep.createTransitionAttributes(trsnAttrs, trxInfo);
		}
		
		return trsnAttrs;
	}
	
	@Deprecated
	@Override
	public ETransitionAttributes routeContainer(EContainer container, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception {
		return route(container.getId(), MESConstants.Object.Type.Container, routingName, stepName, workCenterName, trxInfo);
	}

	@Deprecated
	@Override
	public ETransitionAttributes routeBatch(EBatch batch, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception {
		return route(batch.getId(), MESConstants.Object.Type.Batch, routingName, stepName, workCenterName, trxInfo);
	}

	@Deprecated
	@Override
	public ETransitionAttributes routeLot(ELot lot, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception {
		return route(lot.getId(), MESConstants.Object.Type.Lot, routingName, stepName, workCenterName, trxInfo);
	}

	@Deprecated
	@Override
	public ETransitionAttributes routeUnit(EUnit unit, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception {
		return route(unit.getId(), MESConstants.Object.Type.Unit, routingName, stepName, workCenterName, trxInfo);
	}
	
	@Override
	public void joinRouting(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String optWorkCenterName, boolean stepFlowEnforcement, MESTrxInfo trxInfo) throws Exception {		
		ERouting routing = routingRep.findByName(routingName);
		if (routing == null) {
			throw new Exception("Routing by name is not found: " + routingName);
		}
		
		Set<EStep> steps = routing.getSteps();
		if (steps == null || steps.isEmpty()) {
			throw new Exception("The routing contains no step");
		}
		
		EStep step = null;		
		if (stepName == null) {
			step = routing.findStartStep();
			if (step == null) {
				throw new Exception("The routing doesn't have a start step");
			}
		} else {
			step = routing.findStepByName(stepName);
			if (step == null) {
				throw new Exception("Step by name is not found: " + stepName);
			}
		}

		ETransitionAttributes trsnAttrs = transitionAttributesRep.findTransitionAttributes(objectId, objectType);
		boolean foundTrsnAttrs = true;
		if (trsnAttrs == null) {
			trsnAttrs = new ETransitionAttributes(objectId, objectType);
			foundTrsnAttrs = false;
		}
		
		trsnAttrs.setObjectId(objectId);
		trsnAttrs.setObjectType(objectType);
		trsnAttrs.setRoutingName(routingName);
		trsnAttrs.setStepName(stepName);
		trsnAttrs.setStepFlowEnforcement(stepFlowEnforcement);
		
		if (optWorkCenterName != null && optWorkCenterName.length() > 0) {
			EWorkCenter atWorkCenter = workCenterRep.findByName(optWorkCenterName);
			if (atWorkCenter != null) {
				String opName = null;
				if (step.getOperation() != null) {
					EOperation operation = operationRep.findById(step.getOperation().getId());
					opName = operation.getName();
				}

				EProductionLine prodLine = atWorkCenter.getProductionLine();
				String prodLineName = (prodLine != null) ? prodLine.getName() : null;
				String areaName = (prodLine != null && prodLine.getArea() != null) ? prodLine.getArea().getName() : null;
				String siteName = (prodLine != null && prodLine.getArea() != null && prodLine.getArea().getSite() != null) ? prodLine.getArea()
						.getSite().getName() : null;
				trsnAttrs.setSiteName(siteName);
				trsnAttrs.setAreaName(areaName);
				trsnAttrs.setWorkCenterName(atWorkCenter.getName());
				trsnAttrs.setProductionLineName(prodLineName);
				trsnAttrs.setOperationName(opName);
			}
		}

		String userName = routingRep.getAuthenticatedUserName();
		if (userName == null) userName = "Anonymous";
		trsnAttrs.setLastModifier(userName);
		
		Set<EWorkCenter> workCenters = step.getWorkCenters();
		if (workCenters == null || workCenters.isEmpty()) {
			throw new Exception("No work center(s) defined for step name '" + step.getName() + "' of routing " + routingName);
		}
		
		Set<EStepStatus> stepStatuses = step.getStepStatuses();
		if (stepStatuses == null || stepStatuses.isEmpty()) {
			throw new Exception("No step status(es) defined for step name '" + step.getName() + "' of routing " + routingName);
		}
		
		/*EStepStatus startStepStatus = step.getStartStepStatus();
		if (startStepStatus == null) {
			throw new Exception("Start step status is not defined for step name '" + step.getName() + "' of routing " + routingName);
		}
		trsnAttrs.setStepStatus(startStepStatus.getName());*/

		if (trxInfo != null) {
			Date trxTime = trxInfo.getTime();
			trsnAttrs.setStatusTime(trxTime);
			trsnAttrs.setStatusTimeDecoder(routingRep.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));
			trsnAttrs.setComment(trxInfo.getComment());
		}
		
		if (foundTrsnAttrs) {
			this.transitionAttributesRep.updateTransitionAttributes(trsnAttrs, trxInfo);
		}
		else {
			this.transitionAttributesRep.createTransitionAttributes(trsnAttrs, trxInfo);
		}
	}
	
	public ETransitionAttributes getTransitionAttributes(long objectId, MESConstants.Object.Type objectType) throws Exception {
		ETransitionAttributes trsnAttrs = trsnAttrsRepository.findTransitionAttributes(objectId, objectType);
		if (trsnAttrs == null) {
			throw new Exception(objectType.toString() + " not joining a routing yet, " + objectType.toString() + " ID: " + objectId);
		}
		return trsnAttrs;
	}
	
	@Override
	public void transact(long objectId, MESConstants.Object.Type objectType, String stepName, String optWorkCenterName, 
			String stepStatusName, String lotStatus, String optTransitionName, String optReason, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(objectId, objectType);
		if (trsnAttrs == null) {
			throw new Exception(objectType.toString() + " not joining a routing yet, " + objectType.toString() + " ID: " + objectId);
		}
		
		EStep atStep = findStepByName(trsnAttrs.getRoutingName(), stepName);
		EWorkCenter atWorkCenter = findWorkCenterByName(optWorkCenterName);

		if (atWorkCenter != null) {			
			EProductionLine prodLine = atWorkCenter.getProductionLine();
			String prodLineName = (prodLine != null) ? prodLine.getName() : null;
			String areaName = (prodLine != null && prodLine.getArea() != null) ? prodLine.getArea().getName() : null;
			String siteName = (prodLine != null && prodLine.getArea() != null && prodLine.getArea().getSite() != null) ? prodLine.getArea().getSite()
					.getName() : null;
			trsnAttrs.setSiteName(siteName);
			trsnAttrs.setAreaName(areaName);
			trsnAttrs.setWorkCenterName(atWorkCenter.getName());
			trsnAttrs.setRoutingName(atStep.getRouting().getName());
			trsnAttrs.setProductionLineName(prodLineName);
			
			EOperation operation = null;
			if(atStep.getOperation() != null){
				operation = findOperationById(atStep.getOperation().getId());
				trsnAttrs.setOperationName(operation.getName());
			}else{
				trsnAttrs.setOperationName("");
			}
		}

		trsnAttrs.setStepName(stepName);
		trsnAttrs.setLastModifier(userService.getAuthenticatedUser().getName());
		trsnAttrs.setStepStatus(stepStatusName);
		trsnAttrs.setObjectStatus(lotStatus);
		Date trxTime = trxInfo.getTime();
		trsnAttrs.setStatusTime(trxTime);
		trsnAttrs.setStatusTimeDecoder(routingRep.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));

		if (trxInfo != null) {
			trsnAttrs.setComment(trxInfo.getComment());
		}

		this.trsnAttrsRepository.updateTransitionAttributes(trsnAttrs, trxInfo);
	}
	
	// -------------------------- transition attributes ---------------------------
	@Override
	public ETransitionAttributes findTransitionAttributes(long objectId, Type objectType) throws Exception {
		return this.transitionAttributesRep.findTransitionAttributes(objectId, objectType);
	}
}
