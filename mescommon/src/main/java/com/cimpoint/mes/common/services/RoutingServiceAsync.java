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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RoutingServiceAsync {
	public void findAllModelNames(AsyncCallback<Map<String, List<String>>> callback);
	public void findAllModelNamesAsJSON(AsyncCallback<String> callback);
	
	//site
	public void findSiteById(long id, AsyncCallback<ESite> callback);
	public void findSiteByName(String name, AsyncCallback<ESite> callback);
	public void findAllSites(AsyncCallback<Set<ESite>> callback);
	public void findAllSiteNames(AsyncCallback<Set<String>> callback);	
	public void findSiteNames(SiteFilter filter, AsyncCallback<Set<String>> callback);	
	public void findSites(SiteFilter filter, AsyncCallback<Set<ESite>> callback);	
	public void createSite(String name, String description, Set<EArea> optAreas, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ESite> callback);
	public void saveSite(ESite site, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	@Deprecated public void removeSite(ESite site, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removeSite(String siteName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	
	//area
	public void findAreaById(long id, AsyncCallback<EArea> callback);
	public void findAreaByName(String name, AsyncCallback<EArea> callback);
	public void findAllAreas(AsyncCallback<Set<EArea>> callback);	
	public void findAreas(AreaFilter filter, AsyncCallback<Set<EArea>> callback);	
	public void findAllAreaNames(AsyncCallback<Set<String>> callback);
	public void findAreaNames(AreaFilter filter, AsyncCallback<Set<String>> callback);	
	public void createArea(String name, String description, ESite site, Set<EProductionLine> productionLines, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EArea> callback);
	public void saveArea(EArea area, MESTrxInfo trxInfo, AsyncCallback<Void> callback);	
	public void saveAreas(Set<EArea> areas, MESTrxInfo trxInfo, AsyncCallback<Void> callback);	
	@Deprecated public void removeArea(EArea area, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removeArea(String areaName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);

	//work center
	public void findWorkCenterById(long id, AsyncCallback<EWorkCenter> callback);
	public void findWorkCenterByName(String name, AsyncCallback<EWorkCenter> callback);
	public void findAllWorkCenters(AsyncCallback<Set<EWorkCenter>> callback);	
	public void findAllWorkCenterNames(AsyncCallback<Set<String>> callback);
	public void findWorkCenterNames(WorkCenterFilter filter, AsyncCallback<Set<String>> callback);
	public void findWorkCenters(WorkCenterFilter filter, AsyncCallback<Set<EWorkCenter>> callback);
	public void createWorkCenter(String name, String description, EArea optArea, 
			Set<EEquipment> optEquipments, EProductionLine optProductionLine, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EWorkCenter> callback);
	public void saveWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo, AsyncCallback<Void> callback);	
	public void saveWorkCenters(Set<EWorkCenter> workCenters, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	@Deprecated public void removeWorkCenter(EWorkCenter workCenter, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	public void removeWorkCenter(String workCenterName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
	
	//routing
	//@Deprecated public ERouting createRouting(String name, String description, String revision, EStep entryStep, 
	//		CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
	public void createRouting(String name, String description, EStep entryStep, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<ERouting> callback);
	public void findRoutingById(Long id, AsyncCallback<ERouting> callback);
	//public ERouting findRoutingByNameAndRevision(String name, String revision) throws Exception;
	@Deprecated public void findRoutingsByName(String name, AsyncCallback<Set<ERouting>> callback);
	public void findRoutingByName(String name, AsyncCallback<ERouting> callback);
	public void findAllRoutings(AsyncCallback<Set<ERouting>> callback);
    public void findAllRoutingNames(AsyncCallback<Set<String>> callback);	
    public void findRoutingNames(RoutingFilter filter, AsyncCallback<Set<String>> callback);    
    //public Set<String> findAllRoutingNameWithRevisions() throws Exception;
    //public Set<String> findRevisionsByRoutingName(String routingName) throws Exception;
    public void saveRouting(ERouting routing, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeRouting(ERouting routing, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeRouting(String routingName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void transact(long objectId, MESConstants.Object.Type objectType, String stepName, String optWorkCenterName, 
			String stepStatusName, String lotStatus, String optTransitionName, String optReason, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
            
    //transition
    public void createTransition(String transitionName, Quantity transferQuantity,
			EDictionary reasonDictionary, CustomAttributes customAttributes,
			MESTrxInfo trxInfo, AsyncCallback<ETransition> callback);
    public void findTransitionById(Long id, AsyncCallback<ETransition> callback);
    public void findTransitionByName(String routingName, String transitionName, AsyncCallback<ETransition> callback);
    public void findAllTransitions(AsyncCallback<Set<ETransition>> callback);
    public void findAllTransitionNames(AsyncCallback<Set<String>> callback);
    public void saveTransition(ETransition transition, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeTransition(ETransition transition, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeTransition(String transitionName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    
    //step
    public void findStepById(Long id, AsyncCallback<EStep> callback);  
    public void findStepByName(String routingName, String stepName, AsyncCallback<EStep> callback);  
    public void findSteps(String routingName, AsyncCallback<Set<EStep>> callback);  
    public void findStepNames(String routingName, AsyncCallback<Set<String>> callback);  
    public void findStepNames(StepFilter filter, AsyncCallback<Set<String>> callback);
	public void findSteps(StepFilter filter, AsyncCallback<Set<EStep>> callback);
	public void createStep(StepType type, String name, String description, ERouting optRouting, EOperation optOperation, 
			Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EStep> callback);
    public void saveStep(EStep step, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void saveSteps(Set<EStep> steps, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeStep(EStep step, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeStep(String stepName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    
    //step status
    public void findStepStatusById(Long id, AsyncCallback<EStepStatus> callback);  
    public void findStepStatusByName(String stepStatusName, AsyncCallback<EStepStatus> callback);  
    public void createStepStatus(String name, boolean isStarting, boolean isEnding, String nextDefaultStatusName, MESTrxInfo trxInfo, AsyncCallback<EStepStatus> callback);
    public void saveStepStatus(EStepStatus stepStatus, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeStepStatus(String stepStatusName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
        
    //operation
    public void findOperationById(long id, AsyncCallback<EOperation> callback);
	public void findOperationByName(String name, AsyncCallback<EOperation> callback);
    public void findAllOperations(AsyncCallback<Set<EOperation>> callback);
    public void findAllOperationNames(AsyncCallback<Set<String>> callback);	
    public void findOperationNames(OperationFilter filter, AsyncCallback<Set<String>> callback);    
    //public EOperation createOperation(String name, String description, EWorkCenter workCenter, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception;
    public void createOperation(String name, String description, CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EOperation> callback);
    public void saveOperation(EOperation operation, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeOperation(EOperation operation, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeOperation(String operationName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    
    //production line
    public void findProductionLineById(long id, AsyncCallback<EProductionLine> callback);
	public void findProductionLineByName(String name, AsyncCallback<EProductionLine> callback);
    public void findAllProductionLines(AsyncCallback<Set<EProductionLine>> callback);
    public void findAllProductionLineNames(AsyncCallback<Set<String>> callback);	
    public void findProductionLineNames(ProductionLineFilter filter, AsyncCallback<Set<String>> callback);
	public void findProductionLines(ProductionLineFilter filter, AsyncCallback<Set<EProductionLine>> callback);
	public void createProductionLine(String name, String description, EArea area, Set<EWorkCenter> workCenters, 
			CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EProductionLine> callback);
    public void saveProductionLine(EProductionLine productionLine, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void saveProductionLines(Set<EProductionLine> prodLines, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeProductionLine(EProductionLine productionLine, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void removeProductionLine(String productionLineName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);       
	    
    //equipment
    public void findEquipmentById(long id, AsyncCallback<EEquipment> callback);
	public void findEquipmentByName(String name, AsyncCallback<EEquipment> callback);
    public void findAllEquipments(AsyncCallback<Set<EEquipment>> callback);
    public void findAllEquipmentNames(AsyncCallback<Set<String>> callback);	
    public void findEquipmentNames(EquipmentFilter filter, AsyncCallback<Set<String>> callback);    
    public void findEquipments(EquipmentFilter filter, AsyncCallback<Set<EEquipment>> callback);   
    public void createEquipment(String equipmentName, String description, EWorkCenter workCenter, 
    		CustomAttributes customAttributes, MESTrxInfo trxInfo, AsyncCallback<EEquipment> callback);
    public void saveEquipment(EEquipment equipment, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void saveEquipments(Set<EEquipment> equipments, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    @Deprecated public void removeEquipment(EEquipment equipment, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void removeEquipment(String equipmentName, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    
    //route objects
    @Deprecated public void route(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String workCenterName, MESTrxInfo trxInfo, AsyncCallback<ETransitionAttributes> callback);
    @Deprecated public void routeContainer(EContainer container, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo, AsyncCallback<ETransitionAttributes> callback);
    @Deprecated public void routeBatch(EBatch batch, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo, AsyncCallback<ETransitionAttributes> callback);
    @Deprecated public void routeLot(ELot lot, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo, AsyncCallback<ETransitionAttributes> callback);
    @Deprecated public void routeUnit(EUnit unit, String routingName, String stepName, String workCenterName, MESTrxInfo trxInfo, AsyncCallback<ETransitionAttributes> callback);
    
    public void findTransitionAttributes(long objectId, MESConstants.Object.Type objectType, AsyncCallback<ETransitionAttributes> callback);    
    public void joinRouting(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String optWorkCenterName, boolean stepFlowEnforcement, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
}
