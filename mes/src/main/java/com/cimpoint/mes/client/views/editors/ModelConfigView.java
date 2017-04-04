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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.cimpoint.mes.client.objects.Operation;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.Routing;
import com.cimpoint.mes.client.objects.Site;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ModelConfigView extends HLayout {
	private ModelGrid grid;
	private CategoryItem categoryItem;
	private SubcategoryItem subcategoryItem;
	private EditorContainer editorContainer;
	private ModelEditor editor;
	private ListGridField nameField;
	private ToolStripButton duplicateButton;
	private ToolStripButton removeButton;
	
	public static final String[] Categories = {
		"Bill of Materials", 
		"Custom Data Collection", 
		"Custom Code", 
		"Database", 
		"Data Management", 
		"Dictionary", 
		"Recipe", 
		"Workflow", 
		"User"
	};
	
	public static final String[] BillOfMaterialsSubcategories = new String[] {"Parts", "BOMs", "Manufacturing BOMs"};
	public static final String[] DictionarySubcategories = new String[] {"Locale Groups", "Items", "Dictionaries"};	
	public static final String[] RoutingSubcategories = new String[] {"Sites", "Areas", "Production Lines", "Work Centers", "Equipments", "Operations", "Routings"};
	
	public ModelConfigView() {
        grid = new ModelGrid();
        DynamicForm form = new DynamicForm();
        editorContainer = new EditorContainer(this);
        categoryItem = new CategoryItem(Categories);
        subcategoryItem = new SubcategoryItem();
        
        ToolStripButton refreshButton = new ToolStripButton();
        refreshButton.setIcon("[SKIN]actions/refresh.png");
        refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onLoadingData();
			}	
		});
						
        ToolStripButton addButton = new ToolStripButton();
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (categoryItem.getValue() == null || subcategoryItem.getValue() == null) return;
				onAddingModel(null);
			}			
		});
		
		removeButton = new ToolStripButton();
		removeButton.setIcon("[SKIN]actions/remove.png");
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onRemovingModel();
			}			
		});

		duplicateButton = new ToolStripButton();
		duplicateButton.setIcon("actions/16x16/duplicate.png");
		duplicateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onDuplicatingModel();
			}			
		});
		
		ToolStrip editorToolStrip = new ToolStrip();
		editorToolStrip.setSize("100%", "30px");
		editorToolStrip.setAlign(Alignment.RIGHT);		
		editorToolStrip.addButton(refreshButton);
		editorToolStrip.addButton(addButton);
		editorToolStrip.addButton(removeButton);
		editorToolStrip.addButton(duplicateButton);
		        
		form.setWidth100();
		categoryItem.setWidth("100%");
		subcategoryItem.setWidth("100%");
		form.setFields(categoryItem, subcategoryItem);
		
        VLayout vlayout = new VLayout();
        vlayout.setWidth(250);
        vlayout.setMinWidth(250);
        vlayout.addMember(form);
        vlayout.addMember(editorToolStrip);
        vlayout.addMember(grid);
        vlayout.setShowResizeBar(true);
        
        this.addMember(vlayout);
		this.addMember(editorContainer);
		
		this.setWidth100();
		this.setHeight100();
		this.setOverflow(Overflow.HIDDEN);
	}

	public void deselectAll() {
		grid.deselectAllRecords();		
	}
	
	public boolean isModelExisted(String modelName) {
		return grid.findRecord(modelName) != null;
	}
	
	protected void onModelNameChanged(String oldName, String newName) {
		grid.onModelNameChanged(oldName, newName);
	}
	
	private void onLoadingData() {
		String selSubcategory = subcategoryItem.getSelection();
		if (selSubcategory != null) {
			nameField.setTitle(selSubcategory);
			grid.loadData();
		}
	}		
	
	private void onAddingModel(String name) {
		String modelTypeName = subcategoryItem.getSelectedModelType().toString();
		PromptNewNameDialog dlg = new PromptNewNameDialog("Add " + modelTypeName, this);
		dlg.show(modelTypeName + " name", new CallbackHandler<PromptNewNameDialog.Response>() {
			@Override
			public void onSuccess(PromptNewNameDialog.Response result) {
				final String name = result.getReturnData();
				if (name != null) {
					addModel(name, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {							
							ModelGridRecord r = grid.addRecord(name);
							grid.selectRecord(r);
						}						
					});
				}
			}		
		});
	}
		
	private void onEditingModel(final String name) {
		String category = categoryItem.getSelection();
		String subcategory = subcategoryItem.getSelection();
		
		ModelEditor editor = null;		
		if (category.equals("Workflow")) {
			if (editorContainer.existTab(name)) {
				editorContainer.selectTab(name);
				editor = editorContainer.getEditor(name);
				editor.reset();
				editor.onInitialize(name);
			}
			else {
				editor = addEditorTab(subcategory, name);
				editor.onInitialize(name);
			}
		}
	}
	
	private void onRemovingModel() {
		final String[] selModelNames = grid.getMultipleSelections();
		if (selModelNames == null) return;
		
		MESApplication.showYesNoMessage("Remove Confirmation", "Area you sure you would like to remove " + 
				arrayToString(selModelNames) + "?", 300, 50, new CallbackHandler<MessageButton>() {
			@Override
			public void onSuccess(MessageButton respButton) {
				if (respButton == MessageButton.No) return;				
				removeModels(selModelNames);
			}
		});
	}	
	
	private String arrayToString(String[] arr) {
		String finalString = "";
		for (String s: arr) {
			finalString += s + ", ";
		}
		return finalString.substring(0, finalString.length()-2);
	}
	
	private void onDuplicatingModel() {		
		final String oldName = grid.getSingleSelection();
		if (oldName == null) return;
		
		String modelTypeName = subcategoryItem.getSelectedModelType().toString();
		PromptNewNameDialog dlg = new PromptNewNameDialog("Duplicate " + modelTypeName, this);
		dlg.show("Duplicate " + oldName + " as ", new CallbackHandler<PromptNewNameDialog.Response>() {
			@Override
			public void onSuccess(PromptNewNameDialog.Response result) {
				final String name = result.getReturnData();
				if (name != null) {
					duplicateModule(oldName, name, new CallbackHandler<Void>() {
						@Override
						public void onSuccess(Void result) {							
							ModelGridRecord r = grid.addRecord(name);
							grid.selectRecord(r);
						}						
					});
				}
			}		
		});
	}
	
	private ModelEditor addEditorTab(String subcategory, final String name) {
		final Tab tab = new Tab(name);
		tab.setID(name);
		tab.setCanClose(true);
		
		if (subcategory.equals("Sites")) {
			editor = new SiteEditor();
		}
		else if (subcategory.equals("Areas")) {
			editor = new AreaEditor();
		}
		else if (subcategory.equals("Production Lines")) {
			editor = new ProductionLineEditor();
		}
		else if (subcategory.equals("Work Centers")) {
			editor = new WorkCenterEditor();
		}
		else if (subcategory.equals("Equipments")) {
			editor = new EquipmentEditor();
		}
		else if (subcategory.equals("Operations")) {
			editor = new OperationEditor();
		}
		else if (subcategory.equals("Routings")) {
			editor = new RoutingEditor();
		}
		
		editor.setModelConfigView(this);
		editor.setTitle(name);
		editor.setName(name);
		editor.setContainer(tab);
		tab.setPane(editor);
		editorContainer.addTab(tab);
		editorContainer.selectTab(tab);
		
		return editor;
	}

	private void addModel(final String name, final CallbackHandler<Void> callback) {
		if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Site) {
			String desc = null;
			Set<Area> areas = null;
			CustomAttributes customAttributes = null;
			String comment = "Create site";
			MESApplication.getMESControllers().getRoutingController()
				.createSite(name, desc, areas, customAttributes, comment, new CallbackHandler<Site>() {
				@Override
				public void onSuccess(Site result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Area) {
			Site site = null;
			String desc = null;
			Set<ProductionLine> prodLines = null;
			CustomAttributes customAttributes = null;
			String comment = "Create area";
			MESApplication.getMESControllers().getRoutingController()
				.createArea(name, desc, site, prodLines, customAttributes, comment, new CallbackHandler<Area>() {
				@Override
				public void onSuccess(Area result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.ProductionLine) {
			Area optArea = null;
			Set<WorkCenter> optWorkCenters = null;
			String desc = null;
			CustomAttributes customAttributes = null;
			String comment = "Create production line";
			MESApplication.getMESControllers().getRoutingController()
				.createProductionLine(name, desc, optArea, optWorkCenters, customAttributes, comment, new CallbackHandler<ProductionLine>() {
				@Override
				public void onSuccess(ProductionLine result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.WorkCenter) {
			Area optArea = null;
			Set<Equipment> equipments = null;
			ProductionLine optProductionLine = null;
			String desc = null;
			CustomAttributes customAttributes = null;
			String comment = "Create work center";
			MESApplication.getMESControllers().getRoutingController()
				.createWorkCenter(name, desc, optArea, equipments, optProductionLine, customAttributes, comment, new CallbackHandler<WorkCenter>() {
				@Override
				public void onSuccess(WorkCenter result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Equipment) {
			WorkCenter workCenter = null;
			String desc = null;
			CustomAttributes customAttributes = null;
			String comment = "Create equipment";
			MESApplication.getMESControllers().getRoutingController()
				.createEquipment(name, desc, workCenter, customAttributes, comment, new CallbackHandler<Equipment>() {
				@Override
				public void onSuccess(Equipment result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Operation) {
			String desc = null;
			CustomAttributes customAttributes = null;
			String comment = "Create operation";
			MESApplication.getMESControllers().getRoutingController()
				.createOperation(name, desc, customAttributes, comment, new CallbackHandler<Operation>() {
				@Override
				public void onSuccess(Operation result) {
					callback.onSuccess(null);
				}						
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Routing) {
			String desc = null;
			Step entryStep = null; //TODO can't be null
			CustomAttributes customAttributes = null;
			String comment = "Create routing";
			MESApplication.getMESControllers().getRoutingController()
				.createRouting(name, desc, entryStep, customAttributes, comment, new CallbackHandler<Routing>() {
				@Override
				public void onSuccess(Routing result) {
					callback.onSuccess(null);
				}						
			});
		}		
	}
	
	private void duplicateModule(final String existingName, final String newName, final CallbackHandler<Void> callback) {
		/*if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Site) {
			MESApplication.getMESControllers().getRoutingController().findSiteByName(existingName, new CallbackHandler<Site>() {
				@Override
				public void onSuccess(Site existingSite) {
					String desc = existingSite.getDescription();
					Set<Area> areas = existingSite.getAreas(); 
					CustomAttributes customAttributes = existingSite.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createSite(newName, desc, areas, customAttributes, comment, new CallbackHandler<Site>() {
						@Override
						public void onSuccess(Site result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});	
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Area) {
			MESApplication.getMESControllers().getRoutingController().findAreaByName(existingName, new CallbackHandler<Area>() {
				@Override
				public void onSuccess(Area existingArea) {
					Site site = existingArea.getSite();
					String desc = existingArea.getDescription();
					Set<ProductionLine> prodLines = existingArea.getProductionLines(); 
					CustomAttributes customAttributes = existingArea.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createArea(newName, desc, site, prodLines, customAttributes, comment, new CallbackHandler<Area>() {
						@Override
						public void onSuccess(Area result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});	
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.ProductionLine) {
			MESApplication.getMESControllers().getRoutingController().findProductionLineByName(existingName, new CallbackHandler<ProductionLine>() {
				@Override
				public void onSuccess(ProductionLine existingProdLine) {
					Area area = existingProdLine.getArea();
					String desc = existingProdLine.getDescription();
					Set<WorkCenter> wcs = existingProdLine.getWorkCenters();
					CustomAttributes customAttributes = existingProdLine.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createProductionLine(newName, desc, area, wcs, customAttributes, comment, new CallbackHandler<ProductionLine>() {
						@Override
						public void onSuccess(ProductionLine result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.WorkCenter) {
			MESApplication.getMESControllers().getRoutingController().findWorkCenterByName(existingName, new CallbackHandler<WorkCenter>() {
				@Override
				public void onSuccess(WorkCenter existingWC) {
					Area area = existingWC.getArea();
					String desc = existingWC.getDescription();
					Set<Equipment> eqs = existingWC.getEquipments();
					ProductionLine prodLine = existingWC.getProductionLine();
					CustomAttributes customAttributes = existingWC.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createWorkCenter(newName, desc, area, eqs, prodLine, customAttributes, comment, new CallbackHandler<WorkCenter>() {
						@Override
						public void onSuccess(WorkCenter result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Equipment) {
			MESApplication.getMESControllers().getRoutingController().findEquipmentByName(existingName, new CallbackHandler<Equipment>() {
				@Override
				public void onSuccess(final Equipment eq) {
					eq.getWorkCenter(new CallbackHandler<WorkCenter>() {
						@Override
						public void onSuccess(WorkCenter wc) {
							String desc = eq.getDescription();
							CustomAttributes customAttributes = eq.getCustomAttributes();
							String comment = "Duplicate " + existingName;
							MESApplication.getMESControllers().getRoutingController()
							.createEquipment(newName, desc, wc, customAttributes, comment, new CallbackHandler<Equipment>() {
								@Override
								public void onSuccess(Equipment result) {
									callback.onSuccess(null);
								}						
							});
						}						
					});
				}				
			});
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Operation) {
			MESApplication.getMESControllers().getRoutingController().findOperationByName(existingName, new CallbackHandler<Operation>() {
				@Override
				public void onSuccess(Operation existingOp) {
					String desc = existingOp.getDescription();
					CustomAttributes customAttributes = existingOp.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createOperation(newName, desc, customAttributes, comment, new CallbackHandler<Operation>() {
						@Override
						public void onSuccess(Operation result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});
		} else*/ 
		
		if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Routing) {
			MESApplication.getMESControllers().getRoutingController().findRoutingByName(existingName, new CallbackHandler<Routing>() {
				@Override
				public void onSuccess(Routing existingRouting) {
					String desc = existingRouting.getDescription();
					Step entryStep = existingRouting.getEntryStep();
					CustomAttributes customAttributes = existingRouting.getCustomAttributes();
					String comment = "Duplicate " + existingName;
					MESApplication.getMESControllers().getRoutingController()
					.createRouting(newName, desc, entryStep, customAttributes, comment, new CallbackHandler<Routing>() {
						@Override
						public void onSuccess(Routing result) {
							callback.onSuccess(null);
						}						
					});
				}				
			});
		}
	}	
	
	private void removeModels(String... modelNames) {
		ChainCall cc = new ChainCall();
		
		class RemoveCallback extends CallbackHandler<Void> {
			private String name;
			
			public RemoveCallback(String name) {
				this.name = name;
			}
			
			@Override
			public void onSuccess(Void result) {
				editorContainer.closeTab(name);
				grid.removeSelectedData();
			}
		}
		
		if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Site) {
			for (final String name: modelNames) {
				final String comment = "Remove site";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeSite(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
			
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Area) {
			for (final String name: modelNames) {
				final String comment = "Remove area";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeArea(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.ProductionLine) {
			for (final String name: modelNames) {
				final String comment = "Remove production line";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeProductionLine(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.WorkCenter) {
			for (final String name: modelNames) {
				final String comment = "Remove work center";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeWorkCenter(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Equipment) {
			for (final String name: modelNames) {
				final String comment = "Remove equipment";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeEquipment(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Operation) {
			for (final String name: modelNames) {
				final String comment = "Remove operation";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeOperation(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		} else if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Routing) {
			for (final String name: modelNames) {
				final String comment = "Remove routing";
				ChainAction<Void> ca = new ChainAction<Void>() {
					@Override
					public void invoke(Object[] inputData, ChainCallbackHandler<Void> callback) {
						MESApplication.getMESControllers().getRoutingController().removeRouting(name, comment, new RemoveCallback(name));
					}							
				};				
				cc.add(ca, (Object[]) null);
			}
		}
				
		cc.runSequential();
	}
	
	private class CategoryItem extends SelectItem {
		
		public CategoryItem(String[] categories) {
			super("category", "Category");
			
			setValueMap(makeCSSStrings(categories));
			
			addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					grid.setData(new ListGridRecord[0]);
					
					String selCategory = (String) event.getValue();
					selCategory = selCategory.replaceAll("\\<.*?\\>", "");
					subcategoryItem.clearValue();
					
					if (selCategory.equals("Bill of Material")) {
						subcategoryItem.setValueMap(makeCSSStrings(BillOfMaterialsSubcategories));
					}
					else if (selCategory.equals("Custom Data Collection")) {
						//TODO
					}
					else if (selCategory.equals("Custom Code")) {
						//TODO
					}
					else if (selCategory.equals("Database")) {
						//TODO
					}
					else if (selCategory.equals("Data Management")) {
						//TODO
					}
					else if (selCategory.equals("Dictionary")) {
						subcategoryItem.setValueMap(makeCSSStrings(DictionarySubcategories));
					}
					else if (selCategory.equals("Recipe")) {
						//TODO
					}
					else if (selCategory.equals("Workflow")) {
						subcategoryItem.setValueMap(makeCSSStrings(RoutingSubcategories));
					}
					else if (selCategory.equals("User")) {
						//TODO
					}
					else {
						subcategoryItem.setValueMap(new String[0]);
					}
				}			
			});				
		}
		
		public String getSelection() {
			return categoryItem.getValueAsString().replaceAll("\\<.*?\\>", "");
		}

		private String[] makeCSSStrings(String[] srcStrings) {
			String[] cssStrings = new String[srcStrings.length + 1];
			cssStrings[0] = "";
			for (int i=0; i<srcStrings.length; i++) {
				String css = "<span style=\"display: inline-block; line-height: 2em;\">" + srcStrings[i] + "</span>";
				cssStrings[i+1] = css;
			}		
			return cssStrings;
		}
	}
	
	private class SubcategoryItem extends SelectItem {
		
		public SubcategoryItem() {
			super("subcategory", "Subcategory");
			
			this.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					removeButton.disable();
					duplicateButton.disable();					
					grid.setCurrentModelType(getSelectedModelType());
					onLoadingData();	
				}				
			});
		}
		
		public String getSelection() {
			String selection = getValueAsString();
			if (selection == null || selection.isEmpty()) return null;
			selection = selection.replaceAll("\\<.*?\\>", "");
			return selection;
		}
		
		public MESConstants.Object.Type getSelectedModelType() {
			String title = this.getValueAsString();
			String objectType = title.replaceAll(" ", "");
			objectType = objectType.replaceAll("\\<.*?\\>", "");
			objectType = objectType.substring(0, objectType.length()-1);
			
			try {
				MESConstants.Object.Type type = MESConstants.Object.Type.valueOf(objectType);
				return type;
			}
			catch (Exception ex) {
				SC.say("TODO");
				return null;
			}
		}
	}

	protected class ModelGrid extends ListGrid {
		private Map<MESConstants.Object.Type, ListGridRecord[]> dataMap;
		private MESConstants.Object.Type currentModelType;
		private ModelNameDataSource dataSource;
		
		public ModelGrid() {
			dataMap = new HashMap<MESConstants.Object.Type, ListGridRecord[]>();
			dataSource = new ModelNameDataSource();
			
			setWidth100();
			setHeight100();
			setShowAllRecords(true);  
			setShowFilterEditor(true);
			setAutoFetchData(false);  
			//setFilterOnKeypress(true);
			//setFetchDelay(500);  
			setDataSource(dataSource);
	        	        
	        nameField = new ListGridField("modelName", "Names");
			nameField.setCanDragResize(false);
			nameField.setWidth("100%");
			nameField.setCanFilter(true);
			
			setFields(nameField);
			setSort(new SortSpecifier[] {new SortSpecifier("modelName", SortDirection.ASCENDING)});
	        sort(0, SortDirection.ASCENDING);
			addCellDoubleClickHandler(new CellDoubleClickHandler() {
				@Override
				public void onCellDoubleClick(CellDoubleClickEvent event) {
					ModelGridRecord r = (ModelGridRecord) event.getRecord();
					String name = r.getModelName();			
					onEditingModel(name);
				}			
			});
			
			addSelectionChangedHandler(new SelectionChangedHandler() {
				@Override
				public void onSelectionChanged(SelectionEvent event) {
					ListGridRecord[] selRecords = event.getSelection();
					if (selRecords != null && selRecords.length == 1) {
						if (subcategoryItem.getSelectedModelType() == MESConstants.Object.Type.Routing) {
							duplicateButton.enable();
						}
						removeButton.enable();
					}
					else if (selRecords != null && selRecords.length > 1) {
						removeButton.enable();
						duplicateButton.disable();
					}
					else if (selRecords == null || selRecords.length == 0) {
						duplicateButton.disable();
						removeButton.disable();
					}
				}			
			});
		}
		
		protected String getSingleSelection() {
			ModelGridRecord r = (ModelGridRecord) this.getSelectedRecord();
			if (r != null) return r.getModelName();
			return null;
		}
		
		protected String[] getMultipleSelections() {
			List<String> names = null;
			ListGridRecord[] selRecords = this.getSelectedRecords();
			
			if (selRecords != null && selRecords.length > 0) {
				names = new ArrayList<String>();
				for (ListGridRecord r: selRecords) {
					ModelGridRecord mr = (ModelGridRecord) r;
					names.add(mr.getModelName());
				}
			}
						
			if (names == null) return null;
			return names.toArray(new String[names.size()]);
		}

		protected void selectRecord(ModelGridRecord record) {
			deselectAllRecords();
			selectSingleRecord(record);
		}

		protected ModelGridRecord addRecord(String name) {
			ModelGridRecord r = new ModelGridRecord(name);
			getRecordList().add(r);
			cacheCurrentModels();
			return r;
		}
		
		protected void onModelNameChanged(String oldName, String newName) {
			ModelGridRecord r = findRecord(oldName);
			if (r != null) {
				r.setModelName(newName);
				updateData(r);
				redraw();
			}
		}
		
		protected ModelGridRecord findRecord(String modelName) {
			if (modelName == null) return null;
			
			ListGridRecord[] records = (ListGridRecord[]) getRecords();
			if (records != null) {
				for (ListGridRecord r: records) {
					ModelGridRecord mr = (ModelGridRecord) r;
					String rName = mr.getModelName();
					if (rName != null && rName.equals(modelName)) {
						return mr;
					}
				}
			}
			
			return null;
		}
		
		protected void setCurrentModelType(MESConstants.Object.Type modelType) {
			this.currentModelType = modelType;
		}
		
		protected MESConstants.Object.Type getCurrentModelType() {
			return this.currentModelType;
		}
		
		private void loadData() {
			dataSource.fetchData(null, null);
		}
		
		private void cacheCurrentModels() {
			ListGridRecord[] records = this.getRecords();
			dataMap.put(currentModelType, records);
		}
		
		class ModelNameDataSource extends DataSource {	
			
			public ModelNameDataSource() {
				this.setID(SC.generateID());
				this.setDataProtocol(DSProtocol.CLIENTCUSTOM);
				this.setClientOnly(true);
			}
			
			@Override
			protected Object transformRequest(final DSRequest request) {
				final MESConstants.Object.Type modelType = ModelGrid.this.getCurrentModelType();
				if (modelType == null) return request;
				
				String namePrefix = null;
				if (ModelGrid.this.getFilterEditorCriteria() != null) {
					namePrefix = ModelGrid.this.getFilterEditorCriteria().getAttributeAsString("modelName");
				}
				RoutingController routingController = MESApplication.getMESControllers().getRoutingController();
				routingController.findObjectNames(modelType, namePrefix, new CallbackHandler<Set<String>>() {
					@Override
					public void onSuccess(Set<String> names) {
						List<ModelGridRecord> allRecords = new ArrayList<ModelGridRecord>();
						if (names != null && names.size() > 0) {
							for (String name : names) {
								ModelGridRecord r = new ModelGridRecord(name);
								allRecords.add(r);
							}
						}
						
						ListGridRecord[] recordArr = (ListGridRecord[]) allRecords.toArray(new ListGridRecord[allRecords.size()]);
						setData(recordArr);	
					}
				});
				
				return request;
			}
		}
	}
	
	private class ModelGridRecord extends ListGridRecord {
		
		public ModelGridRecord(String modelName) {
			setModelName(modelName);
		}
		
		public String getModelName() {
			return getAttribute("modelName");
		}
		
		public void setModelName(String modelName) {
			setAttribute("modelName", modelName);
		}
	}
}
