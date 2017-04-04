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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;

@Entity
@Table(name="WorkCenter")
public class EWorkCenter implements Serializable {
	private static final long serialVersionUID = 3919522219673124937L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description", length=255)
	private String description;	
		
	@ManyToOne
	@JoinColumn(name="AreaId")
	private EArea area;
	
	@ManyToOne
	@JoinColumn(name="ProductionLineId")
	private EProductionLine productionLine; //optional
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EStep> steps;
	
	@OneToMany(mappedBy = "workCenter", targetEntity = EEquipment.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EEquipment> equipments;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	public EWorkCenter() {}
	
	public EWorkCenter(String name, String description, EArea optArea, 
			Set<EEquipment> optEquipments, EProductionLine optProductionLine, CustomAttributes customAttributes) {
		this.setName(name);
		this.setDescription(description);
		this.setArea(optArea);
		this.setEquipments(optEquipments);
		this.setProductionLine(optProductionLine);
		this.setCustomAttributes(customAttributes);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EArea getArea() {
		return area;
	}

	public void setArea(EArea area) {
		this.area = area;
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

	public Set<EEquipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(Set<EEquipment> equipments) {
		this.equipments = equipments;
	}

	public EProductionLine getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(EProductionLine productionLine) {
		this.productionLine = productionLine;
	}

	public Set<EStep> getSteps() {
		return steps;
	}

	public void setSteps(Set<EStep> steps) {
		this.steps = steps;
	}

	public void removeStep(EStep step) {
		if (this.steps != null) {
			this.steps.remove(step);
		}
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EWorkCenter && obj != null) {
			EWorkCenter wc = (EWorkCenter) obj;
			if (wc.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }
}
