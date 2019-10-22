package sample.AbstractClassChallenge;

public interface ItemList
{
	ListItem getRoot();
	boolean addItem(ListItem listItem);
	boolean removeItem(ListItem listItem);
	void traverse(ListItem root);
}
