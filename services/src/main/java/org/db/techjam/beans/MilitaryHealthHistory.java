package org.db.techjam.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class MilitaryHealthHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1619050186473361522L;

	public ArrayList<MilitaryEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<MilitaryEvent> events) {
		this.events = events;
	}

	public ArrayList<MilitaryExposure> getExposures() {
		return exposures;
	}

	public void setExposures(ArrayList<MilitaryExposure> exposures) {
		this.exposures = exposures;
	}

	private ArrayList<MilitaryEvent> events;
	private ArrayList<MilitaryExposure> exposures;

}
