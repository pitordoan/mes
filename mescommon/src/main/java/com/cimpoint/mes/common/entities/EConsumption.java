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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="Consumption")
public class EConsumption implements Serializable {
	private static final long serialVersionUID = 6886763048830821652L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
		
	@Column(name="Type")
	@Enumerated(EnumType.STRING)
	private MESConstants.Material.Type materialType;
	
	@Column(name="MaterialId")
	private Long materialId;
			
	@Column(name="ConsumptionType")
	@Enumerated(EnumType.STRING)
	private MESConstants.Consumption.Type consumptionType;
	
	@Column(name="ObjectId")
	private Long objectId;
	
	@Column(name="ObjectType")
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.Type objectType;
	
	@Column(name="RoutingName")
	private String routingName;
	
	@Column(name="StepName")
	private String stepName;
	
	@Column(name="TimeDecoder")
	private String timeDecoder;
	
	@Column(name="Time")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
		
	@ManyToOne
	@JoinColumn(name="TravelerId")
	private ETraveler traveler;
	
	public EConsumption() {
		
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EContainer container) {
		this.setMaterialType(MESConstants.Material.Type.Part);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(part.getId());
		this.setObjectId(container.getId());
		this.setObjectType(MESConstants.Object.Type.Container);
		//this.setRoutingName(container.getTransitionAttributes().getRoutingName());
		//this.setStepName(container.getTransitionAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EBatch batch) {
		this.setMaterialType(MESConstants.Material.Type.Part);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(part.getId());
		this.setObjectId(batch.getId());
		this.setObjectType(MESConstants.Object.Type.Batch);
		//this.setRoutingName(batch.getTrxAttributes().getRoutingName());
		//this.setStepName(batch.getTrxAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EPart part, ELot lot) {
		this.setMaterialType(MESConstants.Material.Type.Part);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(part.getId());
		this.setObjectId(lot.getId());
		this.setObjectType(MESConstants.Object.Type.Lot);
		//this.setRoutingName(lot.getTransitionAttributes().getRoutingName());
		//this.setStepName(lot.getTransitionAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EPart part, EUnit unit) {
		this.setMaterialType(MESConstants.Material.Type.Part);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(part.getId());
		this.setObjectId(unit.getId());
		this.setObjectType(MESConstants.Object.Type.Unit);
		//this.setRoutingName(unit.getTransitionAttributes().getRoutingName());
		//this.setStepName(unit.getTransitionAttributes().getStepName());
		this.setTime(new Date());
	}
		
	public EConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, EContainer container) {
		this.setMaterialType(MESConstants.Material.Type.Component);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(component.getId());
		this.setObjectId(container.getId());
		this.setObjectType(MESConstants.Object.Type.Container);
		//this.setRoutingName(container.getTransitionAttributes().getRoutingName());
		//this.setStepName(container.getTransitionAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, EBatch batch) {
		this.setMaterialType(MESConstants.Material.Type.Component);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(component.getId());
		this.setObjectId(batch.getId());
		this.setObjectType(MESConstants.Object.Type.Batch);
		//this.setRoutingName(batch.getTrxAttributes().getRoutingName());
		//this.setStepName(batch.getTrxAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public EConsumption(MESConstants.Consumption.Type consumptionType, EComponent component, ELot lot) {
		this.setMaterialType(MESConstants.Material.Type.Component);
		this.setConsumptionType(consumptionType);
		this.setMaterialId(component.getId());
		this.setObjectId(lot.getId());
		this.setObjectType(MESConstants.Object.Type.Lot);
		//this.setRoutingName(lot.getTransitionAttributes().getRoutingName());
		//this.setStepName(lot.getTransitionAttributes().getStepName());
		this.setTime(new Date());
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public ETraveler getTraveler() {
		return traveler;
	}

	public void setTraveler(ETraveler traveler) {
		this.traveler = traveler;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getRoutingName() {
		return routingName;
	}

	public void setRoutingName(String routingName) {
		this.routingName = routingName;
	}

	public String getTimeDecoder() {
		return timeDecoder;
	}

	public void setTimeDecoder(String timeDecoder) {
		this.timeDecoder = timeDecoder;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public MESConstants.Object.Type getObjectType() {
		return objectType;
	}

	public void setObjectType(MESConstants.Object.Type objectType) {
		this.objectType = objectType;
	}

	public MESConstants.Material.Type getMaterialType() {
		return materialType;
	}
	
	public void setMaterialType(MESConstants.Material.Type type) {
		materialType = type;
	}

	public MESConstants.Consumption.Type getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(MESConstants.Consumption.Type consumptionType) {
		this.consumptionType = consumptionType;
	}

	public boolean equals(Object obj) {  
		if (obj instanceof EConsumption && obj != null) {
			EConsumption e = (EConsumption) obj;
			if (e.getObjectId() == this.getObjectId() && 
					e.getMaterialId() == this.getMaterialId() && 
					e.getMaterialType() == this.getMaterialType()) {
				return true;
			}
		}
	    return false;
    }  
     
    public int hashCode() {  
        return this.objectId.hashCode() + this.materialId.hashCode() + this.materialType.hashCode();
    } 
}
