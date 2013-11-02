package com.db.techJam.client.leftnav;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LeftNav extends Composite{

	LeftNavUiBinder uiBinder = GWT.create(LeftNavUiBinder.class);
	interface LeftNavUiBinder extends UiBinder<Widget, LeftNav>{}
	
	public LeftNav() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
