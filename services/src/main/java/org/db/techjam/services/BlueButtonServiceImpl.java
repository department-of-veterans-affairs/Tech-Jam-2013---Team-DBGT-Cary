package org.db.techjam.services;

import java.io.IOException;

import org.db.techjam.beans.BlueButton;
import org.db.techjam.beans.Individual;
import org.db.techjam.dao.BlueButtonDao;
import org.osgi.framework.ServiceException;

public class BlueButtonServiceImpl implements BlueButtonService {

	private BlueButtonDao blueButtonDao;

	public BlueButton retrieve(final Individual individual) throws ServiceException {

		try {
			return this.getBlueButtonDao().getBlueButton(individual);
		} catch (final IOException ioE) {
			throw new ServiceException(
					"Failed to retreive individual blue button:"
							+ individual.toString());
		}

	}

	public void persist(BlueButton blueButton) throws ServiceException {

		try {
			this.getBlueButtonDao().setBlueButton(blueButton);
		} catch (final IOException ioE) {
			throw new ServiceException(
					"Failed to retreive individual blue button:"
							+ blueButton.toString());
		}

	}

	public BlueButtonDao getBlueButtonDao() {
		return blueButtonDao;
	}

	public void setBlueButtonDao(BlueButtonDao blueButtonDao) {
		this.blueButtonDao = blueButtonDao;
	}

}
