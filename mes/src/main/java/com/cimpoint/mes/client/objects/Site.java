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
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.ESite;

public class Site extends ClientObject<ESite> implements Named, Persistable {
	private Set<Area> removedAreas;
	
	public Site(ESite entity) {
		this.entity = entity;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Site)
            return this.getName().equals(((Site) obj).getName()); 
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
	
	public Set<Area> getAreas() {
		Set<EArea> eareas = entity.getAreas();
		return  MESTypeConverter.toClientObjectSet(eareas, Area.class);
	}

	public void setAreas(Set<Area> areas) {
		if (areas == null) {
			entity.setAreas(null);
			return;
		}
		
		for (Area a: areas) {
			a.setSite(this);
		}		
		Set<EArea> eareas =  MESTypeConverter.toEntitySet(areas);
		entity.setAreas(eareas);
	}
	
	public void addArea(Area area) {
		Set<Area> areas = this.getAreas();
		if (areas == null) areas = new HashSet<Area>();
		if (areas.contains(area)) {
			areas.remove(area);
		}
		areas.add(area);
		this.setAreas(areas);
	}
	
	public void removeArea(Area area) {
		Set<Area> areas = this.getAreas();
		if (areas != null && areas.contains(area)) {
			areas.remove(area);
			if (removedAreas == null) removedAreas = new HashSet<Area>();
			removedAreas.add(area);
			this.setAreas(areas);
		}
	}
	
	public void updateArea(Area area) {
		Set<Area> areas = this.getAreas();
		if (areas != null) {
			for (Area a: areas) {
				if (a.getName().equals(area.getName())) {
					a.setName(area.getName());
					a.setDescription(area.getDescription());
					this.setAreas(areas);
					break;
				}
			}
		}				
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
	
	@Override
	public void save(String comment, final CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveSite(this, comment, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				if (removedAreas != null) {
					for (Area area: removedAreas) {
						area.setSite(null);				
					}
					MESApplication.getMESControllers().getRoutingController().saveAreas(removedAreas, "Removed from site", new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							removedAreas = null;
							callback.onSuccess(null);
						}				
					});
				}
			}			
		});
	}
}
