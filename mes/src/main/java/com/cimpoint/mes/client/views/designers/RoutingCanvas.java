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
package com.cimpoint.mes.client.views.designers;

import com.cimpoint.mes.client.views.designers.WorkflowCanvas.ConnectorType;
import com.cimpoint.mes.client.views.designers.WorkflowCanvas.Mode;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;

public class RoutingCanvas extends Canvas {
	private WorkflowCanvas workflowCanvas;
	private DrawStep startStep;
	private DrawStep endStep;
	private final int STEP_WIDTH = 32;
	private final int STEP_HEIGHT = 32;
	
	public RoutingCanvas(int left, int top, String width, String height) {
		workflowCanvas = new WorkflowCanvas(left, top, width, height) {
			@Override
			protected WorkflowStep newWorkflowStep() {
				int index = workflowCanvas.getNextNameIndex();
				return createWorkflowStep("step" + index, "actions/32x32/inner_step.png");
			}

			@Override
			protected Connector newConnector(WorkflowStep fromStep, WorkflowStep toStep) {
				return createConnector(fromStep, toStep);
			}			
		};
		
		this.setWidth(width);
		this.setHeight(height);
		this.setOverflow(Overflow.AUTO); 
        this.addChild(workflowCanvas);
	}

	protected DrawStep newStartStep(int left, int top) {
		if (startStep != null) { //only one is allowed
			startStep.destroy();
		}
		
		WorkflowStep step = createWorkflowStep("Entry", left, top, "actions/32x32/start_step.png");
		startStep = new DrawStep(step);		
		return startStep;		
	}
	
	protected DrawStep newEndStep(int left, int top) {
		if (endStep != null) { //only one is allowed
			endStep.destroy();
		}
		
		WorkflowStep step = createWorkflowStep("Exit", left, top, "actions/32x32/end_step.png");
		endStep = new DrawStep(step);
		return endStep;
	}
	
	protected DrawStep newInnerStep(int left, int top) {
		int i = workflowCanvas.getNextNameIndex();
		String name = "Step" + i;
		WorkflowStep step = createWorkflowStep(name, left, top, "actions/32x32/inner_step.png");
		
		return new DrawStep(step);
	}
		
	private WorkflowStep createWorkflowStep(String name, int left, int top, String imageSrc) {
		int width = STEP_WIDTH;
		int height = STEP_HEIGHT;
		WorkflowStep step = new WorkflowStep(name, imageSrc, name, width, height, workflowCanvas);
		step.setLeft(left);
		step.setTop(top);
		workflowCanvas.addWorkflowStep(step);
		
		return step;
	}
	
	private WorkflowStep createWorkflowStep(String name, String imageSrc) {
		int width = STEP_WIDTH;
		int height = STEP_HEIGHT;
		WorkflowStep step = new WorkflowStep(name, imageSrc, name, width, height, workflowCanvas);
		
		return step;
	}
	
	protected DrawTransition newTransition(DrawStep fromStep, DrawStep toStep) {		
		DrawTransition trsn = new DrawTransition(createConnector(fromStep.getWorkflowStep(), toStep.getWorkflowStep()));
		return trsn;
	}

	private Connector createConnector(WorkflowStep fromStep, WorkflowStep toStep) {
		ConnectorType connectortype = ConnectorType.Directed;
		int thickness = 1;
		String name = "Transition" + workflowCanvas.getNextConnectorNameIndex();
		Connector conn = new Connector(name, fromStep, toStep, connectortype, thickness, workflowCanvas);
		return conn;
	}
	
	protected void setMode(Mode mode) {
		workflowCanvas.setMode(mode);
	}

	protected void setSelectionHandler(WorkflowCanvas.SelectionHandler handler) {
		workflowCanvas.setSelectionHandler(handler);
	}

	protected void removeSelection() {
		workflowCanvas.removeSelection();
	}

	public boolean hasSelection() {
		return workflowCanvas.hasSelection();
	}
}
