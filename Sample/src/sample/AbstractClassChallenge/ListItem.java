package sample.AbstractClassChallenge;


public abstract class ListItem 
{
	private String itemName;
	protected ListItem nextItem = null;
	protected ListItem previousItem = null;
	
	public ListItem(String itemName)
	{
		this.itemName = itemName;
	}
	
	abstract int compareTo(ListItem item);
	abstract ListItem setNextItem(ListItem item);
	abstract ListItem setPreviousItem(ListItem previous);
	abstract ListItem next();
	abstract ListItem previous();
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
