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
package com.cimpoint.mes.client.views.editors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.ChainAction;
import com.cimpoint.common.ChainCall;
import com.cimpoint.common.ChainCall.ChainCallbackHandler;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.RoutingController;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.Site;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.client.views.designers.RoutingResourceDesigner;
import com.cimpoint.mes.client.views.editors.ModelTreeGrid.NodeType;
import com.cimpoint.mes.client.views.editors.ValuesPickerDialog.Response;
import com.cimpoint.mes.common.filters.AreaFilter;
import com.smartgwt.client.widgets.tree.TreeNode;

public class SiteEditor extends ModelEditor {
	private boolean initialized = false;
	private RoutingController routingController;
	private NameProperty propName;
	private TextProperty propDesc;
	private CustomAttributesProperty propCustomAttributes;
	private ModelTreeGrid designerTreeGrid;
	private TreeNode areasTreeNode;
	
	public SiteEditor() {
		super(new RoutingResourceDesigner());
		routingController = MESApplication.getMESControllers().getRoutingController();
		designerTreeGrid = ((RoutingResourceDesigner) super.getModelDesigner()).getModelTreeGrid();
		propName = new NameProperty("Name", new NamePropertyEditor(this));
		propDesc = new TextProperty("Description");
		propCustomAttributes = new CustomAttributesProperty("Custom Attributes", new CustomAttributesPropertyEditor(this));
		this.getPropertiesEditor().setProperties(propName, propDesc, propCustomAttributes);
	}

	@Override
	public void onInitialize(final String siteName) {
		if (initialized) return;
		
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			@Override
			public void onSuccess(Site site) {
				designerTreeGrid.clear();
				
				propName.setValue(site.getName());
				propDesc.setValue(site.getDescription());
				propCustomAttributes.setCustomAttributes(site.getCustomAttributes());
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.RootModel, siteName, siteName, "pieces/16/site.png");
				
				Set<Area> areas = displayAreas(site, designerTreeGrid, true);
				if (areas != null && areas.size() > 0) {
					for (Area area: areas) {
						String areaPath = site.getName() + "/areas/" + area.getName();
						Set<ProductionLine> prodLines = displayProductionLines(area, areaPath, designerTreeGrid, false);
						if (prodLines != null && prodLines.size() > 0) {
							for (ProductionLine pdl: prodLines) {
								String prodLinePath = siteName + "/areas/" + area.getName() + "/prodLines/" + pdl.getName();								
								Set<WorkCenter> wcs = displayWorkCenters(pdl, prodLinePath, designerTreeGrid, false);
								if (wcs != null && wcs.size() > 0) {
									for (WorkCenter wc: wcs) {
										String wcPath = siteName + "/areas/" + area.getName() + "/prodLines/" + 
												wc.getProductionLine().getName() + "/workCenters/" + wc.getName();
										displayEquipments(wc, wcPath, designerTreeGrid, false);
									}
								}
							}
						}
					}
				} else {
					areasTreeNode = designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.EditableAreas, siteName, "areas", "Areas (0)", "pieces/16/area.png");
				}
				
