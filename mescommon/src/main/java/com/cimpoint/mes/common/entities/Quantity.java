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
package com.cimpoint.mes.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cimpoint.mes.common.MESConstants;

public class Quantity implements Serializable {
	private static final long serialVersionUID = 570134839028561225L;
	
	private String qty = "";
	private MESConstants.Object.UnitOfMeasure unitOfMeasure = MESConstants.Object.UnitOfMeasure.NA;
	
	public Quantity() {}
	
	public Quantity(Object quantity, MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		if (quantity != null) {
			this.qty = String.valueOf(quantity); 
			this.unitOfMeasure = unitOfMeasure;
		}
	}
	
	public Quantity(BigDecimal value, MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		qty = value.toPlainString();	
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public Double asDouble() {
		return Double.parseDouble(qty);
	}
	
	public Integer asInteger() {
		return Integer.parseInt(qty);
	}
	
	public MESConstants.Object.UnitOfMeasure getUnitOfMeasure() {
		return this.unitOfMeasure;
	}
	
	public void setUnitOfMeasure(MESConstants.Object.UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public String asString() {
		return qty;
	}
	
	public Quantity add(Double value) {
		Double newValue = this.asDouble() + value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity add(Integer value) {
		Integer newValue = this.asInteger() + value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity subtract(Double value) {
		Double newValue = this.asDouble() - value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity subtract(Integer value) {
		Integer newValue = this.asInteger() - value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity multiply(Double value) {
		Double newValue = this.asDouble() * value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity multiply(Integer value) {
		Integer newValue = this.asInteger() * value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity divide(Double value) throws Exception {
		if (value == null) throw new Exception("Divide by zero");
		Double newValue = this.asDouble() / value;
		this.qty = String.valueOf(newValue);
		return this;
	}
	
	public Quantity divide(Integer value)  throws Exception {
		if (value == null) throw new Exception("Divide by zero");
		Integer newValue = this.asInteger() / value;
		this.qty = String.valueOf(newValue);
		return this;
	}
}
