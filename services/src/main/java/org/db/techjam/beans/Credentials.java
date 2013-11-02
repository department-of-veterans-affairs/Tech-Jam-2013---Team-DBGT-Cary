package org.db.techjam.beans;

import java.io.Serializable;

public class Credentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6901487523533560029L;

	private String userID;

	private String password;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
