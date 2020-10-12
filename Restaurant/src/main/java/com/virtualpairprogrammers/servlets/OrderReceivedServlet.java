package com.virtualpairprogrammers.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.domain.Order;
import com.virtualpairprogrammers.websockets.KitchenDisplaySessionHandler;
import com.virtualpairprogrammers.websockets.KitchenDisplaySessionHandlerFactory;

@WebServlet("/orderReceived.html")
public class OrderReceivedServlet extends HttpServlet {
	
	MenuDao menuDao = MenuDaoFactory.getMenuDao();
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int maxId = menuDao.getFullMenu().size();
		Order order = menuDao.newOrder(request.getUserPrincipal().getName());
		for (int i = 0; i <maxId+1; i++) {
			String quantity = request.getParameter("item_" + i);
			 try  
			  {  
			    int q = Integer.parseInt(quantity);
			    if (q > 0) {
			    	menuDao.addToOrder(order.getId(), menuDao.getItem(i), q);
			    	order.addToOrder(menuDao.getItem(i), q);
			    }
			  }  
			  catch(NumberFormatException nfe)  
			  {  
			    //that's fine it just means there wasn't an order for this item 
			  }  
			  
		}
		
		KitchenDisplaySessionHandler handler = KitchenDisplaySessionHandlerFactory.getKitchenDisplaySessionHandler();
		handler.newOrder(order); //Integrating web sockets into our application. 
		
		System.out.println("A new order has been received.");
		
		HttpSession session = request.getSession();
		session.setAttribute("orderId", order.getId());
		
		String redirectUrl = "/thankYou.html";
		redirectUrl = response.encodeURL(redirectUrl); 
		//If cookies not in browser, session attributes cannot be retrieved. In that case
		//we have to encode sessionID in URL. That's what response.encodeURL does.
		
		response.sendRedirect(redirectUrl);

	}
}
