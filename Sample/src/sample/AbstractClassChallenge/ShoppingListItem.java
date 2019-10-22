package sample.AbstractClassChallenge;


public class ShoppingListItem extends ListItem
{

	public ShoppingListItem(String itemName, ListItem nextItem, ListItem previousItem) 
	{
		super(itemName);		
	}

	@Override
	public int compareTo(ListItem item)
	{
		String itemName = this.getItemName();
		String itemName2 = item.getItemName();
		
		if(itemName.compareTo(itemName2) == 0)
			return 0;
		else 
			{
				if(itemName.compareTo(itemName2) < 0)
					// String object less than argument
					return -1;
				else
					// String object greater than argument
					return 1;
			}
	}

	@Override
	ListItem setNextItem(ListItem item) 
	{
		this.nextItem = item;
		return this.nextItem;
	}

	@Override
	ListItem setPreviousItem(ListItem previous)
	{
		this.previousItem = previous;
		return this.previousItem;
	}

	@Override
	ListItem next() 
	{
		return nextItem;
	}

	@Override
	ListItem previous()
	{
		return previousItem;
	}

}
