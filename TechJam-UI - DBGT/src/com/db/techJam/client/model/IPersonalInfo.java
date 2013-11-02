package com.db.techJam.client.model;

public interface IPersonalInfo {
		
	public String getFirstName();

	public void setFirstName(String firstName);

	public String getMiddleInitial();

	public void setMiddleInitial(String middleInitial);

	public String getLastName();

	public void setLastName(String lastName) ;

	public String getSuffix();

	public void setSuffix(String suffix);

	public String getDateOfBirth();

	public void setDateOfBirth(String dateOfBirth) ;

	public String getAlias();

	public void setAlias(String alias);

	public String getRelationShipToVA() ;

	public void setRelationShipToVA(String relationShipToVA) ;

	public String getGender();

	public void setGender(String gender) ;

	public String getBloodType();

	public void setBloodType(String bloodType);

	public String getOrganDonor();

	public void setOrganDonor(String organDonor);

	public String getMartialStatus();

	public void setMartialStatus(String martialStatus);

}
