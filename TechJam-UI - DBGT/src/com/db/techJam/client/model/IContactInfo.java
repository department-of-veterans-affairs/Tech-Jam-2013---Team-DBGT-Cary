package com.db.techJam.client.model;

public interface IContactInfo {
	
	public String getState();

	public void setState(String state);

	public String getCountry();

	public void setCountry(String country) ;
	public String getAddressLine1();

	public void setAddressLine1(String addressLine1) ;

	public String getAddressLine2();

	public void setAddressLine2(String addressLine2);

	public String getCity() ;

	public void setCity(String city) ;

	public String getProvince() ;

	public void setProvince(String province);

	public String getPostalCode() ;

	public void setPostalCode(String postalCode) ;

	public String getWorkPhone();

	public void setWorkPhone(String workPhone) ;

	public String getHomePhone();

	public void setHomePhone(String homePhone) ;

	public String getPagerNumber();

	public void setPagerNumber(String pagerNumber);

	public String getFaxNumber() ;

	public void setFaxNumber(String faxNumber) ;

	public String getEmailAddress() ;

	public void setEmailAddress(String emailAddress) ;

	public String getPreferredMethodOfContact();

	public void setPreferredMethodOfContact(String preferredMethodOfContact);

	public IIndividual getIndividual() ;

	public void setIndividual(IIndividual individual);

}
