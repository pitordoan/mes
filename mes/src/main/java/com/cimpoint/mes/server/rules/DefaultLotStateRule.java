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
package com.cimpoint.mes.server.rules;

import java.io.Serializable;


public class DefaultLotStateRule implements StateRule, Serializable {
	private static final long serialVersionUID = -7436922067769895425L;
	
	public String getState(StateInput input) throws Exception {
		return null;
	}

	public String getEntryState() {
		return "Created";
	}

	public String getExitState() {
		return "Finished";
	}

}
