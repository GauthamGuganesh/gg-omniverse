package sample.OOPChallenge;

public class Additions
{
	private String additionName;
	private int additionPrice;
	
	protected Additions(String additionName, int additionPrice)
	{
		this.additionName = additionName;
		this.additionPrice = additionPrice;
	}

	public String getAdditionName() {
		return additionName;
	}

	public void setAdditionName(String additionName) {
		this.additionName = additionName;
	}

	public int getAdditionPrice() {
		return additionPrice;
	}

	public void setAdditionPrice(int additionPrice) {
		this.additionPrice = additionPrice;
	}
	
	@Override
	public String toString() 
	{
		return "AdditionName :: " + additionName + "additionPrice :: " + additionPrice;
	}
}
