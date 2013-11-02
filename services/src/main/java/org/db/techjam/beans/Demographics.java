package org.db.techjam.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class Demographics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197038771664883573L;
	private Individual individual;
	private PersonalInfo personalInfo;
	private ContactInfo contactInfo;
	private EmergencyContact emergencyContact;

	public Demographics(Individual individual) throws IOException {
		this.individual = individual;
		this.personalInfo = new PersonalInfo(individual);
		this.contactInfo = new ContactInfo(individual);
		this.emergencyContact = new EmergencyContact(individual);
	}

	public PersonalInfo getPersonalInfo() throws IOException {
		return personalInfo.getPersonalInfo();
	}

	public ContactInfo getContactInfo() throws IOException {
		return contactInfo.getContactInfo();
	}

	public LinkedList<EmergencyContact> getAllEmergencyContacts()
			throws IOException {
		return emergencyContact.getAllEmergencyContacts(individual);
	}

	public void putPersonalInfo(String firstName, String mi, String lastName,
			String suffix, String dob, String alias, String rel, String gender,
			String bloodType, String organDonor, String ms, String currentOp)
			throws IOException {
		PersonalInfo pI = new PersonalInfo(individual);
		pI.putPersonalInfo(firstName, mi, lastName, suffix, dob, alias, rel,
				gender, bloodType, organDonor, ms, currentOp);
	}

	public void putContactInfo(String addrLine1, String addrLine2, String city,
			String state, String country, String province, String postalCode,
			String workPhone, String homePhone, String pagerNumber,
			String faxNumber, String emailAddress,
			String preferredMethodOfContact) throws IOException {
		ContactInfo c = new ContactInfo(individual);
		c.putContactInfo(addrLine1, addrLine2, city, state, country, province,
				postalCode, workPhone, homePhone, pagerNumber, faxNumber,
				emailAddress, preferredMethodOfContact);
	}

	// public static void main(String args[]) throws IOException {
	// Individual i = new Individual("John Doe","9-10-1960");
	// Demographics dg = new Demographics(i);
	// EmergencyContact eC1 = new EmergencyContact(i);
	// EmergencyContact eC2 = new EmergencyContact(i);
	//
	// eC1.putEmergencyContact("EC1Fname", "EC2LName", "ADD1", "ADD2", "CITY",
	// "PROVIN", "REL", "STATE", "COUNTRY", "POSTCODE", "34523452345",
	// "12342345", "23452345", "e.c1@vets.org");
	// eC2.putEmergencyContact("EC2Fname", "EC2LName", "ADD1", "ADD2", "CITY",
	// "PROVIN", "REL", "STATE", "COUNTRY", "POSTCODE", "34523452345",
	// "12342345", "23452345", "e.c2@vets.org");
	// //
	// // dg.putPersonalInfo("John", "S", "Doe", "Sr.", "9-10-1960", "JD",
	// "REL", "M", "AB+", "YES", "MS", "OPS");
	// // dg.putContactInfo("2332 GOODWOOOD CIR", "NEXT TO WOODWAYBLUFF",
	// "CARY","NC","USA","PROVINCE","27513","200300222", "234232442",
	// "23455234555","342342343", "j.d@vets.org", "email");
	// // ContactInfo cI = dg.getContactInfo();
	// // PersonalInfo pI = dg.getPersonalInfo();
	// // System.out.println(cI);
	// // System.out.println(pI);
	// LinkedList<EmergencyContact> eList = dg.getAllEmergencyContacts();
	// for (EmergencyContact e:eList) {
	// System.out.println(e);
	// }
	// }

}