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
 *     Duy Chung - modify implementation
 ***********************************************************************************/

package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.AppView;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;


public class DictionariesView extends AppView {

//	private DictionaryGrid dicGrid;
//	private NameGrid nameGrid;
//	private EditorContainerView editorContainerView;
//	private DictionaryEditor editor;
	private boolean viewCreated = false;
	
	public void createView() {
		if (viewCreated) return;
		DictionaryListingPanel dicPanel = new DictionaryListingPanel();
		super.addMember(dicPanel);
		
		viewCreated = true;
//		// main layout of dictionaires view
//		VLayout mainLayout = new VLayout();
//		HLayout hLayout = new HLayout();
//		hLayout.setHeight("500px");
//		
//		dicGrid = new DictionaryGrid();
//		dicGrid.setWidth(200);
//		dicGrid.setHeight100();
//		dicGrid.setLayoutAlign(VerticalAlignment.BOTTOM);
//		dicGrid.setShowAllRecords(true);
//		ListGridField dicField = new ListGridField("dictionary", "Dictionary");
//		dicGrid.setFields(dicField);
//			
//		LayoutSpacer spacer = new LayoutSpacer();
//		spacer.setWidth(5);
//		
//		nameGrid = new NameGrid();
//		nameGrid.setShowAllRecords(true);
//		nameGrid.setWidth(200);
//		nameGrid.setHeight100();
//		ListGridField nameField = new ListGridField("name", "Name" );
//		nameGrid.setFields(nameField);
//		
//		editor = new DictionaryEditor();
//		editorContainerView = new EditorContainerView();
//		editorContainerView.setHeight100();
//		
//		HStack dicButtons = new HStack();		
//		AddButton btnAdd = new AddButton("Add");
//		btnAdd.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				//TODO
//				final Window winModal = new Window();  
//				winModal.setWidth(300);  
//				winModal.setHeight(110);  
//				winModal.setTitle("Add Dictionary");  
//				winModal.setShowMinimizeButton(false);  
//				winModal.setIsModal(true);  
//				winModal.setShowModalMask(true);  
//				winModal.centerInPage();  
//				winModal.addCloseClickHandler(new com.smartgwt.client.widgets.events.CloseClickHandler() {
//					@Override
//					public void onCloseClick(CloseClientEvent event) {
//						winModal.destroy(); 
//					}
//				});
//				
//				VLayout vLayout = new VLayout();
//				
//				DynamicForm form = new DynamicForm();
//				form.setWidth100();  
//				form.setPadding(5);  
//				form.setItemLayout(FormLayoutType.TABLE);
//				form.setNumCols(3);
//				form.setLayoutAlign(VerticalAlignment.BOTTOM);  
//
//				TextItem textName = new TextItem("name", "Name");
//				textName.setRequired(true);
//				textName.setColSpan(2);
//				textName.setWidth(230);
//
//				HLayout tagButtons = new HLayout(5);
//				tagButtons.setWidth100();
//				tagButtons.setAlign(Alignment.CENTER);		
//				DictionaryAddButton dicAddBtn = new DictionaryAddButton("Add");
//				Button dicCancelBtn = new Button("Cancel");
//				dicCancelBtn.addClickHandler(new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						winModal.destroy();
//					}
//				});
//				
//				tagButtons.setMembers(dicAddBtn, dicCancelBtn);
//				form.setFields(textName);
//				vLayout.setMembers(form, tagButtons);
//				
//				winModal.addItem(vLayout);  
//				winModal.show();  
//  
//			}
//		});
//		RemoveDicButton btnRemove = new RemoveDicButton("Remove");
//		btnRemove.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {  
//                SC.confirm("Remove Dictionary", "Are you sure you'd like to remove dictionary ?", new BooleanCallback() {
//					
//					@Override
//					public void execute(Boolean value) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
//            }   	
//		});
//		dicButtons.setMembersMargin(5);
//		dicButtons.setLayoutTopMargin(10);
//		dicButtons.setMembers(btnAdd, btnRemove);
//		
//		hLayout.setMembers(dicGrid, spacer, nameGrid, spacer, editorContainerView);
//		mainLayout.setMembers(hLayout, dicButtons);
//		super.addMember(mainLayout);
	}

	@Override
	public void onInitialize(final CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void)null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @author Chung Khanh Duy
	 *
	 */
	class DictionaryGrid extends ListGrid implements RecordClickHandler {

		public DictionaryGrid() {
			addRecordClickHandler(this);
		}	
		
		@Override
		public void onRecordClick(RecordClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * 
	 * @author Chung Khanh Duy
	 *
	 */
	class NameGrid extends ListGrid implements RecordClickHandler {

		public NameGrid() {
			addRecordClickHandler(this);
		}
		
		@Override
		public void onRecordClick(RecordClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * New button
	 * @author Chung Khanh Duy
	 *
	 */
	class NewButton extends Button implements ClickHandler {
		
		public NewButton(String title) {
			super(title);
		}
		
		public void onClick(ClickEvent event) {
			
		}
	}
	
	/**
	 * Delete button is used in form
	 * @author Chung Khanh Duy
	 *
	 */
	class DeleteButton extends Button implements ClickHandler {
		
		public DeleteButton(String title) {
			super(title);
		}
		
		public void onClick(ClickEvent event) {
			
		}
	}
		
	/**
	 * Save button
	 * @author Chung Khanh Duy
	 *
	 */
	class SaveDictionaryButton extends Button implements ClickHandler {
		
		public SaveDictionaryButton(String title) {
			super(title);
			addClickHandler(this);
		}
		
		@Override
		public void onClick(ClickEvent event) {
	
		}
	}
	
	/**
	 * Add button
	 * @author Chung Khanh Duy
	 *
	 */
	class AddButton extends Button implements ClickHandler {
		
		public AddButton(String title) {
			super(title);
			addClickHandler(this);
		}
		
		@Override
		public void onClick(ClickEvent event) {
	
		}
	}
	
	class RemoveDicButton extends Button implements ClickHandler {
		
		public RemoveDicButton(String title) {
			super(title);
			addClickHandler(this);
		}
		
		@Override
		public void onClick(ClickEvent event) {
	
		}
	}
	
	/**
	 * Dictionary Add button
	 * @author Chung Khanh Duy
	 *
	 */
	class DictionaryAddButton extends Button implements ClickHandler {
		
		public DictionaryAddButton(String title) {
			super(title);
			addClickHandler(this);
		}
		
		@Override
		public void onClick(ClickEvent event) {
	
		}
	}
	
}
