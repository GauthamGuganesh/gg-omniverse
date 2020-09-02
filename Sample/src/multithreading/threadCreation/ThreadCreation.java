package multithreading.threadCreation;

public class ThreadCreation {
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("We are now in :: " + Thread.currentThread().getName());		
				System.out.println("Current Thread Priority : " + Thread.currentThread().getPriority() );
				throw new RuntimeException("Intentional error");
			}
		}); //1 way to create a Thread.
		
		thread.setName("Misbehaving thread");
		thread.setPriority(Thread.MAX_PRIORITY); // Static priority setting by programmer. (used to calculate dynamic priority).
		//0-10 can be given.
		
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Unknown error occured in " + t.getName() + " Error : " + e.getMessage());
			}
		}); //Exception handler
		
		System.out.println("We are in :: " + Thread.currentThread().getName() + " before starting new thread");
		thread.start();
		System.out.println("We are in :: " + Thread.currentThread().getName() + " after starting new thread");
		
		Thread newThread = new NewThread();
		newThread.start();
		
	}

	private static class NewThread extends Thread
	{
		@Override
		public void run() {
			System.out.println("We are now in :: " + Thread.currentThread().getName());		
			System.out.println("Current Thread Priority : " + Thread.currentThread().getPriority() );
		}
	} // Another way to create thread. Instead of runnable we just extend Thread because Thread itself implements Runnable.
	
}



