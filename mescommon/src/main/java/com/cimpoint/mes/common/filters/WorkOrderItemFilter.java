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

public class WorkOrderItemFilter extends ObjectFilter {

	private static final long serialVersionUID = 1L;
	
	public WorkOrderItemFilter() {}
	
	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint wherePartNumber() {
		return super.where("partNumber");
	}
	
	public SearchConstraint wherePartRevision() {
		return super.where("partRevision");
	}
	
	public SearchConstraint whereWorkOrder() {
		return super.where("workOrder");
	}

	public SearchConstraint whereWorkOrderNumber() {
		return super.where("workOrder.number");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public SortConstraint orderByParNumber() {
		return super.orderBy("partNumber");
	}

	public SortConstraint orderByPartRevision() {
		return super.orderBy("partRevision");
	}
	
	public WorkOrderItemFilter AND() {
		this.newAND();
		return this;
	}

	public WorkOrderItemFilter OR() {
		this.newOR();
		return this;
	}

}
