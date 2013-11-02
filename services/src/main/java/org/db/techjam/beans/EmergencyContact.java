package org.db.techjam.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.db.techjam.hbase.connector.HTablePoolFactory;

public class EmergencyContact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4580902490665581857L;
	private String firstName;
	private String lastName;
	private String relationShip;
	private String homePhoneNumber;
	private String workPhoneNumber;
	private String cellPhoneNumber;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String province;
	private String postCode;
	private String emailAddress;
	private Individual individual;

	private static final byte[] FIRST_NAME = Bytes.toBytes("firstName");
	private static final byte[] LAST_NAME = Bytes.toBytes("lastName");
	private static final byte[] REL = Bytes.toBytes("relationShip");
	private static final byte[] ADDR_LINE1 = Bytes.toBytes("addressLine1");
	private static final byte[] ADDR_LINE2 = Bytes.toBytes("addressLine2");
	private static final byte[] CITY = Bytes.toBytes("city");
	private static final byte[] STATE = Bytes.toBytes("state");
	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] COUNTRY = Bytes.toBytes("country");
	private static final byte[] PROVINCE = Bytes.toBytes("province");
	private static final byte[] PC = Bytes.toBytes("postCode");
	private static final byte[] WORK_PHONE = Bytes.toBytes("workPhoneNumber");
	private static final byte[] HOME_PHONE = Bytes.toBytes("homePhoneNumber");
	private static final byte[] CELL_PHONE = Bytes.toBytes("cellPhoneNumber");
	private static final byte[] EMAIL_ADDR = Bytes.toBytes("emailAddress");
	private static final byte[] CF = Bytes.toBytes("CONTACT_INFO");
	private static final byte[] TBNAME = Bytes.toBytes("EMERGENCYCONTACTS");

	public Put mkPut(String firstName2, String lastName2, String rel2) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName()
				+ firstName2 + lastName2 + rel2));

		return p;
	}

	public Get mkGet(String firstName2, String lastName2, String rel2) {
		Get g = new Get(Bytes.toBytes(this.individual.getFullName()
				+ firstName2 + lastName2 + rel2));

		return g;
	}

	public Individual getIndividual() {
		return individual;
	}

	public EmergencyContact(Individual individual) {

		this.individual = individual;

	}

	public void putEmergencyContact(String firstName, String lastName,
			String address1, String address2, String city, String province,
			String rel, String state, String country, String postCode,
			String workPhoneNumber, String homePhoneNumber,
			String cellPhoneNumber, String emailAddress) throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface emerContacts = pool.getTable(TBNAME);

		Put p = mkPut(firstName, lastName, rel);
		p.add(CF, INDIVIDUAL, Bytes.toBytes(individual.getFullName()));
		p.add(CF, FIRST_NAME, Bytes.toBytes(firstName));
		p.add(CF, LAST_NAME, Bytes.toBytes(lastName));
		p.add(CF, ADDR_LINE1, Bytes.toBytes(address1));
		p.add(CF, ADDR_LINE2, Bytes.toBytes(address2));
		p.add(CF, CITY, Bytes.toBytes(city));
		p.add(CF, REL, Bytes.toBytes(rel));
		p.add(CF, PROVINCE, Bytes.toBytes(province));
		p.add(CF, STATE, Bytes.toBytes(state));
		p.add(CF, COUNTRY, Bytes.toBytes(country));
		p.add(CF, PC, Bytes.toBytes(postCode));
		p.add(CF, WORK_PHONE, Bytes.toBytes(workPhoneNumber));
		p.add(CF, HOME_PHONE, Bytes.toBytes(homePhoneNumber));
		p.add(CF, CELL_PHONE, Bytes.toBytes(cellPhoneNumber));
		p.add(CF, EMAIL_ADDR, Bytes.toBytes(emailAddress));

		emerContacts.put(p);
		emerContacts.close();

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public LinkedList<EmergencyContact> getAllEmergencyContacts(Individual i)
			throws IOException {
		Scan s = new Scan();
		System.out.println(i.getFullName());
		SingleColumnValueFilter filter = new SingleColumnValueFilter(CF,
				INDIVIDUAL, CompareOp.EQUAL, Bytes.toBytes(i.getFullName()));
		s.setFilter(filter);
		s.addFamily(CF);
		LinkedList<EmergencyContact> emergencyContacts = new LinkedList<EmergencyContact>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME)
				.getScanner(s);
		for (Result r : results) {
			EmergencyContact eC = new EmergencyContact(this.individual);
			eC.setFirstName(Bytes.toString(r.getValue(CF, FIRST_NAME)));
			eC.setLastName(Bytes.toString(r.getValue(CF, LAST_NAME)));
			eC.setAddress1(Bytes.toString(r.getValue(CF, ADDR_LINE1)));
			eC.setAddress2(Bytes.toString(r.getValue(CF, ADDR_LINE2)));
			eC.setCity(Bytes.toString(r.getValue(CF, CITY)));
			eC.setProvince(Bytes.toString(r.getValue(CF, PROVINCE)));
			eC.setState(Bytes.toString(r.getValue(CF, STATE)));
			eC.setCountry(Bytes.toString(r.getValue(CF, COUNTRY)));
			eC.setPostCode(Bytes.toString(r.getValue(CF, PC)));
			eC.setRelationShip(Bytes.toString(r.getValue(CF, REL)));
			eC.setWorkPhoneNumber(Bytes.toString(r.getValue(CF, WORK_PHONE)));
			eC.setHomePhoneNumber(Bytes.toString(r.getValue(CF, HOME_PHONE)));
			eC.setCellPhoneNumber(Bytes.toString(r.getValue(CF, CELL_PHONE)));
			eC.setEmailAddress(Bytes.toString(r.getValue(CF, EMAIL_ADDR)));
			emergencyContacts.add(eC);
		}

		return emergencyContacts;
	}

	@Override
	public String toString() {
		return "EmergencyContact [firstName=" + firstName + ", lastName="
				+ lastName + ", relationShip=" + relationShip
				+ ", homePhoneNumber=" + homePhoneNumber + ", workPhoneNumber="
				+ workPhoneNumber + ", cellPhoneNumber=" + cellPhoneNumber
				+ ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", country="
				+ country + ", province=" + province + ", postCode=" + postCode
				+ ", emailAddress=" + emailAddress + ", individual="
				+ individual + "]";
	}
}
