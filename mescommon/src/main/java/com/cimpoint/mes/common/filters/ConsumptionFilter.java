/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Chung Khanh Duy - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.common.filters;

import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class ConsumptionFilter extends ObjectFilter {
	
	private static final long serialVersionUID = 1L;
	
	public ConsumptionFilter() {}
	
	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereMaterialType() {
		return super.where("materialType");
	}

	public SearchConstraint whereMaterialId() {
		return super.where("materialId");
	}

	public SearchConstraint whereObjecType() {
		return super.where("objectType");
	}

	public SearchConstraint whereRoutingName() {
		return super.where("routingName");
	}
	
	public SearchConstraint whereStepName() {
		return super.where("stepName");
	}
	
	public SearchConstraint whereTravellerWorkOrderNumber() {
		return super.where("traveler.workOrderNumber");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public ConsumptionFilter AND() {
		this.newAND();
		return this;
	}

	public ConsumptionFilter OR() {
		this.newOR();
		return this;
	}

}
