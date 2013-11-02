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

public class VAAppointments implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7381738309288981884L;
	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] SOURCE = Bytes.toBytes("source");
	private static final byte[] LASTUPDATED = Bytes.toBytes("lastUpdated");
	private static final byte[] TYPE = Bytes.toBytes("type");
	private static final byte[] DATETIME = Bytes.toBytes("dateTime");
	private static final byte[] LOCATION = Bytes.toBytes("location");
	private static final byte[] STATUS = Bytes.toBytes("status");
	private static final byte[] CLINIC = Bytes.toBytes("clinic");
	private static final byte[] PHONENUMBER = Bytes.toBytes("phoneNumber");
	private static final byte[] NOTE = Bytes.toBytes("note");
	private static final byte[] CF = Bytes.toBytes("DETAILS");
	private static final byte[] TBNAME = Bytes.toBytes("VAAppointments");
	
	private String source;
	private String lastUpdated;
	private String type;
	private String datetime;
	private String location;
	private String status;
	private String clinic;
	private String phoneNumber;
	
	public void putVAAppointments(String source,String lastUpdated,String type,String datetime,String location,String status,String clinic, String phoneNumber) throws IOException {
		
		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);
		
		Put p = mkPut(source,clinic);
		
		p.add(CF,SOURCE,Bytes.toBytes(source));
		p.add(CF,LASTUPDATED,Bytes.toBytes(lastUpdated));
		p.add(CF,TYPE,Bytes.toBytes(type));
		p.add(CF,DATETIME,Bytes.toBytes(datetime));
		p.add(CF,PHONENUMBER,Bytes.toBytes(phoneNumber));
		p.add(CF,LOCATION,Bytes.toBytes(location));
		p.add(CF,STATUS,Bytes.toBytes(status));
		p.add(CF,CLINIC,Bytes.toBytes(clinic));
		p.add(CF,INDIVIDUAL,Bytes.toBytes(individual.getFullName()));
		
		
		individuals.put(p);
		individuals.close();
		
		
	}

	private Put mkPut(String source,String clinic) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName()+source+clinic)); 
		
		return p;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClinic() {
		return clinic;
	}

	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Individual getIndividual() {
		return individual;
	}

	private String note;
	
	
	private Individual individual;
	
	public VAAppointments(Individual individual){
		
		this.individual = individual;
			
	}
	
	
	public LinkedList<VAAppointments> getVAAppointments(Individual i) throws IOException {
		
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
		LinkedList<VAAppointments> vaAppointments = new LinkedList<VAAppointments>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME).getScanner(s);
		for(Result r : results) {
			VAAppointments vA = new VAAppointments(this.individual);
			vA.setClinic(Bytes.toString(r.getValue(CF,CLINIC)));
			vA.setDatetime(Bytes.toString(r.getValue(CF,DATETIME)));
			vA.setLastUpdated(Bytes.toString(r.getValue(CF,LASTUPDATED)));
			vA.setLocation(Bytes.toString(r.getValue(CF,LOCATION)));
			vA.setIndividual(i);
			vA.setNote(Bytes.toString(r.getValue(CF,NOTE)));
			vA.setPhoneNumber(Bytes.toString(r.getValue(CF,PHONENUMBER)));
			vA.setSource(Bytes.toString(r.getValue(CF,SOURCE)));
			vA.setStatus(Bytes.toString(r.getValue(CF,STATUS)));
			vA.setType(Bytes.toString(r.getValue(CF,TYPE)));
			vaAppointments.add(vA);
		}

		return vaAppointments;
	}

	private void setIndividual(Individual i) {
		this.individual = i;
		
	}
	
}