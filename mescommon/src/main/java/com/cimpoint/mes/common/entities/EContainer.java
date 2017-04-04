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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Configurable;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;

@Configurable(dependencyCheck = true)
@Entity
@Table(name="Container")
public class EContainer implements Serializable, EntityTransactional {	
	private static final long serialVersionUID = -1871279531034890566L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Number", length=20, nullable=false, unique=true)
	private String number;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="WorkOrderNumber")
	private String workOrderNumber;
	
	@Column(name="WorkOrderIteNumber")
	private String workOrderItemNumber;
	
	@OneToMany(mappedBy = "container", targetEntity = EBatch.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private Set<EBatch> batches;
		
	@OneToMany(mappedBy = "container", targetEntity = ELot.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private Set<ELot> lots;
	
	@OneToMany(mappedBy = "container", targetEntity = EUnit.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private Set<EUnit> units;
	
	@Transient
	private EContainer parentContainer;
	
	@Transient
	private Set<EContainer> childContainers;
			
	@Column(name="LockVersion")
	private Long lockVersion;
	
	@Column(name="Quantity")
	private String quantity;
	
	@Column(name="LocationId")
	private Long locationId;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="State")
	private String state;
		
	@Embedded
	private CustomAttributes customAttributes;
	
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETrxAttributes trxAttributes;
		
	/*@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ETransitionAttributes transitionAttributes;*/
		
	public EContainer() {}
	
	public EContainer(String number, String category) throws Exception {
		this.setNumber(number);
		this.setCategory(category);		
	}
	
	public EContainer(String number, String category, Set<EBatch> batches, Set<ELot> lots, Set<EUnit> units) throws Exception {
		this.setNumber(number);
		this.setCategory(category);		
		this.setBatches(batches);
		this.setLots(lots);
		this.setUnits(units);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public Set<EUnit> getUnits() {
		return units;
	}

	public void setUnits(Set<EUnit> units) {
		this.units = units;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public EContainer getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(EContainer parentContainer) {
		this.parentContainer = parentContainer;
	}

	public Set<EContainer> getChildContainers() {
		return childContainers;
	}

	public void setChildContainers(Set<EContainer> childContainers) {
		this.childContainers = childContainers;
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

	public CustomAttributes getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.customAttributes = customAttributes;
	}

	@Override
	public void setTrxAttributes(ETrxAttributes trxAttributes) {
		this.trxAttributes = trxAttributes;
	}

	@Override
	public ETrxAttributes getAtrxAttributes() {
		return this.trxAttributes;
	}

	/*@Override
	public ETransitionAttributes getTransitionAttributes() {
		return transitionAttributes;
	}

	@Override
	public void setTransitionAttributes(ETransitionAttributes transitionAttributes) {
		this.transitionAttributes = transitionAttributes;
	}*/

	public boolean equals(Object obj) {  
		if (obj instanceof EContainer && obj != null) {
			EContainer e = (EContainer) obj;
			if (e.getNumber().equalsIgnoreCase(this.getNumber())) {
				return true;
			}
		}
	    return false;
    }  
     
    public int hashCode() {  
        return this.number.hashCode();
    }

    public String getTypeHierarchy() {
		String hierarchy = "";
		if (this.workOrderNumber != null && !this.workOrderNumber.isEmpty()) {
			hierarchy = "WorkOrder";
		}
		
		if (this.workOrderItemNumber != null && !this.workOrderItemNumber.isEmpty()) {
			hierarchy += "\\WorkOrderItem";
		}
		
		if (this.parentContainer != null) {
			StringBuffer buffer = new StringBuffer();
			getParentContainersHierarchy(this.parentContainer, buffer);
			hierarchy += buffer.toString();
		}
		hierarchy += "\\[Container]";
		
		if (this.childContainers != null && this.childContainers.size() > 0) {
			hierarchy += "\\Container(" + this.childContainers.size() + ")";
		}
		
		//TODO get grand children containers as well
		
		return hierarchy;
	}
    
    private void getParentContainersHierarchy(EContainer container, StringBuffer hierarchy) {
    	if (container != null) {
    		hierarchy.append("\\Container");
    		EContainer parentContainer = container.getParentContainer();
    		if (parentContainer != null) {
    			getParentContainersHierarchy(parentContainer, hierarchy);
    		}
    	}
    }
}

