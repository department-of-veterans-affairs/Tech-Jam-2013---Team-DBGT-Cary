package com.db.techJam.client.medications;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MedicationsTab extends Composite {
	
	MedicationsTabUiBinder uiBinder = GWT.create(MedicationsTabUiBinder.class);
	interface MedicationsTabUiBinder extends UiBinder<Widget,MedicationsTab>{}
	
	public MedicationsTab() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

}
