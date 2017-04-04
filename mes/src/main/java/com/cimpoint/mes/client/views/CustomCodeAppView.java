package com.cimpoint.mes.client.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cimpoint.common.AppBrowser;
import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.Constants;
import com.cimpoint.common.Constants.Form.FieldProperty;
import com.cimpoint.common.Constants.Form.FieldType;
import com.cimpoint.common.views.AppView;
import com.cimpoint.mes.common.entities.EApplication;
import com.cimpoint.mes.common.entities.EForm;
import com.cimpoint.mes.common.entities.EFormField;
import com.cimpoint.mes.common.entities.EFormFieldProperty;
import com.cimpoint.mes.common.entities.ELayout;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CustomCodeAppView extends AppView {
	private boolean viewCreated = false;
	private EApplication application;
	
	public CustomCodeAppView(EApplication application) {
		this.application = application;
	}
	
	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void)null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
	private void createView() {
		if (viewCreated) return;

		try {			
			ELayout layout = this.application.getLayout();
			Layout appLayout = (layout.getLayoutType() == Constants.Form.LayoutType.VLayout)? new VLayout() : new HLayout();
			createViewFromLayout(layout, appLayout);		
			super.addMember(appLayout);
			
			viewCreated = true;
		}
		catch (Exception ex) {
			AppBrowser.showError(ex.getMessage());
		}
	}

	private void createViewFromLayout(ELayout layout, final Layout appLayout) throws Exception {
		Set<EForm> forms = layout.getForms();
		for (EForm form: forms) {
			DynamicForm dform = new DynamicForm();
			dform.setID(form.getName().replaceAll(" ", ""));
			dform.setAutoWidth();
			dform.setAutoHeight();
			Set<EFormField> fields = form.getFormFields();
			if (fields != null) {
				List<FormItem> formItems = new ArrayList<FormItem>();
				for (EFormField field: fields) {
					FormItem formItem = createFormItem(field);
					formItems.add(formItem);
				}
				
				dform.setFields(formItems.toArray(new FormItem[formItems.size()]));
			}
			
			appLayout.addMember(dform);
		}
		
		Set<ELayout> childLayouts = layout.getChildLayouts();
		for (ELayout childLayout: childLayouts) {
			Layout childAppLayout = (childLayout.getLayoutType() == Constants.Form.LayoutType.VLayout)? new VLayout() : new HLayout();
			appLayout.addMember(childAppLayout);
			createViewFromLayout(childLayout, childAppLayout);
		}
	}

	private FormItem createFormItem(EFormField field) throws Exception {
		FormItem formItem = null;
		if (field.getFieldType() == FieldType.Text) {
			formItem = new TextItem(field.getName(), field.getLabel());
			String maxValueLength = field.getPropertyValue(FieldProperty.MaxValueLength);
			if (maxValueLength != null) {
				((TextItem) formItem).setLength(Integer.valueOf(maxValueLength));
			}
		}
		else if (field.getFieldType() == FieldType.Select) {
			formItem = new SelectItem(field.getName(), field.getLabel());			
		}
		else {
			throw new RuntimeException("Field Type not supported yet");
		}
		
		String propWidth = field.getPropertyValue(FieldProperty.Width);
		if (propWidth != null) {
			if (propWidth.indexOf("%") != -1) {
				throw new Exception("Width percentage is not supported.");
			}
			else {
				formItem.setWidth(Integer.valueOf(propWidth));			
			}
		}
		
		String propHeight = field.getPropertyValue(FieldProperty.Height);
		if (propHeight != null) {
			if (propHeight.indexOf("%") != -1) {
				throw new Exception("Height percentage is not supported.");
			}
			else {
				formItem.setHeight(Integer.valueOf(propHeight));			
			}
		}		
		
		if (field.getStartNewRow()) {
			formItem.setStartRow(true);
		}
		
		if (field.getEndCurrentRow()) {
			formItem.setEndRow(true);
		}
		
		formItem.setColSpan(field.getColumnSpan());
		
		//TODO set other properties here
		
		return formItem;
	}
}
