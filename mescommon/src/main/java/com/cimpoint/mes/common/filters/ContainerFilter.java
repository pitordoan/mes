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

import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class ContainerFilter implements Serializable {
	private static final long serialVersionUID = 2725751657281474066L;

	public static enum Attribute {
		Key, Name, Priority, PartNumber, Status, State
	};

	public SearchConstraint whereId() {
		return null; // TODO add impl //TODO add impl

	}

	public SearchConstraint whereName() {
		return null; // TODO add impl //TODO add impl

	}

	public SearchConstraint wherePriority() {
		return null; // TODO add impl //TODO add impl

	}

	public SearchConstraint wherePartNumber() {
		return null; // TODO add impl //TODO add impl

	}

	public SearchConstraint whereStatus() {
		return null; // TODO add impl //TODO add impl

	}

	public SearchConstraint whereState() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByKey() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByName() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByPriority() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByPartNumber() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByStatus() {
		return null; // TODO add impl //TODO add impl

	}

	public SortConstraint orderByState() {
		return null; // TODO add impl //TODO add impl

	}

	public ContainerFilter or() {
		return null; // TODO add impl //TODO add impl

	}
}
