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

package com.cimpoint.mes.client.controllers;

import com.cimpoint.common.controllers.UserController;


public class MESControllers {

	private UserController userController;
	private DataCollectionController dataCollectionController;
	private PartRecipeController partRecipeController;
	private WorkOrderController workOrderController;
	private LotController lotController;
	private RoutingController routingController;
	private UnitController unitController;
	private DictionaryController dictionaryController;
	private ConsumptionController consumptionController; 
	private BillOfMarterialController billOfMarterialController; 
	private CustomCodeController customCodeController;

	public ConsumptionController getConsumptionController() {
		if (consumptionController == null) {
			consumptionController = new ConsumptionController();
		}
		return consumptionController;
	}

	public UserController getUserController() {
		if (userController == null) {
			userController = new UserController();
		}
		return userController;
	}

	public DataCollectionController getDataCollectionController() {
		if (dataCollectionController == null) {
			dataCollectionController = new DataCollectionController();
		}
		return dataCollectionController;
	}

	public PartRecipeController getPartRecipeController() {
		if (partRecipeController == null) {
			partRecipeController = new PartRecipeController();
		}
		return partRecipeController;
	}

	public WorkOrderController getWorkOrderController() {
		if (workOrderController == null) {
			workOrderController = new WorkOrderController();
		}
		return workOrderController;
	}

	public LotController getLotController() {
		if (lotController == null) {
			lotController = new LotController();
		}
		return lotController;
	}

	public RoutingController getRoutingController() {
		if (routingController == null) {
			routingController = new RoutingController();
		}
		return routingController;
	}

	public UnitController getUnitController() {
		if (unitController == null) {
			unitController = new UnitController();
		}
		return unitController;
	} 

	public DictionaryController getDictionaryController() {
		if (dictionaryController == null) {
			dictionaryController = new DictionaryController();
		}
		return dictionaryController;
	} 
	
	public BillOfMarterialController getBillOfMarterialController() {
		if (billOfMarterialController == null) {
			billOfMarterialController = new BillOfMarterialController();
		}
		return billOfMarterialController;
	}

	public CustomCodeController getCustomCodeController() {
		if (customCodeController == null) {
			customCodeController = new CustomCodeController();
		}
		return customCodeController;
	}
	
}
