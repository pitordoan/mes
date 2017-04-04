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
package com.cimpoint.mes.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.cimpoint.mes.client.views.AreaEditorSectionStack;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VStack;

@Service("customCodeServlet")
public class CustomCodeServlet extends HttpServlet {
	private static final long serialVersionUID = -6663365438749891076L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action").equals("GetModule")) {
			PrintWriter out = response.getWriter();
			DynamicForm form = createTestForm();			
			JavaScriptObject jsObj = form.getJsObj();
			String src = jsObj.toSource();
			out.write(src);
		}	
	}
	
	private DynamicForm createTestForm() {
		DynamicForm dynamicForm = new DynamicForm();
		
		dynamicForm.setWidth(624);
		dynamicForm.setHeight("100%");
		dynamicForm.setWrapItemTitles(false);
		
		VStack vStack = new VStack();
		vStack.setLeft(30);
		vStack.setHeight100();
		
		TextItem nameTextItem = new TextItem("name", "Name");
		nameTextItem.setWidth(200);
		nameTextItem.setLength(50);
		nameTextItem.setRequired(true);
		
		TextItem descTextItem = new TextItem("desc", "Description");
		descTextItem.setWidth(500);
		descTextItem.setLength(255);
		descTextItem.setRequired(false);
		
		SelectItem siteSelectItem = new SelectItem("site", "Site");
		siteSelectItem.setWidth(200);
		siteSelectItem.setRequired(false);
		//end form
				
		dynamicForm.setCellPadding(3);
		dynamicForm.setFields(new FormItem[] { nameTextItem, descTextItem, siteSelectItem });
		
		return dynamicForm;
	}
}
