package com.cimpoint.mes.common.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("DatabaseConnectionService")
public interface DatabaseConnectionService extends RemoteService {
	
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static DatabaseConnectionServiceAsync instance;
		public static DatabaseConnectionServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(DatabaseConnectionService.class);
			}
			return instance;
		}
	}
}
