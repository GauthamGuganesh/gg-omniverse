package com.virtualpairprogrammers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MenuSearchCorrectionFilter implements Filter 
{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		String parameter = request.getParameter("searchTerm");
		
		 //Slang term for chicken. Instead for chicken user searches for boneless65,
														//we can modify it.
		if(parameter.toLowerCase().equals("boneless65"))
		{
			//no request.setParameter method in servlet-api.jar because it is purposefully
			//disabled to preserve the integrity of data given by the user. So we need a workaround.
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
