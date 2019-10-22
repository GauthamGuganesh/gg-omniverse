package sample;

import sample.OOPChallenge.DeluxeBurger;
import sample.OOPChallenge.Hamburger;
import sample.OOPChallenge.HealthyBurger;

public class BillsBurgers 
{
	public static void main(String[] args) throws Exception
	{
		try 
		{
			int totalPrice = 0;
			Hamburger hamburger = new Hamburger("HamBurger", "Italian roll", "Chicken", 150);
			hamburger.addAddition("Tomato", 10);
			hamburger.addAddition("Lettuce", 20);
			hamburger.addAddition("Onion", 5);
			hamburger.addAddition("Capsicum", 15);
			hamburger.addAddition("Jalapeno", 12);
			hamburger.printInvoice();
			totalPrice += hamburger.getTotalPrice();
			
			HealthyBurger healthyBurger = new HealthyBurger("Turkey", 200);
			healthyBurger.addAddition("Tomato", 10);
			healthyBurger.addAddition("Lettuce", 20);
			healthyBurger.addAddition("Onion", 5);
			healthyBurger.addAddition("Capsicum", 15);
			healthyBurger.addAddition("Jalapeno", 12);
			healthyBurger.addAddition("Pickles", 20);
			healthyBurger.addAddition("Mayonnaise", 4);
			healthyBurger.printInvoice();
			totalPrice += healthyBurger.getTotalPrice();
			
			DeluxeBurger deluxeBurger = new DeluxeBurger("Oregano", "Bacon", 250);
			deluxeBurger.addAddition("Tomato", 20);
			deluxeBurger.addAddition("Capsicum", 30);
			deluxeBurger.printInvoice();
			totalPrice += deluxeBurger.getTotalPrice();
			
			System.out.println("Total purchase price :: " + totalPrice);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
