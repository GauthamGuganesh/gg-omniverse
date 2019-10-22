package sample.MapChallenge;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 17/02/2016.
 */
public class Basket {
    private final String name;
    private Map<StockItem, Integer> reservedList;

    public Basket(String name) {
        this.name = name;
        this.reservedList = new HashMap<>();
    }
    
    public boolean reserve(StockItem item, int quantity)
    {
    	if(item != null && quantity > 0)
    	{
    		if(quantity > item.quantityInStock() - item.getReservedStock())
    		{
    			System.out.println("Out of Stock");
    			return false;
    		}
    		else
    		{
    			int stockInBasket = reservedList.getOrDefault(item, 0);
    			item.reserveStock(quantity);
    			reservedList.put(item, stockInBasket + quantity);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
 	public boolean unreserve(StockItem item, int quantity)
 	{
 		if(item != null && quantity > 0)
    	{
 			int stockInBasket = reservedList.getOrDefault(item, 0);
    		if(quantity > stockInBasket)
    		{
    			System.out.println("Attempting to unreserve more than the number of items reserved.");
    			return false;
    		}
    			
			item.reserveStock(-quantity);
			reservedList.put(item, stockInBasket - quantity);
			return true;
    	}    	
    	
    	return false;
 	}
   
    public Map<StockItem, Integer> reservedItems()
    {
    	return Collections.unmodifiableMap(reservedList);
    }

    @Override
    public String toString() {
        String s = "\nShopping basket of reserved Items " + name + " contains " + reservedList.size() + ((reservedList.size() == 1) ? " item" : " items") + "\n";
        double totalCost = 0.0;
        for (Map.Entry<StockItem, Integer> item : reservedList.entrySet()) {
            s = s + item.getKey() + ". " + item.getValue() + " purchased\n";
            totalCost += item.getKey().getPrice() * item.getValue();
        }
        return s + "Total cost " + totalCost;
    }
}
