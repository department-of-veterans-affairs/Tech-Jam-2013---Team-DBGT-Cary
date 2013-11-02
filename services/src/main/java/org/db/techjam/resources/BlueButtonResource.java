package org.db.techjam.resources;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.db.techjam.beans.BlueButton;
import org.db.techjam.services.BlueButtonService;
import org.osgi.framework.ServiceException;

@Path("/blueButton")
public class BlueButtonResource {

	@Context
	private ServletConfig config;

	private BlueButtonService blueButtonService;

	public ServletConfig getConfig() {
		return config;
	}

	public void setConfig(ServletConfig a_config) {
		this.config = a_config;
	}

	@Path("/getBlueButton")
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public BlueButton getBlueButton(@Context final HttpServletRequest request,
			@Context final HttpServletResponse response)
			throws ServiceException {
		System.out.print("getBlueButton");
		return new BlueButton();

		// return this.getBlueButtonService().retrieve(individual);
	}

	@Path("/setBlueButton")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_FORM_URLENCODED })
	public void setBlueButton(@Context final HttpServletRequest request,
			@Context final HttpServletResponse response,
			final BlueButton blueButton) throws ServiceException {
		System.out.print("setBlueButton");;
		this.getBlueButtonService().persist(blueButton);
	}

	public BlueButtonService getBlueButtonService() {
		return blueButtonService;
	}

	public void setBlueButtonService(BlueButtonService blueButtonService) {
		this.blueButtonService = blueButtonService;
	}

}
