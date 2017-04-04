/**
 * 
 */
package com.cimpoint.mes.client.views;

import java.util.Set;
import java.util.TreeSet;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.ListingPanel;

/**
 * @author Chung Khanh Duy
 *
 */
public class CustomCodeListingPanel extends ListingPanel {

	
	public CustomCodeListingPanel() {
		super("customPanel", "20%");
		
		this.addSection("librarySection", "Libraries", new LibraryEditor());
		this.addSection("customCodeSection", "Custom Codes", new LibraryEditor());
	}
	
	@Override
	public void onSectionHeaderClicked(String selSectionTitle) {
		if (selSectionTitle.equals("Libraries")) {
			final String sectionId = "librarySection";
			addSectionTreeNode(sectionId, "newLibrary", "New Library...", "pieces/16/piece_green.png");
		}	
	}

}
