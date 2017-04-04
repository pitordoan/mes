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
@Table(name="TransitionAttributes")
public class ETransitionAttributes implements Serializable {
	private static final long serialVersionUID = -7602463280519897678L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="ObjectId", nullable=false)
	private Long objectId;
		
	@Column(name="ObjectType", nullable=false)
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.Type objectType;
	
	@Column(name="ObjectStatus")
	private String objectStatus;
		
	@Column(name="StepStatus")
	private String stepStatus;
	
	@Deprecated
	@Column(name="State")
	private String state;
	
	@Column(name="StateRuleClassName")
	private String stateRuleClassName;
			
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
	
	//[UTC timezone]-[UTC time]:[client timezone]-[client trx time]
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
	
	@Column(name="StepFlowEnforcement")
	private Boolean stepFlowEnforcement;
			
	public ETransitionAttributes() {}
	
	public ETransitionAttributes(Long trackedObjectId, MESConstants.Object.Type trackedObjectType) {
		this.setObjectId(trackedObjectId);
		this.setObjectType(trackedObjectType);
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

	public Boolean getStepFlowEnforcement() {
		return stepFlowEnforcement;
	}

	public void setStepFlowEnforcement(Boolean stepFlowEnforcement) {
		this.stepFlowEnforcement = stepFlowEnforcement;
	}
}
