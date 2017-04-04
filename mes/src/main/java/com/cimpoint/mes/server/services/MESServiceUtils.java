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

package com.cimpoint.mes.server.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.Constants;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.mes.client.logics.DefaultAutoStepRule;
import com.cimpoint.mes.client.logics.DefaultManualStepRule;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.entities.EDictionary;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.EFormFieldProperty;
import com.cimpoint.mes.common.entities.ELayout;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.Quantity;

public class MESServiceUtils {

	public static class Routings {
		
		/*@Deprecated
		public static EStep newQueuingEntryStep(String name, String description) {
			ERouting optRouting = null;
			EOperation optOperation = null;
			Set<EWorkCenter> optWorkCenters = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			CustomAttributes customAttributes = null;								
			return new EStep(StepType.Entry, name, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		}
		
		@Deprecated
		public static EStep newQueuingEntryStep(String name, String description,
				Set<EWorkCenter> optWorkCenters, CustomAttributes customAttributes) {
			ERouting optRouting = null;
			EOperation optOperation = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();								
			return new EStep(StepType.Entry, name, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		}
		
		@Deprecated
		public static EStep newQueuingExitStep(String name, String description) {
			ERouting optRouting = null;
			EOperation optOperation = null;
			Set<EWorkCenter> optWorkCenters = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();
			CustomAttributes customAttributes = null;								
			return new EStep(StepType.Exit, name, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		}
		
		@Deprecated
		public static EStep newQueuingExitStep(String name, String description, 
				Set<EWorkCenter> optWorkCenters, CustomAttributes customAttributes) {
			ERouting optRouting = null;
			EOperation optOperation = null;
			String ruleClassName = DefaultAutoQueuingStepRule.class.getName();								
			return new EStep(StepType.Exit, name, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		}
		
		//@Deprecated
		//public static EStep newInnerStep(String stepName, String description, EOperation optOperation, 
		//		Set<EWorkCenter> optWorkCenters, String ruleClassName, CustomAttributes customAttributes) {
		//	ERouting optRouting = null;								
		//	return new EStep(StepType.Inner, stepName, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		//}
		
		@Deprecated
		public static EStep newAutoStartInnerStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultAutoStartStepRule.class.getName();
			return new EStep(StepType.Inner, name, description, optRouting, optOperation, optWorkCenters, ruleClassName, customAttributes);
		}*/
				
		public static EStepStatus newStartStepStatus(String name, String nextDefaultStatusName) {
			return new EStepStatus(name, true, false, nextDefaultStatusName);
		}
		
		public static EStepStatus newEndStepStatus(String name) {
			return new EStepStatus(name, false, true, null);
		}
		
		public static EStepStatus newStartAndEndStepStatus(String name, String nextDefaultStatusName) {
			return new EStepStatus(name, true, true, nextDefaultStatusName);
		}
		
		public static EStepStatus newStepStatus(String name, String nextDefaultStatusName) {
			return new EStepStatus(name, false, false, nextDefaultStatusName);
		}
		
		public static EStep newAutoStartStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultAutoStepRule.class.getName();
			return new EStep(StepType.Start, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newManualStartStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultManualStepRule.class.getName();
			return new EStep(StepType.Start, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newAutoEndStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultAutoStepRule.class.getName();
			return new EStep(StepType.End, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newManualEndStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultManualStepRule.class.getName();
			return new EStep(StepType.End, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newAutoInnerStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultAutoStepRule.class.getName();
			return new EStep(StepType.Inner, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newManualInnerStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			String ruleClassName = DefaultManualStepRule.class.getName();
			return new EStep(StepType.Inner, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static EStep newInnerStep(String stepName, String description, EOperation optOperation, 
				Set<EWorkCenter> optWorkCenters, Set<EStepStatus> stepStatuses, String ruleClassName, CustomAttributes customAttributes ) {
			String name = stepName;
			ERouting optRouting = null;
			return new EStep(StepType.Inner, name, description, optRouting, optOperation, optWorkCenters, stepStatuses, ruleClassName, customAttributes);
		}
		
		public static ETransition newTransition(String name, EDictionary reasons) {
			Quantity transferQuantity = null;
			CustomAttributes customAttributes = null;			
			return new ETransition(name, transferQuantity, reasons, customAttributes);
		}
		
		public static ETransition newTransition(String name, Quantity transferQuantity, EDictionary reasons, CustomAttributes customAttributes) {
			return new ETransition(name, transferQuantity, reasons, customAttributes);
		}
	}
	
	public static class Forms {
		
		public static ELayout newVLayout() {
			ELayout layout = new ELayout(Constants.Form.LayoutType.VLayout);
			return layout;
		}
		
		public static ELayout newHLayout() {
			ELayout layout = new ELayout(Constants.Form.LayoutType.HLayout);
			return layout;
		}
		
		public static EForm newForm(String name) {
			EForm form = new EForm(name);
			return form;
		}
		
		public static EForm newForm(String name, Set<EFormField> formFields) {
			EForm form = new EForm(name, formFields);
			return form;
		}
		
		public static EFormField newTextField(String name, String label, int width, int height, 
				int columnSpan, boolean startNewRow, boolean endCurrentRow, int maxValueLength) {
			EFormFieldProperty widthProp = new EFormFieldProperty(Constants.Form.FieldProperty.Width, width);
			EFormFieldProperty heightProp = new EFormFieldProperty(Constants.Form.FieldProperty.Height, height);			
			EFormFieldProperty maxValueLengthProp = new EFormFieldProperty(Constants.Form.FieldProperty.MaxValueLength, maxValueLength);
			Set<EFormFieldProperty> props = new HashSet<EFormFieldProperty>();
			EFormFieldProperty[] propArr = new EFormFieldProperty[] {widthProp, heightProp, maxValueLengthProp};
			props.addAll(Arrays.asList(propArr));
			
			return new EFormField(Constants.Form.FieldType.Text, name, label, columnSpan, startNewRow, endCurrentRow, props);
		}
		
		public static EFormField newSelectField(String name, String label, int width, int height, 
				int columnSpan, boolean startNewRow, boolean endCurrentRow) {
			EFormFieldProperty widthProp = new EFormFieldProperty(Constants.Form.FieldProperty.Width, width);
			EFormFieldProperty heightProp = new EFormFieldProperty(Constants.Form.FieldProperty.Height, height);			
			Set<EFormFieldProperty> props = new HashSet<EFormFieldProperty>();
			EFormFieldProperty[] propArr = new EFormFieldProperty[] {widthProp, heightProp};
			props.addAll(Arrays.asList(propArr));
			
			return new EFormField(Constants.Form.FieldType.Select, name, label, columnSpan, startNewRow, endCurrentRow, props);
		}
	}
}
