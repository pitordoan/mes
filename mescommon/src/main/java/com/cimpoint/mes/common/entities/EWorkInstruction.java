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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WorkInstruction")
public class EWorkInstruction implements Serializable {
	private static final long serialVersionUID = -3994330283729214793L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name")
	private String name;
		
	@Column(name="MappedState")
	private String mappedState;
			
	public EWorkInstruction() {}
	
	public EWorkInstruction(String name) {
		this.setName(name);
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

	public String getMappedState() {
		return mappedState;
	}

	public void setMappedState(String mappedState) {
		this.mappedState = mappedState;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof EWorkInstruction && obj != null) {
			EWorkInstruction e = (EWorkInstruction) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
}
