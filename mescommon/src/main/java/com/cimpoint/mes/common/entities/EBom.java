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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Bom" , uniqueConstraints=@UniqueConstraint(columnNames= {"Name", "Revision"}))
public class EBom implements Serializable {
	private static final long serialVersionUID = 5082841654413884294L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", nullable=false)
	private String name;
	
	@Column(name="Description", length=255, nullable=true)
	private String description;
	
	@Column(name="Revision", length=30, nullable=false)
	private String revision;
	
	@Column(name="StartDate", length=20, nullable=true)
	private String startDate;
	
	@Column(name="EndDate", length=20, nullable=true)
	private String endDate;
	
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
//	private Set<EBomComposition> compositions;
	
	@OneToMany(mappedBy = "bom", targetEntity = EBomItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EBomItem> bomItems;
	
	public EBom() {}
	
	public EBom(String name, String description, String revision, String startDate, String endDate, Set<EBomItem> bomItems) {
		this.setName(name);
		this.setDescription(description);
		this.setRevision(revision);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setBomItems(bomItems);
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
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

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
		if (obj instanceof EBom && obj != null) {
			EBom e = (EBom) obj;
			if (e.getName().equalsIgnoreCase(this.getName()) && e.getRevision().equalsIgnoreCase(this.getRevision())) {
				return true;
			}
		}
	    return false;
    }  
     
//    public int hashCode() {  
//        return this.name.hashCode();
//    }

	public Set<EBomItem> getBomItems() {
		return bomItems;
	}

	public void setBomItems(Set<EBomItem> bomItems) {
		this.bomItems = bomItems;
	}

}
