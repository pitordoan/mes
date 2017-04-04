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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cimpoint.mes.common.filters.UnitFilter;
import com.cimpoint.mes.common.services.RoutingService;
import com.cimpoint.mes.common.services.UnitService;
import com.cimpoint.mes.server.repositories.TransitionAttributesRepository;
import com.cimpoint.mes.server.repositories.UnitRepository;
import com.cimpoint.mes.server.rules.RuleInput;
import com.cimpoint.mes.server.rules.RuleService;
import com.cimpoint.mes.server.rules.StateInput;
import com.cimpoint.mes.server.rules.StateRule;
import com.cimpoint.mes.server.rules.StepRule;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Service("unitService")
public class UnitServiceImpl extends RemoteServiceServlet implements UnitService {
	private static final long serialVersionUID = -360493876257333690L;

	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private TransitionAttributesRepository trsnAttrsRepository;

	@Autowired
	private RoutingService routingService;

	@Autowired
	private UserService userService;

	@Override
	public void enableCache(boolean cacheable) {
		unitRepository.enableCache(cacheable);
	}

	@Override
	public boolean isCacheEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNextUnitNumber() throws Exception {
		return unitRepository.getNextUnitSerialNumber();
	}

	@Override
	public String getInitUnitNumber() {
		return unitRepository.getInitUnitSerialNumber();
	}

	@Override
	public void setInitUnitNumber(String unitNumber) {
		unitRepository.setInitUnitSerialNumber(unitNumber);
	}

	@Override
	public EUnit findUnitByNumber(String unitNumber) throws Exception {
		return unitRepository.findUnitBySerialNumber(unitNumber);
	}

	public Set<String> findUnitNumbersByLotNumber(String lotNumber)throws Exception {
		Set<String> result = new HashSet<String>();
		result.addAll(unitRepository.findUnitsByLotNumber(lotNumber));
		return result;
	}
	
	@Override
	public Set<EUnit> findUnits(UnitFilter unitFiler) throws Exception {
		Set<EUnit> result = new HashSet<EUnit>();
		result.addAll(unitRepository.findUnits(unitFiler));
		return result;
	}

