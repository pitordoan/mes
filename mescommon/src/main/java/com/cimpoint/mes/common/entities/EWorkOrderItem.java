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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;
import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="WorkOrderItem")
public class EWorkOrderItem implements Serializable, EntityTransactional {
	private static final long serialVersionUID = -3362872577957224029L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Number", length=20, nullable=false, unique=true)
	private String number;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
	
	@Column(name="PartNumber")
	private String partNumber;
	
	@Column(name="PartRevision")
	private String partRevision;
		
	@ManyToOne
	@JoinColumn(name="WorkOrderId", nullable=false)
	private EWorkOrder workOrder;

	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETrxAttributes trxAttributes;
	
	/*@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETransitionAttributes transitionAttributes;*/
	
	@Column(name="Quantity", length=20)
	private String quantity;
	
	@Column(name="UnitOfMeasure", length=20)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitOfMeasure unitOfMeasure;

	@Column(name="PromisedShipTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date promisedShipTime;
	
	public EWorkOrderItem() {}
	
	public EWorkOrderItem(String workOrderItemNumber, EPart part) {
		this.setNumber(workOrderItemNumber);
		this.setPartNumber(part.getName());
		this.setPartRevision(part.getRevision());
		this.setUnitOfMeasure(part.getUnitOfMeasure());
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

	public EWorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(EWorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartRevision() {
		return partRevision;
	}

	public void setPartRevision(String partRevision) {
		this.partRevision = partRevision;
	}

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
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
	
	public boolean equals(Object obj) {  
		if (obj instanceof EWorkOrderItem && obj != null) {
			EWorkOrderItem e = (EWorkOrderItem) obj;
			if (e.getNumber().equalsIgnoreCase(this.getNumber())) {
				return true;
			}
		}
	    return false;
    }  
	
	public String getTypeHierarchy() {
		String hierarchy = "WorkOrder\\[WorkOrderItem]";
		return hierarchy;
	}
}
