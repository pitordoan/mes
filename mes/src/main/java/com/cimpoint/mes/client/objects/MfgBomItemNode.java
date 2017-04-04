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

public class MfgBomItemNode extends TreeNode {
	
	private MfgBomItem mfgBomItem;
	private String parentId;
	
	public MfgBomItemNode(MfgBomItem item, String parentId) {
		this.mfgBomItem = item;
		this.parentId = parentId;
		setAttribute("parentId", parentId);
	}

	public MfgBomItem getMfgBomItem() {
		return mfgBomItem;
	}

	public void setMfgBomItem(MfgBomItem mfgBomItem) {
		this.mfgBomItem = mfgBomItem;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
