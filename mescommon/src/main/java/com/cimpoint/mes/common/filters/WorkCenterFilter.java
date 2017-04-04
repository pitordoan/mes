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

import java.io.Serializable;
import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class WorkCenterFilter extends ObjectFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public WorkCenterFilter() {}

	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint whereArea() {
		return super.where("area");
	}

	public SearchConstraint whereProductionLine() {
		return super.where("productionLine");
	}	
	
	public SearchConstraint whereProductionLineName() {
		return super.where("productionLine.name");
	}
	
	public SearchConstraint whereStep() {
		return super.where("steps");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public WorkCenterFilter AND() {
		this.newAND();
		return this;
	}

	public WorkCenterFilter OR() {
		this.newOR();
		return this;
	}
}
