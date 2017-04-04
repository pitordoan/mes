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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DataAttribute")
public class EAttribute implements Serializable {
	private static final long serialVersionUID = 3627289454106543742L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
				
	@Column(name="Attribute", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Value", length=225)
	private String value;
	
	@ManyToOne
	@JoinColumn(name="DataSetId")
	private EDataSet dataSet;
		
	public EAttribute() {}
	
	public EAttribute(EDataSet dataSet, String attributeName, String value) {
		this.setDataSet(dataSet);
		this.setName(attributeName);
		this.setValue(value);
		if (dataSet.getAttributes() == null) {
			dataSet.setAttributes(new HashSet<EAttribute>());
		}
		dataSet.getAttributes().add(this);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String attribute) {
		this.name = attribute;
	}
	
	public EDataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(EDataSet dataSet) {
		this.dataSet = dataSet;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EAttribute && obj != null) {
			EAttribute e = (EAttribute) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
     
    public int hashCode() {  
        return this.name.hashCode();
    } 
}
