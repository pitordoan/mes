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
import com.smartgwt.client.types.LineCap;
import com.smartgwt.client.types.LinePattern;
import com.smartgwt.client.widgets.drawing.DrawCurve;
import com.smartgwt.client.widgets.drawing.DrawTriangle;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.drawing.events.ClickEvent;
import com.smartgwt.client.widgets.drawing.events.ClickHandler;
import com.smartgwt.client.widgets.drawing.events.DragMove;
import com.smartgwt.client.widgets.drawing.events.DragMoveHandler;
import com.smartgwt.client.widgets.drawing.events.MouseOutEvent;
import com.smartgwt.client.widgets.drawing.events.MouseOutHandler;
import com.smartgwt.client.widgets.drawing.events.MouseOverEvent;
import com.smartgwt.client.widgets.drawing.events.MouseOverHandler;

public class Connector extends Widget {
	private WorkflowStep startPointStep;
	private WorkflowStep endPointStep;
	private Point startPoint; //DrawCurve doesn't keep X/Y of start/end point after being set
	private Point endPoint;
	private Point controlPoint1;
	private Point controlPoint2;
	private DrawCurve connector;
	private DrawTriangle endArrow;
	//private DrawOval controlKnob1;
	//private DrawOval controlKnob2;
	private Widget draggedOverWidget;
	public static enum ControlPointType {StartPoint, EndPoint};
	
	public Connector(String name, final WorkflowStep fromStep, 
			final WorkflowStep toStep, ConnectorType connectortype, int thickness, WorkflowCanvas workflowCanvas) {
		super(name, new DrawCurve(), workflowCanvas);
		this.connector = (DrawCurve) super.drawItem;
		this.connector.setCanDrag(false);	
		connector.setLineColor("gray");
		connector.setLineWidth(thickness);
		connector.setLinePattern(LinePattern.SOLID);
		connector.setLineCap(LineCap.BUTT);
		        
		startPointStep = fromStep;
		endPointStep = toStep;
		setStartPoint(startPointStep.getConnectingPoint(endPointStep));
		setEndPoint(endPointStep.getConnectingPoint(startPointStep));
		
		setControlPoints();
		
		fromStep.addConnector(this, ControlPointType.StartPoint);
		toStep.addConnector(this, ControlPointType.EndPoint);
		
		endArrow = new DrawTriangle();
		endArrow.setFillColor("gray");	
	}
	
