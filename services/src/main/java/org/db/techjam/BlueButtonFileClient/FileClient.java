package org.db.techjam.BlueButtonFileClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.db.techjam.beans.ContactInfo;
import org.db.techjam.beans.Individual;
import org.db.techjam.beans.PersonalInfo;

public class FileClient {
	
	public static String runParser(String mode) throws IOException {
		String scriptPath = "/home/cloudera/workspace/techjam.services/src/main/resources/parseJSON.pl";
		String filePath = "/home/cloudera/workspace/techjam.services/src/main/resources/sample.txt";
		String[] cmd = new String[4];
		cmd[0] = "/usr/bin/perl";
		cmd[1] = scriptPath;
		cmd[2] = filePath;
		cmd[3] = mode;
		
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);
		
		
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		StringBuffer sb = new StringBuffer();
		while((line = bfr.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
		return sb.toString();
	}

	public static Individual getIndividual() throws IOException, JSONException {
		FileClient fC = new FileClient();
		String s = fC.runParser("individual");
		JSONObject jObject  = new JSONObject(s);
		String fullName = jObject.getString("Name");
		String dob = jObject.getString("Date of Birth");
		return new Individual(fullName, dob);
	}
	
	public static ContactInfo getContactInfo() throws IOException, JSONException {
		
		FileClient fC = new FileClient();
		String s = fC.runParser("contact");
		JSONObject jObject  = new JSONObject(s);
		String addrLine1 = jObject.getString("Address Line 1");
		String addrLine2 = jObject.getString("Address Line 2");
		String city = jObject.getString("City");
		String state = jObject.getString("State");
		String country = jObject.getString("Country");
		String province = jObject.getString("Province");
		String postalCode = jObject.getString("Zip/Post Code");
		String homePhone = jObject.getString("Home Phone Number");
		String pagerNumber = jObject.getString("Pager Number");
		String faxNumber = jObject.getString("Fax Number");
		String workPhone = jObject.getString("Work Phone Number");
		String email = jObject.getString("Email");
		String preferredMethodOfContact = jObject.getString("Preferred Method Of Contact");
		ContactInfo cI = new ContactInfo(new Individual());
		cI.putContactInfo(addrLine1, addrLine2, city, state, country, province, postalCode, workPhone, homePhone, pagerNumber, faxNumber, email,preferredMethodOfContact);
		return cI;
		
	}
	
	public static PersonalInfo getPersonalInfo() throws IOException, JSONException {
		
		String s = runParser("PersonalInfo");
		JSONObject jObject  = new JSONObject(s);
		String firstName = jObject.getString("FirstName");
		String lastName = jObject.getString("LastName");
		String mi = jObject.getString("MiddleInitial");
		String gender = jObject.getString("Gender");
		String alias = jObject.getString("Alias");
		String rel = jObject.getString("Relationship");
		String suffix = jObject.getString("Suffix");
		String bloodType = jObject.getString("BloodType");
		String organDoner = jObject.getString("OrganDonor");
		String dob = jObject.getString("DateOfBirth");
		String ms = jObject.getString("MaritalStatus");
		String currentOp = jObject.getString("CurrentOccupation");
		PersonalInfo pI = new PersonalInfo(new Individual());
		//pI.putPersonalInfo(firstName, mi, lastName, suffix, dob, alias, rel, gender, bloodType, organDoner, ms, currentOp);
		
		return pI;
	}
	
	public static void main(String args[]) throws IOException, JSONException {
		
		System.out.println(FileClient.getPersonalInfo());
		
	}

	
}
