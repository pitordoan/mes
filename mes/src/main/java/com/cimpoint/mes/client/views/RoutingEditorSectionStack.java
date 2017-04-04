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
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.objects.Step;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RoutingEditorSectionStack extends EditorSectionStack {
	private RoutingEditor routingEditor;
		
	public RoutingEditorSectionStack(RoutingEditor routingEditor, String width, String height) {
		super(width, height);
		this.routingEditor = routingEditor;
	}
	
	public void addStepGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE | CSectionStack.BUTTON_ADD | CSectionStack.BUTTON_REMOVE;
		boolean expanded = false;
		ListGridField nameField = new ListGridField("name", "Name");
		ListGridField descField = new ListGridField("description", "Description");
		this.addGridSection(name, title, new ListGridField[] { nameField, descField }, buttonsBitwise, expanded);
	}
	
	public void addTransitionsGridSection(String name, String title) {
		ListGridField fromField = new ListGridField("from", "From Step:Op");  
	    ListGridField toField = new ListGridField("to", "To Step:Op");  
	    ListGridField transitionField = new ListGridField("name", "Transition");  
	    ListGridField reasonField = new ListGridField("reasons", "Reasons");  
	    this.addGridSection(name, title, new ListGridField[] {fromField, toField, transitionField, reasonField});	
	}
	    
	@Override
	public void onGridRecordAdd(String sectionName, final ListGrid grid, final CallbackHandler<ListGridRecord> callback) {
		if (this.routingEditor.validateForm()) {
			if (sectionName.equals("steps")) {
				StepEditor stepEditor = new StepEditor(this.routingEditor, this, grid, null);		
				stepEditor.show();
			}
			else if (sectionName.equals("transitions")) {
				TransitionEditor transitionEditor = new TransitionEditor(this.routingEditor, false, this, grid);			
				transitionEditor.show();
			} else if (sectionName.equals("customAttributes")) {
				int nCols = grid.getFields().length;
				for (int col = 0; col < nCols; col++) {
					TextItem fieldEditor = new TextItem();
					grid.getField(col).setEditorType(fieldEditor);
				}
	
				CustomAttributesRecord record = new CustomAttributesRecord();
				callback.onSuccess(record);
			}
		}
	}
	
	@Override
	public GridRecordEditor getGridRecordEditor(String sectionName, ListGrid grid) {
//		if (sectionName.equals("steps")) {
//			return new StepEditor(routingEditor, false, this, grid);
//		}
		
		return null;
	}
	
	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid, GridRecordEditor editor) {
		if (sectionName.equals("steps")) {
			Step selStep = findGridSelectedStep("steps");
			if (selStep != null) {
				StepEditor stepEditor = new StepEditor(routingEditor, this, grid, selStep);
				stepEditor.show();
			}
		} else if (sectionName.equals("transitions")) {
			TransitionEditor transitionEditor = new TransitionEditor(routingEditor, true, this, grid);
			transitionEditor.show();
		}
	}
}
