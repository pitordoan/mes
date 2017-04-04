package com.cimpoint.mes.client.views.editors;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class Dialog extends Window {
	private Canvas parent;
	private HLayout buttonsLayout;
	private Label msgLabel;	
	private boolean showed = false;
	
	public Dialog(Canvas parent, String title, int width, int height) {
		this(parent, title, null, width, height);
	}
	
	public Dialog(Canvas parent, String title, String msg, int width, int height) {
		this.parent = parent;
		
		if (title != null) {
			this.setTitle(title);
		}
		else {
			this.setShowTitle(false);
		}
		
		this.setShowMaximizeButton(false);
		this.setShowMinimizeButton(false);
		this.setShowCloseButton(false);
		this.setIsModal(true);
		this.setShowModalMask(false);
		this.setWidth(width);
		this.setHeight(height);
		this.setCanDragResize(true);  
		this.setStyleName("cimtrack-Window");
		
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setBorder("1px solid LightGray");
		
		if (msg != null && !msg.isEmpty()) {
			msgLabel = new Label();
			msgLabel.setWidth100();
			msgLabel.setAutoHeight();
			msgLabel.setMargin(10);
			msgLabel.setWrap(true);
			msgLabel.setContents(msg);
			layout.addMember(msgLabel);
		}
		
		Canvas contentCanvas = getContentCanvas();
		contentCanvas.setWidth100();
		contentCanvas.setHeight("*");	
		layout.addMember(contentCanvas);
		
		LayoutSpacer layoutSpacer = new LayoutSpacer();
		layoutSpacer.setWidth100();
		layoutSpacer.setHeight(20);
		layout.addMember(layoutSpacer);

		buttonsLayout = new HLayout();
		buttonsLayout.setHeight(1);
		buttonsLayout.setWidth100();
		buttonsLayout.setAlign(Alignment.CENTER);
		buttonsLayout.setMembersMargin(20);

		layout.addMember(buttonsLayout);
		layout.addMember(layoutSpacer);

		this.addItem(layout);
	}
	
	protected abstract Canvas getContentCanvas();
	
	protected void addButton(IButton button) {
		buttonsLayout.addMember(button);
	}
	
	protected IButton getButton(String buttonID) {
		return (IButton) buttonsLayout.getMember(buttonID);
	}
	
	@Override
	public void show() {
		if (showed) return;
		
		if (this.parent != null) {
			int left = (this.parent.getWidth() > this.getWidth())? this.parent.getAbsoluteLeft() + 
					(this.parent.getWidth() - this.getWidth())/2 : this.parent.getAbsoluteLeft();
			int top = (this.parent.getHeight() > this.getHeight())? this.parent.getAbsoluteTop() + 
					(this.parent.getHeight() - this.getHeight())/2 : this.parent.getAbsoluteTop();
			this.moveTo(left, top);
		}
		
		showed = true;		
		super.show();			
	}
	
	@Override
	public void hide() {
		showed = false;
		super.hide();
	}
}
