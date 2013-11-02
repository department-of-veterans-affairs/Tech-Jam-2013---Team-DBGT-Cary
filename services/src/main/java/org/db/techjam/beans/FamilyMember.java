package org.db.techjam.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class FamilyMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4051184201610217501L;
	private String source;
	private String relationship;
	private String firstName;
	private String lastName;
	private String livingStatus;
	private String comments;
	private ArrayList<HealthIssue> healthIssues;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLivingStatus() {
		return livingStatus;
	}

	public void setLivingStatus(String livingStatus) {
		this.livingStatus = livingStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ArrayList<HealthIssue> getHealthIssues() {
		return healthIssues;
	}

	public void setHealthIssues(ArrayList<HealthIssue> healthIssues) {
		this.healthIssues = healthIssues;
	}

}
