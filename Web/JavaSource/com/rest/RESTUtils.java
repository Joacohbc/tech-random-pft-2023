package com.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

public class RESTUtils {
	public static void responseJSON(HttpServletResponse httpResponse, int statusCode, String json) throws IOException {
    	httpResponse.setStatus(statusCode);
    	httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    	httpResponse.getWriter().write(json);
	}
}
