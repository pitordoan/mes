package com.cimpoint.mes.client.views.designers;

import java.util.Set;


public class DrawTransition {
	private Connector connector;
	
	public DrawTransition(Connector connector) {
		this.connector = connector;
	}

	public String getName() {
		return this.connector.getName();
	}

	public Set<String> getReasons() {
		return null;
	}
}
