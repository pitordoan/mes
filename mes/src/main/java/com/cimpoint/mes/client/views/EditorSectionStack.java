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
package com.cimpoint.mes.client.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.entities.CustomAttributes;
import com.cimpoint.common.views.CGrid.ConfirmButton;
import com.cimpoint.common.views.CGrid.EditorRecord;
import com.cimpoint.common.views.CMessageDialog.MessageButton;
import com.cimpoint.common.views.CSectionStack;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.objects.Area;
import com.cimpoint.mes.client.objects.Equipment;
import com.cimpoint.mes.client.objects.ProductionLine;
import com.cimpoint.mes.client.objects.Step;
import com.cimpoint.mes.client.objects.Transition;
import com.cimpoint.mes.client.objects.TransitionRecord;
import com.cimpoint.mes.client.objects.WorkCenter;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class EditorSectionStack extends CSectionStack {

	public EditorSectionStack(String width, String height) {
		super(width, height);
	}

	public void setGridCustomAttributesData(String gridName, CustomAttributes customAttributes) {
		if (customAttributes == null) {
			CustomAttributesRecord[] arr = new CustomAttributesRecord[10];
			arr[0] = new CustomAttributesRecord("Attribute1", null);
			arr[1] = new CustomAttributesRecord("Attribute2", null);
			arr[2] = new CustomAttributesRecord("Attribute3", null);
			arr[3] = new CustomAttributesRecord("Attribute4", null);
			arr[4] = new CustomAttributesRecord("Attribute5", null);
			arr[5] = new CustomAttributesRecord("Attribute6", null);
			arr[6] = new CustomAttributesRecord("Attribute7", null);
			arr[7] = new CustomAttributesRecord("Attribute8", null);
			arr[8] = new CustomAttributesRecord("Attribute9", null);
			arr[9] = new CustomAttributesRecord("Attribute10", null);

			super.setGridData(gridName, arr);
		} else {
			CustomAttributesRecord[] arr = new CustomAttributesRecord[10];
			arr[0] = new CustomAttributesRecord("Attribute1", customAttributes.getAttribute1());
			arr[1] = new CustomAttributesRecord("Attribute2", customAttributes.getAttribute2());
			arr[2] = new CustomAttributesRecord("Attribute3", customAttributes.getAttribute3());
			arr[3] = new CustomAttributesRecord("Attribute4", customAttributes.getAttribute4());
			arr[4] = new CustomAttributesRecord("Attribute5", customAttributes.getAttribute5());
			arr[5] = new CustomAttributesRecord("Attribute6", customAttributes.getAttribute6());
			arr[6] = new CustomAttributesRecord("Attribute7", customAttributes.getAttribute7());
			arr[7] = new CustomAttributesRecord("Attribute8", customAttributes.getAttribute8());
			arr[8] = new CustomAttributesRecord("Attribute9", customAttributes.getAttribute9());
			arr[9] = new CustomAttributesRecord("Attribute10", customAttributes.getAttribute10());

			super.setGridData(gridName, arr);
		}
	}

	public void setGridAreasData(String gridName, Set<Area> areas) {
		if (areas == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<AreaRecord> ar = new ArrayList<AreaRecord>();
			for (Area a : areas) {
				AreaRecord r = new AreaRecord(a);
				ar.add(r);
			}

			Collections.sort(ar, new Comparator<AreaRecord>() {
				@Override
				public int compare(AreaRecord a1, AreaRecord a2) {
					return Utils.compareStrings(a1.getName(), a2.getName());
				}				
			});
			
			super.setGridData(gridName, ar.toArray(new AreaRecord[ar.size()]));
		}
	}

	public void setGridWorkCentersData(String gridName, Set<WorkCenter> workCenters) {
		if (workCenters == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<WorkCenterRecord> wcList = new ArrayList<WorkCenterRecord>();
			for (WorkCenter wc : workCenters) {
				WorkCenterRecord r = new WorkCenterRecord(wc);
				wcList.add(r);
			}

			Collections.sort(wcList, new Comparator<WorkCenterRecord>() {
				@Override
				public int compare(WorkCenterRecord o1, WorkCenterRecord o2) {
					return Utils.compareStrings(o1.getName(), o2.getName());
				}				
			});
			
			super.setGridData(gridName, wcList.toArray(new WorkCenterRecord[wcList.size()]));
		}
	}

	public Set<WorkCenter> getGridSectionDataAsWorkCenterSet(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null) {
			Set<WorkCenter> workCenters = new HashSet<WorkCenter>();
			for (ListGridRecord r : records) {
				WorkCenterRecord plr = (WorkCenterRecord) r;
				WorkCenter pdl = plr.getWorkCenter();
				if (pdl != null) {
					workCenters.add(pdl);
				}
			}
			return workCenters;
		}

		return null;
	}

	public Set<Equipment> getGridSectionDataAsEquipmentSet(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null) {
			Set<Equipment> eqs = new HashSet<Equipment>();
			for (ListGridRecord r : records) {
				EquipmentRecord eqr = (EquipmentRecord) r;
				Equipment eq = eqr.getEquipment();
				if (eq != null) {
					eqs.add(eq);
				}
			}
			return eqs;
		}

		return null;
	}

	public Set<ProductionLine> getGridSectionDataAsProductionLineSet(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null) {
			Set<ProductionLine> prodLines = new HashSet<ProductionLine>();
			for (ListGridRecord r : records) {
				ProductionLineRecord plr = (ProductionLineRecord) r;
				ProductionLine pdl = plr.getProductionLine();
				if (pdl != null) {
					prodLines.add(pdl);
				}
			}
			return prodLines;
		}

		return null;
	}
	
	public Set<Step> getGridSectionDataAsStepSet(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null) {
			Set<Step> steps = new HashSet<Step>();
			for (ListGridRecord r : records) {
				StepRecord record = (StepRecord) r;
				Step s = record.getStep();
				if (s != null) {
					steps.add(s);
				}
			}
			return steps;
		}

		return null;
	}

	/**
	 * Get data from transition grid
	 * @param sectionName
	 * @return
	 */
	public Set<Transition> getGridSectionDataAsTransitionSet(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null) {
			Set<Transition> transitions = new HashSet<Transition>();
			for (ListGridRecord r : records) {
				TransitionRecord record = (TransitionRecord) r;
				Transition trans = record.getTransition();
				if (trans != null) {
					transitions.add(trans);
				}
			}
			return transitions;
		}

		return null;
	}
	
	private void addGridRecord(String gridName, ListGridRecord record, boolean sorting) {
		List<ListGridRecord> records = new ArrayList<ListGridRecord>();
		ListGridRecord[] existingRecords = super.getGridData(gridName);
		if (existingRecords != null && existingRecords.length > 0) {
			records.addAll(Arrays.asList(existingRecords));
		}			
		records.add(record);
		
		if (sorting) {
			Collections.sort(records, new Comparator<ListGridRecord>() {
				@Override
				public int compare(ListGridRecord o1, ListGridRecord o2) {
					return Utils.compareStrings(o1.getAttributeAsString("name"), o2.getAttributeAsString("name"));
				}				
			});
		}
		
		super.setGridData(gridName, records.toArray(new ListGridRecord[records.size()]));
	}
	
