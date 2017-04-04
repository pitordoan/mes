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

import com.smartgwt.client.widgets.tab.Tab;

/**
 * @author Tai Huynh
 * */
public class CCustomTab extends Tab{

	private boolean isActive;
//	private WOTrackingDetails tabpan;

	public CCustomTab() {
		super();
		isActive = true;
		createView();
	}

	public CCustomTab(String title) {
		super(title);
		isActive = true;
		createView();
	}
	
	public void createView(){
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	/*public WOTrackingDetails getTabpan() {
		return tabpan;
	}
	
	public void setTabpan(WOTrackingDetails tabpan) {
		this.tabpan = tabpan;
	}*/
	
}
