package com.rest;

import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dto.ItrMapper;
import com.services.ItrBean;

@Path("itrs")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ItrController {
	
	@EJB
	private ItrBean itrBean;
	
	@GET
	public Response getEventos() {
		try {
			return Response.ok(itrBean.findAll()
					.stream()
					.map(t -> ItrMapper.toItrDTO(t))
					.collect(Collectors.toList()))
					.build();
		} catch (Exception e) {
			return RESTUtils.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
