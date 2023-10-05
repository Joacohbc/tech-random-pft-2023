package com.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dto.JsonDTO;

public class RESTUtils {
	public static void responseJSON(HttpServletResponse httpResponse, int statusCode, String json) throws IOException {
    	httpResponse.setStatus(statusCode);
    	httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    	httpResponse.getWriter().write(json);
	}
	
	public static Response message(Status st, String message) {
		return Response.status(st)
				.entity(new JsonDTO()
							.put("message", message)
							.build())
				.build();
	}
	
	public static Response error(Status st, String message) {
		return Response.status(st)
				.entity(new JsonDTO()
							.put("error", message)
							.build())
				.build();
	}
}
