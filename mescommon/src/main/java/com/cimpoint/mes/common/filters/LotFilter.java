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

public class LotFilter extends ObjectFilter {
	private static final long serialVersionUID = 6563864186489550881L;

	public static enum Attribute {
		Key, Name, Priority, PartNumber, Status, State
	};

	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereNumber() {
		return super.where("number");
	}

	public SearchConstraint whereOriginalNumber() {
		return super.where("originalNumber");
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

	public SearchConstraint whereBatchName() {
		return super.where("processBatch.name");
	}
	
	public SearchConstraint whereMaterialName() {
		return super.where("materialName");
	}
	
//	public SortConstraint orderById() {
//		return super.orderBy("id");
//	}
//
//	public SortConstraint orderByName() {
//		return super.orderBy("name");
//	}
	
	public LotFilter AND() {
		this.newAND();
		return this;
	}

	public LotFilter OR() {
		this.newOR();
		return this;
	}
}
