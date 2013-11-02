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


class HealthCareProvider implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1840689090635433645L;
	private static final byte[] INDIVIDUAL = Bytes.toBytes("individual");
	private static final byte[] SOURCE = Bytes.toBytes("source");
	private static final byte[] PROVIDERNAME = Bytes.toBytes("providerName");
	private static final byte[] TYPEOFPROVIDER = Bytes.toBytes("typeOfProvider");
	private static final byte[] OTHERCLINICALINFORMATION = Bytes.toBytes("otherClinicalInformation");
	private static final byte[] PHONENUMBER = Bytes.toBytes("phoneNumber");
	private static final byte[] EXTENSION = Bytes.toBytes("extension");
	private static final byte[] EMAIL = Bytes.toBytes("email");
	private static final byte[] COMMENTS = Bytes.toBytes("comments");
	
	private static final byte[] CF = Bytes.toBytes("DETAILS");
	private static final byte[] TBNAME = Bytes.toBytes("HEALTHCAREPOVIDERS");
	
	private String source;
	private String providerName;
	private String typeOfProvider;
	private String otherClinicalInformation;
	private String phoneNumber;
	private String extension;
	private String email;
	private String comments;
	
	private Individual individual;
	
	public HealthCareProvider(Individual individual){
		
		this.individual = individual;
			
	}
	
	public void putHealthCareProvider(String source,String providerName,String typeOfProvider,String otherClinicalInformation,String phoneNumber,String extension,String email, String comments) throws IOException {
		
		HTablePool pool = HTablePoolFactory.getPool();
		HTableInterface individuals = pool.getTable(TBNAME);
		
		Put p = mkPut(source,providerName);
		
		p.add(CF,SOURCE,Bytes.toBytes(source));
		p.add(CF,PROVIDERNAME,Bytes.toBytes(providerName));
		p.add(CF,TYPEOFPROVIDER,Bytes.toBytes(typeOfProvider));
		p.add(CF,OTHERCLINICALINFORMATION,Bytes.toBytes(otherClinicalInformation));
		p.add(CF,PHONENUMBER,Bytes.toBytes(phoneNumber));
		p.add(CF,EXTENSION,Bytes.toBytes(extension));
		p.add(CF,EMAIL,Bytes.toBytes(email));
		p.add(CF,COMMENTS,Bytes.toBytes(comments));
		p.add(CF,INDIVIDUAL,Bytes.toBytes(individual.getFullName()));
		
		
		individuals.put(p);
		individuals.close();
		
		
	}

	
	private Put mkPut(String source,String provider) {
		Put p = new Put(Bytes.toBytes(this.individual.getFullName()+source+providerName)); 
		
		return p;
	}
	
	public Individual getIndividual() {
		return individual;
	}



	public void setIndividual(Individual individual) {
		this.individual = individual;
	}
	
	public LinkedList<HealthCareProvider> getHealthCareProviders(Individual i) throws IOException {
		
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
		LinkedList<HealthCareProvider> healthCareProviders = new LinkedList<HealthCareProvider>();
		ResultScanner results = HTablePoolFactory.getPool().getTable(TBNAME).getScanner(s);
		for(Result r : results) {
			HealthCareProvider hC = new HealthCareProvider(this.individual);
			hC.setSource(Bytes.toString(r.getValue(CF,SOURCE)));
			hC.setProviderName(Bytes.toString(r.getValue(CF,PROVIDERNAME)));
			hC.setEmail(Bytes.toString(r.getValue(CF,EMAIL)));
			hC.setExtension(Bytes.toString(r.getValue(CF,EXTENSION)));
			hC.setIndividual(i);
			hC.setComments(Bytes.toString(r.getValue(CF,COMMENTS)));
			hC.setTypeOfProvider(Bytes.toString(r.getValue(CF,TYPEOFPROVIDER)));
			hC.setOtherClinicalInformation(Bytes.toString(r.getValue(CF,OTHERCLINICALINFORMATION)));
			healthCareProviders.add(hC);
		}

		return healthCareProviders;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getTypeOfProvider() {
		return typeOfProvider;
	}
	public void setTypeOfProvider(String typeOfProvider) {
		this.typeOfProvider = typeOfProvider;
	}
	public String getOtherClinicalInformation() {
		return otherClinicalInformation;
	}
	public void setOtherClinicalInformation(String otherClinicalInformation) {
		this.otherClinicalInformation = otherClinicalInformation;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}