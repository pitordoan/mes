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
import java.util.HashMap;
import java.util.Map;
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
@Table(name="FormField")
public class EFormField implements Serializable {
	private static final long serialVersionUID = 4532967392413911026L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;

	@Column(name="Name", length=50, nullable=true)
	private String name;
	
	@Column(name="Label", length=255, nullable=true)
	private String label;
	
	@Column(name="ColumnSpan")
	private Integer columnSpan;
	
	@Column(name="StartNewRow")
	private Boolean startNewRow;
	
	@Column(name="EndCurrentRow")
	private Boolean endCurrentRow;
		
	@Column(name="Type", length=30)
	@Enumerated(EnumType.STRING)
	private Constants.Form.FieldType fieldType;
		
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "formId")
	private EForm form;
	
	@OneToMany(mappedBy = "formField", targetEntity = EFormFieldProperty.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EFormFieldProperty> formFieldProperties;
	
	private transient Map<String, String> propValueMap;
		
	public EFormField() {}
	
	public EFormField(Constants.Form.FieldType type, String name, String label, int columnSpan, boolean startNewRow, boolean endCurrentRow,
			Set<EFormFieldProperty> formFieldProperties) {		
		this.setFieldType(type);
		this.setName(name);
		this.setLabel(label);
		this.setColumnSpan(columnSpan);
		this.setStartNewRow(startNewRow);
		this.setEndCurrentRow(endCurrentRow);
		this.setFormFieldProperties(formFieldProperties);
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

	public EForm getForm() {
		return form;
	}

	public void setForm(EForm form) {
		this.form = form;
	}

	public Constants.Form.FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(Constants.Form.FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public Set<EFormFieldProperty> getFormFieldProperties() {
		return formFieldProperties;
	}

	public void setFormFieldProperties(Set<EFormFieldProperty> formFieldProperties) {
		this.formFieldProperties = formFieldProperties;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getColumnSpan() {
		return columnSpan;
	}

	public void setColumnSpan(Integer columnSpan) {
		this.columnSpan = columnSpan;
	}

	public Boolean getStartNewRow() {
		return startNewRow;
	}

	public void setStartNewRow(Boolean startNewRow) {
		this.startNewRow = startNewRow;
	}

	public Boolean getEndCurrentRow() {
		return endCurrentRow;
	}

	public void setEndCurrentRow(Boolean endCurrentRow) {
		this.endCurrentRow = endCurrentRow;
	}
	
	public String getPropertyValue(Constants.Form.FieldProperty fieldProperty) {
		if (propValueMap == null && formFieldProperties != null) {
			propValueMap = new HashMap<String, String>();
			for (EFormFieldProperty prop: formFieldProperties) {
				propValueMap.put(prop.getProperty().toString(), prop.getValue());
			}
		}
		
		if (propValueMap != null) {
			return propValueMap.get(fieldProperty.toString());
		}
		return null;		
	}
}
