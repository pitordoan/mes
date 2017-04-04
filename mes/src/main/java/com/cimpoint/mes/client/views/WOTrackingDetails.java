/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.LotDetailRecord;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.UnitDetailRecord;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.objects.WorkOrderItem;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WOTrackingDetails extends Canvas{

	public static final String CURRENT_TITLE = "Current";
	public static final String HISTORY_TITLE = "History";
	public static final String CUSTOME_ATTRIBUTES_TITLE = "Custom Attributes";
	
	public static final String CURRENT_ID = "current";
	public static final String HISTORY_ID = "history";
	public static final String CUSTOME_ATTRIBUTES_ID = "customAttributes";
	
	public static final String WIDTH = "505px";
	public static final String HEIGHT = "415px";
	
	private Lot lot;
	private Unit unit;
	private WorkOrder wo;

	private WorkOrderItem woItem;
	private Container container;
	private Batch batch;
	
	private RoutingController routingController;

	private WOTrackingActionPanel woActionPanel;

	private WOTrackingLotDetailsSectionStack lotStack; 
	private WOTrackingUnitDetailsSectionStack unitStack; 
	
	private Label lblSelectedTrackedObject;

	public WOTrackingDetails(){
		routingController = MESApplication.getMESControllers().getRoutingController();
		initLotStack();
	}

	public WOTrackingDetails(WOTrackingActionPanel woActionPanel){
		this.woActionPanel = woActionPanel;
		routingController = MESApplication.getMESControllers().getRoutingController();
		initLotStack();
	}

	public WOTrackingDetails(WOTrackingActionPanel woActionPanel, Label lblSelectedTrackedObject){
		this.lblSelectedTrackedObject = lblSelectedTrackedObject;
		this.woActionPanel = woActionPanel;
		routingController = MESApplication.getMESControllers().getRoutingController();
		initLotStack();
	}

	public void initLotStack() {
		lotStack = new WOTrackingLotDetailsSectionStack(WIDTH, HEIGHT);
		lotStack.addLotSection(CURRENT_ID, CURRENT_TITLE);
		lotStack.addHistorySection(HISTORY_ID, HISTORY_TITLE);
		lotStack.addCustomAttributesSection(CUSTOME_ATTRIBUTES_ID, CUSTOME_ATTRIBUTES_TITLE);
		this.addChild(lotStack);
		lotStack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = lotStack.getSelectedLotRecord(CURRENT_ID);
				if(record instanceof LotDetailRecord) {
					woActionPanel.setLot(lot);
					woActionPanel.setSelectedLotDetail((LotDetailRecord)record);
					woActionPanel.loadLotData((LotDetailRecord)record);
					lblSelectedTrackedObject.setContents(MESConstants.LOT + ": " + record.getAttributeAsString("lotNumber"));
					lblSelectedTrackedObject.redraw();
				}
			}
		});
	}
	
	public void initUnitStack() {
		if(this.contains(lotStack))
			this.removeChild(lotStack);
		
		unitStack = new WOTrackingUnitDetailsSectionStack(WIDTH, HEIGHT);
		unitStack.addUnitSection(CURRENT_ID, CURRENT_TITLE);
		unitStack.addHistorySection(HISTORY_ID, HISTORY_TITLE);
		unitStack.addCustomAttributesSection(CUSTOME_ATTRIBUTES_ID, CUSTOME_ATTRIBUTES_TITLE);
		this.addChild(unitStack);
		unitStack.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = unitStack.getSelectedUnitRecord(CURRENT_ID);
				if(record instanceof UnitDetailRecord) {
					woActionPanel.setUnit(unit);
					woActionPanel.setSelectedUnitDetail((UnitDetailRecord)record);
					woActionPanel.loadUnitData((UnitDetailRecord)record);
					lblSelectedTrackedObject.setContents(MESConstants.UNIT + ": " + record.getAttributeAsString("unitNumber"));
					lblSelectedTrackedObject.redraw();
				}
			}
		});
	}

	public void showLotDetails(){
		if(lotStack == null) {
			initLotStack();
		}
		lotStack.setLot(lot);
		lotStack.setGridLotDetailsData(CURRENT_ID);
	}

	public void showLotHistory(){
		lotStack.setGridHistoryData(HISTORY_ID);
	}
	
	public void showLotCustomAttributes(){
//		details.addNameDescriptionGridSection("customAttribute", "Custom Attributes");
	}
	
	public void showUnitDetails(){
		if(unitStack == null) {
			initUnitStack();
		}
		unitStack.setUnit(unit);
		unitStack.setGridUnitDetailsData(CURRENT_ID);
	}

	public void showUnitHistory(){
		lotStack.setGridHistoryData(HISTORY_ID);
	}
	
	public void showUnitCustomAttributes(){
//		details.addNameDescriptionGridSection("customAttribute", "Custom Attributes");
	}
	
	public Lot getLot() {
		return lot;
	}
	
	public void setLot(Lot lot) {
		this.lot = lot;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Label getLblSelectedTrackedObject() {
		return lblSelectedTrackedObject;
	}
	
	public void setLblSelectedTrackedObject(Label lblSelectedTrackedObject) {
		this.lblSelectedTrackedObject = lblSelectedTrackedObject;
	}
	
	public WorkOrder getWo() {
		return wo;
	}

	public void setWo(WorkOrder wo) {
		this.wo = wo;
	}

	public WorkOrderItem getWoItem() {
		return woItem;
	}

	public void setWoItem(WorkOrderItem woItem) {
		this.woItem = woItem;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}
}
