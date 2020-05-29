package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.virtualpairprogrammers.data.MenuDaoFactory;

@WebServlet("/updateStatus")
public class OrderStatusUpdateAJAXServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		Long orderId = Long.valueOf(req.getParameter("id"));
		
		String status = MenuDaoFactory.getMenuDao().getOrder(orderId).getStatus();
		
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		JSONObject json = new JSONObject();
		json.put("status", status);
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");	
		json.put("time", sdf.format(new Date()));
		
		out.write(json.toString());
		out.close();
	}
}
