package com.db.techJam.client.widgets;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UpcomingAppointments extends Composite {

	UpcomingUiBinder uiBinder = GWT.create(UpcomingUiBinder.class);
	interface UpcomingUiBinder extends UiBinder<Widget, UpcomingAppointments>{}
	
	public UpcomingAppointments() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
