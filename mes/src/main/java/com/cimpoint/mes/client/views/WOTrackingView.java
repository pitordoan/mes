/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     pitor, tai - initial implementation
 ***********************************************************************************/

package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.AppView;
import com.cimpoint.common.views.CMessageDialog;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.LotController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.controllers.UnitController;
import com.cimpoint.mes.client.controllers.WorkOrderController;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.objects.WorkOrderItem;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class WOTrackingView<previousRecord> extends AppView {
	private HLayout hMainlayout;
	private VLayout vBodyLayout;
	
	// search panel
	private WOTrackingSearchPanel woSearchPanel;

	private Label lblSelectedTrackedObjects;
	private Label lblTrxMessage;

	private WOTrackingCheckListTree woCheckLists;

//	private WOTrackingTabSet woTabSetHistory;
	private WOTrackingTabSet woTabSetLotDetail;

	// tabs
	private CCustomTab tabCheckList;
	private CCustomTab tabDetails;
	private CCustomTab tabConsumptions;
	private CCustomTab tabProceduces;
	private CCustomTab tabRouting;
//	private CCustomTab tabBatch;
	
	//left
	WOTrackingTreeGrid treeGrid;
	
	//details
	WOTrackingDetails woDetails;
			
	// right
	private WOTrackingActionPanel woActionPanel;

	// controllers
	private WorkOrderController workOrderController = MESApplication.getMESControllers().getWorkOrderController();
	private LotController lotController = MESApplication.getMESControllers().getLotController();
	private UnitController unitController = MESApplication.getMESControllers().getUnitController();
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	private boolean viewCreated = false;

	private Lot currentLot;
	private Unit currentUnit;
	
	/**
	 * init all elements of WorkOrderView
	 * */
	public void createView() {
		if (viewCreated) {
			return;
		}
		
		// main
		hMainlayout = new HLayout(5);
		hMainlayout.setWidth100();
		hMainlayout.setHeight100();

		treeGrid = new WOTrackingTreeGrid();
		
		// rhs
		woActionPanel = new WOTrackingActionPanel(this, 5);
		woActionPanel.setWidth("3%");
		
		vBodyLayout = new VLayout(5);
		vBodyLayout.setWidth("75%");
		// search part
		woSearchPanel = new WOTrackingSearchPanel(5);

		lblSelectedTrackedObjects = new Label();
		lblSelectedTrackedObjects.setWidth100();

		lblSelectedTrackedObjects.setBackgroundColor("#D5F2B8");
		lblSelectedTrackedObjects.setAlign(Alignment.CENTER);
		lblSelectedTrackedObjects.setHeight("35");
		lblSelectedTrackedObjects.setStyleName("cimtrack-WorkOrderTrackingMessage");

		lblTrxMessage = new Label();
		lblTrxMessage.setWidth100();
		lblTrxMessage.setBackgroundColor("#D5F2B8");
		lblTrxMessage.setAlign(Alignment.CENTER);
		lblTrxMessage.setHeight("35");
		lblTrxMessage.setStyleName("cimtrack-WorkOrderTrackingMessage");


		woCheckLists = new WOTrackingCheckListTree(this);

		// set tabs to heres
		woTabSetLotDetail = new WOTrackingTabSet();

		tabCheckList = new CCustomTab("Checklist");
		tabDetails = new CCustomTab("Details");
		tabConsumptions = new CCustomTab("Consumptions");
		tabProceduces = new CCustomTab("Proceduces");
		tabRouting = new CCustomTab("Routing");
//		tabBatch = new CCustomTab("Batches");

		// add tabs
		woTabSetLotDetail.addTab(tabCheckList);
		woTabSetLotDetail.addTab(tabDetails);
		woTabSetLotDetail.addTab(tabConsumptions);
		woTabSetLotDetail.addTab(tabProceduces);
		woTabSetLotDetail.addTab(tabRouting);
		
		tabCheckList.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(currentLot != null) {
					woCheckLists.setLot(currentLot);
					woCheckLists.displayCheckList(); 
				}
			}
		});
		
