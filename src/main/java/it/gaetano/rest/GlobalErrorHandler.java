package it.gaetano.rest;

import jakarta.ws.rs.core.Response;

import it.gaetano.domain.CustomRuntimeException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class GlobalErrorHandler {

	@ServerExceptionMapper(CustomRuntimeException.class)
	public Response handleCustomRuntimeException(CustomRuntimeException cre) {
		return Response.serverError()
			.header("X-CUSTOM-ERROR", "500")
			.entity(new CustomError(500, cre.getMessage()))
			.build();
	
    }

	@ServerExceptionMapper(CustomRuntimeException.class)
	public Response handleCustomBadRequestException(CustomRuntimeException cre) {
		return Response.serverError()
			.header("X-CUSTOM-ERROR", "400")
			.entity(new CustomError(400, cre.getMessage()))
			.build();
	
    }
}