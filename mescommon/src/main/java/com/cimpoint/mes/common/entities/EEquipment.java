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

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;

@Entity
@Table(name="Equipment")
public class EEquipment implements Serializable {
	private static final long serialVersionUID = 240822846477393298L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
		
	@Column(name="Description", length=255)
	private String description;
		
	@ManyToOne //(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="WorkCenterId")
	private EWorkCenter workCenter;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	public EEquipment() {}
	
	public EEquipment(String name, String description, EWorkCenter workCenter, CustomAttributes customAttributes) {
		this.setName(name);
		this.setDescription(description);
		this.setWorkCenter(workCenter);
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

	public EWorkCenter getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(EWorkCenter workCenter) {
		this.workCenter = workCenter;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EEquipment && obj != null) {
			EEquipment e = (EEquipment) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
}
