package com.db.techJam.client.widgets;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CurrentMedications extends Composite{
	
	CurrentMedicationsUiBinder uiBinder = GWT.create(CurrentMedicationsUiBinder.class);
	interface CurrentMedicationsUiBinder extends UiBinder<Widget, CurrentMedications>{}
	
	public CurrentMedications() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
