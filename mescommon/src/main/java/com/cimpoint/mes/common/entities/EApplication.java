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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Application")
public class EApplication implements Serializable {
	private static final long serialVersionUID = 8111825754962144993L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Category", length=50, nullable=false)
	private String category;

	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description", length=255)
	private String description;
	
	@Column(name="Version", length=30)
	private String version;
		
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ELayout layout;
	
	public EApplication() {}
	
	public EApplication(String name, String version, String category, String description, ELayout layout) {		
		this.setName(name);
		this.setVersion(version);
		this.setCategory(category);
		this.setDescription(description);
		this.setLayout(layout);
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

	public ELayout getLayout() {
		return layout;
	}

	public void setLayout(ELayout layout) {
		this.layout = layout;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
