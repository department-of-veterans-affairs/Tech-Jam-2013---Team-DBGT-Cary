package org.db.techjam.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MilitaryEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7176176795180213799L;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getServiceBranch() {
		return serviceBranch;
	}

	public void setServiceBranch(String serviceBranch) {
		this.serviceBranch = serviceBranch;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public ArrayList<MilitaryExposure> getExposures() {
		return exposures;
	}

	public void setExposures(ArrayList<MilitaryExposure> exposures) {
		this.exposures = exposures;
	}

	public String getServiceLocations() {
		return serviceLocations;
	}

	public void setServiceLocations(String serviceLocations) {
		this.serviceLocations = serviceLocations;
	}

	public String getOnboardShip() {
		return onboardShip;
	}

	public void setOnboardShip(String onboardShip) {
		this.onboardShip = onboardShip;
	}

	public String getMilitaryOccupationalSpeciality() {
		return militaryOccupationalSpeciality;
	}

	public void setMilitaryOccupationalSpeciality(
			String militaryOccupationalSpeciality) {
		this.militaryOccupationalSpeciality = militaryOccupationalSpeciality;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getMilitaryServiceDescription() {
		return militaryServiceDescription;
	}

	public void setMilitaryServiceDescription(String militaryServiceDescription) {
		this.militaryServiceDescription = militaryServiceDescription;
	}

	private String source;
	private String eventTitle;
	private Date eventDate;
	private String serviceBranch;
	private String rank;
	private ArrayList<MilitaryExposure> exposures;
	private String serviceLocations;
	private String onboardShip;
	private String militaryOccupationalSpeciality;
	private String assignment;
	private String militaryServiceDescription;

}
