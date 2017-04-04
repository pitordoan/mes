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

import java.util.HashMap;
import java.util.Map;

import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.views.common.DetailsView.EventHandler;
import com.cimpoint.mes.client.views.wot.DataPanel;
import com.cimpoint.mes.client.views.wot.ObjectHierarchyPanel;
import com.cimpoint.mes.client.views.wot.SearchPanel;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.Type;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class DetailsView extends ComponentView<EventHandler> {
	private TabSet tabSet;
	private Object object;
	private MESConstants.Object.Type objectType;
	private Map<String, DetailsPane> detailsPaneMap = new HashMap<String, DetailsPane>();
	private Tab activeTab;
	private boolean registeredHandlers = false;
	
	public DetailsView() {
		createView();
		registerHandlers();	
	}
	
	private void createView() {
		tabSet = new TabSet();
		tabSet.setWidth100();
		tabSet.setHeight100();
		tabSet.setTabBarPosition(Side.BOTTOM);  		
		
		this.addChild(tabSet);
				
		activeTab = addTab("propertiesTab", "Properties");
		addTab("historyTab", "History");
		addTab("routingTab", "Routing");
		
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				registerHandlers();
			}
		});
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		
		MESApplication.getWOTrackingView().getSearchPanel().addEventHandler(new SearchPanel.EventHandler(DetailsView.class) {
			@Override
			public void onSearchFound(Class<?> handlerClass, Object object) {
				if (handlerClass == DetailsView.class) {
					showObjectByType(object);
				}
			}			
		});
		
		MESApplication.getWOTrackingView().getDataPanel().addEventHandler(new DataPanel.EventHandler(DetailsView.class) {
			@Override
			public void onTabSelected(Class<?> handlerClass, String tabName) {
				showDataOnTab(activeTab);
			}

			@Override
			public void onTabDeselected(Class<?> handlerClass, String tabName) {				
			}	
		});
		
		MESApplication.getWOTrackingView().getObjectHierarachyPanel().addEventHandler(new ObjectHierarchyPanel.EventHandler(DetailsView.class) {
			@Override
			public void onObjectSelected(Class<?> handlerClass, Object object, MESConstants.Object.Type objectType, String objectName) {
				if (handlerClass == DetailsView.class) {
					showObjectByType(object);
				}
			}			
		});
		
		registeredHandlers = true;
	}
	
	private void showObjectByType(Object object) {
		if (object instanceof WorkOrder) {
			showWorkOrder((WorkOrder) object);
		}
		else if (object instanceof Lot) {
			showLot((Lot) object);
		}
		else if (object instanceof Unit) {
			showUnit((Unit) object);
		}
		else if (object instanceof Batch) {
			showBatch((Batch) object);
		}
		else if (object instanceof Container) {
			showContainer((Container) object);
		}
	}
	
	private void showContainer(Container container) {
		this.object = container;
		this.objectType = MESConstants.Object.Type.Container;
		showDataOnTab();
	}

	private void showBatch(Batch batch) {
		this.object = batch;
		this.objectType = MESConstants.Object.Type.Batch;
		showDataOnTab();
	}

	private void showUnit(Unit unit) {
		this.object = unit;
		this.objectType = MESConstants.Object.Type.Unit;
		showDataOnTab();
	}

	private void showLot(Lot lot) {
		this.object = lot;
		this.objectType = MESConstants.Object.Type.Lot;
		showDataOnTab();
	}

	private void showWorkOrder(WorkOrder workOrder) {
		this.object = workOrder;
		this.objectType = MESConstants.Object.Type.WorkOrder;
		showDataOnTab();
	}

	private Tab addTab(String name, String title) {
		final Tab newTab = new Tab();
		newTab.setName(name);
		newTab.setTitle(title);
		tabSet.addTab(newTab);
		
		newTab.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				activeTab = event.getTab();				
				if (objectType != null) {
					showDataOnTab(newTab);
				}
			}			
		});
		
		return newTab;
	}
		
	private void showDataOnTab() {
		if (this.isVisible()) {
			showDataOnTab(this.activeTab);
		}
	}
	
	private void showDataOnTab(Tab tab) {
		if (objectType != null) {
			if (tab.getName().equals("propertiesTab")) {
				showDataOnPropertiesTab(tab);
			}
			else if (tab.getName().equals("historyTab")) {
				showDataOnHistoryTab(tab);
			}
			else if (tab.getName().equals("routingTab")) {
				showRoutingTab(tab);
			}
		}
	}
	
	private void showDataOnPropertiesTab(Tab tab) {
		String key = objectType.toString() + "_Properties";
		DetailsPane p = detailsPaneMap.get(key);
		if (p == null) {	
			if (objectType == MESConstants.Object.Type.Lot) {
				p = new LotPropertiesPane();
				detailsPaneMap.put(key, p);
			}
			//TODO add more support here
			else {
				throw new RuntimeException("Object type not supported: " + objectType);
			}
		}
		
		p.loadData(object);
		tabSet.updateTab(tab, p);		
	}
	
	private void showDataOnHistoryTab(Tab tab) {
		String key = objectType.toString() + "_History";
		DetailsPane p = detailsPaneMap.get(key);
		if (p == null) {	
			if (objectType == MESConstants.Object.Type.Lot) {
				p = new LotHistoryPane();
				detailsPaneMap.put(key, p);
			}
			//TODO add more support here
			else {
				throw new RuntimeException("Object type not supported: " + objectType);
			}
		}
		
		p.loadData(object);
		tabSet.updateTab(tab, p);		
	}
	
	private void showRoutingTab(Tab tab) {
		String key = "Routing";
		DetailsPane p = detailsPaneMap.get(key);
		if (p == null) {	
			p = new RoutingPane();
			detailsPaneMap.put(key, p);
		}
		
		p.loadData(object);
		tabSet.updateTab(tab, p);		
	}
			
	public static abstract class EventHandler {
		
	}
}
