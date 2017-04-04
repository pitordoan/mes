package com.cimpoint.mes.client.views.common;

public class ComponentEventHandler {
	private Class<?> handlerCls;
	
	public ComponentEventHandler(Class<?> handlerCls) {
		this.handlerCls = handlerCls;
	}
	
	public Class<?> getHandlerClass() {
		return handlerCls;
	}
}
