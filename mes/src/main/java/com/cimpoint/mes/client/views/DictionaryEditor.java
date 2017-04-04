/**
 * 
 */
package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.ListingPanel;
import com.cimpoint.common.views.PropertiesEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * @author Chung Khanh Duy
 *
 */
public class DictionaryEditor extends PropertiesEditor {

	private DynamicForm dynamicForm;
	private TextItem nameTextItem;
	private TextItem descTextItem;
	private SelectItem localeGroupItem;
	private SelectItem typeItem;
	private boolean viewCreated = false;
	private ListingPanel listingPanel;
	
	@Override
	public String getTitle() {
		return "Dictionary Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		listingPanel = super.getListingPanel();
		createView();
		callback.onSuccess((Void)null);
	}

	@Override
	public void onClose() {
		
	}

	@Override
	public void onNewButtionClicked(ClickEvent event) {
		
	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		
	}
	
	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		// TODO Auto-generated method stub
		
	}
	
	private void createView() {
		if (viewCreated) {
//			sectionStack.reset();
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
		descTextItem.setWidth(200);
		descTextItem.setLength(50);
		descTextItem.setRequired(false);
		
		localeGroupItem = new SelectItem("localeGroup", "Locale Group");
		localeGroupItem.setWidth(200);
		localeGroupItem.setRequired(true);
		
		typeItem = new SelectItem("type", "Type");
		typeItem.setWidth(200);
		typeItem.setRequired(true);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, localeGroupItem, typeItem, getDefaultActionButtonsCanvasItem() });
		vStack.addMember(dynamicForm);
		
		super.addChild(vStack);
		
		viewCreated = true;
	}
	

}
