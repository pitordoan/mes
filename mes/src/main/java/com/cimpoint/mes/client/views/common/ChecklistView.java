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
package com.cimpoint.mes.client.views.common;

import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.views.editors.Dialog;
import com.cimpoint.mes.client.views.wot.SearchPanel;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;


public class ChecklistView extends ComponentView<Object> {
	private ChecklistTreeGrid itemTreeGrid;
	private boolean registeredHandlers = false;
	
	public ChecklistView() {
		createView();
		registerHandlers();	
	}
	
	private void createView() {
        itemTreeGrid = new ChecklistTreeGrid();
        this.addChild(itemTreeGrid);
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		
		MESApplication.getWOTrackingView().getSearchPanel().addEventHandler(new SearchPanel.EventHandler(ChecklistView.class) {
			@Override
			public void onSearchFound(Class<?> handlerClass, Object object) {
				if (handlerClass == ChecklistView.class) { 
					if (object instanceof WorkOrder) {
						showWorkOrder((WorkOrder) object);
					}
					else if (object instanceof Lot) {
						showLot((Lot) object);
					}
					else if (object instanceof Unit) {
						showUnit((Unit) object);
					}
					else if (object instanceof Batch) {
						showBatch((Batch) object);
					}
					else if (object instanceof Container) {
						showContainer((Container) object);
					}
				}
			}			
		});
		
		registeredHandlers = true;
	}

