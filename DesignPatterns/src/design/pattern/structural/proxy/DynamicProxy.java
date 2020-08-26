package design.pattern.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

interface Human
{
	void walk();
	void talk();
}

interface Animal
{
	void hunt();
}

class Lion implements Animal
{

	@Override
	public void hunt() {
		System.out.println("Hunting...");
	}
	
}

class Person implements Human
{

	@Override
	public void walk() {
		System.out.println("Walking...");
	}

	@Override
	public void talk() {
		System.out.println("Talking...");
	}
	
}

class LoggingHandler implements InvocationHandler //DynamicProxy implemented through InvocationHandler
{
	private Object target; //Target object on which to invoke methods
	private Map<String, Integer> calls = new HashMap<>(); 
	
	LoggingHandler(Object target)
	{
		this.target = target;
	}
	
	@Override //Invoked by proxy instance.
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		String methodName = method.getName();
		calls.merge(methodName, 1, Integer::sum);
		
		if(methodName.contains("toString"))
		{
			return calls.toString();
		}
		
		return method.invoke(target, args);
	}
	
}

public class DynamicProxy {

	//Creating and returning a proxy instance
	@SuppressWarnings("unchecked")
	public static<T> T getProxyInstanceForLogging(T target, Class<?> intf)
	{
		/*Returns a proxy instance of type interface(here 'Human'), for the target (usually the interface the target implements)
		 * The proxy instance dispatches method invocations to the invocation Handler(LoggingHandler here) .
		 */
		return (T) Proxy.newProxyInstance(intf.getClassLoader(), new Class<?>[] { intf }, new LoggingHandler(target));
	}
	
	public static void main(String[] args) {
		
		Human h = getProxyInstanceForLogging(new Person(), Human.class); //Target object here is 'new Person()'.
		
		h.walk(); // 'h' is a proxy instance. Method invocation dispatched to LoggingHandler.
		h.talk();
		h.walk();
		
		System.out.println(h.toString());
		
		Animal a = getProxyInstanceForLogging(new Lion(), Animal.class);
		a.hunt();
		a.hunt();
		a.hunt();
		
		System.out.println(a.toString());
	}
}







