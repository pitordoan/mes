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

import java.util.HashMap;
import java.util.Map;

import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ObjectTreeGrid extends TreeGrid {
	protected Tree tree;
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	
	@Override
	public void setData(Tree tree) {
		this.tree = tree;
		super.setData(tree);
	}
			
	public MESConstants.Object.Type getNodeType(TreeNode treeNode) {
		String type = treeNode.getAttribute("Type");
		return MESConstants.Object.Type.valueOf(type);
	}
	
	public void selectTreeNode(TreeNode treeNode) {
		this.selectSingleRecord(treeNode);
	}
	
	public void selectTreeNodeEx(TreeNode treeNode) {
		openAllParentFolders(treeNode);
		this.selectSingleRecord(treeNode);
	}

	public TreeNode findParentNode(TreeNode treeNode) {
		return tree.getParent(treeNode);
	}
	
	public TreeNode[] getChildrenNodes(TreeNode node) {
		return tree.getChildren(node);
	}

	public int getNodeLevel(TreeNode node) {
		return tree.getLevel(node);
	}
	
	public void openFolder(TreeNode node) {
		tree.openFolder(node);
		this.selectRecord(node);
	}
	
	public void openAllParentFolders(TreeNode node) {
		TreeNode parentNode = this.findParentNode(node);
		if (parentNode != null) {
			this.openFolder(parentNode);
			openAllParentFolders(parentNode);
		}
	}

	public TreeNode findTreeNode(MESConstants.Object.Type nodeType, String nodeName) {
		TreeNode[] nodes = tree.getAllNodes();
		if (nodes != null) {
			for (TreeNode n: nodes) {
				MESConstants.Object.Type type = getNodeType(n);
				if (nodeType == type && getNodeName(n).equals(nodeName)) {
					return n;
				}
			}
		}
		return null;
	}
				
	public void move(TreeNode node, TreeNode newParent) {			
		if (tree.getParent(node) != newParent) {
			tree.move(node, newParent);
		}
	}
	
	public TreeNode findSelectedTreeNode() {
		return (TreeNode) this.getSelectedRecord();
	}
	
	public TreeNode addTreeNode(MESConstants.Object.Type type, String nodeName, String nodeTitle, String iconURL) {
		TreeNode node = this.findTreeNode(type, nodeName);
		if (node != null) {
			return node;
		}
					
		node = new TreeNode();
		node.setAttribute("Parent", "Root");
		node.setAttribute("Name", SC.generateID() + "_" + nodeName);
		node.setAttribute("Title", nodeTitle);
		node.setAttribute("Type", type.toString());
		node.setIcon(iconURL);
		tree.add(node, "Root");
        this.addData(node);
        
		return node;
	}
			
	public TreeNode addTreeNode(MESConstants.Object.Type type, TreeNode parentNode, String nodeName, String nodeTitle, String iconURL) {
		TreeNode node = this.findTreeNode(type, nodeName);
		if (node != null) {
			this.move(node, parentNode);
			return node;
		}
		
		node = new TreeNode();  
		node.setAttribute("Parent", getPathToNode(parentNode));
		node.setAttribute("Name", SC.generateID() + "_" + nodeName); 
		node.setAttribute("Title", nodeTitle);
		node.setAttribute("Type", type.toString());
		node.setIcon(iconURL);
        tree.add(node, getPathToNode(parentNode));
		this.addData(node);
        
		return node;
	}
			
	public String getNodeName(TreeNode node) {
		String name = node.getAttribute("Name");
		int p = name.lastIndexOf("_");
		if (p != -1) return name.substring(p + 1);
		return name;
	}
	
	public Object getRefObject(TreeNode node) {
		String key = getNodeType(node).toString() + "_" + getNodeName(node);
		return objectMap.get(key);
	}
	
	public String getPathToNode(TreeNode node) {
		return this.tree.getParentPath(node) + "/" + node.getAttribute("Name");
	}
	
	public void removeTreeNode(MESConstants.Object.Type type, String name) {
		TreeNode n = findTreeNode(type, name);
		if (n != null) {
			tree.remove(n);			
			String key = getNodeType(n).toString() + "_" + getNodeName(n);
			this.objectMap.remove(key);
		}
	}
	
	public void removeNodes(TreeNode[] nodes) {
		this.tree.removeList(nodes);
		
		for (TreeNode n: nodes) {
			String key = getNodeType(n).toString() + "_" + getNodeName(n);
			this.objectMap.remove(key);
		}
	}
	
	@Override
	public void clear() {
		this.tree.removeList(tree.getAllNodes());
		this.objectMap.clear();
	}
}
