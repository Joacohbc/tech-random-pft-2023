package com.rest;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.TokenManagmentBean;
import com.auth.TokenWrapper;
import com.auth.UserDetails;
import com.dto.JsonDTO;

@WebFilter(urlPatterns = "/api/auth/*")
public class TokenWebFilter implements Filter {

	@EJB
	private TokenManagmentBean jwtBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if(httpRequest.getRequestURI().endsWith("/api/auth/login") 
				|| httpRequest.getRequestURI().endsWith("/api/auth/register")
				|| httpRequest.getRequestURI().endsWith("/api/auth/refresh")
				|| httpRequest.getRequestURI().endsWith("/api/auth/itrs")) {
			chain.doFilter(request, response);
			return;
		}
		
		String authorizationHeader = httpRequest.getHeader("Authorization");
				
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			RESTUtils.responseJSON((HttpServletResponse) response, HttpServletResponse.SC_FORBIDDEN,
					new JsonDTO()
						.put("error", "No token provided")
						.toJSONString());
			return;
		}

		String token = authorizationHeader.substring(7);
		UserDetails ud = jwtBean.getTokenInfo(token);
		if (ud == null) {
			RESTUtils.responseJSON((HttpServletResponse) response, HttpServletResponse.SC_BAD_REQUEST,
					new JsonDTO()
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
