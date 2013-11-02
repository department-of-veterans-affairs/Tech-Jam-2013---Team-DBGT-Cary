package org.db.techjam.beans;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.db.techjam.hbase.connector.HTablePoolFactory;

class VAMedicationHistory {

	
	
	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] SOURCE = Bytes.toBytes("source");
	private static final byte[] LATESTUPDATED = Bytes.toBytes("latestUpdated");
	private static final byte[] PRESCRIPTIONNUM = Bytes.toBytes("prescriptionNum");
	private static final byte[] MEDICATION = Bytes.toBytes("medication");
	private static final byte[] INSTRUCTIONS = Bytes.toBytes("instructions");
	private static final byte[] STATUS = Bytes.toBytes("status");
	private static final byte[] REFILLSREMAINING = Bytes.toBytes("refillsremaining");
	private static final byte[] LASTFILLEDON = Bytes.toBytes("lastfilledon");
	private static final byte[] INITIALLYORDERED = Bytes.toBytes("initiallyOrdered");
	private static final byte[] QUANTITY = Bytes.toBytes("quantity");
	private static final byte[] DAYSSUPPLY = Bytes.toBytes("daysSupply");
	private static final byte[] PHARMACY = Bytes.toBytes("pharmacy");

	private static final byte[] CF = Bytes.toBytes("DETAILS");
	private static final byte[] TBNAME = Bytes.toBytes("VAMEDICATIONHISTORY");

	private String source;
	private String latestUpdated;
	private String prescriptionNum;
	private String medication;
	private String instructions;
	private String status;
	private String refillsremaining;
	private String lastfilledon;
	private String initiallyOrdered;
	private String quantity;
	private String daysSupply;
	private String pharmacy;

	private Individual individual;

	public VAMedicationHistory(Individual individual) {

		this.individual = individual;

	}

	public void putVAMedicationHistory(String source, String latestUpdated,
			String prescriptionNum, String medication,
			String instructions, String status, String refillsRemaining, String lastFilledon, String initiallyOrdered, String quantity
			,String daysSupply,String pharmacy)
			throws IOException {

		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);

		Put p = mkPut(source, prescriptionNum, pharmacy);

		p.add(CF, SOURCE, Bytes.toBytes(source));
		p.add(CF, LATESTUPDATED, Bytes.toBytes(latestUpdated));
		p.add(CF, PRESCRIPTIONNUM, Bytes.toBytes(prescriptionNum));
		p.add(CF, MEDICATION, Bytes.toBytes(medication));
		p.add(CF, INSTRUCTIONS, Bytes.toBytes(instructions));
		p.add(CF, STATUS, Bytes.toBytes(status));
		p.add(CF, REFILLSREMAINING, Bytes.toBytes(refillsRemaining));
		p.add(CF, LASTFILLEDON, Bytes.toBytes(lastFilledon));
		p.add(CF, INITIALLYORDERED, Bytes.toBytes(initiallyOrdered));
		p.add(CF, QUANTITY, Bytes.toBytes(quantity));
		p.add(CF, DAYSSUPPLY, Bytes.toBytes(daysSupply));
		p.add(CF, PHARMACY, Bytes.toBytes(pharmacy));
		p.add(CF, INDIVIDUAL, Bytes.toBytes(individual.getFullName()));

		individuals.put(p);
		individuals.close();

	}

	private Put mkPut(String source, String prescriptionNum, String pharmacy) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName() + source
				+ prescriptionNum + pharmacy));

		return p;
	}

	public Individual getIndividual() {
		return individual;
	}


	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public LinkedList<VAMedicationHistory> getHealthCareProviders(Individual i)
			throws IOException {

		Scan s = new Scan();
		System.out.println(i.getFullName());
		SingleColumnValueFilter filter = new SingleColumnValueFilter(CF,
				INDIVIDUAL, CompareOp.EQUAL, Bytes.toBytes(i.getFullName()));
		s.setFilter(filter);
		s.addFamily(CF);
		LinkedList<VAMedicationHistory> vaMedicationHistory = new LinkedList<VAMedicationHistory>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME)
				.getScanner(s);
		for (Result r : results) {
			VAMedicationHistory vR = new VAMedicationHistory(this.individual);
			vR.setSource(Bytes.toString(r.getValue(CF, SOURCE)));
			vR.setPrescriptionNum(Bytes.toString(r.getValue(CF, PRESCRIPTIONNUM)));
			vR.setMedication(Bytes.toString(r.getValue(CF, MEDICATION)));
			vR.setStatus(Bytes.toString(r.getValue(CF, STATUS)));
			vR.setRefillsremaining(Bytes.toString(r.getValue(CF, REFILLSREMAINING)));
			vR.setLatestUpdated(Bytes.toString(r.getValue(CF, LATESTUPDATED)));
			vR.setDaysSupply(Bytes.toString(r.getValue(CF, DAYSSUPPLY)));
			vR.setInitiallyOrdered(Bytes.toString(r.getValue(CF, INITIALLYORDERED)));
			vR.setQuantity(Bytes.toString(r.getValue(CF, QUANTITY)));
			vR.setPharmacy(Bytes.toString(r.getValue(CF, PHARMACY)));
			vR.setIndividual(i);
			vR.setInstructions(Bytes.toString(r.getValue(CF, INSTRUCTIONS)));
			vR.setLastfilledon(Bytes.toString(r.getValue(CF, LASTFILLEDON)));
			vaMedicationHistory.add(vR);
		}

		return vaMedicationHistory;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLatestUpdated() {
		return latestUpdated;
	}

	public void setLatestUpdated(String latestUpdated) {
		this.latestUpdated = latestUpdated;
	}

	public String getPrescriptionNum() {
		return prescriptionNum;
	}

	public void setPrescriptionNum(String prescriptionNum) {
		this.prescriptionNum = prescriptionNum;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefillsremaining() {
		return refillsremaining;
	}

	public void setRefillsremaining(String refillsremaining) {
		this.refillsremaining = refillsremaining;
	}

	public String getLastfilledon() {
		return lastfilledon;
	}

	public void setLastfilledon(String lastfilledon) {
		this.lastfilledon = lastfilledon;
	}

	public String getInitiallyOrdered() {
		return initiallyOrdered;
	}

	public void setInitiallyOrdered(String initiallyOrdered) {
		this.initiallyOrdered = initiallyOrdered;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDaysSupply() {
		return daysSupply;
	}

	public void setDaysSupply(String daysSupply) {
		this.daysSupply = daysSupply;
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

}
