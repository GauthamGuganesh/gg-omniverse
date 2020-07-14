package design.pattern.creational.singleton;

import java.util.HashMap;
import java.util.Map;

enum Type
{
	PRIMARY,
	AUX,
	FALLBACK; //Only a total of three instances can be created. This is multiton.
}

class Printer{
	
	private static Map<Type, Printer> map = new HashMap<>();
	
	private Printer() {}
	
	public static Printer get(Type type)
	{
		if(map.containsKey(type))
			return map.get(type);
		
		Printer p = new Printer(); //Lazy-loading. Creating object only when asked.
		map.put(type, p);
		return p;
	}
	
}

public class Multiton {
	
	public static void main(String[] args) {
		
		Printer printer = Printer.get(Type.PRIMARY);
		Printer printer2 = Printer.get(Type.AUX);
		Printer printer3 = Printer.get(Type.FALLBACK);
		
		//Now even if we get printer of type AUX again, the same instance is returned and not new one.
		
		
		Printer printer4 = Printer.get(Type.AUX);
		
		// printer4 == printer2. This is multiton.
	}

}

