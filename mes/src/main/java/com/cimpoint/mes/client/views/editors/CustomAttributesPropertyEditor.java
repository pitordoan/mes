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

import java.util.Map;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.views.editors.ValuesEditorDialog.Response;
import com.smartgwt.client.widgets.layout.VLayout;

public class CustomAttributesPropertyEditor extends ModelPropertyEditor {

	private ModelEditor modelEditor;
	
	public CustomAttributesPropertyEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
	
	@Override
	public void show() {
		Map<String, String> data = this.getModelProperty().getValueAsMap();	
		if (data != null && !data.isEmpty()) {
			final ValuesEditorDialog dlg = new ValuesEditorDialog((VLayout) modelEditor.getModelDesigner(), "Custom Attributes Editor", 260, 380);
			dlg.show(data, new Response.Button[] {Response.Button.OK, Response.Button.Cancel}, new CallbackHandler<Response>() {
				@Override
				public void onSuccess(Response resp) {
					if (resp.getButton() == Response.Button.Cancel) return;				
					final Map<String, String> valuesMap = resp.getReturnData();
					if (valuesMap != null && !valuesMap.isEmpty()) {	
						getModelProperty().setValueMap(valuesMap);
						modelEditor.setEditing(true);
					}
				}	
			});
		}
	}
}
