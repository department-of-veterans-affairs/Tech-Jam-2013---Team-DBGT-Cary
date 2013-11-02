package org.db.techjam.dao;

import java.io.IOException;

import org.db.techjam.beans.BlueButton;

import org.db.techjam.beans.Individual;

public class BlueButtonDaoImpl implements BlueButtonDao {
	
	
	public BlueButton getBlueButton(Individual individual)
			throws IOException {
		
		return new BlueButton(individual);
 
		
	}

	
	
	public void setBlueButton(BlueButton blueButton) throws IOException {

	}
}
