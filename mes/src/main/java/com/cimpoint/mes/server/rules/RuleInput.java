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
package com.cimpoint.mes.server.rules;

import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.RoutingService;

public class RuleInput {
	private Object object;
	private String routingName;
	private EStep step;
	private MESTrxInfo transactionInfo;
	private RoutingService routingService;
		
	public RuleInput() {}
	
	public RuleInput(Object object, String routingName, EStep atStep, RoutingService routingService, MESTrxInfo trxInfo) {
		this.object = object;
		this.setRoutingName(routingName);
		this.step = atStep;
		this.transactionInfo = trxInfo;
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public EStep getStep() {
		return step;
	}

	public void setStep(EStep step) {
		this.step = step;
	}

	public MESTrxInfo getTransitionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(MESTrxInfo transitionInfo) {
		this.transactionInfo = transitionInfo;
	}

	public RoutingService getRoutingService() {
		return routingService;
	}

	public void setRoutingService(RoutingService routingService) {
		this.routingService = routingService;
	}

	public String getRoutingName() {
		return routingName;
	}

	public void setRoutingName(String routingName) {
		this.routingName = routingName;
	}
}
