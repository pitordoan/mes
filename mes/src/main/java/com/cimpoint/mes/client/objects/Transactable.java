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
package com.cimpoint.mes.client.objects;

import com.cimpoint.common.CallbackHandler;


public interface Transactable {
	
	public void findTransitionAttributes(CallbackHandler<TransitionAttributes> callback);
	
	public void joinRouting(String routingName, String stepName, String optWorkCenterName,
			boolean stepFlowEnforcement, String comment, CallbackHandler<Void> callback);
	
	public void transact(String stepName, String workCenterName, String stepStatusName, String objectStatus, String optTransitionName, 
			String optReason, String optComment, CallbackHandler<Void> callback);
}
