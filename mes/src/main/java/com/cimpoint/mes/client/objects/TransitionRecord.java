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
 ***********************************************************************************/
package com.cimpoint.mes.client.objects;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TransitionRecord extends ListGridRecord {
	
	private Transition transition;
	
	public TransitionRecord(Transition transition) {
		setTransition(transition);
	}
	
	public Transition getTransition() {
		return transition;
	}
	
	public void setTransition(Transition transition) {
		if (transition != null) {
			try {
				this.transition = transition;
				this.setName(transition.getName());
				
				// set to Step
				Operation oper = null;
				Step step = transition.getToStep();
				if (step != null) {
					String toStep = step.getName();
					oper = step.getOperation();
					if (oper != null ) {
						toStep += ": "  + oper.getName();
					}
					
					this.setToStep(toStep);
				}
				
				// set from Step
				step = transition.getFromStep();
				if (step != null) {
					String fromStep = step.getName();
					oper = step.getOperation();
					if (oper != null) {
						fromStep += ": " + oper.getName();
					}
					this.setFromStep(fromStep);
				}
				
				this.setReasons("ReasonList1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getName() {
		return this.getAttributeAsString("name");
	}
	
	public void setName(String name) {
		this.transition.setName(name);
		setAttribute("name", name);
	}
	
	public String getToStep() {
		return this.getAttributeAsString("to");
	}
	
	public void setToStep(String toStepName) {
		setAttribute("to", toStepName);
	}
	
	public String getFromStep() {
		return this.getAttributeAsString("from");
	}
	
	public void setFromStep(String fromStepName) {
		setAttribute("from", fromStepName);
	}
	
	public String getReasons() {
		return this.getAttributeAsString("reasons");
	}
	
	public void setReasons(String reason) {
		setAttribute("reasons", reason);
	}
}
