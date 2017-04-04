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

package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.AppView;
import com.cimpoint.common.views.EditorContainerView;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;

public class DatabaseConnectionsView extends AppView {

	// this is the main layout of change request
	private HLayout hlayoutContent;

	private ListGrid gridConnections;
	
	private DatabaseEditor editor;
	private EditorContainerView editorContainerView;
	
	public void createView() {
		// TODO create GUI here
		hlayoutContent = new HLayout(5);

		gridConnections = new ListGrid();
		gridConnections.setHeight(200);
		gridConnections.setWidth(250);
		ListGridField connField = new ListGridField("connection", "Connections");
		gridConnections.setFields(connField);

		editor = new DatabaseEditor();
		editorContainerView = new EditorContainerView();
		
		hlayoutContent.setMembers(gridConnections, editorContainerView);
		super.addMember(hlayoutContent);
		
	}

	@Override
	public void onInitialize(final CallbackHandler<Void> callback) {
		createView();
		editor.onInitialize(new CallbackHandler<Void>() {

			@Override
			public void onSuccess(Void result) {
				editorContainerView.setPropertiesEditor(editor);
			}
		});
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {

	}
	
}
