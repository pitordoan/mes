package com.cimpoint.mes.common.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("DictionaryService")
public interface DictionaryService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static DictionaryServiceAsync instance;
		public static DictionaryServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(DictionaryService.class);
			}
			return instance;
		}
	}
}
