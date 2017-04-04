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

import java.util.Date;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.common.MESConstants.Object.PartCatergory;
import com.cimpoint.mes.common.MESUtils.Dates;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

public class PartEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private TextItem revisionTextItem;
	private SelectItem categorySelectItem;
	private DateItem startDate;
	private DateItem endDate;
	private boolean viewCreated = false;
	private BillOfMarterialController billOfMarterialController = MESApplication.getMESControllers().getBillOfMarterialController();		
	private ListingPanel listingPanel;
		
	@Override
	public String getTitle() {
		return "Part Editor";
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
		
		categorySelectItem = new SelectItem("category", "Category");
		categorySelectItem.setWidth(200);
		categorySelectItem.setRequired(false);
		categorySelectItem.setValueMap(PartCatergory.Catergory1.toString(),
									   PartCatergory.Category2.toString(),
									   PartCatergory.Category3.toString());		
		
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
		//end form
						
		RowSpacerItem rowSpacer = new RowSpacerItem();
		rowSpacer.setHeight(3);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, revisionTextItem, categorySelectItem, startDate, endDate, rowSpacer, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
	}
	
	@Override
	public void onNewButtionClicked(ClickEvent event) {
		resetForm();
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
		
		final String partName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final String partRevision = revisionTextItem.getValueAsString();
		final PartCatergory category = PartCatergory.valueOf(categorySelectItem.getValueAsString());
		final String start = Dates.toString(startDate.getValueAsDate(), Dates.Date);
		final String end = Dates.toString(endDate.getValueAsDate(), Dates.Date);
		
		billOfMarterialController.findPartByNameAndRevision(partName, partRevision, new CallbackHandler<Part>() {
			@Override
			public void onSuccess(final Part part) {
				if (part == null) {
					String comment = "Create Part";
					
					billOfMarterialController.createPart(partName, desc, partRevision, null, null, category, null, start, end, comment, new CallbackHandler<Part>() {

						@Override
						public void onSuccess(Part part) {
							try {									
								if (part != null) {
									String nodeName = part.getName() + "Rev" + part.getRevision();
									String text = part.getName() + " - Rev" + part.getRevision();
									listingPanel.addSectionTreeNode("partsSection", nodeName, text, "pieces/16/piece_green.png");
								}
								
							} catch (Exception ex) {
								MESApplication.showError(ex.getMessage());
							}
						}
					});
					
				}
				else {
					part.setName(partName);
					part.setDescription(desc);
					part.setRevision(partRevision);
					part.setCategory(category);
					part.setEffectiveStartDate(start);
					part.setEffectiveEndDate(end);
					String comment = "Update Part";
					
					billOfMarterialController.savePart(part, comment, new CallbackHandler<Void>() {

						@Override
						public void onSuccess(Void result) {
							try {
								String nodeName = part.getName() + "Rev" + part.getRevision();
								String text = part.getName() + " - Rev" + part.getRevision();
								listingPanel.updateSectionTreeNode("partsSection", nodeName, text, "pieces/16/piece_green.png");	
							} catch (Exception ex) {
								MESApplication.showError(ex.getMessage());
							}							
						}
					});

				}						
			}	
		});
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String number = nameTextItem.getValueAsString();
		final String revision = revisionTextItem.getValueAsString();
		billOfMarterialController.findPartByNameAndRevision(number, revision, new CallbackHandler<Part>() {
			@Override
			public void onSuccess(final Part part) {
				if (part != null) {
					String comment = "Remove part";

					billOfMarterialController.removePart(part, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							String nodeName = part.getName() + "Rev" + part.getRevision();
							listingPanel.removeSectionTreeNode("partsSection", nodeName);
							resetForm();
						}			
					});
				}
			}			
		});
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Part...")) {
			resetForm();
		}
		else {
			String[] tokens = treeNodeText.split(" - Rev");
			String partNumber = tokens[0].trim();
			String revision = tokens[1].trim();
			billOfMarterialController.findPartByNameAndRevision(partNumber, revision, new CallbackHandler<Part>() {
				public void onSuccess(Part part) {
					setForm(part);
				}				
			});
		}
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter part name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		revisionTextItem.clearValue();
		revisionTextItem.setPrompt("Enter revision");
		
		categorySelectItem.setValue(" ");
	}
	
	private void setForm(Part part) {
		nameTextItem.setValue(part.getName());
		descTextItem.setValue(part.getDescription());
		revisionTextItem.setValue(part.getRevision());
		categorySelectItem.setValue(part.getCategory().toString());
		startDate.setValue(part.getEffectiveStartDate());
		endDate.setValue(part.getEffectiveEndDate());
	}
}
