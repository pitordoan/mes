package com.cimpoint.mes.client.logics;

import com.cimpoint.common.CallbackHandler;

public interface StepRule {
	public String getName();
	
	public boolean isAutomatic();
	
	public void getNextObjectStatus(final Object object, final String stepName, final String workCenterName, final String stepStatusName, 
			final String optTransitionName, final String optReason, final CallbackHandler<String> callback);
	
	public void performAction(final Object object, final String stepName, final String workCenterName, 
			final String stepStatusName, final String optTransitionName,
			final String optReason, final String optComment, final CallbackHandler<Void> callback);
}