				initialized = true;
			}
		});
	}

	@Override
	public void onClose(final CallbackHandler<Boolean> callback) {
		if (!this.isEditing()) {
			callback.onSuccess(true);
			return;
		}
		
		MESApplication.showYesNoCancelMessage("Save Site", "Site " + getName() + " has not been saved yet.<br/>Would you like to save it?", 
				350, 150, new CallbackHandler<MessageButton>() {
			@Override
			public void onSuccess(MessageButton button) {
				if (button == MessageButton.Yes) {
					onSaveButtionClicked(new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {
							callback.onSuccess(true);
						}						
					});
				}
				else if (button == MessageButton.No) {
					callback.onSuccess(true);
				}
				else {
					callback.onSuccess(false);
				}
			}			
		});
	}

	@Override
	public void onSaveButtionClicked(final CallbackHandler<Void> callback) {
		if (!this.getPropertiesEditor().validateProperties())
			return;

		new ChainCall()
		.add(new ChainAction<Set<Area>>() {
			@Override
			public void invoke(final Object[] inputData, final ChainCallbackHandler<Set<Area>> callback) {
				getAreas(callback);
			}	
		})
		.add(new ChainAction<Site>() {
			@Override
			public void invoke(Object[] inputData, ChainCallbackHandler<Site> callback) {
				String siteName = propName.getValueAsString();
				String desc = propDesc.getValueAsString();
				Set<Area> areas = getReturnData(0);	//returned by getAreas()			
				CustomAttributes customAttributes = propCustomAttributes.getCustomAttributes();
				
				saveSite(siteName, desc, areas, customAttributes, callback);
			}			
		})
		.runSequential(callback);
	}

	@Override
	public void onAddButtionClicked(final CallbackHandler<Void> callback) {
		final TreeNode selNode = designerTreeGrid.findSelectedTreeNode();
		if (selNode != null) {
			ModelTreeGrid.NodeType nodeType = ModelTreeGrid.NodeType.valueOf(selNode.getAttribute("Type"));		
			if (nodeType == ModelTreeGrid.NodeType.EditableAreas) {
				addAreas(selNode, callback);
			}
		}
	}

	@Override
	public void onRemoveButtionClicked(final CallbackHandler<Void> callback) {	
		final String siteName = super.getName();
		final TreeNode[] selNodes = designerTreeGrid.findSelectedTreeNodes();
		if (selNodes != null && selNodes.length > 0) {		
			if (selNodes[0].getAttribute("Type").equals(NodeType.Area.toString())) {
				TreeNode parentNode = designerTreeGrid.getTree().getParent(selNodes[0]);
				ModelTreeGrid.NodeType parentNodeType = ModelTreeGrid.NodeType.valueOf(parentNode.getAttribute("Type"));		
				if (parentNodeType == ModelTreeGrid.NodeType.EditableAreas) {
					removeAreas(selNodes, siteName, callback);
				}
			}	
		}
		else {
			callback.onSuccess(null);
		}
	}

	@Override
	public void onRefreshButtionClicked(final CallbackHandler<Void> callback) {
		initialized = false;
		onInitialize(getName());
		callback.onSuccess(null);
	}

	@Override
	public void onModelNameChanged(String oldName, String newName, CallbackHandler<Void> callback) {
		updateSiteName(oldName, newName, callback);
	}
	
	private void addAreas(final TreeNode selParentNode, final CallbackHandler<Void> callback) {
		AreaFilter filter = new AreaFilter();
		filter.OR();
		filter.whereSite().isNull();
		
		MESApplication.getMESControllers().getRoutingController().findAreaNames(filter, new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> names) {	
				if (names != null && names.size() > 0) {
					List<String> availNames = new ArrayList<String>();
					for (String name: names) {
						if (!designerTreeGrid.containsTreeNodeName(name)) {
							availNames.add(name);
						}
					}				
					
					if (availNames.size() > 0) {
						showValuesPickerDialog("Select Area(s)", availNames, new CallbackHandler<Response>() {
							@Override
							public void onSuccess(Response result) {
								final Set<String> selNames = result.getReturnData();
								addAreas(selNames, selParentNode, callback);
							}							
						});
					}
					else {
						MESApplication.showOKMessage("No available area to add", null);
						callback.onSuccess(null);
					}
				}
				else {
					callback.onSuccess(null);
				}
			}
		});
	}
	
	private void addAreas(final Set<String> areaNames, TreeNode selParentNode, final CallbackHandler<Void> callback) {
		if (areaNames == null) {
			callback.onSuccess(null);
			return;
		}
		
		final String siteName = super.getName();
		final String selParentPath = designerTreeGrid.getPathToNode(selParentNode);
		
		//update GUI
		for (String name: areaNames) {
			designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.Area, 
					selParentPath, name, name, "pieces/16/area.png");
		}
		
		String newParentTitle = null;
		TreeNode parentNode = designerTreeGrid.findTreeNode(selParentPath);
		String parentNodeType = parentNode.getAttribute("Type");
		if (parentNodeType != null && parentNodeType.equals(NodeType.EditableAreas.toString())) {
			newParentTitle = "Areas (" + designerTreeGrid.getChildNodeCount(parentNode) + ")";
		}
		parentNode.setAttribute("Title", newParentTitle);
		designerTreeGrid.updateData(parentNode);
		//designerTreeGrid.openTreeNode(parentNode);	
		
		//update DB
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			public void onSuccess(final Site site) {
				AreaFilter filter = new AreaFilter();
				filter.OR();
				filter.whereName().isInList(areaNames.toArray(new String[areaNames.size()]));
				routingController.findAreas(filter, new CallbackHandler<Set<Area>>() {
					@Override
					public void onSuccess(Set<Area> areas) {
						if (areas != null) {
							for (Area area : areas) {
								site.addArea(area);
							}
						}
						site.save("Add areas", callback);
					}
				});
			}
		});	
	}
	
	public void removeAreas(final TreeNode[] selNodes, String siteName, final CallbackHandler<Void> callback) {
		final Set<String> areaNames = new HashSet<String>();
		for (TreeNode node: selNodes) {
			String name = node.getAttribute("ModelName");
			areaNames.add(name);
		}
			
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			public void onSuccess(final Site site) {
				AreaFilter filter = new AreaFilter();
				filter.OR();
				filter.whereName().isInList(areaNames.toArray(new String[areaNames.size()]));
				routingController.findAreas(filter, new CallbackHandler<Set<Area>>() {
					@Override
					public void onSuccess(Set<Area> areas) {
						//remove from DB
						if (areas != null) {
							for (Area area : areas) {
								site.removeArea(area);
							}
							site.save("Remove areas", callback);
						}
						
						//remove from GUI
						designerTreeGrid.removeSelectedTreeNodes();		
						TreeNode areasNode = designerTreeGrid.getTree().getParent(selNodes[0]);
						areasNode.setAttribute("Title", "Areas (" + designerTreeGrid.getChildNodeCount(areasNode) + ")");
						designerTreeGrid.updateData(areasNode);	
					}
				});
			}
		});
	}
		
	public static Set<Area> displayAreas(Site site, ModelTreeGrid designerTreeGrid, boolean editable) {
		ModelTreeGrid.NodeType groupNodeType = (editable)? ModelTreeGrid.NodeType.EditableAreas : ModelTreeGrid.NodeType.Areas;
		String siteName = site.getName();
		Set<Area> areas = site.getAreas();
		String areasNodeTitle = "Areas (" + areas.size() + ")";
		designerTreeGrid.addTreeNode(groupNodeType, siteName, "areas", areasNodeTitle, "pieces/16/area.png");		
		String areasPath = siteName + "/areas";
		for (Area area : areas) {
			String areaName = area.getName();
			designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.Area, areasPath, areaName, areaName, "pieces/16/area.png");
		}
		
		return areas;
	}
	
	public static Set<ProductionLine> displayProductionLines(Area area, String areaPath, ModelTreeGrid designerTreeGrid, boolean editable) {
		ModelTreeGrid.NodeType groupNodeType = (editable)? ModelTreeGrid.NodeType.EditableProductionLines : ModelTreeGrid.NodeType.ProductionLines;
		Set<ProductionLine> prodLines = area.getProductionLines();
		if (prodLines != null && prodLines.size() > 0) {
			String prodLineNodeTitle = "Production Lines (" + prodLines.size() + ")";
			designerTreeGrid.addTreeNode(groupNodeType, areaPath, "prodLines", prodLineNodeTitle, "pieces/16/production_line.png");			
			for (ProductionLine prodLine : prodLines) {
				String prodLineName = prodLine.getName();
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.ProductionLine, areaPath + "/prodLines", prodLineName, prodLineName,
						"pieces/16/production_line.png");
			}
		}
		
		return prodLines;
	}
	
	public static Set<WorkCenter> displayWorkCenters(ProductionLine prodLine, String prodLinePath, ModelTreeGrid designerTreeGrid, boolean editable) {
		ModelTreeGrid.NodeType groupNodeType = (editable)? ModelTreeGrid.NodeType.EditableWorkCenters : ModelTreeGrid.NodeType.WorkCenters;
		Set<WorkCenter> workCenters = prodLine.getWorkCenters();
		if (workCenters != null && workCenters.size() > 0) {
			String wcsNodeTitle = "Work Centers (" + workCenters.size() + ")";
			designerTreeGrid.addTreeNode(groupNodeType, prodLinePath, "workCenters", wcsNodeTitle, "pieces/16/workcenter.png");			
			for (WorkCenter wc: workCenters) {
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.WorkCenter, prodLinePath + "/workCenters", wc.getName(), wc.getName(),
						"pieces/16/workcenter.png");
			}
		}
		
		return workCenters;
	}
	
	public static Set<Equipment> displayEquipments(WorkCenter workCenter, String wcPath, ModelTreeGrid designerTreeGrid, boolean editable) {
		ModelTreeGrid.NodeType groupNodeType = (editable)? ModelTreeGrid.NodeType.EditableEquipments : ModelTreeGrid.NodeType.Equipments;
		Set<Equipment> eqs = workCenter.getEquipments();
		if (eqs != null && eqs.size() > 0) {
			String eqsNodeTitle = "Equipments (" + eqs.size() + ")";
			designerTreeGrid.addTreeNode(groupNodeType, wcPath, "equipments", eqsNodeTitle, "pieces/16/equipment.png");			
			for (Equipment eq: eqs) {
				designerTreeGrid.addTreeNode(ModelTreeGrid.NodeType.Equipment, wcPath + "/equipments", eq.getName(), eq.getName(),
						"pieces/16/equipment.png");
			}
		}
		
		return eqs;
	}
				
	private void getAreas(final ChainCallbackHandler<Set<Area>> callback) {
		if (areasTreeNode != null) {
			TreeNode[] childNodes = designerTreeGrid.getTree().getChildren(areasTreeNode);
			if (childNodes != null && childNodes.length > 0) {
				String[] names = new String[childNodes.length];
				int i=0;
				for (TreeNode n : childNodes) {
					String name = n.getAttribute("ModelName");
					names[i++] = name;
				}

				AreaFilter filter = new AreaFilter();
				filter.OR();
				filter.whereName().isInList(names);
				routingController.findAreas(filter, new CallbackHandler<Set<Area>>() {
					@Override
					public void onSuccess(Set<Area> result) {
						callback.onSuccess(result);
					}
				});
			}
			else {
				callback.onSuccess(null);
			}
		}
		else {
			callback.onSuccess(null);	
		}
	}
	
	private void saveSite(final String siteName, final String desc, final Set<Area> areas, 
			final CustomAttributes customAttributes, final ChainCallbackHandler<Site> callback) {
		routingController.findSiteByName(siteName, new CallbackHandler<Site>() {
			public void onSuccess(final Site site) {
				site.setName(siteName);
				site.setDescription(desc);
				site.setAreas(areas);
				site.setCustomAttributes(customAttributes);
				String comment = "Update site";
				routingController.saveSite(site, comment, new CallbackHandler<Void>() {
					public void onSuccess(Void result) {
						callback.onSuccess(site);
					}
				});
			}
		});
	}
	
	private void updateSiteName(final String oldSiteName, final String newSiteName, final CallbackHandler<Void> callback) {
		routingController.findSiteByName(oldSiteName, new CallbackHandler<Site>() {
			public void onSuccess(final Site site) {
				site.setName(newSiteName);
				String comment = "Update site name";
				routingController.saveSite(site, comment, callback);
			}
		});
	}
}
