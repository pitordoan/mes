/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import java.util.ArrayList;
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
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.filters.LotFilter;
import com.cimpoint.mes.common.filters.UnitFilter;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

public class WOTrackingTreeGrid extends VLayout{

	private WOTrackingView<?> containerView;
	private TreeGrid itemTreeGrid;
	private Tree data;
	private Lot lot;
	private Unit unit;
	private WorkOrder wo;
	private WorkOrderItem woi;
	private Container container;
	private Batch batch;

	private List<ItemTreeNode> nodes = new ArrayList<ItemTreeNode>();

	//controllers
	private WorkOrderController workOrderController = MESApplication.getMESControllers().getWorkOrderController();
	private LotController lotController = MESApplication.getMESControllers().getLotController();
	private UnitController unitController = MESApplication.getMESControllers().getUnitController();

	private WOTrackingDetails woDetails;

	public WOTrackingTreeGrid(WOTrackingView<?> containerView, int elementMargin) {
		super(elementMargin);
		this.containerView = containerView;
		createView();
	}

	public WOTrackingTreeGrid(WOTrackingView<?> containerView, int elementMargin, WOTrackingDetails woDetails) {
		super(elementMargin);
		this.woDetails = woDetails;
		this.containerView = containerView;
		createView();
	}

	public WOTrackingTreeGrid() {
		super();
		createView();
	}

	public void createView(){
		this.setShowEdges(true);  
		this.setWidth("140px");  
		this.setMembersMargin(10);  
		this.setLayoutMargin(10);
		data = new Tree();  
		data.setModelType(TreeModelType.PARENT);  
		data.setRootValue(1);  
		data.setNameProperty("number");  
		data.setIdField("number");  
		data.setParentIdField("parentId");  
		data.setOpenProperty("true");  


		itemTreeGrid = new TreeGrid();  
		itemTreeGrid.setShowHeader(false);
		itemTreeGrid.setWidth(180);  
		itemTreeGrid.setHeight("90%");  
		itemTreeGrid.setAutoFetchData(true);  

		itemTreeGrid.setCanReorderRecords(true);  
		itemTreeGrid.setCanAcceptDroppedRecords(true);  
		itemTreeGrid.setShowOpenIcons(false);  
		itemTreeGrid.setDropIconSuffix("into");  
		itemTreeGrid.setClosedIconSuffix("");  
		itemTreeGrid.setData(data);  

		//		itemTreeGrid.draw();
		this.addMember(itemTreeGrid);
	}

	public static class ItemTreeNode extends TreeNode {  
		public ItemTreeNode(String title, String number, String parentId) {  
			setAttribute("title", title);  
			setAttribute("number", number);
			setAttribute("parentId", parentId);
		}  
	}  

