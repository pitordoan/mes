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

package com.cimpoint.mes.common;

public class MESConstants {

	// tree node for routing list panel
	public static final String ROUTING_SITE = "New Site...";
	public static final String ROUTING_AREA = "New Area...";
	public static final String ROUTING_WORK_CENTER = "New Work Center...";
	public static final String ROUTING_PRODUCTION_LINE = "New Production Line...";
	public static final String ROUTING_OPERATION = "New Operation...";
	public static final String ROUTING_EQUIPMENT = "New Equipment...";
	public static final String ROUTING_STEP = "New Step...";
	public static final String ROUTING_TRANSITIONS = "New Transitions...";
	public static final String ROUTING_ROUTING = "New Routing...";
	public static final String SPACE_STRING = " ";
	public static final String REV_FOR_TEXT = " - Rev";
	public static final String REV_FOR_NAME = "Rev";
	
	//WO Constants
	public static final String SEARCH = "Search ";
	public static final String SEARCH_DOT = "...";
	public static final String SEPARATOR = ":";

	public static final String LOT = "Lot";
	public static final String WORK_ORDER = "Work Order";
	public static final String WORK_ORDER_ITEM = "Work Order Item";
	public static final String UNIT = "Unit";
	public static final String BATCH = "Batch";
	public static final String CONTAINER = "Container";

	public static final String SHORT_LOT = "LOT";
	public static final String SHORT_WORK_ORDER = "WO";
	public static final String SHORT_WORK_ORDER_ITEM = "WOI";
	public static final String SHORT_UNIT = "UNIT";
	public static final String SHORT_BATCH = "BATCH";
	public static final String SHORT_CONTAINER = "CONTAINER";
	
	public static final String UNITS = "Units...";
	
	//TODO: will be configed to properties file in future
	public static final int BUTTON_HEIGHT = 25;
	
	public static class Consumption {
		public static enum Type {Consumed, Scrapped, Produced};
	}
		
	public static class Material {
		public static enum Type {Part, Component};
	}
	
	public static class Object {
		public static enum Type {AccessPlan, AccessSchedule, Site, Area, Attribute, Container, 
			MaterialIO, Batch, DataSet, WorkOrder, WorkOrderItem, Lot, ProductionLine, WorkCenter, Equipment, Operation, Routing, Unit, User, CheckListItem};		
		public static enum LotType {Discrete, Process};
		public static enum UnitType {StandAlone, ContainedInLot, ContainedInUnit, ContainedInLotAndUnit};
		public static enum UnitOfMeasure {NA, Each, lb, ton, kg, g};
		public static enum StepType {Start, Inner, End};
		public static enum StepStatus {Queued, Started, Paused, Completed, Reworked};
		public static enum PartCatergory {Catergory1, Category2, Category3};
		public static enum Reason {OK, FAIL};
	}
	
	public static class Dictionary {
		public static enum Type {Message, Reason};		
	}
	
	public static class AccessSchedule {
		public static enum DayOfWeek {EveryDay, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday};		
	}
	
}
