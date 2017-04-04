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
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EWorkCenter;

public class WorkCenter extends ClientObject<EWorkCenter> implements Named, Persistable {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private Set<Equipment> removedEquipments;
	
	public WorkCenter(EWorkCenter e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof WorkCenter)
            return this.getName().equals(((WorkCenter) obj).getName()); 
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

	/*public void getEquipments(CallbackHandler<Set<Equipment>> callback) {
		routingController.findEquipmentsByWorkCenter(this, callback);
	}*/

	public ProductionLine getProductionLine() {
		EProductionLine pdl = entity.getProductionLine();
		return  MESTypeConverter.toClientObject(pdl, ProductionLine.class);
	}
	
	public void setProductionLine(ProductionLine productionLine) {
		if (productionLine != null) {
			entity.setProductionLine(productionLine.toEntity());
		}
	}
		
	public Area getArea() {
		return  MESTypeConverter.toClientObject(entity.getArea(), Area.class);
	}

	public void setArea(Area area) {
		if (area != null) {
			EArea earea = area.toEntity();
			entity.setArea(earea);
		}
	}

	@Override
	public void save(String comment, final CallbackHandler<Void> callback) {
		MESApplication.getMESControllers().getRoutingController().saveWorkCenter(this, comment, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				if (removedEquipments != null) {
					for (Equipment eq: removedEquipments) {
						eq.setWorkCenter(null);				
					}
					MESApplication.getMESControllers().getRoutingController().saveEquipments(removedEquipments, 
							"Removed from work center", new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							removedEquipments = null;
							callback.onSuccess(null);
						}				
					});
				}
			}			
		});
	}
	
	public void updateStep(Step step) {
		Set<Step> steps = this.getSteps();
		if (steps != null) {
			for (Step s: steps) {
				if (s.getName().equals(step.getName())) {
					s.setName(step.getName());
					s.setDescription(step.getDescription());
					this.setSteps(steps);
					break;
				}
			}
		}				
	}

	public void setSteps(Set<Step> steps) {
		if (steps != null) {
			for (Step s: steps) {
				s.addWorkCenter(this);
			}
		}
		Set<EStep> esteps = MESTypeConverter.toEntitySet(steps);
		this.entity.setSteps(esteps);
	}

	public Set<Step> getSteps() {
		return  MESTypeConverter.toClientObjectSet(entity.getSteps(), Step.class);
	}

	public void addStep(Step step) {
		Set<Step> steps = this.getSteps();
		if (steps == null) steps = new HashSet<Step>();
		steps.add(step);
		this.setSteps(steps);
	}
	
	public void updateEquipment(Equipment equipment) {
		Set<Equipment> eqs = this.getEquipments();
		if (eqs != null) {
			for (Equipment eq: eqs) {
				if (eq.getName().equals(equipment.getName())) {
					eq.setName(equipment.getName());
					eq.setDescription(equipment.getDescription());
					this.setEquipments(eqs);
					break;
				}
			}
		}				
	}

	public Set<Equipment> getEquipments() {
		return MESTypeConverter.toClientObjectSet(entity.getEquipments(), Equipment.class);
	}

	public void addEquipment(Equipment equipment) {
		Set<Equipment> eqs = this.getEquipments();
		if (eqs == null) eqs = new HashSet<Equipment>();
		eqs.add(equipment);
		this.setEquipments(eqs);
	}
	
	public void removeEquipment(Equipment equipment) {
		Set<Equipment> eqs = this.getEquipments();
		if (eqs != null && eqs.contains(equipment)) {
			eqs.remove(equipment);
			if (removedEquipments == null) removedEquipments = new HashSet<Equipment>();
			removedEquipments.add(equipment);
			this.setEquipments(eqs);
		}
	}
	
	public void setEquipments(Set<Equipment> equipments) {
		if (equipments != null) {
			for (Equipment eq: equipments) {
				eq.setWorkCenter(this);
			}
		}
		Set<EEquipment> eeqs = MESTypeConverter.toEntitySet(equipments);
		entity.setEquipments(eeqs);
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}

	public void setCustomAttributes(CustomAttributes customAttributes) {
		entity.setCustomAttributes(customAttributes);
	}
}

