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
package com.cimpoint.mes.client.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.MESTypeConverter.EntityToClientObjectCallback;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Batch;
import com.cimpoint.mes.client.objects.Container;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Site;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.StepStatus;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.cimpoint.mes.client.objects.Unit;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESConstants.Object.StepType;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EArea;
import com.cimpoint.mes.common.entities.EDictionary;
import com.cimpoint.mes.common.entities.EEquipment;
import com.cimpoint.mes.common.entities.EOperation;
import com.cimpoint.mes.common.entities.EProductionLine;
import com.cimpoint.mes.common.entities.ERouting;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.EStep;
import com.cimpoint.mes.common.entities.EStepStatus;
import com.cimpoint.mes.common.entities.ETransition;
import com.cimpoint.mes.common.entities.ETransitionAttributes;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.entities.Quantity;
import com.cimpoint.mes.common.filters.AreaFilter;
import com.cimpoint.mes.common.filters.EquipmentFilter;
import com.cimpoint.mes.common.filters.OperationFilter;
import com.cimpoint.mes.common.filters.ProductionLineFilter;
import com.cimpoint.mes.common.filters.RoutingFilter;
import com.cimpoint.mes.common.filters.SiteFilter;
import com.cimpoint.mes.common.filters.StepFilter;
import com.cimpoint.mes.common.filters.WorkCenterFilter;
import com.cimpoint.mes.common.services.RoutingService;
import com.cimpoint.mes.common.services.RoutingServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RoutingController extends AppController {

	private RoutingServiceAsync routingService;
	private Set<Site> sites = new HashSet<Site>();
	private Set<Area> areas = new HashSet<Area>();
	private Set<WorkCenter> workCenters = new HashSet<WorkCenter>();
	private Set<Operation> operations = new HashSet<Operation>();
	private Set<Routing> routings = new HashSet<Routing>();
	private Set<Step> steps = new HashSet<Step>();
	private Set<StepStatus> stepStatuses = new HashSet<StepStatus>();
	private Set<Transition> transitions = new HashSet<Transition>();
	private Set<ProductionLine> productionLines = new HashSet<ProductionLine>();
	private Set<Equipment> equipments = new HashSet<Equipment>();

	public RoutingController() {
		routingService = RoutingService.Util.getInstance();
	}

	@Override
	public void init(final CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void cache(ClientObject<?> obj) {
		if (obj instanceof Site) {
			Site site = (Site) obj;
			if (sites.contains(site)) {
				sites.remove(site);
			}
			sites.add(site);
		} else if (obj instanceof Area) {
			Area area = (Area) obj;
			if (areas.contains(area)) {
				areas.remove(area);
			}
			areas.add(area);
		} else if (obj instanceof WorkCenter) {
			WorkCenter wc = (WorkCenter) obj;
			if (workCenters.contains(wc)) {
				workCenters.remove(wc);
			}
			workCenters.add(wc);
		} else if (obj instanceof Operation) {
			Operation op = (Operation) obj;
			if (operations.contains(op)) {
				operations.remove(op);
			}
			operations.add(op);
		} else if (obj instanceof Routing) {
			Routing routing = (Routing) obj;
			if (routings.contains(routing)) {
				routings.remove(routing);
			}
			routings.add(routing);
		} else if (obj instanceof Step) {
			Step step = (Step) obj;
			if (steps.contains(step)) {
				steps.remove(step);
			}
			steps.add(step);
		} else if (obj instanceof Transition) {
			Transition trsn = (Transition) obj;
			if (transitions.contains(trsn)) {
				transitions.remove(trsn);
			}
			transitions.add(trsn);
		} else if (obj instanceof ProductionLine) {
			ProductionLine pdl = (ProductionLine) obj;
			if (productionLines.contains(pdl)) {
				productionLines.remove(pdl);
			}
			productionLines.add(pdl);
		} else if (obj instanceof Equipment) {
			Equipment eq = (Equipment) obj;
			if (equipments.contains(eq)) {
				equipments.remove(eq);
			}
			equipments.add(eq);
		} else {
			throw new RuntimeException("Object type not supported");
		}
	}

	public void clearCache(ClientObject<?> obj) {
		if (obj instanceof Site) {
			Site site = (Site) obj;
			if (sites.contains(site)) {
				sites.remove(site);
			}
		} else if (obj instanceof Area) {
			Area area = (Area) obj;
			if (areas.contains(area)) {
				areas.remove(area);
			}
		} else if (obj instanceof WorkCenter) {
			WorkCenter wc = (WorkCenter) obj;
			if (workCenters.contains(wc)) {
				workCenters.remove(wc);
			}
		} else if (obj instanceof Operation) {
			Operation op = (Operation) obj;
			if (operations.contains(op)) {
				operations.remove(op);
			}
		} else if (obj instanceof Routing) {
			Routing routing = (Routing) obj;
			if (routings.contains(routing)) {
				routings.remove(routing);
			}
		} else if (obj instanceof Step) {
			Step step = (Step) obj;
			if (steps.contains(step)) {
				steps.remove(step);
			}
		} else if (obj instanceof Transition) {
			Transition trsn = (Transition) obj;
			if (transitions.contains(trsn)) {
				transitions.remove(trsn);
			}
		} else if (obj instanceof ProductionLine) {
			ProductionLine pdl = (ProductionLine) obj;
			if (productionLines.contains(pdl)) {
				productionLines.remove(pdl);
			}
		} else if (obj instanceof Equipment) {
			Equipment eq = (Equipment) obj;
			if (equipments.contains(eq)) {
				equipments.remove(eq);
			}
		} else {
			throw new RuntimeException("Object type not supported");
		}
	}

	public void cache(Set<Area> objSet) {
		if (objSet == null || objSet.size() == 0)
			return;

		for (Area a : objSet) {
			cache(a);
		}
	}

	public void clearCache(Class<?> objClass) {
		if (objClass == Site.class) {
			this.sites.clear();
		} else if (objClass == Area.class) {
			this.areas.clear();
		} else if (objClass == ProductionLine.class) {
			this.productionLines.clear();
		} else if (objClass == WorkCenter.class) {
			this.workCenters.clear();
		} else if (objClass == Operation.class) {
			this.operations.clear();
		} else if (objClass == Routing.class) {
			this.routings.clear();
		} else if (objClass == Step.class) {
			this.steps.clear();
		} else if (objClass == Transition.class) {
			this.transitions.clear();
		} else if (objClass == Equipment.class) {
			this.equipments.clear();
		}
	}
	
	public void clearCache(Class<?> objClass, String objName) {
		if (objClass == Site.class) {
			for (Site site: this.sites) {
				if (site.getName().equals(objName)) {
					this.sites.remove(site);
					break;
				}
			}
		} else if (objClass == Area.class) {
			for (Area area: this.areas) {
				if (area.getName().equals(objName)) {
					this.areas.remove(area);
					break;
				}
			}
		} else if (objClass == ProductionLine.class) {
			for (ProductionLine pl: this.productionLines) {
				if (pl.getName().equals(objName)) {
					this.productionLines.remove(pl);
					break;
				}
			}
		} else if (objClass == WorkCenter.class) {
			for (WorkCenter wc: this.workCenters) {
				if (wc.getName().equals(objName)) {
					this.workCenters.remove(wc);
					break;
				}
			}
		} else if (objClass == Operation.class) {
			for (Operation op: this.operations) {
				if (op.getName().equals(objName)) {
					this.operations.remove(op);
					break;
				}
			}
		} else if (objClass == Routing.class) {
			for (Routing r: this.routings) {
				if (r.getName().equals(objName)) {
					this.routings.remove(r);
					break;
				}
			}
		} else if (objClass == Step.class) {
			for (Step s: this.steps) {
				if (s.getName().equals(objName)) {
					this.steps.remove(s);
					break;
				}
			}
		} else if (objClass == Transition.class) {
			for (Transition t: this.transitions) {
				if (t.getName().equals(objName)) {
					this.transitions.remove(t);
					break;
				}
			}
		} else if (objClass == Equipment.class) {
			for (Equipment e: this.equipments) {
				if (e.getName().equals(objName)) {
					this.equipments.remove(e);
					break;
				}
			}
		}
	}
	
	public void findAllModelNames(CallbackHandler<Map<String, List<String>>> callback) {
		this.routingService.findAllModelNames(callback);
	}
	
	// ---------------- site -----------------------
	public void findSiteById(Long id, final CallbackHandler<Site> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean foundSite = false;

		// find from cache
		if (this.sites != null) {
			for (Site s : this.sites) {
				if (s.getId() == id) {
					callback.onSuccess(s);
					foundSite = true;
					return;
				}
			}
		}

		if (!foundSite) {
			// find from server
			this.routingService.findSiteById(id, new CallbackHandler<ESite>() {
				public void onSuccess(ESite esite) {
					Site site = MESTypeConverter.toClientObject(esite, Site.class);
					if (site != null) {
						sites.add(site);
					}
					callback.onSuccess(site);
				}
			});
		}
	}

	public void findSiteByName(String name, final CallbackHandler<Site> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		Site foundSite = null;

		// find from cache
		if (this.sites != null) {
			for (Site site : sites) {
				if (site != null && site.getName().equals(name)) {
					foundSite = site;
					break;
				}
			}
		}

		if (foundSite == null) {
			// find from server
			this.routingService.findSiteByName(name, new CallbackHandler<ESite>() {
				public void onSuccess(ESite esite) {
					Site site = MESTypeConverter.toClientObject(esite, Site.class);
					if (site != null) {
						sites.add(site);
					}
					callback.onSuccess(site);
				}
			});
		}
		else {
			callback.onSuccess(foundSite);
		}
	}

	public void findAllSites(final CallbackHandler<Set<Site>> callback) {
		if (sites != null && !sites.isEmpty()) {
			callback.onSuccess(sites);
		} else {
			this.routingService.findAllSites(new CallbackHandler<Set<ESite>>() {
				public void onSuccess(Set<ESite> result) {
					sites = MESTypeConverter.toClientObjectSet(result, Site.class);
					callback.onSuccess(sites);
				}
			});
		}
	}

	public void findAllSiteNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllSiteNames(callbackHandler);
	}

	public void findSiteNames(SiteFilter filter, CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findSiteNames(filter, callbackHandler);
	}

	public void findSites(final SiteFilter filter, final CallbackHandler<Set<Site>> callbackHandler) {
		this.routingService.findSites(filter, new CallbackHandler<Set<ESite>>() {
			@Override
			public void onSuccess(Set<ESite> result) {
				Set<Site> sites = MESTypeConverter.toClientObjectSet(result, Site.class);
				callbackHandler.onSuccess(sites);
			}
		});
	}

	public void createSite(String name, String desc, Set<Area> areas, CustomAttributes customAttributes, String comment,
			final CallbackHandler<Site> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EArea> eareas = MESTypeConverter.toEntitySet(areas);
		this.routingService.createSite(name, desc, eareas, customAttributes, trxInfo, new CallbackHandler<ESite>() {
			@Override
			public void onSuccess(ESite e) {
				Site site = MESTypeConverter.toClientObject(e, Site.class);
				cache(site);
				clearCache(Area.class);
				callback.onSuccess(site);
			}
		});
	}

	public void saveSite(final Site site, String comment, final CallbackHandler<Void> callback) {
		ESite e = site.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveSite(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(site);
				clearCache(Area.class);
				callback.onSuccess(result);
			}
		});
	}

	@Deprecated
	public void removeSite(final Site site, final String comment, final CallbackHandler<Void> callback) {
		ESite e = site.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeSite(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(site);
				clearCache(Area.class);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeSite(final String siteName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeSite(siteName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Site.class, siteName);
				clearCache(Area.class);
				callback.onSuccess(result);
			}
		});
	}

	// --------------------- area ---------------------
	public void findAreaById(Long id, final CallbackHandler<Area> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean foundArea = false;

		// find from cache
		if (this.areas != null) {
			for (Area a : this.areas) {
				if (a != null && a.getId() == id) {
					callback.onSuccess(a);
					foundArea = true;
					return;
				}
			}
		}

		if (!foundArea) {
			// find from server
			this.routingService.findAreaById(id, new CallbackHandler<EArea>() {
				public void onSuccess(EArea e) {
					Area area = MESTypeConverter.toClientObject(e, Area.class);
					if (area != null) {
						areas.add(area);
					}
					callback.onSuccess(area);
				}
			});
		}
	}

	public void findAreaByName(String name, final CallbackHandler<Area> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		Area foundArea = null;

		// find from cache
		if (this.areas != null) {
			for (Area a : this.areas) {
				if (a != null && a.getName().equals(name)) {
					foundArea = a;
				}
			}
		}

		if (foundArea == null) {
			// find from server
			this.routingService.findAreaByName(name, new CallbackHandler<EArea>() {
				public void onSuccess(EArea e) {
					Area area = MESTypeConverter.toClientObject(e, Area.class);
					if (area != null) {
						areas.add(area);
					}
					callback.onSuccess(area);
				}
			});
		}
		else {
			callback.onSuccess(foundArea);
		}
	}

	public void findAllAreas(final CallbackHandler<Set<Area>> callback) {
		if (areas != null && !areas.isEmpty()) {
			callback.onSuccess(areas);
		} else {
			this.routingService.findAllAreas(new CallbackHandler<Set<EArea>>() {
				public void onSuccess(Set<EArea> result) {
					areas = MESTypeConverter.toClientObjectSet(result, Area.class);
					callback.onSuccess(areas);
				}
			});
		}
	}

	public void findAreas(final AreaFilter filter, final CallbackHandler<Set<Area>> callback) {
		this.routingService.findAreas(filter, new CallbackHandler<Set<EArea>>() {
			@Override
			public void onSuccess(Set<EArea> result) {
				Set<Area> areas = MESTypeConverter.toClientObjectSet(result, Area.class);
				callback.onSuccess(areas);
			}
		});
	}

	public void findAllAreaNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllAreaNames(callbackHandler);
	}

	public void findAreaNames(final AreaFilter filter, final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAreaNames(filter, callbackHandler);
	}

	public void createArea(String name, String description, final Site optSite, Set<ProductionLine> productionLines,
			CustomAttributes customAttributes, String comment, final CallbackHandler<Area> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EProductionLine> epdls = MESTypeConverter.toEntitySet(productionLines);
		ESite esite = (optSite != null) ? optSite.toEntity() : null;
		this.routingService.createArea(name, description, esite, epdls, customAttributes, trxInfo, new CallbackHandler<EArea>() {
			@Override
			public void onSuccess(EArea e) {
				Area area = MESTypeConverter.toClientObject(e, Area.class);
				cache(area);
				clearCache(Site.class);
				clearCache(ProductionLine.class);
				callback.onSuccess(area);
			}
		});
	}

	public void saveArea(final Area area, String comment, final CallbackHandler<Void> callback) {
		EArea e = area.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveArea(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(area);
				if (area.getSite() != null) {
					area.getSite().updateArea(area); // update site cache
				}
				clearCache(ProductionLine.class);
				callback.onSuccess(result);
			}
		});
	}

	public void saveAreas(Set<Area> areas, String comment, CallbackHandler<Void> callback) {
		Set<EArea> eareas = MESTypeConverter.toEntitySet(areas);
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveAreas(eareas, trxInfo, callback);
	}

	@Deprecated
	public void removeArea(final Area area, final String comment, final CallbackHandler<Void> callback) {
		EArea e = area.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeArea(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				areas.remove(area);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeArea(final String areaName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeArea(areaName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Area.class, areaName);
				clearCache(ProductionLine.class);
				callback.onSuccess(result);
			}
		});
	}

	// --------------- work center -----------------------
	public void findWorkCenterById(Long id, final CallbackHandler<WorkCenter> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		WorkCenter foundWC = null;

		// find from cache
		if (this.workCenters != null) {
			for (WorkCenter wc : this.workCenters) {
				if (wc.getId() == id) {
					foundWC = wc;
				}
			}
		}

		if (foundWC == null) {
			// find from server
			this.routingService.findWorkCenterById(id, new CallbackHandler<EWorkCenter>() {
				public void onSuccess(EWorkCenter e) {
					WorkCenter wc = MESTypeConverter.toClientObject(e, WorkCenter.class);
					if (wc != null) {
						workCenters.add(wc);
					}
					callback.onSuccess(wc);
				}
			});
		}
		else {
			callback.onSuccess(foundWC);
		}
	}

	public void findWorkCenterByName(String name, final CallbackHandler<WorkCenter> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		WorkCenter foundWC = null;

		// find from cache
		if (this.workCenters != null) {
			for (WorkCenter wc : this.workCenters) {
				if (wc != null && wc.getName().equals(name)) {
					foundWC = wc;
				}
			}
		}

		if (foundWC == null) {
			// find from server
			this.routingService.findWorkCenterByName(name, new CallbackHandler<EWorkCenter>() {
				public void onSuccess(EWorkCenter e) {
					WorkCenter wc = MESTypeConverter.toClientObject(e, WorkCenter.class);
					if (wc != null) {
						workCenters.add(wc);
					}
					callback.onSuccess(wc);
				}
			});
		}
		else {
			callback.onSuccess(foundWC);
		}
	}

	public void findAllWorkCenters(final CallbackHandler<Set<WorkCenter>> callback) {
		if (workCenters != null && !workCenters.isEmpty()) {
			callback.onSuccess(workCenters);
		} else {
			this.routingService.findAllWorkCenters(new CallbackHandler<Set<EWorkCenter>>() {
				public void onSuccess(Set<EWorkCenter> result) {
					workCenters = MESTypeConverter.toClientObjectSet(result, WorkCenter.class);
					callback.onSuccess(workCenters);
				}

			});
		}
	}

	public void findAllWorkCenterNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllWorkCenterNames(callbackHandler);
	}

	public void findWorkCenterNames(WorkCenterFilter filter, CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findWorkCenterNames(filter, callbackHandler);
	}

	public void findWorkCenters(final WorkCenterFilter filter, final CallbackHandler<Set<WorkCenter>> callbackHandler) {
		this.routingService.findWorkCenters(filter, new CallbackHandler<Set<EWorkCenter>>() {
			@Override
			public void onSuccess(Set<EWorkCenter> result) {
				Set<WorkCenter> wcs = MESTypeConverter.toClientObjectSet(result, WorkCenter.class);
				callbackHandler.onSuccess(wcs);
			}
		});
	}

	public void createWorkCenter(String name, String description, Area optArea, Set<Equipment> equipments, final ProductionLine optProductionLine,
			CustomAttributes customAttributes, String comment, final CallbackHandler<WorkCenter> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		EArea earea = (optArea != null) ? optArea.toEntity() : null;
		Set<EEquipment> eeqs = MESTypeConverter.toEntitySet(equipments);
		EProductionLine eProductionLine = (optProductionLine != null) ? optProductionLine.toEntity() : null;
		this.routingService.createWorkCenter(name, description, earea, eeqs, eProductionLine, customAttributes, trxInfo,
				new CallbackHandler<EWorkCenter>() {
					@Override
					public void onSuccess(EWorkCenter e) {
						WorkCenter wc = MESTypeConverter.toClientObject(e, WorkCenter.class);
						cache(wc);
						if (optProductionLine != null) {
							optProductionLine.addWorkCenter(wc);
						}
						clearCache(ProductionLine.class);
						callback.onSuccess(wc);
					}
				});
	}

	public void saveWorkCenter(final WorkCenter workCenter, String comment, final CallbackHandler<Void> callback) {
		EWorkCenter e = workCenter.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveWorkCenter(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(workCenter);
				if (workCenter.getProductionLine() != null) {
					workCenter.getProductionLine().updateWorkCenter(workCenter); // update
																					// site
																					// cache
				}
				clearCache(ProductionLine.class);
				clearCache(Step.class);
				clearCache(Equipment.class);				
				callback.onSuccess(null);
			}
		});
	}

	public void saveWorkCenters(Set<WorkCenter> workCenters, String comment, CallbackHandler<Void> callbackHandler) {
		if (workCenters == null || workCenters.isEmpty()) {
			callbackHandler.onSuccess((Void) null);
		}

		Set<EWorkCenter> wcs = MESTypeConverter.toEntitySet(workCenters);
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveWorkCenters(wcs, trxInfo, callbackHandler);
	}

	@Deprecated
	public void removeWorkCenter(final WorkCenter workCenter, final String comment, final CallbackHandler<Void> callback) {
		EWorkCenter e = workCenter.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeWorkCenter(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				workCenters.remove(workCenter);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeWorkCenter(final String workCenterName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeWorkCenter(workCenterName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(WorkCenter.class, workCenterName);
				callback.onSuccess(result);
			}
		});
	}

	// ----------------------------- routing ------------------------------
	public void findRoutingById(Long id, final CallbackHandler<Routing> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		Routing foundRouting = null;

		// find from cache
		if (this.routings != null) {
			for (Routing r : this.routings) {
				if (r != null && r.getId() == id) {
					foundRouting = r;
				}
			}
		}

		if (foundRouting == null) {
			// find from server
			this.routingService.findRoutingById(id, new CallbackHandler<ERouting>() {
				public void onSuccess(ERouting e) {
					Routing r = MESTypeConverter.toClientObject(e, Routing.class);
					routings.add(r);
					callback.onSuccess(r);
				}
			});
		}
		else {
			callback.onSuccess(foundRouting);
		}
	}

	/*@Deprecated no longer support routing revision
	public void findRoutingByNameAndRevision(String name, String revision, final CallbackHandler<Routing> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.routings != null) {
			for (Routing r : this.routings) {
				if (r != null && r.getName().equals(name) && r.getRevision().equals(revision)) {
					callback.onSuccess(r);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findRoutingByNameAndRevision(name, revision, new CallbackHandler<ERouting>() {
				public void onSuccess(ERouting e) {
					Routing r = MESTypeConverter.toClientObject(e, Routing.class);
					if (r != null) {
						routings.add(r);
					}
					callback.onSuccess(r);
				}
			});
		}
	}*/

	@Deprecated /*no longer support routing revision*/
	public void findRoutingsByName(String name, final CallbackHandler<Set<Routing>> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.routings != null) {
			Set<Routing> routings = new HashSet<Routing>();
			for (Routing r : this.routings) {
				if (r != null && r.getName().equals(name)) {
					routings.add(r);
					found = true;
				}
			}
			callback.onSuccess(routings);			
		}

		if (!found) {
			// find from server
			this.routingService.findRoutingsByName(name, new CallbackHandler<Set<ERouting>>() {
				public void onSuccess(Set<ERouting> eroutings) {
					Set<Routing> routings = MESTypeConverter.toClientObjectSet(eroutings, Routing.class);
					callback.onSuccess(routings);
				}
			});
		}
	}
	
	public void findRoutingByName(String name, final CallbackHandler<Routing> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		Routing foundRouting = null;

		// find from cache
		if (this.routings != null) {
			for (Routing r : this.routings) {
				if (r != null && r.getName().equals(name)) {
					foundRouting = r;
					break;
				}
			}
		}

		if (foundRouting == null) {
			// find from server
			this.routingService.findRoutingByName(name, new CallbackHandler<ERouting>() {
				public void onSuccess(ERouting erouting) {
					Routing r = MESTypeConverter.toClientObject(erouting, Routing.class);
					callback.onSuccess(r);
				}
			});
		}
		else {
			callback.onSuccess(foundRouting);
		}
	}
		
	public void createRouting(String name, String description, Step startStep, CustomAttributes customAttributes, String comment,
			final AsyncCallback<Routing> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		EStep eStartStep = (startStep != null)? startStep.toEntity() : null;
		this.routingService.createRouting(name, description, eStartStep, customAttributes, trxInfo, new CallbackHandler<ERouting>() {
			@Override
			public void onSuccess(ERouting e) {
				Routing routing = MESTypeConverter.toClientObject(e, Routing.class);
				cache(routing);
				callback.onSuccess(routing);
			}
		});
	}
			
	@Deprecated
	public Step findStepByRoutingNameAndStepName(String routingName, String name) {
		for (Step s : steps) {
			if (s.getRouting().getName().equals(routingName) && s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	public void findAllRoutings(final CallbackHandler<Set<Routing>> callback) {
		if (routings != null && !routings.isEmpty()) {
			callback.onSuccess(routings);
		} else {
			this.routingService.findAllRoutings(new CallbackHandler<Set<ERouting>>() {
				@Override
				public void onSuccess(Set<ERouting> result) {
					routings = MESTypeConverter.toClientObjectSet(result, Routing.class);
					callback.onSuccess(routings);
				}
			});
		}
	}

	public void findAllRoutingNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllRoutingNames(callbackHandler);
	}
	
	/*public void findAllRoutingNameWithRevisions(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllRoutingNameWithRevisions(callbackHandler);
	}*/

	public void createStep(StepType type, String name, String description, Operation optOperation, Set<WorkCenter> workCenters, 
			Set<StepStatus> stepStatuses, String ruleClassName,
			CustomAttributes customAttributes, String comment, final CallbackHandler<Step> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EWorkCenter> ewcls = MESTypeConverter.toEntitySet(workCenters);
		Set<EStepStatus> eStepStatuses = MESTypeConverter.toEntitySet(stepStatuses);
		EOperation eoperation = (optOperation != null) ? optOperation.toEntity() : null;
		ERouting erouting = null;
		this.routingService.createStep(type, name, description, erouting, eoperation, ewcls, eStepStatuses, ruleClassName, customAttributes, trxInfo,
				new CallbackHandler<EStep>() {
					@Override
					public void onSuccess(EStep e) {
						Step step = MESTypeConverter.toClientObject(e, Step.class);
						cache(step);
						// clearCache(Step.class);
						clearCache(Operation.class);
						callback.onSuccess(step);
					}
				});
	}

	public void saveRouting(Routing routing, String comment, AsyncCallback<Void> callback) {
		ERouting e = routing.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveRouting(e, trxInfo, new VoidCallback(callback));
	}

	@Deprecated
	public void removeRouting(final Routing routing, final String comment, final AsyncCallback<Void> callback) {
		ERouting e = routing.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeRouting(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				routings.remove(routing);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeRouting(final String routingName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeRouting(routingName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Routing.class, routingName);
				callback.onSuccess(result);
			}
		});
	}

	// --------------------- step ----------------------------
	public void findStepById(Long id, final CallbackHandler<Step> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.steps != null) {
			for (Step s : this.steps) {
				if (s != null && s.getId() == id) {
					callback.onSuccess(s);
					found = true;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findStepById(id, new CallbackHandler<EStep>() {
				public void onSuccess(EStep e) {
					Step s = MESTypeConverter.toClientObject(e, Step.class);
					if (s != null) {
						steps.add(s);
					}
					callback.onSuccess(s);
				}
			});
		}
	}

	public void findStepByName(String routingName, String stepName, final CallbackHandler<Step> callback) {
		if (stepName == null || stepName.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.steps != null) {
			for (Step s : this.steps) {
				if (s != null && s.getRouting().getName().equals(routingName) && s.getName().equals(stepName)) {
					callback.onSuccess(s);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findStepByName(routingName, stepName, new CallbackHandler<EStep>() {
				public void onSuccess(EStep e) {
					Step s = MESTypeConverter.toClientObject(e, Step.class);
					if (s != null) {
						steps.add(s);
					}
					callback.onSuccess(s);
				}
			});
		}
	}

	public void findAllSteps(final String routingName, final CallbackHandler<Set<Step>> callback) {
		if (steps != null && !steps.isEmpty()) {
			callback.onSuccess(steps);
		} else {
			this.routingService.findSteps(routingName, new CallbackHandler<Set<EStep>>() {
				@Override
				public void onSuccess(Set<EStep> result) {
					steps = MESTypeConverter.toClientObjectSet(result, Step.class);
					callback.onSuccess(steps);
				}
			});
		}
	}

	public void findStepNames(final String routingName, final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findStepNames(routingName, callbackHandler);
	}

	public void findSteps(StepFilter filter, final CallbackHandler<Set<Step>> callbackHandler) {
		this.routingService.findSteps(filter, new CallbackHandler<Set<EStep>>() {
			@Override
			public void onSuccess(Set<EStep> result) {
				Set<Step> steps = MESTypeConverter.toClientObjectSet(result, Step.class);
				callbackHandler.onSuccess(steps);
			}
		});
	}

	public void findSteps(String routingName, final CallbackHandler<Set<Step>> callbackHandler) {
		this.routingService.findSteps(routingName, new CallbackHandler<Set<EStep>>() {
			@Override
			public void onSuccess(Set<EStep> result) {
				Set<Step> steps = MESTypeConverter.toClientObjectSet(result, Step.class);
				callbackHandler.onSuccess(steps);
			}
		});
	}
	
	public void findStepNames(StepFilter filter, CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findStepNames(filter, callbackHandler);
	}

	public void saveStep(Step step, String comment, AsyncCallback<Void> callback) {
		EStep e = step.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveStep(e, trxInfo, new VoidCallback(callback));
	}

	public void saveSteps(Set<Step> steps, String comment, CallbackHandler<Void> callbackHandler) {
		Set<EStep> esteps = MESTypeConverter.toEntitySet(steps);
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveSteps(esteps, trxInfo, callbackHandler);
	}

	@Deprecated
	public void removeStep(final Step step, final String comment, final AsyncCallback<Void> callback) {
		EStep e = step.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeStep(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				steps.remove(step);
				callback.onSuccess(result);
			}
		});
	}

	public void removeStep(final String stepName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeStep(stepName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Step.class, stepName);
				callback.onSuccess(result);
			}
		});
	}
	
	// --------------------- step status ----------------------------
	public void findStepStatusById(Long id, final CallbackHandler<StepStatus> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.stepStatuses != null) {
			for (StepStatus ss : this.stepStatuses) {
				if (ss != null && ss.getId() == id) {
					callback.onSuccess(ss);
					found = true;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findStepStatusById(id, new CallbackHandler<EStepStatus>() {
				public void onSuccess(EStepStatus e) {
					StepStatus ss = MESTypeConverter.toClientObject(e, StepStatus.class);
					if (ss != null) {
						stepStatuses.add(ss);
					}
					callback.onSuccess(ss);
				}
			});
		}
	}

	public void findStepStatusByName(String stepStatusName, final CallbackHandler<StepStatus> callback) {
		if (stepStatusName == null || stepStatusName.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.stepStatuses != null) {
			for (StepStatus ss : this.stepStatuses) {
				if (ss != null && ss.getName().equals(stepStatusName)) {
					callback.onSuccess(ss);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findStepStatusByName(stepStatusName, new CallbackHandler<EStepStatus>() {
				public void onSuccess(EStepStatus e) {
					StepStatus ss = MESTypeConverter.toClientObject(e, StepStatus.class);
					if (ss != null) {
						stepStatuses.add(ss);
					}
					callback.onSuccess(ss);
				}
			});
		}
	}

	public void saveStepStatus(StepStatus stepStatus, String comment, AsyncCallback<Void> callback) {
		EStepStatus e = stepStatus.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveStepStatus(e, trxInfo, new VoidCallback(callback));
	}

	public void removeStepStatus(final String stepStatusName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeStepStatus(stepStatusName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(StepStatus.class, stepStatusName);
				callback.onSuccess(result);
			}
		});
	}
	
	// ------------------ transition --------------------
	public void findTransitionById(Long id, final CallbackHandler<Transition> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.transitions != null) {
			for (Transition t : this.transitions) {
				if (t != null && t.getId() == id) {
					callback.onSuccess(t);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findTransitionById(id, new CallbackHandler<ETransition>() {
				public void onSuccess(ETransition e) {
					Transition t = MESTypeConverter.toClientObject(e, Transition.class);
					if (t != null) {
						transitions.add(t);
					}
					callback.onSuccess(t);
				}
			});
		}
	}

	public void findTransitionByName(String routingName, String transitionName, final CallbackHandler<Transition> callback) {
		if (transitionName == null || transitionName.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.transitions != null) {
			for (Transition t : this.transitions) {
				if (t != null && t.getRoutingName().equals(routingName) && t.getName().equals(transitionName)) {
					callback.onSuccess(t);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findTransitionByName(routingName, transitionName, new CallbackHandler<ETransition>() {
				public void onSuccess(ETransition e) {
					Transition t = MESTypeConverter.toClientObject(e, Transition.class);
					if (t != null) {
						transitions.add(t);
					}
					callback.onSuccess(t);
				}
			});
		}
	}

	public void findAllTransitions(final CallbackHandler<Set<Transition>> callback) {
		if (transitions != null && !transitions.isEmpty()) {
			callback.onSuccess(transitions);
		} else {
			this.routingService.findAllTransitions(new CallbackHandler<Set<ETransition>>() {
				@Override
				public void onSuccess(Set<ETransition> result) {
					transitions = MESTypeConverter.toClientObjectSet(result, Transition.class);
					callback.onSuccess(transitions);
				}
			});
		}
	}

	public void findAllTransitionNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllTransitionNames(callbackHandler);
	}

	public void createTransition(String name, Quantity transferQuantity,
			EDictionary reasonDictionary, CustomAttributes customAttributes, String comment, final CallbackHandler<Transition> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		//EDictionary reason = null;
		routingService.createTransition(name, transferQuantity, reasonDictionary, customAttributes, trxInfo, new CallbackHandler<ETransition>() {
			@Override
			public void onSuccess(ETransition e) {
				Transition trans = MESTypeConverter.toClientObject(e, Transition.class);
				cache(trans);
				callback.onSuccess(trans);
			}
		});
	}
	
	public void saveTransition(Transition transition, String comment, AsyncCallback<Void> callback) {
		ETransition e = transition.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveTransition(e, trxInfo, new VoidCallback(callback));
	}

	@Deprecated
	public void removeTransition(final Transition transition, final String comment, final AsyncCallback<Void> callback) {
		ETransition e = transition.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeTransition(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				transitions.remove(transition);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeTransition(final String transitionName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeTransition(transitionName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Transition.class, transitionName);
				callback.onSuccess(result);
			}
		});
	}

	// ----------------------------- operation -------------------------------
	public void findOperationById(Long id, final CallbackHandler<Operation> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.operations != null) {
			for (Operation op : this.operations) {
				if (op != null && op.getId() == id) {
					callback.onSuccess(op);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findOperationById(id, new CallbackHandler<EOperation>() {
				public void onSuccess(EOperation e) {
					Operation op = MESTypeConverter.toClientObject(e, Operation.class);
					if (op != null) {
						operations.add(op);
					}
					callback.onSuccess(op);
				}
			});
		}
	}

	public void findOperationByName(String name, final CallbackHandler<Operation> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.operations != null) {
			for (Operation op : this.operations) {
				if (op != null && op.getName().equals(name)) {
					callback.onSuccess(op);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findOperationByName(name, new CallbackHandler<EOperation>() {
				public void onSuccess(EOperation e) {
					Operation op = MESTypeConverter.toClientObject(e, Operation.class);
					if (op != null) {
						operations.add(op);
					}
					callback.onSuccess(op);
				}
			});
		}
	}

	public Operation findOperationByName(String name) {
		for (Operation op : operations) {
			if (op.getName().equals(name)) {
				return op;
			}
		}
		return null;
	}

	public void findAllOperations(final CallbackHandler<Set<Operation>> callback) {
		if (operations != null && !operations.isEmpty()) {
			callback.onSuccess(operations);
		} else {
			this.routingService.findAllOperations(new CallbackHandler<Set<EOperation>>() {
				@Override
				public void onSuccess(Set<EOperation> result) {
					operations = MESTypeConverter.toClientObjectSet(result, Operation.class);
					callback.onSuccess(operations);
				}
			});
		}
	}

	public void findAllOperationNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllOperationNames(callbackHandler);
	}

	public void createOperation(String name, String description, CustomAttributes customAttributes, String comment, AsyncCallback<Operation> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.createOperation(name, description, customAttributes, trxInfo, new EntityToClientObjectCallback<EOperation, Operation>(
				Operation.class, callback));
	}

	public void saveOperation(Operation operation, String comment, AsyncCallback<Void> callback) {
		EOperation e = operation.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveOperation(e, trxInfo, new VoidCallback(callback));
	}

	@Deprecated
	public void removeOperation(final Operation operation, final String comment, final AsyncCallback<Void> callback) {
		EOperation e = operation.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeOperation(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				operations.remove(operation);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeOperation(final String operationName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeOperation(operationName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Operation.class, operationName);
				callback.onSuccess(result);
			}
		});
	}

	// --------------------------- production line -------------------------
	public void findProductionLineById(Long id, final CallbackHandler<ProductionLine> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.productionLines != null) {
			for (ProductionLine p : this.productionLines) {
				if (p != null && p.getId() == id) {
					callback.onSuccess(p);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findProductionLineById(id, new CallbackHandler<EProductionLine>() {
				public void onSuccess(EProductionLine e) {
					ProductionLine p = MESTypeConverter.toClientObject(e, ProductionLine.class);
					if (p != null) {
						productionLines.add(p);
					}
					callback.onSuccess(p);
				}
			});
		}
	}

	public void findProductionLineByName(String name, final CallbackHandler<ProductionLine> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.productionLines != null) {
			for (ProductionLine p : this.productionLines) {
				if (p != null && p.getName().equals(name)) {
					callback.onSuccess(p);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findProductionLineByName(name, new CallbackHandler<EProductionLine>() {
				public void onSuccess(EProductionLine e) {
					ProductionLine p = MESTypeConverter.toClientObject(e, ProductionLine.class);
					if (p != null) {
						productionLines.add(p);
					}
					callback.onSuccess(p);
				}
			});
		}
	}

	public void findAllProductionLines(final CallbackHandler<Set<ProductionLine>> callback) {
		if (productionLines != null && !productionLines.isEmpty()) {
			callback.onSuccess(productionLines);
		} else {
			this.routingService.findAllProductionLines(new CallbackHandler<Set<EProductionLine>>() {
				@Override
				public void onSuccess(Set<EProductionLine> result) {
					productionLines = MESTypeConverter.toClientObjectSet(result, ProductionLine.class);
					callback.onSuccess(productionLines);
				}
			});
		}
	}

	public void findAllProductionLineNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllProductionLineNames(callbackHandler);
	}

	public void createProductionLine(String name, String description, final Area optArea, Set<WorkCenter> optWorkCenters,
			CustomAttributes customAttributes, String comment, final AsyncCallback<ProductionLine> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		final Set<EWorkCenter> ewcs = MESTypeConverter.toEntitySet(workCenters);
		EArea earea = (optArea != null) ? optArea.toEntity() : null;
		this.routingService.createProductionLine(name, description, earea, ewcs, customAttributes, trxInfo, new CallbackHandler<EProductionLine>() {
			@Override
			public void onSuccess(EProductionLine e) {
				ProductionLine pdl = MESTypeConverter.toClientObject(e, ProductionLine.class);
				cache(pdl);
				if (optArea != null) {
					optArea.addProductionLine(pdl);
				}
				clearCache(WorkCenter.class);
				callback.onSuccess(pdl);
			}
		});
	}

	public void saveProductionLine(final ProductionLine productionLine, String comment, final AsyncCallback<Void> callback) {
		EProductionLine e = productionLine.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveProductionLine(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(productionLine);
				if (productionLine.getArea() != null) {
					productionLine.getArea().updateProductionLine(productionLine); // update
																					// site
																					// cache
				}
				clearCache(WorkCenter.class);
				callback.onSuccess(result);
			}
		});
	}

	@Deprecated
	public void removeProductionLine(final ProductionLine productionLine, final String comment, final AsyncCallback<Void> callback) {
		EProductionLine e = productionLine.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeProductionLine(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				productionLines.remove(productionLine);
				callback.onSuccess(result);
			}
		});
	}

	public void removeProductionLine(final String productionLineName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeProductionLine(productionLineName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(ProductionLine.class, productionLineName);
				callback.onSuccess(result);
			}
		});
	}
	
	public void findProductionLineNames(ProductionLineFilter filter, CallbackHandler<Set<String>> callbackHandler) {
		routingService.findProductionLineNames(filter, callbackHandler);
	}

	public void findProductionLines(ProductionLineFilter filter, final CallbackHandler<Set<ProductionLine>> callbackHandler) {
		this.routingService.findProductionLines(filter, new CallbackHandler<Set<EProductionLine>>() {
			@Override
			public void onSuccess(Set<EProductionLine> result) {
				Set<ProductionLine> prodLines = MESTypeConverter.toClientObjectSet(result, ProductionLine.class);
				callbackHandler.onSuccess(prodLines);
			}
		});
	}

	public void saveProductionLines(Set<ProductionLine> prodLines, String comment, CallbackHandler<Void> callbackHandler) {
		Set<EProductionLine> es = MESTypeConverter.toEntitySet(prodLines);
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveProductionLines(es, trxInfo, callbackHandler);
	}

	// ---------------- equipment ------------------------
	public void findEquipmentById(Long id, final CallbackHandler<Equipment> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.equipments != null) {
			for (Equipment eq : this.equipments) {
				if (eq != null && eq.getId() == id) {
					callback.onSuccess(eq);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findEquipmentById(id, new CallbackHandler<EEquipment>() {
				public void onSuccess(EEquipment e) {
					Equipment eq = MESTypeConverter.toClientObject(e, Equipment.class);
					if (eq != null) {
						equipments.add(eq);
					}
					callback.onSuccess(eq);
				}
			});
		}
	}

	public void findEquipmentByName(String name, final CallbackHandler<Equipment> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.equipments != null) {
			for (Equipment eq : this.equipments) {
				if (eq != null && eq.getName().equals(name)) {
					callback.onSuccess(eq);
					found = true;
					return;
				}
			}
		}

		if (!found) {
			// find from server
			this.routingService.findEquipmentByName(name, new CallbackHandler<EEquipment>() {
				public void onSuccess(EEquipment e) {
					Equipment eq = MESTypeConverter.toClientObject(e, Equipment.class);
					if (eq != null) {
						equipments.add(eq);
					}
					callback.onSuccess(eq);
				}
			});
		}
	}

	public void findAllEquipments(final CallbackHandler<Set<Equipment>> callback) {
		if (equipments != null && !equipments.isEmpty()) {
			callback.onSuccess(equipments);
		} else {
			this.routingService.findAllEquipments(new CallbackHandler<Set<EEquipment>>() {
				public void onSuccess(Set<EEquipment> eset) {
					equipments = MESTypeConverter.toClientObjectSet(eset, Equipment.class);
					callback.onSuccess(equipments);
				}
			});
		}
	}

	public void findEquipmentsByOperation(final Operation operation, final CallbackHandler<Set<Equipment>> callback) {
		final Set<Equipment> result = new HashSet<Equipment>();

		findAllEquipments(new CallbackHandler<Set<Equipment>>() {
			@Override
			public void onSuccess(Set<Equipment> eset) {
				if (eset != null) {
					final String thisOpName = operation.getName();
					for (Equipment eq : eset) {
						final Equipment equipment = eq;
						eq.getOperation(new CallbackHandler<Operation>() {
							@Override
							public void onSuccess(Operation op) {
								if (op.getName().equals(thisOpName)) {
									result.add(equipment);
								}
							}
						});
					}
					callback.onSuccess(result);
				}
			}
		});
	}

	public void findEquipmentsByWorkCenter(final WorkCenter workCenter, final CallbackHandler<Set<Equipment>> callback) {
		final Set<Equipment> result = new HashSet<Equipment>();

		findAllEquipments(new CallbackHandler<Set<Equipment>>() {
			@Override
			public void onSuccess(Set<Equipment> eset) {
				if (eset != null) {
					final String wcName = workCenter.getName();
					for (Equipment eq : eset) {
						final Equipment equipment = eq;
						eq.getWorkCenter(new CallbackHandler<WorkCenter>() {
							@Override
							public void onSuccess(WorkCenter wc) {
								if (wc.getName().equals(wcName)) {
									result.add(equipment);
								}
							}
						});
					}
					callback.onSuccess(result);
				}
			}
		});
	}

	public void findAllEquipmentNames(final CallbackHandler<Set<String>> callbackHandler) {
		this.routingService.findAllEquipmentNames(callbackHandler);
	}

	public void findEquipmentByName(String name, AsyncCallback<Equipment> callback) {
		this.routingService.findEquipmentByName(name, new MESTypeConverter.EntityToClientObjectCallback<EEquipment, Equipment>(Equipment.class,
				callback));
	}

	public void findEquipmentNames(EquipmentFilter filter, CallbackHandler<Set<String>> callbackHandler) {
		routingService.findEquipmentNames(filter, callbackHandler);
	}

	public void findEquipments(EquipmentFilter filter, final CallbackHandler<Set<Equipment>> callbackHandler) {
		this.routingService.findEquipments(filter, new CallbackHandler<Set<EEquipment>>() {
			@Override
			public void onSuccess(Set<EEquipment> result) {
				Set<Equipment> eqs = MESTypeConverter.toClientObjectSet(result, Equipment.class);
				callbackHandler.onSuccess(eqs);
			}
		});
	}

	public void createEquipment(String name, String description, WorkCenter workCenter, CustomAttributes customAttributes, String comment,
			AsyncCallback<Equipment> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		EWorkCenter eWC = (workCenter != null)? workCenter.toEntity() : null;
		this.routingService.createEquipment(name, description, eWC, customAttributes, trxInfo,
				new MESTypeConverter.EntityToClientObjectCallback<EEquipment, Equipment>(Equipment.class, callback));
	}

	public void saveEquipment(Equipment equipment, String comment, AsyncCallback<Void> callback) {
		EEquipment e = equipment.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveEquipment(e, trxInfo, callback);
	}

	public void saveEquipments(Set<Equipment> equipments, String comment, CallbackHandler<Void> callbackHandler) {
		Set<EEquipment> es = MESTypeConverter.toEntitySet(equipments);
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.saveEquipments(es, trxInfo, callbackHandler);
	}

	@Deprecated
	public void removeEquipment(final Equipment equipment, final String comment, final AsyncCallback<Void> callback) {
		EEquipment e = equipment.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeEquipment(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				equipments.remove(equipment);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeEquipment(final String equipmentName, final String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.removeEquipment(equipmentName, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				clearCache(Equipment.class, equipmentName);
				callback.onSuccess(result);
			}
		});
	}

	//------------------ transition attributes -------------------------
	public void findTransitionAttributes(final Object object, final CallbackHandler<TransitionAttributes> callback) {
		Long objectId = null;
		MESConstants.Object.Type objectType = null;
		
		if (object instanceof Lot) {
			Lot lot = (Lot) object;
			objectId = lot.getId();
			objectType = MESConstants.Object.Type.Lot;
		}
		else if (object instanceof Unit) {
			Unit unit = (Unit) object;
			objectId = unit.getId();
			objectType = MESConstants.Object.Type.Unit;
		}
		else if (object instanceof Batch) {
			Batch batch = (Batch) object;
			objectId = batch.getId();
			objectType = MESConstants.Object.Type.Batch;
		}
		else if (object instanceof Container) {
			Container container = (Container) object;
			objectId = container.getId();
			objectType = MESConstants.Object.Type.Container;
		}
		else {
			throw new RuntimeException("Object type not supported");
		}
				
		this.routingService.findTransitionAttributes(objectId, objectType, new CallbackHandler<ETransitionAttributes>() {
			@Override
			public void onSuccess(ETransitionAttributes trsnAttrs) {
				callback.onSuccess(new TransitionAttributes(trsnAttrs));
			}
		});
	}
	
	@Deprecated
	public void getTransitionAttributes(final long objectId, final MESConstants.Object.Type objectType, final CallbackHandler<TransitionAttributes> callback) {
		this.routingService.findTransitionAttributes(objectId, objectType, new CallbackHandler<ETransitionAttributes>() {
			@Override
			public void onSuccess(ETransitionAttributes trsnAttrs) {
				if (trsnAttrs == null) {
					callback.onFailure(new Exception("Object not joining a routing yet, object id, object type: " + objectId + ", " + objectType));
				}
				else {
					callback.onSuccess(new TransitionAttributes(trsnAttrs));
				}
			}
		});
	}
	
	@Deprecated
	public void getTransitionAttributesForContainer(final long containerId, final CallbackHandler<TransitionAttributes> callback) {
		getTransitionAttributes(containerId, MESConstants.Object.Type.Container, callback);
	}
	
	@Deprecated
	public void getTransitionAttributesForLot(final long lotId, final CallbackHandler<TransitionAttributes> callback) {
		getTransitionAttributes(lotId, MESConstants.Object.Type.Lot, callback);
	}
	
	@Deprecated
	public void getTransitionAttributesForBatch(final long batchId, final CallbackHandler<TransitionAttributes> callback) {
		getTransitionAttributes(batchId, MESConstants.Object.Type.Batch, callback);
	}
	
	@Deprecated
	public void getTransitionAttributesForUnit(final long unitId, final CallbackHandler<TransitionAttributes> callback) {
		getTransitionAttributes(unitId, MESConstants.Object.Type.Unit, callback);
	}
	
	/*@Deprecated
	public void findRevisionsByRoutingName(String routingName, final CallbackHandler<Set<String>> callback) {
		if (routingName == null || routingName.isEmpty()) {
			callback.onSuccess(null);
			return;
		}
		
		boolean found = false;
		Set<String> revisions = new HashSet<String>();
		
		// find from cache
		if (this.routings != null) {
			for (Routing r : this.routings) {
				if (r != null && r.getName().equals(routingName)) {
					revisions.add(r.getRevision());
				}
			}
		}
		
		if (!revisions.isEmpty()) {
			callback.onSuccess(revisions);
			found = true;
		}
		
		// find from server
		if (!found) {
			this.routingService.findRevisionsByRoutingName(routingName, new CallbackHandler<Set<String>>() {

				@Override
				public void onSuccess(Set<String> revisions) {
					if (revisions != null && !revisions.isEmpty()) {
						callback.onSuccess(revisions);
					}
				}
			});
		}
	}*/
	
	public void findAllObjectNames(MESConstants.Object.Type objectType, final CallbackHandler<Set<String>> callback) {
		if (objectType == MESConstants.Object.Type.Site) {
			this.routingService.findAllSiteNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.Area) {
			this.routingService.findAllAreaNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.ProductionLine) {
			this.routingService.findAllProductionLineNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.WorkCenter) {
			this.routingService.findAllWorkCenterNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.Equipment) {
			this.routingService.findAllEquipmentNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.Operation) {
			this.routingService.findAllOperationNames(callback);
		}
		else if (objectType == MESConstants.Object.Type.Routing) {
			this.routingService.findAllRoutingNames(callback);
		}
		else {
			callback.onFailure(new Exception("Object type not supported"));
		}
	}
	
	public void findObjectNames(MESConstants.Object.Type objectType, final String namePrefix, final CallbackHandler<Set<String>> callback) {
		if (namePrefix == null || namePrefix.isEmpty()) {
			findAllObjectNames(objectType, callback);
		}
		else {
			if (objectType == MESConstants.Object.Type.Site) {
				SiteFilter filter = new SiteFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findSiteNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.Area) {
				AreaFilter filter = new AreaFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findAreaNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.ProductionLine) {
				ProductionLineFilter filter = new ProductionLineFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findProductionLineNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.WorkCenter) {
				WorkCenterFilter filter = new WorkCenterFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findWorkCenterNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.Equipment) {
				EquipmentFilter filter = new EquipmentFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findEquipmentNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.Operation) {
				OperationFilter filter = new OperationFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findOperationNames(filter, callback);
			}
			else if (objectType == MESConstants.Object.Type.Routing) {
				RoutingFilter filter = new RoutingFilter();
				filter.whereName().isLike(namePrefix + "%");
				this.routingService.findRoutingNames(filter, callback);
			}
			else {
				callback.onFailure(new Exception("Object type not supported"));
			}
		}
	}
	
	public void joinRouting(long objectId, MESConstants.Object.Type objectType, String routingName, 
			String stepName, String optWorkCenterName, boolean stepFlowEnforcement, String comment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.routingService.joinRouting(objectId, objectType, routingName, stepName, optWorkCenterName, stepFlowEnforcement, trxInfo, callback);
	}
	
	public void transact(Object object, String stepName, String optWorkCenterName, String stepStatusName, 
			String objectStatus, String optTransitionName, String optReason, String optComment, final CallbackHandler<Void> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(optComment);
		Long objectId = null;
		MESConstants.Object.Type objectType = null;
		
		if (object instanceof Lot) {
			Lot lot = (Lot) object;
			objectId = lot.getId();
			objectType = MESConstants.Object.Type.Lot;
		}
		else if (object instanceof Unit) {
			Unit unit = (Unit) object;
			objectId = unit.getId();
			objectType = MESConstants.Object.Type.Unit;
		}
		else if (object instanceof Batch) {
			Batch batch = (Batch) object;
			objectId = batch.getId();
			objectType = MESConstants.Object.Type.Batch;
		}
		else if (object instanceof Container) {
			Container container = (Container) object;
			objectId = container.getId();
			objectType = MESConstants.Object.Type.Container;
		} 
		else {
			throw new RuntimeException("Object type not supported");
		}
		
		this.routingService.transact(objectId, objectType, stepName, optWorkCenterName, stepStatusName, objectStatus, 
				optTransitionName, optReason, trxInfo, callback);
	}
}
