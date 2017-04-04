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
import com.cimpoint.common.Utils;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class MfgBomItemEditor extends GridRecordEditor {
	private DynamicForm dynamicForm;
	private DynamicForm consumForm;
	private DynamicForm produceForm;
	private SelectItem containerPartItem;
	private SelectItem partItem;
	private SelectItem revisionItem;
	private SelectItem comsumRoutingItem;
	private SelectItem consumRoutRevItem;
	private SelectItem consumStepItem;
	private SelectItem consumTypeItem;
	private TextItem consumQtyItem;
	private SelectItem consumeUomItem; // UoM : Unit of Measure
	
	private SelectItem produceRoutingItem;
	private SelectItem produceRoutRevItem;
	private SelectItem produceStepItem;
	private TextItem produceQtyItem;
	private SelectItem produceUomItem;
	private TabSet topTabSet;
	
	private BillOfMarterialController billOfMarterialController = MESApplication.getMESControllers().getBillOfMarterialController();
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	private MfgBomEditor mfgBomEditor;
	
	public MfgBomItemEditor(MfgBomEditor mfgBomEditor) {
		super("Manufacturing BOM Item Editor", 210, 140);
		
		this.mfgBomEditor = mfgBomEditor;
		
		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		vStack.setBorder("1px solid LightGray");
		
		topTabSet = new TabSet();  
        topTabSet.setTabBarPosition(Side.TOP);  
        topTabSet.setTabBarAlign(Side.LEFT);  
        topTabSet.setWidth(400);  
        topTabSet.setHeight(200);  
		
        final Tab consumptionTab = new Tab("Consumption");  
        
        consumForm = new DynamicForm();
        consumForm.setItemLayout(FormLayoutType.TABLE);
        consumForm.setNumCols(4);
        
        comsumRoutingItem = new SelectItem("consumRouting", "Routing");
        comsumRoutingItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String routingName = comsumRoutingItem.getValueAsString();
				/*routingController.findRevisionsByRoutingName(routingName, new CallbackHandler<Set<String>>() {

					@Override
					public void onSuccess(Set<String> result) {
						String[] sortedRevision = Utils.toSortedStringArray(result);
						consumRoutRevItem.setValueMap(sortedRevision);
					}
				});*/
			}
		});
        
        consumRoutRevItem = new SelectItem("consumeRoutingRev", "Rev");
        consumRoutRevItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				// load steps related with routing name and routing revision
				String name = comsumRoutingItem.getValueAsString();
				String rev = consumRoutRevItem.getValueAsString();
				/*routingController.findRoutingByNameAndRevision(name, rev, new CallbackHandler<Routing>() {

					@Override
					public void onSuccess(Routing routing) {
						Set<Step> steps = routing.getSteps();
						Set<String> stepNames = new HashSet<String>();
						if (!steps.isEmpty()) {
							for (Step step : steps) {
								stepNames.add(step.getName());
							}
							String[] sortStepNames = Utils.toSortedStringArray(stepNames);
							consumStepItem.setValueMap(sortStepNames);
						}
					}
				});*/
			}
		});
        
        consumStepItem = new SelectItem("consumeStep", "Step");
        consumStepItem.setColSpan(4);
        
        consumTypeItem = new SelectItem("consumeType", "Type");
        consumTypeItem.setColSpan(4);
        
        consumQtyItem = new TextItem("consumeQty", "Qty");
        consumQtyItem.setColSpan(4);
        
        consumeUomItem = new SelectItem("consumeUnitOfMeasure", "UoM");
        consumeUomItem.setColSpan(4);
        
        consumForm.setFields(new FormItem[] {comsumRoutingItem, consumRoutRevItem, consumStepItem, consumTypeItem, consumQtyItem, consumeUomItem});
        consumptionTab.setPane(consumForm);  
        
        final Tab produceTab = new Tab("Produce");  
        produceForm = new DynamicForm();
        produceForm.setItemLayout(FormLayoutType.TABLE);
        produceForm.setNumCols(4);
        
        produceRoutingItem = new SelectItem("produceRouting", "Routing");
        produceRoutingItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				String produceRoutingName = produceRoutingItem.getValueAsString();
				/*routingController.findRevisionsByRoutingName(produceRoutingName, new CallbackHandler<Set<String>>() {

					@Override
					public void onSuccess(Set<String> result) {
						// load revisions related routing name
						String[] sortedRevision = Utils.toSortedStringArray(result);
						produceRoutRevItem.setValueMap(sortedRevision);
					}
				});*/
			}
		});
        
        
        produceRoutRevItem = new SelectItem("produceRoutingRev", "Rev");
        
        produceStepItem = new SelectItem("produceStep", "Step");
        produceStepItem.setColSpan(4);
        
        produceQtyItem = new TextItem("produceQty", "Qty");
        produceQtyItem.setColSpan(4);
        
        produceUomItem = new SelectItem("produceUnitOfMeasure", "UoM");
        produceUomItem.setColSpan(4);
        
        produceForm.setFields(new FormItem[] {produceRoutingItem, produceRoutRevItem, produceStepItem, produceQtyItem, produceUomItem} );
        
        produceTab.setPane(produceForm);
        
        topTabSet.addTab(consumptionTab);
        topTabSet.addTab(produceTab);  
        
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight(140);
		dynamicForm.setWidth(210);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setLeft(30);
		
		CanvasItem canvasItem = new CanvasItem("material", "Material I/O");
		canvasItem.setCanvas(topTabSet);
		
		containerPartItem = new SelectItem("containerPart", "Container Part");
		containerPartItem.setWidth(200);
		containerPartItem.setRequired(true);

		partItem = new SelectItem("part", "Part");
		partItem.setWidth(200);
		partItem.setRequired(true);
		partItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String partName = partItem.getValueAsString();
				billOfMarterialController.findRevisionsByPartName(partName, new CallbackHandler<Set<String>>() {

					@Override
					public void onSuccess(Set<String> result) {
						String[] sortedRevision = Utils.toSortedStringArray(result);
						revisionItem.setValueMap(sortedRevision);
					}
				});
				
			}
		});
		
		
		revisionItem = new SelectItem("revision", "Revision");
		revisionItem.setWidth(100);
		revisionItem.setRequired(true);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { containerPartItem, partItem, revisionItem, canvasItem, getDefaultActionButtonsCanvasItem() });
		
		vStack.setLayoutMargin(20);
		vStack.addMember(dynamicForm);
		
		super.addItem(vStack);
		
		reset();
	}
	
	public void reset() {
		// load all part and revision into container part select item
		billOfMarterialController.findAllPartNameWithRevisions(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> result) {
				Set<String> containerPartNames = new HashSet<String>();
				containerPartNames.add(MESConstants.SPACE_STRING);
				containerPartNames.addAll(result);
				String[] sortedPartRevision = com.cimpoint.common.Utils.toSortedStringArray(containerPartNames);
				Set<String> partNames = new HashSet<String>();
				for (String s : result) {
					String name = s.split("-")[0];
					partNames.add(name.trim());
				}
				containerPartItem.setValueMap(sortedPartRevision);
				partItem.setValueMap(Utils.toSortedStringArray(partNames));
			}
		});
		
		// populate routing names into consumeRoutingItem and produceRoutingItem
		/*routingController.findAllRoutingNameWithRevisions(new CallbackHandler<Set<String>>() {

			Set<String> routingNames = new HashSet<String>();
			
			@Override
			public void onSuccess(Set<String> routings) {
				for(String s : routings) {
					String name = s.split("-")[0];
					routingNames.add(name.trim());
				}
				
				comsumRoutingItem.setValueMap(Utils.toSortedStringArray(routingNames));
				produceRoutingItem.setValueMap(Utils.toSortedStringArray(routingNames));
			}
		});*/
		

	}
	

	@Override
	public void onOKButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
		
		// get value
		final String strContainer = containerPartItem.getValueAsString();
		String values[] = new String[2];
		
		if (strContainer == null || MESConstants.SPACE_STRING.equals(strContainer)) {
			values[0] = values[1] = "";
		} else {
			values = strContainer.split(MESConstants.REV_FOR_TEXT);
		}
		
		final String parentId = containerPartItem.getValueAsString();
		final String partName = partItem.getValueAsString();
		final String revision = revisionItem.getValueAsString();
		
		billOfMarterialController.findPartByNameAndRevision(values[0].trim(), values[1].trim(), new CallbackHandler<Part>() {

			@Override
			public void onSuccess(final Part containerPart) {
					billOfMarterialController.findPartByNameAndRevision(partName, revision, new CallbackHandler<Part>() {
						@Override
						public void onSuccess(Part part) {
							if (part != null) {
								Long containerPartId = null;
								String containerName = "";
								String containerRevision = "";
								// if user does not select container part
								if (containerPart != null) {
									containerPartId = containerPart.getId();
									containerName = containerPart.getName();
									containerRevision = containerPart.getRevision();
								}
								Long partId = part.getId();
								
								String tabTitle = topTabSet.getSelectedTab().getTitle();
								if ("Consumption".equals(tabTitle)) {
									
								} else {
									
								}
								/*
								MfgBomItem bomItem = ClientObjectUtils.BomItems.newBomItem(containerPartId, containerRevision, containerName, partId, revision, part.getName());
								BomItemNode node = new BomItemNode(bomItem, parentId);
								mfgBomEditor.addMfgBomItem(node);
								mfgBomEditor.setDataForGrid();
								*/
							}
							
						}
					});
				}
		});
		
		// close dialog
		this.destroy();
	}

	@Override
	public void onCancelButtionClicked(ClickEvent event) {
		this.destroy();
	}
}
