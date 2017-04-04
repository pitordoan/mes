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

import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="MfgBomItem")
public class EMfgBomItem implements Serializable {
	private static final long serialVersionUID = 3866711789472271651L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
		
	@Column(name="ContainerPartId")
	private Long containerPartId;
	
	@Column(name="ContainerName")
	private String containerName;
	
	@Column(name="ContainerRevision")
	private String containerRevision;
	
	@Column(name="PartId")
	private Long partId;
	
	@Column(name="PartName")
	private String partName;
	
	@Column(name="PartRevision")
	private String partRevision;
	
	@Column(name="ConsumptionType")
	@Enumerated(EnumType.STRING)
	private MESConstants.Consumption.Type consumptionType;	
	
	@Column(name="ConsumptionRoutingId")
	private Long consumptionRoutingId;
	
	@Column(name="ConsumptionRoutingName")
	private String consumptionRoutingName;
	
	@Column(name="ConsumptionRoutingRev")
	private String consumptionRoutingRevision;
	
	@Column(name="ConsumptionStepId")
	private Long consumptionStepId;
	
	@Column(name="ConsumptionStepName")
	private String consumptionStepName;
	
	@Column(name="ConsumptionQty")
	private String consumptionQuantity;
	
	@Column(name="ConsumptionUoM")
	private String consumptionUnitOfMeasure;
	
	@Column(name="ProduceRoutingId")
	private Long produceRoutingId;
	
	@Column(name="ProduceRoutingName")
	private String produceRoutingName;
	
	@Column(name="ProduceRoutingRev")
	private String produceRoutingRevision;
	
	@Column(name="ProduceStepId")
	private Long produceStepId;
	
	@Column(name="ProduceStepName")
	private String produceStepName;
	
	@Column(name="ProduceQty")
	private String produceQuantity;
	
	@Column(name="ProduceUoM")
	private String produceUnitOfMeasure;
	
	@ManyToOne 
	@JoinColumn(name="MfgBomId")
	private EMfgBom mfgBom;
	
	public EMfgBomItem() {}
		
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Long getContainerPartId() {
		return containerPartId;
	}

	public void setContainerPartId(Long containerPartId) {
		this.containerPartId = containerPartId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public MESConstants.Consumption.Type getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(MESConstants.Consumption.Type consumptionType) {
		this.consumptionType = consumptionType;
	}

	public Long getConsumptionRoutingId() {
		return consumptionRoutingId;
	}

	public void setConsumptionRoutingId(Long consumptionRoutingId) {
		this.consumptionRoutingId = consumptionRoutingId;
	}

	public String getConsumptionRoutingRevision() {
		return consumptionRoutingRevision;
	}

	public void setConsumptionRoutingRevision(String consumptionRoutingRevision) {
		this.consumptionRoutingRevision = consumptionRoutingRevision;
	}

	public Long getConsumptionStepId() {
		return consumptionStepId;
	}

	public void setConsumptionStepId(Long consumptionStepId) {
		this.consumptionStepId = consumptionStepId;
	}

	public String getConsumptionQuantity() {
		return consumptionQuantity;
	}

	public void setConsumptionQuantity(String consumptionQuantity) {
		this.consumptionQuantity = consumptionQuantity;
	}

	public String getConsumptionUnitOfMeasure() {
		return consumptionUnitOfMeasure;
	}

	public void setConsumptionUnitOfMeasure(String consumptionUnitOfMeasure) {
		this.consumptionUnitOfMeasure = consumptionUnitOfMeasure;
	}

	public Long getProduceRoutingId() {
		return produceRoutingId;
	}

	public void setProduceRoutingId(Long produceRoutingId) {
		this.produceRoutingId = produceRoutingId;
	}

	public String getProduceRoutingRevision() {
		return produceRoutingRevision;
	}

	public void setProduceRoutingRevision(String produceRoutingRevision) {
		this.produceRoutingRevision = produceRoutingRevision;
	}

	public Long getProduceStepId() {
		return produceStepId;
	}

	public void setProduceStepId(Long produceStepId) {
		this.produceStepId = produceStepId;
	}

	public String getProduceQuantity() {
		return produceQuantity;
	}

	public void setProduceQuantity(String produceQuantity) {
		this.produceQuantity = produceQuantity;
	}

	public String getProduceUnitOfMeasure() {
		return produceUnitOfMeasure;
	}

	public void setProduceUnitOfMeasure(String produceUnitOfMeasure) {
		this.produceUnitOfMeasure = produceUnitOfMeasure;
	}

	public String getPartRevision() {
		return partRevision;
	}

	public void setPartRevision(String partRevision) {
		this.partRevision = partRevision;
	}

	public EMfgBom getMfgBom() {
		return mfgBom;
	}

	public void setMfgBom(EMfgBom mfgBom) {
		this.mfgBom = mfgBom;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getContainerRevision() {
		return containerRevision;
	}

	public void setContainerRevision(String containerRevision) {
		this.containerRevision = containerRevision;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getConsumptionRoutingName() {
		return consumptionRoutingName;
	}

	public void setConsumptionRoutingName(String consumptionRoutingName) {
		this.consumptionRoutingName = consumptionRoutingName;
	}

	public String getConsumptionStepName() {
		return consumptionStepName;
	}

	public void setConsumptionStepName(String consumptionStepName) {
		this.consumptionStepName = consumptionStepName;
	}

	public String getProduceRoutingName() {
		return produceRoutingName;
	}

	public void setProduceRoutingName(String produceRoutingName) {
		this.produceRoutingName = produceRoutingName;
	}

	public String getProduceStepName() {
		return produceStepName;
	}

	public void setProduceStepName(String produceStepName) {
		this.produceStepName = produceStepName;
	}

	
}
