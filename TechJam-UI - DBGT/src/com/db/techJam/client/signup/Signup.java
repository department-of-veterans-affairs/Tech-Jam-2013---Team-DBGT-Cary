package com.db.techJam.client.signup;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Signup extends Composite {
	
	SignupUiBinder uiBinder = GWT.create(SignupUiBinder.class);
	interface SignupUiBinder extends UiBinder<Widget, Signup>{}
	
	public Signup() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
