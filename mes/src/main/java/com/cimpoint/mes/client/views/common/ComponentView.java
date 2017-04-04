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
package com.cimpoint.mes.client.views.common;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.layout.VLayout;

public abstract class ComponentView<EventHandler> extends VLayout {
	private List<EventHandler> handlers;
	private boolean visible = false;
	
	public void addEventHandler(EventHandler handler) {
		if (handlers == null) {
			handlers = new ArrayList<EventHandler>();
		}
		handlers.add(handler);
	}

	protected List<EventHandler> getEventHandlers() {
		return handlers;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
}
