/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     Tai Huynh - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.client.views;

import com.cimpoint.common.views.CDialog;
import com.cimpoint.mes.common.MESConstants;
import com.smartgwt.client.widgets.Button;
/**
 * @author Tai Huynh
 * this class extends the Button. 
 * Put the active status to enable or disable the button.
 * 
 * */
public class CButton extends Button{ 

	private boolean isActive;
	private CDialog dialog;
	

	private boolean isVisibleDialog;
	
	public CButton(String title) {
		super(title);
		this.isActive = true;
		isVisibleDialog = false;
		this.setWidth100();
		this.setHeight(MESConstants.BUTTON_HEIGHT);
	}

	public CButton(boolean isActive) {
		super();
		this.isActive = isActive;
		isVisibleDialog = false;
		this.setWidth100();
		this.setHeight(MESConstants.BUTTON_HEIGHT);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void initDialog(String label, String id){
		/*if(dialog == null){
			dialog = new WoTrackingDialog(label, 500, 400);
//			dialog.createView();
			dialog.hide();
		}*/
	}
	
	public CDialog getDialog() {
		return dialog;
	}

	public void setDialog(CDialog dialog) {
		this.dialog = dialog;
	}

	public boolean isVisibleDialog() {
		return isVisibleDialog;
	}

	public void setVisibleDialog(boolean isVisibleDialog) {
		this.isVisibleDialog = isVisibleDialog;
	}

}
