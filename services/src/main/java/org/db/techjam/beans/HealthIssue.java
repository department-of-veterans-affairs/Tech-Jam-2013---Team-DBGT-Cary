package org.db.techjam.beans;

import java.io.Serializable;

public class HealthIssue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5064906590949910510L;
	private String issueDescription;

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

}
