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

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.smartgwt.client.widgets.layout.VLayout;

public class TransitionReasonsPropertyEditor extends ModelPropertyEditor {

	private ModelEditor modelEditor;
	
	public TransitionReasonsPropertyEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
	
	@Override
	public void show() {
		String[] reasons = this.getModelProperty().getValueAsArray();
		
		//TODO remove this test hard-code
		reasons = new String[] {"reason1", "reason2", "reason3"};
		
		if (reasons.length > 0) {
			ValuesPickerDialog dlg = new ValuesPickerDialog((VLayout) modelEditor.getModelDesigner(), "Select Transition Reason(s)", 300, 340);
			dlg.show(reasons, new Response.Button[] {Response.Button.OK, Response.Button.Cancel}, new CallbackHandler<Response>() {
				@Override
				public void onSuccess(Response resp) {
					if (resp.getButton() == Response.Button.Cancel) return;				
					Set<String> selNames = resp.getReturnData();
					if (selNames != null && !selNames.isEmpty()) {	
						getModelProperty().setValueSet(selNames);
						modelEditor.setEditing(true);
					}
				}							
			});
		}
	}
}
