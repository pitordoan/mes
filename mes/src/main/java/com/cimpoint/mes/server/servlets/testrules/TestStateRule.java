package com.cimpoint.mes.server.servlets.testrules;

import com.cimpoint.mes.server.rules.StateInput;
import com.cimpoint.mes.server.rules.StateRule;

public class TestStateRule implements StateRule {

	public String getEntryState() {
		return "Blending";
	}

	public String getExitState() {
		return "Shipping";
	}

	public String getState(StateInput event) throws Exception {
		if (event.getStepName().equals("stepB")) {
			return "Mixing";
		}
		else if (event.getStepName().equals("stepD")) {
			return "Pouring";
		}
		else return null;
	}

}
