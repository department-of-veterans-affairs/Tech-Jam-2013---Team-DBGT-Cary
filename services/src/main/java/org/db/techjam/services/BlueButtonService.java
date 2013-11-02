package org.db.techjam.services;

import org.db.techjam.beans.BlueButton;
import org.db.techjam.beans.Individual;
import org.osgi.framework.ServiceException;

public interface BlueButtonService {

	public BlueButton retrieve(final Individual individual) throws ServiceException;

	public void persist(final BlueButton blueButton) throws ServiceException;

}
