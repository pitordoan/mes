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
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.filters.ProductionLineFilter;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

public class WorkCenterEditor extends PropertiesEditor {
	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SelectItem areaSelectItem;
	private SelectItem pdLineSelectItem;
	private WorkCenterEditorSectionStack sectionStack;
	private boolean viewCreated = false;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();	
	private ListingPanel listingPanel;
		
	@Override
	public String getTitle() {
		return "Work Center Editor";
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
		nameTextItem.setPrompt("Enter work center name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		descTextItem = new TextItem("desc", "Description");
		descTextItem.setPrompt("Enter description (optional)");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		
		areaSelectItem = new SelectItem("area", "Area");
		areaSelectItem.setPrompt("Select an area (optional)");
		areaSelectItem.setWidth(200);
		areaSelectItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				final String selAreaName = (String) event.getValue();
				pdLineSelectItem.setValue((String) null);
				if (selAreaName != null) {
					final ProductionLineFilter filter = new ProductionLineFilter();
					filter.whereAreaName().isEqual(selAreaName);
					MESApplication.getMESControllers().getRoutingController().findProductionLineNames(filter, new CallbackHandler<Set<String>>() {
						@Override
						public void onSuccess(Set<String> pdlNames) {
							loadProductionLineNames(pdlNames);
						}
					});
				} else {
					MESApplication.getMESControllers().getRoutingController().findAllProductionLineNames(new CallbackHandler<Set<String>>() {
						@Override
						public void onSuccess(Set<String> pdlNames) {
							loadProductionLineNames(pdlNames);
						}
					});
				}
			}
		});
		
		pdLineSelectItem = new SelectItem("productionLine", "Production Line");
		pdLineSelectItem.setPrompt("Select a production line (optional)");
		pdLineSelectItem.setWidth(200);
		pdLineSelectItem.setRequired(false);
		//end form
					
		sectionStack = new WorkCenterEditorSectionStack("500px", "*");
		sectionStack.addNameDescriptionGridSection("equipments", "Equipment");
		sectionStack.addNameValueGridSection("customAttributes", "Custom Attributes");
		CanvasItem sectionStackCI = sectionStack.toCanvasItem("Others");
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, areaSelectItem, pdLineSelectItem, sectionStackCI, 
				getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
	}
		
	private void loadProductionLineNames(Set<String> pdlNames) {
		if (pdlNames != null && pdlNames.size() > 0) {
			pdlNames.add("");
			String[] sortedPdlNames = Utils.toSortedStringArray(pdlNames);
			pdLineSelectItem.setValueMap(sortedPdlNames);
		}
		else {
			pdLineSelectItem.setValueMap((String) null);
		}
	}
	
	@Override
	public void onNewButtionClicked(ClickEvent event) {
		resetForm();
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;

		final String wcName = nameTextItem.getValueAsString();
		final String desc = descTextItem.getValueAsString();
		final String areaName = areaSelectItem.getValueAsString();
		final String prdLineName = pdLineSelectItem.getValueAsString();
		final Set<Equipment> equipments = sectionStack.getGridSectionDataAsEquipmentSet("equipments");
		final CustomAttributes customAttributes = sectionStack.getGridSectionDataAsCustomAttributes("customAttributes");

		routingController.findAreaByName(areaName, new CallbackHandler<Area>() {
			@Override
			public void onSuccess(final Area area) {
				routingController.findProductionLineByName(prdLineName, new CallbackHandler<ProductionLine>() {
					public void onSuccess(final ProductionLine prdLine) {
						routingController.findWorkCenterByName(wcName, new CallbackHandler<WorkCenter>() {
							public void onSuccess(WorkCenter wc) {
								if (wc == null) {
									String comment = "Create work center";
									routingController.createWorkCenter(wcName, desc, area, equipments, prdLine, customAttributes, comment,
											new CallbackHandler<WorkCenter>() {
												public void onSuccess(WorkCenter wc) {
													listingPanel.addSectionTreeNode("workCentersSection", wcName, wcName, "pieces/16/piece_green.png");
												}
											});
								} else {
									wc.setName(wcName);
									wc.setDescription(desc);
									wc.setArea(area);
									wc.setProductionLine(prdLine);
									final WorkCenter existingWorkCenter = wc;
									String comment = "Update work center";
									routingController.saveWorkCenter(existingWorkCenter, comment, new CallbackHandler<Void>() {
										public void onSuccess(Void result) {
											listingPanel.updateSectionTreeNode("workCentersSection", existingWorkCenter.getName(),
													existingWorkCenter.getName(), "pieces/16/piece_green.png");
										}
									});
								}
							}
						}); // end of findWorkCenterByName
					}
				}); // end of findProductionLineByName
			}
		}); // end of findAreaByName
	}
	
	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		final String name = nameTextItem.getValueAsString();
		routingController.findWorkCenterByName(name, new CallbackHandler<WorkCenter>() {
			@Override
			public void onSuccess(WorkCenter wc) {
				if (wc != null) {
					String comment = "Remove work center";
					routingController.removeWorkCenter(wc, comment, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							listingPanel.removeSectionTreeNode("workCentersSection", name);
							resetForm();
						}			
					});
				}
			}			
		});
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		if (treeNodeText != null && treeNodeText.equals("New Work Center...")) {
			resetForm();
		}
		else {
			routingController.findWorkCenterByName(treeNodeText, new CallbackHandler<WorkCenter>() {
				public void onSuccess(WorkCenter wc) {
					setForm(wc);
				}				
			});
		}
	}
	
	private void resetForm() {
		MESApplication.getMESControllers().getRoutingController().findAllAreaNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(final Set<String> areaNames) {
				MESApplication.getMESControllers().getRoutingController().findAllProductionLineNames(new CallbackHandler<Set<String>>() {
					@Override
					public void onSuccess(Set<String> pdlNames) {	
						if (areaNames != null && !areaNames.isEmpty()) {
							areaNames.add("");
							String[] sortedAreaNames = Utils.toSortedStringArray(areaNames);
							areaSelectItem.setValueMap(sortedAreaNames);	
						}
						
						if (pdlNames != null && !pdlNames.isEmpty()) {
							pdlNames.add("");
							String[] sortedPdlNames = Utils.toSortedStringArray(pdlNames);
							pdLineSelectItem.setValueMap(sortedPdlNames);	
						}
						
						nameTextItem.clearValue();
						nameTextItem.setPrompt("Enter production line name");
						
						descTextItem.clearValue();
						descTextItem.setPrompt("Enter description (optional)");
						
						areaSelectItem.setValue((String) null);
						
						pdLineSelectItem.setValue((String) null);
								
						sectionStack.setGridData("equipments", new ListGridRecord[0]);
						sectionStack.setGridCustomAttributesData("customAttributes", (CustomAttributes) null);	
					}
				});
			}				
		});
	}
	
	private void setForm(final WorkCenter workCenter) {
		MESApplication.getMESControllers().getRoutingController().findAllAreaNames(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(final Set<String> areaNames) {
				nameTextItem.setValue(workCenter.getName());
				descTextItem.setValue(workCenter.getDescription());		
				
				String areaName = (workCenter.getArea() != null)? workCenter.getArea().getName() : null;
				areaSelectItem.setValue((String) areaName);		
				
				String prdlineName = (workCenter.getProductionLine() != null)? workCenter.getProductionLine().getName() : null;
				pdLineSelectItem.setValue(prdlineName);
				
				Set<Equipment> equipments = workCenter.getEquipments();
				sectionStack.setGridEquipmentsData("equipments", equipments);
				
				CustomAttributes customAttrs = workCenter.getCustomAttributes();
				sectionStack.setGridCustomAttributesData("customAttributes", customAttrs);
				
				if (areaName != null) {
					ProductionLineFilter filter = new ProductionLineFilter();
					filter.whereAreaName().isEqual(areaName);
					MESApplication.getMESControllers().getRoutingController().findProductionLineNames(filter, new CallbackHandler<Set<String>>() {
						@Override
						public void onSuccess(Set<String> pdlNames) {	
							if (areaNames != null) {
								areaNames.add("");
								String[] sortedAreaNames = Utils.toSortedStringArray(areaNames);
								areaSelectItem.setValueMap(sortedAreaNames);	
							}
							
							if (pdlNames != null) {
								pdlNames.add("");
								String[] sortedPdlNames = Utils.toSortedStringArray(pdlNames);
								pdLineSelectItem.setValueMap(sortedPdlNames);	
							}
						}
					});
				}
			}				
		});
	}
}
