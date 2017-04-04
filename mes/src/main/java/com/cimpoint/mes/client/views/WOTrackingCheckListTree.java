package com.cimpoint.mes.client.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.common.entities.ECheckList;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class WOTrackingCheckListTree extends VLayout{
	private List<CheckListTreeNode> nodes = new ArrayList<CheckListTreeNode>();
	private TreeGrid itemTreeGrid;
	private Tree data;
	private VLayout mainLayoutTree;

	private WOTrackingView<?> containerView;
	private Lot lot;
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();

	boolean isDisplayTree = false; 

	public WOTrackingCheckListTree(WOTrackingView<?> containerView, int elementMargin) {
		super(elementMargin);
		this.containerView = containerView;
		createView();
	}

	public WOTrackingCheckListTree(WOTrackingView<?> containerView) {
		super();
		this.containerView = containerView;
		createView();
	}

	private void createView(){
		this.setWidth100();
		this.setHeight("30%");
	}

	private void addDataToTree() {
	
		CheckListTreeNode[] all = new CheckListTreeNode[nodes.size()];
		int i = 0;
		for (CheckListTreeNode itemTreeNode : nodes) {
			all[i] = itemTreeNode;
			i++;
		}
		
		data = new Tree();
		data.setModelType(TreeModelType.PARENT);
		data.setIdField("listItem");
		data.setParentIdField("parentId");
		data.setNameProperty("listItem");
		data.setRootValue(1);
		data.setData(all);

		itemTreeGrid = new TreeGrid();
		itemTreeGrid.setShowOpenIcons(false);
		itemTreeGrid.setShowDropIcons(false);
		itemTreeGrid.setClosedIconSuffix("");
		
		itemTreeGrid.setFields(new TreeGridField("listItem", "List Item"), 
				new TreeGridField("description", "Description"), 
				new TreeGridField("result", "Result"), 
				new TreeGridField("comments", "Comments"), 
				new TreeGridField("fail", "Fail"), 
				new TreeGridField("pass", "Pass"));
		itemTreeGrid.setData(data);

		itemTreeGrid.getData().openAll();
		itemTreeGrid.draw();
		WOTrackingCheckListTree.this.addMember(itemTreeGrid);
		
	}

	public VLayout getMainLayoutTree() {
		return mainLayoutTree;
	}

	public void setMainLayoutTree(VLayout mainLayoutTree) {
		this.mainLayoutTree = mainLayoutTree;
	}

	public void displayCheckList() {
		
		if(isDisplayTree){
			return;
		}else if (lot != null  ) {
			routingController.getTransitionAttributesForLot(lot.getId(), new CallbackHandler<TransitionAttributes>() {
				@Override
				public void onSuccess(TransitionAttributes trsnAttrs) {
					String stepName = trsnAttrs.getStepName();
					String routingName = trsnAttrs.getRoutingName();
					routingController.findStepByName(routingName, stepName, new CallbackHandler<Step>() {

						@Override
						public void onSuccess(Step result) {
							if(result != null) {
								ECheckList checkList = result.toEntity().getCheckList();
								if(checkList != null) {
									if(nodes != null)
										nodes.clear();
									CheckListTreeNode root = new CheckListTreeNode(checkList.getName(), checkList.getDescription(), 
											checkList.getResult() == 1 ? "Pass": "Fail" , checkList.getComment(), true, true, "");
									nodes.add(root);
									Set<ECheckList> checkLists = checkList.getChildCheckLists();
									if(checkLists != null) {
										for (ECheckList eCheckList : checkLists) {
											CheckListTreeNode checkListItem = new CheckListTreeNode(eCheckList.getName(), eCheckList.getDescription(), 
													eCheckList.getResult() == 1 ? "Pass": "Fail" , eCheckList.getComment(), true, true, checkList.getName());
											nodes.add(checkListItem);
										}
									}
									if(itemTreeGrid != null)
										itemTreeGrid.destroy();
									addDataToTree();
								}

							} else {
								MESApplication.showError("No existing transaction from " + lot.getNumber() + ".");
							}
						}

					});
				}

				@Override
				public void onFailure(Throwable t) {
					MESApplication.showError("Error to search transaction from " + lot.getNumber() + ".");
				}
			});
		}
	}

	public static class CheckListTreeNode extends TreeNode {  
		public CheckListTreeNode(String listItem, String description, String result, String comments, boolean fail, boolean pass, String parentId) {
			setAttribute("listItem", listItem);
			setAttribute("description", description);
			setAttribute("result", result);
			setAttribute("comments", comments);
			setAttribute("fail", fail);
			setAttribute("pass", pass);
			setAttribute("parentId", parentId);
		}
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}
}
