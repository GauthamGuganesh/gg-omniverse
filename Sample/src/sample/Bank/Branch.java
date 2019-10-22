package sample.Bank;

import java.util.ArrayList;
import java.util.List;

public class Branch 
{
	private String branchName;
	private List<Customer> customerList;
	
	private Branch(String name)
	{
		this.branchName = name;
		customerList = new ArrayList<Customer>();
	}
	
	protected void addCustomer(String customerName, Double transaction)
	{
		Customer createCustomer = Customer.createCustomer(customerName, transaction);
		customerList.add(createCustomer);
	}
	
	protected void addTransactionToCustomer(String customerName, Double transaction)
	{
		Customer name = getCustomer(customerName);
		
		if(name != null)
			name.addTransaction(transaction);
			
		else
			System.out.println("Customer not present in records");
		
	}
	
	protected Customer getCustomer(String customerName)
	{
		for(int i = 0 ; i < customerList.size() ; i++)
		{
			String name = customerList.get(i).getName();
			if(name != null)
			{
				if(name.equals(customerName))
				{
					Customer customer = customerList.get(i);
					return customer;
				}
			}
		}
		
		return null;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
	protected static Branch createBranch(String branchName)
	{
		Branch newBranch = new Branch(branchName);
		return newBranch;
	}
}
