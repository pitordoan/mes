package com.cimpoint.mes.client.views.designers;

public class DrawStep {
	private WorkflowStep step;
	
	public DrawStep(WorkflowStep step) {
		this.step = step;
	}
	
	protected WorkflowStep getWorkflowStep() {
		return step;
	}
	
	public void destroy() {
		step.destroy();
	}
	
	public String getName() {
		return step.getName();
	}

	public String getStepRule() {
		// TODO Auto-generated method stub
		return null;
	}
}
