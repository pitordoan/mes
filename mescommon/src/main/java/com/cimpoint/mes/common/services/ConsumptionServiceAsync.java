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

import java.util.Set;

import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EConsumption;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConsumptionServiceAsync {
	public void findConsumption(MESConstants.Consumption.Type consumptionType, 
			MESConstants.Object.Type objectType, Long objectId, AsyncCallback<Set<EConsumption>> callback);
	public void findConsumption(MESConstants.Object.Type objectType, Long objectId, AsyncCallback<Set<EConsumption>> callback);
    
    public void consumePart(EContainer container, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void consumePart(EBatch batch, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void consumePart(ELot lot, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void consumePart(EUnit unit, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void scrapPart(EContainer container, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void scrapPart(EBatch batch, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void scrapPart(ELot lot, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void scrapPart(EUnit unit, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);  
    public void producePart(EContainer container, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void producePart(EBatch batch, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void producePart(ELot lot, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);
    public void producePart(EUnit unit, EPart part, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    

    public void consumeComponent(EContainer container, EComponent component, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void consumeComponent(EComponent component, EBatch batch, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void consumeComponent(EComponent component, ELot lot, MESTrxInfo trxInfo, AsyncCallback<Void> callback);       
    public void scrapComponent(EComponent component, EContainer container, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void scrapComponent(EComponent component, EBatch batch, MESTrxInfo trxInfo, AsyncCallback<Void> callback);    
    public void scrapComponent(EComponent component, ELot lot, MESTrxInfo trxInfo, AsyncCallback<Void> callback); 
}
