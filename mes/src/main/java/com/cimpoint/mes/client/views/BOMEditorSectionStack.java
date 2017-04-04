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

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.client.objects.BomItemNode;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class BOMEditorSectionStack extends EditorSectionStack {

	private BOMEditor bomEditor;
	
	public BOMEditorSectionStack(BOMEditor bomEditor, String width, String height) {
		super(width, height);
		this.bomEditor = bomEditor;
	}

	public void addGridPartSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_SINGLE | CSectionStack.BUTTON_ADD | CSectionStack.BUTTON_REMOVE;
		boolean expanded = false;
		TreeGridField idField = new TreeGridField("id", "id");
		idField.setHidden(true);
		TreeGridField nameField = new TreeGridField("partName", "Part");				
		TreeGridField revisionField = new TreeGridField("revision", "Revision");
		this.addTreeGridSection(name, title, new TreeGridField[] { idField, nameField, revisionField }, buttonsBitwise, expanded);
	}

	@Override
	public void onGridRecordAdd(String sectionName, ListGrid grid, CallbackHandler<ListGridRecord> callback) {
		// TODO Auto-generated method stub
		if (sectionName.equals("part")) {
			BomItemEditor partEditor = new BomItemEditor(bomEditor);
			partEditor.show();
		}
	}


	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid, GridRecordEditor editor) {
		if (sectionName.equals("part")) {
			BomItemEditor partEditor = new BomItemEditor(bomEditor);
			partEditor.show();
		}
	}
	
	public Set<BomItem> getGridDataBomItemSet(Tree data) {
		Set<BomItem> bomItems = new HashSet<BomItem>();
		
		// get bom items from grid
		TreeNode[] nodes = data.getAllNodes();
		for (int i = 0; i < nodes.length; i++) {
			BomItemNode node = (BomItemNode)nodes[i];
			bomItems.add(node.getBomItem());
		}
		
		return bomItems;
	}
}
