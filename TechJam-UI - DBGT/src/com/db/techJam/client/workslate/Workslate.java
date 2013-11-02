package com.db.techJam.client.workslate;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Workslate extends Composite{

	WorkslateUiBinder uiBinder = GWT.create(WorkslateUiBinder.class);
	interface WorkslateUiBinder extends UiBinder<Widget, Workslate>{}
	
	@UiField
	static
	HTMLPanel brandBar;
	@UiField
	static
	HTMLPanel leftNav;
	@UiField
	HTMLPanel centerPanel;
	@UiField
	DockLayoutPanel workslate;
	
	boolean initialized=false;
	
	public Workslate() {
		if(!initialized){
			initWidget(uiBinder.createAndBindUi(this));
			initialized=true;
		}
	}
	
	public void add(Widget w){
		centerPanel.clear();
		centerPanel.add(w);
	}

	public static HTMLPanel getBrandBar() {
		return brandBar;
	}

	public static HTMLPanel getLeftNav() {
		return leftNav;
	}
}
