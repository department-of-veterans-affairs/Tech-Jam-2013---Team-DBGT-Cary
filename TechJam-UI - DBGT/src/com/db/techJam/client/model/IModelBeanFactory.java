package com.db.techJam.client.model;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface IModelBeanFactory extends AutoBeanFactory {
	
	AutoBean<IBlueButton> getBlueButton();
	
	AutoBean<IContactInfo> getContactInfo();
	
	AutoBean<IDemographics> getDemographics();
	
	AutoBean<IEmergencyContact> getEmergencyContact();
	
	AutoBean<IIndividual> getIndividual();
	
	AutoBean<IPersonalInfo> getPersonalInfo();

}
