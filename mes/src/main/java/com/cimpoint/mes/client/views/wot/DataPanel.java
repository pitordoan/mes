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
package com.cimpoint.mes.client.views.wot;

import java.util.List;

import com.cimpoint.mes.client.views.common.ComponentEventHandler;
import com.cimpoint.mes.client.views.common.ComponentView;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class DataPanel extends ComponentView<DataPanel.EventHandler> {
	private TabSet tabSet;
	private boolean registeredHandlers = false;
	
	public DataPanel(String width, String height) {
		this.setWidth(width);
		this.setHeight(height);
				
		tabSet = new TabSet();
		tabSet.setWidth100();
		tabSet.setHeight100();
				
		this.addChild(tabSet);
		
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				registerHandlers();
			}
		});
	}
	
	public void addTab(String name, String title, final ComponentView<?> view) {
		Tab newTab = new Tab();
		newTab.setName(name);
		newTab.setTitle(title);
		newTab.setPane(view);
		tabSet.addTab(newTab);
		
		newTab.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				view.setVisible(true);
				fireOnTabSelected(event.getTab().getName());
			}			
		});
		
		newTab.addTabDeselectedHandler(new TabDeselectedHandler() {
			@Override
			public void onTabDeselected(TabDeselectedEvent event) {
				view.setVisible(false);
				fireOnTabDeselected(event.getTab().getName());
			}			
		});
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		//TODO add code here
		registeredHandlers = true;
	}	
	
	private void fireOnTabSelected(String tabName) {
		List<EventHandler> handlers = super.getEventHandlers();
		if (handlers != null) {
			for (EventHandler h: handlers) {
				h.onTabSelected(h.getHandlerClass(), tabName);
			}
		}
	}
	
	private void fireOnTabDeselected(String tabName) {
		List<EventHandler> handlers = super.getEventHandlers();
		if (handlers != null) {
			for (EventHandler h: handlers) {
				h.onTabDeselected(h.getHandlerClass(), tabName);
			}
		}
	}
		
	public static abstract class EventHandler  extends ComponentEventHandler {
		public EventHandler(Class<?> handlerClass) {
			super(handlerClass);
		}
		
		public abstract void onTabSelected(Class<?> handlerClass, String tabName);
		public abstract void onTabDeselected(Class<?> handlerClass, String tabName);	
	}
}
