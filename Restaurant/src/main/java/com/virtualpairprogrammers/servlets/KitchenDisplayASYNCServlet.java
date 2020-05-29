package com.virtualpairprogrammers.servlets;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(value="/kitchenAsyncServlet", asyncSupported = true) //making a servlet asynchronous
public class KitchenDisplayASYNCServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
/*		Long size = Long.parseLong(req.getParameter("size"));
		
		MenuDao dao = MenuDaoFactory.getMenuDao();
		
		while(dao.getAllOrders().size() < size)  // Listening till getting a response for client.
		{										// Displays all orders placed and keeps polling till new order received.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e); // HTTP Worker Thread.
			}
		} //Till now implemented synchronous style. Now move whole 'while block' to separate "Background" thread to implement asynchronous.
		
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/HTML");
		
		writer.println("<p><strong> Next Order : </strong>" + dao.getOrder(size).toString() + "</p>");
		writer.close(); */   //Moving code to a new "Background" task(implementing Asynchronous servlets
		
		//Need to expose Request, Response objects to the background task through "Asynchronous Context".
		
		AsyncContext asyncContext = req.startAsync(req, resp); // To start async context "startAsync()"
		
		KitchenOrderDisplayTask task = new KitchenOrderDisplayTask();
		task.setAsyncContext(asyncContext);
		asyncContext.start(task); // To start a asynchronous servlet background task.
		
	}
}
