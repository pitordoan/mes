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

import com.cimpoint.common.views.ListingPanel;

public class DictionaryListingPanel extends ListingPanel {

	public DictionaryListingPanel() {
		super("dictionaryPanel", "20%");
		
		//TODO add editors
		this.addSection("localeSection", "Locale Groups", new LocaleGroupEditor());
		this.addSection("itemSection", "Items", new ItemEditor());
		this.addSection("dictionarySection", "Dictionaries", new DictionaryEditor());
	}
	
	@Override
	public void onSectionHeaderClicked(String selSectionTitle) {
		if (selSectionTitle.equals("Locale Groups")) {
			final String sectionId = "localeSection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newLocale", "New Locale...", "pieces/16/piece_green.png");	
			}
		} else if (selSectionTitle.equals("Items")) {
			final String sectionId = "itemSection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newItem", "New Item...", "pieces/16/piece_green.png");	
			}
		} else if (selSectionTitle.equals("Dictionaries")) {
			final String sectionId = "dictionarySection";
			if (this.isEmptySection(sectionId)) {
				addSectionTreeNode(sectionId, "newDictionary", "New Dictionary...", "pieces/16/piece_green.png");	
			}
		}
	}
	
}
