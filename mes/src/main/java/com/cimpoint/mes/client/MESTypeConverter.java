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

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.entities.EUser;
import com.cimpoint.common.entities.EUserGroup;
import com.cimpoint.common.exceptions.ControllerException;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.common.objects.User;
import com.cimpoint.common.objects.UserGroup;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Attribute;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Bom;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.client.objects.Component;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.DataSet;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.Recipe;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Site;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.Traveler;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.client.objects.WorkInstruction;
import com.cimpoint.mes.client.objects.WorkOrder;
import com.cimpoint.mes.client.objects.WorkOrderItem;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EAttribute;
import com.cimpoint.mes.common.entities.EBatch;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.EContainer;
import com.cimpoint.mes.common.entities.EDataSet;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.ELot;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ERecipe;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.ETraveler;
import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.EWorkInstruction;
import com.cimpoint.mes.common.entities.EWorkOrder;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MESTypeConverter {

	@SuppressWarnings({ "unchecked" })
	public static <Entity, CObj> Set<Entity> toEntitySet(Set<CObj> srcSet) {
		try {
			Set<Entity> destSet = new HashSet<Entity>();
			if (srcSet != null) {
				for (CObj sobj : srcSet) {
					if (sobj != null) {
						Entity dobj = ((ClientObject<Entity>) sobj).toEntity();
						destSet.add(dobj);
					}
				}
			}
			return destSet;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <T1, T2> Set<T2> toClientObjectSet(Set<T1> entitySet, Class<T2> clientObjClass) {
		try {
			Set<T2> clientObjSet = new HashSet<T2>();
			if (entitySet != null) {
				for (T1 clientObj : entitySet) {
					T2 dobj = (T2) toClientObject(clientObj, clientObjClass);
					clientObjSet.add(dobj);
				}
			}
			return clientObjSet;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public static <T1, T2> T2 toClientObject(T1 srcObj, Class<T2> clientObjClass) {
		if (srcObj == null)
			return null;

		T2 dobj = null;
		if (clientObjClass == Area.class) {
			dobj = (T2) new Area((EArea) srcObj);
		} else if (clientObjClass == Attribute.class) {
			dobj = (T2) new Attribute((EAttribute) srcObj);
		} else if (clientObjClass == Batch.class) {
			dobj = (T2) new Batch((EBatch) srcObj);
		} else if (clientObjClass == Component.class) {
			dobj = (T2) new Component((EComponent) srcObj);
		}
		// else if (clientObjClass == ComponentConsumption.class) {
		// dobj = (T2) new ComponentConsumption((EComponentConsumption)
		// srcObj);
		// }
		else if (clientObjClass == Container.class) {
			dobj = (T2) new Container((EContainer) srcObj);
		} else if (clientObjClass == DataSet.class) {
			dobj = (T2) new DataSet((EDataSet) srcObj);
		} else if (clientObjClass == Equipment.class) {
			dobj = (T2) new Equipment((EEquipment) srcObj);
		} else if (clientObjClass == Lot.class) {
			dobj = (T2) new Lot((ELot) srcObj);
		} else if (clientObjClass == Operation.class) {
			dobj = (T2) new Operation((EOperation) srcObj);
		} else if (clientObjClass == Part.class) {
			dobj = (T2) new Part((EPart) srcObj);
		}
		// else if (clientObjClass == PartConsumption.class) {
		// dobj = (T2) new PartConsumption((EPartConsumption) srcObj,
		// appController);
		// }
		else if (clientObjClass == ProductionLine.class) {
			dobj = (T2) new ProductionLine((EProductionLine) srcObj);
		} else if (clientObjClass == Recipe.class) {
			dobj = (T2) new Recipe((ERecipe) srcObj);
		} else if (clientObjClass == Routing.class) {
			dobj = (T2) new Routing((ERouting) srcObj);
		} else if (clientObjClass == Site.class) {
			dobj = (T2) new Site((ESite) srcObj);
		} else if (clientObjClass == Step.class) {
			dobj = (T2) new Step((EStep) srcObj);
		} else if (clientObjClass == Transition.class) {
			dobj = (T2) new Transition((ETransition) srcObj);
		} else if (clientObjClass == Traveler.class) {
			dobj = (T2) new Traveler((ETraveler) srcObj);
		} else if (clientObjClass == Unit.class) {
			dobj = (T2) new Unit((EUnit) srcObj);
		} else if (clientObjClass == User.class) {
			dobj = (T2) new User((EUser) srcObj);
		} else if (clientObjClass == UserGroup.class) {
			dobj = (T2) new UserGroup((EUserGroup) srcObj);
		} else if (clientObjClass == WorkCenter.class) {
			dobj = (T2) new WorkCenter((EWorkCenter) srcObj);
		} else if (clientObjClass == WorkInstruction.class) {
			dobj = (T2) new WorkInstruction((EWorkInstruction) srcObj);
		} else if (clientObjClass == WorkOrder.class) {
			dobj = (T2) new WorkOrder((EWorkOrder) srcObj);
		} else if (clientObjClass == WorkOrderItem.class) {
			dobj = (T2) new WorkOrderItem((EWorkOrderItem) srcObj);
		} else if (clientObjClass == Bom.class) {
			dobj = (T2) new Bom((EBom) srcObj);
		} else if (clientObjClass == BomItem.class) {
			dobj = (T2) new BomItem((EBomItem) srcObj);
		} else {
			throw new RuntimeException("MESTypeConverter: Object type conversion to type " + clientObjClass.getName() + " is not supported.");
		}

		return dobj;
	}
	
	public static class EntityToClientObjectCallback<Entity, ClientObj> implements AsyncCallback<Entity> {
		private AsyncCallback<ClientObj> callback;
		private Class<ClientObj> clientObjCls;
		
		public EntityToClientObjectCallback(Class<ClientObj> clientObjCls, AsyncCallback<ClientObj> callback) {
			//This doesn't work for GWT compiler
			//ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			//this.clientObjCls = (Class<ClientObject>) genericSuperclass.getActualTypeArguments()[1];
			
			this.clientObjCls = clientObjCls;
			this.callback = callback;
		}
		
		public void onFailure(Throwable caught) {
			if (callback != null) {
				this.callback.onFailure(caught);
			}
			else {
				throw new ControllerException(caught);
			}			
		}
	
		public void onSuccess(Entity entity) {
			ClientObj cobj = toClientObject(entity, clientObjCls);
			callback.onSuccess(cobj);
		}
	}
	
	public static class EntityToClientObjectSetCallback<Entity, ClientObj> implements AsyncCallback<Set<Entity>> {
		private AsyncCallback<Set<ClientObj>> callback;
		private Class<ClientObj> clientObjCls;
		
		public EntityToClientObjectSetCallback(Class<ClientObj> clientObjCls, AsyncCallback<Set<ClientObj>> callback) {
			//This doesn't work for GWT compiler
			//ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			//this.clientObjCls = (Class<ClientObject>) genericSuperclass.getActualTypeArguments()[1];
			
			this.clientObjCls = clientObjCls;
			this.callback = callback;
		}
				
		public void onFailure(Throwable caught) {
			if (callback != null) {
				this.callback.onFailure(caught);
			}
			else {
				throw new ControllerException(caught);
			}	
		}
	
		public void onSuccess(Set<Entity> entitySet) {
			Set<ClientObj> cObjSet = new HashSet<ClientObj>();
			for (Entity entity : entitySet) {
				ClientObj cobj = toClientObject(entity, clientObjCls);
				cObjSet.add(cobj);
			}
			callback.onSuccess(cObjSet);
		}
	}
	
	/*public static class EntityToStringSetCallback<Entity> implements AsyncCallback<Set<Entity>> {
		private AsyncCallback<Set<String>> callback;
		private Class<String> clientObjCls;
		
		public EntityToStringSetCallback(Class<ClientObj> clientObjCls, AsyncCallback<Set<ClientObj>> callback) {
			//This doesn't work for GWT compiler
			//ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			//this.clientObjCls = (Class<ClientObject>) genericSuperclass.getActualTypeArguments()[1];
			
			this.clientObjCls = clientObjCls;
			this.callback = callback;
		}
				
		public void onFailure(Throwable caught) {
			if (callback != null) {
				this.callback.onFailure(caught);
			}
			else {
				throw new ControllerException(caught);
			}	
		}
	
		public void onSuccess(Set<Entity> entitySet) {
			Set<ClientObj> cObjSet = new HashSet<ClientObj>();
			for (Entity entity: entitySet) {
				ClientObj cobj = toClientObject(entity, clientObjCls);
				cObjSet.add(cobj);
			}
			callback.onSuccess(cObjSet);
		}
	}*/
}