//		woTabSetLotDetail.addTab(tabBatch);

		// woTabSetLotDetail.setSelectedTab(4);
		woTabSetLotDetail.selectTab(1);

		vBodyLayout.addMember(woSearchPanel);

		HLayout hMainlayoutA = new HLayout(10);
		hMainlayoutA.setWidth("98%");

		VLayout vBodyLayoutA = new VLayout(10);

		vBodyLayoutA.addMember(lblSelectedTrackedObjects);
		vBodyLayoutA.addMember(woTabSetLotDetail);
		vBodyLayoutA.addMember(lblTrxMessage);

		//		treeGrid
		hMainlayoutA.setMembers(treeGrid, vBodyLayoutA);
		vBodyLayout.addMember(hMainlayoutA);

		vBodyLayout.setStyleName("cimtrack-WorkOrderTrackingBody");

		hMainlayout.setMembers(vBodyLayout, woActionPanel);

		// add events for search menu
		woSearchPanel.getItemLot().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});

		woSearchPanel.getItemUnit().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});

		woSearchPanel.getItemWorkOrder().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});

		woSearchPanel.getItemWorkOrderItem().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});

		woSearchPanel.getItemContainer().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});

		woSearchPanel.getItemBatch().addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				woTabSetLotDetail.selectTab(1);
			}
		});
		
		woDetails = new WOTrackingDetails(woActionPanel);
		
		treeGrid.setWoDetails(woDetails);
		woDetails.setLblSelectedTrackedObject(lblSelectedTrackedObjects);
		// main
		hMainlayout.setStyleName("cimtrack-WorkOrderTrackingBody");

		this.setBackgroundColor("#F3F3F3");

		VStack vStackAll = new VStack();
		vStackAll.addMember(hMainlayout);
		vStackAll.setWidth100();
		vStackAll.setHeight100();
		this.addChild(vStackAll);

		woSearchPanel.getBtnGo().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String searchData = woSearchPanel.getCimSearchTxt().getTxtContent().getValueAsString();
				String title = woSearchPanel.getMenuButton().getTitle();

				if (title.contains(MESConstants.WORK_ORDER)) {
					searchWorkOrder(searchData);
				} else if (title.contains(MESConstants.WORK_ORDER_ITEM)) {
					searchWorkOrderItem(searchData);
				} else if (title.contains(MESConstants.CONTAINER)) {
					searchContainer(searchData);
				} else if (title.contains(MESConstants.BATCH)) {
					searchBatch(searchData);
				} else if (title.contains(MESConstants.LOT)) {
					searchLot(searchData);
				} else if (title.contains(MESConstants.UNIT)) {
					searchUnit(searchData);
				}
			}
		});
		
		tabConsumptions.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				WOTrackingConsumptionsStack woConsumption = new WOTrackingConsumptionsStack(WOTrackingDetails.WIDTH, WOTrackingDetails.HEIGHT);
				if(currentLot != null) {
					woConsumption.loadData(MESConstants.Object.Type.Lot, currentLot.getId());
				}
				tabConsumptions.setPane(woConsumption);
			}
		});

		viewCreated = true;
		
		//this.show();
	}

	@Override
	public void onInitialize(final CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub

	}

	private void searchContainer(final String containerName) {
	}
	
	private void searchBatch(final String batchNumber) {
	}
	
	private void searchLot(final String lotNumber) {
		lotController.findLotByNumber(lotNumber, new CallbackHandler<Lot>() {
			@Override
			public void onSuccess(Lot lot) {
				if (lot != null) {
					currentLot = lot;		
					displayData(lot);
				} else {
					currentLot = null;
					MESApplication.showOKMessage("Lot not found by lot number: " + lotNumber);
				}
			}
		});
	}
	
	private void searchUnit(final String unitSN) {
		unitController.findUnitByNumber(unitSN, new CallbackHandler<Unit>() {
			@Override
			public void onSuccess(Unit unit) {
				if (unit != null) {
					currentUnit = unit;
					displayUnitData(unit);
				} else {
					CMessageDialog dlg = new CMessageDialog("WO Tracking Error Message", 250, 50);
					dlg.showOKMessage("This unit number does not exist.", null);
				}
			}

		});
	}
	
	private void searchWorkOrder(final String workOrderNumber) {
		
		workOrderController.findWorkOrderByNumber(workOrderNumber, new CallbackHandler<WorkOrder>() {
			@Override
			public void onSuccess(WorkOrder result) {
				if (result != null) {
					// selected WorkOrder
					lblSelectedTrackedObjects.setContents(MESConstants.WORK_ORDER + ": " + result.getNumber());
					lblSelectedTrackedObjects.redraw();

					// set WO detail
//					wo.setWo(result);
//					wo.setWOData();

					String s = "WO:" + result.getNumber();
					HeaderSpan span = new HeaderSpan(s, new String[] { "woNumber", "status", "shipDate", "quantity", "uoM" });
					span.setAlign(Alignment.LEFT);
//					wo.redraw();

					// detail tabs
//					tabCheckList.setPane(wo);

					woTabSetLotDetail.selectTab(0);
					// history
				} else {
					MESApplication.showOKMessage("Lot not found by lot number: " + workOrderNumber);
				}
			}
		});
		
	/*	workOrderController.findWorkOrderByNumber(workOrderNumber, new CallbackHandler<WorkOrder>() {

			@Override
			public void onSuccess(WorkOrder result) {
				if (result != null) {
					// selected WorkOrder
					lblSelectedTrackedObjects.setContents(MESConstants.WORK_ORDER + ": " + result.getNumber());
					lblSelectedTrackedObjects.redraw();

					// set WO detail
					wo.setWo(result);
					wo.setWOData();

					String s = "WO:" + result.getNumber();
					HeaderSpan span = new HeaderSpan(s, new String[] { "woNumber", "status", "shipDate", "quantity", "uoM" });
					span.setAlign(Alignment.LEFT);
					wo.redraw();

					// detail tabs
					tabCheckList.setPane(wo);

					woTabSetLotDetail.selectTab(0);
					// history
					tabHistory.setPane(woLotHistory);
					tabCustomerAttr.setPane(woCustomAttribute);
				} else {
					CMessageDialog dlg = new CMessageDialog("WO Tracking Error Message", 250, 50);
					dlg.showOKMessage("This word order number does not exist.", null);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				CMessageDialog dlg = new CMessageDialog("WO Tracking Error Message", 250, 50);
				dlg.showOKMessage("Errors occur in this operation.", null);
			}
		});*/
	}
	
	private void searchWorkOrderItem(final String workOrderItem) {
		workOrderController.findWorkOrderItemByNumber(workOrderItem, new CallbackHandler<WorkOrderItem>() {

			@Override
			public void onSuccess(WorkOrderItem result) {
				if (result != null) {

					// selected WorkOrderItem
					lblSelectedTrackedObjects.setTitle(result.getNumber());
				} else {
					CMessageDialog dlg = new CMessageDialog("WO Tracking Error Message", 250, 50);
					dlg.showOKMessage("This word order number does not exist.", null);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				CMessageDialog dlg = new CMessageDialog("WO Tracking Error Message", 250, 50);
				dlg.showOKMessage("Errors occur in this operation.", null);

			}
		});
	}
	

	public void displayWO(WorkOrder wo) {
		treeGrid.setWo(wo);
		treeGrid.buildWOTree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.WORK_ORDER + " " + wo.getNumber());
		lblSelectedTrackedObjects.redraw();
		
		
		/*woDetails.setLot(lot);
		woDetails.showLotDetails();
		tabDetails.setPane(woDetails);
		
		tabCheckList.setPane(woCheckLists);
		woTabSetLotDetail.selectTab(1);*/
	}

	public void displayWOI(WorkOrderItem woi) {
		treeGrid.setWoi(woi);
		treeGrid.buildWOITree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.WORK_ORDER_ITEM + " " + woi.getNumber());
		lblSelectedTrackedObjects.redraw();
		
		
		/*woDetails.setLot(lot);
		woDetails.showLotDetails();
		tabDetails.setPane(woDetails);
		
		tabCheckList.setPane(woCheckLists);
		woTabSetLotDetail.selectTab(1);*/
	}

	public void displayContainer(Container container) {
		treeGrid.setContainer(container);
		treeGrid.buildContainerTree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.CONTAINER + " " + container.getName());
		lblSelectedTrackedObjects.redraw();
		
		/*woDetails.setLot(lot);
		woDetails.showLotDetails();
		tabDetails.setPane(woDetails);
		
		tabCheckList.setPane(woCheckLists);
		woTabSetLotDetail.selectTab(1);*/
	}



	public void displayBatch(Batch batch) {
		treeGrid.setBatch(batch);
		treeGrid.buildBatchTree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.BATCH + " " + batch.getNumber());
		lblSelectedTrackedObjects.redraw();
		
		/*woDetails.setLot(lot);
		woDetails.showLotDetails();
		tabDetails.setPane(woDetails);
		
		tabCheckList.setPane(woCheckLists);
		woTabSetLotDetail.selectTab(1);*/
	}


	public void displayData(Lot lot) {
		treeGrid.setLot(lot);
		treeGrid.buildLotTree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.LOT + " " + lot.getNumber());
		lblSelectedTrackedObjects.redraw();
		
		
		woDetails.setLot(lot);
		woDetails.showLotDetails();
		tabDetails.setPane(woDetails);
		
		tabCheckList.setPane(woCheckLists);
		woTabSetLotDetail.selectTab(1);
	}

	public void displayUnitData(Unit unit) {
		treeGrid.setUnit(unit);
		treeGrid.buildUnitTree();
		
		// selected Lot
		lblSelectedTrackedObjects.setContents(MESConstants.UNIT + " " + unit.getSerialNumber());
		lblSelectedTrackedObjects.redraw();
		
		
		woDetails.setUnit(unit);
		woDetails.showUnitDetails();
		tabDetails.setPane(woDetails);
		
//		tabCheckList.setPane(woCheckLists);
//		woTabSetLotDetail.selectTab(1);
	}

	public Unit getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(Unit currentUnit) {
		this.currentUnit = currentUnit;
	}

	public void refreshData(Lot lot) {
		woDetails.showLotDetails();
	}
		
	public void displayTransactionMessage(String message) {
		lblTrxMessage.setContents(message);
	}

	public Lot getCurrentLot() {
		return currentLot;
	}

	public void setCurrentLot(Lot currentLot) {
		this.currentLot = currentLot;
	}
}
