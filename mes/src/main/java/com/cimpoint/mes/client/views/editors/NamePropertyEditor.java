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

import com.cimpoint.common.CallbackHandler;

public class NamePropertyEditor extends ModelPropertyEditor {
	private ModelEditor modelEditor;
	
	public NamePropertyEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
	
	@Override
	public void show() {
		PromptNewNameDialog dlg = new PromptNewNameDialog("Name Editor", modelEditor.getModelConfigView());
		dlg.show("Rename " + getModelProperty().getValueAsString() + " as ", new CallbackHandler<PromptNewNameDialog.Response>() {
			@Override
			public void onSuccess(PromptNewNameDialog.Response resp) {
				if (resp.getButton() == PromptNewNameDialog.Response.Button.Cancel) return;				
				final String name = resp.getReturnData();
				if (name != null && name.length() > 0) {
					getModelProperty().setValue(name);
					modelEditor.onModelNameChanged(name);
				}
			}		
		});    		
	}		
}
