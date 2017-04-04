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
package com.cimpoint.mes.common.services;

import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/equipmentService")
public interface EquipmentService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static EquipmentServiceAsync instance;
		public static EquipmentServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(EquipmentService.class);
			}
			return instance;
		}
	}
	
	public EEquipment findEquipmentByName(String name) throws Exception;
	public EEquipment createEquipment(String equipmentName, String description, Long workCenterId, Long optOperationId) throws Exception;
    public void saveEquipment(EEquipment equipment, MESTrxInfo trxInfo) throws Exception;
}