	public void addTreeGridToWO() {
		ItemTreeNode[] all = new ItemTreeNode[nodes.size()];
		int i = 0;
		for (ItemTreeNode itemTreeNode : nodes) {
			all[i] = itemTreeNode;
			i++;
		}

		if(itemTreeGrid != null)
			itemTreeGrid.destroy();

		data = new Tree();  
		data.setModelType(TreeModelType.PARENT);  
		data.setRootValue(1);  
		data.setNameProperty("number");  
		data.setIdField("title");  
		data.setParentIdField("parentId");  
		data.setOpenProperty("true");  
		data.setData(all);  
		data.openAll();

		itemTreeGrid = new TreeGrid();  
		itemTreeGrid.setShowHeader(false);
		itemTreeGrid.setWidth(180);  
		itemTreeGrid.setHeight("90%");  
		itemTreeGrid.setAutoFetchData(true);  
		itemTreeGrid.setCanReorderRecords(true);  
		itemTreeGrid.setCanAcceptDroppedRecords(true);  
		itemTreeGrid.setShowOpenIcons(true);  
		itemTreeGrid.setDropIconSuffix("into");  
		itemTreeGrid.setClosedIconSuffix("");  
		itemTreeGrid.setData(data);  

		itemTreeGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				SC.say("doubleclick");
			}
		});
		
		itemTreeGrid.addNodeClickHandler(new NodeClickHandler() {

			@Override
			public void onNodeClick(NodeClickEvent event) {
				ItemTreeNode selectedNode = (ItemTreeNode)event.getNode();
				String title = selectedNode.getAttributeAsString("title");
				String number = selectedNode.getAttributeAsString("number");
				String[] titleParts = title.split(" ");
				if(title.equals(MESConstants.UNITS)) {
					showAllUnits(selectedNode);
				} else if(titleParts[0].equals(MESConstants.SHORT_WORK_ORDER)) {
					searchWO(number, selectedNode);
				} else if(titleParts[0].equals(MESConstants.SHORT_WORK_ORDER_ITEM)) {
					searchLotsFromWOI(number, selectedNode);
				} else if(titleParts[0].equals(MESConstants.SHORT_LOT)) {
//					searchLot(number, selectedNode);
					showLotDetail(number, selectedNode);
				} else if(titleParts[0].equals(MESConstants.SHORT_CONTAINER)) {
					//					TODO: Search and build Container Tree
					//					searchContainer(number, selectedNode);
				} else if(titleParts[0].equals(MESConstants.SHORT_BATCH)) {
					//					TODO: Search and build Batch Tree
					//					searchUnit(number, selectedNode);
				} else if (titleParts[0].equals(MESConstants.SHORT_UNIT)) {
					searchUnit(number, selectedNode);
				}
			}            
		});

		TreeGridField formattedField = new TreeGridField("number");  
		formattedField.setCellFormatter(new CellFormatter() {  
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
				if(record != null) {
					String recordTitle = record.getAttributeAsString("title");
					return recordTitle;
				} else {
					return "";
				}
			}  
		});

		itemTreeGrid.setFields(formattedField);  

		itemTreeGrid.draw();  
		this.addMember(itemTreeGrid);
	}

	public void buildWOTree() {
		if(wo != null) {
			if(nodes.size() > 0)
				nodes.clear();
			String woNumber = wo.getNumber();
			ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ woNumber, woNumber, " ");
			nodes.add(root);
			addTreeGridToWO();
		} else {
			MESApplication.showError("Work Order is empty.");
		}
	}

	public void buildWOITree() {
		if(woi != null) {
			if(nodes.size() > 0)
				nodes.clear();
			String woNumber = woi.toEntity().getWorkOrder().getNumber();
			ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ woNumber, woNumber, " ");
			String woiNumber = woi.getNumber();
			ItemTreeNode woi = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber, woiNumber, MESConstants.SHORT_WORK_ORDER + " "+ woNumber);
			
			nodes.add(root);
			nodes.add(woi);
			addTreeGridToWO();
		} else {
			MESApplication.showError("Work Order Item is empty");
		}
	}

	public void buildContainerTree() {
		if(container != null) {
			if(nodes.size() > 0)
				nodes.clear();
			String woNumber = container.toEntity().getWorkOrderNumber();
			ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ woNumber, woNumber, " ");
			String woiNumber = container.toEntity().getWorkOrderItemNumber();
			ItemTreeNode woi = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber, woiNumber, MESConstants.SHORT_WORK_ORDER + " "+ woNumber);
			ItemTreeNode containerNode = new ItemTreeNode(MESConstants.SHORT_CONTAINER + " "+ container.getName(), container.getName(), MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber);
			
			nodes.add(root);
			nodes.add(woi);
			nodes.add(containerNode);
			addTreeGridToWO();
		} else {
			MESApplication.showError("Work Order Item is empty");
		}
	}

	public void buildBatchTree() {
		if(batch != null) {
			if(nodes.size() > 0)
				nodes.clear();
			String woNumber = batch.toEntity().getWorkOrderNumber();
			ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ woNumber, woNumber, " ");
			nodes.add(root);
			
			
			String woiNumber = batch.toEntity().getWorkOrderItemNumber();
			ItemTreeNode woi = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber, woiNumber, MESConstants.SHORT_WORK_ORDER + " "+ woNumber);
			nodes.add(woi);
			String parent = MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber;
			
			EContainer eContainer = batch.toEntity().getContainer();
			if(eContainer != null){
				ItemTreeNode containerNode = new ItemTreeNode(MESConstants.SHORT_CONTAINER + " "+ eContainer.getNumber(), eContainer.getNumber(), MESConstants.SHORT_WORK_ORDER_ITEM + " "+ woiNumber);
				nodes.add(containerNode);
				parent = MESConstants.SHORT_CONTAINER + " "+ eContainer.getNumber();
			}
			
			ItemTreeNode batchNode = new ItemTreeNode(MESConstants.SHORT_BATCH + " "+ batch.getNumber(), batch.getNumber(), parent);
			nodes.add(batchNode);
			
			addTreeGridToWO();
		} else {
			MESApplication.showError("Work Order Item is empty");
		}
	}
	
	public void buildUnitTree() {
		if(unit != null) {
			EUnit entity = unit.toEntity();

			//will put container and batch later
			if(nodes.size() > 0)
				nodes.clear();
			final String workOrderNumber = entity.getWorkOrderNumber();
			if(!MESUtils.Strings.isEmptyString(workOrderNumber)) {
				ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ workOrderNumber, workOrderNumber, "");
				nodes.add(root);
				String woiNumber = entity.getWorkOrderItemNumber();

				String unitParent = "";
				ItemTreeNode woiNode = null;
				if(!MESUtils.Strings.isEmptyString(woiNumber)) {
					unitParent = MESConstants.SHORT_WORK_ORDER_ITEM  + " " + woiNumber;
					woiNode = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM  + " "+ woiNumber, woiNumber, root.getAttribute("title"));
					nodes.add(woiNode);
				}

				EContainer container = entity.getContainer();
				ItemTreeNode containerNode = null;
				if(container != null){
					String containerNumber = container.getNumber();
					containerNode = new ItemTreeNode(MESConstants.SHORT_CONTAINER + " " + containerNumber, containerNumber, woiNode != null ? woiNode.getAttribute("title") : root.getAttribute("title"));
					nodes.add(containerNode);
					unitParent = MESConstants.SHORT_CONTAINER + " " +containerNumber;
				}

				ELot elot = entity.getLot();
				ItemTreeNode lotNode = null;
				if(elot != null) {
					String lotNumber = elot.getNumber();
					lotNode = new ItemTreeNode(MESConstants.SHORT_LOT + " " + lotNumber, lotNumber, container != null ? containerNode.getAttribute("title") : woiNode.getAttribute("title"));
					nodes.add(lotNode);
					unitParent = MESConstants.SHORT_LOT + " " + lotNumber;
				}

				ItemTreeNode unitNode = new ItemTreeNode(MESConstants.SHORT_UNIT + " "+ unit.getSerialNumber(), unit.getSerialNumber(), unitParent);
				nodes.add(unitNode);

				addTreeGridToWO();
			} else {
				MESApplication.showError("Unit " + unit.getSerialNumber() + " does not have Work Order Number.");
			}
		}
	}
	

	public void buildLotTree() {
		if(lot != null) {
			ELot entity = lot.toEntity();

			//will put container and batch later
			if(nodes.size() > 0)
				nodes.clear();
			final String workOrderNumber = entity.getWorkOrderNumber();
			if(!MESUtils.Strings.isEmptyString(workOrderNumber)) {
				ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ workOrderNumber, workOrderNumber, "");
				nodes.add(root);
				String woiNumber = entity.getWorkOrderItemNumber();
				String lotParent = MESConstants.SHORT_WORK_ORDER_ITEM  + " " + woiNumber;
				ItemTreeNode woiNode = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM  + " "+ woiNumber, woiNumber, root.getAttribute("title"));
				nodes.add(woiNode);

				EContainer container = entity.getContainer();
				ItemTreeNode containerNode = null;
				if(container != null){
					String containerNumber = container.getNumber();
					containerNode = new ItemTreeNode(MESConstants.SHORT_CONTAINER + " " + containerNumber, containerNumber, woiNode.getAttribute("title"));
					nodes.add(containerNode);
					lotParent = MESConstants.SHORT_CONTAINER + " " +containerNumber;
				}

				EBatch batch = entity.getProcessBatch();
				ItemTreeNode batchNode = null;
				if(batch != null) {
					String batchNumber = batch.getName();
					batchNode = new ItemTreeNode(MESConstants.SHORT_BATCH + " " + batchNumber, batchNumber, container != null ? containerNode.getAttribute("title") : woiNode.getAttribute("title"));
					nodes.add(batchNode);
					lotParent = MESConstants.SHORT_BATCH + " " + batchNumber;
				}

				ItemTreeNode lotNode = new ItemTreeNode(MESConstants.SHORT_LOT + " "+ lot.getNumber(), lot.getNumber(), lotParent);
				nodes.add(lotNode);

				addTreeGridToWO();
			} else {
				MESApplication.showError("Lot " + lot.getNumber() + " does not have Work Order Number.");
			}
		}
	}

	private void searchWO(final String number) {
		workOrderController.findWorkOrderByNumber(number, new CallbackHandler<WorkOrder>() {

			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Error to search Work Order Number " + number + ".");
			}

			@Override
			public void onSuccess(WorkOrder result) {

				if(result != null) {
				}
			}});
	}
	
	private void showAllUnits(final ItemTreeNode selectedNode) {
		final ItemTreeNode parentNode = (ItemTreeNode)data.getParent(selectedNode);
		final String title = parentNode.getAttributeAsString("title");
		final String number = parentNode.getAttributeAsString("number");
		unitController.findUnitNumbersByLotNumber(number, new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> result) {
				for (String string : result) {
					ItemTreeNode unitNode = new ItemTreeNode(MESConstants.SHORT_UNIT + " " + string, string, title);
					data.add(unitNode, parentNode);
				}
				
				data.remove(selectedNode);
				data.openAll();
				itemTreeGrid.redraw();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Search Lot number " + number + " has problem.");
			}
		});
	}
	
	private void searchUnit(final String unitNumber, final ItemTreeNode selectedNode) {
		UnitFilter unitFilter = new UnitFilter();
		unitFilter.whereId().isLike(unitNumber);
		unitController.findUnitByNumber(unitNumber, new CallbackHandler<Unit>() {

			@Override
			public void onSuccess(Unit result) {
				if(result != null) {
					woDetails.initUnitStack();
					woDetails.setUnit(result);
					woDetails.showUnitDetails();
				} else {
					MESApplication.showError("Unit " + unitNumber + " does not have any children.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Unit " + unitNumber + " does not have any children.");
			}
		});
	}
	
	private void showLotDetail(final String lotNumber, final ItemTreeNode selectedNode) {
		ItemTreeNode unitNode = new ItemTreeNode(MESConstants.UNITS, MESConstants.UNITS, selectedNode.getAttributeAsString("title"));
		data.add(unitNode, selectedNode);
		data.openAll();
		lotController.findLotByNumber(lotNumber, new CallbackHandler<Lot>() {
			
			@Override
			public void onSuccess(Lot result) {
				if(result != null) {
					woDetails.setLot(result);
					woDetails.showLotDetails();
				} 
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Error in searching lot " + lotNumber);
			}
		});
	}

	/*private void searchLot(final String lotNumber, final ItemTreeNode selectedNode) {
		UnitFilter unitFilter = new UnitFilter();
		unitFilter.whereLotNumber().isLike(lotNumber);
		
		unitController.findUnits(unitFilter, new CallbackHandler<Set<Unit>>() {

			@Override
			public void onSuccess(Set<Unit> result) {
				if(result != null) {
					for (Unit unit : result) {
						ItemTreeNode unitNode = new ItemTreeNode(MESConstants.SHORT_UNIT + " " + unit.getSerialNumber(), unit.getSerialNumber(), selectedNode.getAttributeAsString("title"));
						data.add(unitNode, selectedNode);
					}

					data.openAll();
					itemTreeGrid.redraw();
					lotController.findLotByNumber(lotNumber, new CallbackHandler<Lot>() {
						
						@Override
						public void onSuccess(Lot result) {
							if(result != null) {
								woDetails.setLot(result);
								woDetails.displayLot();
							} else {
								
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							
						}
					});
				} else {
					MESApplication.showError("Lot " + lotNumber + " does not have any Units.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Error in searching lot.");
			}
		});
	}*/

	private void searchWO(final String number, final ItemTreeNode selectedNode) {
		workOrderController.findWorkOrderByNumber(number, new CallbackHandler<WorkOrder>() {

			@Override
			public void onFailure(Throwable caught) {
				MESApplication.showError("Error to search Work Order Number " + number + ".");
			}

			@Override
			public void onSuccess(WorkOrder result) {

				if(result != null) {
					if(nodes.size() > 0)
						nodes.clear();
					ItemTreeNode root = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER + " "+ number, number, " ");
					String parent = MESConstants.SHORT_WORK_ORDER + " "+ number;
					nodes.add(root);
					Set<WorkOrderItem> workOrderItems = result.getItems();
					for (final WorkOrderItem workOrderItem : workOrderItems) {
						ItemTreeNode woiNode = new ItemTreeNode(MESConstants.SHORT_WORK_ORDER_ITEM + " "+ workOrderItem.getNumber(), workOrderItem.getNumber(), parent);
						nodes.add(woiNode);
					}
					addTreeGridToWO();

				} else {
					MESApplication.showError("Work Order " + number + " does not exist any WOI.");
				}
			}
		});
	}


	private void searchLotsFromWOI(final String number, final ItemTreeNode selectedNode) {
		
		lotController.findLotNumbersByWorkOrderItemNumber(number, new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> result) {
				if(result != null){
					for (String lotNumber : result) {
						ItemTreeNode lotNode = new ItemTreeNode(MESConstants.SHORT_LOT + " "+ lotNumber, 
								lotNumber, selectedNode.getAttributeAsString("title"));
						data.add(lotNode, selectedNode);
					}
					data.openAll();
					itemTreeGrid.redraw();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	private void searchWOI(final String number, final ItemTreeNode selectedNode) {
		LotFilter lotFilter = new LotFilter();
		lotFilter.whereWorkOrderItemNumber().isLike(number);
		lotController.findLots(lotFilter, new CallbackHandler<Set<Lot>>(){

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Set<Lot> result) {
				if(result != null){
					for (Lot lot : result) {
						ItemTreeNode lotNode = new ItemTreeNode(MESConstants.SHORT_LOT + " "+ lot.getNumber(), 
								lot.getNumber(), selectedNode.getAttributeAsString("title"));
						data.add(lotNode, selectedNode);
					}
					data.openAll();
					itemTreeGrid.redraw();
				}
			}

		});

	}
/*
	public WOTrackingDetails getWoDetails() {
		return woDetails;
	}

	public void setWoDetails(WOTrackingDetails woDetails) {
		this.woDetails = woDetails;
	}*/

	public WorkOrder getWo() {
		return wo;
	}

	public void setWo(WorkOrder wo) {
		this.wo = wo;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public WOTrackingDetails getWoDetails() {
		return woDetails;
	}

	public void setWoDetails(WOTrackingDetails woDetails) {
		this.woDetails = woDetails;
	}


	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public WorkOrderItem getWoi() {
		return woi;
	}

	public void setWoi(WorkOrderItem woi) {
		this.woi = woi;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

}