	@Override
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo)
			throws Exception {
		EUnit e = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision);
		unitRepository.create(e, trxInfo);
		return e;
	}

	@Override
	public Set<EUnit> createUnits(String[] unitSerialNumbers, String workOrderNumber, String partNumber, String partRevision, MESTrxInfo trxInfo)
			throws Exception {
		Set<EUnit> set = new HashSet<EUnit>();
		for (String sn : unitSerialNumbers) {
			EUnit e = new EUnit(sn, workOrderNumber, partNumber, partRevision);
			unitRepository.create(e, trxInfo);
			set.add(e);
		}

		return set;
	}

	@Override
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, MESTrxInfo trxInfo)
			throws Exception {
		EUnit e = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, lot);
		unitRepository.create(e, trxInfo);
		return e;
	}

	@Override
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit parentUnit, MESTrxInfo trxInfo)
			throws Exception {
		EUnit e = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, parentUnit);
		unitRepository.create(e, trxInfo);
		return e;
	}

	@Override
	public EUnit createUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot lot, EUnit parentUnit,
			MESTrxInfo trxInfo) throws Exception {
		EUnit e = new EUnit(unitSerialNumber, workOrderNumber, partNumber, partRevision, lot, parentUnit);
		unitRepository.create(e, trxInfo);
		return e;
	}

	@Override
	public void saveUnit(EUnit unit, MESTrxInfo trxInfo) throws Exception {
		unitRepository.update(unit, trxInfo);
	}

	@Override
	public void changeStatus(EUnit unit, String newStatus, MESTrxInfo trxInfo) throws Exception {
		unit.setStatus(newStatus);
		unitRepository.update(unit, trxInfo);
	}

	@Override
	public void changeState(EUnit unit, String newState, MESTrxInfo trxInfo) throws Exception {
		unit.setState(newState);
		unitRepository.update(unit, trxInfo);
	}

	@Override
	public EUnit queueUnit(EUnit unit, String stepName,
			String optWorkCenterName, MESTrxInfo trxInfo) throws Exception {
		transactUnit("Queued", unit, stepName, optWorkCenterName, null, null, trxInfo);
		return unit;
	}

	@Override
	public EUnit startUnit(EUnit unit, String stepName,
			String optWorkCenterName, MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(unit);
		EStep step = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		StepRule stepRule = RuleService.getStepRuleByClassName(step.getRuleClassName());
		RuleInput stepInput = new RuleInput(unit, trsnAttrs.getRoutingName(), step, routingService, trxInfo);

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
		transactUnit(status, unit, stepName, optWorkCenterName, newState, null, trxInfo);

		// post start
		stepRule.onPostStart(stepInput);

		return unit;
	}

	@Override
	public EUnit completeUnit(EUnit unit, String stepName,
			String optWorkCenterName, String transitionName, MESTrxInfo trxInfo)
			throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(unit);
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
				RuleInput input2 = new RuleInput(unit, trsnAttrs.getRoutingName(), nextStep, routingService, trxInfo);
				ETransition[] transitions = nextStepRule.getAutoCompleteTransitions(input2);
				if (transitions == null || transitions.length == 0) {
					throw new Exception("No outgoing transition defined for auto completed step: " + stepName);
				}
			}
		}
		
		EStep step = routingService.findStepByName(trsnAttrs.getRoutingName(), stepName);
		StepRule stepRule = RuleService.getStepRuleByClassName(step.getRuleClassName());
		RuleInput input = new RuleInput(unit, trsnAttrs.getRoutingName(), step, routingService, trxInfo);

		// pre complete
		stepRule.onPreComplete(input);

		// complete
		String status = MESConstants.Object.StepStatus.Completed.toString();
		transactUnit(status, unit, stepName, optWorkCenterName, null, null, trxInfo);

		// auto start, complete, or queue at the next step
		if (nextStepRule != null) {
			if (nextStepRule.getAutoAction() == StepRule.AutoAction.StartAndComplete) {
				startUnit(unit, nextStepName, optWorkCenterName, trxInfo); // start
				RuleInput input2 = new RuleInput(unit, trsnAttrs.getRoutingName(), nextStep, routingService, trxInfo);
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
	
					Set<EUnit> splittedLots = splitLot(unit, splitSizes, trxInfo);
					Iterator<EUnit> it = splittedLots.iterator();
					int i = 0;
					while (it.hasNext()) {
						EUnit splittedLot = it.next();
						ETransition t = transitions[i++];
						completeUnit(splittedLot, nextStepName, optWorkCenterName, t.getName(), trxInfo); 
					}
				} else {
					completeUnit(unit, nextStepName, optWorkCenterName, transitions[0].getName(), trxInfo); 
				}
			} else if (nextStepRule.getAutoAction() == StepRule.AutoAction.Start) {
				startUnit(unit, nextStepName, optWorkCenterName, trxInfo); // start
			} else if (nextStepRule.getAutoAction() == StepRule.AutoAction.Queue) {
				queueUnit(unit, nextStepName, optWorkCenterName, trxInfo);
			}
		}
		
		// post complete
		stepRule.onPostComplete(input);

		return unit;
	}
	
	//TODO: clearify and do with these typesStandAlone, ContainedInLot, ContainedInUnit, ContainedInLotAndUnit
	public Set<EUnit> splitLot(EUnit unit, Set<Quantity> splitQuantities, MESTrxInfo trxInfo) throws Exception {
		Set<EUnit> eunits = new HashSet<EUnit>();
		for (Quantity qty : splitQuantities) {
			String splittedUnitNumber = this.getNextUnitNumber();
		}

		return eunits;
	}

	@Override
	public EUnit pauseUnit(EUnit lot, String stepName,
			String optWorkCenterName, String transitionName, MESTrxInfo trxInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void transactUnit(String status, EUnit unit, String stepName, String optWorkCenterName, String newState, String transitionName,
			MESTrxInfo trxInfo) throws Exception {
		ETransitionAttributes trsnAttrs = getTransitionAttributes(unit);
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
		trsnAttrs.setStatusTimeDecoder(unitRepository.getTimeDecoder(trxTime, trxInfo.getTimeZoneId()));

		if (trxInfo != null) {
			trsnAttrs.setComment(trxInfo.getComment());
		}

		if (newState != null) {
			trsnAttrs.setState(newState);
		}

		this.trsnAttrsRepository.updateTransitionAttributes(trsnAttrs, trxInfo);
	}
	

	public ETransitionAttributes getTransitionAttributes(EUnit unit) throws Exception {
		ETransitionAttributes trsnAttrs = trsnAttrsRepository.findTransitionAttributes(unit.getId(), MESConstants.Object.Type.Unit);
		if (trsnAttrs == null) {
			throw new Exception("Lot not joining a routing yet, lot number: " + unit.getSerialNumber());
		}
		return trsnAttrs;
	}
}
