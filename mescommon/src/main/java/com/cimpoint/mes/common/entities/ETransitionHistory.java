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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cimpoint.mes.common.MESConstants;

@Entity
@Table(name="StatusHistory")
public class ETransitionHistory implements Serializable {
	private static final long serialVersionUID = 5140363622223449799L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="ObjectId")
	private Long objectId;
		
	@Column(name="ObjectType")
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.Type objectType;
		
	@Column(name="StepStatus")
	private String stepStatus;
	
	@Column(name="ObjectStatus")
	private String objectStatus;
	
	@Deprecated
	@Column(name="State")
	private String state;
	
	@Column(name="StateRuleClassName")
	private String stateRuleClassName;
			
	@Column(name="PlantName")
	private String plantName;
	
	@Column(name="SiteName")
	private String siteName;
	
	@Column(name="AreaName")
	private String areaName;
	
	@Column(name="RoutingName")
	private String routingName;
		
	@Column(name="ProductionLineName")
	private String productionLineName;
	
	@Column(name="WorkCenterName")
	private String workCenterName;
	
	@Column(name="StepName")
	private String stepName;
	
	@Column(name="OperationName")
	private String operationName;
	
	@Column(name="TransitionPath")
	private String transitionPath;
	
	@Column(name="LastModifiedBy")
	private String lastModifier;
	
	//[UTC timezone]-[UTC time]-[local trx timezone]-[local trx time]
	@Column(name="StatusTimeDecoder")
	private String statusTimeDecoder;
	
	@Column(name="StatusTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date statusTime;
	
	@Column(name="StateTimeDecoder")
	private String stateTimeDecoder;
	
	@Column(name="StateTime")	
	@Temporal(TemporalType.TIMESTAMP)
	private Date stateTime;
			
	@Column(name="Comment")
	private String comment;
			
	public ETransitionHistory() {}
	
	public ETransitionHistory(ETransitionAttributes transitionAttributes) {
		this.setComment(transitionAttributes.getComment());
		this.setLastModifier(transitionAttributes.getLastModifier());
		this.setState(transitionAttributes.getState());
		this.setStateRuleClassName(transitionAttributes.getStateRuleClassName());
		this.setStateTime(transitionAttributes.getStateTime());
		this.setStateTimeDecoder(transitionAttributes.getStateTimeDecoder());
		this.setStepStatus(transitionAttributes.getStepStatus());
		this.setObjectStatus(transitionAttributes.getObjectStatus());
		this.setStatusTime(transitionAttributes.getStatusTime());
		this.setStatusTimeDecoder(transitionAttributes.getStatusTimeDecoder());
		this.setStepName(transitionAttributes.getStepName());
		this.setTransitionPath(transitionAttributes.getTransitionPath());
		this.setRoutingName(transitionAttributes.getRoutingName());
		this.setObjectId(transitionAttributes.getObjectId());
		this.setObjectType(transitionAttributes.getObjectType());
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public MESConstants.Object.Type getObjectType() {
		return objectType;
	}

	public void setObjectType(MESConstants.Object.Type objectType) {
		this.objectType = objectType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getStepStatus() {
		return stepStatus;
	}

	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}
	
	@Deprecated
	public String getState() {
		return state;
	}

	@Deprecated
	public void setState(String state) {
		this.state = state;
	}

	public String getStateRuleClassName() {
		return stateRuleClassName;
	}

	public void setStateRuleClassName(String stateRuleClassName) {
		this.stateRuleClassName = stateRuleClassName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getTransitionPath() {
		return transitionPath;
	}

	public void setTransitionPath(String transitionPath) {
		this.transitionPath = transitionPath;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getStatusTimeDecoder() {
		return statusTimeDecoder;
	}

	public void setStatusTimeDecoder(String statusTimeDecoder) {
		this.statusTimeDecoder = statusTimeDecoder;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public String getStateTimeDecoder() {
		return stateTimeDecoder;
	}

	public void setStateTimeDecoder(String stateTimeDecoder) {
		this.stateTimeDecoder = stateTimeDecoder;
	}

	public Date getStateTime() {
		return stateTime;
	}

	public void setStateTime(Date stateTime) {
		this.stateTime = stateTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getRoutingName() {
		return routingName;
	}

	public void setRoutingName(String routingName) {
		this.routingName = routingName;
	}

	public String getProductionLineName() {
		return productionLineName;
	}

	public void setProductionLineName(String productionLineName) {
		this.productionLineName = productionLineName;
	}

	public String getWorkCenterName() {
		return workCenterName;
	}

	public void setWorkCenterName(String workCenterName) {
		this.workCenterName = workCenterName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(String objectStatus) {
		this.objectStatus = objectStatus;
	}
}
