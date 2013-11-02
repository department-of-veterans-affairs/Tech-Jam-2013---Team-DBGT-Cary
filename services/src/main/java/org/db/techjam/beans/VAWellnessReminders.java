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

class VAWellnessReminders implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3968246722555341519L;
	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] SOURCE = Bytes.toBytes("source");
	private static final byte[] LATESTUPDATED = Bytes.toBytes("latestUpdated");
	private static final byte[] REMINDERDESC = Bytes.toBytes("reminderDesc");
	private static final byte[] DUEDATE = Bytes.toBytes("dueDate");
	private static final byte[] LASTCOMPLETED = Bytes.toBytes("lastCompleted");
	private static final byte[] LOCATION = Bytes.toBytes("location");
	
	private static final byte[] CF = Bytes.toBytes("DETAILS");
	private static final byte[] TBNAME = Bytes.toBytes("WELLNESSREMINDERS");
	
	private String source;
	private String latestUpdated;
	private String reminderDescription;
	private String dueDate;
	private String lastCompleted;
	private String location;
	
	private Individual individual;
	
	public VAWellnessReminders(Individual individual){
		
		this.individual = individual;
			
	}
	
	public void putHealthCareProvider(String source,String providerName,String typeOfProvider,String otherClinicalInformation,String phoneNumber,String extension,String email, String comments) throws IOException {
		
		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);
		
		Put p = mkPut(source,providerName);
		
		p.add(CF,SOURCE,Bytes.toBytes(source));
		p.add(CF,LATESTUPDATED,Bytes.toBytes(latestUpdated));
		p.add(CF,REMINDERDESC,Bytes.toBytes(reminderDescription));
		p.add(CF,DUEDATE,Bytes.toBytes(dueDate));
		p.add(CF,LASTCOMPLETED,Bytes.toBytes(lastCompleted));
		p.add(CF,LOCATION,Bytes.toBytes(location));
		p.add(CF,INDIVIDUAL,Bytes.toBytes(individual.getFullName()));
		
		
		individuals.put(p);
		individuals.close();
		
		
	}

	
	private Put mkPut(String source,String location) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName()+source+location)); 
		
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

	public String getLatestUpdated() {
		return latestUpdated;
	}

	public void setLatestUpdated(String latestUpdated) {
		this.latestUpdated = latestUpdated;
	}

	public String getReminderDescription() {
		return reminderDescription;
	}

	public void setReminderDescription(String reminderDescription) {
		this.reminderDescription = reminderDescription;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getLastCompleted() {
		return lastCompleted;
	}

	public void setLastCompleted(String lastCompleted) {
		this.lastCompleted = lastCompleted;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	
	public LinkedList<VAWellnessReminders> getHealthCareProviders(Individual i) throws IOException {
		
		Scan s = new Scan();
		System.out.println(i.getFullName());
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				CF,
				INDIVIDUAL,
				CompareOp.EQUAL,
				Bytes.toBytes(i.getFullName())
				);
		s.setFilter(filter);
		s.addFamily(CF);
		LinkedList<VAWellnessReminders> vaWellNessReminders = new LinkedList<VAWellnessReminders>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME).getScanner(s);
		for(Result r : results) {
			VAWellnessReminders vR = new VAWellnessReminders(this.individual);
			vR.setSource(Bytes.toString(r.getValue(CF,SOURCE)));
			vR.setLatestUpdated(Bytes.toString(r.getValue(CF,LATESTUPDATED)));
			vR.setLastCompleted(Bytes.toString(r.getValue(CF,LASTCOMPLETED)));
			vR.setReminderDescription(Bytes.toString(r.getValue(CF,REMINDERDESC)));
			vR.setIndividual(i);
			vR.setDueDate(Bytes.toString(r.getValue(CF,DUEDATE)));
			vR.setLocation(Bytes.toString(r.getValue(CF,LOCATION)));
			vaWellNessReminders.add(vR);
		}

		return vaWellNessReminders;
	}
	
	
	
}