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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="DataSet")
public class EDataSet implements Serializable {
	private static final long serialVersionUID = -61685395370057199L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="AttributeNames")
	private String attributeNames;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
		
	@Column(name="AssociatedObjectId")
	private Long associatedObjectId;
	
	@Column(name="AssociatedObjectType")
	private String associatedObjectType;
	
	@OneToMany(mappedBy = "dataSet", targetEntity = EAttribute.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)  
	private Set<EAttribute> attributes;
		
	public EDataSet() {}
	
	public EDataSet(String name, String[] attributeNames, Long associatedObjectId, String associatedObjectType) {
		this.setName(name);
		
		if (attributes != null) {
			Set<EAttribute> attrs = new HashSet<EAttribute>();
			for (String attr: attributeNames) {
				EAttribute e = new EAttribute(this, attr, (String) null);
				attrs.add(e);
			}
			this.setAttributes(attrs);
		}
		
		this.setAssociatedObjectId(associatedObjectId);
		this.setAssociatedObjectType(associatedObjectType);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Long getAssociatedObjectId() {
		return associatedObjectId;
	}

	public void setAssociatedObjectId(Long associatedObjectId) {
		this.associatedObjectId = associatedObjectId;
	}

	public String getAssociatedObjectType() {
		return associatedObjectType;
	}

	public void setAssociatedObjectType(String associatedObjectType) {
		this.associatedObjectType = associatedObjectType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(String attributeNames) {
		this.attributeNames = attributeNames;
	}

	public Set<EAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<EAttribute> attributes) {
		this.attributes = attributes;
	}

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EDataSet && obj != null) {
			EDataSet e = (EDataSet) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
}
