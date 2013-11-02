package com.db.techJam.client;

import com.db.techJam.client.careplans.AllCarePlans;
import com.db.techJam.client.login.Login;
import com.google.gwt.core.client.EntryPoint;

public class TechJam implements EntryPoint {
	
	Login login;
	AllCarePlans tab = new AllCarePlans();

	@Override
	public void onModuleLoad() {
		login = new Login();
//		RootPanel.get().add(tab);
	}
}
