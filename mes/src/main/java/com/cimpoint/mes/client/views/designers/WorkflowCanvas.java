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

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.events.BrowserEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionMoveEvent;
import com.smartgwt.client.widgets.events.DragRepositionMoveHandler;

public abstract class WorkflowCanvas extends Canvas {
	public static enum Mode {Select, AddStep, ConnectSteps};
	public static enum ConnectorType {Directed, Indirected};
	
	private DrawPane pane;
	private Widget selectedWidget;
	private WorkflowStep fromStep;
	//private Connector selectedConnector;
	private List<WorkflowStep> steps = new ArrayList<WorkflowStep>();
	private List<Connector> connectors = new ArrayList<Connector>();
	private Mode mode = Mode.Select;
	private int stepNameIndex = 0;
	private int connectorNameIndex = 0;
	
	//private ConnectorType defaultConnectorType = ConnectorType.Directed;	
	//private int defaultConnectorThickness = 1;
	//private int defaultWidgetWidth = 32;
	//private int defaultWidgetHeight = 32;
	//private String defaultStepImageSource;
	private SelectionHandler selectionHandler;
	
	public WorkflowCanvas(int left, int top, String width, String height) {
		this.setWidth(width);
		this.setHeight(height);
		this.pane = new DrawPane();
		this.pane.setHeight(height);  
        this.pane.setWidth(width);  
        this.pane.setTop(top);  
        this.pane.setLeft(left);  
        this.pane.setCursor(Cursor.AUTO);  
        this.pane.setCanDrag(true);
        this.pane.setOverflow(Overflow.HIDDEN); //set parent to overflow but not this canvas itself
                
        this.setOverflow(Overflow.AUTO);
        this.addChild(this.pane);
        
        /*final Label statusLabel = new Label();
    	statusLabel.setWidth("50%");
    	statusLabel.setHeight(50);
    	this.pane.addChild(statusLabel);
		this.pane.addHoverHandler(new HoverHandler() {
			@Override
			public void onHover(HoverEvent event) {
				statusLabel.setContents("Hover x: " + event.getX() + ", y: " + event.getY());
			}			
		});*/
		
		this.pane.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (selectedWidget != null) {
					selectedWidget.drawSelected(false);
					selectedWidget = null;
				}
				
				if (fromStep != null) {
					fromStep.drawSelected(false);
					fromStep = null;
				}

				if (mode == Mode.AddStep) {
					int[] coord = getRelativeCoordinate(event);
					WorkflowStep step = newWorkflowStep();
					int left = coord[0] - step.getWidth() / 2;
					int top = coord[1] - step.getHeight() / 2;
					step.setLeft(left);
					step.setTop(top);
					addWorkflowStep(step);
				}
				
				fireSelectionHandler(null);
			}			
		});
		
		pane.addDragRepositionMoveHandler(new DragRepositionMoveHandler() {
			@Override
			public void onDragRepositionMove(DragRepositionMoveEvent event) {
				SC.say(event.getSource().toString());
			}			
		});
		
			
			/*@Override
			public void onDragMove(DragMove event) {
				Widget w = workflowCanvas.getMouseOverWidget();
				if (w != null && w.containsPoint(event.getX(), event.getY())) {
					w.drawSelected(true);
				}
				
				DrawItem[] drawItems = workflowCanvas.getDrawPane().getDrawItems();
				for (DrawItem di: drawItems) {
					if (di.isInBounds(event.getX(), event.getY())) {
						
						//if (di instanceof Widget) {
						//	((Widget) di).drawSelected(true);
						//}
					}
				}
			}			
		});*/
		
	}
	
	private int[] getRelativeCoordinate(BrowserEvent<?> event) {
		int x = event.getX() - this.pane.getAbsoluteLeft();
		int y = event.getY() - this.pane.getAbsoluteTop();
		return new int[] {x, y};
	}
			
	protected void addWorkflowStep(WorkflowStep step) {
		this.steps.add(step);
		step.draw();
	}
	
	protected void removeWorkflowStep(WorkflowStep step) {
		if (this.steps.contains(step)) {
			this.steps.remove(step);
		}
		step.destroy();
	}
	
	protected void addConnector(Connector conn) {
		this.connectors.add(conn);
		conn.draw();		
	}
	
	protected void removeConnector(Connector conn) {
		if (this.connectors.contains(conn)) {
			this.connectors.remove(conn);
		}
		conn.destroy();
	}
	
	public Widget findWorkflowStepAt(int x, int y) {
		for (WorkflowStep s: steps) {
			if (s.containsPoint(x, y)) {
				return s;
			}
		}
		
		return null;
	}
	
	public void zoom(float zoomLevel) {
		this.pane.zoom(zoomLevel);
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return this.mode;
	}
		
	public void setSelectionHandler(SelectionHandler handler) {
		selectionHandler = handler;
	}
	
	private void fireSelectionHandler(Widget w) {
		if (selectionHandler != null) {
			if (w == null) {
				selectionHandler.onCanvasSelected();
			}
			else if (w instanceof Connector) {
				selectionHandler.onConnectorSelected((Connector) w);
			} 
			else if (w instanceof WorkflowStep) {
				selectionHandler.onStepSelected((WorkflowStep) w);
			}
		}
	}
	
	protected void onConnectorClicked(Connector conn) {	
		if (this.selectedWidget != null && this.selectedWidget != conn) {
			this.selectedWidget.drawSelected(false);
		}
		
		this.selectedWidget = conn;
		this.selectedWidget.drawSelected(true);					
		fireSelectionHandler(this.selectedWidget);
	}
	
	protected void onWorkflowStepClicked(WorkflowStep step) {
		if (mode == Mode.ConnectSteps) {
			if (this.fromStep == null) {
				if (this.selectedWidget != null) {
					this.selectedWidget.drawSelected(false);
					this.selectedWidget = null;
				}
				
				this.fromStep = (WorkflowStep) step;
				step.drawSelected(true);
			} else if (this.fromStep != null && step != this.fromStep) {
				Connector conn = newConnector(this.fromStep, (WorkflowStep) step);
				addConnector(conn);
				this.fromStep.drawSelected(false);
				this.fromStep = null;
			}
		} else {
			if (this.selectedWidget != null) {
				this.selectedWidget.drawSelected(false);
			}
			
			if (this.fromStep != null) {
				this.fromStep.drawSelected(false);
				this.fromStep = null;
			}
			
			this.selectedWidget = step;
			
			if (mode == Mode.Select) {
				this.selectedWidget.drawSelected(true);
			}
		}

		fireSelectionHandler(this.selectedWidget);
	}
	
	protected void onWidgetMouseOver(Widget widget) {
		if (widget instanceof Connector) {
			widget.drawSelected(true);
		}
	}
	
	protected void onWidgetMouseOut(Widget widget) {
		if (widget instanceof Connector && this.selectedWidget != widget) {
			widget.drawSelected(false);
		}
	}
	
	protected abstract WorkflowStep newWorkflowStep();
	
	protected abstract Connector newConnector(WorkflowStep fromStep, WorkflowStep toStep);
		
	protected void resetMode() {
		this.mode = Mode.Select;
	}

	protected int getNextNameIndex() {
		++stepNameIndex;
		return stepNameIndex;
	}
	
	protected int getNextConnectorNameIndex() {
		++connectorNameIndex;
		return connectorNameIndex;
	}

	protected void addDrawItems(DrawItem... drawItems) {
		if (drawItems != null) {
			for (DrawItem di: drawItems) {
				di.setDrawPane(pane);
			}
		}
	}
	
	protected void removeSelection() {
		if (this.selectedWidget != null) {
			if (this.selectedWidget instanceof WorkflowStep) {
				
				this.removeWorkflowStep((WorkflowStep) this.selectedWidget);
			}
			else if (this.selectedWidget instanceof Connector) {
				this.removeConnector((Connector) this.selectedWidget);
			}
		}
	}
	
	public static abstract class SelectionHandler {
		public abstract void onStepSelected(WorkflowStep step);
		public abstract void onConnectorSelected(Connector connector);
		public abstract void onCanvasSelected();
	}

	protected boolean hasSelection() {
		return this.selectedWidget != null;
	}
}
