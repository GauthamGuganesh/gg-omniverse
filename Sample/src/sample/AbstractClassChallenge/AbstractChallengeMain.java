package sample.AbstractClassChallenge;

public class AbstractChallengeMain
{
	public static void main(String[] args) 
	{
		ShoppingTree list = new ShoppingTree(null);
	//	list.traverse(list.getRoot());
		
		ListItem item1 = new ShoppingListItem("Cabbage", null, null);
		ListItem item2 = new ShoppingListItem("Tomato", null, null);
		ListItem item3 = new ShoppingListItem("Asparagus", null, null);
		ListItem item4 = new ShoppingListItem("Beetroot", null, null);
		ListItem item5 = new ShoppingListItem("Milk", null, null);
		ListItem item6 = new ShoppingListItem("Urulai", null, null);
		
		boolean addItem = list.addItem(item1);
		System.out.println("Addition status :: " + addItem);
		
		addItem = list.addItem(item2);
		System.out.println("Addition status :: " + addItem);
	
		addItem = list.addItem(item3);
		System.out.println("Addition status :: " + addItem);
		
		addItem = list.addItem(item4);
		System.out.println("Addition status :: " + addItem);
		
//		addItem = list.addItem(item5);
//		System.out.println("Addition status :: " + addItem);
		
		list.addItem(item6);
		
		
		list.traverse(list.getRoot());
		
		list.removeItem(item1);
		System.out.println("");
		list.traverse(list.getRoot());
		
	}
}
