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
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;

public class RoutingsListingPanel extends ListingPanel {
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	public RoutingsListingPanel() {
		super("routingsPanel", "200px");
		
		this.addSection("sitesSection", "Sites", new SiteEditor());
		this.addSection("areasSection", "Areas", new AreaEditor());
		this.addSection("productionLinesSection", "Production Lines", new ProductionLineEditor());
		this.addSection("workCentersSection", "Work Centers", new WorkCenterEditor());
		this.addSection("operationsSection", "Operations", new OperationEditor());
		this.addSection("equipmentsSection", "Equipments", new EquipmentEditor());
		this.addSection("routingsSection", "Routings", new RoutingEditor());
	}
	
	@Override
	public void onSectionHeaderClicked(String selSectionTitle) {
		if (selSectionTitle.equals("Sites")) {
			final String sectionId = "sitesSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllSiteNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newSite", "New Site...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		}	
		else if (selSectionTitle.equals("Areas")) {
			final String sectionId = "areasSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllAreaNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newArea", "New Area...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {							
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		}	
		else if (selSectionTitle.equals("Work Centers")) {
			final String sectionId = "workCentersSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllWorkCenterNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newWorkCenter", "New Work Center...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		}
		else if (selSectionTitle.equals("Production Lines")) {
			final String sectionId = "productionLinesSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllProductionLineNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newProductionLines", "New Production Line...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		}
		else if (selSectionTitle.equals("Operations")) {
			final String sectionId = "operationsSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllOperationNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newOperation", "New Operation...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		}
		else if (selSectionTitle.equals("Equipments")) {
			final String sectionId = "equipmentsSection";
			if (this.isEmptySection(sectionId)) {
				routingController.findAllEquipmentNames(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newEquipment", "New Equipment...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});
			}
		} else if (selSectionTitle.equals("Routings")) {
			final String sectionId = "routingsSection";
			if (this.isEmptySection(sectionId)) {
				/*routingController.findAllRoutingNameWithRevisions(new CallbackHandler<Set<String>>() {
		
					@Override
					public void onSuccess(Set<String> names) {
						addSectionTreeNode(sectionId, "newRouting", "New Routing...", "pieces/16/piece_green.png");		
						if (names != null) {	
							String[] sortedNames = Utils.toSortedStringArray(names);
							for (String name: sortedNames) {
								addSectionTreeNode(sectionId, name, name, "pieces/16/piece_green.png");	
							}
						}
					}
					
				});*/
			}
		}
	}
}
