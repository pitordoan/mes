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
package com.cimpoint.mes.client;

import com.cimpoint.common.AppConfigurations.DatabaseType;

public class MESConfigurations {
	public static enum RunMode {Development, Production};
	private static CommonConfiguration commonConfig = new CommonConfiguration();
		
	public static String getMesServerURL() {
		return "http://localhost:8080";
	}
	
	public static RunMode getRunMode() {
		return RunMode.Development; //TODO fetch from a config file later
	}

	public static DatabaseType getDatabaseType() {
		return commonConfig.getDatabaseType();
	}
}
