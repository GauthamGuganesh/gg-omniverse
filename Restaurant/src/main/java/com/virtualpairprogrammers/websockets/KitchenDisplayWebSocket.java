package com.virtualpairprogrammers.websockets;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.virtualpairprogrammers.data.MenuDao;
import com.virtualpairprogrammers.data.MenuDaoFactory;

@ServerEndpoint("/KitchenManagement")
public class KitchenDisplayWebSocket 
{
	@OnOpen
	public void open(Session session)
	{
		KitchenDisplaySessionHandler kitchenDisplaySessionHandler = KitchenDisplaySessionHandlerFactory.getKitchenDisplaySessionHandler();
		kitchenDisplaySessionHandler.addSession(session);
	}
	
	@OnClose
	public void close(Session session)
	{
		KitchenDisplaySessionHandler kitchenDisplaySessionHandler = KitchenDisplaySessionHandlerFactory.getKitchenDisplaySessionHandler();
		kitchenDisplaySessionHandler.removeSession(session);
	}
	
	@OnError
	public void error(Throwable e)
	{
		throw new RuntimeException(e);
	}
	
	@OnMessage //Triggered when receiving message from client
	public void sendMessage(String message, Session session)
	{
		JSONObject json = new JSONObject(message);
		Long id = json.getLong("id");
		String status = json.getString("status");
		
		//Updating the server with message from client and sending updated message to all clients.
		
		MenuDao menuDao = MenuDaoFactory.getMenuDao();
		System.out.println(id + " : " + status);
		menuDao.updateOrderStatus(id,status);
		
		KitchenDisplaySessionHandler kitchenDisplaySessionHandler = KitchenDisplaySessionHandlerFactory.getKitchenDisplaySessionHandler();
		kitchenDisplaySessionHandler.updateOrder(menuDao.getOrder(id));
	}
}
