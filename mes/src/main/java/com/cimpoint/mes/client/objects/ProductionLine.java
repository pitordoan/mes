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
package com.cimpoint.mes.client.objects;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.EWorkCenter;

public class ProductionLine extends ClientObject<EProductionLine> implements Named, Persistable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private Set<WorkCenter> removedWCs;
	
	public ProductionLine(EProductionLine e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof ProductionLine)
            return this.getName().equals(((ProductionLine) obj).getName()); 
        else
            return false;
    }
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + this.getName().hashCode();
		hash = 31 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
		return hash;
	}
	
	public Long getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getName();
	}
	
	public void setName(String name) {
		entity.setName(name);
	}

	public String getDescription() {
		return entity.getDescription();
	}
	
	public void setDescription(String description) {
		entity.setDescription(description);
	}

	public void setArea(Area area) {
		if (area != null) {
			entity.setArea(area.toEntity());
		}
		else {
			entity.setArea(null);
		}
	}

	public Area getArea() {
		EArea earea = entity.getArea();
		return  MESTypeConverter.toClientObject(earea, Area.class);
	}
	
	public Set<WorkCenter> getWorkCenters() {
		Set<EWorkCenter> ewcs = entity.getWorkCenters();
		Set<WorkCenter> wcs = MESTypeConverter.toClientObjectSet(ewcs, WorkCenter.class);
		return wcs;
	}
	
	public void setWorkCenters(Set<WorkCenter> workCenters) {
		Set<EWorkCenter> wcs =  MESTypeConverter.toEntitySet(workCenters);
		entity.setWorkCenters(wcs);
	}
		
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
	
	public void save(String comment, final CallbackHandler<Void> callback)  {
		MESApplication.getMESControllers().getRoutingController().saveProductionLine(this, comment, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				if (removedWCs != null) {
					for (WorkCenter wc: removedWCs) {
						wc.setProductionLine(null);				
					}
					MESApplication.getMESControllers().getRoutingController().saveWorkCenters(removedWCs, 
							"Removed from production line", new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							removedWCs = null;
							callback.onSuccess(null);
						}				
					});
				}
			}			
		});
	}
	
	public void updateWorkCenter(WorkCenter workCenter) {
		Set<WorkCenter> wcs = this.getWorkCenters();
		if (wcs != null) {
			for (WorkCenter wc: wcs) {
				if (wc.getName().equals(workCenter.getName())) {
					wc.setName(workCenter.getName());
					wc.setDescription(workCenter.getDescription());
					this.setWorkCenters(wcs);
					break;
				}
			}
		}				
	}

	public void addWorkCenter(WorkCenter workCenter) {
		Set<WorkCenter> wcs = this.getWorkCenters();
		if (wcs == null) wcs = new HashSet<WorkCenter>();
		wcs.add(workCenter);
		this.setWorkCenters(wcs);
	}
	
	public void removeWorkCenter(WorkCenter workCenter) {
		Set<WorkCenter> wcs = this.getWorkCenters();
		if (wcs != null && wcs.contains(workCenter)) {
			wcs.remove(workCenter);
			if (removedWCs == null) removedWCs = new HashSet<WorkCenter>();
			removedWCs.add(workCenter);
			this.setWorkCenters(wcs);
		}
	}
}
