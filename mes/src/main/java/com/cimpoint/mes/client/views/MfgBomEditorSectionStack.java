/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.CSectionStack;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class MfgBomEditorSectionStack extends EditorSectionStack {

	private MfgBomEditor mfgBomEditor;
	
	public MfgBomEditorSectionStack(String width, String height) {
		super(width, height);
	}

	public void addGridPartSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE | CSectionStack.BUTTON_ADD | CSectionStack.BUTTON_REMOVE;
		boolean expanded = false;
		TreeGridField idField = new TreeGridField("id", "id");
		idField.setHidden(true);
		TreeGridField partField = new TreeGridField("partName", "Part");		
		TreeGridField partRevisionField = new TreeGridField("partRevision", "Rev");
		TreeGridField routingField = new TreeGridField("routing", "Routing");
		TreeGridField routingRevisionField = new TreeGridField("routingRevision", "Rev");
		TreeGridField stepField = new TreeGridField("step", "Step");
		TreeGridField materialField = new TreeGridField("material", "Material I/O");
		TreeGridField qtyField = new TreeGridField("qty", "Qty");
		TreeGridField uomField = new TreeGridField("uom", "UoM");
		this.addTreeGridSection(name, title, new TreeGridField[] { partField, partRevisionField, routingField, routingRevisionField, stepField, materialField, qtyField, uomField }, buttonsBitwise, expanded);
	}

	@Override
	public void onGridRecordAdd(String sectionName, ListGrid grid, CallbackHandler<ListGridRecord> callback) {
		// TODO Auto-generated method stub
		if (sectionName.equals("bomItems")) {
			MfgBomItemEditor mfgBomItemEditor = new MfgBomItemEditor(mfgBomEditor);
			mfgBomItemEditor.show();
		}
	}
	
	
	
	
}
