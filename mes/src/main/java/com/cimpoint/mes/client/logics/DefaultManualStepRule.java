package com.cimpoint.mes.client.logics;

import com.cimpoint.common.CallbackHandler;

public class DefaultManualStepRule implements StepRule {

	@Override
	public String getName() {
		return DefaultManualStepRule.class.getSimpleName();
	}
	
	@Override
	public void performAction(Object object, String stepName, String workCenterName, String stepStatusName, String optTransitionName,
			String optReason, String optComment, CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAutomatic() {
		return false;
	}

	@Override
	public void getNextObjectStatus(Object object, String stepName, String workCenterName, String stepStatusName, String optTransitionName,
			String optReason, CallbackHandler<String> callback) {
		// TODO Auto-generated method stub
		
	}
}
