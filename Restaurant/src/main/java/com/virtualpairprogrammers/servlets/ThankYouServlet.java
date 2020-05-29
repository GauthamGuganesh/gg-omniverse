package com.virtualpairprogrammers.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;

@WebServlet("/thankYou.html")
@ServletSecurity(@HttpConstraint(rolesAllowed= {"user"}))
public class ThankYouServlet extends HttpServlet {

	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{			
		HttpSession session = request.getSession();
		Long orderId = (Long) session.getAttribute("orderId");
		
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		
		Double total = menuDao.getOrderTotal(orderId);
		String status = menuDao.getOrder(orderId).getStatus();
		
		if (orderId == null) {
			response.sendRedirect("/order.html");
			return;
		}
		
		request.setAttribute("status", status);
		request.setAttribute("id", orderId);
		request.setAttribute("total", total);
		
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher = context.getRequestDispatcher("/thankyou.jsp");
		requestDispatcher.forward(request, response);
	}
}
