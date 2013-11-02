package org.db.techjam.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class BlueButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4688584368987681540L;

	private Individual individual;

	private Demographics demographics;

	private List<EmergencyContact> emergencyContacts;
	
	private ContactInfo contactInfo;
	
	private PersonalInfo personalInfo;


	public BlueButton(Individual individual) throws IOException {
		super();
		this.individual = individual;
		this.demographics = new Demographics(individual);
		this.setEmergencyContacts(demographics.getAllEmergencyContacts());
		this.contactInfo = demographics.getContactInfo();
		this.setPersonalInfo(demographics.getPersonalInfo());
	}

	public BlueButton() {
		// TODO Auto-generated constructor stub
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public Demographics getDemographics() {
		return demographics;
	}

	public void setDemographics(Demographics demographics) {
		this.demographics = demographics;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<EmergencyContact> getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

	
	
}
