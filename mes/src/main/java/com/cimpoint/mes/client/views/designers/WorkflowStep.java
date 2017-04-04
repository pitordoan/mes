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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cimpoint.mes.client.views.designers.Connector.ControlPointType;
import com.smartgwt.client.widgets.drawing.DrawGroup;
import com.smartgwt.client.widgets.drawing.DrawImage;
import com.smartgwt.client.widgets.drawing.DrawLabel;
import com.smartgwt.client.widgets.drawing.DrawLine;
import com.smartgwt.client.widgets.drawing.DrawLinePath;
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.drawing.events.ClickEvent;
import com.smartgwt.client.widgets.drawing.events.ClickHandler;
import com.smartgwt.client.widgets.drawing.events.DragMove;
import com.smartgwt.client.widgets.drawing.events.DragMoveHandler;
import com.smartgwt.client.widgets.drawing.events.DragStop;
import com.smartgwt.client.widgets.drawing.events.DragStopHandler;

public class WorkflowStep extends Widget {
	private DrawGroup drawGroup;
	private DrawRect drawRect;
	private DrawImage drawImage;
	private DrawLabel drawLabel;
	private Map<Connector, ControlPointType> connectorPointMap = new HashMap<Connector, ControlPointType>();
		
	public WorkflowStep(String name, String imageSrc, String label, int width, int height, WorkflowCanvas workflowCanvas) {
		super(name, new DrawGroup(), workflowCanvas);
		
		drawRect = new DrawRect();
		drawImage = new DrawImage();
		drawLabel = new DrawLabel();
		drawGroup = (DrawGroup) super.drawItem;
		
		drawGroup.setCanDrag(true);
		
		drawGroup.setWidth(width);
		drawGroup.setHeight(height);
		drawGroup.setCanDrag(true);
				
		drawRect.setLineWidth(1);
		drawRect.setWidth(width);
		drawRect.setHeight(height);
		drawRect.setFillColor("blue");
		drawRect.setFillOpacity(0.5f);
		
		if (imageSrc != null && !imageSrc.isEmpty()) {
			drawImage.setSrc(imageSrc);
			drawImage.setWidth(width);
			drawImage.setHeight(height);
		}
						
		drawLabel.setContents(label);
		drawLabel.setFontSize(12);
		drawLabel.setFontWeight("normal");
		
		drawRect.setDrawGroup(drawGroup);
		drawImage.setDrawGroup(drawGroup);
		drawLabel.setDrawGroup(drawGroup);
	}

	public void setLeft(int left) {
		drawGroup.setLeft(left);	
		drawRect.setLeft(left);
		drawImage.setLeft(left);
		drawLabel.setLeft(left);		
	}
	
	public void setTop(int top) {
		drawGroup.setTop(top);	
		drawRect.setTop(top);
		drawImage.setTop(top);
		drawLabel.setTop(top + drawGroup.getHeight() + 2);
	}
		
	@Override
	public void drawSelected(boolean b) {
		if (b) {
			drawRect.show();		
		}
		else {
			drawRect.hide();	
		}
	}
	
	@Override
	public void destroy() {
		Iterator<Connector> it = connectorPointMap.keySet().iterator();
		while (it.hasNext()) {
			it.next().destroy();			
		}
		drawGroup.destroy();
		connectorPointMap.clear();
	}
	
