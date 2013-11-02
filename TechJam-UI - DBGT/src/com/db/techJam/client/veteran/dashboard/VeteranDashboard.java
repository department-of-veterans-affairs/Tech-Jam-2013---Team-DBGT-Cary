package com.db.techJam.client.veteran.dashboard;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class VeteranDashboard extends Composite {

	VeteranDashboardUiBinder uiBinder = GWT.create(VeteranDashboardUiBinder.class);
	interface VeteranDashboardUiBinder extends UiBinder<Widget, VeteranDashboard>{}
	
	public VeteranDashboard() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
