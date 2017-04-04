/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation 
 *     ***********************************************************************************/
package com.cimpoint.mes.common.filters;

import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class BatchFilter extends ObjectFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BatchFilter() {}
	
	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint whereWorkOrderNumber() {
		return super.where("workOrderNumber");
	}
	
	public SearchConstraint whereWorkOrderItemNumber() {
		return super.where("workOrderItemNumber");
	}

	public SearchConstraint whereContainerNumber() {
		return super.where("container.number");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public BatchFilter AND() {
		this.newAND();
		return this;
	}

	public BatchFilter OR() {
		this.newOR();
		return this;
	}

}
