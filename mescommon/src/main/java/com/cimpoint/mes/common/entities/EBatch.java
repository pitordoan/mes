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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.entities.ETrxAttributes;
import com.cimpoint.common.entities.EntityTransactional;

@Entity
@Table(name="Batch")
public class EBatch implements Serializable, EntityTransactional {
	private static final long serialVersionUID = -5418612491013215269L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=50, nullable=false, unique=true)
	private String name;
	
	@Version
	@Column(name="LockVersion")
	private Long lockVersion;
		
	@Column(name="WorkOrderNumber", length=20)
	private String workOrderNumber;
	
	@Column(name="WorkOrderIteNumber", length=20)
	private String workOrderItemNumber;
	
	@ManyToOne
	@JoinColumn(name="ContainerId")
	private EContainer container;
	
	@OneToMany(mappedBy = "processBatch", targetEntity = ELot.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ELot> lots;
		
	@Embedded
	private CustomAttributes customAttributes;
		
	/*@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private TransitionAttributes trxAttributes;*/
		
	public EBatch() {}
	
	public EBatch(String name) {
		this.setName(name);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ELot> getLots() {
		return lots;
	}

	public void setLots(Set<ELot> lots) {
		this.lots = lots;
	}

	public EContainer getContainer() {
		return container;
	}

	public void setContainer(EContainer container) {
		this.container = container;
	}
		
	public Long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public ETrxAttributes getAtrxAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	/*public TransitionAttributes getTrxAttributes() {
		return trxAttributes;
	}

	public void setTrxAttributes(TransitionAttributes trxAttributes) {
		this.trxAttributes = trxAttributes;
	}*/

	public boolean equals(Object obj) {  
		if (obj instanceof EBatch && obj != null) {
			EBatch e = (EBatch) obj;
			if (e.getName().equalsIgnoreCase(this.getName())) {
				return true;
			}
		}
	    return false;
    }  
     
    public int hashCode() {  
        return this.name.hashCode();
    }

    public String getTypeHierarchy() {
		String hierarchy = "";
		if (this.workOrderNumber != null && !this.workOrderNumber.isEmpty()) {
			hierarchy = "WorkOrder";
		}
		
		if (this.workOrderItemNumber != null && !this.workOrderItemNumber.isEmpty()) {
			hierarchy += "\\WorkOrderItem";
		}
		
		hierarchy += "\\Batch";
		
		return hierarchy;
	}

	/*@Override
	public ETransitionAttributes getTransitionAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransitionAttributes(ETransitionAttributes transitionAttributes) {
		// TODO Auto-generated method stub
		
	} */
}
