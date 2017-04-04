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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;

@Entity
@Table(name="ProductionLine")
public class EProductionLine implements Serializable {
	private static final long serialVersionUID = 1846318345867600120L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description")
	private String description;
	
	@ManyToOne //(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="areaId")
	private EArea area;
	
	@OneToMany(mappedBy = "productionLine", targetEntity = EWorkCenter.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EWorkCenter> workCenters;
		
	@Embedded
	private CustomAttributes customAttributes;
	
	public EProductionLine() {}
	
	public EProductionLine(String name, String description, EArea optArea, Set<EWorkCenter> optWorkCenters, CustomAttributes customAttributes) {
		this.setName(name);
		this.setDescription(description);
		this.setArea(optArea);
		this.setWorkCenters(optWorkCenters);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<EWorkCenter> getWorkCenters() {
		return workCenters;
	}

	public void setWorkCenters(Set<EWorkCenter> workCenters) {
		if (workCenters != null) {
			for (EWorkCenter wc: workCenters) {
				wc.setProductionLine(this);
			}
		}
		this.workCenters = workCenters;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public EArea getArea() {
		return area;
	}

	public void setArea(EArea area) {
		this.area = area;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof EProductionLine && obj != null) {
			EProductionLine e = (EProductionLine) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
     
    /*public int hashCode() {  
        return this.name.hashCode();
    } */
}
