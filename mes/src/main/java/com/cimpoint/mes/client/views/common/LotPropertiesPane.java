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
package com.cimpoint.mes.client.views.common;

import java.util.ArrayList;
import java.util.List;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.mes.client.objects.Lot;
import com.cimpoint.mes.client.objects.TransitionAttributes;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

public class LotPropertiesPane extends DetailsPane {
	private final String[][] fields = {
			{"lotNumber", "Lot Name"},
			{"prodLine", "Prod. Line"},
			{"workCenter", "Work Center"},
			{"routing", "Routing"},
			{"step", "Step"},
			{"operation", "Operation"},
			
			{"status", "Status"},
			{"quantity", "Qty"},
			{"uom", "UoM"},
			{"partNumber", "Part No."},
			{"partRevision", "Part Rev."}
	};
	
	private PropertiesForm form;
	
	public LotPropertiesPane() {
		form = new PropertiesForm();
		this.addChild(form);
	}
	
	@Override
	public void loadData(Object lot) {
		form.loadData((Lot) lot);
	}
	
	private class PropertiesForm extends DynamicForm {
		
		public PropertiesForm() {
			this.setWidth100();
			this.setHeight100();
			this.setMargin(5);
			
			List<FormItem> items = new ArrayList<FormItem>();
			for (String[] fieldAttrs: fields) {
				String name = fieldAttrs[0];
				String title = fieldAttrs[1];
				StaticTextItem item = new StaticTextItem(name, title);
				item.setWrapTitle(false);
				item.setWidth("100%");
				item.setCellHeight(20);
				items.add(item);	
			}
			
			this.setFields(items.toArray(new FormItem[items.size()]));
		}

		public void loadData(final Lot lot) {
			if (lot != null) {				
				lot.findTransitionAttributes(new CallbackHandler<TransitionAttributes>() {
					@Override
					public void onSuccess(TransitionAttributes attrs) {
						getItem("lotNumber").setValue(lot.getNumber());
						getItem("quantity").setValue(lot.getQuantity().asString());
						getItem("partNumber").setValue(lot.getMaterialName());
						getItem("partRevision").setValue(lot.getMaterialRevision());
						getItem("uom").setValue(lot.getUnitOfMeasure());
						
						if (attrs != null) {
							getItem("prodLine").setValue(attrs.getProductionLineName());
							getItem("workCenter").setValue(attrs.getWorkCenterName());
							getItem("routing").setValue(attrs.getRoutingName());
							getItem("step").setValue(attrs.getStepName());
							getItem("operation").setValue(attrs.getOperationName());
							getItem("status").setValue(attrs.getStepStatus());						
						}
					}	
				});
			}
			else {
				getItem("lotNumber").setValue("");
				getItem("quantity").setValue("");
				getItem("partNumber").setValue("");
				getItem("partRevision").setValue("");
				getItem("uom").setValue("");
				getItem("prodLine").setValue("");
				getItem("workCenter").setValue("");
				getItem("routing").setValue("");
				getItem("step").setValue("");
				getItem("operation").setValue("");
				getItem("status").setValue("");
			}
		}
	}
	
}