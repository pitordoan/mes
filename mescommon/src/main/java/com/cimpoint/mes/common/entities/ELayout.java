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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.Constants;

@Entity
@Table(name="Layout")
public class ELayout implements Serializable {
	private static final long serialVersionUID = 8007135722448839743L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="LayoutType", length=7)
	@Enumerated(EnumType.STRING)
	private Constants.Form.LayoutType layoutType;
	
	@OneToMany(mappedBy = "layout", targetEntity = EForm.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EForm> forms;
	
	@ManyToOne
	@JoinColumn(name="ParentLayoutId")
	private ELayout parentLayout;
	
	@OneToMany(mappedBy = "parentLayout", targetEntity = ELayout.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ELayout> childLayouts;
	
	public ELayout() {}
	
	public ELayout(Constants.Form.LayoutType layoutType) {		
		this.setLayoutType(layoutType);
	}
	
	public ELayout(Constants.Form.LayoutType layoutType, Set<EForm> forms, Set<ELayout> childLayouts) {		
		this.setLayoutType(layoutType);
		this.setForms(forms);
		this.setChildLayouts(childLayouts);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Constants.Form.LayoutType getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(Constants.Form.LayoutType layoutType) {
		this.layoutType = layoutType;
	}

	public Set<ELayout> getChildLayouts() {
		return childLayouts;
	}

	public void setChildLayouts(Set<ELayout> childLayouts) {
		this.childLayouts = childLayouts;
	}

	public ELayout getParentLayout() {
		return parentLayout;
	}

	public void setParentLayout(ELayout parentLayout) {
		this.parentLayout = parentLayout;
	}

	public Set<EForm> getForms() {
		return forms;
	}

	public void setForms(Set<EForm> forms) {
		this.forms = forms;
	}
	
	public void addForm(EForm form) {
		if (this.forms == null) {
			this.forms = new HashSet<EForm>();
		}
		form.setLayout(this);
		this.forms.add(form);
	}
}
