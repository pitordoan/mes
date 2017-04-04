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
package com.cimpoint.mes.server.repositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.TrxInfo;
import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.filters.StepFilter;

@Repository("stepRepository")
public class StepRepository extends JpaRepository<Long, EStep> {
	
	@Autowired
	private EntityManager entityManager;
	
	public StepRepository() {
		super(EStep.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	public Set<EStep> findSteps(StepFilter filter) {
		Set<EStep> steps = super.find(filter);
		return steps;
	}

	public Set<String> findStepNames(StepFilter filter) {
		Set<String> names = super.findAttribute("Name", filter);
		return names;
	}
	
	@SuppressWarnings("unchecked")
	public Set<EStep> findSteps(String routingName) {
		try {
			Query qry = getEntityManager().createQuery("select o from EStep o where o.routing.name = ?1")
					.setParameter(1, routingName);
			List<EStep> steps = (List<EStep>) qry.getResultList();
			return new HashSet<EStep>(steps);
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> findStepNames(String routingName) {
		try {
			Query qry = getEntityManager().createQuery("select o from EStep o where o.routing.name = ?1")
					.setParameter(1, routingName);
			List<EStep> steps = (List<EStep>) qry.getResultList();
			Set<String> names = new HashSet<String>();
			for (EStep s: steps) {
				names.add(s.getName());
			}
			return names;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public EStep findStepByName(String routingName, String stepName) {
		try {
			Query qry = getEntityManager().createQuery("select o from EStep o where o.routing.name = ?1 and o.name = ?2")
					.setParameter(1, routingName)
					.setParameter(2, stepName)
					.setMaxResults(1);
			EStep step = (EStep) qry.getSingleResult();
			return step;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public EStep createStep(StepType type, String name, String description, ERouting optRouting, EOperation optOperation, 
			Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, CustomAttributes customAttributes,
			MESTrxInfo trxInfo) throws Exception {
		//TODO
		EStep e = new EStep(type, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		this.create(e, trxInfo);
		return e;
	}
	
	/*@Transactional
	public EStep createStep(EStep step, MESTrxInfo trxInfo) throws Exception {
		Set<EWorkCenter> workCenters = step.getWorkCenters();
		Set<EStepStatus> stepStatuses = step.getStepStatuses();
		Set<ETransition> incomingTransitions = step.getIncomingTransitions();
		Set<ETransition> outgoingTransitions = step.getOutgoingTransitions();
		
		step.setWorkCenters(null);
		step.setIncomingTransitions(null);
		step.setOutgoingTransitions(null);
		step.setStepStatuses(null);
		
		step.setId(null); //this fixes the error 500 due to the custom serializer making it 0	
		this.create(step, trxInfo);
			
		this.entityManager.flush();
		
		workCenters = createOrAttachWorkCenters(step, workCenters, trxInfo);
		step.setWorkCenters(workCenters);
		
		createOrAttachStepStatuses(step, stepStatuses, trxInfo);
		step.setStepStatuses(stepStatuses);
		
		incomingTransitions = createOrAttachIncomingTransition(step, incomingTransitions, trxInfo);
		step.setIncomingTransitions(incomingTransitions);
		
		outgoingTransitions = createOrAttachOutgoingTransition(step, outgoingTransitions, trxInfo);
		step.setOutgoingTransitions(outgoingTransitions);
		
		step = this.entityManager.merge(step);
		this.entityManager.flush();
		
		return step;
	}*/
	
	@Transactional
	public EStep createStep(EStep step, MESTrxInfo trxInfo) throws Exception {
		createOrAttachWorkCenters(step, trxInfo);
		createOrAttachStepStatuses(step, trxInfo);
		createOrAttachIncomingTransition(step, trxInfo);
		createOrAttachOutgoingTransition(step, trxInfo);
		
		refIncomingTransition(step);
		refOutgoingTransition(step);
		
		step.setId(null); //this fixes the error 500 due to the custom serializer making it 0	
		step = this.create(step, trxInfo);
		
		return step;
	}
	
	@Transactional 
	public void remove(Long id, TrxInfo trxInfo) {
		EStep e = entityManager.find(entityClass, id);
		
		//detach work centers from step so they don't get removed when the step is removed
		Set<EWorkCenter> wcs = e.getWorkCenters();
		if (wcs != null) {
			for (EWorkCenter wc : wcs) {
				wc.removeStep(e);
			}
		}
		e.setWorkCenters(null);
		
		//detach transitions from step so they don't get removed when the step is removed
//		Set<ETransition> incomingTransitions = e.getIncomingTransitions();
//		Set<ETransition> outgoingTransitions = e.getOutgoingTransitions();
		
//		if (incomingTransitions != null) {
//			for (ETransition t : incomingTransitions) {
//				t.setToStep(null);
//				t.setFromStep(null);
//			}
//		}
//		
//		if (outgoingTransitions != null) {
//			for (ETransition t : outgoingTransitions) {
//				t.setToStep(null);
//				t.setFromStep(null);
//			}
//		}
//		e.setIncomingTransitions(null);
//		e.setOutgoingTransitions(null);
		
//		detach operation
		EOperation operation = e.getOperation();
		Set<EStep> steps = operation.getSteps();
		if (steps != null && !steps.isEmpty()) {
			steps.remove(e);
		}
		e.setOperation(null);
		super.remove(e, trxInfo);
	}
	
	private void createOrAttachWorkCenters(EStep step, TrxInfo trxInfo) {
		Set<EWorkCenter> workCenters = step.getWorkCenters();
		Set<EWorkCenter> attachedWorkCenters = new HashSet<EWorkCenter>();
		if (workCenters != null) {
			for (EWorkCenter wc: workCenters) {
				if (wc.getId() == null || wc.getId() == 0L) {
					wc = this.createEntity(wc, trxInfo);
				}
				else {
					wc = this.updateEntity(wc, trxInfo);
				}
				
				Set<EStep> steps = wc.getSteps();
				if (steps == null) {
					steps = new HashSet<EStep>();
					wc.setSteps(steps);
				}
				
				if (!steps.contains(step)) {
					steps.add(step);
				}
				
				attachedWorkCenters.add(wc);
			}
		}
		
		step.setWorkCenters(attachedWorkCenters);
	}
	
	private void createOrAttachStepStatuses(EStep step, TrxInfo trxInfo) {
		Set<EStepStatus> stepStatuses = step.getStepStatuses();
		Set<EStepStatus> attachedStepStatuses = new HashSet<EStepStatus>();
		if (stepStatuses != null) {
			for (EStepStatus ss: stepStatuses) {
				if (ss.getId() == null || ss.getId() == 0L) {
					ss = this.createEntity(ss, trxInfo);
				}
				else {
					ss = this.updateEntity(ss, trxInfo);
				}
				
				Set<EStep> steps = ss.getSteps();
				if (steps == null) {
					steps = new HashSet<EStep>();
					ss.setSteps(steps);
				}
				
				if (!steps.contains(step)) {
					steps.add(step);
				}
				
				attachedStepStatuses.add(ss);
			}
		}
		
		step.setStepStatuses(attachedStepStatuses);
	}
	
	private void createOrAttachIncomingTransition(EStep step, TrxInfo trxInfo) {
		Set<ETransition> transitions = step.getIncomingTransitions();
		Set<ETransition> attachedTransitions = new HashSet<ETransition>();
		if (transitions != null) {
			for (ETransition trsn: transitions) {
				if (trsn.getId() == null || trsn.getId() == 0L) {
					trsn.setToStepName(step.getName());
					trsn.setFromStep(null);
					trsn.setToStep(null);
					trsn = this.createEntity(trsn, trxInfo);
				}
				else {
					trsn = this.updateEntity(trsn, trxInfo);
				}

				attachedTransitions.add(trsn);
			}
		}
		
		step.setIncomingTransitions(attachedTransitions);
	}
	
	private void refIncomingTransition(EStep step) {
		Set<ETransition> transitions = step.getIncomingTransitions();
		Set<ETransition> attachedTransitions = new HashSet<ETransition>();
		if (transitions != null) {
			for (ETransition trsn: transitions) {
				trsn.setToStep(step);				
				attachedTransitions.add(trsn);
			}
		}
		
		step.setIncomingTransitions(attachedTransitions);
	}

	private void createOrAttachOutgoingTransition(EStep step, TrxInfo trxInfo) {
		Set<ETransition> transitions = step.getOutgoingTransitions();
		Set<ETransition> attachedTransitions = new HashSet<ETransition>();
		if (transitions != null) {
			for (ETransition trsn: transitions) {
				if (trsn.getId() == null || trsn.getId() == 0L) {
					trsn.setFromStepName(step.getName());
					trsn.setFromStep(null);
					trsn.setToStep(null);
					trsn = this.createEntity(trsn, trxInfo);
				}

				trsn = this.updateEntity(trsn, trxInfo);
				
				attachedTransitions.add(trsn);
			}
		}
		
		step.setOutgoingTransitions(attachedTransitions);
	}
	
	private void refOutgoingTransition(EStep step) {
		Set<ETransition> transitions = step.getOutgoingTransitions();
		Set<ETransition> attachedTransitions = new HashSet<ETransition>();
		if (transitions != null) {
			for (ETransition trsn: transitions) {
				trsn.setFromStep(step);				
				attachedTransitions.add(trsn);
			}
		}
		
		step.setOutgoingTransitions(attachedTransitions);
	}
}
