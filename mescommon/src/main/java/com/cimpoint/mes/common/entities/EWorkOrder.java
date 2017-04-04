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
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;
import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="WorkOrder")
public class EWorkOrder implements Serializable, EntityTransactional {
	private static final long serialVersionUID = -4033156050452042389L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Number", length=20, nullable=false, unique=true)
	private String number;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
	
	@OneToMany(mappedBy = "workOrder", targetEntity = EWorkOrderItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EWorkOrderItem> items;
	
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETrxAttributes trxAttributes;
	
	/*@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETransitionAttributes transitionAttributes;*/
	
	@Column(name="Quantity", length=20)
	private String quantity;
	
	@Column(name="UnitOfMeasure", length=20, nullable=false)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitOfMeasure unitOfMeasure;

	@Column(name="PromisedShipTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date promisedShipTime;
	
	@Embedded
	private CustomAttributes customAttributes;
			
	public EWorkOrder() {}
	
	public EWorkOrder(String woNumber, Set<EWorkOrderItem> workOrderItems, MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		this.setNumber(woNumber);
		for (EWorkOrderItem woi: workOrderItems) {
			woi.setWorkOrder(this);
		}
		this.setItems(workOrderItems);
		this.setQuantity(String.valueOf(workOrderItems.size()));
		this.setUnitOfMeasure(unitOfMeasure);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Set<EWorkOrderItem> getItems() {
		return items;
	}

	public void setItems(Set<EWorkOrderItem> items) {
		this.items = items;
	}
	
	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	/*@Override
	public ETransitionAttributes getTransitionAttributes() {
		return this.transitionAttributes;
	}

	@Override
	public void setTransitionAttributes(
			ETransitionAttributes transitionAttributes) {
		this.transitionAttributes = transitionAttributes;
		
	}*/

	@Override
	public void setTrxAttributes(ETrxAttributes trxAttributes) {
		this.trxAttributes = trxAttributes;
	}

	@Override
	public ETrxAttributes getAtrxAttributes() {
		return this.trxAttributes;
	}
	
	public Date getPromisedShipTime() {
		return promisedShipTime;
	}

	public void setPromisedShipTime(Date promisedShipTime) {
		this.promisedShipTime = promisedShipTime;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof EWorkOrder && obj != null) {
			EWorkOrder e = (EWorkOrder) obj;
			if (e.getNumber().equalsIgnoreCase(this.getNumber())) {
				return true;
			}
		}
	    return false;
    }

	public String getTypeHierarchy() {
		if (this.items != null && this.items.size() > 0) {
			return "[WorkOrder]\\WorkOrderItem(" + this.items.size() + ")";
		}
		else {
			return "[WorkOrder]";
		}
	}
}
