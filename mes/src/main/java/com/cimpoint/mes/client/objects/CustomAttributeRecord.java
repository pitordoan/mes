package com.cimpoint.mes.client.objects;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CustomAttributeRecord  extends ListGridRecord {

	public CustomAttributeRecord(String customAttribute, String lastModifiedTime, String user, String value) {
		setAttribute("customAttribute", customAttribute);
		setAttribute("last_modified_time", lastModifiedTime);
		setAttribute("user", user);
		setAttribute("value", value);

	}

	public String getCustomAttribute() {
		return getAttribute("step");
	}

	public void setCustomAttribute(String customAttribute) {
		setAttribute("customAttribute", customAttribute);
	}

	public String getLastModifiedTime() {
		return getAttribute("last_modified_time");
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		setAttribute("last_modified_time", lastModifiedTime);
	}

	public String getUser() {
		return getAttribute("user");
	}

	public void setUser(String user) {
		setAttribute("user", user);
	}

	public String getValue() {
		return getAttribute("value");
	}

	public void setValue(String value) {
		setAttribute("value", value);
	}
}
