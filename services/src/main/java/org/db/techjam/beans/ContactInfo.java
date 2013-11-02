package org.db.techjam.beans;

import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.db.techjam.hbase.connector.HTablePoolFactory;

public class ContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7787938138985404279L;
	private static final byte[] ADDR_LINE1 = Bytes.toBytes("addressLine1");
	private static final byte[] ADDR_LINE2 = Bytes.toBytes("addressLine2");
	private static final byte[] CITY = Bytes.toBytes("city");
	private static final byte[] PROVINCE = Bytes.toBytes("province");
	private static final byte[] PC = Bytes.toBytes("postalCode");
	private static final byte[] WORK_PHONE = Bytes.toBytes("workPhone");
	private static final byte[] HOME_PHONE = Bytes.toBytes("homePhone");
	private static final byte[] PAGER_NUMBER = Bytes.toBytes("pagerNumber");
	private static final byte[] FAX_NUMBER = Bytes.toBytes("faxNumber");
	private static final byte[] EMAIL_ADDR = Bytes.toBytes("emailAddress");
	private static final byte[] PMC = Bytes.toBytes("preferredMethodOfContact");
	private static final byte[] STATE = Bytes.toBytes("state");
	private static final byte[] COUNTRY = Bytes.toBytes("country");

	private static final byte[] CF = Bytes.toBytes("CONTACT_INFO");
	private static final byte[] TBNAME = Bytes.toBytes("INDIVIDUALS");

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String province;
	private String state;

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

	private String country;
	private String postalCode;
	private String workPhone;
	private String homePhone;
	private String pagerNumber;
	private String faxNumber;
	private String emailAddress;
	private String preferredMethodOfContact;

	private Individual individual;

	public ContactInfo getContactInfo() throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);
		Get g = mkGet(individual);
		g.addFamily(CF);
		Result r = individuals.get(g);

		ContactInfo cI = new ContactInfo(individual);
		cI.setAddressLine1(Bytes.toString(r.getValue(CF, ADDR_LINE1)));
		cI.setAddressLine2(Bytes.toString(r.getValue(CF, ADDR_LINE2)));
		cI.setCity(Bytes.toString(r.getValue(CF, CITY)));
		cI.setProvince(Bytes.toString(r.getValue(CF, PROVINCE)));
		cI.setPostalCode(Bytes.toString(r.getValue(CF, PC)));
		cI.setWorkPhone(Bytes.toString(r.getValue(CF, WORK_PHONE)));
		cI.setHomePhone(Bytes.toString(r.getValue(CF, HOME_PHONE)));
		cI.setPagerNumber(Bytes.toString(r.getValue(CF, PAGER_NUMBER)));
		cI.setFaxNumber(Bytes.toString(r.getValue(CF, FAX_NUMBER)));
		cI.setEmailAddress(Bytes.toString(r.getValue(CF, EMAIL_ADDR)));
		cI.setPreferredMethodOfContact(Bytes.toString(r.getValue(CF, PMC)));
		cI.setState(Bytes.toString(r.getValue(CF, STATE)));
		cI.setCountry(Bytes.toString(r.getValue(CF, COUNTRY)));

		return cI;

	}

	public void putContactInfo(String addrLine1, String addrLine2, String city,
			String state, String country, String province, String postalCode,
			String workPhone, String homePhone, String pagerNumber,
			String faxNumber, String emailAddress,
			String preferredMethodOfContact) throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);

		Put p = mkPut(individual);

		p.add(CF, ADDR_LINE1, Bytes.toBytes(addrLine1));
		p.add(CF, ADDR_LINE2, Bytes.toBytes(addrLine2));
		p.add(CF, CITY, Bytes.toBytes(city));
		p.add(CF, PROVINCE, Bytes.toBytes(province));
		p.add(CF, PC, Bytes.toBytes(postalCode));
		p.add(CF, WORK_PHONE, Bytes.toBytes(workPhone));
		p.add(CF, HOME_PHONE, Bytes.toBytes(homePhone));
		p.add(CF, PAGER_NUMBER, Bytes.toBytes(pagerNumber));
		p.add(CF, FAX_NUMBER, Bytes.toBytes(faxNumber));
		p.add(CF, EMAIL_ADDR, Bytes.toBytes(emailAddress));
		p.add(CF, PMC, Bytes.toBytes(preferredMethodOfContact));
		p.add(CF, STATE, Bytes.toBytes(state));
		p.add(CF, COUNTRY, Bytes.toBytes(country));

		individuals.put(p);
		individuals.close();

	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	@Override
	public String toString() {
		return "ContactInfo [addressLine1=" + addressLine1 + ", addressLine2="
				+ addressLine2 + ", city=" + city + ", province=" + province
				+ ", state=" + state + ", country=" + country + ", postalCode="
				+ postalCode + ", workPhone=" + workPhone + ", homePhone="
				+ homePhone + ", pagerNumber=" + pagerNumber + ", faxNumber="
				+ faxNumber + ", emailAddress=" + emailAddress
				+ ", preferredMethodOfContact=" + preferredMethodOfContact
				+ ", individual=" + individual + "]";
	}

	public ContactInfo(Individual individual) throws IOException {

		this.individual = individual;

	}

	private Get mkGet(Individual individual) {
		Get g = new Get(Bytes.toBytes(individual.getFullName()
				+ individual.getDateOfBirth()));
		return g;
	}

	public Put mkPut(Individual individual) {
		Put p = new Put(Bytes.toBytes(individual.getFullName()
				+ individual.getDateOfBirth()));

		return p;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getPagerNumber() {
		return pagerNumber;
	}

	public void setPagerNumber(String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPreferredMethodOfContact() {
		return preferredMethodOfContact;
	}

	public void setPreferredMethodOfContact(String preferredMethodOfContact) {
		this.preferredMethodOfContact = preferredMethodOfContact;
	}

}
