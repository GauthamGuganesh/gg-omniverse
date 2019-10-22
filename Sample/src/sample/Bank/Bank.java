package sample.Bank;

import java.util.ArrayList;
import java.util.List;

public class Bank
{
	private String bankName;
	private List<Branch> branches;
	
	public Bank(String bankName)
	{
		this.bankName = bankName;
		branches = new ArrayList<Branch>();
	}

	public void addNewBranch(String branch)
	{
		Branch createBranch = Branch.createBranch(branch);
		branches.add(createBranch);
	}
	
	public void addNewCustomer(String branchName, String customerName, Double transactionAmount)
	{
		Branch branch = getBranch(branchName);
		branch.addCustomer(customerName, transactionAmount);
	}
	
	public void addNewTransactionToExistingCustomer(String branchName, String customerName, Double transaction)
	{
		Branch branch = getBranch(branchName);
		branch.addTransactionToCustomer(customerName, transaction);	
	}
	
	private Branch getBranch(String branchName)
	{
		for(int i = 0 ; i < branches.size() ; i++)
		{
			String name = branches.get(i).getBranchName();
			if(name != null)
			{
				if(name.equals(branchName))
				{
					Branch branch = branches.get(i);
					return branch;
				}
			}
		}
		
		return null;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
