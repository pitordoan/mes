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
package com.cimpoint.mes.client.objects;

import java.util.Set;

import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EDictionary;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.server.rules.DefaultAutoQueuingStepRule;
import com.cimpoint.mes.server.rules.DefaultAutoStartStepRule;

public class ClientObjectUtils {

	public static class Transitions {
		public static Transition newTransition(String name, Dictionary reasons) {
			Quantity transferQuantity = null;
			EDictionary eReasons = (reasons != null)? reasons.toEntity() : null;
			CustomAttributes customAttributes = null;
			ETransition e = new ETransition(name, transferQuantity, eReasons, customAttributes);
			return new Transition(e);
		}

		public static Transition newTransition(String name, Quantity transferQuantity, Dictionary reasons, CustomAttributes customAttributes) {
			EDictionary eReasons = (reasons != null)? reasons.toEntity() : null;
			ETransition e = new ETransition(name, transferQuantity, eReasons, customAttributes);
			return new Transition(e);
		}
	}
	
	public static class Steps {
		public static Step newStep(StepType stepType, String stepName, String description, Routing optRouting, Operation optOperation, Set<WorkCenter> optWorkCenters,
				Set<StepStatus> stepStatuses, String ruleClassName, CustomAttributes customAttributes) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optEOperation = (optOperation != null)? optOperation.toEntity() : null;
			Set<EWorkCenter> optEWorkCenters = MESTypeConverter.toEntitySet(optWorkCenters);
			Set<EStepStatus> eStepStatuses = MESTypeConverter.toEntitySet(stepStatuses);
			EStep e = new EStep(stepType, stepName, description, optERouting, optEOperation, optEWorkCenters, eStepStatuses, ruleClassName, customAttributes);
			return new Step(e);
		}
		
		/*public static Step newQueuingEntryStep(String name, String description, Routing optRouting) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optOperation = null;
			Set<EWorkCenter> optWorkCenters = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			CustomAttributes customAttributes = null;
			EStep e = new EStep(StepType.Entry, name, description, optERouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}

		public static Step newQueuingEntryStep(String name, String description, Routing optRouting, Set<WorkCenter> optWorkCenters, CustomAttributes customAttributes) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optOperation = null;
			Set<EWorkCenter> optEWorkCenters = MESTypeConverter.toEntitySet(optWorkCenters);
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			EStep e = new EStep(StepType.Entry, name, description, optERouting, optOperation, optEWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}

		public static Step newQueuingExitStep(String name, String description, Routing optRouting) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optOperation = null;
			Set<EWorkCenter> optEWorkCenters = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			CustomAttributes customAttributes = null;
			EStep e = new EStep(StepType.Exit, name, description, optERouting, optOperation, optEWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}

		public static Step newQueuingExitStep(String name, String description, Routing optRouting, Set<WorkCenter> optWorkCenters, CustomAttributes customAttributes) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optOperation = null;
			Set<EWorkCenter> optEWorkCenters = MESTypeConverter.toEntitySet(optWorkCenters);
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			EStep e = new EStep(StepType.Exit, name, description, optERouting, optOperation, optEWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}

		public static Step newInnerStep(String stepName, String description, Routing optRouting, Operation optOperation, Set<WorkCenter> optWorkCenters,
				String ruleClassName, CustomAttributes customAttributes) {
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optEOperation = (optOperation != null)? optOperation.toEntity() : null;
			Set<EWorkCenter> optEWorkCenters = MESTypeConverter.toEntitySet(optWorkCenters);
			EStep e = new EStep(StepType.Inner, stepName, description, optERouting, optEOperation, optEWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}

		public static Step newAutoStartInnerStep(String stepName, String description, Routing optRouting, Operation optOperation, Set<WorkCenter> optWorkCenters,
				CustomAttributes customAttributes) {
			String name = stepName;
			ERouting optERouting = (optRouting != null)? optRouting.toEntity() : null;
			EOperation optEOperation = (optOperation != null)? optOperation.toEntity() : null;
			Set<EWorkCenter> optEWorkCenters = MESTypeConverter.toEntitySet(optWorkCenters);
			String ruleClassName = DefaultAutoStartStepRule.class.getName();
			EStep e = new EStep(StepType.Inner, name, description, optERouting, optEOperation, optEWorkCenters, ruleClassName, customAttributes);
			return new Step(e);
		}*/
		
	}
	
	public static class BomItems {
		public static BomItem newBomItem(Long containerPartId, String containerRev, String containerName, Long partId, String partRevision, String partName) {
			EBomItem e = new EBomItem(containerPartId, containerRev, containerName, partId, partRevision, partName);
			BomItem bomItem = new BomItem(e);
			return bomItem;
		}
	}
}
