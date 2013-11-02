package org.db.techjam.dao;

public class AUser {

	public String user;
	public String name;
	public String email;
	public String password;
	public long tweetCount;

	@Override
	public String toString() {
		return String.format("<User: %s, %s, %s, %d>", user, name, email,
				tweetCount);
	}

}
