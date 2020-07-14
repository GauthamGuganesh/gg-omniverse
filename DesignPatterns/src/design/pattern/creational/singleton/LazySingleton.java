package design.pattern.creational.singleton;

//Java doesn't allow you to create top-level static classes; only nested (inner) static classes.
public class LazySingleton {
	
	private static LazySingleton lazy;
	private LazySingleton()
	{
		
	}
	
	//This is not thread-safe. Using synchronization is demanding on performance.
	//So try double-checked lock.
	
//	public static LazySingleton getInstance()
//	{
//		//Creating object only when asked. => This is lazy singleton.
//		
//		if(lazy == null) { lazy = new LazySingleton(); };
//		
//		return lazy;
//	}
	
	//double check lock. Thread safe.
	
	public static LazySingleton getInstance()
	{
		if(lazy == null)
		{
			//Class level lock.
			synchronized (LazySingleton.class) {
				
				if(lazy == null) lazy = new LazySingleton();
				
			}
		}
		
		return lazy;
	}
	
	public static LazySingleton newInstance()
	{
		return Impl.instance;
	}
	
	// Or can use innner-static class to create singleton. It is thread-safe. Inner classes can access private members of outer.
	// Inner-static class can only access STATIC members of OUTER class.
	// => because inner-static-class can be created without outer class instance. 
	private static class Impl
	{
		private static final LazySingleton instance = new LazySingleton();
		/*
		 * 
		 * 
		 * Inner-class inside access:
		 * --------------------------
		 * inner-static class can have static,non-static members.
		 * inner-nonstatic-class can have only non-static members.
		 * 
		 * 
		 * 
		 * Inner-class outside access:
		 * ---------------------------
		 * inner-static class can access ONLY static members of outer class.
		 * innter-nonstatic-class can access ANY member of outer class.
		 * 
		 * 
		 */
	}
	
	public static void main(String[] args) {
		
		LazySingleton lazy = LazySingleton.getInstance();
		System.out.println(lazy);
	}

}
