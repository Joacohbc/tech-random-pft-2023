package com.rest;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.mail.internet.ContentType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.collections.map.HashedMap;

import com.auth.TokenManagmentBean;
import com.auth.TokenWrapper;
import com.auth.UserDetails;
import com.dto.JsonWrapper;
import com.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

@WebFilter(urlPatterns = "/api/auth/*")
public class TokenWebFilter implements Filter {

	@EJB
	private TokenManagmentBean jwtBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorizationHeader = httpRequest.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			RESTUtils.responseJSON((HttpServletResponse) response, HttpServletResponse.SC_FORBIDDEN,
					new JsonWrapper()
						.put("error", "No token provided")
						.toJSONString());
			return;
		}

		String token = authorizationHeader.substring(7);
		UserDetails ud = jwtBean.getTokenInfo(token);
		if (ud == null) {
			RESTUtils.responseJSON((HttpServletResponse) response, HttpServletResponse.SC_BAD_REQUEST,
					new JsonWrapper()
						.put("error", "invalid token")
						.toJSONString());
			return;
		}

		TokenWrapper tw = new TokenWrapper();
		tw.setToken(token);
		tw.setUserInfo(ud);
		request.setAttribute("token", tw);

		chain.doFilter(request, response);
	}

}
