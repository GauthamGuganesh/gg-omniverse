package com.virtualpairprogrammers.websockets;

public class KitchenDisplaySessionHandlerFactory
{
	private static KitchenDisplaySessionHandler handler;
	
	public static KitchenDisplaySessionHandler getKitchenDisplaySessionHandler()
	{
		if(handler == null)
			handler = new KitchenDisplaySessionHandler();
		
		return handler;
	}
}
