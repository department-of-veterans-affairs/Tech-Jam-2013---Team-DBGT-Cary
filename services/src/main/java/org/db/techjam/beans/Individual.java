package org.db.techjam.beans;

import java.io.Serializable;

public class Individual implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7557498834711201659L;

	private String fullName;

	private String dateOfBirth;

	public Individual() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Individual(String fullName, String dateOfBirth) {
		super();
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		return fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
