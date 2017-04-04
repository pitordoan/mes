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
package com.cimpoint.mes.client.views;

import java.util.HashSet;
import java.util.Set;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Utils;
import com.cimpoint.common.views.GridRecordEditor;
import com.cimpoint.mes.client.MESApplication;
import com.cimpoint.mes.client.controllers.BillOfMarterialController;
import com.cimpoint.mes.client.objects.BomItem;
import com.cimpoint.mes.client.objects.BomItemNode;
import com.cimpoint.mes.client.objects.ClientObjectUtils;
import com.cimpoint.mes.client.objects.Part;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VStack;

public class BomItemEditor extends GridRecordEditor {

	private DynamicForm dynamicForm;
	private SelectItem containerPartItem;
	private SelectItem partItem;
	private SelectItem revisionItem;
	private BillOfMarterialController billOfMarterialController = MESApplication.getMESControllers().getBillOfMarterialController();
	private BOMEditor bomEditor;
	
	public BomItemEditor(BOMEditor bomEditor) {
		super("BOM Item Editor", 210, 140);
		
		this.bomEditor = bomEditor;
		
		VStack vStack = new VStack();
		vStack.setMembersMargin(5);
		vStack.setBorder("1px solid LightGray");
		
		//start form
		dynamicForm = new DynamicForm();
		dynamicForm.setHeight(140);
		dynamicForm.setWidth(210);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setLeft(30);
		
		containerPartItem = new SelectItem("containerPart", "Container Part");
		containerPartItem.setWidth(200);
//		containerPartItem.setRequired(true);

		partItem = new SelectItem("part", "Part");
		partItem.setWidth(200);
		partItem.setRequired(true);
		partItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String partName = partItem.getValueAsString();
				billOfMarterialController.findRevisionsByPartName(partName, new CallbackHandler<Set<String>>() {

					@Override
					public void onSuccess(Set<String> result) {
						String[] sortedRevision = Utils.toSortedStringArray(result);
						revisionItem.setValueMap(sortedRevision);
					}
				});
				
			}
		});
		
		revisionItem = new SelectItem("revision", "Revision");
		revisionItem.setWidth(100);
		revisionItem.setRequired(true);
		
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { containerPartItem, partItem, revisionItem, getDefaultActionButtonsCanvasItem() });
		
		vStack.setLayoutMargin(20);
		vStack.addMember(dynamicForm);
		
		super.addItem(vStack);
		
		reset();
	}

	public void reset() {
		// load all part and revision into container part select item
		billOfMarterialController.findAllPartNameWithRevisions(new CallbackHandler<Set<String>>() {
			@Override
			public void onSuccess(Set<String> result) {
				Set<String> containerPartNames = new HashSet<String>();
				containerPartNames.add(MESConstants.SPACE_STRING);
				containerPartNames.addAll(result);
				String[] sortedPartRevision = com.cimpoint.common.Utils.toSortedStringArray(containerPartNames);
				Set<String> partNames = new HashSet<String>();
				for (String s : result) {
					String name = s.split("-")[0];
					partNames.add(name.trim());
				}
				containerPartItem.setValueMap(sortedPartRevision);
				partItem.setValueMap(Utils.toSortedStringArray(partNames));
			}
		});
		
		
	}
	
	@Override
	public void onOKButtionClicked(ClickEvent event) {
		if (!dynamicForm.validate())
			return;
		
		// get value
		final String strContainer = containerPartItem.getValueAsString();
		String values[] = new String[2];
		
		if (strContainer == null || MESConstants.SPACE_STRING.equals(strContainer)) {
			values[0] = values[1] = "";
		} else {
			values = strContainer.split(MESConstants.REV_FOR_TEXT);
		}
		
		final String parentId = containerPartItem.getValueAsString();
		final String partName = partItem.getValueAsString();
		final String revision = revisionItem.getValueAsString();
		
		billOfMarterialController.findPartByNameAndRevision(values[0].trim(), values[1].trim(), new CallbackHandler<Part>() {

			@Override
			public void onSuccess(final Part containerPart) {
					billOfMarterialController.findPartByNameAndRevision(partName, revision, new CallbackHandler<Part>() {
						@Override
						public void onSuccess(Part part) {
							if (part != null) {
								Long containerPartId = null;
								String containerName = "";
								String containerRevision = "";
								// if user does not select container part
								if (containerPart != null) {
									containerPartId = containerPart.getId();
									containerName = containerPart.getName();
									containerRevision = containerPart.getRevision();
								}
								Long partId = part.getId();
								
								BomItem bomItem = ClientObjectUtils.BomItems.newBomItem(containerPartId, containerRevision, containerName, partId, revision, part.getName());
								BomItemNode node = new BomItemNode(bomItem, parentId);
								bomEditor.addBomItem(node);
								bomEditor.setDataForGrid();
							}
							
						}
					});
				}
		});
		
		// close dialog
		this.destroy();
	}

	@Override
	public void onCancelButtionClicked(ClickEvent event) {
		this.destroy();
	}

}
