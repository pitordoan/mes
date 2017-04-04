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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;

public class Routing extends ClientObject<ERouting> implements Named, Persistable {

	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public Routing(ERouting e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Routing)
            return this.getName().equals(((Routing) obj).getName()); 
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

	/*public String getRevision() {
		return entity.getRevision();
	}
	
	public void setRevision(String revision) {
		entity.setRevision(revision);
	}*/
	
	public Step getEntryStep() {
		Set<EStep> esteps = entity.getSteps();
		if (esteps != null) {
			for (EStep s: esteps) {
				if (s.getType() == StepType.Start) {
					return MESTypeConverter.toClientObject(s, Step.class);
				}
			}
		}
		throw new RuntimeException("Entry step not found");
	}
	
	public void setEntryStep(Step entryStep) {
		this.entity.setStartStepId(entryStep.getId());
	}

	public Step getExitStep() {
		Set<EStep> esteps = entity.getSteps();
		if (esteps != null) {
			for (EStep s: esteps) {
				if (s.getType() == StepType.End) {
					return MESTypeConverter.toClientObject(s, Step.class);
				}
			}
		}
		throw new RuntimeException("Exit step not found");
	}
	
	public void setSteps(Set<Step> steps) {
		Set<EStep> esteps = MESTypeConverter.toEntitySet(steps);
		entity.setSteps(esteps);
	}
		
	public Set<Step> getSteps() {
		Set<EStep> esteps = entity.getSteps();
		return  MESTypeConverter.toClientObjectSet(esteps, Step.class);
	}
	
	public void save(String comment, CallbackHandler<Void> callback) {
		routingController.saveRouting(this, comment, callback);
	}
	
	public CustomAttributes getCustomAttributes() {
		return entity.getCustomAttributes();
	}
	
	public void setCustomAttributes(CustomAttributes customAttributes) {
		this.entity.setCustomAttributes(customAttributes);
	}
}
