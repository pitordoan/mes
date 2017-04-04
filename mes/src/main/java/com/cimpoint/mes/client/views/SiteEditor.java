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
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Site;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class SiteEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SiteEditorSectionStack sectionStack;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();		
	private ListingPanel listingPanel;
		
	@Override
	public String getTitle() {
		return "Site Editor";
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
		dynamicForm.setPadding(5);  
		
		nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		//end form
								
		sectionStack = new SiteEditorSectionStack("500px", "*");
		sectionStack.addNameDescriptionGridSection("areas", "Areas");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, sectionStackCI, getDefaultActionButtonsCanvasItem() });
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
		
		final String siteName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final Set<Area> areas = sectionStack.getGridSectionDataAsAreaSet("areas");
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");
		
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			public void onSuccess(final Site site) {
				if (site == null) {
					String comment = "Create site";
					routingController.createSite(siteName, desc, areas, customAttributes, comment, new CallbackHandler<Site>() {
						public void onSuccess(Site site) {
							Set<Area> newAreas = site.getAreas();
							sectionStack.setGridAreasData("areas", newAreas); //update GUI							
							listingPanel.addSectionTreeNode("sitesSection", site.getName(), site.getName(), "pieces/16/piece_green.png");		
						}						
					});
				}
				else {
					site.setName(siteName);
					site.setDescription(desc);
					site.setAreas(areas);	
					site.setCustomAttributes(customAttributes);
					
					String comment = "Update site";
					routingController.saveSite(site, comment, new CallbackHandler<Void>() {
						public void onSuccess(Void result) {
							Set<Area> newAreas = site.getAreas();
							sectionStack.setGridAreasData("areas", newAreas); //update GUI
							listingPanel.updateSectionTreeNode("sitesSection", site.getName(), site.getName(), "pieces/16/piece_green.png");		
						}
						
					});
				}
			}
			
		});
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String siteName = nameTextItem.getValueAsString();
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			@Override
			public void onSuccess(Site site) {
				if (site != null) {
					String comment = "Remove site";
					routingController.removeSite(site, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("sitesSection", siteName);
							resetForm();
						}			
					});
				}
			}			
		});
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Site...")) {
			resetForm();
		}
		else {
			routingController.findSiteByName(treeNodeText, new CallbackHandler<Site>() {
				public void onSuccess(Site site) {
					setForm(site);
				}				
			});
		}
	}
	
	private void resetForm() {
		nameTextItem.clearValue();
		nameTextItem.setPrompt("Enter site name");
		
		descTextItem.clearValue();
		descTextItem.setPrompt("Enter description (optional)");
		
		sectionStack.setGridData("areas", new ListGridRecord[0]);
		sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);		
	}
	
	private void setForm(Site site) {
		nameTextItem.setValue(site.getName());
		descTextItem.setValue(site.getDescription());		
		
		Set<Area> areas = site.getAreas();
		sectionStack.setGridAreasData("areas", areas);
		
		CustomAttributes customAttrs = site.getCustomAttributes();
		sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
	}
}
