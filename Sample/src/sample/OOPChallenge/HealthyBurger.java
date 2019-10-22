package sample.OOPChallenge;

import java.util.List;

public class HealthyBurger extends Hamburger 
{
	public HealthyBurger(String meat, int price)
	{
		super("Healthy Burger", "Brown bread roll", meat, price);
	}
	
	@Override
	public void addAddition(String additionName, int additionPrice) 
	{
		List<Additions> additions = getAdditions();
		if(additions.size() < 6)
			additions.add(new Additions(additionName, additionPrice));
		else
			System.out.println("Additions size exceeded. Only 6 additions can be added for Healthy burger. Cannot add " + additionName);
	}
}
