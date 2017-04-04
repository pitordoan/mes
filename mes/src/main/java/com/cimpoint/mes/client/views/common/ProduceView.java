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

public class ProduceView extends ComponentView<Object> {
	private boolean registeredHandlers = false;
	
	public ProduceView() {
		createView();
		registerListeners();	
	}
	
	private void registerListeners() {
		if (registeredHandlers) return;
		
		// TODO Auto-generated method stub
		
		registeredHandlers = true;
	}

	private void createView() {
	}
}
