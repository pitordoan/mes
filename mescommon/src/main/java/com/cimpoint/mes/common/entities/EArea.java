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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimpoint.common.entities.CustomAttributes;

@Entity
@Table(name="Area")
public class EArea implements Serializable {
	private static final long serialVersionUID = 2435783096843384947L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="Description", length=255)
	private String description;
		
	@ManyToOne //(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="SiteId")
	private ESite site;
	
	@OneToMany(mappedBy = "area", targetEntity = EProductionLine.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EProductionLine> productionLines;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	public EArea() {}
	
	public EArea(String name, String description, ESite optSite, Set<EProductionLine> optProductionLines, CustomAttributes customAttributes) {
		this.setName(name);
		this.setDescription(description);
		this.setSite(optSite);
		this.setProductionLines(optProductionLines);
		this.setCustomAttributes(customAttributes);
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

	public ESite getSite() {
		return site;
	}

	public void setSite(ESite site) {
		this.site = site;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public Set<EProductionLine> getProductionLines() {
		return productionLines;
	}

	public void setProductionLines(Set<EProductionLine> productionLines) {
		if (productionLines != null) {
			for (EProductionLine pdl: productionLines) {
				pdl.setArea(this);
			}
		}
		this.productionLines = productionLines;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EArea && obj != null) {
			EArea e = (EArea) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
     
    /*public int hashCode() {  
        return this.name.hashCode();
    } */
}
