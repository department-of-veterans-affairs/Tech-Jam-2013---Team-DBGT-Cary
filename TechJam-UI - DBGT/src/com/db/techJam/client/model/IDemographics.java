package com.db.techJam.client.model;

public interface IDemographics {
	
	IIndividual getIndividual();
	void setIndividual(IIndividual value);
	
	IPersonalInfo getPersonalInfo();
	void setPersonalInfo(IPersonalInfo value);
	
	IContactInfo getContactInfo();
	void setContactInfo(IContactInfo value);
	
	IEmergencyContact getEmergencyContact();
	void setEmergencyContact(IEmergencyContact value);
	
	

}
