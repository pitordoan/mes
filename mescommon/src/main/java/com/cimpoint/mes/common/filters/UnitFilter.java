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
package com.cimpoint.mes.common.filters;

import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class UnitFilter extends ObjectFilter {
	private static final long serialVersionUID = -5097237224771711625L;

	public static enum Attribute {
		Key, Name, Priority, PartNumber, Status, State
	};

	public UnitFilter() {};
	
	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereSerialNumber() {
		return super.where("serialNumber");
	}

	public SearchConstraint whereWorkOrderNumber() {
		return super.where("workOrderNumber");
	}

	public SearchConstraint whereLot() {
		return super.where("lot");
	}

	public SearchConstraint whereLotNumber() {
		return super.where("lot.number");
	}
	
	public SearchConstraint whereLotId() {
		return super.where("lot.id");
	}
	
	public SearchConstraint whereParentUnit() {
		return super.where("parentUnit");
	}
	
	public SearchConstraint whereContainer() {
		return super.where("container");
	}
	
	public SearchConstraint whereContainerNumber() {
		return super.where("container.number");
	}
	
	public SearchConstraint wherePartNumber() {
		return super.where("partNumber");
	}
	
	public SearchConstraint wherePartRevision() {
		return super.where("partRevision");
	}
	
	public SearchConstraint whereState() {
		return super.where("state");
	}
	
	public SearchConstraint whereStatus() {
		return super.where("status");
	}

	public SearchConstraint whereCategory() {
		return super.where("category");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByParNumber() {
		return super.orderBy("partNumber");
	}

	public SortConstraint orderByPartRevision() {
		return super.orderBy("partRevision");
	}
	
	public SortConstraint orderByStatus() {
		return super.orderBy("status");
	}
	
	public SortConstraint orderByState() {
		return super.orderBy("state");
	}

	public SortConstraint orderByCategory() {
		return super.orderBy("category");
	}

	public UnitFilter AND() {
		this.newAND();
		return this;
	}

	public UnitFilter OR() {
		this.newOR();
		return this;
	}
}
