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

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.services.UserService;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.LotFilter;
import com.cimpoint.mes.common.services.LotService;
import com.cimpoint.mes.common.services.RoutingService;
import com.cimpoint.mes.server.repositories.LotRepository;
import com.cimpoint.mes.server.repositories.TransitionAttributesRepository;
import com.cimpoint.mes.server.rules.RuleInput;
import com.cimpoint.mes.server.rules.RuleService;
import com.cimpoint.mes.server.rules.StateInput;
import com.cimpoint.mes.server.rules.StateRule;
import com.cimpoint.mes.server.rules.StepRule;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("lotService")
public class LotServiceImpl extends RemoteServiceServlet implements LotService {
	private static final long serialVersionUID = 3254299115573696431L;

	@Autowired
	private LotRepository lotRepository;

	@Autowired
	private RoutingService routingService;

	@Autowired
	private TransitionAttributesRepository trsnAttrsRepository;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void initialize() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void enableCache(boolean cacheable) {
		// TODO Auto-generated method stub

	}

	public boolean isCacheEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getNextLotNumber() throws Exception {
		return lotRepository.getNextLotNumber();
	}

	public String getInitLotNumber() {
		return lotRepository.getInitLotNumber();
	}

	public void setInitLotNumber(String lotNumber) {
		lotRepository.setInitLotNumber(lotNumber);
	}

	public ELot findLotByNumber(String lotNumber) throws Exception {
		return lotRepository.findByNumber(lotNumber);
	}

	public Set<ELot> findLots(LotFilter lotFiler) throws Exception {
		return lotRepository.find(lotFiler);
	}

	public void saveLot(ELot lot, MESTrxInfo trxInfo) throws Exception {
		// TODO with trxInfo
		lotRepository.update(lot, trxInfo);
	}

	public ELot createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
			String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception {
		return createDiscreteLot(lotNumber, workOrderNumber, workOrderItemNumber, units, partNumber, partRevision, null, customAttributes, trxInfo);
	}

	public ELot createDiscreteLot(String lotNumber, String workOrderNumber, Set<EUnit> units, 
			String partNumber, String partRevision, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception{
		return createDiscreteLot(lotNumber, workOrderNumber, null, units, partNumber, partRevision, null, customAttributes, trxInfo);
	}

	public ELot createDiscreteLot(String lotNumber, String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, 
			String partNumber, String partRevision, String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception{
		return createDiscreteLot(null, lotNumber, workOrderNumber, workOrderItemNumber, units, partNumber, partRevision, stateRuleClassName, customAttributes, trxInfo);
	}

	public ELot createDiscreteLot(String originalLotNumber, String splittedLotNumber, 
			String workOrderNumber, String workOrderItemNumber, Set<EUnit> units, String partNumber, String partRevision,  
			String stateRuleClassName, CustomAttributes customAttributes, MESTrxInfo trxInfo) throws Exception{
		if (stateRuleClassName == null)
			stateRuleClassName = "DefaultLotStateRule";
		String state = RuleService.getStateRuleByClassName(stateRuleClassName).getEntryState();
		return this.lotRepository.createDiscreteLot(originalLotNumber, splittedLotNumber, state, workOrderNumber, workOrderItemNumber, units, partNumber, partRevision,
				stateRuleClassName, customAttributes, trxInfo);
	}

	public ELot createProcessLot(String lotNumber, String workOrderNumber, Quantity amount, String componentName, String componentRevision,
			MESTrxInfo trxInfo) throws Exception {
		return createProcessLot(lotNumber, workOrderNumber, amount, componentName, componentRevision, trxInfo);
	}

	public ELot createProcessLot(String lotNumber, String workOrderNumber, Quantity amount, String componentName, String componentRevision,
			String stateRuleClassName, MESTrxInfo trxInfo) throws Exception {
		return createProcessLot(null, lotNumber, workOrderNumber, amount, componentName, componentRevision, stateRuleClassName, trxInfo);
	}

	public ELot createProcessLot(String originalLotNumber, String splittedLotNumber, String workOrderNumber, Quantity amount, String componentName,
			String componentRevision, String stateRuleClassName, MESTrxInfo trxInfo) throws Exception {
		if (stateRuleClassName == null)
			stateRuleClassName = "DefaultLotStateRule";
		String state = RuleService.getStateRuleByClassName(stateRuleClassName).getEntryState();
		return this.lotRepository.createProcessLot(originalLotNumber, splittedLotNumber, state, workOrderNumber, amount, componentName,
				componentRevision, stateRuleClassName, trxInfo);
	}

	@Deprecated
	public void changeLotStatus(ELot lot, String newStatus, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		trsnAttrs.setStepStatus(newStatus);
		trsnAttrsRepository.update(trsnAttrs, trxInfo);
	}

	@Deprecated
	public void changeLotState(ELot lot, String newState, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		trsnAttrs.setState(newState);
		trsnAttrsRepository.update(trsnAttrs, trxInfo);
	}

	public void changeLotOperation(ELot lot, EOperation newOperation, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		trsnAttrs.setOperationName(newOperation.getName());
		trsnAttrsRepository.update(trsnAttrs, trxInfo);
	}

	@Deprecated
	private void transactLot(String status, ELot lot, String stepName, String optWorkCenterName, String newState, String transitionName,
			MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		EStep atStep = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		EWorkCenter atWorkCenter = routingService.findWorkCenterByName(optWorkCenterName);

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
				operation = routingService.findOperationById(atStep.getOperation().getId());
				trsnAttrs.setOperationName(operation.getName());
			}else{
				trsnAttrs.setOperationName("");
			}
		}

		trsnAttrs.setStepName(stepName);
		trsnAttrs.setLastModifier(userService.getAuthenticatedUser().getName());
		trsnAttrs.setStepStatus(status);
		Date trxTime = trxInfo.getTime();
		trsnAttrs.setStatusTime(trxTime);
		trsnAttrs.setStatusTimeDecoder(lotRepository.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));

