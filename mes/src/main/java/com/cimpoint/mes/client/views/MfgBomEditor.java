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
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.objects.BomItemNode;
import com.cimpoint.mes.client.objects.MfgBomItemNode;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

public class MfgBomEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private TextItem revisionTextItem;
	private DateItem startDate;
	private DateItem endDate;
	private boolean viewCreated = false;
	private BillOfMarterialController billOfMarterialControllerer = MESApplication.getMESControllers().getBillOfMarterialController();
	private MfgBomEditorSectionStack sectionStack;
	private ListingPanel listingPanel;
	private Tree data = new Tree();
		
	@Override
	public String getTitle() {
		return "Manufacturing BOM Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();			
		callback.onSuccess((Void)null);
	}

	private void createView() {
		if (viewCreated) {
			return;
		}
		
		VStack vStack = new VStack();
		vStack.setLeft(50);
		vStack.setHeight100();

		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setSize("100%", "100%");
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setPadding(5);  
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		
		revisionTextItem = new TextItem("revision", "Revision");
		revisionTextItem.setWidth(200);
		revisionTextItem.setLength(50);
		revisionTextItem.setRequired(true);
		
		startDate = new DateItem();  
		startDate.setName("startDate");  
		startDate.setTitle("Effective Start Date");  
	    startDate.setRequired(true);  
	    startDate.setWidth(200);
		
	    endDate = new DateItem();  
	    endDate.setName("endDate");  
	    endDate.setTitle("Effective End Date");  
	    endDate.setRequired(true);  
	    endDate.setWidth(200);
		
		sectionStack = new MfgBomEditorSectionStack("500px", "*");
		sectionStack.addGridPartSection("bomItems", "Bom Items");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Bom Items");
	    
		//end form
						
		RowSpacerItem rowSpacer = new RowSpacerItem();
		rowSpacer.setHeight(3);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, revisionTextItem, startDate, endDate, sectionStackCI, rowSpacer, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
	}
	
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		// TODO Auto-generated method stub
		
	}

	public void addMfgBomItem(MfgBomItemNode newNode) {
		String parentId = newNode.getParentId();
		TreeNode parent = data.findById(parentId);
		if (parent != null) {
			data.add(newNode, parent);
		} else {
			data.add(newNode, data.getRoot());
		}
	}
	
	public void setDataForGrid() {
		sectionStack.setTreeGridData("part", data);
	}
}
