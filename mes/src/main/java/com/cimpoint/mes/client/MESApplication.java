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

package com.cimpoint.mes.client;

import com.cimpoint.common.AppBrowser;
import com.cimpoint.common.Application;
import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.AppView;
import com.cimpoint.common.views.BrowserView;
import com.cimpoint.common.views.CMessageDialog;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.common.views.HeaderView.ActionHandler;
import com.cimpoint.mes.client.controllers.MESControllers;
import com.cimpoint.mes.client.views.CustomCodeAppView;
import com.cimpoint.mes.client.views.common.ChecklistView;
import com.cimpoint.mes.client.views.common.ConsumptionView;
import com.cimpoint.mes.client.views.common.DetailsView;
import com.cimpoint.mes.client.views.common.ProduceView;
import com.cimpoint.mes.client.views.editors.SystemConfigurationsView;
import com.cimpoint.mes.client.views.wot.WOTrackingView;
import com.cimpoint.mes.common.entities.EApplication;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class MESApplication extends Application {

	private static MESControllers appControllers = new MESControllers();
	private static SystemConfigurationsView sysConfView;
	private static WOTrackingView wotView;
	
	public static MESControllers getMESControllers() {
		return appControllers;
	}
	
	/*public static AppView findView(String name) {
		return AppBrowser.getBrowserView().getBodyView().getAppsListingView().findAppView(name);
	}
	
	public static AppView findSystemConfigurationsView(String treeNodeId) {
		return AppBrowser.getBrowserView().getBodyView().getAppsListingView().findAppView("appsTab", "sysConfigurationsSection", treeNodeId);
	}

	public static AppView findShopFloorControlView(String treeNodeId) {
		return AppBrowser.getBrowserView().getBodyView().getAppsListingView().findAppView("appsTab", "shopFloorControlSection", treeNodeId);
	}*/
	
	@Override
	public void onModuleLoad() {
		AppBrowser.init(getBackgroundCanvas());
		final BrowserView view = AppBrowser.getBrowserView();
				
		view.getHeaderView().addApplication("sysConfiguration", "System Configurations", "pieces/16/piece_red.png", new ActionHandler() {
			@Override
			public void onAppSelected() {
				if (sysConfView == null) {
					sysConfView = new SystemConfigurationsView();
				}
				view.getBodyView().showView(sysConfView, "MES Configurations", "pieces/16/piece_red.png");
			}			
		});

		view.getHeaderView().addApplication("workOrderTrackingApp", "Work Order Tracking", "pieces/16/piece_green.png", new ActionHandler() {
			@Override
			public void onAppSelected() {
				if (wotView == null) {
					wotView = new WOTrackingView();
					wotView.onInitialize(new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							//TODO load custom view per user
							wotView.getDataPanel().addTab("checklist", "Checklist", new ChecklistView());
							wotView.getDataPanel().addTab("consumption", "Consumption", new ConsumptionView());
							wotView.getDataPanel().addTab("produce", "Produce", new ProduceView());
							wotView.getDataPanel().addTab("details", "Details", new DetailsView());							
						}						
					});
				}
				view.getBodyView().showView(wotView, "Work Order Tracking", "pieces/16/piece_green.png");
			}			
		});
		
		view.create();
		
		//working ones
		// ---Apps tab
		//view.getBodyView().getAppsListingView().addTab("appsTab", "Apps");

		// System Configurations section
		//view.getBodyView().getAppsListingView().addSection("appsTab", "sysConfigurationsSection", "Configurations");

		//view.getBodyView().getAppsListingView()
		//.addSectionTreeNode("appsTab", "sysConfigurationsSection", "mesConfigurations", "MES Configurations", "pieces/16/piece_red.png", new SystemConfigurationsView());
		
		
		
		/*view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "billOfMaterialsApp", "Bill of Materials", "pieces/16/piece_red.png", new BillOfMaterialsView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "customDataCollectionsApp", "Custom Data Collections", "pieces/16/piece_red.png", new CustomDataCollectionsView());

		view.getBodyView().getAppsListingView()
				.addSectionTreeNode("appsTab", "sysConfigurationsSection", "customCodesApp", "Custom Codes", "pieces/16/piece_red.png", new CustomCodesView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "databasesApp", "Databases", "pieces/16/piece_red.png", new DatabaseConnectionsView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "dataManagementApp", "Data Management", "pieces/16/piece_red.png", new DataManagementView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "dictionariesApp", "Dictionaries", "pieces/16/piece_red.png", new DictionariesView());

//		view.getBodyView().getAppsListingView()
//		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "productionTemplatesApp", "Production Templates", "pieces/16/piece_red.png", new ProductionTemplatesView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "recipesApp", "Recipes", "pieces/16/piece_red.png", new RecipesView());

		view.getBodyView().getAppsListingView().addSectionTreeNode("appsTab", "sysConfigurationsSection", "routingsApp", "Routings", "pieces/16/piece_red.png", new RoutingsView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "sysConfigurationsSection", "userManagerApp", "Users", "pieces/16/piece_red.png", new UserManagerView());*/

		//Shop Floor Control section
		//view.getBodyView().getAppsListingView().addSection("appsTab", "shopFloorControlSection", "Shop Floor Control");
		
		
		//working one
		//view.getBodyView().getAppsListingView().addSection("appsTab", "applications", "Applications");
		
		
		/*view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "shopFloorControlSection", "defectTrackingApp", "Defect Tracking", "pieces/16/piece_green.png", new DefectTrackingView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "shopFloorControlSection", "equipmentTrackingApp", "Equipment Tracking", "pieces/16/piece_green.png", new EquipmentTrackingView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "shopFloorControlSection", "inventoryControlApp", "Inventory Control", "pieces/16/piece_green.png", new InventoryControlView());

		view.getBodyView().getAppsListingView()
		.addSectionTreeNode("appsTab", "shopFloorControlSection", "laborTrackingApp", "Labor Tracking", "pieces/16/piece_green.png", new LaborTrackingView());*/

		//working ones
		//view.getBodyView().getAppsListingView()
		//.addSectionTreeNode("appsTab", "applications", "workOrderTrackingApp", "Work Order Tracking", "pieces/16/piece_green.png", new WOTrackingView());
		//loadCustomApplications();
		
		//working one
		// ---Reports tab
		//view.getBodyView().getAppsListingView().addTab("reports", "Reports");
	}

	/*private void loadCustomApplications() {
		getMESControllers().getCustomCodeController().findAllApplications(new CallbackHandler<Set<EApplication>>() {
			@Override
			public void onSuccess(Set<EApplication> apps) {
				BrowserView view = AppBrowser.getBrowserView();
				for (EApplication app: apps) {
					String sectionId = app.getCategory().replaceAll(" ", "");
					sectionId = sectionId.toLowerCase();
					if (!view.getBodyView().getAppsListingView().existsSection("appsTab", sectionId)) {				
						view.getBodyView().getAppsListingView().addSection("appsTab", sectionId, app.getCategory());
					}
					String tabId = app.getName().replaceAll(" ", "");
					String nodeText = app.getName();
					view.getBodyView().getAppsListingView()
					.addSectionTreeNode("appsTab", sectionId, tabId, nodeText, "pieces/16/piece_green.png", toAppView(app));
				}				
			}			
		});
	}*/

	private Canvas getBackgroundCanvas() {
		VLayout bgCanvas = new VLayout();
		bgCanvas.setWidth100();
		bgCanvas.setHeight100();
		bgCanvas.setAlign(VerticalAlignment.CENTER);
		
		Label productNameLabel = new Label(); 
		productNameLabel.setHeight(50);  
		productNameLabel.setPadding(10);  
		productNameLabel.setAlign(Alignment.CENTER);  
		productNameLabel.setValign(VerticalAlignment.CENTER);  
		productNameLabel.setWrap(false);  
        productNameLabel.setIcon("pieces/16/logo_small.png");  
        productNameLabel.setStyleName("cimtrack-WelcomeProductName");
        productNameLabel.setContents("CIMTrack v0.1.0");  
        bgCanvas.addMember(productNameLabel);
        
        Label productDescLabel = new Label();
        productDescLabel.setHeight(30);  
        productDescLabel.setPadding(10);  
        productDescLabel.setAlign(Alignment.CENTER);  
        productDescLabel.setValign(VerticalAlignment.CENTER); 
        productDescLabel.setWrap(false);  
        productDescLabel.setStyleName("cimtrack-WelcomeProductDesc");
        productDescLabel.setContents("CIMPoint Manufacturing Execution System");  
        bgCanvas.addMember(productDescLabel);
        
        Label envLabel = new Label(); 
        envLabel.setHeight(30);  
        envLabel.setPadding(10);  
        envLabel.setAlign(Alignment.CENTER);  
        envLabel.setValign(VerticalAlignment.CENTER);  
        envLabel.setWrap(false);  
        envLabel.setStyleName("cimtrack-WelcomeEnvironment");
        envLabel.setContents("Development Environment");  
        bgCanvas.addMember(envLabel);
        
        Label copyrightLabel = new Label();
        copyrightLabel.setHeight(30);  
        copyrightLabel.setPadding(10);  
        copyrightLabel.setAlign(Alignment.CENTER);  
        copyrightLabel.setValign(VerticalAlignment.CENTER);  
		copyrightLabel.setWrap(false);  
        copyrightLabel.setStyleName("cimtrack-WelcomeCopyright");
        copyrightLabel.setContents("Copyright &#169; 2011 CIMpoint LLC, www.cimpoint.com.  All rights reserved.");  
        bgCanvas.addMember(copyrightLabel);
        
        return bgCanvas;
	}
	
	public static SystemConfigurationsView getSystemConfigurationView() {
		return sysConfView;
	}
	
	public static WOTrackingView getWOTrackingView() {
		return wotView;
	}
	
	private AppView toAppView(EApplication app) {
		CustomCodeAppView view = new CustomCodeAppView(app);
		return view;
	}
	
	public static void showError(String msg) {
		CMessageDialog dlg = new CMessageDialog("MES Error Message", 300, 150);
		dlg.showOKMessage(msg, null);
	}
	
	public static void showMessage(String msg, CMessageDialog.MessageButton[] buttons, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog("MES Info Message", 300, 150);
		dlg.showMessage(msg, buttons, callback);
	}
	
	public static void showYesNoMessage(String msg, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog("MES Info Message", 300, 150);
		dlg.showYesNoMessage(msg, callback);
	}
	
	public static void showYesNoMessage(String title, String msg, int width, int height, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog(title, width, height);
		dlg.showYesNoMessage(msg, callback);
	}
	
	public static void showYesNoCancelMessage(String title, String msg, int width, int height, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog(title, width, height);
		dlg.showYesNoCancelMessage(msg, callback);
	}
	
	public static void showOKCancelMessage(String msg, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog("MES Info Message", 300, 150);
		dlg.showOKCancelMessage(msg, callback);
	}
	
	public static void showOKMessage(String msg, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog("MES Info Message", 300, 150);
		dlg.showOKMessage(msg, callback);
	}
	
	public static void showOKMessage(String msg) {
		CMessageDialog dlg = new CMessageDialog("MES Info Message", 300, 150);
		dlg.showOKMessage(msg, null);
	}
	
	public static void showConfirmMessage(String msg, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog("MES Confirmation Message", 300, 150);
		dlg.showConfirmMessage(msg, callback);
	}
	
	public static void showConfirmMessage(String title, String msg, int width, int height, CallbackHandler<MessageButton> callback) {
		CMessageDialog dlg = new CMessageDialog(title, width, height);
		dlg.showConfirmMessage(msg, callback);
	}
}
