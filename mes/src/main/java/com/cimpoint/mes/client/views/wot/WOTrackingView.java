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

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.AppView;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class WOTrackingView extends AppView
{
	private boolean viewCreated = false;
	private ObjectHierarchyPanel objectHierarachyPanel;
	private SearchPanel searchPanel;
	private DataPanel dataPanel;
	private ControlPanel controlPanel;

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void) null);
	}

	private void createView() {
		if (viewCreated) return;
        
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setLayoutMargin(5);
		layout.setMembersMargin(5);
				
		VLayout middleColumnLayout = new VLayout();
		middleColumnLayout.setMembersMargin(5);
		middleColumnLayout.setWidth("*");
		middleColumnLayout.setHeight100();
		searchPanel = new SearchPanel("100%", "30px");
		dataPanel = new DataPanel("100%", "100%");		
		middleColumnLayout.setMembers(searchPanel, dataPanel);
        
		controlPanel = new ControlPanel("200px", "100%");
		
		objectHierarachyPanel = new ObjectHierarchyPanel("200px", "100%");	
		
		layout.setMembers(objectHierarachyPanel, middleColumnLayout, controlPanel);
		
		this.setMembersMargin(5);
		this.setStyleName("cimtrack-AppView");
		this.addChild(layout);
		
		viewCreated = true;
	}
	
	@Override
	public void onClose() {
		viewCreated = false;
	}

	public DataPanel getDataPanel() {
		return dataPanel;
	}
	
	public SearchPanel getSearchPanel() {
		return searchPanel;
	}
	
	public ObjectHierarchyPanel getObjectHierarachyPanel() {
		return objectHierarachyPanel;
	}
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
}
