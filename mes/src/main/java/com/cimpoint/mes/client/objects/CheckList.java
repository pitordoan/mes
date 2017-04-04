package com.cimpoint.mes.client.objects;

import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.Persistable;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.LotController;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.common.entities.ECheckList;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.Numbered;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CheckList  extends ClientObject<ECheckList> implements Numbered, Persistable{

	private LotController lotController = MESApplication.getMESControllers().getLotController();
	private RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
	
	
	@Override
	public void save(String comment, CallbackHandler<Void> callback) {
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public String getNumber() {
		return null;
	}

	@Override
	public void setNumber(String number) {
		
	}

}
