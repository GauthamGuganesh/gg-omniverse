package sample.OOPChallenge;

import java.util.List;

public class DeluxeBurger extends Hamburger 
{
	public DeluxeBurger(String breadRollType, String meat, int price)
	{
		super("Deluxe Burger", breadRollType, meat, price);
		initializeAdditions();
	}
	
	public void initializeAdditions()
	{
		List<Additions> additions = getAdditions();
		additions.add(new Additions("Chips", 20));
		additions.add(new Additions("Drinks", 30));
	}
	
	@Override
	public void addAddition(String additionName, int additionPrice) 
	{
		System.out.println("Cannot add additions to Deluxe Burger. It's a pre-built package. Cannot add " + additionName);
	}
}
