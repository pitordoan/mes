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

public class AreaFilter extends ObjectFilter implements Serializable {
	private static final long serialVersionUID = -5638177965730127496L;

	public AreaFilter() {
	}

	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SearchConstraint whereSiteName() {
		return super.where("site.name");
	}

	public SearchConstraint whereSite() {
		return super.where("site");
	}
	
	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public AreaFilter AND() {
		this.newAND();
		return this;
	}

	public AreaFilter OR() {
		this.newOR();
		return this;
	}
}