	@Override
	public void draw() {
		this.drawGroup.addDragMoveHandler(new DragMoveHandler() {
			@Override
			public void onDragMove(DragMove event) {
				if ((event.getX() - getWidth()) <= 0 || (event.getY() - getHeight()) <= 0) {
					event.cancel();
					return;
				}
												
				moveConnectedPoints();
			}			
		});
		
		this.drawGroup.addDragStopHandler(new DragStopHandler() {
			@Override
			public void onDragStop(DragStop event) {
				moveConnectedPoints();
			}			
		});		
		
		this.drawGroup.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				workflowCanvas.onWorkflowStepClicked(WorkflowStep.this);
				event.cancel();
			}			
		});
		
		drawGroup.draw();
		drawImage.draw();
		drawLabel.draw();

		drawRect.hide();
	}
	
	protected void addConnector(Connector connector, ControlPointType pointType) {
		connectorPointMap.put(connector, pointType);
	}
	
	private void moveConnectedPoints() {
		if ((getLeft() + getWidth()) > (workflowCanvas.getLeft() + workflowCanvas.getWidth())) {
			workflowCanvas.setWidth(workflowCanvas.getWidth() + getWidth() + 20);
			workflowCanvas.scrollToRight();			
		}
		
		if ((getTop() + getHeight()) > (workflowCanvas.getTop() + workflowCanvas.getHeight())) {
			workflowCanvas.setHeight(workflowCanvas.getHeight() + getHeight() + 20);
			workflowCanvas.scrollToBottom();			
		}
		
		Iterator<Connector> it = connectorPointMap.keySet().iterator();
		while (it.hasNext()) {
			Connector conn = it.next();
			conn.onWidgetMoved();
		}
	}
	
	public Point getConnectingPoint(WorkflowStep anotherStep) {
		if ((this.getLeft() + this.getWidth()) < anotherStep.getLeft() + (anotherStep.getWidth())) {
			return this.getRightControlPoint();
		}
		else {
			return this.getLeftControlPoint();
		}
	}

	public int getLeft() {
		if (drawItem instanceof DrawLine) {
			return ((DrawLine) drawItem).getStartLeft();
		}
		else if (drawItem instanceof DrawLinePath) {
			return ((DrawLinePath) drawItem).getStartLeft();
		}
		else if (drawItem instanceof DrawGroup) {
			return ((DrawGroup) drawItem).getLeft();
		}

		throw new RuntimeException("Not supported widget subtype");
	}
		
	public int getTop() {
		if (drawItem instanceof DrawLine) {
			return ((DrawLine) drawItem).getStartTop();
		}
		else if (drawItem instanceof DrawLinePath) {
			return ((DrawLinePath) drawItem).getStartTop();
		}
		else if (drawItem instanceof DrawGroup) {
			return ((DrawGroup) drawItem).getTop();
		}
		
		throw new RuntimeException("Not supported widget subtype");
	}
	
	public int getWidth() {
		if (drawItem instanceof DrawLine) {
			return ((DrawLine) drawItem).getEndLeft() - getLeft();
		}
		else if (drawItem instanceof DrawLinePath) {
			return ((DrawLinePath) drawItem).getEndLeft() - getLeft();
		}
		else if (drawItem instanceof DrawGroup) {
			return ((DrawGroup) drawItem).getWidth();
		}

		throw new RuntimeException("Not supported widget subtype");
	}
	
	public int getHeight() {
		if (drawItem instanceof DrawLine) {
			return ((DrawLine) drawItem).getEndTop() - getTop();
		}
		else if (drawItem instanceof DrawLinePath) {
			return ((DrawLinePath) drawItem).getEndTop() - getTop();
		}
		else if (drawItem instanceof DrawGroup) {
			return ((DrawGroup) drawItem).getHeight();
		}

		throw new RuntimeException("Not supported widget subtype");
	}
	
	public Point getLeftControlPoint() {
		//return new Point(getLeft(), getTop() + getHeight()/2);
		return new Point(getLeft() - 6, getTop() + getHeight()/2);
	}
	
	public Point getTopControlPoint() {
		//return new Point(getLeft() + getWidth()/2, getTop());
		return new Point(getLeft() + getWidth()/2, getTop() - 6);
	}
	
	public Point getRightControlPoint() {
		//return new Point(getLeft() + getWidth(), getTop() + getHeight()/2);
		return new Point(getLeft() + getWidth() + 6, getTop() + getHeight()/2);
	}
	
	public Point getBottomControlPoint() {
		//return new Point(getLeft() + getWidth()/2, getTop() + getHeight());
		return new Point(getLeft() + getWidth()/2, getTop() + getHeight() + 6);
	}
}
