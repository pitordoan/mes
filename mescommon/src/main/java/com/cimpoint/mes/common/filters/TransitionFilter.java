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

public class TransitionFilter extends ObjectFilter {

	private static final long serialVersionUID = 1L;
	
	public TransitionFilter() {}
	
	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint whereRouting() {
		return super.where("routing");
	}
	
	public SearchConstraint whereRoutingName() {
		return super.where("routing.name");
	}
	
	public SearchConstraint whereFromStep() {
		return super.where("fromStep");
	}
	
	public SearchConstraint whereFromStepName() {
		return super.where("fromStep.name");
	}
	
	public SearchConstraint whereToStep() {
		return super.where("toStep");
	}
	
	public SearchConstraint whereToStepName() {
		return super.where("toStep.name");
	}

	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public TransitionFilter AND() {
		this.newAND();
		return this;
	}

	public TransitionFilter OR() {
		this.newOR();
		return this;
	}

}
