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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;

@Entity
@Table(name="Recipe")
public class ERecipe implements Serializable {
	private static final long serialVersionUID = -8351319121898196492L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name")
	private String name;
			
	@OneToMany(mappedBy = "recipe", targetEntity = EComponent.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private Set<EComponent> fomularComponents;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	public ERecipe() {}
	
	public ERecipe(String name, Set<EComponent> fomularComponents) {
		this.setName(name);
		this.setFomularComponents(fomularComponents);
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

	public Set<EComponent> getFomularComponents() {
		return fomularComponents;
	}

	public void setFomularComponents(Set<EComponent> fomularComponents) {
		this.fomularComponents = fomularComponents;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof ERecipe && obj != null) {
			ERecipe e = (ERecipe) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
}
