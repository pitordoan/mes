/**
 * 
 */
package com.cimpoint.mes.client.views;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.views.PropertiesEditor;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HStack;

/**
 * @author Chung Khanh Duy
 * 
 */
public class DatabaseEditor extends PropertiesEditor {

	private TextItem textConnectionName;
	private TextItem comboboxType;
	private ComboBoxItem comboboxDatabase;
	private TextItem textDatabaseServerName;
	private TextItem textDatabaseName;
	private TextItem textUsername;
	private TextItem textPassword;

	private DynamicForm dynamicForm;

	@Override
	public String getTitle() {
		return "Database Connection Editor";
	}

	@Override
	public void onInitialize(CallbackHandler<Void> callback) {
		createView();
		callback.onSuccess((Void) null);
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNewButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub
		if (!dynamicForm.validate())
			return;
		
	}

	@Override
	public void onRemoveButtionClicked(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onListingTreeNodeClicked(String treeNodeText) {
		// TODO Auto-generated method stub

	}

	private void createView() {
		dynamicForm = new DynamicForm();
		dynamicForm.setWidth(600);
		dynamicForm.setItemLayout(FormLayoutType.TABLE);
		dynamicForm.setNumCols(3);

		dynamicForm.setColWidths(250, 100, 250);

		textConnectionName = new TextItem("connectionName", "Connection Name");
		textConnectionName.setColSpan(2);
		textConnectionName.setWidth(200);
		textConnectionName.setRequired(true);
		
		comboboxType = new ComboBoxItem("type", "Type");
		comboboxType.setValueMap("Production", "Operation");
		comboboxType.setAlign(Alignment.LEFT);
		comboboxType.setWidth(200);
		comboboxType.setRequired(true);

		comboboxDatabase = new ComboBoxItem("database", "Database");
		comboboxDatabase.setValueMap("Microsoft SQL Server", "Oracle Database",
				"MySQL", "PostgreSQL", "H2");
		comboboxDatabase.setAlign(Alignment.LEFT);
		comboboxDatabase.setWidth(200);
		comboboxDatabase.setRequired(true);

		textDatabaseServerName = new TextItem("databaseServerName",
				"Database Server Name");
		textDatabaseServerName.setWidth(200);
		textDatabaseServerName.setColSpan(2);
		textDatabaseServerName.setRequired(true);
		
		textDatabaseName = new TextItem("databaseName", "Database Name");
		textDatabaseName.setWidth(200);
		textDatabaseName.setColSpan(2);
		textDatabaseName.setRequired(true);
		
		textUsername = new TextItem("username", "Username");
		textUsername.setRequired(true);
		textUsername.setWidth(200);
		textUsername.setColSpan(2);
		textPassword = new TextItem("password", "Password");
		textPassword.setWidth(200);
		textPassword.setColSpan(2);
		textPassword.setRequired(true);
		
		CanvasItem canvasItem = getDefaultActionButtonsCanvasItem();
		canvasItem.setColSpan(2);

		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { textConnectionName, comboboxType,
				comboboxDatabase, textDatabaseServerName, textDatabaseName,
				textUsername, textPassword, canvasItem });

		HStack hStack = getDefaultActionButtionsHStack();
		InitializeButton btnIntialize = new InitializeButton("Initialize");
		hStack.addMember(btnIntialize);
		super.addChild(dynamicForm);
	}

	/**
	 * IntializeButton
	 * 
	 * @author Chung Khanh Duy
	 * 
	 */
	class InitializeButton extends Button implements ClickHandler {

		public InitializeButton(String title) {
			super(title);
			this.setHeight(25);
			this.addClickHandler(this);
		}

		public void onClick(ClickEvent event) {

		}
	}

	class TypeButton extends ButtonItem implements ClickHandler {

		public TypeButton(String name, String title) {
			super(name, title);
			this.setHeight(25);
		}

		public void onClick(ClickEvent event) {

		}
	}

	class DatabaseButton extends ButtonItem implements ClickHandler {

		public DatabaseButton(String name, String title) {
			super(name, title);
			this.setHeight(25);
		}

		public void onClick(ClickEvent event) {

		}
	}
}
