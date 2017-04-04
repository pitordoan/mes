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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimpoint.common.Constants;

@Entity
@Table(name="FormFieldProperty")
public class EFormFieldProperty implements Serializable {
	private static final long serialVersionUID = 8618128511138471070L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Property", length=50)
	@Enumerated(EnumType.STRING)
	private Constants.Form.FieldProperty property;
	
	@Column(name="Value", length=255)
	private String value;
		
	@Column(name="CustomCodeClassPath", length=50, nullable=true)
	private String customCodeClassPath;
			
	@Column(name="CustomCodeFunctionName", length=50, nullable=true)
	private String customCodeFunctionName;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "formFieldId")
	private EFormField formField;
	
	public EFormFieldProperty() {}
	
	public EFormFieldProperty(Constants.Form.FieldProperty property, Object value) {		
		this.setProperty(property);
		this.setValue(String.valueOf(value));
	}

	public EFormFieldProperty(Constants.Form.FieldProperty property, Object value, String customCodeClassPath, String customCodeFunctionName) {		
		this.setProperty(property);
		this.setValue(String.valueOf(value));
		this.setCustomCodeClassPath(customCodeClassPath);
		this.setCustomCodeFunctionName(customCodeFunctionName);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Constants.Form.FieldProperty getProperty() {
		return property;
	}

	public void setProperty(Constants.Form.FieldProperty property) {
		this.property = property;
	}

	public String getCustomCodeClassPath() {
		return customCodeClassPath;
	}

	public void setCustomCodeClassPath(String customCodeClassPath) {
		this.customCodeClassPath = customCodeClassPath;
	}

	public String getCustomCodeFunctionName() {
		return customCodeFunctionName;
	}

	public void setCustomCodeFunctionName(String customCodeFunctionName) {
		this.customCodeFunctionName = customCodeFunctionName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EFormField getFormField() {
		return formField;
	}

	public void setFormField(EFormField formField) {
		this.formField = formField;
	}

}
