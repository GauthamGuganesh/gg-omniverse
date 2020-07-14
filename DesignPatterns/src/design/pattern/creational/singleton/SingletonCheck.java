package design.pattern.creational.singleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Singleton implements Serializable {
	
	private static Singleton instance;
	private int value;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private Singleton()
	{
		
	}
	
	static 
	{
		//Can use try catch and handle exceptions from constructor etc.
		//Static blocks executed only once in lifetime.
		
		instance = new Singleton(); 
	}
	
	public static Singleton getInstance()
	{
		//If initialized in a static block, can handle certain situations even before object creation.
		// hence can be used to do some pre-processing here.
		return instance;
	}
	
	/* Solves the serialization problem in singleton pattern. When deserializing new object is constructed,
	 * regardless of private constructor. readResolve resolves the new object into the instance we need.
	 */
	protected Object readResolve()
	{
		return instance;
	}

}


public class SingletonCheck
{
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		
		Singleton object = Singleton.getInstance(); 
		
		String fileName = "/Users/gauthamguganesh/git/gg-omniverse/DesignPatterns/src/Singleton/file.txt";
		
		try(FileOutputStream fio = new FileOutputStream(fileName);
		    ObjectOutputStream oo = new ObjectOutputStream(fio))
		{
			object.setValue(111);
			oo.writeObject(object);
		}
		
		object.setValue(222);
		
		try(FileInputStream fis = new FileInputStream(fileName); 
	     	ObjectInputStream oi = new ObjectInputStream(fis))
		{
			Singleton instance = (Singleton )oi.readObject();
			
			System.out.println(instance == object);
			System.out.println(object.getValue());
			System.out.println(instance.getValue());
		}
	}
}




