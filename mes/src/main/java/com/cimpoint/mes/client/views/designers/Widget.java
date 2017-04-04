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

import com.smartgwt.client.widgets.drawing.DrawItem;

public abstract class Widget {		
	private String name;
	protected DrawItem drawItem;
	protected WorkflowCanvas workflowCanvas;
	
	protected Widget(String name, DrawItem drawItem, WorkflowCanvas workflowCanvas) {
		this.name = name;
		this.drawItem = drawItem;
		this.workflowCanvas = workflowCanvas;		
		this.workflowCanvas.addDrawItems(drawItem);
	}
				
	public boolean containsPoint(int x, int y) {
		return this.drawItem.isInBounds(x, y);
	}
			
	public void setCanDrag(boolean canDrag) {
		this.drawItem.setCanDrag(canDrag);
	}
	
	public String getName() {
		return name;
	}

	public abstract void destroy();
	
	public abstract void drawSelected(boolean b);
	
	public abstract void draw();
}
