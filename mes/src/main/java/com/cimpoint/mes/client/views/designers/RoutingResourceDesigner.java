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
package com.cimpoint.mes.client.views.designers;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.ChainCall.ChainCallbackHandler;
import com.cimpoint.mes.client.views.editors.ModelEditor;
import com.cimpoint.mes.client.views.editors.ModelTreeGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.TreeNode;

public class RoutingResourceDesigner extends VLayout implements ModelDesigner {
	private ModelTreeGrid grid;
	private ToolStripButton addButton;
	private ToolStripButton removeButton;
	private ModelEditor modelEditor;

	public RoutingResourceDesigner() {
		this.setWidth("*");
		this.setShowResizeBar(true);

		grid = new ModelTreeGrid("100%", "100%");

		ToolStrip editorToolStrip = new ToolStrip();
		editorToolStrip.setSize("100%", "30px");
		editorToolStrip.setAlign(Alignment.RIGHT);

		ToolStripButton refreshButton = new ToolStripButton();
		refreshButton.setIcon("[SKIN]actions/refresh.png");
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modelEditor.onRefreshButtionClicked(new CallbackHandler<Void>() {
					@Override
					public void onSuccess(Void result) {
						modelEditor.setEditing(false);
					}
				});
			}
		});

		addButton = new ToolStripButton();
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modelEditor.onAddButtionClicked(new CallbackHandler<Void>() {
					@Override
					public void onSuccess(Void result) {
						// modelEditor.setEditing(true);
					}
				});
			}
		});

		removeButton = new ToolStripButton();
		removeButton.setIcon("[SKIN]actions/remove.png");
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modelEditor.onRemoveButtionClicked(new CallbackHandler<Void>() {
					@Override
					public void onSuccess(Void result) {
						// modelEditor.setEditing(true);
					}
				});
			}
		});

		ToolStripButton saveButton = new ToolStripButton();
		saveButton.setIcon("[SKIN]actions/save.png");
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				performSave(null);
			}
		});

		grid.addCellClickHandler(new CellClickHandler() {
			@Override
			public void onCellClick(CellClickEvent event) {
				addButton.disable();
				removeButton.disable();
				
				TreeNode[] selNodes = grid.findSelectedTreeNodes();
				if (selNodes != null) {
					int siblingNodeCount = 0;
					
					for (TreeNode n : selNodes) {
						ModelTreeGrid.NodeType nodeType = ModelTreeGrid.NodeType.valueOf(n.getAttribute("Type"));
						if (nodeType.toString().startsWith("Editable")) {
							addButton.enable();
							break;
						} else if (!nodeType.toString().endsWith("s") && grid.getTree().getParent(n)
							.getAttribute("Type").toString().startsWith("Editable")) {
							siblingNodeCount++;
						}
					}
					
					if (siblingNodeCount == selNodes.length) {
						removeButton.enable();
					}
				}
			}
		});

		editorToolStrip.addButton(refreshButton);
		editorToolStrip.addButton(addButton);
		editorToolStrip.addButton(removeButton);
		editorToolStrip.addButton(saveButton);

		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				addButton.disable();
				removeButton.disable();
			}
		});

		setMembers(editorToolStrip, grid);
	}

	private void performSave(final ChainCallbackHandler<Void> callback) {
		modelEditor.onSaveButtionClicked(new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				modelEditor.setEditing(false);
				if (callback != null)
					callback.onSuccess(null);
			}
		});
	}

	@Override
	public void reset() {
		addButton.disable();
		removeButton.disable();
	}

	public ModelTreeGrid getModelTreeGrid() {
		return grid;
	}

	@Override
	public void onModelNameChanged(String oldName, String newName) {
		TreeNode rootNode = grid.findTreeNode(oldName);
		if (rootNode != null) {
			rootNode.setAttribute("ModelName", newName);
			rootNode.setAttribute("Title", newName);
			grid.updateData(rootNode);
			grid.redraw();
		}
	}

	@Override
	public void setModified(boolean modified) {
		grid.setModified(modified);
	}

	@Override
	public void setModelEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
}