	@Override
	public void draw() {
		connector.addDragMoveHandler(new DragMoveHandler() {
			@Override
			public void onDragMove(DragMove event) {
				Widget w = workflowCanvas.findWorkflowStepAt(event.getX(), event.getY());
				if (draggedOverWidget != null && draggedOverWidget != w) {
					draggedOverWidget.drawSelected(false);
				}

				draggedOverWidget = w;
				if (draggedOverWidget != null) {
					draggedOverWidget.drawSelected(true);
				}
			}
		});
		
		connector.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				workflowCanvas.onConnectorClicked(Connector.this);
				event.cancel();
			}			
		});
		
		connector.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				workflowCanvas.onWidgetMouseOver(Connector.this);
				event.cancel();
			}			
		});
		
		connector.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				workflowCanvas.onWidgetMouseOut(Connector.this);
				event.cancel();
			}			
		});
		
		connector.draw();	
		drawEndArrow();
	}
	
	@Override
	public void drawSelected(boolean b) {
		if (b) connector.setLineColor("blue");
		else connector.setLineColor("gray");
		showControlKnobs(b);
	}
	
	@Override
	public void destroy() {
		connector.destroy();
		endArrow.destroy();
		//if (controlKnob1 != null && controlKnob1.isCreated()) controlKnob1.destroy();
		//if (controlKnob2 != null && controlKnob2.isCreated()) controlKnob2.destroy();
	}
	
	public void onWidgetMoved() {	
		redrawConnector();
		showControlKnobs(false);
	}
	
	private void setControlPoints() {
		int dx = (Math.abs(getEndPoint().getX() - getStartPoint().getX())) / 2;
		if (dx < 100) dx = 100;
		
		//start point is the right control point of startPointWidget
		if (getStartPoint().getX() == startPointStep.getRightControlPoint().getX()) {
			controlPoint1 = new Point(getStartPoint().getX() + dx, getStartPoint().getY());
			controlPoint2 = new Point(getEndPoint().getX() - dx, getEndPoint().getY());
			connector.setControlPoint1(controlPoint1);
			connector.setControlPoint2(controlPoint2);
		}
		//start point is the left control point of startPointWidget
		else { // if (getStartPoint().getX() == startPointWidget.getLeftControlPoint().getX()) {
			controlPoint1 = new Point(getStartPoint().getX() - dx, getStartPoint().getY());
			controlPoint2 = new Point(getEndPoint().getX() + dx, getEndPoint().getY());
			connector.setControlPoint1(controlPoint1);
			connector.setControlPoint2(controlPoint2);
		}
	}
	
	public boolean containsPoint(int x, int y) {
		return connector.isPointInPath(x, y);
	}

	private Point getStartPoint() {
		return startPoint;
	}
	
	private Point getEndPoint() {
		return endPoint;
	}

	private void setStartPoint(Point point) {
		startPoint = point;
		connector.setStartPoint(point);
	}
	
	private void setEndPoint(Point point) {
		endPoint = point;
		connector.setEndPoint(endPoint);
	}

	private void redrawConnector() {
		connector.destroy();
		
		startPoint = startPointStep.getConnectingPoint(endPointStep);
		endPoint = endPointStep.getConnectingPoint(startPointStep);
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setControlPoints();
		workflowCanvas.addDrawItems(connector);
		connector.draw();
		
		drawEndArrow();
	}
	
	/*private void redrawControlKnobs(String color) {
		//drawControlKnob1(color);
		//drawControlKnob2(color);
	}
	
	private void drawControlKnob1(String color) {
		if (this.controlKnob1 == null) {
			this.controlKnob1 = new DrawOval();	
			
			this.controlKnob1.addDragMoveHandler(new DragMoveHandler() {
				@Override
				public void onDragMove(DragMove event) {
					//workflowCanvas.setCursor(Cursor.MOVE);
					setStartPoint(new Point(event.getX(), event.getY()));
					
					Widget w = workflowCanvas.findWorkflowStepAt(event.getX(), event.getY());
					if (draggedOverWidget != null && draggedOverWidget != w) {
						draggedOverWidget.drawSelected(false);
					}
					
					draggedOverWidget = w;
					if (draggedOverWidget != null) {
						draggedOverWidget.drawSelected(true);
					}						
				}					
			});
			
			this.controlKnob1.addDragStopHandler(new DragStopHandler() {
				@Override
				public void onDragStop(DragStop event) {
					//workflowCanvas.setCursor(Cursor.ARROW);
					redrawConnector();
				}					
			});
			
			this.controlKnob1.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {						
					//workflowCanvas.setCursor(Cursor.MOVE);
					drawControlKnob1("green");
				}					
			});
			
			this.controlKnob1.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					//workflowCanvas.setCursor(Cursor.ARROW);
					drawControlKnob1("red");
				}					
			});
			
			this.workflowCanvas.setDrawPane(controlKnob1);
			this.controlKnob1.setCanDrag(true);
			this.controlKnob1.setLeft(startPoint.getX() - 5);
			this.controlKnob1.setTop(startPoint.getY() - 5);
			this.controlKnob1.setWidth(10);
			this.controlKnob1.setHeight(10);
			this.controlKnob1.setFillColor(color);
			this.controlKnob1.setFillOpacity(0.5f);
			this.controlKnob1.setLineWidth(0);
			this.controlKnob1.draw();
		}		
		else {
			this.controlKnob1.destroy();
			this.workflowCanvas.setDrawPane(controlKnob1);
			this.controlKnob1.setCanDrag(true);
			this.controlKnob1.setFillColor(color);
			this.controlKnob1.setFillOpacity(0.5f);
			this.controlKnob1.setLineWidth(0);
			this.controlKnob1.setLeft(startPoint.getX() - 5);
			this.controlKnob1.setTop(startPoint.getY() - 5);
			this.controlKnob1.draw();
		}
	}
	
	private void drawControlKnob2(String color) {
		if (this.controlKnob2 == null) {
			this.controlKnob2 = new DrawOval();	
			
			this.controlKnob2.addDragMoveHandler(new DragMoveHandler() {
				@Override
				public void onDragMove(DragMove event) {
					//workflowCanvas.setCursor(Cursor.MOVE);
					setEndPoint(new Point(event.getX(), event.getY()));
					
					Widget w = workflowCanvas.findWorkflowStepAt(event.getX(), event.getY());
					if (draggedOverWidget != null && draggedOverWidget != w) {
						draggedOverWidget.drawSelected(false);
					}
					
					draggedOverWidget = w;
					if (draggedOverWidget != null) {
						draggedOverWidget.drawSelected(true);
					}						
				}					
			});
			
			this.controlKnob2.addDragStopHandler(new DragStopHandler() {
				@Override
				public void onDragStop(DragStop event) {
					//workflowCanvas.setCursor(Cursor.ARROW);
					redrawConnector();
				}					
			});
			
			this.controlKnob2.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {						
					//workflowCanvas.setCursor(Cursor.MOVE);
					drawControlKnob2("green");
				}					
			});
			
			this.controlKnob2.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					///workflowCanvas.setCursor(Cursor.ARROW);
					drawControlKnob2("red");
				}					
			});
			

			this.workflowCanvas.setDrawPane(controlKnob2);
			this.controlKnob2.setCanDrag(true);
			this.controlKnob2.setLeft(endPoint.getX() - 5);
			this.controlKnob2.setTop(endPoint.getY() - 5);
			this.controlKnob2.setWidth(10);
			this.controlKnob2.setHeight(10);
			this.controlKnob2.setFillColor(color);
			this.controlKnob2.setFillOpacity(0.5f);
			this.controlKnob2.setLineWidth(0);
			this.controlKnob2.draw();
		}		
		else {
			this.controlKnob2.destroy();
			this.workflowCanvas.setDrawPane(controlKnob2);
			this.controlKnob2.setCanDrag(true);
			this.controlKnob2.setFillColor(color);
			this.controlKnob2.setFillOpacity(0.5f);
			this.controlKnob2.setLineWidth(0);
			this.controlKnob2.setLeft(endPoint.getX() - 5);
			this.controlKnob2.setTop(endPoint.getY() - 5);
			this.controlKnob2.draw();
		}
	}*/
	
	public void showControlKnobs(boolean showed) {
		/*if (showed) {
			connector.showKnobs(KnobType.STARTPOINT, KnobType.ENDPOINT);
		}
		else {
			connector.hideKnobs(KnobType.STARTPOINT, KnobType.ENDPOINT);
		}*/
		
		/*if (showed) {
			redrawControlKnobs("red");
		}
		else {
			if (this.controlKnob1 != null) {
				this.controlKnob1.destroy();		
			}
			
			if (this.controlKnob2 != null) {
				this.controlKnob2.destroy();		
			}
		}*/
	}
	
	private void drawEndArrow() {
		if (endArrow.isCreated()) endArrow.destroy();
		
		//end point is the right control point of endPointWidget
		if (getEndPoint().getX() == endPointStep.getRightControlPoint().getX()) {
			Point arrowEndPoint = new Point(endPoint.getX(), endPoint.getY());
			Point arrowStartPoint1 = new Point(arrowEndPoint.getX() + 5, arrowEndPoint.getY() - 3);
			Point arrowStartPoint2 = new Point(arrowEndPoint.getX() + 5, arrowEndPoint.getY() + 3);
			endArrow.setPoints(arrowStartPoint1, arrowStartPoint2, arrowEndPoint);
		}
		//end point is the left control point of endPointWidget
		else {
			Point arrowEndPoint = new Point(endPoint.getX(), endPoint.getY());
			Point arrowStartPoint1 = new Point(arrowEndPoint.getX() - 5, arrowEndPoint.getY() - 3);
			Point arrowStartPoint2 = new Point(arrowEndPoint.getX() - 5, arrowEndPoint.getY() + 3);
			endArrow.setPoints(arrowStartPoint1, arrowStartPoint2, arrowEndPoint);			
		}
		
		this.workflowCanvas.addDrawItems(endArrow);
		endArrow.draw();
	}
	
	/*private Point getForwardArrowPoint(float t) {
		//float t = 0.7f;
		float p0X = startPoint.getX();
		float p0Y = startPoint.getY();
		float p1X = controlPoint1.getX();
		float p1Y = controlPoint1.getY();
		float p2X = controlPoint2.getX();
		float p2Y = controlPoint2.getY();
		float p3X = endPoint.getX();
		float p3Y = endPoint.getY();
		
		Point pt = new Point(bezierInterpolation(t, p0X, p1X, p2X, p3X), bezierInterpolation(t, p0Y, p1Y, p2Y, p3Y));
		return pt;
	}
			
	private int bezierInterpolation(float t, float a, float b, float c, float d) {
		float c1 = (float)(d - (3.0 * c) + (3.0 * b) - a);
	    float c2 = (float)((3.0 * c) - (6.0 * b) + (3.0 * a));
	    float c3 = (float)((3.0 * b) - (3.0 * a));
	    float c4 = a;
	
	    float value = (float) (c1*t*t*t + c2*t*t + c3*t + c4 );
	    return Math.round(value);
	}
	
	
	private int bezierTangent(float t, float a, float b, float c, float d) {
	    float c1 = (float)(d - (3.0 * c) + (3.0 * b) - a);
	    float c2 = (float)((3.0 * c) - (6.0 * b) + (3.0 * a));
	    float c3 = (float)((3.0 * b) - (3.0 * a));
	
	    float value = (float) ((3.0 * c1 * t* t) + (2.0 * c2 * t) + c3);
	    return Math.round(value);
    }*/
}
