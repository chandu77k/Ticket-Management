package com.questk2.entity;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * A servlet filter that logs incoming request URIs.
 */
@Component
@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter{
	/**
     * Intercepts HTTP requests and logs the request URI before passing the request along the filter chain.
     *
     * @param request  the incoming servlet request.
     * @param response the outgoing servlet response.
     * @param chain    the filter chain to pass the request and response along.
     * @throws IOException      if an I/O error occurs during filtering.
     * @throws ServletException if a servlet error occurs during filtering.
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println("Request URI : "+httpRequest.getRequestURI());
		chain.doFilter(request, response);
	}

}
