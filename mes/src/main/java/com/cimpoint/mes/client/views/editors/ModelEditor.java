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
package com.cimpoint.mes.client.views.editors;

import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.views.designers.ModelDesigner;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.TreeNode;

public abstract class ModelEditor extends HLayout {
	public static enum Mode {Add, Update};
	
	private ModelConfigView modelConfigView;
	protected ModelDesigner modelDesigner;
	protected ModelPropertiesEditor propsEditor;
	private String title;
	private String oldName;
	private String name;
	private boolean isEditing;
	private Tab container;
		
	protected ModelEditor(ModelDesigner modelDesigner) {
		this.isEditing = false;
		this.propsEditor = new ModelPropertiesEditor(this, "20%");
		modelDesigner.setModelEditor(this);
		this.modelDesigner = modelDesigner;
		this.setMembers((VLayout) modelDesigner, propsEditor);
	}
	
	public void reset() {
		modelDesigner.reset();
	}
	
	public void setModelConfigView(ModelConfigView modelConfigView) {
		this.modelConfigView = modelConfigView;
	}
	
	public ModelConfigView getModelConfigView() {
		return modelConfigView;
	}
	
	public ModelDesigner getModelDesigner() {
		return modelDesigner;
	}
	
	public void setContainer(Tab container) {
		this.container = container;
	}
			
	public ModelPropertiesEditor getPropertiesEditor() {
		return this.propsEditor;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOldName() {
		return this.oldName;
	}
	
	public void setEditing(boolean editing) {
		if (container == null) return;
		
		this.isEditing = editing;
		modelDesigner.setModified(editing);
		
		if (!editing) {
			container.setTitle(title);
		}
		else {
			container.setTitle(makeHighlightCSSName(title));
		}
	}
	
	public boolean isEditing() {
		return this.isEditing;
	}

	protected void showValuesPickerDialog(String title, List<String> data, 
			final CallbackHandler<Response> callback) {
		ValuesPickerDialog dlg = new ValuesPickerDialog((VLayout) modelDesigner, title, 300, 340);
		dlg.show(data, new Response.Button[] {Response.Button.OK, Response.Button.Cancel}, callback);
	}
	
	protected String toNamesString(TreeNode[] nodes) {
		if (nodes != null) {
			String names = "";
			for (TreeNode n: nodes) {
				names += n.getAttribute("Name") + ", ";
			}
			if (names.endsWith(", ")) {
				return names.substring(0, names.length()-2);
			}
			return names;
		}
		return "";
	}
	
	protected void onModelNameChanged(final String newName) {
		onModelNameChanged(getName(), newName, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {				
				oldName = getName();
				setName(newName);
				setTitle(newName);
				container.setID(newName);
				container.setTitle(newName);
				
				modelConfigView.onModelNameChanged(oldName, newName);
				modelDesigner.onModelNameChanged(oldName, newName);
			}			
		});
	}
		
	private String makeHighlightCSSName(String name) {
		String cssName = "<span style=\"color: red;\">" + name + "</span>";
		return cssName;
	}
	
	public abstract void onInitialize(String name);
	
	public abstract void onClose(final CallbackHandler<Boolean> callback);
	
	public abstract void onRefreshButtionClicked(final CallbackHandler<Void> callback);
	
	public abstract void onSaveButtionClicked(final CallbackHandler<Void> callback);
	
	public abstract void onAddButtionClicked(final CallbackHandler<Void> callback);
		
	public abstract void onRemoveButtionClicked(final CallbackHandler<Void> callback);
	
	public abstract void onModelNameChanged(final String oldName, final String newName, final CallbackHandler<Void> callback);
}
