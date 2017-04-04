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

package com.cimpoint.mes.client.views.editors;

import com.cimpoint.common.CallbackHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.CloseClickHandler;
import com.smartgwt.client.widgets.tab.events.TabCloseClickEvent;

public class EditorContainer extends HLayout {
	private TabSet modelViewTabSet;
	private ModelConfigView modelListingPanel;
	
	public EditorContainer(ModelConfigView modelConfigView) {
		this.modelListingPanel = modelConfigView;
		this.modelViewTabSet = new TabSet();
		this.modelViewTabSet.setTabBarThickness(25);
		this.modelViewTabSet.setPaneMargin(0);
		this.modelViewTabSet.addCloseClickHandler(new CloseClickHandler() {
	        public void onCloseClick(final TabCloseClickEvent event) {
	        	final Tab tab = event.getTab();	        	
	        	ModelEditor view = (ModelEditor) tab.getPane();	
	        	view.onClose(new CallbackHandler<Boolean>() {
					@Override
					public void onSuccess(Boolean proceed) {
						if (proceed) {
							try {
								modelViewTabSet.removeTab(tab);
								modelListingPanel.deselectAll();		
							} catch (Exception ex) {}
						}
					}	
	            }); 
	        	
	        	event.cancel();
	        }
	    });
		
		this.setMembersMargin(3);
		this.addMember(modelViewTabSet);
	}

	public Tab[] getTabs() {
		return modelViewTabSet.getTabs();
	}

	public void addTab(Tab tab) {
		modelViewTabSet.addTab(tab);
	}

	public void selectTab(Tab tab) {
		modelViewTabSet.selectTab(tab);
	}
	
	public void selectTab(String id) {
		modelViewTabSet.selectTab(id);
	}

	public boolean existTab(String id) {
		return modelViewTabSet.getTabNumber(id) != -1;
	}
	
	public void closeTab(String id) {
		if (existTab(id)) {
			modelViewTabSet.removeTab(id);
		}
	}

	public ModelEditor getEditor(String name) {
		Tab tab = modelViewTabSet.getTab(name);
		if (tab != null) {
			return (ModelEditor) tab.getPane();
		}
		throw new RuntimeException("Editor not found by name: " + name);
	}
}
