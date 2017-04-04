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

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

//Example usage:
//UserServiceAsync userService = GWT.create(UserService.class);
//((ServiceDefTarget) userService).setRpcRequestBuilder(new CustomRpcRequestBuilder());

public class CustomRpcRequestBuilder extends RpcRequestBuilder {

    /*private class RequestCallbackWrapper implements RequestCallback {

        private RequestCallback callback;

        RequestCallbackWrapper(RequestCallback aCallback) {
            this.callback = aCallback;
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
            Log.debug("onResposenReceived is called");
            // put the code to hide your progress bar
            callback.onResponseReceived(request, response);

        }

        @Override
        public void onError(Request request, Throwable exception) {
            Log.error("onError is called",new Exception(exception));
            // put the code to hide your progress bar
            callback.onError(request, exception);

        }

    }*/

    @Override  
    protected RequestBuilder doCreate(String serviceEntryPoint) {

        RequestBuilder rb = super.doCreate(serviceEntryPoint);
        // put the code to show your progress bar      
        //rb.
        return rb;  
    }

    /*@Override
     protected void doFinish(RequestBuilder rb) {
         super.doFinish(rb);
         rb.setCallback(new RequestCallbackWrapper(rb.getCallback()));

     }*/

}