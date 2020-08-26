package design.pattern.behavioural.command;

import design.pattern.behavioural.command.BankAccountCommand.Action;

class BankAccount
{
	int balance;
	
	public boolean withdraw(int amount)
	{
		if(balance - amount >= 0)
		{
			balance -= amount;
			return true;
		}
		
		return false;
	}
	
	public boolean deposit(int amount)
	{
		balance += amount;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [balance=" + balance + "]";
	}
	
}

interface Command
{
	public void call();
}

class BankAccountCommand implements Command
{
	public boolean success;
	private BankAccount account;
	private Action action;
	private int amount;
	
	public BankAccountCommand(BankAccount account, Action action, int amount) {
		this.account = account;
		this.action  = action;
		this.amount  = amount;
	}
	
	public enum Action
	{
		DEPOSIT,
		WITHDRAWAL
	}

	@Override
	public void call() {
		switch(action.toString())
		{
			case "DEPOSIT":
				success = account.deposit(amount);
				break;
			case "WITHDRAWAL":
				success = account.withdraw(amount);
				break;
			default:
				System.out.println("Invalid Action");
		}
	}
	
}

public class BankAccountDemo {

	public static void main(String[] args) {
		BankAccount acc = new BankAccount();
		
		Command c  = new BankAccountCommand(acc, Action.DEPOSIT, 1000);
		c.call();
		System.out.println(acc);
		Command c1 = new BankAccountCommand(acc, Action.WITHDRAWAL, 1000);
		c1.call();
		System.out.println(acc);
	}
}





