package com.virtualpairprogrammers.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;

@WebServlet("/processorder.html")
public class ProcessOrderServlet extends HttpServlet{
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
	MenuDao menuDao = MenuDaoFactory.getMenuDao();
	List<Order> orders;
	orders = menuDao.getAllOrders();
	request.setAttribute("orders", orders);
	
	List<String> statuses = new ArrayList<String>();
	statuses.add("order accepted");
	statuses.add("payment received");
	statuses.add("being prepared");
	statuses.add("ready for collection");
	
	request.setAttribute("statuses", statuses);

	ServletContext context = getServletContext();
	RequestDispatcher dispatch = context.getRequestDispatcher("/processorder.jsp");
	dispatch.forward(request,response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		Long id = Long.valueOf(request.getParameter("id"));
		String status =  request.getParameter("status");
		System.out.println(id + " : " + status);
		menuDao.updateOrderStatus(id,status);
		doGet(request,response);
		}
}
