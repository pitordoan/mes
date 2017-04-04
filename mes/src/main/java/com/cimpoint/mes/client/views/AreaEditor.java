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
import com.cimpoint.mes.client.objects.Site;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class AreaEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SelectItem siteSelectItem;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();		
	private ListingPanel listingPanel;
	private AreaEditorSectionStack sectionStack;
			
	@Override
	public String getTitle() {
		return "Area Editor";
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
		
		siteSelectItem = new SelectItem("site", "Site");
		siteSelectItem.setWidth(200);
		siteSelectItem.setRequired(false);
		//end form
				
		sectionStack = new AreaEditorSectionStack("500px", "*");		
		sectionStack.addNameDescriptionGridSection("productionLines", "Production Lines");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, siteSelectItem, sectionStackCI, getDefaultActionButtonsCanvasItem() });
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
		
		final String areaName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final Set<ProductionLine> productionLines = sectionStack.getGridSectionDataAsProductionLineSet("productionLines");
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		
		routingController.findSiteByName(siteSelectItem.getValueAsString(), new CallbackHandler<Site>() {
			@Override
			public void onSuccess(final Site site) {
				routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
					public void onSuccess(final Area area) {
						if (area == null) {
							String comment = "Create Area";
							routingController.createArea(areaName, desc, site, productionLines, customAttributes, comment, new CallbackHandler<Area>() {
								public void onSuccess(Area area) {
									try {										
										listingPanel.addSectionTreeNode("areasSection", areaName, areaName, "pieces/16/piece_green.png");
									} catch (Exception ex) {
										MESApplication.showError(ex.getMessage());
									}
								}
								
							});
						}
						else {
							area.setName(areaName);
							area.setDescription(desc);
							area.setSite(site);
							area.setProductionLines(productionLines);
							String comment = "Update site";
							
							routingController.saveArea(area, comment, new CallbackHandler<Void>() {
								public void onSuccess(Void result) {
									try {
										listingPanel.updateSectionTreeNode("areasSection", area.getName(), area.getName(), "pieces/16/piece_green.png");	
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
		final String name = nameTextItem.getValueAsString();
		routingController.findAreaByName(name, new CallbackHandler<Area>() {
			@Override
			public void onSuccess(Area area) {
				if (area != null) {
					String comment = "Remove area";
					routingController.removeArea(area, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("areasSection", name);
							resetForm();
						}			
					});
				}
			}			
		});
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Area...")) {
			resetForm();
		}
		else {
			routingController.findAreaByName(treeNodeText, new CallbackHandler<Area>() {
				public void onSuccess(Area area) {
					setForm(area);
				}				
			});
		}
	}
		
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter area name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		routingController.findAllSiteNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> siteNames) {
				if (siteNames != null && !siteNames.isEmpty()) {
					siteNames.add("");
					String[] sortedSiteNames = Utils.toSortedStringArray(siteNames);
					siteSelectItem.setValueMap(sortedSiteNames);	
				}
	
				siteSelectItem.setValue((String)null);
			}			
		});
		
		sectionStack.setGridData("productionLines", new ListGridRecord[0]);
		sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);
	}
	
	private void setForm(final Area area) {
		MESApplication.getMESControllers().getRoutingController().findAllSiteNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> siteNames) {
				if (siteNames != null && !siteNames.isEmpty()) {
					siteNames.add("");
					String[] sortedSiteNames = Utils.toSortedStringArray(siteNames);
					siteSelectItem.setValueMap(sortedSiteNames);	
				}
				
				nameTextItem.setValue(area.getName());
				descTextItem.setValue(area.getDescription());
				
				String siteName = (area.getSite() != null)? area.getSite().getName() : null;
				siteSelectItem.setValue((String) siteName);	
				
				Set<ProductionLine> prodLines = area.getProductionLines();
				sectionStack.setGridProductionLinesData("productionLines", prodLines);
				
				CustomAttributes customAttrs = area.getCustomAttributes();
				sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
			}				
		});
	}
}
