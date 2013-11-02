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

public class PersonalInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5618060533026584091L;
	private static final byte[] FIRST_NAME = Bytes.toBytes("firstName");
	private static final byte[] MI = Bytes.toBytes("middleInitial");
	private static final byte[] LAST_NAME = Bytes.toBytes("lastName");
	private static final byte[] SUFFIX = Bytes.toBytes("suffix");
	private static final byte[] DOB = Bytes.toBytes("dateOfBirth");
	private static final byte[] ALIAS = Bytes.toBytes("alias");
	private static final byte[] REL = Bytes.toBytes("relationShipToVA");
	private static final byte[] GENDER = Bytes.toBytes("gender");
	private static final byte[] BLOOD_TYPE = Bytes.toBytes("bloodType");
	private static final byte[] ORGAN_DONOR = Bytes.toBytes("organDonor");
	private static final byte[] MS = Bytes.toBytes("martialStatus");
	private static final byte[] CURRENT_OP = Bytes.toBytes("currentOperation");
	private static final byte[] CF = Bytes.toBytes("CONTACT_INFO");
	private static final byte[] TBNAME = Bytes.toBytes("INDIVIDUALS");

	private String firstName;

	private String middleInitial;

	private String lastName;

	private String suffix;

	private String dateOfBirth;

	private String alias;

	private String relationShipToVA;

	private String gender;

	private String bloodType;

	private String organDonor;

	private String martialStatus;

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	private String currentOperation;

	private Individual individual;

	public PersonalInfo getPersonalInfo() throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();

		HTableInterface individuals = pool.getTable(TBNAME);
		Get g = mkGet(individual);
		g.addFamily(CF);
		Result r = individuals.get(g);

		PersonalInfo pI = new PersonalInfo(individual);
		pI.setFirstName(Bytes.toString(r.getValue(CF, FIRST_NAME)));
		pI.setMiddleInitial(Bytes.toString(r.getValue(CF, MI)));
		pI.setLastName(Bytes.toString(r.getValue(CF, LAST_NAME)));
		pI.setSuffix(Bytes.toString(r.getValue(CF, SUFFIX)));
		pI.setDateOfBirth(Bytes.toString(r.getValue(CF, DOB)));
		pI.setAlias(Bytes.toString(r.getValue(CF, ALIAS)));
		pI.setFirstName(Bytes.toString(r.getValue(CF, FIRST_NAME)));
		pI.setRelationShipToVA(Bytes.toString(r.getValue(CF, REL)));
		pI.setGender(Bytes.toString(r.getValue(CF, GENDER)));
		pI.setBloodType(Bytes.toString(r.getValue(CF, BLOOD_TYPE)));
		pI.setOrganDonor(Bytes.toString(r.getValue(CF, ORGAN_DONOR)));
		pI.setMartialStatus(Bytes.toString(r.getValue(CF, MS)));
		pI.setCurrentOperation(Bytes.toString(r.getValue(CF, CURRENT_OP)));

		return pI;

	}

	public void putPersonalInfo(String firstName, String mi, String lastName,
			String suffix, String dob, String alias, String rel, String gender,
			String bloodType, String organDonor, String ms, String currentOp)
			throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);

		Put p = mkPut(individual);

		p.add(CF, FIRST_NAME, Bytes.toBytes(firstName));
		p.add(CF, MI, Bytes.toBytes(mi));
		p.add(CF, LAST_NAME, Bytes.toBytes(lastName));
		p.add(CF, SUFFIX, Bytes.toBytes(suffix));
		p.add(CF, DOB, Bytes.toBytes(dob));
		p.add(CF, ALIAS, Bytes.toBytes(alias));
		p.add(CF, REL, Bytes.toBytes(rel));
		p.add(CF, GENDER, Bytes.toBytes(gender));
		p.add(CF, BLOOD_TYPE, Bytes.toBytes(bloodType));
		p.add(CF, ORGAN_DONOR, Bytes.toBytes(organDonor));
		p.add(CF, MS, Bytes.toBytes(ms));
		p.add(CF, CURRENT_OP, Bytes.toBytes(currentOp));

		individuals.put(p);
		individuals.close();

	}

	public PersonalInfo(Individual individual) throws IOException {

		this.individual = individual;

	}

	public Put mkPut(Individual individual) {
		Put p = new Put(Bytes.toBytes(individual.getFullName()
				+ individual.getDateOfBirth()));

		return p;
	}

	public Get mkGet(Individual individual) {
		Get g = new Get(Bytes.toBytes(individual.getFullName()
				+ individual.getDateOfBirth()));

		return g;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getRelationShipToVA() {
		return relationShipToVA;
	}

	public void setRelationShipToVA(String relationShipToVA) {
		this.relationShipToVA = relationShipToVA;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getOrganDonor() {
		return organDonor;
	}

	public void setOrganDonor(String organDonor) {
		this.organDonor = organDonor;
	}

	public String getMartialStatus() {
		return martialStatus;
	}

	@Override
	public String toString() {
		return "PersonalInfo [firstName=" + firstName + ", middleInitial="
				+ middleInitial + ", lastName=" + lastName + ", suffix="
				+ suffix + ", dateOfBirth=" + dateOfBirth + ", alias=" + alias
				+ ", relationShipToVA=" + relationShipToVA + ", gender="
				+ gender + ", bloodType=" + bloodType + ", organDonor="
				+ organDonor + ", martialStatus=" + martialStatus
				+ ", currentOperation=" + currentOperation + ", individual="
				+ individual + "]";
	}

	public void setMartialStatus(String martialStatus) {
		this.martialStatus = martialStatus;
	}

	public String getCurrentOperation() {
		return currentOperation;
	}

	public void setCurrentOperation(String currentOperation) {
		this.currentOperation = currentOperation;
	}

}
