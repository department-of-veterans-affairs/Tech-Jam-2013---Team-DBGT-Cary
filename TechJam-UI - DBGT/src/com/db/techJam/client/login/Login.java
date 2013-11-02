package com.db.techJam.client.login;

import com.db.techJam.client.signup.Signup;
import com.db.techJam.client.veteran.dashboard.VeteranDashboard;
import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Login {

	LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
	interface LoginUiBinder extends UiBinder<Widget, Login>{}
	
	Signup signUp = new Signup();
	VeteranDashboard dashboard = new VeteranDashboard();
	
	@UiField
	DialogBox dialog;
	
	@UiField
	Button signInBtn,signUpBtn;	
	
	public Login() {
		uiBinder.createAndBindUi(this);
		dialog.center();
		dialog.show();
	}
	
	@UiHandler("signUpBtn")
	void handleSignUpClicked(ClickEvent event){
		dialog.hide();
		RootPanel.get().add(signUp);
	}
	
	@UiHandler("signInBtn")
	void handleSignInClicked(ClickEvent event){
		dialog.hide();
		RootPanel.get().clear();
		RootPanel.get().add(dashboard);
//		RootPanel.get().clear();
//		workSlate.getLeftNav().add(leftNav);
//		RootPanel.get().add(med);
		
	}
}
