/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
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
@Table(name="MfgBom", uniqueConstraints=@UniqueConstraint(columnNames= {"Name", "Revision"}))
public class EMfgBom implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@OneToMany(mappedBy = "mfgBom", targetEntity = EMfgBomItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EMfgBomItem> mfgBomItems;
	
	public EMfgBom() {}

	public EMfgBom(String name, String description, String revision, String startDate, String endDate, Set<EMfgBomItem> mfgBomItems) {
		this.setName(name);
		this.setDescription(description);
		this.setRevision(revision);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setMfgBomItems(mfgBomItems);
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

	public Set<EMfgBomItem> getMfgBomItems() {
		return mfgBomItems;
	}

	public void setMfgBomItems(Set<EMfgBomItem> mfgBomItems) {
		this.mfgBomItems = mfgBomItems;
	}
	
}
