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


public class Location implements Serializable {
	private static final long serialVersionUID = 7189092898872957200L;
	
	private Long plantId;
	private String plantName;
	private Long siteId;
	private String siteName;
	private Long areaId;
	private String areaName;
	private Long workCenterId;
	private String workCenterName;

	public Location() {}
	
	public Location(ESite site, EArea area, EWorkCenter workCenter) {
		this.siteId = site.getId();
		this.siteName = site.getName();
		this.areaId = area.getId();
		this.areaName = area.getName();
		this.workCenterId = workCenter.getId();
		this.workCenterName = workCenter.getName();
	}
	
	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getWorkCenterId() {
		return workCenterId;
	}

	public void setWorkCenterId(Long workCenterId) {
		this.workCenterId = workCenterId;
	}

	public String getWorkCenterName() {
		return workCenterName;
	}

	public void setWorkCenterName(String workCenterName) {
		this.workCenterName = workCenterName;
	}
}
