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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "StepStatus")
public class EStepStatus implements Serializable {
	private static final long serialVersionUID = -5695748499535169731L;

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Long id;

	@Column(name = "Name", length = 50, nullable = false)
	private String name;

	@Column(name = "Starting", nullable = false)
	private boolean starting;

	@Column(name = "Ending", nullable = false)
	private boolean ending;

	@Column(name = "NextDefaultStatusName", length = 50, nullable = true)
	private String nextDefaultStatusName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EStep> steps;
			
	public EStepStatus() {}
	
	public EStepStatus(String name, boolean isStarting, boolean isEnding, String optNextDefaultStatusName) {
		setName(name);
		setStarting(isStarting);
		setEnding(isEnding);
		setNextDefaultStatusName(optNextDefaultStatusName);
	}
	
	public Long getId() {
		return id;
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

	public boolean isStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isEnding() {
		return ending;
	}

	public void setEnding(boolean ending) {
		this.ending = ending;
	}

	public String getNextDefaultStatusName() {
		return nextDefaultStatusName;
	}

	public void setNextDefaultStatusName(String nextDefaultStatusName) {
		this.nextDefaultStatusName = nextDefaultStatusName;
	}

	public Set<EStep> getSteps() {
		return steps;
	}

	public void setSteps(Set<EStep> steps) {
		this.steps = steps;
	}
}