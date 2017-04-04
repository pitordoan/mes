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
package com.cimpoint.mes.server.rules;

import java.util.HashMap;
import java.util.Map;



public class RuleService {
	private static Map<String, StateRule> stateRuleMap = new HashMap<String, StateRule>();
	private static Map<String, StepRule> stepRuleMap = new HashMap<String, StepRule>();
	
	@SuppressWarnings("unchecked")
	public static StateRule getStateRuleByClassName(String clsName) throws Exception {
		if (clsName == null) return null;
		
		if (clsName.indexOf("$") != -1) {
			throw new Exception("Inner State rule implementation class is not supported.");
		}
		
		if (clsName.indexOf('.') == -1) {
			clsName = RuleService.class.getPackage().getName() + "." + clsName;
		}
						
		if (!stateRuleMap.containsKey(clsName)) {
			StateRule rule = null;
			Class<StateRule> cls = (Class<StateRule>) Class.forName(clsName);
			rule = cls.newInstance();
			stateRuleMap.put(clsName, rule);			
		}
		
		return stateRuleMap.get(clsName);
	}
		
	@SuppressWarnings("unchecked")
	public static StepRule getStepRuleByClassName(String clsName) throws Exception {
		try {
			if (clsName.indexOf('.') == -1) {
				clsName = RuleService.class.getPackage().getName() + "." + clsName;
			}
						
			if (!stepRuleMap.containsKey(clsName)) {
				if (clsName.indexOf("$") != -1) {
					throw new Exception("Inner StepRule implementation class is not supported.");
				}

				StepRule rule = null;
				Class<StepRule> cls = (Class<StepRule>) Class.forName(clsName);
				rule = cls.newInstance();
				stepRuleMap.put(clsName, rule);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed to create step rule by class name: " + clsName);
		}

		return stepRuleMap.get(clsName);
	}
}
