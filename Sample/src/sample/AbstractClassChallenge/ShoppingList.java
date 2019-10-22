package sample.AbstractClassChallenge;


public class ShoppingList implements ItemList
{
	private ListItem head;

	@Override
	public ListItem getRoot() 
	{
		return head;
	}

	@Override
	public boolean addItem(ListItem listItem) 
	{
		if(head == null)
		{
			head = (ShoppingListItem) listItem;
			return true;
		}
		
		ListItem currentItem = head;
		ShoppingListItem shItem = (ShoppingListItem) listItem;
		
		while(currentItem != null)
		{
			System.out.println("Comapare To value :: " + shItem.compareTo(currentItem));
			if(shItem.compareTo(currentItem) < 0)
			{
				// Item object precedes currentItem
				if(currentItem.previous() != null)
				{
					// Inserting item between two items since currentItem is not root item;
					shItem.setNextItem(currentItem);
					shItem.setPreviousItem(currentItem.previous());
					currentItem.previous().setNextItem(shItem);
					currentItem.setPreviousItem(shItem);
					return true;
				}
				else
				{
					// Inserting item before root since currentItem is root item
					shItem.setNextItem(currentItem);
					currentItem.setPreviousItem(shItem);
					head = shItem;
					return true;
				}
			}
			else if(shItem.compareTo(currentItem) > 0)
			{
				if(currentItem.next() == null)
				{
					System.out.println("Current Item :: " + currentItem.getItemName());
					currentItem.setNextItem(shItem);
					shItem.setPreviousItem(currentItem);
					return true;
				}
				
				currentItem = currentItem.next();
			}
			else
			{
				System.out.println("Duplicate value cannot add");
				return false;
			}
		}
		
		return false;
	}
	
	private ListItem getItem(ListItem itemName)
	{
		ListItem currentItem = head;
		
		while(currentItem != null)
		{
			if(currentItem.getItemName().equals(itemName.getItemName()))
				return currentItem;
			
			currentItem = currentItem.next();
		}
		
		return null;
	}

	@Override
	public boolean removeItem(ListItem item) 
	{
		ListItem currentItem = head;
		ShoppingListItem shItem = (ShoppingListItem) getItem(item);
		
		if(currentItem == null)
		{
			System.out.println("Item not in list");
			return false;
		}
		
		while(currentItem != null)
		{
			if(shItem.compareTo(currentItem) == 0)
			{		
				if(currentItem.previous() == null)
				{				
					head = currentItem.next();
					currentItem = null;
					return true;
				} 
				else if(currentItem.next() == null)
				{
					currentItem.previous().setNextItem(null);
					currentItem = null;
					return true;
				}
				else
				{
					currentItem.previous().setNextItem(currentItem.next());
					currentItem.next().setPreviousItem(currentItem.previous());
					currentItem = null;
					return true;
				}
			}
			else
				currentItem = currentItem.next();
		}
		
		System.out.println("Item to remove not in list");
		return false;
	}

	@Override
	public void traverse(ListItem root)
	{
		ListItem currentItem = root;
		System.out.println("Printing Shopping List Items******************************");
		
		if(currentItem == null)
		{
			System.out.println("List is empty");
			return;
		}
		
		int i = 1;
		while(currentItem != null)
		{
			System.out.println("#"+i + " " + currentItem.getItemName());
			currentItem = currentItem.next();
			i++;
		}
			
	}
}