	private void showWorkOrder(WorkOrder workOrder) {
		itemTreeGrid.clear();
		
        //TODO remove this test
        TreeNode node = itemTreeGrid.addTreeNode("item1", "Item 1", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.1", "Item 1.1", "Failed", "blah...", "pieces/16/site.png", true);
        itemTreeGrid.addTreeNode(node, "item1.2", "Item 1.2", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.3", "Item 1.3", "Reworked", "", "pieces/16/site.png", false);
        
        itemTreeGrid.addTreeNode("item2", "Item 2", "Passed", "blah...", "pieces/16/site.png", false);
	}
	
	private void showLot(Lot lot) {
		itemTreeGrid.clear();
		
        //TODO remove this test
        TreeNode node = itemTreeGrid.addTreeNode("item1", "Item 1", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.1", "Item 1.1", "Failed", "blah...", "pieces/16/site.png", true);
        itemTreeGrid.addTreeNode(node, "item1.2", "Item 1.2", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.3", "Item 1.3", "Reworked", "", "pieces/16/site.png", false);
        
        itemTreeGrid.addTreeNode("item2", "Item 2", "Passed", "blah...", "pieces/16/site.png", false);
	}
	
	private void showUnit(Unit unit) {
		itemTreeGrid.clear();
		
        //TODO remove this test
        TreeNode node = itemTreeGrid.addTreeNode("item1", "Item 1", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.1", "Item 1.1", "Failed", "blah...", "pieces/16/site.png", true);
        itemTreeGrid.addTreeNode(node, "item1.2", "Item 1.2", "Passed", "", "pieces/16/site.png", false);
        itemTreeGrid.addTreeNode(node, "item1.3", "Item 1.3", "Reworked", "", "pieces/16/site.png", false);
        
        itemTreeGrid.addTreeNode("item2", "Item 2", "Passed", "blah...", "pieces/16/site.png", false);
	}
	
	private void showBatch(Batch batch) {
		//TODO
	}
	
	private void showContainer(Container container) {
		//TODO
	}
		
	private class ChecklistTreeGrid extends ObjectTreeGrid {
		private CellEditorDialog cellEditor;
		
		public ChecklistTreeGrid() {
			cellEditor = new CellEditorDialog(this);
			
			this.setWidth100();  
			this.setHeight100();  
			//this.setFolderIcon("pieces/16/cubes_green.png");  
			//this.setNodeIcon("pieces/16/star_green.png");  
			this.setShowOpenIcons(false);  
			this.setShowDropIcons(false);  
			this.setClosedIconSuffix("");
			this.setShowHeader(true);
			this.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			this.setShowSelectedStyle(false);  
			
			//TODO uncomment these line to show partial selection
			//currently there's a bug in the nightly of build 3.1d
			//that it doesn't correctly update the parent node for partially selected
			//when one of the child node's isSelected property is updated programmatically
			//this.setShowPartialSelection(true);  
			//this.setCascadeSelection(true);  
				        
			TreeGridField fieldTitle = new TreeGridField("Item"); 
			fieldTitle.setCanEdit(false);
			fieldTitle.setCanSort(true);
			
			TreeGridField fieldStatus = new TreeGridField("Status"); 
			fieldStatus.setCanSort(false);
			
			TreeGridField fieldComments = new TreeGridField("Comments");  
			fieldComments.setCanEdit(false);
			fieldComments.setCanSort(false);
	        
	        this.setFields(fieldTitle, fieldStatus, fieldComments);  
	        	         
	        Tree tree = new Tree();  
	        tree.setModelType(TreeModelType.PARENT); 
	        tree.setNameProperty("Name");  
	        tree.setTitleProperty("Item");
	        tree.setIdField("Name");  
	        tree.setParentIdField("Parent");  	        
	        tree.setShowRoot(true); 
	        tree.setRootValue("Root");
	        tree.setAttribute("reportCollisions", false, true);
	        	        
	        setSelectionProperty("isSelected");
	        
	        setData(tree);
	         
	        setOverflow(Overflow.HIDDEN);     
	        setSort(new SortSpecifier[] {new SortSpecifier("Title", SortDirection.ASCENDING)}); 
	       		 
	        this.addCellClickHandler(new CellClickHandler() {
				@Override
				public void onCellClick(CellClickEvent event) {
					TreeNode selNode = (TreeNode) event.getRecord();
					validateCellEdit(selNode, event.getX(), event.getY());
				}	        	
	        });
	        
	        	        
	        this.addSelectionChangedHandler(new SelectionChangedHandler() {
				@Override
				public void onSelectionChanged(SelectionEvent event) {
					TreeNode selNode = ((TreeNode) (Record) event.getRecord());
					validateCellEdit(selNode, event.getX(), event.getY());
				}	        	
	        });
		}
				
		private void validateCellEdit(final TreeNode node, int x, int y) {
			boolean checked = node.getAttributeAsBoolean("isSelected");
			if (checked) {
				String status = node.getAttribute("Status");				
				String[] statusArr = new String[] {"Passed", "Failed", "Reworked"};
				String selectedStatus = (status != null && !status.isEmpty())? status : null;
				String comments = node.getAttribute("Comments");
				
				final boolean edit = (selectedStatus != null);
								
				cellEditor.show(statusArr, selectedStatus, comments, x, y, new CallbackHandler<CellEditorDialog.Response>() {	
					@Override
					public void onSuccess(CellEditorDialog.Response resp) {
						if (resp.getButton() == CellEditorDialog.Response.Button.OK) {
							String status = resp.getStatus();
							String comments = resp.getComments();
							setCellValues(node, true, status, comments);
						}
						else {
							if (!edit) {
								setCellValues(node, false, null, null);
							}
						}
						
						redraw();						
					}					
				});					
			}
			else {
				setCellValues(node, false, null, null);
				redraw();
			}
		}
				
		private void setCellValues(TreeNode node, boolean checked, String status, String comments) {
			node.setAttribute("isSelected", checked);
			node.setAttribute("Status", status);
			node.setAttribute("Comments", comments);

			TreeNode[] children = getChildrenNodes(node);
			if (children != null) {
				for (TreeNode d: children) {
					d.setAttribute("isSelected", checked);
					d.setAttribute("Status", status);
					d.setAttribute("Comments", comments);
				}
			}
			
			this.markForRedraw();
		}

		public TreeNode addTreeNode(String nodeName, String nodeTitle, 
				String status, String comments, String iconURL, boolean checked) {
			TreeNode node = this.findTreeNode(MESConstants.Object.Type.CheckListItem, nodeName);
			if (node != null) {
				return node;
			}
						
			node = new TreeNode();
			node.setAttribute("Parent", "Root");
			node.setAttribute("Name", SC.generateID() + "_" + nodeName);
			node.setAttribute("Item", nodeTitle);
			node.setAttribute("Type", MESConstants.Object.Type.CheckListItem);
			if (checked) {
				node.setAttribute("Status", status);
				node.setAttribute("Comments", comments);
			}
			node.setAttribute("isSelected", checked);
			node.setIcon(iconURL);
	        tree.add(node, "Root");
	        this.addData(node);
	        	        
			return node;
		}
				
		public TreeNode addTreeNode(TreeNode parentNode, String nodeName, 
				String nodeTitle, String status, String comments, String iconURL, boolean checked) {
			TreeNode node = this.findTreeNode(MESConstants.Object.Type.CheckListItem, nodeName);
			if (node != null) {
				this.move(node, parentNode);
				return node;
			}
			
			node = new TreeNode();  
			node.setAttribute("Parent", getPathToNode(parentNode));
			node.setAttribute("Name", SC.generateID() + "_" + nodeName); 
			node.setAttribute("Item", nodeTitle);
			node.setAttribute("Type", MESConstants.Object.Type.CheckListItem);
			if (checked) {
				node.setAttribute("Status", status);
				node.setAttribute("Comments", comments);
			}
			node.setAttribute("isSelected", checked);
			node.setIcon(iconURL);
	        tree.add(node, getPathToNode(parentNode));
			this.addData(node);
	        
			return node;
		}
	}
	
	public static class CellEditorDialog extends Dialog { 
		private DynamicForm contentForm;
		private SelectItem statusItem;
		private TextAreaItem commentsItem;
		protected List<String> data;
		private final int BUTTON_WIDTH = 80;
		private CallbackHandler<Response> callback;

		public CellEditorDialog(Canvas parent) {
			super(parent, "Checklist Editor", null, 350, 220);
			newButton(Response.Button.OK);
			newButton(Response.Button.Cancel);			
		}
		
		protected void newButton(final Response.Button button) {
			final String buttonID = button.toString();
			String caption = button.toString().replaceAll("_", " ");
			IButton btn = new IButton(caption);
			btn.setWidth(BUTTON_WIDTH);
			btn.setShowRollOver(true);
			btn.setID(button.toString());
			
			super.addButton(btn);

			btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (buttonID.equals(Response.Button.OK.toString())) {
						if (contentForm.validate()) {
							String status = statusItem.getValueAsString();
							String comments = commentsItem.getValueAsString();
							Response resp = new Response(button, status, comments);
							callback.onSuccess(resp);	
							hide();							
						}
					} else {
						Response resp = new Response(button, null, null);
						callback.onSuccess(resp);	
						hide();						
					}
				}
			});
		}
		
		

		public void show(String[] data, String selectedStatus, String comments, int x, int y, CallbackHandler<Response> callback) {
			this.callback = callback;

			statusItem.setValueMap(data);
			statusItem.setValue(selectedStatus);
			commentsItem.setValue(comments);

			this.show();
			super.moveTo(this.getAbsoluteLeft(), y + 10);
		}
		
		@Override
		public void hide() {
			statusItem.setValue((String)null);
			commentsItem.setValue((String) null);	
			super.hide();
		}
		
		@Override
		protected Canvas getContentCanvas() {
			if (contentForm == null) {
				contentForm = new DynamicForm();
				contentForm.setMargin(10);
				
				statusItem = new SelectItem("status", "Status");
				statusItem.setWidth("100%");
				statusItem.setRequired(true);
				
				commentsItem = new TextAreaItem("comments", "Comments");
				commentsItem.setWidth("100%");
				commentsItem.setHeight(70);
				
				contentForm.setFields(new FormItem[] {statusItem, commentsItem});
			}
			return contentForm;
		}
				
		public static class Response {
			public static enum Button {
				OK, Cancel
			};

			private Button button;
			private String status;
			private String comments;

			public Response(Button button, String status, String comments) {
				this.setButton(button);
				this.status = status;
				this.comments = comments;
			}
			
			public String getStatus() {
				return status;
			}

			public String getComments() {
				return comments;
			}
			
			public Button getButton() {
				return button;
			}

			public void setButton(Button button) {
				this.button = button;
			}
		}
	}
}
