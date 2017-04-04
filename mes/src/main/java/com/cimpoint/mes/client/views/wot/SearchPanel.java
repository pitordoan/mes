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

import java.util.ArrayList;
import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.views.common.ComponentEventHandler;
import com.cimpoint.mes.client.views.common.ComponentView;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class SearchPanel extends ComponentView<SearchPanel.EventHandler> {
	//private List<EventHandler> handlers;
	private String[] searchMenus = {"Work Order", "Lot", "Batch", "Unit", "Container"};
	private String searchType;
	private TextItem searchValueItem;
	private IMenuButton menuButton;
	private Label objNameLabel;
	private boolean registeredHandlers = false;
	
	public SearchPanel(String width, String height) {
		this.setWidth(width);
		this.setHeight(height);
		
		final Menu menu = new Menu();
		menu.setWidth(150);
		menu.setShowShadow(true);  
		menu.setShadowDepth(10);  
		menu.setSelectionType(SelectionStyle.SINGLE);
		
		menuButton = new IMenuButton("Search...", menu);
		menuButton.setAutoFit(true);
				
		DynamicForm searchForm = new DynamicForm();
		searchValueItem = new TextItem("searchValue", "");
		searchValueItem.disable();
		searchValueItem.setShowTitle(false);
		searchForm.setFields(searchValueItem);
		searchValueItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					doSearch();
				}
			}			
		});
		
		objNameLabel = new Label();
		objNameLabel.setTitle("");
		objNameLabel.setWidth("100%");
		objNameLabel.setBackgroundColor("#D5F2B8");
		objNameLabel.setAlign(Alignment.CENTER);
		objNameLabel.setValign(VerticalAlignment.CENTER);
		objNameLabel.setHeight(30);
		
		Button goButton = new Button("Go");
		goButton.setWidth(50);
		goButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});
		
		for (String menuName: searchMenus) {
			MenuItem menuItem = new MenuItem(menuName);
			menu.addItem(menuItem);			
			menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					String selItemTitle = event.getItem().getTitle();
					menuButton.setTitle("Search " + selItemTitle);
					searchType = selItemTitle;
					searchValueItem.enable();
					searchValueItem.focusInItem();
				}				
			});
		}
				
		HLayout layout = new HLayout();
		layout.setMembers(menuButton, searchForm, goButton);  
		
		this.setMembersMargin(5);
		this.addMember(layout);
		this.addMember(objNameLabel);
		
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				registerHandlers();
			}
		});
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		
		MESApplication.getWOTrackingView().getObjectHierarachyPanel().addEventHandler(new ObjectHierarchyPanel.EventHandler(SearchPanel.class) {
			@Override
			public void onObjectSelected(Class<?> handlerClass, Object object, MESConstants.Object.Type objectType, String objectName) {
				if (handlerClass == SearchPanel.class) {
					String type = objectType.toString();
					if (objectType == MESConstants.Object.Type.WorkOrder) {
						type = "WO";
					}
					else if (objectType == MESConstants.Object.Type.WorkOrderItem) {
						type = "WOI";
					}
					searchValueItem.setValue("");
					objNameLabel.setContents(type + ": " + objectName);
				}
			}			
		});
		
		registeredHandlers = true;				
	}	
	
	private void doSearch() {
		if (searchType != null) {
			final String searchValue = searchValueItem.getValueAsString();
			
			if (searchType.equals("Work Order")) {
				MESApplication.getMESControllers().getWorkOrderController().findWorkOrderByNumber(searchValue, new CallbackHandler<WorkOrder>() {
					@Override
					public void onSuccess(WorkOrder wo) {
						if (wo == null) {
							MESApplication.showOKMessage("Work order number not found: " + searchValue);
						}
						else {
							objNameLabel.setContents("WO: " + searchValue);
							fireSearchFoundEvent(wo);
						}
					}							
				});
			}
			else if (searchType.equals("Lot")) {
				MESApplication.getMESControllers().getLotController().findLotByNumber(searchValue, new CallbackHandler<Lot>() {
					@Override
					public void onSuccess(Lot lot) {
						if (lot == null) {
							MESApplication.showOKMessage("Lot number not found: " + searchValue);
						}
						else {
							objNameLabel.setContents(MESConstants.Object.Type.Lot.toString() + ": " + searchValue);
							fireSearchFoundEvent(lot);
						}
					}							
				});
			}
			else if (searchType.equals("Unit")) {
				MESApplication.getMESControllers().getUnitController().findUnitByNumber(searchValue, new CallbackHandler<Unit>() {
					@Override
					public void onSuccess(Unit unit) {
						if (unit == null) {
							MESApplication.showOKMessage("Unit serial number not found: " + searchValue);
						}
						else {
							objNameLabel.setContents(MESConstants.Object.Type.Unit.toString() + ": " + searchValue);
							fireSearchFoundEvent(unit);
						}
					}							
				});
			}
			else if (menuButton.getTitle().equals("Batch")) {
				//TODO
			}
			else if (menuButton.getTitle().equals("Container")) {
				//TODO
			}
		}
	}
	
	private void fireSearchFoundEvent(Object object) {
		List<EventHandler> handlers = super.getEventHandlers();
		if (handlers == null) return;
		for (EventHandler h: handlers) {
			h.onSearchFound(h.getHandlerClass(), object);
		}
	}
	
	/*public void addEventHandler(EventHandler handler) {
		if (handlers == null) {
			handlers = new ArrayList<EventHandler>();
		}
		handlers.add(handler);
	}*/
	
	public static abstract class EventHandler extends ComponentEventHandler {
		public EventHandler(Class<?> handlerClass) {
			super(handlerClass);
		}
		
		public abstract void onSearchFound(Class<?> handlerClass, Object object);
	}
}
