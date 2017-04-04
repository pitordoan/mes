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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;

import com.cimpoint.common.entities.CustomAttributes;

@Configurable(dependencyCheck = true)
@Entity
@Table(name="Traveler")
public class ETraveler implements Serializable {	
	private static final long serialVersionUID = 3649995522061512552L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
		
	@Column(name="ConsumptionType")
	private String consumptionType;
	
	@Column(name="WorkOrderNumber")
	private String workOrderNumber;
	
	@OneToMany(mappedBy = "traveler", targetEntity = EConsumption.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private Set<EConsumption> consumptions;
		
	@Transient
	private Set<EContainer> containers;
	
	@Transient   
	private Set<EBatch> batches;
	
	@Transient   
	private Set<ELot> lots;
	
	@Transient   
	private Set<EUnit> units;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
	
	@Column(name="Comment")
	private String comment;
			
	@Column(name="CreatedBy")
	private String creator;
		
	@Column(name="CreatedTimeDecoder")
	private String createdTimeDecoder;
	
	@Column(name="CreatedTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
		
	@Column(name="FinishedTimeDecoder")
	private String finishedTimeDecoder;
	
	@Column(name="FinishedTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedTime;
	
	@Column(name="ClosedTimeDecoder") 
	private String closedTimeDecoder;
	
	@Column(name="ClosedTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedTime;
	
	@Column(name="PromisedShipTimeDecoder")
	private String promisedShipTimeDecoder;
	
	@Column(name="PromisedShipTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date promisedShipTime;
	
	@Column(name="ShippedTimeDecoder")
	private String shippedTimeDecoder;
	
	@Column(name="ShippedTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date shippedTime;
	
	@Embedded
	private CustomAttributes customAttributes;
		
	public ETraveler() {}
	
	public ETraveler(String workOrderNumber) throws Exception {
		this.setWorkOrderNumber(workOrderNumber);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}
	
	public Set<EUnit> getUnits() {		
		return units;
	}

	public void setUnits(Set<EUnit> units) {
		this.units = units;
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
	
	public String getConsumptionType() {
		return consumptionType;
	}
	public void setConsumptionType(String consumptionType) {
		this.consumptionType = consumptionType;
	}
	public Set<EBatch> getBatches() {
		return batches;
	}
	public void setBatches(Set<EBatch> batches) {
		this.batches = batches;
	}
	public Set<ELot> getLots() {
		return lots;
	}
	public void setLots(Set<ELot> lots) {
		this.lots = lots;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<EContainer> getContainers() {
		return containers;
	}

	public void setContainers(Set<EContainer> containers) {
		this.containers = containers;
	}

	public Set<EConsumption> getConsumptions() {
		return consumptions;
	}

	public void setConsumptions(Set<EConsumption> consumptions) {
		this.consumptions = consumptions;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}
	
	public boolean equals(Object obj) {  
		if (obj instanceof ETraveler && obj != null) {
			ETraveler e = (ETraveler) obj;
			if (e.getWorkOrderNumber().equalsIgnoreCase(this.getWorkOrderNumber())) {
				return true;
			}
		}
	    return false;
    }  
}

