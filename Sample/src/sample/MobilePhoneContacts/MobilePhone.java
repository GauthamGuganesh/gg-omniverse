package sample.MobilePhoneContacts;

import java.util.ArrayList;
import java.util.Scanner;

public class MobilePhone
{
	private ArrayList<Contacts> contacts;
	
	public MobilePhone()
	{
		contacts = new ArrayList<Contacts>();
	}
	
	public void addContact(Contacts contact)
	{
		contacts.add(contact);
	}
	
	public void removeContact(Contacts name)
	{
		int position = findContact(name.getName());
		contacts.remove(position);
	}
	
	public Contacts queryContact(String name)
	{
		int position = findContact(name);
		
		if(position != -1)
			return contacts.get(position);
		
		return null;
	}
	
	public void updateContact(Contacts newContact, Contacts oldContact)
	{
		int index = findContact(oldContact.getName());
		if(index != -1)
			contacts.set(index, newContact);
		else
			System.out.println("Update denied. Specified contact name unavailable to update");
	}
	
	private int findContact(String contactName)
	{
		for(int i = 0; i < contacts.size() ; i++)
		{
			Contacts tempContact = contacts.get(i);
			if(tempContact.getName().equals(contactName))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public void printContactList()
	{
		if(contacts.size() != 0)
			for(int i = 0 ; i < contacts.size() ; i++)
			{
				System.out.println("## Name   " + contacts.get(i).getName());
				System.out.println("## Number " + contacts.get(i).getNumber());
			}
		else
			System.out.println("Contact List Empty");
	}
	
	public static void main(String[] args) 
	{
		MobilePhone mobilePhone = new MobilePhone();
		boolean flag = true;
		Scanner scanner = new Scanner(System.in);
		while(flag)
		{
			printOptions();
			System.out.println("Enter your choice ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice)
			{
			case 1:
				System.out.println("Enter contact name/number to add : \n");
				String name = scanner.nextLine();
				String number = scanner.nextLine();
				mobilePhone.addContact(Contacts.createContact(name, number));
				break;
			case 2:
				System.out.println("Enter contact name to remove : \n");
				String remove = scanner.nextLine();
				Contacts queryContact = mobilePhone.queryContact(remove);
				mobilePhone.removeContact(queryContact);
				break;
			case 3:
				System.out.println("Enter contact name to update \n ");
				String oldContact = scanner.nextLine();
				Contacts querContacts = mobilePhone.queryContact(oldContact);
				System.out.println("Enter new contact name/number");
				String newContact = scanner.nextLine();
				String newNumber = scanner.nextLine();
				mobilePhone.updateContact(Contacts.createContact(newContact, newNumber), querContacts);
				break;
			case 4:
				System.out.println("Enter contact name to search \n");
				String search = scanner.nextLine();
				if(mobilePhone.queryContact(search) != null)
					System.out.println("Contact present in List");
				else
					System.out.println("Contact unavailable");
				break;
			case 5:
				mobilePhone.printContactList();
				break;
			case 6:
				flag = false;
				break;	
			}
		}
		scanner.close();
	}

	private static void printOptions() 
	{
		System.out.println("1. Add Contacts \n ");
		System.out.println("2. Remove Contacts \n ");
		System.out.println("3. Update Contacts \n ");
		System.out.println("4. Search Contacts \n ");
		System.out.println("5. Print Contacts \n ");
		System.out.println("6. Quit \n ");
	}
}
