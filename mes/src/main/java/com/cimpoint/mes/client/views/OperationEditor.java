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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Operation;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

public class OperationEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private OperationEditorSectionStack sectionStack;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private ListingPanel listingPanel;
		
	@Override
	public String getTitle() {
		return "Operation Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub

	}

	private void createView() {
		if (viewCreated) return;
		
		this.setWidth(624);
		
		VStack vStack = new VStack();
		vStack.setLeft(30);
		vStack.setHeight100();
		
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight("100%");
		dynamicForm.setWrapItemTitles(false);
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		//end form
		
		sectionStack = new OperationEditorSectionStack("500px", "*");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, 
				sectionStackCI, getDefaultActionButtonsCanvasItem() });
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
		
		final String opName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		
		routingController.findOperationByName(opName, new CallbackHandler<Operation>() {
			public void onSuccess(Operation op) {
				if (op == null) {
					String comment = "Create operation";
					routingController.createOperation(opName, desc, customAttributes, comment, new CallbackHandler<Operation>() {
						public void onSuccess(Operation op) {
							listingPanel.addSectionTreeNode("operationsSection", opName, opName, "pieces/16/piece_green.png");
						}

					});
				} else {
					op.setName(opName);
					op.setDescription(desc);
					final Operation existingOperation = op;
					String comment = "Update operation";
					routingController.saveOperation(existingOperation, comment, new CallbackHandler<Void>() {
						public void onSuccess(Void result) {
							listingPanel.updateSectionTreeNode("operationsSection", existingOperation.getName(), existingOperation.getName(),
									"pieces/16/piece_green.png");
						}

					});
				}
			}
		}); // end of findOperationByName
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String name = nameTextItem.getValueAsString();
		routingController.findOperationByName(name, new CallbackHandler<Operation>() {
			@Override
			public void onSuccess(Operation op) {
				if (op != null) {
					String comment = "Remove operation";
					routingController.removeOperation(op, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("operationsSection", name);
							resetForm();
						}			
					});
				}
			}			
		});
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Operation...")) {
			resetForm();
		}
		else {
			routingController.findOperationByName(treeNodeText, new CallbackHandler<Operation>() {
				public void onSuccess(Operation op) {
					setForm(op);
				}				
			});
		}		
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter operation name");

		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		MESApplication.getMESControllers().getRoutingController().findAllWorkCenterNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> wcNames) {
				sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);
			}
		});
	}
	
	private void setForm(final Operation op) {
		MESApplication.getMESControllers().getRoutingController().findAllWorkCenterNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(final Set<String> wcNames) {
				nameTextItem.setValue(op.getName());
				descTextItem.setValue(op.getDescription());		
					
				CustomAttributes customAttrs = op.getCustomAttributes();
				sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
				
			}				
		});
	}
}
