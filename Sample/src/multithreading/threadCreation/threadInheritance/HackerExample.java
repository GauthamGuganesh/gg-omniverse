package multithreading.threadCreation.threadInheritance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HackerExample {
	public static int MAX_SIZE = 9999;
	
	public static void main(String[] args) {
		
		Random r = new Random();
		Vault v = new Vault(10);
		
		System.out.println("Vault key is :: " + v.key);
		
		List<Thread> threads = new ArrayList<>();
		threads.add(new AscendingHacker(v));
		threads.add(new DescendingHacker(v));
		threads.add(new PoliceThread());
		
		for(Thread t : threads)
			t.start();
	}
}

class Vault
{
	int key;
	
	Vault(int key)
	{
		this.key = key;
	}
	
	public boolean isCorrect(int key)
	{
		try 
		{
			Thread.sleep(500);
		} catch (InterruptedException e) {
			
		} //returning password after a delay to hinder hackers.
		
		return (this.key == key);
	}
}

abstract class Hacker extends Thread
{
	protected Vault vault;
	Hacker(Vault vault)
	{
		this.vault = vault;
	}
	
	@Override
	public void start()  //Overriding start method incase want to add any functionality before calling super.start()
	{
		System.out.println("Starting hacker thread :: " + this.getName());
		this.setPriority(Thread.MAX_PRIORITY);
		this.setName(this.getClass().getSimpleName());
		super.start(); //starts this thread. Since same start method to start any derived class of Thread, present in base class.
		//Don't get confused. Base class instance is equal to derived class instance if Hacker is instantiated.
	}
}

class AscendingHacker extends Hacker
{
	AscendingHacker(Vault vault) {
		super(vault);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < HackerExample.MAX_SIZE; i++)
		{
			if(vault.isCorrect(i))
			{
				System.out.println("Cracked Password :: "  + i + " :: " + this.getName());
				System.exit(0);
			}
		}
	}
	
}

class DescendingHacker extends Hacker
{
	DescendingHacker(Vault vault) {
		super(vault);
	}
	
	@Override
	public void run() {
		for(int i = HackerExample.MAX_SIZE; i >= 0; i--)
		{
			if(vault.isCorrect(i))
			{
				System.out.println("Cracked Password :: "  + i + " :: " + this.getName());
				System.exit(0); //Exits the whole application. Not just the thread.
			}
		}
	}
	
}

class PoliceThread extends Thread
{
	@Override
	public void run() {
		for(int i = 10 ; i > 0; i--)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			System.out.println(i + " seconds left");
		}
		
		System.out.println("Game over hackers");
		System.exit(0);
	}
}








