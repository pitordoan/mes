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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.objects.Bom;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.common.MESConstants;

public class BillOfMaterialsListingPanel extends ListingPanel {
	
	private BillOfMarterialController bomController = MESApplication.getMESControllers().getBillOfMarterialController();
	
	public BillOfMaterialsListingPanel() {
		super("billOfMaterialsPanel", "20%");
		
		this.addSection("partsSection", "Parts", new PartEditor());
		this.addSection("bomsSection", "BOMs", new BOMEditor());
		this.addSection("mfgBomsSection", "Manufacturing BOMs", new MfgBomEditor());
	}
	
	@Override
	public void onSectionHeaderClicked(String selSectionTitle) {
		if (selSectionTitle.equals("Parts")) {
			final String sectionId = "partsSection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newPart", "New Part...", "pieces/16/piece_green.png");	
				bomController.findAllParts(new CallbackHandler<Set<Part>>() {
					
					@Override
					public void onSuccess(Set<Part> result) {
						for (Part p : result) {
							String nodeName = p.getName() + MESConstants.REV_FOR_NAME + p.getRevision();
							String text = p.getName() + MESConstants.REV_FOR_TEXT + p.getRevision();
							addSectionTreeNode("partsSection", nodeName, text, "pieces/16/piece_green.png");
						}
					}
				});
			}
		}
		else if (selSectionTitle.equals("BOMs")) {
			final String sectionId = "bomsSection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newBOM", "New BOM...", "pieces/16/piece_green.png");	
				bomController.findAllBoms(new CallbackHandler<Set<Bom>>() {

					@Override
					public void onSuccess(Set<Bom> result) {
						for (Bom bom : result) {
							String nodeName = bom.getName() + MESConstants.REV_FOR_NAME + bom.getRevision();
							String text = bom.getName() + MESConstants.REV_FOR_TEXT + bom.getRevision();
							addSectionTreeNode("bomsSection", nodeName, text, "pieces/16/piece_green.png");
						}
					}
				});
			}
		} else if (selSectionTitle.equals("Manufacturing BOMs")) {
			final String sectionId = "mfgBomsSection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newMfgBOM", "New Mfg Bom...", "pieces/16/piece_green.png");	
			}
		}
	}
}
