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
import com.cimpoint.mes.client.views.editors.ValueSelectionDialog.Response;
import com.smartgwt.client.widgets.layout.VLayout;

public class StepRulePropertyEditor extends ModelPropertyEditor {

	private ModelEditor modelEditor;
	
	public StepRulePropertyEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
	
	@Override
	public void show() {
		String[] ruleNames = this.getModelProperty().getValueAsArray();
		
		//TODO remove this test hard-code
		ruleNames = new String[] {"rule1", "rule2", "rule3"};
		
		if (ruleNames.length > 0) {
			ValueSelectionDialog dlg = new ValueSelectionDialog((VLayout) modelEditor.getModelDesigner(), "Select Step Rule", "Rules", 300, 150);
			dlg.show(ruleNames, new Response.Button[] {Response.Button.OK, Response.Button.Cancel}, new CallbackHandler<Response>() {
				@Override
				public void onSuccess(Response resp) {
					if (resp.getButton() == Response.Button.Cancel) return;				
					String rule = resp.getReturnData();
					if (rule != null && !rule.isEmpty()) {	
						getModelProperty().setValue(rule);
						modelEditor.setEditing(true);
					}
				}							
			});
		}
	}
}
