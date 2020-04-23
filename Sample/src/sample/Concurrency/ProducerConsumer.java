package sample.Concurrency;

public class ProducerConsumer 
{
	public static void main(String[] args) 
	{
		Message message = new Message();
		Writer writer = new Writer(message);
		Reader reader = new Reader(message);
		
		new Thread(writer).start();
		new Thread(reader).start();		
	}
}

class Message 
{
	private String message;
	private boolean isEmpty = true;
	
	public synchronized void write(String message)
	{
		while(!isEmpty)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.message = message;
		isEmpty = false;
		notifyAll();
	}
	
	public synchronized String read()
	{
		while(isEmpty)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		isEmpty = true;
		notifyAll();
		return message;
	}
}

class Writer implements Runnable
{
	Message message;
	public Writer(Message message)
	{
		this.message = message;
	}
	
	@Override
	public void run()
	{
		String[] s = {"This is line 1", "This is line2", "This is line3", "This is line4"};
		
		for(int i = 0; i < s.length ; i++)
		{
			try
			{
				System.out.println(Thread.currentThread().getName() + " Writing message..." + s[i]);
				message.write(s[i]);
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		message.write("Finished");
	}
	
}

class Reader implements Runnable
{
	Message message;
	public Reader(Message message)
	{
		this.message = message;
	}
	
	@Override
	public void run() 
	{
		try 
		{
			String readMessage = "";
			while(!readMessage.equals("Finished"))
			{
				Thread.sleep(3000);
				readMessage = message.read();
				System.out.println(Thread.currentThread().getName() + " Reading message ..." + readMessage);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}




