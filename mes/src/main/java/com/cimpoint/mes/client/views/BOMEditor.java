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
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.objects.Bom;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.client.objects.BomItemNode;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils.Dates;
import com.smartgwt.client.types.TreeModelType;
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

public class BOMEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private TextItem revisionTextItem;
	private DateItem startDate;
	private DateItem endDate;
	private boolean viewCreated = false;
	private BillOfMarterialController billOfMarterialControllerer = MESApplication.getMESControllers().getBillOfMarterialController();
	private BOMEditorSectionStack sectionStack;
	private ListingPanel listingPanel;
	private Tree data = new Tree();
		
	@Override
	public String getTitle() {
		return "BOM Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();			
		callback.onSuccess((Void)null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub

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
		
		sectionStack = new BOMEditorSectionStack(this, "500px", "*");
		sectionStack.addGridPartSection("part", "Part");
		
		
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Part");
	    
		//end form
						
		RowSpacerItem rowSpacer = new RowSpacerItem();
		rowSpacer.setHeight(3);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, revisionTextItem, startDate, endDate, sectionStackCI, rowSpacer, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
		
		// initialize tree
		configTree();
	}
	
	@Override
	public void onNewButtionClicked(ClickEvent event) {
		resetForm();
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
		
		final String name = nameTextItem.getValueAsString();
		final String revision = revisionTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final String start = Dates.toString(startDate.getValueAsDate(), Dates.Date);
		final String end = Dates.toString(endDate.getValueAsDate(), Dates.Date);
		final Set<BomItem> bomItems = sectionStack.getGridDataBomItemSet(data);
		
		billOfMarterialControllerer.findBomByNameAndRevision(name, revision, new CallbackHandler<Bom>() {

			@Override
			public void onSuccess(final Bom bom) {
				if (bom == null) {
					// create new bom
					String comment = "Create new Bom";
					
					billOfMarterialControllerer.createBom(name, desc, revision, start, end, bomItems, comment, new CallbackHandler<Bom>() {
						@Override
						public void onSuccess(Bom newBom) {
							String nodeName = newBom.getName() + MESConstants.REV_FOR_NAME + newBom.getRevision();
							String text = newBom.getName() + MESConstants.REV_FOR_TEXT + newBom.getRevision();
							listingPanel.addSectionTreeNode("bomsSection", nodeName, text, "pieces/16/piece_green.png");
						}
					});
					
				} else {
					// update bom
					bom.setName(name);
					bom.setDescription(desc);
					bom.setRevision(revision);
					bom.setStartDate(start);
					bom.setEndDate(end);
					bom.setBomItems(bomItems);
					String comment = "Update bom";
					billOfMarterialControllerer.saveBom(bom, comment, new CallbackHandler<Void>() {

						@Override
						public void onSuccess(Void result) {
							try {
								String nodeName = bom.getName() + MESConstants.REV_FOR_NAME + bom.getRevision();
								String text = bom.getName() + MESConstants.REV_FOR_TEXT + bom.getRevision();
								listingPanel.updateSectionTreeNode("bomsSection", nodeName, text, "pieces/16/piece_green.png");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String name = nameTextItem.getValueAsString();
		final String revision = revisionTextItem.getValueAsString();
		billOfMarterialControllerer.findBomByNameAndRevision(name, revision, new CallbackHandler<Bom>() {

			@Override
			public void onSuccess(final Bom bom) {
				if ( bom != null ) {
					String comment = "Remove Bom";
					billOfMarterialControllerer.removeBom(bom, comment, new CallbackHandler<Void>() {

						@Override
						public void onSuccess(Void result) {
							String nodeName = bom.getName() + MESConstants.REV_FOR_NAME + bom.getRevision();
							listingPanel.removeSectionTreeNode("bomsSection", nodeName);
							resetForm();
						}
					});
				}
			}
		});
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New BOM...")) {
			resetForm();
		}
		else {
			String[] tokens = treeNodeText.split(MESConstants.REV_FOR_TEXT);
			String bomName = tokens[0].trim();
			String revision = tokens[1].trim();
			billOfMarterialControllerer.findBomByNameAndRevision(bomName, revision, new CallbackHandler<Bom>() {

				@Override
				public void onSuccess(Bom bom) {
					setForm(bom);
				}
			});
		}
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter bom name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		revisionTextItem.clearValue();
		revisionTextItem.setPrompt("Enter bom revision");
		
		data.removeList(data.getAllNodes());
		setDataForGrid();
	}
	
	// load data when clicking on existing bom
	private void setForm(final Bom bom) {
		resetForm();
		
		if ( bom != null ) {
			nameTextItem.setValue(bom.getName());
			descTextItem.setValue(bom.getDescription());
			revisionTextItem.setValue(bom.getRevision());
			startDate.setValue(bom.getStartDate());
			endDate.setValue(bom.getEndDate());
			
			Set<BomItem> bomItemLst = bom.getBomItems();
			if ( bomItemLst == null) {
				bomItemLst = new HashSet<BomItem>();
			}
			
			// remove all nodes
			data.removeList(data.getAllNodes());
			
			// add root node first
			for (BomItem item : bomItemLst) {
				if (item.getContainerName().equals("")) {
					BomItemNode root = new BomItemNode(item, "");
					data.add(root, data.getRoot());
				}
			}
			
			
			// then add child nodes 
			for (BomItem item : bomItemLst) {
				if (!item.getContainerName().equals("")) {
					BomItemNode childNode = new BomItemNode(item, item.getContainerName() + MESConstants.REV_FOR_TEXT + item.getContainerRevision()); 
					addBomItem(childNode);
				}
			}
			sectionStack.setTreeGridData("part", data);
		}
	}
	
	// config tree data
	private void configTree() {
		data.setModelType(TreeModelType.PARENT);
		data.setNameProperty("partName");
		data.setIdField("id");
		data.setParentIdField("parentId");
		data.setRootValue("");
	}
	
	public void addBomItem(BomItemNode newNode) {
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
