package org.db.techjam.beans;

import java.io.Serializable;

public class MilitaryExposure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8387278571574040194L;

	public String getExposureDescription() {
		return exposureDescription;
	}

	public void setExposureDescription(String exposureDescription) {
		this.exposureDescription = exposureDescription;
	}

	private String exposureDescription;
}
