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
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ESite;

public class Area extends ClientObject<EArea> implements Named, Persistable {
	private Set<ProductionLine> removedProductionLines;
	
	public Area(EArea e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Area)
            return this.getName().equals(((Area) obj).getName()); 
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

	public Site getSite() {
		ESite esite = entity.getSite();
		return  MESTypeConverter.toClientObject(esite, Site.class);
	}
	
	public void setSite(Site site) {
		ESite esite = (site != null)? site.toEntity() : null;
		entity.setSite(esite);
	}

	public Set<ProductionLine> getProductionLines() {
		Set<EProductionLine> pdls = entity.getProductionLines();
		return  MESTypeConverter.toClientObjectSet(pdls, ProductionLine.class);
	}

	public void setProductionLines(Set<ProductionLine> productionLines) {
		Set<EProductionLine> pdls =  MESTypeConverter.toEntitySet(productionLines);
		entity.setProductionLines(pdls);
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
	
	@Override
	public void save(String comment, final CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveArea(this, comment, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				if (removedProductionLines != null) {
					for (ProductionLine prodLine: removedProductionLines) {
						prodLine.setArea(null);				
					}
					MESApplication.getMESControllers().getRoutingController().saveProductionLines(removedProductionLines, 
							"Removed from area", new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							removedProductionLines = null;
							callback.onSuccess(null);
						}				
					});
				}
			}			
		});
	}
	
	public void updateProductionLine(ProductionLine productionLine) {
		Set<ProductionLine> pdls = this.getProductionLines();
		if (pdls != null) {
			for (ProductionLine pdl: pdls) {
				if (pdl.getName().equals(productionLine.getName())) {
					pdl.setName(productionLine.getName());
					pdl.setDescription(productionLine.getDescription());
					this.setProductionLines(pdls);
					break;
				}
			}
		}				
	}

	public void addProductionLine(ProductionLine productionLine) {
		Set<ProductionLine> pdls = this.getProductionLines();
		if (pdls == null) pdls = new HashSet<ProductionLine>();
		pdls.add(productionLine);
		this.setProductionLines(pdls);
	}
	
	public void removeProductionLine(ProductionLine productionLine) {
		Set<ProductionLine> productionLines = this.getProductionLines();
		if (productionLines != null && productionLines.contains(productionLine)) {
			productionLines.remove(productionLine);
			if (removedProductionLines == null) removedProductionLines = new HashSet<ProductionLine>();
			removedProductionLines.add(productionLine);
			this.setProductionLines(productionLines);
		}
	}
}
