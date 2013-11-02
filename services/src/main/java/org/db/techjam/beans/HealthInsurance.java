package org.db.techjam.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

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

class HealthInsurance implements Serializable{

	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] SOURCE = Bytes.toBytes("source");
	private static final byte[] COMPANYNAME = Bytes.toBytes("companyName");
	private static final byte[] PRIINSUPROV = Bytes.toBytes("primaryInsProv");
	private static final byte[] IDNUMBER = Bytes.toBytes("idNumber");
	private static final byte[] INSURED = Bytes.toBytes("insured");
	private static final byte[] STARTDATE = Bytes.toBytes("startDate");
	private static final byte[] STOPDATE = Bytes.toBytes("stopDate");
	private static final byte[] PREAPPRPHONENUM = Bytes
			.toBytes("preApprovePhoneNumber");
	private static final byte[] COMPANYPHONENUMBER = Bytes
			.toBytes("companyPhoneNumber");
	private static final byte[] COMMENTS = Bytes.toBytes("comments");
	private static final byte[] GROUPNUMBER = Bytes.toBytes("groupNumber");

	private static final byte[] CF = Bytes.toBytes("DETAILS");
	private static final byte[] TBNAME = Bytes.toBytes("HEALTHINSURANCE");

	private String source;
	private String companyName;
	private String primaryInsProv;
	private String idNumber;
	private String insured;
	private String startDate;
	private String stopDate;
	private String preApprovePhoneNumber;
	private String companyPhoneNumber;
	private String comments;
	private String groupNumber;

	private Individual individual;

	public HealthInsurance(Individual individual) {

		this.individual = individual;

	}

	public void putHealthCareProvider(String source, String companyName,
			String primaryInsProv, String idNumber, String insured,
			String startDate, String stopDate, String preApprovePhoneNumber,
			String companyPhoneNumber, String comments, String groupNumber)
			throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);

		Put p = mkPut(source, idNumber);

		p.add(CF, SOURCE, Bytes.toBytes(source));
		p.add(CF, COMPANYNAME, Bytes.toBytes(companyName));
		p.add(CF, PRIINSUPROV, Bytes.toBytes(primaryInsProv));
		p.add(CF, IDNUMBER, Bytes.toBytes(idNumber));
		p.add(CF, INSURED, Bytes.toBytes(insured));
		p.add(CF, STARTDATE, Bytes.toBytes(startDate));
		p.add(CF, STOPDATE, Bytes.toBytes(stopDate));
		p.add(CF, PREAPPRPHONENUM, Bytes.toBytes(preApprovePhoneNumber));
		p.add(CF, COMPANYPHONENUMBER, Bytes.toBytes(companyPhoneNumber));
		p.add(CF, INDIVIDUAL, Bytes.toBytes(individual.getFullName()));
		p.add(CF, GROUPNUMBER, Bytes.toBytes(groupNumber));

		individuals.put(p);
		individuals.close();

	}

	private Put mkPut(String source, String idNumber) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName() + source
				+ idNumber));

		return p;
	}

	public Individual getIndividual() {
		return individual;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public LinkedList<HealthInsurance> getHealthCareProviders(Individual i)
			throws IOException {

		Scan s = new Scan();
		System.out.println(i.getFullName());
		SingleColumnValueFilter filter = new SingleColumnValueFilter(CF,
				INDIVIDUAL, CompareOp.EQUAL, Bytes.toBytes(i.getFullName()));
		s.setFilter(filter);
		s.addFamily(CF);
		LinkedList<HealthInsurance> healthInsurances = new LinkedList<HealthInsurance>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME)
				.getScanner(s);
		for (Result r : results) {
			HealthInsurance hI = new HealthInsurance(this.individual);
			hI.setSource(Bytes.toString(r.getValue(CF, SOURCE)));
			hI.setStartDate(Bytes.toString(r.getValue(CF, STARTDATE)));
			hI.setStopDate(Bytes.toString(r.getValue(CF, STOPDATE)));
			hI.setComments(Bytes.toString(r.getValue(CF, COMMENTS)));
			hI.setInsured(Bytes.toString(r.getValue(CF, INSURED)));
			hI.setCompanyName(Bytes.toString(r.getValue(CF, COMPANYNAME)));
			hI.setIndividual(i);
			hI.setCompanyPhoneNumber(Bytes.toString(r.getValue(CF,
					COMPANYPHONENUMBER)));
			hI.setPreApprovePhoneNumber(Bytes.toString(r.getValue(CF,
					PREAPPRPHONENUM)));
			hI.setGroupNumber(Bytes.toString(r.getValue(CF, GROUPNUMBER)));

			healthInsurances.add(hI);
		}

		return healthInsurances;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrimaryInsProv() {
		return primaryInsProv;
	}

	public void setPrimaryInsProv(String primaryInsProv) {
		this.primaryInsProv = primaryInsProv;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getInsured() {
		return insured;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public String getPreApprovePhoneNumber() {
		return preApprovePhoneNumber;
	}

	public void setPreApprovePhoneNumber(String preApprovePhoneNumber) {
		this.preApprovePhoneNumber = preApprovePhoneNumber;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}