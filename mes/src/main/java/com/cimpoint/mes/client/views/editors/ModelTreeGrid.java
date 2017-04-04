package com.cimpoint.mes.client.views.editors;

import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ModelTreeGrid extends TreeGrid {
	public static enum NodeType {Sites, Areas, EditableAreas, ProductionLines, EditableProductionLines,
		WorkCenters, EditableWorkCenters, EditableEquipments, Equipments, Routings, Steps, Operations, RootModel, 
		Site, Area, ProductionLine, WorkCenter, Equipment, Routing, Step, Operation};
	
	private Tree tree;
	private boolean modified = false;
		
	public ModelTreeGrid(String width, String height) {
		this.setWidth100();  
		this.setHeight100();  
		this.setFolderIcon("pieces/16/cubes_green.png");  
		this.setNodeIcon("pieces/16/star_green.png");  
		this.setShowOpenIcons(false);  
		this.setShowDropIcons(false);  
		this.setClosedIconSuffix("");  
		this.setShowHeader(false);
			        
		TreeGridField field = new TreeGridField("Title");  
		field.setCanSort(false);
        
        this.setFields(field);  
         
        tree = new Tree();  
        tree.setModelType(TreeModelType.PARENT); 
        tree.setNameProperty("ModelName");  
        tree.setTitleProperty("Title");
        tree.setIdField("ModelName");  
        tree.setParentIdField("Parent");  
        tree.setShowRoot(false);  	
        tree.setAttribute("reportCollisions", false, true);
        setData(tree);
         
        setWidth(width);  
        setHeight(height);  
        setOverflow(Overflow.HIDDEN);     
        setSort(new SortSpecifier[] {new SortSpecifier("Title", SortDirection.ASCENDING)});        
	}
	
	public TreeNode findSelectedTreeNode() {
		return (TreeNode) this.getSelectedRecord();
	}
			
	public TreeNode[] findSelectedTreeNodes() {
		ListGridRecord[] selRecords = this.getSelectedRecords();
		TreeNode[] selNodes = new TreeNode[selRecords.length];
		int i = 0;
		for (ListGridRecord r: selRecords) {
			TreeNode node = (TreeNode) r;
			selNodes[i++] = node;
		}
		
		return selNodes;
	}
	
	public TreeNode addTreeNode(NodeType type, String nodeName, String nodeTitle, String iconURL) {
		TreeNode node = new TreeNode(nodeName);
		node.setAttribute("Type", type.toString());
		node.setAttribute("Parent", "Root");
		node.setAttribute("ModelName", nodeName);
		node.setAttribute("Title", nodeTitle);
		node.setIcon(iconURL);
        tree.add(node, "Root");
        this.addData(node);
        
		return node;
	}
	
	public TreeNode addTreeNode(NodeType type, String parentPath, String nodeName, String nodeTitle, String iconURL) {
		TreeNode node = new TreeNode(nodeName);  
		node.setAttribute("Type", type.toString());
		node.setAttribute("Parent", parentPath);
		node.setAttribute("ModelName", nodeName); 
		node.setAttribute("Title", nodeTitle);
		node.setIcon(iconURL);
        tree.add(node, parentPath);
		this.addData(node);
        
		return node;
	}

	public void removeSelectedTreeNodes() {
		this.removeSelectedData();
	}

	public boolean isModified() {
		return modified;
	}
		
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public boolean containsTreeNodeName(String name) {
		TreeNode[] nodes = tree.getAllNodes();
		if (nodes != null) {
			for (TreeNode n: nodes) {
				if (n.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getPathToNode(TreeNode node) {
		return this.tree.getParentPath(node) + "/" + node.getName();
	}

	public void openTreeNode(TreeNode node) {
		this.tree.openAll(node);
	}

	public TreeNode findTreeNode(String path) {
		return this.tree.find(path);
	}
	
	public int getChildNodeCount(TreeNode node) {
		return tree.getChildren(node).length;
	}
	
	@Override
	public void clear() {
		this.tree.removeList(tree.getAllNodes());
	}
}
