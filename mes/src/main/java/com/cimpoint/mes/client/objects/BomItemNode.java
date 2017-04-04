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
package com.cimpoint.mes.client.objects;

import com.smartgwt.client.widgets.tree.TreeNode;

public class BomItemNode extends TreeNode {
	
	private BomItem bomItem;
	private String parentId;
	
	public BomItemNode(String id, String name, String revision, String parendId) {
		setAttribute("id", id);
		setAttribute("partName", name);
		setAttribute("revision", revision);
		setAttribute("parentId", parendId);
	}
	
	public BomItemNode(BomItem item, String parentId) {
		this.bomItem = item;
		this.parentId = parentId;
		setAttribute("id", item.getNameRevision());
		setAttribute("partName", item.getName());
		setAttribute("revision", item.getRevision());
		setAttribute("parentId", parentId);
	}

	public BomItem getBomItem() {
		return bomItem;
	}

	public void setBomItem(BomItem bomItem) {
		this.bomItem = bomItem;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
