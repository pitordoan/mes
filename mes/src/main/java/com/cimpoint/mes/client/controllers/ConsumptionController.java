package com.cimpoint.mes.client.controllers;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.ConsumptionService;
import com.cimpoint.mes.common.services.ConsumptionServiceAsync;

public class ConsumptionController extends AppController{

	private ConsumptionServiceAsync consumptionService; 
	
	public ConsumptionController() {
		consumptionService = ConsumptionService.Util.getInstance();
	}
	
	//to consume Material
	public void createConsumption(String materialIO, Part part, Lot lot, String comment, CallbackHandler<Void> callback){
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		if(materialIO.equals("Produce")) {
			consumptionService.producePart(lot.toEntity(), part.toEntity(), trxInfo, callback);
		} else if(materialIO.equals("Scrap")) {
			consumptionService.scrapPart(lot.toEntity(), part.toEntity(), trxInfo, callback);
		}else {
			consumptionService.consumePart(lot.toEntity(), part.toEntity(), trxInfo, callback);
		}
		
	}
	
	@Override
	public void init(CallbackHandler<Void> callback) {
		
	}

	@Override
	public void destroy() {
		
	}
	
	public void findConsumption(MESConstants.Object.Type objectType, Long objectId, CallbackHandler<Set<EConsumption>> callback) {
		consumptionService.findConsumption(objectType, objectId, callback);
	}

}
