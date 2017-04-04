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
import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

public class EquipmentEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SelectItem workCenterSelectItem;
//	private SelectItem operationSelectItem;
	private EquipmentEditorSectionStack sectionStack;
	private boolean viewCreated = false;
	private ListingPanel listingPanel;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	@Override
	public String getTitle() {
		return "Equipment Editor";
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
		
		workCenterSelectItem = new SelectItem("workCenter", "Work Center");
		workCenterSelectItem.setWidth(200);
		//end form
						
		sectionStack = new EquipmentEditorSectionStack("500px", "*");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, workCenterSelectItem,  
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
		
		final String eName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final String wcName = workCenterSelectItem.getValueAsString();
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		
		routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {

			@Override
			public void onSuccess(final WorkCenter wc) {
				routingController.findEquipmentByName(eName, new CallbackHandler<Equipment>() {

					@Override
					public void onSuccess(Equipment e) {
						if (e == null) {
							String comment = "Create equipment";
							routingController.createEquipment(eName, desc, wc, customAttributes, comment, new CallbackHandler<Equipment>() {

								@Override
								public void onSuccess(Equipment result) {
									try {
									listingPanel.addSectionTreeNode("equipmentsSection", eName, eName, "pieces/16/piece_green.png");
									} catch (Exception ex) {
										MESApplication.showError(ex.getMessage());
									}
								}
							});
						} else {
							e.setName(eName);
							e.setDescription(desc);
							e.setWorkCenter(wc);
							final Equipment existEquipment = e;
							String comment = "Update equipment";
							routingController.saveEquipment(existEquipment, comment, new CallbackHandler<Void>() {

								@Override
								public void onSuccess(Void result) {
									listingPanel.updateSectionTreeNode("equipmentsSection", existEquipment.getName(), 
											existEquipment.getName(), "pieces/16/piece_green.png");
								}
							});
						}
					}
				}); // end findEquipmentByname
			}
		}); // end findWorkCenterByName
		
		
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String eName = nameTextItem.getValueAsString();
		routingController.findEquipmentByName(eName, new CallbackHandler<Equipment>() {
			@Override
			public void onSuccess(Equipment equipment) {
				if (equipment != null) {
					String comment = "Remove Equipment";
					routingController.removeEquipment(equipment, comment, new CallbackHandler<Void>() {

						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("equipmentsSection", eName);
							resetForm();
						}
					});
				}
			}
		});
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Equipment...")) {
			resetForm();
		}
		else {
			routingController.findEquipmentByName(treeNodeText, new CallbackHandler<Equipment>() {
				@Override
				public void onSuccess(Equipment e) {
					setForm(e);
				}
			});
		}		
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter Equipment name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		MESApplication.getMESControllers().getRoutingController().findAllWorkCenterNames(new CallbackHandler<Set<String>>() {

			@Override
			public void onSuccess(Set<String> wcNames) {
				if (wcNames != null && !wcNames.isEmpty()) {
					wcNames.add("");
					String[] sortedWcNames = Utils.toSortedStringArray(wcNames);
					workCenterSelectItem.setValueMap(sortedWcNames);	
				}
				workCenterSelectItem.setValue((String)null);
				sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);
			}
		});
	}
	
	private void setForm(final Equipment equipment) {
		MESApplication.getMESControllers().getRoutingController().findAllWorkCenterNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(final Set<String> wcNames) {
				nameTextItem.setValue(equipment.getName());
				descTextItem.setValue(equipment.getDescription());		

				// set workcenter
				if (wcNames != null && !wcNames.isEmpty()) {
					wcNames.add("");
					String[] sortedWCNames = Utils.toSortedStringArray(wcNames);
					workCenterSelectItem.setValueMap(sortedWCNames);	
				}
				
				equipment.getWorkCenter(new CallbackHandler<WorkCenter>() {
					@Override
					public void onSuccess(WorkCenter wc) {
						String wcName = (wc != null) ? wc.getName() : null;
						workCenterSelectItem.setValue(wcName);
					}
				});
				
				CustomAttributes customAttrs = equipment.getCustomAttributes();
				sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
			}				
		});
		
	}
	
	
}
