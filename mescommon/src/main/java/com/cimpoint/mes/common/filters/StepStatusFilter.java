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

import java.io.Serializable;

import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class StepStatusFilter extends ObjectFilter implements Serializable {
	private static final long serialVersionUID = -1441464099198485822L;

	public StepStatusFilter() {
	}

	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint whereStepName() {
		return super.where("step.name");
	}
	
	public SearchConstraint whereWorkCenterName() {
		return super.where("workCenter.name");
	}
	
	public SearchConstraint whereRoutingName() {
		return super.where("routing.name");
	}

	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public StepStatusFilter AND() {
		this.newAND();
		return this;
	}

	public StepStatusFilter OR() {
		this.newOR();
		return this;
	}
}
