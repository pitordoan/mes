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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="Transition")
public class ETransition implements Serializable {
	private static final long serialVersionUID = -1840616661622014358L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false)
	private String name;
	
	@Column(name="TransferQuantity")
	private String transferQuantity;
	
	@Column(name="TransferUnitOfMeasure")
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitOfMeasure transferUnitOfMeasure;
			
	@Column(name="RoutingName")
	private String routingName;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="ToStepId")
	private EStep toStep;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="FromStepId")
	private EStep fromStep;
		
	@Column(name="ReasonDictId", nullable=true)
	private Long reasonDictionaryId;
	
	@Embedded
	private CustomAttributes customAttributes;
	
	@Transient
	private String toStepName;
	
	@Transient
	private String fromStepName;
	
	public ETransition() {}
	
	public ETransition(String transitionName, Quantity transferQuantity, EDictionary reasonDictionary, CustomAttributes customAttributes) {
		this.setName(transitionName);
		
		if (transferQuantity != null) {
			this.setTransferQuantity(transferQuantity.asString());
			this.setTransferUnitOfMeasure(transferQuantity.getUnitOfMeasure());
		}
		
		if (reasonDictionary != null) {
			this.setReasonDictionaryId(reasonDictionary.getId());
		}
		
		this.setCustomAttributes(customAttributes);
	}
	
	public Long getId() {
		return this.id;
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

	public EStep getFromStep() {
		return fromStep;
	}

	public void setFromStep(EStep fromStep) {
		this.fromStep = fromStep;
	}

	public EStep getToStep() {
		return toStep;
	}

	public void setToStep(EStep toStep) {
		this.toStep = toStep;
	}

	/*public ERouting getRouting() {
		return routing;
	}

	public void setRouting(ERouting routing) {
		this.routing = routing;
	}*/
	
	public String getTransferQuantity() {
		return transferQuantity;
	}

	public void setTransferQuantity(String transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	
	public MESConstants.Object.UnitOfMeasure getTransferUnitOfMeasure() {
		return transferUnitOfMeasure;
	}

	public void setTransferUnitOfMeasure(MESConstants.Object.UnitOfMeasure transferUnitOfMeasure) {
		this.transferUnitOfMeasure = transferUnitOfMeasure;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public Long getReasonDictionaryId() {
		return reasonDictionaryId;
	}

	public void setReasonDictionaryId(Long reasonDictionaryId) {
		this.reasonDictionaryId = reasonDictionaryId;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof ETransition && obj != null) {
			ETransition e = (ETransition) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }

	public String getRoutingName() {
		return routingName;
	}

	public void setRoutingName(String routingName) {
		this.routingName = routingName;
	}

	public String getToStepName() {
		return toStepName;
	}

	public void setToStepName(String toStepName) {
		this.toStepName = toStepName;
	}

	public String getFromStepName() {
		return fromStepName;
	}

	public void setFromStepName(String fromStepName) {
		this.fromStepName = fromStepName;
	}  
     
    /*public int hashCode() {  
        return this.name.hashCode();
    } */
}
