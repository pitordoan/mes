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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.PartCatergory;
import com.cimpoint.mes.common.MESConstants.Object.UnitOfMeasure;

@Entity
@Table(name="Part", uniqueConstraints=@UniqueConstraint(columnNames= {"Name", "Revision"}))
public class EPart implements Serializable {
	private static final long serialVersionUID = 1724815840994396648L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=30, nullable=false)
	private String name;
	
	@Column(name="Description", length=255)
	private String description;
	
	@Column(name="Revision", length=30, nullable=false)
	private String revision;
	
	@Column(name="Quantity", length=20, nullable=true)
	private String quantity;
	
	@Column(name="UnitOfMeasure", length=20, nullable=true)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitOfMeasure unitOfMeasure;
	
	@Column(name="Category", length=20, nullable=true)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.PartCatergory category;
	
//	@ManyToMany(mappedBy = "parts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
//	private Set<EBom> boms;
	
	@Column(name="StartDate", length=20, nullable=true)
	private String startDate;
	
	@Column(name="EndDate", length=20, nullable=true)
	private String endDate;
		
	public EPart() {}
	
	public EPart(String partName, String desc, String partRevision, String quantity, 
			UnitOfMeasure unitOfMeasure, PartCatergory category, Set<EBom> boms, String startDate, String endDate) {
		this.name = partName;
		this.description = desc;
		this.revision = partRevision;
		this.unitOfMeasure = unitOfMeasure;
		this.category = category;
//		this.boms = boms;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getRevision() {
		return this.revision;
	}
	
	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public MESConstants.Object.PartCatergory getCategory() {
		return category;
	}

	public void setCategory(MESConstants.Object.PartCatergory category) {
		this.category = category;
	}

//	public Set<EBom> getBoms() {
//		return boms;
//	}
//
//	public void setBoms(Set<EBom> boms) {
//		this.boms = boms;
//	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof EPart && obj != null) {
			EPart e = (EPart) obj;
			if (e.getName().equalsIgnoreCase(this.getName()) && e.getRevision().equalsIgnoreCase(this.getRevision())) {
				return true;
			}
		}
	    return false;
    }  
	
	
}
