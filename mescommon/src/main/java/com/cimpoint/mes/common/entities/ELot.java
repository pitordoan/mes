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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;
import com.cimpoint.mes.common.MESConstants;

@Configurable(dependencyCheck = true)
@Entity
@Table(name="Lot")
public class ELot implements Serializable, EntityTransactional {	
	private static final long serialVersionUID = 3552815895441238425L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Number", length=20, nullable=false, unique=true)
	private String number;
	
	@Column(name="OriginalNumber", length=20)
	private String originalNumber;
	
	@Column(name="Type", length=10)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.LotType type; //Discrete or Process
	
	@Column(name="WorkOrderNumber", length=20)
	private String workOrderNumber;
	
	@Column(name="WorkOrderIteNumber", length=20)
	private String workOrderItemNumber;
	
	@ManyToOne
	@JoinColumn(name="ContainerId")
	private EContainer container;
	
	@ManyToOne
	@JoinColumn(name="BatchId")
	private EBatch processBatch;
	
	@OneToMany(mappedBy = "lot", targetEntity = EUnit.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EUnit> units;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
	
	@Column(name="Quantity", length=20)
	private String quantity;
	
	@Column(name="UnitOfMeasure", length=20)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitOfMeasure unitOfMeasure;
	
	@Column(name="MaterialNumber", length=20)
	private String materialName;
	
	@Column(name="MaterialRevision", length=20)
	private String materialRevision;
		
	@Column(name="CreatedBy", length=30)
	private String creator;
		
	@Column(name="CreatedTimeDecoder", length=30)
	private String createdTimeDecoder;
	
	@Column(name="CreatedTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
		
	@Column(name="FinishedTimeDecoder", length=30)
	private String finishedTimeDecoder;
	
	@Column(name="FinishedTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedTime;
	
	@Column(name="ClosedTimeDecoder", length=30) 
	private String closedTimeDecoder;
	
	@Column(name="ClosedTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedTime;
	
	@Column(name="PromisedShipTimeDecoder", length=30)
	private String promisedShipTimeDecoder;
	
	@Column(name="PromisedShipTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date promisedShipTime;
	
	@Column(name="ShippedTimeDecoder", length=30)
	private String shippedTimeDecoder;
	
	@Column(name="ShippedTime", length=20)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date shippedTime;
	
	@Embedded
	private CustomAttributes customAttributes;
		
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETrxAttributes trxAttributes;
		
	//@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	//private ETransitionAttributes transitionAttributes;
		
	@Transient
	private Set<ELot> splittedLots;
	
	//@Transient
	//private EWorkOrder workOrder;
	
	/*@Transient
	private EPart discretePart;*/
	
	@Transient
	private EComponent processComponent;
	
	public ELot() {}

	public ELot(MESConstants.Object.LotType type, String originalLotNumber, String lotNumber, String workOrderNumber, 
			Set<EUnit> units, String partNumber, String partRevision, CustomAttributes customAttributes) throws Exception {
		this.setOriginalNumber(originalLotNumber);
		this.setType(type);
		this.setNumber(lotNumber);
		this.setMaterialName(partNumber);
		this.setMaterialRevision(partRevision);
		this.setWorkOrderNumber(workOrderNumber);
		if (units != null) {
			this.setUnits(units);
			this.setUnitOfMeasure(MESConstants.Object.UnitOfMeasure.Each);
			this.setQuantity(String.valueOf(units.size()));
		}
		this.setCustomAttributes(customAttributes);	
	}

	public ELot(MESConstants.Object.LotType type, String originalLotNumber, String lotNumber, String workOrderNumber, String workOrderItemNumber, 
			Set<EUnit> units, String partNumber, String partRevision, CustomAttributes customAttributes) throws Exception {
		this.setOriginalNumber(originalLotNumber);
		this.setType(type);
		this.setNumber(lotNumber);
		this.setMaterialName(partNumber);
		this.setMaterialRevision(partRevision);
		this.setWorkOrderNumber(workOrderNumber);
		this.setWorkOrderItemNumber(workOrderItemNumber);
		if (units != null) {
			this.setUnits(units);
			this.setUnitOfMeasure(MESConstants.Object.UnitOfMeasure.Each);
			this.setQuantity(String.valueOf(units.size()));
		}
		this.setCustomAttributes(customAttributes);
	}
	
	public ELot(MESConstants.Object.LotType type, String originalLotNumber, String lotNumber, String workOrderNumber, Quantity quantity) throws Exception {
		this.setOriginalNumber(originalLotNumber);
		this.setType(type);
		this.setNumber(lotNumber);
		this.setQuantity(quantity.asString());
		this.setUnitOfMeasure(quantity.getUnitOfMeasure());
		this.setWorkOrderNumber(workOrderNumber);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public String getWorkOrderItemNumber() {
		return workOrderItemNumber;
	}

	public void setWorkOrderItemNumber(String workOrderItemNumber) {
		this.workOrderItemNumber = workOrderItemNumber;
	}

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialRevision() {
		return materialRevision;
	}

	public void setMaterialRevision(String materialRevision) {
		this.materialRevision = materialRevision;
	}
			
	public Set<EUnit> getUnits() {
		return units;
	}

	public void setUnits(Set<EUnit> units) {
		this.units = units;
	}

	public EContainer getContainer() {
		return container;
	}

	public void setContainer(EContainer container) {
		this.container = container;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedTimeDecoder() {
		return createdTimeDecoder;
	}

	public void setCreatedTimeDecoder(String createdTimeDecoder) {
		this.createdTimeDecoder = createdTimeDecoder;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getFinishedTimeDecoder() {
		return finishedTimeDecoder;
	}

	public void setFinishedTimeDecoder(String finishedTimeDecoder) {
		this.finishedTimeDecoder = finishedTimeDecoder;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public String getClosedTimeDecoder() {
		return closedTimeDecoder;
	}

	public void setClosedTimeDecoder(String closedTimeDecoder) {
		this.closedTimeDecoder = closedTimeDecoder;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	public String getPromisedShipTimeDecoder() {
		return promisedShipTimeDecoder;
	}

	public void setPromisedShipTimeDecoder(String promisedShipTimeDecoder) {
		this.promisedShipTimeDecoder = promisedShipTimeDecoder;
	}

	public Date getPromisedShipTime() {
		return promisedShipTime;
	}

	public void setPromisedShipTime(Date promisedShipTime) {
		this.promisedShipTime = promisedShipTime;
	}

	public String getShippedTimeDecoder() {
		return shippedTimeDecoder;
	}

	public void setShippedTimeDecoder(String shippedTimeDecoder) {
		this.shippedTimeDecoder = shippedTimeDecoder;
	}

	public Date getShippedTime() {
		return shippedTime;
	}

	public void setShippedTime(Date shippedTime) {
		this.shippedTime = shippedTime;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MESConstants.Object.LotType getType() {
		return type;
	}

	public void setType(MESConstants.Object.LotType type) {
		this.type = type;
	}

	public EBatch getProcessBatch() {
		return processBatch;
	}

	public void setProcessBatch(EBatch processBatch) {
		this.processBatch = processBatch;
	}

	public Set<ELot> getSplittedLots() {
		return splittedLots;
	}

	public void setSplittedLots(Set<ELot> splittedLots) {
		this.splittedLots = splittedLots;
	}

	public String getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}
	
	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	/*public void setTransitionAttributes(ETransitionAttributes transitionalAttributes) {
		this.transitionAttributes = transitionalAttributes;
	}

	@Override
	public ETransitionAttributes getTransitionAttributes() {
		return this.transitionAttributes;
	}*/

	/*public EWorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(EWorkOrder workOrder) {
		this.workOrder = workOrder;
	}*/

	/*public EPart getDiscretePart() {
		return discretePart;
	}

	public void setDiscretePart(EPart discretePart) {
		this.discretePart = discretePart;
	}*/

	public EComponent getProcessComponent() {
		return processComponent;
	}

	public void setProcessComponent(EComponent processComponent) {
		this.processComponent = processComponent;
	}

	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Override
	public void setTrxAttributes(ETrxAttributes trxAttributes) {
		this.trxAttributes = trxAttributes;
	}

	@Override
	public ETrxAttributes getAtrxAttributes() {
		return this.trxAttributes;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof ELot && obj != null) {
			ELot e = (ELot) obj;
			if (e.getNumber().equalsIgnoreCase(this.getNumber())) {
				return true;
			}
		}
	    return false;
    }
	
	public String getTypeHierarchy() {
		String hierarchy = "";
		if (this.workOrderNumber != null && !this.workOrderNumber.isEmpty()) {
			hierarchy = "WorkOrder";
		}
		
		if (this.workOrderItemNumber != null && !this.workOrderItemNumber.isEmpty()) {
			hierarchy += "\\WorkOrderItem";
		}
		
		hierarchy += "\\[Lot]";
		
		if (this.units != null && this.units.size() > 0) {
			return hierarchy + "\\Units(" + this.units.size() + ")";
		}
		else {
			return hierarchy;
		}
	}
}

