package com.db.techJam.client.model;

public interface IEmergencyContact {
	
	public String getFirstName();
	public void setFirstName(String firstName);
	public String getLastName();
	public void setLastName(String lastName) ;
	public String getRelationShip();
	public void setRelationShip(String relationShip) ;
	public String getHomePhoneNumber();
	public void setHomePhoneNumber(String homePhoneNumber) ;
	public String getWorkPhoneNumber();
	public void setWorkPhoneNumber(String workPhoneNumber) ;
	public String getCellPhoneNumber();
	public void setCellPhoneNumber(String cellPhoneNumber);
	public String getAddress1() ;
	public void setAddress1(String address1);
	public String getAddress2();
	public void setAddress2(String address2);
	public String getCity();
	public void setCity(String city);
	public String getState() ;
	public void setState(String state);
	public String getCountry() ;
	public void setCountry(String country);
	public String getProvince();
	public void setProvince(String province);
	public String getPostCode() ;
	public void setPostCode(String postCode);
	public String getEmailAddress() ;
	public void setEmailAddress(String emailAddress) ;
	public IIndividual getIndividual();
	public void setIndividual(IIndividual individual);

}
