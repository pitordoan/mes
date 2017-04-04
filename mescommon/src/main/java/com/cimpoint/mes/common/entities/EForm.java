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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Form")
public class EForm implements Serializable {
	private static final long serialVersionUID = 4504156955771703399L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;

	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
				
	@OneToMany(mappedBy = "form", targetEntity = EFormField.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EFormField> formFields;
	
	@ManyToOne
	@JoinColumn(name="LayoutId")
	private ELayout layout;
	
	@Column(name="ModuleName", nullable=true)
	private String moduleName;
	
	public EForm() {}
	
	public EForm(String name) {		
		this.setName(name);
	}

	public EForm(String name, Set<EFormField> formFields) {		
		this.setName(name);
		this.setFormFields(formFields);
	}
	
	public EForm(String name, String moduleName) {		
		this.setName(name);
		this.setModuleName(moduleName);
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

	public Set<EFormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(Set<EFormField> formFields) {
		this.formFields = formFields;
	}

	public ELayout getLayout() {
		return layout;
	}

	public void setLayout(ELayout layout) {
		this.layout = layout;
	}
	
	public void addFormField(EFormField formField) {
		if (this.formFields == null) {
			this.formFields = new HashSet<EFormField>();
		}
		formField.setForm(this);
		this.formFields.add(formField);
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}	
}
