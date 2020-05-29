package com.virtualpairprogrammers.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import org.json.JSONObject;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;
import com.virtualpairprogrammers.domain.Order;

public class KitchenDisplaySessionHandler
{
	List<Session> sessions = new ArrayList<Session>(); // Session handler having all sessions of a websocket.
	
	public void addSession(Session session)
	{
		sessions.add(session);
		//Sending all orders to a session. To prevent orders from being erased on screen after refresh.
		sendAllOrders(session);
	}
	
	public void removeSession(Session session)
	{
		sessions.remove(session);
	}
	
	private void sendMessage(JSONObject message) // Sending a message to all sessions in the handler
	{
		for(Session session : sessions)
		{
			try 
			{
				// Returns a remote-end-point reference of the client that is in conversation with this connection session.
				session.getBasicRemote().sendText(message.toString()); 
			} 
			catch (IOException e)
			{
				//If exception thrown means cannot communicate with client, hence we remove the session object.
				removeSession(session);
			} 

		}
	}
	
	private void sendMessage(JSONObject message, Session session) // Sending a message to a single session
	{
		try 
		{
			session.getBasicRemote().sendText(message.toString()); 
		} 
		catch (IOException e)
		{
			removeSession(session);
		} 
	}

	private JSONObject generateJSONforOrder(Order order) {
		JSONObject json = new JSONObject();
		json.append("id", order.getId().toString());
		json.append("status", order.getStatus());
		json.append("content", order.toString());
		json.append("action", "ADD");
		json.append("time", new Date().toString());
		return json;
	}
	
	public void newOrder(Order order) // As soon as new order received(--OrderReceivedServlet--), it is sent to all clients.
	{
		sendMessage(generateJSONforOrder(order));
	}
	
	public void updateOrder(Order order)
	{
		JSONObject json = new JSONObject();
		json.append("id", order.getId().toString());
		json.append("action", "REMOVE");
		sendMessage(json);
		
		//If order status had been updated to "ready for collection", just remove it. No adding.
		if(!order.getStatus().equals("ready for collection"))
			newOrder(order);
	}
	
	public void sendAllOrders(Session session) 
	{
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		
		List<Order> allOrders = menuDao.getAllOrders();
		
		for(Order order : allOrders)
		{
			if(!order.getStatus().equals("ready for collection"))  // We are not going to display 'ready for collection' objects.
				sendMessage(generateJSONforOrder(order), session);
		}
	}
}





