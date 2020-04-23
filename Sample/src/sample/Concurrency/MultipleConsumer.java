package sample.Concurrency;

import java.util.ArrayList;
import java.util.List;

public class MultipleConsumer
{
	public static void main(String[] args) 
	{
		List<String> buffer = new ArrayList<>();
		MyProducer producer = new MyProducer(buffer);
		MyConsumer consumer = new MyConsumer(buffer);
		MyConsumer consumer2 = new MyConsumer(buffer);
		
		new Thread(producer).start();
		new Thread(consumer).start();		
		new Thread(consumer2).start();
	}
}

class MyProducer implements Runnable
{
	List<String> buffer;
	
	public MyProducer(List<String> buffer)
	{
		this.buffer = buffer;
	}
	
	@Override
	public void run() 
	{
		String[] message = {"num1", "num2", "num3", "num4"};
		
		for(int i = 0 ; i < message.length ; i++)
		{
			System.out.println("Adding...." + message[i]);
			synchronized(buffer)
			{
				buffer.add(message[i]);
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		synchronized(buffer) 
		{
			buffer.add("Finish");
		}
	}
}



class MyConsumer implements Runnable
{
	List<String> buffer;
	
	public MyConsumer(List<String> buffer)
	{
		this.buffer = buffer;
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			synchronized(buffer)
			{
				if(buffer.isEmpty())
					continue;
				if(buffer.get(0).equals("Finish"))
					break;
				
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " Removing ..." + buffer.remove(0));
			}
		}
	}
	
}