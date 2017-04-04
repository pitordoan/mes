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
package com.cimpoint.mes.common.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.StepType;


@Entity
@Table(name="Step")
public class EStep implements Serializable {
	private static final long serialVersionUID = -3586937352740515907L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;

	@Column(name="Name", length=50, nullable=false)
	private String name;

	@Column(name="Description", length=255)
	private String description;

	@Column(name="Type", length=10)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.StepType type; //Entry, Inner, Exit

	@Column(name="Status")
	private String status;

	@OneToMany(mappedBy = "toStep", targetEntity = ETransition.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ETransition> incomingTransitions;

	@OneToMany(mappedBy = "fromStep", targetEntity = ETransition.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ETransition> outgoingTransitions;

	@ManyToMany(mappedBy = "steps", fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EWorkCenter> workCenters;

	@ManyToOne //(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="OperationId")
	private EOperation operation;

	@Embedded
	private CustomAttributes customAttributes;

	@Transient
	private EWorkInstruction workInstruction;	//TODO persist this too

	@ManyToOne
	@JoinColumn(name="RoutingId")
	private ERouting routing;

	@Column(name="RuleClassName")
	private String ruleClassName; 

	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ECheckList checklist;

	@ManyToMany(mappedBy = "steps", fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EStepStatus> stepStatuses;
	
	public EStep() {}

	public EStep(StepType type, String name, String description, ERouting optRouting, EOperation optOperation, 
			Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, CustomAttributes customAttributes) {
		this.setType(type);
		this.setName(name);
		this.setDescription(description);
		this.setWorkCenters(optWorkCenters);
		this.setStepStatuses(stepStatuses);
		this.setOperation(optOperation);
		this.setRouting(optRouting);
		this.setRuleClassName(ruleClassName);
		this.setCustomAttributes(customAttributes);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ERouting getRouting() {
		return routing;
	}

	public void setRouting(ERouting routing) {
		this.routing = routing;
	}

	public Set<ETransition> getIncomingTransitions() {
		return incomingTransitions;
	}

	public void setIncomingTransitions(Set<ETransition> incomingTransitions) {
		this.incomingTransitions = incomingTransitions;
	}

	public Set<ETransition> getOutgoingTransitions() {
		return outgoingTransitions;
	}

	public void setOutgoingTransitions(Set<ETransition> outgoingTransitions) {
		this.outgoingTransitions = outgoingTransitions;
	}

	public EWorkInstruction getWorkInstruction() {
		return workInstruction;
	}

	public void setWorkInstruction(EWorkInstruction workInstruction) {
		this.workInstruction = workInstruction;
	}

	public String getRuleClassName() {
		return ruleClassName;
	}

	public void setRuleClassName(String ruleClassName) {
		this.ruleClassName = ruleClassName;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EOperation getOperation() {
		return operation;
	}

	public void setOperation(EOperation operation) {
		this.operation = operation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<EWorkCenter> getWorkCenters() {
		return workCenters;
	}

	public void setWorkCenters(Set<EWorkCenter> workCenters) {
		this.workCenters = workCenters;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof EStep && obj != null) {
			EStep e = (EStep) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
		return false;
	}

	public MESConstants.Object.StepType getType() {
		return type;
	}

	public void setType(MESConstants.Object.StepType type) {
		this.type = type;
	}

	public ECheckList getCheckList() {
		return checklist;
	}

	public void setCheckList(ECheckList checklist) {
		this.checklist = checklist;
	}

	public void connects(EStep toStep, ETransition toTransition) {
		if (outgoingTransitions == null) {
			outgoingTransitions = new HashSet<ETransition>();
		}
		toTransition.setFromStep(this);
		toTransition.setToStep(toStep);
		outgoingTransitions.add(toTransition);		

		//connect back
		Set<ETransition> incomingTransitions = toStep.getIncomingTransitions();
		if (incomingTransitions == null) {
			incomingTransitions = new HashSet<ETransition>();
		}
		incomingTransitions.add(toTransition);
		toStep.setIncomingTransitions(incomingTransitions);
	}

	public Set<EStepStatus> getStepStatuses() {
		return stepStatuses;
	}

	public void setStepStatuses(Set<EStepStatus> stepStatuses) {
		this.stepStatuses = stepStatuses;
	}

	/*public int hashCode() {  
        return this.name.hashCode();
    } */
}
