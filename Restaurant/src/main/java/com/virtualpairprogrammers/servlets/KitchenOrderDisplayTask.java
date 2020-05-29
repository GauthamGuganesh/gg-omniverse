package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;

public class KitchenOrderDisplayTask implements Runnable 
{
	private AsyncContext asyncContext;
	
	public void setAsyncContext(AsyncContext context)
	{
		this.asyncContext = context;
	}
	
	@Override
	public void run()
	{
		
		HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();
		
		PrintWriter writer;
		try {
			writer = resp.getWriter();
		} catch (IOException e) 
		{
			asyncContext.complete();
			throw new RuntimeException(e);
		}
		resp.setContentType("text/HTML");		

		Long size = Long.parseLong(req.getParameter("size"));		
 		MenuDao dao = MenuDaoFactory.getMenuDao();
		
		while(dao.getAllOrders().size() < size)  // Listening till getting a response for client.
		{										// Displays all orders placed and keeps polling till new order received.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e)
			{				
				asyncContext.complete();
				throw new RuntimeException(e); // HTTP Worker Thread.
			}
		} 
		
		
		Order order = dao.getOrder(size);
		writer.println("<p><strong> Next Order : </strong>" + order.toString() + "</p>");
		writer.close();
		
		
		asyncContext.complete();
	}

}
