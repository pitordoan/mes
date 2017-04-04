
/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import java.util.ArrayList;
import java.util.List;

import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
public class WOTrackingSearchPanel extends HLayout {

	
	//menu items
	private MenuItem itemWorkOrder;
	private MenuItem itemWorkOrderItem;
	private MenuItem itemLot;
	private MenuItem itemUnit;
	private MenuItem itemBatch;  
	private MenuItem itemContainer;
	
	private boolean isActive;
	private Menu menu;
	IMenuButton menuButton;
	
	//	private Button btnSearchLot;
	private Button btnGo;
	private CTextField cimSearchTxt;

	public WOTrackingSearchPanel(int elementMargin) {
		super(elementMargin);
		isActive = true;
		createView();
	}

	public WOTrackingSearchPanel() {
		super();
		createView();
	}

	public void createView(){
		this.setMargin(5);
		this.setHeight(15);
		cimSearchTxt = new CTextField("");

		menu = new Menu();
		menu.setWidth(150);
		menu.setShowShadow(true);  
		menu.setShadowDepth(10);  
		itemWorkOrder = new MenuItem(MESConstants.WORK_ORDER);
		itemWorkOrderItem = new MenuItem(MESConstants.WORK_ORDER_ITEM);
		itemLot = new MenuItem(MESConstants.LOT);
		itemUnit = new MenuItem(MESConstants.UNIT);
		itemBatch = new MenuItem(MESConstants.BATCH);  
		itemContainer = new MenuItem(MESConstants.CONTAINER);
		List<MenuItem> allItems = new ArrayList<MenuItem>();
		allItems.add(itemWorkOrder);
		allItems.add(itemWorkOrderItem);
		allItems.add(itemLot);
		allItems.add(itemUnit);
		allItems.add(itemBatch);
		allItems.add(itemContainer);
		
		menuButton = new IMenuButton(MESConstants.SEARCH + MESConstants.LOT + MESConstants.SEARCH_DOT, menu);  
		
		for (MenuItem menuItem : allItems) {
			
			menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				public void onClick(MenuItemClickEvent event) {
					String itemTitle = event.getItem().getTitle();
					menuButton.setTitle(MESConstants.SEARCH + itemTitle + MESConstants.SEARCH_DOT);
				}
			});
		}
		menu.setItems(itemWorkOrder, itemWorkOrderItem, itemLot, itemUnit, itemBatch, itemContainer);
		menuButton.setWidth(180);
//		menuButton.setAutoWidth();
		//menuButton.setOverflow(Overflow.CLIP_V); //not supported by IE9
		menuButton.setHeight(MESConstants.BUTTON_HEIGHT);
		menuButton.draw();  

		btnGo = new Button("Go");
		btnGo.setHeight(MESConstants.BUTTON_HEIGHT);
		
		this.addMember(menuButton);
		this.addMember(cimSearchTxt);  
		this.addMember(btnGo);  
	}

	public MenuItem getItemWorkOrder() {
		return itemWorkOrder;
	}

	public void setItemWorkOrder(MenuItem itemWorkOrder) {
		this.itemWorkOrder = itemWorkOrder;
	}

	public MenuItem getItemWorkOrderItem() {
		return itemWorkOrderItem;
	}

	public void setItemWorkOrderItem(MenuItem itemWorkOrderItem) {
		this.itemWorkOrderItem = itemWorkOrderItem;
	}

	public MenuItem getItemLot() {
		return itemLot;
	}

	public void setItemLot(MenuItem itemLot) {
		this.itemLot = itemLot;
	}

	public MenuItem getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(MenuItem itemUnit) {
		this.itemUnit = itemUnit;
	}

	public MenuItem getItemBatch() {
		return itemBatch;
	}

	public void setItemBatch(MenuItem itemBatch) {
		this.itemBatch = itemBatch;
	}

	public MenuItem getItemContainer() {
		return itemContainer;
	}

	public void setItemContainer(MenuItem itemContainer) {
		this.itemContainer = itemContainer;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Button getBtnGo() {
		return btnGo;
	}

	public CTextField getCimSearchTxt() {
		return cimSearchTxt;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public IMenuButton getMenuButton() {
		return menuButton;
	}

}
