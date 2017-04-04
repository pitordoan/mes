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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants.Object.StepType;

@Entity
@Table(name="Routing")
public class ERouting implements Serializable {
	private static final long serialVersionUID = -4865504616147064123L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=30, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description")
	private String description;
			
	@Column(name="StartStepId")
	private Long startStepId; 
					
	@Column(name="EndStepId")
	private Long endStepId; 
	
	@OneToMany(mappedBy = "routing", targetEntity = EStep.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EStep> steps;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	public ERouting() {}
	
	public ERouting(String name) {
		this.setName(name);
	}
	
	public ERouting(String name, String description, EStep entryStep, EStep exitStep, Set<EStep> steps, CustomAttributes customAttributes) {		
		this.setName(name);
		this.setDescription(description);		
		if (entryStep != null) {
			this.setStartStepId(entryStep.getId());
		}
		
		if (exitStep != null) {
			this.setEndStepId(exitStep.getId());
		}
		
		if (steps != null) {
			this.setSteps(steps);
		}
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStartStepId() {
		return startStepId;
	}

	public void setStartStepId(Long startStepId) {
		this.startStepId = startStepId;
	}

	public Long getEndStepId() {
		return endStepId;
	}

	public void setEndStepId(Long endStepId) {
		this.endStepId = endStepId;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof ERouting && obj != null) {
			ERouting e = (ERouting) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }
	
	/*public boolean equals(Object obj) {  
		if (obj instanceof ERouting && obj != null) {
			ERouting e = (ERouting) obj;
			if (e.getName().equalsIgnoreCase(this.getName()) && e.getRevision().equals(this.getRevision())) {
				return true;
			}
		}
	    return false;
    }*/

	/*public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}*/

	public Set<EStep> getSteps() {
		return steps;
	}

	public void setSteps(Set<EStep> steps) {
		this.steps = steps;
	}

	public EStep findStepByName(String stepName) {
		if (this.steps != null) {
			for (EStep s: this.steps) {
				if (s.getName().equals(stepName)) return s;
			}
		}
		return null;
	}

	public EStep findStartStep() {
		if (this.steps != null) {
			for (EStep s: this.steps) {
				if (s.getType() == StepType.Start) return s;
			}
		}
		return null;
	}  
     
	public EStep findEndStep() {
		if (this.steps != null) {
			for (EStep s: this.steps) {
				if (s.getType() == StepType.End) return s;
			}
		}
		return null;
	}  
}
