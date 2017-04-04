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
package com.cimpoint.mes.client.views.wot;

import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.LotController;
import com.cimpoint.mes.client.controllers.UnitController;
import com.cimpoint.mes.client.controllers.WorkOrderController;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.objects.WorkOrderItem;
import com.cimpoint.mes.client.views.common.ComponentEventHandler;
import com.cimpoint.mes.client.views.common.ComponentView;
import com.cimpoint.mes.client.views.common.ObjectTreeGrid;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ObjectHierarchyPanel extends ComponentView<ObjectHierarchyPanel.EventHandler> {
	private NavTreeGrid treeGrid;
	private ToolStripButton removeButton;
	private ToolStripButton selectButton;
	private boolean registeredHandlers = false;
	private WorkOrderController woController = MESApplication.getMESControllers().getWorkOrderController();
	private LotController lotController = MESApplication.getMESControllers().getLotController();
	private UnitController unitController = MESApplication.getMESControllers().getUnitController();	
	
	public ObjectHierarchyPanel(String width, String height) {
		this.setWidth(width);
		this.setHeight(height);
		
		ToolStripButton refreshButton = new ToolStripButton();
        refreshButton.setIcon("[SKIN]actions/refresh.png");
        refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.say("TODO");
			}	
		});
        
        removeButton = new ToolStripButton();
        removeButton.setIcon("[SKIN]actions/remove.png");
        removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				treeGrid.removeSelectedData();
			}	
		});
        
		ToolStripButton upTreeButton = new ToolStripButton();
        upTreeButton.setIcon("actions/16x16/uptree.png");
        
        upTreeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final TreeNode treeNode = treeGrid.findSelectedTreeNode();
				if (treeGrid.getNodeLevel(treeNode) == 1) {
					MESConstants.Object.Type type = treeGrid.getNodeType(treeNode);
					if (type == MESConstants.Object.Type.WorkOrderItem) {
						String workOrderItemNumber = treeGrid.getNodeName(treeNode);
						MESApplication.getMESControllers().getWorkOrderController()
							.findWorkOrderItemByNumber(workOrderItemNumber, new CallbackHandler<WorkOrderItem>() {
							@Override
							public void onSuccess(WorkOrderItem woi) {
								showWorkOrder(woi.getWorkOrder(), treeNode);
							}							
						});
					}
					else if (type == MESConstants.Object.Type.Lot) {
						String lotNumber = treeGrid.getNodeName(treeNode);
						MESApplication.getMESControllers().getLotController().findLotByNumber(lotNumber, new CallbackHandler<Lot>() {
							@Override
							public void onSuccess(Lot lot) {
								lot.getWorkOrderItem(new CallbackHandler<WorkOrderItem>() {
									@Override
									public void onSuccess(WorkOrderItem woi) {
										showWorkOrderItem(woi, treeNode);
									}									
								});
							}
						});
					}
					else if (type == MESConstants.Object.Type.Unit) {
						String sn = treeGrid.getNodeName(treeNode);
						MESApplication.getMESControllers().getUnitController().findUnitByNumber(sn, new CallbackHandler<Unit>() {
							@Override
							public void onSuccess(Unit unit) {
								showLot(unit.getLot(), treeNode);
							}							
						});
					}
					else if (type == MESConstants.Object.Type.Batch) {
						SC.say("TODO");
					}
					else if (type == MESConstants.Object.Type.Container) {
						SC.say("TODO");
					}
				}
			}	
		});
        
        ToolStripButton downTreeButton = new ToolStripButton();
        downTreeButton.setIcon("actions/16x16/downtree.png");
        downTreeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final TreeNode treeNode = treeGrid.findSelectedTreeNode();
				if (treeNode != null) {
					MESConstants.Object.Type type = treeGrid.getNodeType(treeNode);
					if (type == MESConstants.Object.Type.WorkOrder) {
						showWorkOrderItems(treeNode);
					}
					else if (type == MESConstants.Object.Type.WorkOrderItem) {
						showLots(treeNode);
					}
					else if (type == MESConstants.Object.Type.Lot) {
						showUnits(treeNode);
					}
					else if (type == MESConstants.Object.Type.Unit) {
						SC.say("TODO show child units");
					}
					else if (type == MESConstants.Object.Type.Batch) {
						SC.say("TODO");
					}
					else if (type == MESConstants.Object.Type.Container) {
						SC.say("TODO");
					}
				}
			}	
		});
        
        selectButton = new ToolStripButton();
        selectButton.setIcon("actions/16x16/select.png");
        selectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TreeNode selNode = treeGrid.findSelectedTreeNode();
				if (selNode != null) {
					MESConstants.Object.Type objectType = treeGrid.getNodeType(selNode);
					String objectName = treeGrid.getNodeName(selNode);
					fireObjectSelectedEvent(objectType, objectName);
				}
			}
		});
        
        ToolStrip explorerToolStrip = new ToolStrip();
		explorerToolStrip.setSize("100%", "30px");
		explorerToolStrip.setAlign(Alignment.RIGHT);				
		explorerToolStrip.addButton(refreshButton);
		explorerToolStrip.addButton(removeButton);
		explorerToolStrip.addButton(upTreeButton);
		explorerToolStrip.addButton(downTreeButton);
		explorerToolStrip.addButton(selectButton);
		
		treeGrid = new NavTreeGrid();
				
		this.addMember(explorerToolStrip);
		this.addMember(treeGrid);
		
		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				registerHandlers();
			}			
		});
	}
	
	private void registerHandlers() {
		if (registeredHandlers) return;
		
		MESApplication.getWOTrackingView().getSearchPanel().addEventHandler(new SearchPanel.EventHandler(ObjectHierarchyPanel.class) {
			@Override
			public void onSearchFound(Class<?> handlerClass, Object object) {
				if (handlerClass == ObjectHierarchyPanel.class) { 
					if (object instanceof WorkOrder) {
						showWorkOrder((WorkOrder) object, null);
					}
					else if (object instanceof Lot) {
						showLot((Lot) object, null);
					}
					else if (object instanceof Unit) {
						showUnit((Unit) object, null);
					}
					else if (object instanceof Batch) {
						showBatch((Batch) object, null);
					}
					else if (object instanceof Container) {
						showContainer((Container) object, null);
					}
				}
			}			
		});
		
		registeredHandlers = true;
	}
	
	private void showWorkOrder(WorkOrder workOrder, TreeNode childNode) {
		if (workOrder == null) return;		

		TreeNode node = treeGrid.findTreeNode(MESConstants.Object.Type.WorkOrder, workOrder.getNumber());
		if (node == null) {
			node = treeGrid.addTreeNode(MESConstants.Object.Type.WorkOrder, workOrder.getNumber(), 
					"WO: " + workOrder.getNumber(), "pieces/16/wo.png");
		}
		
		node = showChildNode(node, childNode);
		treeGrid.selectTreeNodeEx(node);
	}

	private void showWorkOrderItem(final WorkOrderItem woi, TreeNode childNode) {
		if (woi == null) return;
				
		TreeNode node = treeGrid.findTreeNode(MESConstants.Object.Type.WorkOrderItem, woi.getNumber());
		if (node == null) {
			node = treeGrid.addTreeNode(MESConstants.Object.Type.WorkOrderItem, 
					woi.getNumber(), "WOI: " + woi.getNumber(), "pieces/16/woi.png");
		}

		node = showChildNode(node, childNode);
		treeGrid.selectTreeNodeEx(node);
	}
		
	private void showLot(final Lot lot, TreeNode childNode) {
		if (lot == null) return;	
				
		TreeNode node = treeGrid.findTreeNode(MESConstants.Object.Type.Lot, lot.getNumber());
		if (node == null) {
			node = treeGrid.addTreeNode(MESConstants.Object.Type.Lot, lot.getNumber(), 
					"Lot: " + lot.getNumber(), "pieces/16/lot.png");
		}

		node = showChildNode(node, childNode);
		treeGrid.selectTreeNodeEx(node);
	}
	
	private void showUnit(final Unit unit, TreeNode childNode) {
		if (unit == null) return;		
		
		TreeNode node = treeGrid.findTreeNode(MESConstants.Object.Type.Unit, unit.getSerialNumber());
		if (node == null) {
			node = treeGrid.addTreeNode(MESConstants.Object.Type.Unit, unit.getSerialNumber(), 
					"Unit: " + unit.getSerialNumber(), "pieces/16/unit.png");
		}
		
		node = showChildNode(node, childNode);
		treeGrid.selectTreeNodeEx(node);
	}
	
	private TreeNode showChildNode(TreeNode node, TreeNode childNode) {
		if (childNode != null) {
			//TreeNode[] children = treeGrid.getChildrenNodes(node);
			//if (children == null || children.length == 0) {
				treeGrid.move(childNode, node);
				treeGrid.openFolder(node);
				return childNode;
			//}
		}
		
		return node;
	}
	
	private TreeNode showBatch(Batch batch, TreeNode childNode) {
		return null; //TODO
	}
	
	private TreeNode showContainer(Container container, TreeNode childNode) {
		return null; //TODO
	}
	
	private void showWorkOrderItems(final TreeNode parentNode) {
		String woNumber = treeGrid.getNodeName(parentNode);
		MESApplication.getMESControllers().getWorkOrderController().findWorkOrderByNumber(woNumber, new CallbackHandler<WorkOrder>() {
			@Override
			public void onSuccess(WorkOrder workOrder) {
				Set<WorkOrderItem> items = workOrder.getItems();
				if (items != null && items.size() > 0) {
					for (WorkOrderItem item: items) {
						treeGrid.addTreeNode(MESConstants.Object.Type.WorkOrderItem, parentNode, 
								item.getNumber(), "WOI: " + item.getNumber(), "pieces/16/woi.png");
					}
					treeGrid.openFolder(parentNode);
				}
			}							
		});
	}
	
	private void showLots(final TreeNode parentNode) {
		String woi = treeGrid.getNodeName(parentNode);
		MESApplication.getMESControllers().getLotController().findLotsByWorkOrderItemNumber(woi, new CallbackHandler<Set<Lot>>() {
			@Override
			public void onSuccess(Set<Lot> lots) {
				if (lots != null && lots.size() > 0) {
					for (Lot lot: lots) {
						treeGrid.addTreeNode(MESConstants.Object.Type.Lot, parentNode, 
								lot.getNumber(), "Lot: " + lot.getNumber(), "pieces/16/lot.png");
					}
					treeGrid.openFolder(parentNode);
				}
			}							
		});
	}
	
	private void showUnits(final TreeNode parentNode) {
		String lotNumber = treeGrid.getNodeName(parentNode);
		MESApplication.getMESControllers().getLotController().findLotByNumber(lotNumber, new CallbackHandler<Lot>() {
			@Override
			public void onSuccess(Lot lot) {
				try {
					Set<Unit> units = lot.getUnits();
					if (units != null && units.size() > 0) {
						for (Unit unit: units) {
							treeGrid.addTreeNode(MESConstants.Object.Type.Unit, parentNode, 
									unit.getSerialNumber(), "Unit: " + unit.getSerialNumber(), "pieces/16/unit.png");
						}
						treeGrid.openFolder(parentNode);
					}
				} catch (Exception ex) {}
			}							
		});
	}
	
	private class NavTreeGrid extends ObjectTreeGrid {
		
		public NavTreeGrid() {
			this.setWidth100();  
			this.setHeight100();  
			//this.setFolderIcon("pieces/16/cubes_green.png");  
			//this.setNodeIcon("pieces/16/star_green.png");  
			this.setShowOpenIcons(false);  
			this.setShowDropIcons(false);  
			this.setClosedIconSuffix("");
			this.setShowHeader(false);
				        
			TreeGridField fieldTitle = new TreeGridField("Title"); 
			fieldTitle.setCanEdit(false);
			fieldTitle.setCanSort(true);
			
			this.setFields(fieldTitle);  
	        	         
	        final Tree tree = new Tree();  
	        tree.setModelType(TreeModelType.PARENT); 
	        tree.setNameProperty("Name");  
	        tree.setTitleProperty("Title");
	        tree.setIdField("Name");  
	        tree.setParentIdField("Parent");  	        
	        tree.setShowRoot(true); 
	        tree.setRootValue("Root");
	        tree.setAttribute("reportCollisions", false, true);
	        
	        setData(tree);
	         
	        setOverflow(Overflow.HIDDEN);     
	        setSort(new SortSpecifier[] {new SortSpecifier("Title", SortDirection.ASCENDING)}); 
			
			this.addCellClickHandler(new CellClickHandler() {
				@Override
				public void onCellClick(CellClickEvent event) {
					TreeNode selNode = findSelectedTreeNode();
					if (selNode != null) {
						if (tree.getLevel(selNode) == 1) {
							removeButton.enable();
						}
						else {
							removeButton.disable();
						}
						
						MESConstants.Object.Type type = getNodeType(selNode);
						if (type == MESConstants.Object.Type.WorkOrderItem) {
							selectButton.disable();
						}
						else {
							selectButton.enable();
						}
					}
				}	        	
	        });
	        
	        this.addCellDoubleClickHandler(new CellDoubleClickHandler() {
				@Override
				public void onCellDoubleClick(CellDoubleClickEvent event) {
					TreeNode node = (TreeNode) event.getRecord();
					MESConstants.Object.Type nodeType = getNodeType(node);
					
					TreeNode[] childrenNodes = getChildrenNodes(node);
					if (childrenNodes != null) {
						removeNodes(childrenNodes);
					}
					
					if (nodeType == MESConstants.Object.Type.WorkOrder) {
						showWorkOrderItems(node);
					}
					else if (nodeType == MESConstants.Object.Type.WorkOrderItem) {
						showLots(node);
					}
					else if (nodeType == MESConstants.Object.Type.Lot) {
						showUnits(node);
					}
					else if (nodeType == MESConstants.Object.Type.Batch) {
						//TODO
					}
					else if (nodeType == MESConstants.Object.Type.Unit) {
						//TODO
					}
					else if (nodeType == MESConstants.Object.Type.Container) {
						//TODO
					}
				}
	        });	     
		}
	}
	
	private void fireObjectSelectedEvent(final MESConstants.Object.Type objectType, final String objectName) {
		
		List<EventHandler> handlers = super.getEventHandlers();
		if (handlers == null) return;
		for (final EventHandler h: handlers) {
			if (objectType == MESConstants.Object.Type.WorkOrder) {
				woController.findWorkOrderByNumber(objectName, new CallbackHandler<WorkOrder>() {
					@Override
					public void onSuccess(WorkOrder object) {
						h.onObjectSelected(h.getHandlerClass(), object, objectType, objectName);
					}					
				});
			}
			else if (objectType == MESConstants.Object.Type.Lot) {
				lotController.findLotByNumber(objectName, new CallbackHandler<Lot>() {
					@Override
					public void onSuccess(Lot object) {
						h.onObjectSelected(h.getHandlerClass(), object, objectType, objectName);
					}					
				});
			}	
			else if (objectType == MESConstants.Object.Type.Unit) {
				unitController.findUnitByNumber(objectName, new CallbackHandler<Unit>() {
					@Override
					public void onSuccess(Unit object) {
						h.onObjectSelected(h.getHandlerClass(), object, objectType, objectName);
					}					
				});
			}	
			else {
				//TODO 
				throw new RuntimeException("Object type not supported yet");
			}
		}
	}
		
	public static abstract class EventHandler extends ComponentEventHandler {
		public EventHandler(Class<?> handlerCls) {
			super(handlerCls);
		}
		
		public abstract void onObjectSelected(Class<?> handlerCls, Object object, MESConstants.Object.Type objectType, String objectName);
	}
}
