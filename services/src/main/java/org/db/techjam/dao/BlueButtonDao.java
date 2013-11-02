package org.db.techjam.dao;

import java.io.IOException;

import org.db.techjam.beans.BlueButton;
import org.db.techjam.beans.Individual;

public interface BlueButtonDao {

	public BlueButton getBlueButton(Individual individual)
			throws IOException;

	public void setBlueButton(BlueButton blueButton) throws IOException;
}
