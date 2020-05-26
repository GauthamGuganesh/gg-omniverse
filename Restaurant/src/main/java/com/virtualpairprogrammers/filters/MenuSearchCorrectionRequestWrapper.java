package com.virtualpairprogrammers.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MenuSearchCorrectionRequestWrapper extends HttpServletRequestWrapper 
{
	private String newSearchTerm;

	public MenuSearchCorrectionRequestWrapper(HttpServletRequest request) 
	{
		super(request);
	}
	
	@Override
	public String getParameter(String key) //This method is for use in the servlet.(not the filter where we setNewSearchTerm).
	{
		 //In the servlet if you try to get the value of parameter "searchTerm", this newSearchTerm is returned, else 
		//the normal method in parent class is called.
		
		if(key.equals("searchTerm"))
		{
			return newSearchTerm;
		}
		else
			return super.getParameter(key);
	}
	
	public void setNewSearchTerm(String newSearchTerm)
	{
		this.newSearchTerm = newSearchTerm;
	}
}
