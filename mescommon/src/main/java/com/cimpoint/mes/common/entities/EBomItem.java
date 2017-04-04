/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BomItem")
public class EBomItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="ContainterPartId")
	private Long containerPartId;
	
	@Column(name="ContainerRevision", length=30, nullable=false)
	private String containerRevision;
	
	@Column(name="ContainerName", length=30, nullable=false)
	private String containerName;
	
	@Column(name="PartId")
	private Long partId;
	
	@Column(name="PartRevision", length=30, nullable=false)
	private String partRevision;

	@Column(name="PartName", length=30, nullable=false)
	private String partName;
	
	@ManyToOne 
	@JoinColumn(name="BomId")
	private EBom bom;
	
	public EBomItem() {
		
	}
	
	public EBomItem(Long containerPartId, String containerRevision, String containerName, Long partId, String partRevision, String partName) {
		this.containerPartId = containerPartId;
		this.partId = partId;
		this.partRevision = partRevision;
		this.containerRevision = containerRevision;
		this.partName = partName;
		this.containerName = containerName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getPartRevision() {
		return partRevision;
	}

	public void setPartRevision(String partRevision) {
		this.partRevision = partRevision;
	}

	public EBom getBom() {
		return bom;
	}

	public void setBom(EBom bom) {
		this.bom = bom;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getContainerRevision() {
		return containerRevision;
	}

	public void setContainerRevision(String containerRevision) {
		this.containerRevision = containerRevision;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	
}
