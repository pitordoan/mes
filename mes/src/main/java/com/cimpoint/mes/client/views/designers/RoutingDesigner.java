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
package com.cimpoint.mes.client.views.designers;

import com.cimpoint.common.CallbackHandler;
import com.cimpoint.common.ChainCall.ChainCallbackHandler;
import com.cimpoint.mes.client.views.editors.ModelEditor;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class RoutingDesigner extends VLayout implements ModelDesigner {
	private RoutingCanvas routingCanvas;	
	private ToolStripButton selButton;
	private ToolStripButton newStepButton;
	private ToolStripButton newTransitionButton;
	private ToolStripButton removeButton;
	private ModelEditor modelEditor;
	//private float zoomLevel = 1.0f;

	public RoutingDesigner() {
		this.setWidth("*");
		routingCanvas = new RoutingCanvas(0, 0, "100%", "100%");
		
		ToolStrip editorToolStrip = new ToolStrip();
		editorToolStrip.setSize("100%", "30px");
		editorToolStrip.setAlign(Alignment.RIGHT);

		ToolStripButton refreshButton = new ToolStripButton();
		refreshButton.setIcon("[SKIN]actions/refresh.png");
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modelEditor.onRefreshButtionClicked(new CallbackHandler<Void>() {
					@Override
					public void onSuccess(Void result) {
						modelEditor.setEditing(false);
					}
				});
			}
		});

		/*DynamicForm zoomValueForm = new DynamicForm();
		zoomValueForm.setAutoWidth();
		final TextItem zoomValueItem = new TextItem("zoomLevel");
		zoomValueItem.setEndRow(false);
		zoomValueItem.setShowTitle(false);
		zoomValueItem.setWidth(30);
		zoomValueItem.setValue("100");
		zoomValueItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					String msg = "Please enter a number in range [0..200]";
					try {
						int zoom = Integer.valueOf(zoomValueItem.getValueAsString());
						if (zoom < 0 || zoom > 200) {
							MESApplication.showOKMessage(msg);
						}
						else {
							zoomLevel = (float) (zoom * 0.01);
							routingCanvas.zoom(zoomLevel);
						}
					} catch (Exception ex) {
						MESApplication.showOKMessage(msg);
					}
				}
			}			
		});
		
		StaticTextItem  zoomPercentItem = new StaticTextItem ("zoomLabel");	
		zoomPercentItem.setShowTitle(false);
		zoomPercentItem.setStartRow(false);
		zoomPercentItem.setValue("%");
		
		zoomValueForm.setFields(zoomValueItem, zoomPercentItem);
		
		ToolStripButton zoomOutButton = new ToolStripButton();
		zoomOutButton.setIcon("actions/22x22/zoom_out.png");
		zoomOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (zoomLevel > 0.1f) {
					zoomLevel -= 0.1;
				}
				routingCanvas.zoom(zoomLevel);
				zoomValueItem.setValue((int) (zoomLevel * 100 + 0.5));
			}
		});

		ToolStripButton zoomInButton = new ToolStripButton();
		zoomInButton.setIcon("actions/22x22/zoom_in.png");
		zoomInButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (zoomLevel < 2.0f) {
					zoomLevel += 0.1;
				}
				routingCanvas.zoom(zoomLevel);
				zoomValueItem.setValue((int) (zoomLevel * 100 + 0.5));
			}
		});*/

		selButton = new ToolStripButton();
		selButton.setShowRollOver(false);  
    	selButton.setIcon("actions/22x22/select.png");  
    	selButton.setActionType(SelectionType.RADIO);  
    	selButton.setRadioGroup("mode");  
    	selButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				routingCanvas.setMode(WorkflowCanvas.Mode.Select);
			}    		
    	});
    	
    	newStepButton = new ToolStripButton();
    	newStepButton.setShowRollOver(false);  
    	newStepButton.setIcon("actions/22x22/step.png");  
    	newStepButton.setActionType(SelectionType.CHECKBOX);  
    	newStepButton.setActionType(SelectionType.RADIO);  
    	newStepButton.setRadioGroup("mode");  
    	newStepButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				routingCanvas.setMode(WorkflowCanvas.Mode.AddStep);
			}    		
    	});
    	
    	newTransitionButton = new ToolStripButton();
    	newTransitionButton.setShowRollOver(false);  
    	newTransitionButton.setIcon("actions/22x22/arrow.png");  
    	newTransitionButton.setActionType(SelectionType.RADIO);  
    	newTransitionButton.setRadioGroup("mode");  
    	newTransitionButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				routingCanvas.setMode(WorkflowCanvas.Mode.ConnectSteps);
			}    		
    	});
    	    	
    	removeButton = new ToolStripButton();
		removeButton.setIcon("[SKIN]actions/remove.png");
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				modelEditor.onRemoveButtionClicked(new CallbackHandler<Void>() {
					@Override
					public void onSuccess(Void result) {
						routingCanvas.removeSelection();
						modelEditor.setEditing(true);
					}
				});
			}
		});

		ToolStripButton saveButton = new ToolStripButton();
		saveButton.setIcon("[SKIN]actions/save.png");
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				performSave(null);
			}
		});

		editorToolStrip.addButton(refreshButton);
		//editorToolStrip.addButton(zoomOutButton);
		//editorToolStrip.addMember(zoomValueForm);
		//editorToolStrip.addButton(zoomInButton);
		editorToolStrip.addButton(selButton);
		editorToolStrip.addButton(newStepButton);
		editorToolStrip.addButton(newTransitionButton);
		editorToolStrip.addButton(removeButton);
		editorToolStrip.addButton(saveButton);

		this.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				removeButton.disable();
			}
		});

		setMembers(editorToolStrip, routingCanvas); 
	}

	private void performSave(final ChainCallbackHandler<Void> callback) {
		modelEditor.onSaveButtionClicked(new CallbackHandler<Void>() {
			@Override
			public void onSuccess(Void result) {
				modelEditor.setEditing(false);
				if (callback != null)
					callback.onSuccess(null);
			}
		});
	}
	
	public void setSelectionHandler(final SelectionHandler handler) {
		routingCanvas.setSelectionHandler(new WorkflowCanvas.SelectionHandler() {
			@Override
			public void onStepSelected(WorkflowStep step) {
				if (routingCanvas.hasSelection()) {
					if (step.getName().equals("Start") || step.getName().equals("End")) {
						removeButton.disable();
					}
					else {
						removeButton.enable();
					}
				}
				
				handler.onStepSelected(new DrawStep(step));
			}

			@Override
			public void onConnectorSelected(Connector connector) {
				removeButton.enable();
				handler.onTransitionSelected(new DrawTransition(connector));
			}

			@Override
			public void onCanvasSelected() {
				removeButton.disable();
			}
		});
	}
	
	public static abstract class SelectionHandler {
		public abstract void onStepSelected(DrawStep drawStep);
		public abstract void onTransitionSelected(DrawTransition drawTransition);
	}
	
	@Override
	public void reset() {
		removeButton.disable();
	}

	@Override
	public void onModelNameChanged(String oldName, String newName) {
		
	}

	@Override
	public void setModified(boolean modified) {
		
	}

	@Override
	public void setModelEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}
	
	public DrawStep newStartStep(int x, int y) {
		return routingCanvas.newStartStep(x, y);		
	}
	
	public DrawStep newEndStep(int x, int y) {
		return routingCanvas.newEndStep(x, y);
	}
	
	public DrawStep newInnerStep(int x, int y) {
		return routingCanvas.newInnerStep(x, y);
	}
			
	public DrawTransition newTransition(DrawStep fromStep, DrawStep toStep) {		
		return routingCanvas.newTransition(fromStep, toStep);
	}
}
