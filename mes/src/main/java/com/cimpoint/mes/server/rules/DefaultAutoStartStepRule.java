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

import com.cimpoint.mes.common.entities.ETransition;

public class DefaultAutoStartStepRule implements StepRule {
	
	public String getName() {
		return this.getClass().getName();
	}
	
	public void onPreStart(RuleInput input) throws Exception {		
	}

	public void onPostStart(RuleInput input) throws Exception {
	}

	public void onPreComplete(RuleInput input) throws Exception {
	}

	public void onPostComplete(RuleInput input) throws Exception {
	}

	public ETransition[] getAutoCompleteTransitions(RuleInput input) throws Exception {
		return null;
	}

	public AutoAction getAutoAction() {
		return StepRule.AutoAction.Start;
	}
}
