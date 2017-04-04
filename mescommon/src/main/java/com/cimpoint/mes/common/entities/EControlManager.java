/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ControlManager")
public class EControlManager implements Serializable{
	
	private static final long serialVersionUID = -6236142307223026382L;
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;

	@Column(name="Button")
	private String button;
	
	@Column(name="ActionName")
	private String actionName;
	
	@Column(name = "Display", nullable = false)
	private Boolean display;
	
	public EControlManager() {}

	public EControlManager(Long id, String button, String actionName,
			Boolean display) {
		this.id = id;
		this.button = button;
		this.actionName = actionName;
		this.display = display;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof EControlManager && obj != null){
		
		EControlManager manager = (EControlManager) obj;
		if ((getId().equals(manager.getId())))
			return true;
		}
		return false;
	}
}
