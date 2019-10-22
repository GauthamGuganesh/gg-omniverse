package sample.AbstractClassChallenge;

public class ShoppingTree implements ItemList
{
	private ListItem root;
	public ShoppingTree(ListItem root)
	{
		this.root = root;
	}
	
	@Override
	public ListItem getRoot() 
	{
		return this.root;
	}

	@Override
	public boolean addItem(ListItem listItem)
	{
		if(root == null)
		{
			root = listItem;
			return true;
		}
		
		ListItem currentItem = root;
		return addItemToTree(currentItem, listItem);
	}

	private boolean addItemToTree(ListItem currentItem, ListItem listItem)
	{	
		while(currentItem != null)
		{
			if(listItem.compareTo(currentItem) < 0) 
			{
				if(currentItem.previous() != null)
				{
					currentItem = currentItem.previous();
					addItemToTree(currentItem, listItem);
				}
				else
				{
					currentItem.setPreviousItem(listItem);
					return true;
				}
			}
			else if(listItem.compareTo(currentItem) > 0)
			{
				if(currentItem.next() != null)
				{
					currentItem = currentItem.next();
					addItemToTree(currentItem, listItem);
				}
				else
				{
					currentItem.setNextItem(listItem);
					return true;
				}
			}
			else
			{
				System.out.println("Duplicate values. Cannot add");
				return false;
			}
		}
		
		return false;
	}

	@Override
	public boolean removeItem(ListItem listItem)
	{
		ListItem currentItem = getRoot();
		ListItem parentItem = currentItem;
		
		if(currentItem == null)
		{
			System.out.println("List Empty");
			return false;
		}
		
		while(currentItem != null)
		{
			if(listItem.compareTo(currentItem) < 0)
			{
				parentItem = currentItem;
				currentItem = currentItem.previous();
			}
			else if(listItem.compareTo(currentItem) > 0)
			{
				parentItem = currentItem;
				currentItem = currentItem.next();
			}
			if(currentItem.compareTo(listItem) == 0)
			{
				return performRemoval(parentItem, currentItem);			
			}
		}
		
		return false;
	}

	private boolean performRemoval(ListItem parentItem, ListItem currentItem)
	{
		//Deletion with leaf node
		if(currentItem.next() == null && currentItem.previous() == null)
		{
			currentItem = null;
			return true;
		}
		
		//Deletion with one node. Left tree empty.
		if(currentItem.previous() == null)
		{
			// If current node a right child
			if(currentItem == parentItem.next())
				parentItem.setNextItem(currentItem.next());
			
			else if(currentItem == parentItem.previous()) // If current node a left child
				parentItem.setPreviousItem(currentItem.next());
			
			else // Parent item is equal to the currenItem. Parent is the root.
				currentItem = parentItem.next();
			
			return true;
		} 
		//Deletion with one node. Right tree empty.
		else if(currentItem.next() == null)
		{
			// If current Node a left child
			if(currentItem == parentItem.previous())
				parentItem.setPreviousItem(currentItem.previous());
			
			else if(currentItem == parentItem.next())  //If current Node a right child
				parentItem.setNextItem(currentItem.previous());
			
			else
				currentItem = parentItem.previous();
			
			return true;
		}
		else
			// Deletion with two nodes
		{
			// Finding minimum number of right subtree.
			ListItem tobeDeletedNode = currentItem;
			ListItem parentOfminimumNode = currentItem;
			currentItem = currentItem.next(); // Finding minimum node in right sub-tree
			
			while(currentItem.previous() != null)
			{
				parentOfminimumNode = currentItem;
				currentItem = currentItem.previous();
			}
			
			// Setting minimum value from minimum node to deletion node.
			tobeDeletedNode.setItemName(currentItem.getItemName());
			
			//Deleting the minimum node
			if(parentOfminimumNode == tobeDeletedNode)
			{
				tobeDeletedNode.setNextItem(currentItem.next());
				parentOfminimumNode = null;
				return true;
			}
			else
			{
				parentOfminimumNode.setPreviousItem(currentItem.next());
				currentItem = null;
				return true;
			}				
		}
	}

	@Override
	public void traverse(ListItem root) 
	{
		ListItem currentItem = root;
		
		if(currentItem != null)
		{
			//Inorder traversal (left, root, right)
			traverse(currentItem.previous());
			System.out.println("Item Name ## " + currentItem.getItemName());
			traverse(currentItem.next());
		}
	}

}
