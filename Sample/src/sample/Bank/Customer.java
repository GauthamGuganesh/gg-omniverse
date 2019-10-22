package sample.Bank;

import java.util.ArrayList;
import java.util.List;

public class Customer
{
	private String name;
	private List<Double> transactions;
	
	private Customer(String name)
	{
		this.name = name;
		transactions = new ArrayList<Double>();
	}
	
	protected void addTransaction(double transactionAmount)
	{
		transactions.add(transactionAmount);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Double> transactions) {
		this.transactions = transactions;
	}
	
	protected static Customer createCustomer(String customerName, Double transaction)
	{
		Customer customer = new Customer(customerName);
		customer.addTransaction(transaction);
		return customer;
	}
}
