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
package com.cimpoint.mes.client.objects;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EWorkCenter;

public class Step extends ClientObject<EStep> implements Named, Persistable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Step(EStep e) {
		this.entity = e;
	}
		
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Step)
            return this.getName().equals(((Step) obj).getName()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getName().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getName();
	}
	
	public void setName(String name) {
		entity.setName(name);
	}
	
	public String getDescription() {
		return entity.getDescription();
	}
	
	public void setDescription(String description) {
		entity.setDescription(description);
	}
	
	public String getRuleClassName() {
		return entity.getRuleClassName();
	}
	
	
	public void setType(StepType type) {
		entity.setType(type);
	}
	
	public StepType getType() {
		return entity.getType();
	}
	
	public void setRuleClassName(String ruleClassName) {
		entity.setRuleClassName(ruleClassName);
	}
	
	
	public Set<Transition> getIncomingTransitions() {
		Set<ETransition> transitions = entity.getIncomingTransitions();
		return  MESTypeConverter.toClientObjectSet(transitions, Transition.class);
	}

	public Set<Transition> getOutgoingTransitions() {
		Set<ETransition> transitions = entity.getOutgoingTransitions();
		return  MESTypeConverter.toClientObjectSet(transitions, Transition.class);
	}

	//if there's only 1 outgoing transition from the step, it's a default transition anyway
	//there should be only 1 default outgoing transition per step.  
	//if multiple default transitions are connected, the latest one is the one getting effected
	public void connects(Step step, Transition transition) {
		//connect to step
		transition.setFromStep(this);
		transition.setToStep(step);
		Set<ETransition> outgoingTransitions = entity.getOutgoingTransitions();
		if (outgoingTransitions == null) {
			outgoingTransitions = new HashSet<ETransition>();
		}

		ETransition et = transition.toEntity();
		outgoingTransitions.add(et);
		entity.setOutgoingTransitions(outgoingTransitions);
		
		//connect back
		Set<ETransition> incomingTransitions = step.entity.getIncomingTransitions();
		if (incomingTransitions == null) {
			incomingTransitions = new HashSet<ETransition>();
		}
		incomingTransitions.add(et);
		step.entity.setIncomingTransitions(incomingTransitions);
	}

	public WorkInstruction getWorkInstruction() {
		return  MESTypeConverter.toClientObject(entity.getWorkInstruction(), WorkInstruction.class);
	}

	public void setWorkInstruction(WorkInstruction workInstruction) {
		this.entity.setWorkInstruction(workInstruction.toEntity());
	}

	public Routing getRouting() {
		ERouting ewf = entity.getRouting();
		return  MESTypeConverter.toClientObject(ewf, Routing.class);
	}

	public Operation getOperation() {
		EOperation eoperation = entity.getOperation();
		return  MESTypeConverter.toClientObject(eoperation, Operation.class);
	}

	public void setOperation(Operation operation) {
		EOperation eoperation = (operation != null)? operation.toEntity() : null;
		entity.setOperation(eoperation);
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
	
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		routingController.saveStep(this, comment, callback);
	}

	public void addWorkCenter(WorkCenter workCenter) {
		if (workCenter != null) {
			Set<EWorkCenter> ewcs = entity.getWorkCenters();
			if (ewcs == null) ewcs = new HashSet<EWorkCenter>();
			else if (ewcs.contains(workCenter)) ewcs.remove(workCenter);			
			ewcs.add(workCenter.toEntity());
		}
	}
	
	public Set<WorkCenter> getWorkCenters() {
		Set<EWorkCenter> wcls = entity.getWorkCenters();
		return MESTypeConverter.toClientObjectSet(wcls, WorkCenter.class);
	}

	public void setWorkCenters(Set<WorkCenter> workCenters) {
		Set<EWorkCenter> ewcs = MESTypeConverter.toEntitySet(workCenters);
		entity.setWorkCenters(ewcs);
	}

	//TODO fetching this from step conf in the routing
	public String[] getStepStatusNames() {
		//return new String[] {"Queued", "Started", "Completed", "Reworked"};
		Set<StepStatus> ssSet = getStepStatuses();
		String[] ssArr = new String[ssSet.size()];
		
		int i = 0;
		if (ssSet != null) {
			for (StepStatus ss: ssSet) {
				ssArr[i++] = ss.getName();
			}
		}
		
		return ssArr;
	}
	
	public Set<StepStatus> getStepStatuses() {
		return MESTypeConverter.toClientObjectSet(entity.getStepStatuses(), StepStatus.class);
	}
}
