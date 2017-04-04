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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.common.entities.ETraveler;

public class Traveler extends ClientObject<ETraveler> implements Named, Persistable {

	public Traveler(ETraveler e) {
		this.entity = e;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Traveler)
            return this.getName().equals(((Traveler) obj).getName()); 
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
	
	/*public WorkOrder getWorkOrder() throws Exception {
		String workOrderNumber = entity.getWorkOrderNumber();
		WorkOrder wo = this.mesService.getWorkOrderService().findWorkOrderByNumber(workOrderNumber);
		return wo;
	}
	
	public List<Container> getContainers() throws Exception {
		List<EContainer> ecs = entity.getContainers();
		return this.toClientObjectList(ecs, Container.class, Container.class);
	}

	public List<Batch> getBatches() throws Exception {
		List<EBatch> ebs = entity.getBatches();
		return this.toClientObjectList(ebs, Batch.class, Batch.class);
	}

	public List<Lot> getLots() throws Exception {
		List<ELot> els = entity.getLots();
		return this.toClientObjectList(els, Lot.class, Lot.class);
	}

	public List<Unit> getUnits() throws Exception {
		List<EUnit> eus = entity.getUnits();
		return this.toClientObjectList(eus, Unit.class, Unit.class);
	}
	
	public String getComment() throws Exception {
		return entity.getComment();
	}

	public void setComment(String comment) {
		entity.setComment(comment);
	}

	public CustomAttributes getCustomAttributes() throws Exception {
		return entity.getCustomAttributes();
	}

	@Override
	public List<PartConsumption> getPartConsumptions() throws Exception {
		List<Container> containers = this.getContainers();
		List<Batch> batches = this.getBatches();
		List<Lot> lots = this.getLots();
		List<Unit> units = this.getUnits();
		
		List<PartConsumption> pcs = new ArrayList<PartConsumption>();
		
		if (containers != null && containers.size() > 0) {
			for (Container c: containers) {
				List<PartConsumption> s = c.getPartConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (batches != null && batches.size() > 0) {
			for (Batch b: batches) {
				List<PartConsumption> s = b.getPartConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (lots != null && lots.size() > 0) {
			for (Lot lot: lots) {
				List<PartConsumption> s = lot.getPartConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (units != null && units.size() > 0) {
			for (Unit unit: units) {
				List<PartConsumption> s = unit.getPartConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		return pcs;
	}

	@Override
	public List<ComponentConsumption> getComponentConsumptions() throws Exception {
		List<Container> containers = this.getContainers();
		List<Batch> batches = this.getBatches();
		List<Lot> lots = this.getLots();
		List<Unit> units = this.getUnits();
		
		List<ComponentConsumption> pcs = new ArrayList<ComponentConsumption>();
		
		if (containers != null && containers.size() > 0) {
			for (Container c: containers) {
				List<ComponentConsumption> s = c.getComponentConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (batches != null && batches.size() > 0) {
			for (Batch b: batches) {
				List<ComponentConsumption> s = b.getComponentConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (lots != null && lots.size() > 0) {
			for (Lot lot: lots) {
				List<ComponentConsumption> s = lot.getComponentConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		if (units != null && units.size() > 0) {
			for (Unit unit: units) {
				List<ComponentConsumption> s = unit.getComponentConsumptions();
				if (s != null) {
					pcs.addAll(s);
				}
			}
		}
		
		return pcs;
	}*/

	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
}

