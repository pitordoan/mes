package com.cimpoint.mes.client.views.editors;

public class NameProperty extends ModelProperty {

	public NameProperty(String property, ModelPropertyEditor propertyEditor) {
		super(ModelProperty.Type.Text, property, propertyEditor);
		setReadOnly(true);
		setRequired(true);
	}
}
