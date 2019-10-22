package sample.OOPChallenge;

import java.util.ArrayList;
import java.util.List;

public class Hamburger
{
	private String name;
	private String breadRollType;
	private String meat;
	private int basePrice;
	private List<Additions> additions;
	
	public Hamburger(String name, String breadRollType, String meat, int basePrice)
	{
		this.name = name;
		this.breadRollType = breadRollType;
		this.meat = meat;
		this.basePrice = basePrice;
		additions = new ArrayList<Additions>();
	}
	
	public void addAddition(String additionName, int additionPrice)
	{
		if(additions.size() < 4)
		{
			additions.add(new Additions(additionName, additionPrice));
		}
		else
			System.out.println("Additions max limit reached. Cannot add " + additionName);
	}
	
	public int getTotalPrice()
	{
		int totalAdditionsPrice = 0;
		if(!additions.isEmpty())
		{
			for(int i = 0; i < additions.size() ; i++)
				totalAdditionsPrice += additions.get(i).getAdditionPrice();
			
			return basePrice + totalAdditionsPrice;
		}
		
		return basePrice;
	}
	
	public void printInvoice()
	{
		System.out.println("Burger Name :: " + name + "\n");
		System.out.println("Base Price :: " + basePrice + "\n");
		for(int i = 0 ; i < additions.size() ; i++)
		{
			System.out.println("Addition Name :: " + additions.get(i).getAdditionName());
			System.out.println("Addition Price :: " + additions.get(i).getAdditionPrice());
		}
		System.out.println("Total Price :: " + getTotalPrice());
	}
	
	public String getName()
	{
		return name;
	}

	public String getBreadRollType() {
		return breadRollType;
	}

	public void setBreadRollType(String breadRollType) {
		this.breadRollType = breadRollType;
	}

	public String getMeat() {
		return meat;
	}

	public void setMeat(String meat) {
		this.meat = meat;
	}

	public int getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(int price) {
		this.basePrice = price;
	}

	public List<Additions> getAdditions() {
		return additions;
	}

	public void setAdditions(List<Additions> additions) {
		this.additions = additions;
	}
}
