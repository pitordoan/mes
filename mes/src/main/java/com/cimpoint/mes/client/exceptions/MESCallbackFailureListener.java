package com.cimpoint.mes.client.exceptions;

import com.cimpoint.common.exceptions.CallbackFailureListener;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.MESConfigurations;

public class MESCallbackFailureListener implements CallbackFailureListener {

	@Override
	public void onFailure(Throwable caught) {
		caught.printStackTrace();

		if (MESConfigurations.getRunMode() == MESConfigurations.RunMode.Development) {
			MESApplication.showError(caught.getMessage());
		}
	}

}
