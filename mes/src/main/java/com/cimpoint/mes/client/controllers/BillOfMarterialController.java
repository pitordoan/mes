/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Duy Chung - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.controllers;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.controllers.AppController;
import com.cimpoint.common.objects.ClientObject;
import com.cimpoint.mes.client.MESTypeConverter;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Bom;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.client.objects.Site;
import com.cimpoint.mes.common.MESConstants;
import com.cimpoint.mes.common.MESUtils;
import com.cimpoint.mes.common.entities.EBom;
import com.cimpoint.mes.common.entities.EBomItem;
import com.cimpoint.mes.common.entities.EPart;
import com.cimpoint.mes.common.entities.ESite;
import com.cimpoint.mes.common.entities.MESTrxInfo;
import com.cimpoint.mes.common.services.BillOfMaterialService;
import com.cimpoint.mes.common.services.BillOfMaterialServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BillOfMarterialController extends AppController {
	
	private BillOfMaterialServiceAsync billOfMaterialService;
	private Set<Part> parts = new HashSet<Part>();
	private Set<Bom> boms = new HashSet<Bom>();
	
	public BillOfMarterialController() {
		billOfMaterialService = BillOfMaterialService.Util.getInstance();
	}
	
	@Override
	public void init(CallbackHandler<Void> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void cache(ClientObject<?> obj) {
		if (obj instanceof Part) {
			Part part = (Part) obj;
			if (parts.contains(part)) {
				parts.remove(part);
			}
			parts.add(part);
		} else if (obj instanceof Bom) {
			Bom bom = (Bom)obj;
			if (boms.contains(bom)) {
				boms.remove(bom);
			}
			boms.add(bom);
		} else {
			throw new RuntimeException("Object type not supported");
		}
	}

	public void clearCache(ClientObject<?> obj) {
		if (obj instanceof Site) {
			Part part = (Part) obj;
			if (parts.contains(part)) {
				parts.remove(part);
			}
		} else {
			throw new RuntimeException("Object type not supported");
		}
	}

	public void cache(Set<Part> objSet) {
		if (objSet == null || objSet.size() == 0)
			return;

		for (Part a : objSet) {
			cache(a);
		}
	}

	public void clearCache(Class<?> objClass) {
		if (objClass == Site.class) {
			this.parts.clear();
		} 
	}

	public void findPartByName(String name, final CallbackHandler<Part> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean foundPart = false;

		// find from cache
		if (this.parts != null) {
			for (Part p : this.parts) {
				if (p != null && p.getName().equals(name)) {
					callback.onSuccess(p);
					foundPart = true;
				}
			}
		}

		if (!foundPart) {
			// find from server
			this.billOfMaterialService.findPartByNumber(name, new CallbackHandler<EPart>() {
				public void onSuccess(EPart e) {
					Part part = MESTypeConverter.toClientObject(e, Part.class);
					if (part != null) {
						parts.add(part);
					}
					callback.onSuccess(part);
				}
			});
		}
	}

	
	public void findRevisionsByPartName(String partName, final CallbackHandler<Set<String>> callback) {
		if (partName == null || partName.isEmpty()) {
			callback.onSuccess(null);
			return;
		}
		
		boolean found = false;
		Set<String> revisions = new HashSet<String>();
		
		// find from cache
		if (this.parts != null) {
			for (Part p : this.parts) {
				if (p != null && p.getName().equals(partName)) {
					revisions.add(p.getRevision());
				}
			}
		}
		
		if (!revisions.isEmpty()) {
			callback.onSuccess(revisions);
			found = true;
		}
		
		// find from server
		if (!found) {
			this.billOfMaterialService.findRevisionsByPartName(partName, new CallbackHandler<Set<String>>() {

				@Override
				public void onSuccess(Set<String> revisions) {
					if (revisions != null && !revisions.isEmpty()) {
						callback.onSuccess(revisions);
					}
				}
			});
		}
	}
	
	
	public void findPartByNameAndRevision(String name, String revision, final CallbackHandler<Part> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.parts != null) {
			for (Part p : this.parts) {
				if (p != null && p.getName().equals(name) && p.getRevision().equals(revision)) {
					callback.onSuccess(p);
					found = true;
				}
			}
		}

		if (!found) {
			// find from server
			this.billOfMaterialService.findPartByNameAndRevision(name, revision, new CallbackHandler<EPart>() {
				public void onSuccess(EPart e) {
					Part p = MESTypeConverter.toClientObject(e, Part.class);
					if (p != null) {
						parts.add(p);
					}
					callback.onSuccess(p);
				}
			});
		}
	}
	
	public void findPartById(Long id, final CallbackHandler<Part> callback) {
		if (id == null) {
			callback.onSuccess(null);
			return;
		}

		boolean foundSite = false;

		// find from cache
		if (this.parts != null) {
			for (Part p : this.parts) {
				if (p.getId() == id) {
					callback.onSuccess(p);
					foundSite = true;
				}
			}
		}

		if (!foundSite) {
			// find from server
			this.billOfMaterialService.findPartById(id, new CallbackHandler<EPart>() {
				public void onSuccess(EPart epart) {
					Part part = MESTypeConverter.toClientObject(epart, Part.class);
					if (part != null) {
						parts.add(part);
					}
					callback.onSuccess(part);
				}
			});
		}
	}
	
	public void createPart(String partNumber, String desc, String partRevision, String quantiy, 
			MESConstants.Object.UnitOfMeasure unitOfMeasure, MESConstants.Object.PartCatergory category,
			Set<Bom> boms, String startDate, String endDate, String comment, final CallbackHandler<Part> callback) {		
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EBom> bomList = MESTypeConverter.toEntitySet(boms);
		this.billOfMaterialService.createPart(partNumber, desc, partRevision, quantiy, unitOfMeasure, category, bomList, 
											startDate, endDate , trxInfo, new CallbackHandler<EPart>() {
			@Override
			public void onSuccess(EPart e) {
				Part part = MESTypeConverter.toClientObject(e, Part.class);
				cache(part);
				callback.onSuccess(part);
			}
		});
	}
	
	public void savePart(final Part part, String comment, final CallbackHandler<Void> callback) {
		EPart e = part.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.billOfMaterialService.savePart(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(part);
				clearCache(Bom.class);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removePart(final Part part, final String comment, final CallbackHandler<Void> callback) {
		EPart e = part.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.billOfMaterialService.removePart(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				parts.remove(part);
				callback.onSuccess(result);
			}
		});
	}
	
	public void findAllParts(final CallbackHandler<Set<Part>> callback) {
		if (parts != null && !parts.isEmpty()) {
			callback.onSuccess(parts);
		} else {
			this.billOfMaterialService.findAllParts(new CallbackHandler<Set<EPart>>() {

				@Override
				public void onSuccess(Set<EPart> result) {
					parts = MESTypeConverter.toClientObjectSet(result, Part.class);
					callback.onSuccess(parts);
				}
			});
		}
	}
	
	public void findAllPartNameWithRevisions(final CallbackHandler<Set<String>> callbackHandler) {
		this.billOfMaterialService.findAllPartNameWithRevisions(callbackHandler);
	}
	
	// BOM
	public void createBom(String name, String desc, String revision, String startDate, String endDate, Set<BomItem> bomItems, String comment, final CallbackHandler<Bom> callback) {
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		Set<EBomItem> bomItemList = MESTypeConverter.toEntitySet(bomItems);
		
		billOfMaterialService.createBom(name, desc, revision, startDate, endDate, bomItemList, trxInfo, new CallbackHandler<EBom>() {

			@Override
			public void onSuccess(EBom e) {
				Bom bom = MESTypeConverter.toClientObject(e, Bom.class);
				cache(bom);
				callback.onSuccess(bom);
			}
		});
	}
	
	public void findBomByNameAndRevision(String name, String revision, final CallbackHandler<Bom> callback) {
		if (name == null || name.isEmpty()) {
			callback.onSuccess(null);
			return;
		}

		boolean found = false;

		// find from cache
		if (this.boms != null) {
			for (Bom b : this.boms) {
				if (b != null && b.getName().equals(name) && b.getRevision().equals(revision)) {
					callback.onSuccess(b);
					found = true;
				}
			}
		}

		if (!found) {
			// find from server
			this.billOfMaterialService.findBomByNameAndRevision(name, revision, new CallbackHandler<EBom>() {
				public void onSuccess(EBom e) {
					Bom b = MESTypeConverter.toClientObject(e, Bom.class);
					if (b != null) {
						boms.add(b);
					}
					callback.onSuccess(b);
				}
			});
		}
	}
	
	/**
	 * Find all Bill of materials
	 * @param callback
	 */
	public void findAllBoms(final CallbackHandler<Set<Bom>> callback) {
		if (boms != null && !boms.isEmpty()) {
			callback.onSuccess(boms);
		} else {
			this.billOfMaterialService.findAllBoms(new CallbackHandler<Set<EBom>>() {

				@Override
				public void onSuccess(Set<EBom> result) {
					boms = MESTypeConverter.toClientObjectSet(result, Bom.class);
					callback.onSuccess(boms);
				}
			});
		}
	}
	
	public void saveBom(final Bom bom, String comment, final CallbackHandler<Void> callback) {
		EBom e = bom.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.billOfMaterialService.saveBom(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				cache(bom);
				clearCache(BomItem.class);
				callback.onSuccess(result);
			}
		});
	}
	
	public void removeBom(final Bom bom, final String comment, final CallbackHandler<Void> callback) {
		EBom e = bom.toEntity();
		MESTrxInfo trxInfo = MESUtils.newTrxInfo(comment);
		this.billOfMaterialService.removeBom(e, trxInfo, new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				boms.remove(bom);
				callback.onSuccess(result);
			}
		});
	}
}