		if (trxInfo != null) {
			trsnAttrs.setComment(trxInfo.getComment());
		}

		if (newState != null) {
			trsnAttrs.setState(newState);
		}

		this.trsnAttrsRepository.updateTransitionAttributes(trsnAttrs, trxInfo);
	}

	@Deprecated
	public ELot queueLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception {
		transactLot(MESConstants.Object.StepStatus.Queued.toString(), lot, stepName, optWorkCenterName, null, null, trxInfo);
		return lot;
	}

	@Deprecated
	public ELot startLot(ELot lot, String stepName, String optWorkCenterName, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		EStep step = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		StepRule stepRule = RuleService.getStepRuleByClassName(step.getRuleClassName());
		RuleInput stepInput = new RuleInput(lot, trsnAttrs.getRoutingName(), step, routingService, trxInfo);

		String newState = null;
		String stateRuleClassName = trsnAttrs.getStateRuleClassName();
		if (stateRuleClassName != null && stateRuleClassName.length() > 0) {
			StateRule stateRule = RuleService.getStateRuleByClassName("com.cimpoint.mes.server.rules." + trsnAttrs.getStateRuleClassName());
			StateInput stateInput = new StateInput(trsnAttrs.getState(), stepName);
			newState = stateRule.getState(stateInput);
		}
		
		// pre start
		stepRule.onPreStart(stepInput);

		// start
		String status = MESConstants.Object.StepStatus.Started.toString();
		transactLot(status, lot, stepName, optWorkCenterName, newState, null, trxInfo);

		// post start
		stepRule.onPostStart(stepInput);

		return lot;
	}

	@Deprecated
	public ELot completeLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		ETransition completingTrsn = routingService.findTransitionByName(trsnAttrs.getRoutingName(), transitionName);
		if (completingTrsn == null) {
			throw new Exception("Invalid transition name: " + transitionName);
		}
		
		EStep nextStep = completingTrsn.getToStep();
		String nextStepName = null;
		StepRule nextStepRule = null;		
		if (nextStep != null) {
			nextStepName = nextStep.getName();
			nextStepRule = RuleService.getStepRuleByClassName(nextStep.getRuleClassName());
			if (nextStepRule.getAutoAction() == StepRule.AutoAction.StartAndComplete) {				
				RuleInput input2 = new RuleInput(lot, trsnAttrs.getRoutingName(), nextStep, routingService, trxInfo);
				ETransition[] transitions = nextStepRule.getAutoCompleteTransitions(input2);
				if (transitions == null || transitions.length == 0) {
					throw new Exception("No outgoing transition defined for auto completed step: " + stepName);
				}
			}
		}
		
		EStep step = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		StepRule stepRule = RuleService.getStepRuleByClassName(step.getRuleClassName());
		RuleInput input = new RuleInput(lot, trsnAttrs.getRoutingName(), step, routingService, trxInfo);

		// pre complete
		stepRule.onPreComplete(input);

		// complete
		String status = MESConstants.Object.StepStatus.Completed.toString();
		transactLot(status, lot, stepName, optWorkCenterName, null, null, trxInfo);

		// auto start, complete, or queue at the next step
		if (nextStepRule != null) {
			if (nextStepRule.getAutoAction() == StepRule.AutoAction.StartAndComplete) {
				startLot(lot, nextStepName, optWorkCenterName, trxInfo); // start
				RuleInput input2 = new RuleInput(lot, trsnAttrs.getRoutingName(), nextStep, routingService, trxInfo);
				ETransition[] transitions = nextStepRule.getAutoCompleteTransitions(input2);
				if (transitions == null || transitions.length == 0) {
					throw new Exception("No outgoing transition defined for auto completed step: " + stepName);
				}
	
				if (transitions.length > 1) {
					Set<Quantity> splitSizes = new HashSet<Quantity>();
					for (int i = 0; i < transitions.length; i++) {
						ETransition t = transitions[i];
						Quantity splitSize = new Quantity(t.getTransferQuantity(), t.getTransferUnitOfMeasure());
						splitSizes.add(splitSize);
					}
	
					Set<ELot> splittedLots = splitLot(lot, splitSizes, trxInfo);
					Iterator<ELot> it = splittedLots.iterator();
					int i = 0;
					while (it.hasNext()) {
						ELot splittedLot = it.next();
						ETransition t = transitions[i++];
						completeLot(splittedLot, nextStepName, optWorkCenterName, t.getName(), trxInfo); 
					}
				} else {
					completeLot(lot, nextStepName, optWorkCenterName, transitions[0].getName(), trxInfo); 
				}
			} else if (nextStepRule.getAutoAction() == StepRule.AutoAction.Start) {
				startLot(lot, nextStepName, optWorkCenterName, trxInfo); // start
			} else if (nextStepRule.getAutoAction() == StepRule.AutoAction.Queue) {
				queueLot(lot, nextStepName, optWorkCenterName, trxInfo);
			}
		}
		
		// post complete
		stepRule.onPostComplete(input);

		return lot;
	}

	@Deprecated
	//pause: do the same start
	@Override
	public ELot pauseLot(ELot lot, String stepName, String optWorkCenterName, String transitionName, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);

		String newState = null;
		String stateRuleClassName = trsnAttrs.getStateRuleClassName();
		if (stateRuleClassName != null && stateRuleClassName.length() > 0) {
			StateRule stateRule = RuleService.getStateRuleByClassName("com.cimpoint.mes.server.rules." + trsnAttrs.getStateRuleClassName());
			StateInput stateInput = new StateInput(trsnAttrs.getState(), stepName);
			newState = stateRule.getState(stateInput);
		}
		
		// pause
		String status = MESConstants.Object.StepStatus.Paused.toString();
		transactLot(status, lot, stepName, optWorkCenterName, newState, null, trxInfo);

		return lot;
	}
	
	public Set<ELot> findSplittedLots(String originalLotNumber) throws Exception {
		return lotRepository.findSplittedLots(originalLotNumber);
	}

	public Set<ELot> splitLot(ELot lot, Set<Quantity> splitQuantities, MESTrxInfo trxInfo) throws Exception {
		Set<ELot> lots = new HashSet<ELot>();
		Set<EUnit> units = null;

		for (Quantity qty : splitQuantities) {
			String splittedLotNumber = this.getNextLotNumber();
			if (lot.getType() == MESConstants.Object.LotType.Discrete) {
				ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
				ELot splittedLot = this.createDiscreteLot(lot.getNumber(), splittedLotNumber, lot.getWorkOrderNumber(), units, lot.getMaterialName(),
						lot.getMaterialRevision(), trsnAttrs.getStateRuleClassName(), lot.getCustomAttributes(), trxInfo);
				lots.add(splittedLot);
			} else if (lot.getType() == MESConstants.Object.LotType.Process) {
				ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
				ELot splittedLot = this.createProcessLot(lot.getNumber(), splittedLotNumber, lot.getWorkOrderNumber(), qty, lot.getMaterialName(),
						lot.getMaterialRevision(), trsnAttrs.getStateRuleClassName(), trxInfo);
				lots.add(splittedLot);
			} else
				throw new RuntimeException("Not supported lot type: " + lot.getType());
		}

		return lots;
	}
	
	public ETransitionAttributes getTransitionAttributes(ELot lot) throws Exception {
		ETransitionAttributes trsnAttrs = trsnAttrsRepository.findTransitionAttributes(lot.getId(), MESConstants.Object.Type.Lot);
		if (trsnAttrs == null) {
			throw new Exception("Lot not joining a routing yet, lot number: " + lot.getNumber());
		}
		return trsnAttrs;
	}

	@Override
	public Set<String> findLotNumbersByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(lotRepository.findLotNumbersByWorkOrderItemNumber(workOrderItemNumber));
		return result;
	}

	@Override
	public Set<ELot> findLotsByWorkOrderItemNumber(String workOrderItemNumber) throws Exception {
		Set<ELot> result = new HashSet<ELot>();
		result.addAll(lotRepository.findLotsByWorkOrderItemNumber(workOrderItemNumber));
		return result;
	}
	
	/*@Override
	public void transact(ELot lot, String stepName, String optWorkCenterName, String stepStatusName, String lotStatus,
			String optTransitionName, String optReason, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(lot);
		if (trsnAttrs == null) {
			throw new Exception("Lot not joining a routing yet, lot number: " + lot.getNumber());
		}
		
		EStep atStep = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		EWorkCenter atWorkCenter = routingService.findWorkCenterByName(optWorkCenterName);

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
				operation = routingService.findOperationById(atStep.getOperation().getId());
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
		trsnAttrs.setStatusTimeDecoder(lotRepository.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));

		if (trxInfo != null) {
			trsnAttrs.setComment(trxInfo.getComment());
		}

		this.trsnAttrsRepository.updateTransitionAttributes(trsnAttrs, trxInfo);
	}*/
}
