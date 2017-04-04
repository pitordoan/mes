package com.cimpoint.mes.client.logics;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.TransitionAttributes;

public class DefaultAutoStepRule implements StepRule {

	@Override
	public String getName() {
		return DefaultAutoStepRule.class.getSimpleName();
	}
	
	@Override
	public void performAction(final Object object, final String stepName, final String workCenterName, final String stepStatusName, final String optTransitionName,
			final String optReason, final String optComment, final CallbackHandler<Void> callback) {
		this.getNextObjectStatus(object, stepName, workCenterName, stepStatusName, optTransitionName, optReason, new CallbackHandler<String>() {
			@Override
			public void onSuccess(String newStatus) {
				MESApplication.getMESControllers().getRoutingController().transact(object, stepName, workCenterName, 
						stepStatusName, newStatus, optTransitionName, optReason, optComment, callback);
			}				
		});
	}

	@Override
	public boolean isAutomatic() {
		return true;
	}

	@Override
	public void getNextObjectStatus(final Object object, final String stepName, final String workCenterName, final String stepStatusName, 
			final String optTransitionName, final String optReason, final CallbackHandler<String> callback) {
		MESApplication.getMESControllers().getRoutingController().findTransitionAttributes(object, new CallbackHandler<TransitionAttributes>() {
			@Override
			public void onSuccess(TransitionAttributes attrs) {
				callback.onSuccess(attrs.getObjectStatus()); //no object status change throughout its routing
			}				
		});
	}
}
