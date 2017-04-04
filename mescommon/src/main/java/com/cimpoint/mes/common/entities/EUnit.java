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

import org.springframework.beans.factory.annotation.Configurable;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;
import com.cimpoint.mes.common.MESConstants;

@Configurable(dependencyCheck = true)
@Entity
@Table(name="Unit")
public class EUnit implements Serializable, EntityTransactional {	
	private static final long serialVersionUID = -7221707313950159152L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="SerialNumber")
	private String serialNumber;
	
	@Column(name="WorkOrderNumber")
	private String workOrderNumber;
	
	@Column(name="WorkOrderIteNumber")
	private String workOrderItemNumber;
	
	@ManyToOne()
	@JoinColumn(name="LotId")
	private ELot lot;
	
	@ManyToOne
	@JoinColumn(name="ParentUnitId")
	private EUnit parentUnit;
	
	@ManyToOne
	@JoinColumn(name="ContainerId")
	private EContainer container;
	
	@OneToMany(mappedBy = "parentUnit", targetEntity = EUnit.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<EUnit> childUnits;
	
	@Column(name="LockVersion")
	private Long lockVersion;
		
	@Column(name="PartNumber")
	private String partNumber;
	
	@Column(name="PartRevision")
	private String partRevision;
	
	@Column(name="LocationId")
	private Long locationId;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="State")
	private String state;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Type")
	@Enumerated(EnumType.STRING)
	private MESConstants.Object.UnitType type;
	
	@Embedded
	private CustomAttributes customAttributes;
		
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETrxAttributes trxAttributes;
				
	public EUnit() {}
	
	
	public EUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision) throws Exception {
		this.setSerialNumber(unitSerialNumber);
		this.setWorkOrderNumber(workOrderNumber);
		this.setPartNumber(partNumber);
		this.setPartRevision(partRevision);
		this.setType(MESConstants.Object.UnitType.StandAlone);
	}
	
	public EUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot parentLot) throws Exception {
		this.setSerialNumber(unitSerialNumber);
		this.setWorkOrderNumber(workOrderNumber);
		this.setPartNumber(partNumber);
		this.setPartRevision(partRevision);
		this.setLot(parentLot);		
		this.setType(MESConstants.Object.UnitType.ContainedInLot);
	}

	public EUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, EUnit parentUnit) throws Exception {
		this.setSerialNumber(unitSerialNumber);
		this.setWorkOrderNumber(workOrderNumber);
		this.setPartNumber(partNumber);
		this.setPartRevision(partRevision);
		this.setParentUnit(parentUnit);		
		this.setType(MESConstants.Object.UnitType.ContainedInUnit);
	}
	
	public EUnit(String unitSerialNumber, String workOrderNumber, String partNumber, String partRevision, ELot parentLot, EUnit parentUnit) throws Exception {
		this.setSerialNumber(unitSerialNumber);
		this.setWorkOrderNumber(workOrderNumber);
		this.setPartNumber(partNumber);
		this.setPartRevision(partRevision);
		this.setLot(parentLot);	
		this.setParentUnit(parentUnit);
		this.setType(MESConstants.Object.UnitType.ContainedInLotAndUnit);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
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

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ELot getLot() {
		return lot;
	}

	public void setLot(ELot lot) {
		this.lot = lot;
	}

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public EUnit getParentUnit() {
		return parentUnit;
	}

	public void setParentUnit(EUnit parentUnit) {
		this.parentUnit = parentUnit;
	}

	public Set<EUnit> getChildUnits() {
		return childUnits;
	}

	public void setChildUnits(Set<EUnit> childUnits) {
		this.childUnits = childUnits;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public MESConstants.Object.UnitType getType() {
		return type;
	}

	public void setType(MESConstants.Object.UnitType type) {
		this.type = type;
	}

	public EContainer getContainer() {
		return container;
	}

	public void setContainer(EContainer container) {
		this.container = container;
	}
	
	public String getWorkOrderItemNumber() {
		return workOrderItemNumber;
	}

	public void setWorkOrderItemNumber(String workOrderItemNumber) {
		this.workOrderItemNumber = workOrderItemNumber;
	}

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	public ETrxAttributes getTrxAttributes() {
		return trxAttributes;
	}

	public void setTrxAttributes(ETrxAttributes trxAttributes) {
		this.trxAttributes = trxAttributes;
	}

	/*@Override
	public ETransitionAttributes getTransitionAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransitionAttributes(
			ETransitionAttributes transitionAttributes) {
		// TODO Auto-generated method stub
		
	}
*/
	@Override
	public ETrxAttributes getAtrxAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTypeHierarchy() {
		String hierarchy = "";
		if (this.workOrderNumber != null && !this.workOrderNumber.isEmpty()) {
			hierarchy = "WorkOrder";
		}
		
		if (this.workOrderItemNumber != null && !this.workOrderItemNumber.isEmpty()) {
			hierarchy += "\\WorkOrderItem";
		}
		
		if (this.parentUnit != null) {
			StringBuffer buffer = new StringBuffer();
			getParentUnitsHierarchy(this.parentUnit, buffer);
			hierarchy += buffer.toString();
		}
		hierarchy += "\\[Unit]";
		
		if (this.childUnits != null && this.childUnits.size() > 0) {
			hierarchy += "\\Unit(" + this.childUnits.size() + ")";
		}
		
		//TODO get grand children units as well
		
		return hierarchy;
	}
	
	private void getParentUnitsHierarchy(EUnit unit, StringBuffer hierarchy) {
    	if (unit != null) {
    		hierarchy.append("\\Container");
    		EUnit parentUnit = unit.getParentUnit();
    		if (parentUnit != null) {
    			getParentUnitsHierarchy(parentUnit, hierarchy);
    		}
    	}
    }
}

