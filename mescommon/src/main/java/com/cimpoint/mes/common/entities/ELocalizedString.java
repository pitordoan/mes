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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimpoint.common.Constants;

@Entity
@Table(name="LocalizedString")
public class ELocalizedString implements Serializable {
	private static final long serialVersionUID = -4138997902241987005L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
				
	@Column(name="Name", length=225, nullable=false, unique=true)
	private String name;
	
	@Column(name="Locale", length=5, nullable=false)
	@Enumerated(EnumType.STRING)
	private Constants.Locale locale;
	
	@Column(name="Value", length=225, nullable=false)
	private String value;
	
	@ManyToOne
	@JoinColumn(name="DictionaryId")
	private EDictionary dictionary;
		
	public ELocalizedString() {}
	
	public ELocalizedString(EDictionary dictionary, String name, Constants.Locale locale, String value) {
		this.setDictionary(dictionary);
		this.setName(name);
		this.setLocale(locale);
		this.setValue(value);
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

	public Constants.Locale getLocale() {
		return locale;
	}

	public void setLocale(Constants.Locale locale) {
		this.locale = locale;
	}

	public EDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(EDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof ELocalizedString && obj != null) {
			ELocalizedString e = (ELocalizedString) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
}
