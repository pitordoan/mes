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
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class ProductionLineEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private ProductionLineEditorSectionStack sectionStack;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();		
	private ListingPanel listingPanel;
	private SelectItem areaSelectItem;
		
	@Override
	public String getTitle() {
		return "Production Line Editor";
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
		if (viewCreated) {
			sectionStack.reset();
			return;
		}
		
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
		
		areaSelectItem = new SelectItem("area", "Area");
		areaSelectItem.setWidth(200);
		//end form
						
		sectionStack = new ProductionLineEditorSectionStack("500px", "*");
		sectionStack.addNameDescriptionGridSection("workCenters", "Work Centers");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, areaSelectItem,  sectionStackCI, getDefaultActionButtonsCanvasItem() });
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
		
		final String pdlName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final Set<WorkCenter> workCenters = sectionStack.getGridSectionDataAsWorkCenterSet("workCenters");
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		
		routingController.findAreaByName(areaSelectItem.getValueAsString(), new CallbackHandler<Area>() {
			@Override
			public void onSuccess(final Area area) {
				routingController.findProductionLineByName(pdlName, new CallbackHandler<ProductionLine>() {
					public void onSuccess(final ProductionLine pdl) {
						if (pdl == null) {
							String comment = "Create production line";
							routingController.createProductionLine(pdlName, desc, area, workCenters, customAttributes, comment, new CallbackHandler<ProductionLine>() {
								@Override
								public void onSuccess(ProductionLine pdl) {
									listingPanel.addSectionTreeNode("productionLinesSection", pdl.getName(), pdl.getName(), "pieces/16/piece_green.png");
								}
							});											
						}
						else {
							pdl.setName(pdlName);
							pdl.setDescription(desc);
							pdl.setArea(area);
							pdl.setWorkCenters(workCenters);
							String comment = "Update production line";
							
							routingController.saveProductionLine(pdl, comment, new CallbackHandler<Void>() {
								public void onSuccess(Void result) {
									try {
										listingPanel.updateSectionTreeNode("areasSection", pdl.getName(), pdl.getName(), "pieces/16/piece_green.png");	
									} catch (Exception ex) {
										MESApplication.showError(ex.getMessage());
									}
								}
							});
						}
					}
					
				});
			}			
		});
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String pdlName = nameTextItem.getValueAsString();
		routingController.findProductionLineByName(pdlName, new CallbackHandler<ProductionLine>() {
			@Override
			public void onSuccess(ProductionLine pdl) {
				if (pdl != null) {
					String comment = "Remove production line";
					routingController.removeProductionLine(pdl, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("productionLinesSection", pdlName);
							resetForm();
						}			
					});
				}
			}			
		});
	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Production Line...")) {
			resetForm();
		}
		else {
			routingController.findProductionLineByName(treeNodeText, new CallbackHandler<ProductionLine>() {
				public void onSuccess(ProductionLine pdl) {
					setForm(pdl);
				}				
			});
		}
	}
		
	private void resetForm() {
		MESApplication.getMESControllers().getRoutingController().findAllAreaNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> areaNames) {
				if (areaNames != null && !areaNames.isEmpty()) {
					areaNames.add("");
					String[] sortedAreaNames = Utils.toSortedStringArray(areaNames);
					areaSelectItem.setValueMap(sortedAreaNames);	
				}
				
				nameTextItem.clearValue();
				nameTextItem.setPrompt("Enter production line name");
				
				descTextItem.clearValue();
				descTextItem.setPrompt("Enter description (optional)");
				
				areaSelectItem.setValue((String) null);
						
				sectionStack.setGridData("workCenters", new ListGridRecord[0]);
				sectionStack.setGridData("customAttributes", new ListGridRecord[0]);	
			}				
		});	
	}
	
	private void setForm(final ProductionLine productionLine) {
		MESApplication.getMESControllers().getRoutingController().findAllAreaNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> areaNames) {
				if (areaNames != null && !areaNames.isEmpty()) {
					areaNames.add("");
					String[] sortedAreaNames = Utils.toSortedStringArray(areaNames);
					areaSelectItem.setValueMap(sortedAreaNames);	
				}

				nameTextItem.setValue(productionLine.getName());
				descTextItem.setValue(productionLine.getDescription());		
				
				String areaName = (productionLine.getArea() != null)? productionLine.getArea().getName() : null;
				areaSelectItem.setValue((String) areaName);		
				
				Set<WorkCenter> wcs = productionLine.getWorkCenters();
				sectionStack.setGridWorkCentersData("workCenters", wcs);
				
				CustomAttributes customAttrs = productionLine.getCustomAttributes();
				sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
			}				
		});
	}
}
