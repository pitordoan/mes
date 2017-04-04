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
import java.util.Map;
import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/routingService")
public interface RoutingService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static RoutingServiceAsync instance;
		public static RoutingServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(RoutingService.class);
			}
			return instance;
		}
	}
	    
	public Map<String, List<String>> findAllModelNames() throws Exception;
	public String findAllModelNamesAsJSON() throws Exception;
	
	//site
	public ESite findSiteById(long id) throws Exception;
	public ESite findSiteByName(String name) throws Exception;
	public Set<ESite> findAllSites() throws Exception;
	public Set<String> findAllSiteNames() throws Exception;	
	public Set<String> findSiteNames(SiteFilter filter) throws Exception;	
	public Set<ESite> findSites(SiteFilter filter) throws Exception;	
	public ESite createSite(String name, String description, Set<EArea> optAreas, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public void saveSite(ESite site, MESTrxInfo trxInfo) throws Exception;
	@Deprecated public void removeSite(ESite site, MESTrxInfo trxInfo) throws Exception;
	public void removeSite(String siteName, MESTrxInfo trxInfo) throws Exception;
	
	//area
	public EArea findAreaById(long id) throws Exception;
	public EArea findAreaByName(String name) throws Exception;
	public Set<EArea> findAllAreas() throws Exception;	
	public Set<EArea> findAreas(AreaFilter filter) throws Exception;	
	public Set<String> findAllAreaNames() throws Exception;
	public Set<String> findAreaNames(AreaFilter filter) throws Exception;	
	public EArea createArea(String name, String description, ESite site, Set<EProductionLine> productionLines, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public void saveArea(EArea area, MESTrxInfo trxInfo) throws Exception;	
	public void saveAreas(Set<EArea> areas, MESTrxInfo trxInfo) throws Exception;	
	@Deprecated public void removeArea(EArea area, MESTrxInfo trxInfo) throws Exception;
	public void removeArea(String areaName, MESTrxInfo trxInfo) throws Exception;

	//work center
	public EWorkCenter findWorkCenterById(long id) throws Exception;
	public EWorkCenter findWorkCenterByName(String name) throws Exception;
	public Set<EWorkCenter> findAllWorkCenters() throws Exception;	
	public Set<String> findAllWorkCenterNames() throws Exception;
	public Set<String> findWorkCenterNames(WorkCenterFilter filter) throws Exception;
	public Set<EWorkCenter> findWorkCenters(WorkCenterFilter filter) throws Exception;
	public EWorkCenter createWorkCenter(String name, String description, EArea optArea, 
			Set<EEquipment> optEquipments, EProductionLine optProductionLine, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public void saveWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception;	
	public void saveWorkCenters(Set<EWorkCenter> workCenters, MESTrxInfo trxInfo) throws Exception;
	@Deprecated public void removeWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo) throws Exception;
	public void removeWorkCenter(String workCenterName, MESTrxInfo trxInfo) throws Exception;
	
	//routing
	//@Deprecated public ERouting createRouting(String name, String description, String revision, EStep entryStep, 
	//		CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public ERouting createRouting(String name, String description, EStep entryStep, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public ERouting findRoutingById(Long id) throws Exception;
	//public ERouting findRoutingByNameAndRevision(String name, String revision) throws Exception;
	@Deprecated public Set<ERouting> findRoutingsByName(String name) throws Exception;
	public ERouting findRoutingByName(String name) throws Exception;
	public Set<ERouting> findAllRoutings() throws Exception;
    public Set<String> findAllRoutingNames() throws Exception;	
    public Set<String> findRoutingNames(RoutingFilter filter) throws Exception;    
    //public Set<String> findAllRoutingNameWithRevisions() throws Exception;
    //public Set<String> findRevisionsByRoutingName(String routingName) throws Exception;
    public void saveRouting(ERouting routing, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeRouting(ERouting routing, MESTrxInfo trxInfo) throws Exception;
    public void removeRouting(String routingName, MESTrxInfo trxInfo) throws Exception;    
    public void transact(long objectId, MESConstants.Object.Type objectType, String stepName, String optWorkCenterName, 
			String stepStatusName, String lotStatus, String optTransitionName, String optReason, MESTrxInfo trxInfo) throws Exception;
            
    //transition
    public ETransition createTransition(String transitionName, Quantity transferQuantity,
			EDictionary reasonDictionary, CustomAttributes customAttributes,
			MESTrxInfo trxInfo) throws Exception;
    public ETransition findTransitionById(Long id) throws Exception;
    public ETransition findTransitionByName(String routingName, String transitionName) throws Exception;
    public Set<ETransition> findAllTransitions();
    public Set<String> findAllTransitionNames();
    public void saveTransition(ETransition transition, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeTransition(ETransition transition, MESTrxInfo trxInfo) throws Exception;
    public void removeTransition(String transitionName, MESTrxInfo trxInfo) throws Exception;
    
    //step
    public EStep findStepById(Long id) throws Exception;  
    public EStep findStepByName(String routingName, String stepName) throws Exception;  
    public Set<EStep> findSteps(String routingName) throws Exception;  
    public Set<String> findStepNames(String routingName) throws Exception;  
    public Set<String> findStepNames(StepFilter filter) throws Exception;
	public Set<EStep> findSteps(StepFilter filter) throws Exception;
	public EStep createStep(StepType type, String name, String description, ERouting optRouting, EOperation optOperation, 
			Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public void saveStep(EStep step, MESTrxInfo trxInfo) throws Exception;
    public void saveSteps(Set<EStep> steps, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeStep(EStep step, MESTrxInfo trxInfo) throws Exception;
    public void removeStep(String stepName, MESTrxInfo trxInfo) throws Exception;
    
    //step status
    public EStepStatus findStepStatusById(Long id) throws Exception;  
    public EStepStatus findStepStatusByName(String stepStatusName) throws Exception;  
    public EStepStatus createStepStatus(String name, boolean isStarting, boolean isEnding, String nextDefaultStatusName, MESTrxInfo trxInfo) throws Exception;
    public void saveStepStatus(EStepStatus stepStatus, MESTrxInfo trxInfo) throws Exception;
    public void removeStepStatus(String stepStatusName, MESTrxInfo trxInfo) throws Exception;
        
    //operation
    public EOperation findOperationById(long id) throws Exception;
	public EOperation findOperationByName(String name) throws Exception;
    public Set<EOperation> findAllOperations() throws Exception;
    public Set<String> findAllOperationNames() throws Exception;	
    public Set<String> findOperationNames(OperationFilter filter) throws Exception;    
    //public EOperation createOperation(String name, String description, EWorkCenter workCenter, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public EOperation createOperation(String name, String description, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public void saveOperation(EOperation operation, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeOperation(EOperation operation, MESTrxInfo trxInfo) throws Exception;
    public void removeOperation(String operationName, MESTrxInfo trxInfo) throws Exception;
    
    //production line
    public EProductionLine findProductionLineById(long id) throws Exception;
	public EProductionLine findProductionLineByName(String name) throws Exception;
    public Set<EProductionLine> findAllProductionLines() throws Exception;
    public Set<String> findAllProductionLineNames() throws Exception;	
    public Set<String> findProductionLineNames(ProductionLineFilter filter) throws Exception;
	public Set<EProductionLine> findProductionLines(ProductionLineFilter filter) throws Exception;
	public EProductionLine createProductionLine(String name, String description, EArea area, Set<EWorkCenter> workCenters, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public void saveProductionLine(EProductionLine productionLine, MESTrxInfo trxInfo) throws Exception;
    public void saveProductionLines(Set<EProductionLine> prodLines, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeProductionLine(EProductionLine productionLine, MESTrxInfo trxInfo) throws Exception;    
    public void removeProductionLine(String productionLineName, MESTrxInfo trxInfo) throws Exception;       
	    
    //equipment
    public EEquipment findEquipmentById(long id) throws Exception;
	public EEquipment findEquipmentByName(String name) throws Exception;
    public Set<EEquipment> findAllEquipments() throws Exception;
    public Set<String> findAllEquipmentNames() throws Exception;	
    public Set<String> findEquipmentNames(EquipmentFilter filter) throws Exception;    
    public Set<EEquipment> findEquipments(EquipmentFilter filter) throws Exception;   
    public EEquipment createEquipment(String equipmentName, String description, EWorkCenter workCenter, 
    		CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public void saveEquipment(EEquipment equipment, MESTrxInfo trxInfo) throws Exception;
    public void saveEquipments(Set<EEquipment> equipments, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public void removeEquipment(EEquipment equipment, MESTrxInfo trxInfo) throws Exception;
    public void removeEquipment(String equipmentName, MESTrxInfo trxInfo) throws Exception;
    
    //route objects
    @Deprecated public ETransitionAttributes route(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ETransitionAttributes routeContainer(EContainer container, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ETransitionAttributes routeBatch(EBatch batch, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ETransitionAttributes routeLot(ELot lot, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception;
    @Deprecated public ETransitionAttributes routeUnit(EUnit unit, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo) throws Exception;
    
    public ETransitionAttributes findTransitionAttributes(long objectId, MESConstants.Object.Type objectType) throws Exception;    
    public void joinRouting(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String optWorkCenterName, boolean stepFlowEnforcement, MESTrxInfo trxInfo) throws Exception;
}