//	public TreeGrid getTreeGrid() {
//		
//	}
	
	private void updateGridRecord(String gridName, ListGridRecord record) {
		List<ListGridRecord> records = new ArrayList<ListGridRecord>();
		ListGridRecord[] existingRecords = super.getGridData(gridName);
		if (existingRecords != null && existingRecords.length > 0) {
			records.addAll(Arrays.asList(existingRecords));
		}			
		
		for (ListGridRecord r: records) {
			if (r.getAttributeAsString("name").equals(record.getAttributeAsString("name"))) {
				records.remove(r);
				break;
			}
		}
		
		records.add(record);
		
		super.setGridData(gridName, records.toArray(new ListGridRecord[records.size()]));
	}
	
	private void removeGridRecord(String gridName, ListGridRecord record) {
		List<ListGridRecord> records = new ArrayList<ListGridRecord>();
		ListGridRecord[] existingRecords = super.getGridData(gridName);
		if (existingRecords != null && existingRecords.length > 0) {
			records.addAll(Arrays.asList(existingRecords));
		}			
		
		for (ListGridRecord r: records) {
			if (r.getAttributeAsString("name").equals(record.getAttributeAsString("name"))) {
				records.remove(r);
				break;
			}
		}
		
		super.setGridData(gridName, records.toArray(new ListGridRecord[records.size()]));
	}
		
	public void addGridTransitionData(String gridName, Transition newTransition) {
		if (newTransition != null) {
			addGridRecord(gridName, new TransitionRecord(newTransition), false);
		}
	}
	
	public void updateGridTransitionData(String gridName, Transition transition) {
		if (transition != null) {
			updateGridRecord(gridName, new TransitionRecord(transition));
		}
	}
	
	public void removeGridTransitionData(String gridName, Transition transition) {
		if (transition != null) {
			removeGridRecord(gridName, new TransitionRecord(transition));
		}
	}
	
	public void setGridStepsData(String gridName, Set<Step> steps) {
		if (steps == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<StepRecord> rList = new ArrayList<StepRecord>();
			for (Step s : steps) {
				StepRecord r = new StepRecord(s);
				rList.add(r);
			}

			Collections.sort(rList, new Comparator<StepRecord>() {
				@Override
				public int compare(StepRecord o1, StepRecord o2) {
					return Utils.compareStrings(o1.getName(), o2.getName());
				}				
			});
			
			super.setGridData(gridName, rList.toArray(new StepRecord[rList.size()]));
		}
	}
	
	public void addGridStepData(String gridName, Step newStep) {
		if (newStep != null) {
			addGridRecord(gridName, new StepRecord(newStep), true);
		}
	}
	
	public void updateGridStepData(String gridName, Step step) {
		if (step != null) {
			updateGridRecord(gridName, new StepRecord(step));
		}
	}
	
	public void removeGridStepData(String gridName, Step step) {
		if (step != null) {
			removeGridRecord(gridName, new StepRecord(step));
		}
	}

	public void setGridEquipmentsData(String gridName, Set<Equipment> equipments) {
		if (equipments == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<EquipmentRecord> rList = new ArrayList<EquipmentRecord>();
			for (Equipment eq : equipments) {
				EquipmentRecord r = new EquipmentRecord(eq);
				rList.add(r);
			}

			Collections.sort(rList, new Comparator<EquipmentRecord>() {
				@Override
				public int compare(EquipmentRecord o1, EquipmentRecord o2) {
					return Utils.compareStrings(o1.getName(), o2.getName());
				}				
			});
			
			super.setGridData(gridName, rList.toArray(new EquipmentRecord[rList.size()]));
		}
	}

	public void setGridProductionLinesData(String gridName, Set<ProductionLine> prodLines) {
		if (prodLines == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<ProductionLineRecord> pdlr = new ArrayList<ProductionLineRecord>();
			for (ProductionLine pdl : prodLines) {
				ProductionLineRecord r = new ProductionLineRecord(pdl);
				pdlr.add(r);
			}

			Collections.sort(pdlr, new Comparator<ProductionLineRecord>() {
				@Override
				public int compare(ProductionLineRecord o1, ProductionLineRecord o2) {
					return Utils.compareStrings(o1.getName(), o2.getName());
				}				
			});
			
			super.setGridData(gridName, pdlr.toArray(new ProductionLineRecord[pdlr.size()]));
		}
	}
	
	public void setGridTransitionsData(String gridName, Set<Transition> transitions) {
		if (transitions == null) {
			super.setGridData(gridName, new ListGridRecord[0]);
		} else {
			List<TransitionRecord> records = new ArrayList<TransitionRecord>();
			for (Transition t : transitions) {
				TransitionRecord r = new TransitionRecord(t);
				records.add(r);
			}

			Collections.sort(records, new Comparator<TransitionRecord>() {
				@Override
				public int compare(TransitionRecord o1, TransitionRecord o2) {
					return Utils.compareStrings(o1.getName(), o2.getName());
				}				
			});
			
			super.setGridData(gridName, records.toArray(new TransitionRecord[records.size()]));
		}
	
	}

	public void addNameGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_MULTIPLE;
		boolean expanded = false;
		ListGridField nameField = new ListGridField("name", "Name");
		this.addGridSection(name, title, new ListGridField[] { nameField }, buttonsBitwise, expanded);
	}

	public void addNameDescriptionGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_MULTIPLE;
		boolean expanded = false;
		ListGridField nameField = new ListGridField("name", "Name");
		ListGridField descField = new ListGridField("description", "Description");
		descField.setCanEdit(false);
		this.addGridSection(name, title, new ListGridField[] { nameField, descField }, buttonsBitwise, expanded);
	}

	public void addNameValueGridSection(String name, String title) {
		Integer buttonsBitwise = CSectionStack.BUTTON_EDIT_CELL;
		boolean expanded = false;
		ListGridField nameField = new ListGridField("name", "Name");
		nameField.setCanEdit(false);
		ListGridField valueField = new ListGridField("value", "Value");
		this.addGridSection(name, title, new ListGridField[] { nameField, valueField }, buttonsBitwise, expanded);
	}

	public CustomAttributes getGridSectionDataAsCustomAttributes(String sectionName) {
		ListGridRecord[] records = super.getGridData(sectionName);
		if (records != null && records.length > 0) {
			CustomAttributes ca = new CustomAttributes();
			for (ListGridRecord r : records) {
				CustomAttributesRecord car = (CustomAttributesRecord) r;
				ca.setAttribute(car.getName(), car.getValue());
			}
			return ca;
		}

		return null;
	}
	
	public Step findGridSelectedStep(String gridName) {
		ListGridRecord r = super.getGridSelectedRecord(gridName);
		if (r != null) {
			StepRecord stepR = (StepRecord) r;
			return stepR.getStep();
		}
		return null;
	}

	public class CustomAttributesRecord extends ListGridRecord {

		public CustomAttributesRecord() {
		}

		public CustomAttributesRecord(String name, String value) {
			this.setName(name);
			this.setValue(value);
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			this.setAttribute("name", name);
		}

		public String getValue() {
			return this.getAttributeAsString("value");
		}

		public void setValue(String value) {
			this.setAttribute("value", value);
		}
	}

	@Override
	public void onGridMultiRecordsEditorEnter(final String sectionName, final ListGrid grid, final CallbackHandler<EditorRecord[]> callback) {
	}

	@Override
	public void onGridMultiRecordsEditorExit(final String sectionName, final ListGrid grid, final EditorRecord[] records, final ConfirmButton button) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onGridCellEditorEnter(String sectionName, ListGrid grid, int row, int col, Object value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onGridCellEditorExit(String sectionName, ListGrid grid, int row, int col, Object newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onGridRecordAdd(String sectionName, final ListGrid grid, final CallbackHandler<ListGridRecord> callback) {
		if (sectionName.equals("customAttributes")) {
			int nCols = grid.getFields().length;
			for (int col = 0; col < nCols; col++) {
				TextItem fieldEditor = new TextItem();
				grid.getField(col).setEditorType(fieldEditor);
			}

			CustomAttributesRecord record = new CustomAttributesRecord();
			callback.onSuccess(record);
		}
	}

	@Override
	public void onGridRecordRemove(ListGrid grid, ListGridRecord selectedRecord, CallbackHandler<MessageButton> callback) {
		String msg = "Are you sure of removing the selected row?";
		MESApplication.showYesNoMessage(msg, callback);
	}
	
	@Override
	public void onGridRecordEditorEnter(String sectionName, ListGrid grid, GridRecordEditor editor) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public GridRecordEditor getGridRecordEditor(String sectionName, ListGrid grid) {
		// TODO Auto-generated method stub
		return null;
	}

	public class AreaRecord extends ListGridRecord {
		private Area area;

		public AreaRecord() {
		}

		public AreaRecord(Area area) {
			setArea(area);
		}

		public Area getArea() {
			return area;
		}

		public void setArea(Area area) {
			if (area != null) {
				this.area = area;
				this.setName(area.getName());
				this.setDescription(area.getDescription());
			}
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			if (area != null) {
				this.area.setName(name);
			}
			this.setAttribute("name", name);
		}

		public String getDescription() {
			return this.getAttributeAsString("description");
		}

		public void setDescription(String description) {
			if (area != null) {
				this.area.setDescription(description);
			}
			this.setAttribute("description", description);
		}
	}

	public class WorkCenterRecord extends ListGridRecord {
		private WorkCenter wc;

		public WorkCenterRecord() {
		}

		public WorkCenterRecord(WorkCenter wc) {
			setWorkCenter(wc);
		}

		public WorkCenter getWorkCenter() {
			return wc;
		}

		public void setWorkCenter(WorkCenter prodLine) {
			if (prodLine != null) {
				this.wc = prodLine;
				this.setName(prodLine.getName());
				this.setDescription(prodLine.getDescription());
			}
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			if (wc != null) {
				this.wc.setName(name);
			}
			this.setAttribute("name", name);
		}

		public String getDescription() {
			return this.getAttributeAsString("description");
		}

		public void setDescription(String description) {
			if (wc != null) {
				this.wc.setDescription(description);
			}
			this.setAttribute("description", description);
		}
	}

	public class StepRecord extends ListGridRecord {
		private Step step;

		public StepRecord() {
		}

		public StepRecord(Step step) {
			setStep(step);
		}

		public Step getStep() {
			return step;
		}

		public void setStep(Step step) {
			if (step != null) {
				this.step = step;
				this.setName(step.getName());
				this.setDescription(step.getDescription());
			}
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			if (step != null) {
				this.step.setName(name);
			}
			this.setAttribute("name", name);
		}

		public String getDescription() {
			return this.getAttributeAsString("description");
		}

		public void setDescription(String description) {
			if (step != null) {
				this.step.setDescription(description);
			}
			this.setAttribute("description", description);
		}
	}

	public class EquipmentRecord extends ListGridRecord {
		private Equipment equipment;

		public EquipmentRecord() {
		}

		public EquipmentRecord(Equipment equipment) {
			setEquipment(equipment);
		}

		public Equipment getEquipment() {
			return equipment;
		}

		public void setEquipment(Equipment equipment) {
			if (equipment != null) {
				this.equipment = equipment;
				this.setName(equipment.getName());
				this.setDescription(equipment.getDescription());
			}
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			if (equipment != null) {
				this.equipment.setName(name);
			}
			this.setAttribute("name", name);
		}

		public String getDescription() {
			return this.getAttributeAsString("description");
		}

		public void setDescription(String description) {
			if (equipment != null) {
				this.equipment.setDescription(description);
			}
			this.setAttribute("description", description);
		}
	}

	public class ProductionLineRecord extends ListGridRecord {
		private ProductionLine prodLine;

		public ProductionLineRecord() {
		}

		public ProductionLineRecord(ProductionLine prodLine) {
			setProductionLine(prodLine);
		}

		public ProductionLine getProductionLine() {
			return prodLine;
		}

		public void setProductionLine(ProductionLine prodLine) {
			if (prodLine != null) {
				this.prodLine = prodLine;
				this.setName(prodLine.getName());
				this.setDescription(prodLine.getDescription());
			}
		}

		public String getName() {
			return this.getAttributeAsString("name");
		}

		public void setName(String name) {
			if (prodLine != null) {
				this.prodLine.setName(name);
			}
			this.setAttribute("name", name);
		}

		public String getDescription() {
			return this.getAttributeAsString("description");
		}

		public void setDescription(String description) {
			if (prodLine != null) {
				this.prodLine.setDescription(description);
			}
			this.setAttribute("description", description);
		}
	}
	
}
