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
import com.cimpoint.common.views.AppView;


public class SystemConfigurationsView extends AppView {
	private boolean viewCreated = false;
	
	@Override
	public void onInitialize(final CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void)null);
	}
	
	private void createView() {
		if (viewCreated) return;
		this.setStyleName("cimtrack-AppView");
		this.setLayoutMargin(5);
		this.setMembersMargin(5);
		ModelConfigView sectionStack = new ModelConfigView();
        addMember(sectionStack);        
        viewCreated = true;			
	}
	
	@Override
	public void onClose() {
		viewCreated = false;
	}
}
