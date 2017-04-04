package com.cimpoint.mes.client.objects;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Named;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;

public class Bom extends ClientObject<EBom> implements Named, Persistable {

	public Bom(EBom e) {
		this.entity = e;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Bom)
            return this.getName().equals(((Bom) obj).getName()); 
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
		
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public Long getId() {
		return entity.getId();
	}

	@Override
	public String getName() {
		return entity.getName();
	}

	@Override
	public void setName(String name) {
		entity.setName(name);
	}

	public String getDescription() {
		return entity.getDescription();
	}
	
	public void setDescription(String description) {
		entity.setDescription(description);
	}
	
	public String getRevision() {
		return entity.getRevision();
	}
	
	public void setRevision(String revision) {
		entity.setRevision(revision);
	}
	
	public String getStartDate() {
		return entity.getStartDate();
	}
	
	public void setStartDate(String startDate) {
		this.entity.setStartDate(startDate);
	}
	
	public String getEndDate() {
		return entity.getEndDate();
	}
	
	public void setEndDate(String endDate) {
		entity.setEndDate(endDate);
	}
	
	public Set<BomItem> getBomItems() {
		Set<EBomItem> bomItems = entity.getBomItems();
		return MESTypeConverter.toClientObjectSet(bomItems, BomItem.class);
	}
	
	public void setBomItems(Set<BomItem> bomItems) {
		for (BomItem item: bomItems) {
			item.setBom(this);
		}		
		Set<EBomItem> bomItemList =  MESTypeConverter.toEntitySet(bomItems);
		entity.setBomItems(bomItemList);
	}
}
