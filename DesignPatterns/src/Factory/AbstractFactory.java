package Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

interface HotDrink
{
	public void consume();
}

interface HotDrinkFactory
{
	public HotDrink prepare();
}

class Tea implements HotDrink
{

	@Override
	public void consume() {
		System.out.println("Tea is delicious");
	}
	
}

class Coffee implements HotDrink
{

	@Override
	public void consume() {
		System.out.println("Coffee is delicious");
	}
	
}

class TeaFactory implements HotDrinkFactory
{

	@Override
	public HotDrink prepare() {
		System.out.println("Preparing tea");
		return new Tea();
	}
	
}

class CoffeeFactory implements HotDrinkFactory
{

	@Override
	public HotDrink prepare() {
		System.out.println("Preparing Coffee");
		return new Coffee();
	}
	
}

class DrinkMachine
{
	List<Pair<String, HotDrinkFactory>> factories;
	
	public DrinkMachine()
	{
		factories = new ArrayList<>();
	}
	
	public void showAllDrinks() throws Exception
	{
		//org.Reflections needs => java-assisst.jar
		//                      => guava.jar
		//                      => reflections 0.9.11.jar. 0.9.12 has a bug.
		Set<Class<? extends HotDrinkFactory>> subTypes = new Reflections("Factory").getSubTypesOf(HotDrinkFactory.class);
		
		System.out.println("Available drinks");
	
		for(Class<? extends HotDrinkFactory> cl : subTypes)
		{
			factories.add(new Pair<>(cl.getSimpleName(), cl.newInstance()));
			System.out.println(cl.getSimpleName().replace("Factory", ""));
		}	
	}
	
	public void consumeDrinks() {
		
		for(Pair<String,HotDrinkFactory> p : factories)
		{
			HotDrinkFactory hotdrink = p.getValue(p.getKey());
			hotdrink.prepare();
		}
	}
	
	
}

class Pair<K, V>
{
	K key;
	V value;
	
	Pair(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public V getValue(K key)
	{
		return value;
	}
	
	public K getKey()
	{
		return key;
	}
}

public class AbstractFactory 
{
	public static void main(String[] args)  {
		
		DrinkMachine drinks = new DrinkMachine();
		try {
			drinks.showAllDrinks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		drinks.consumeDrinks();
		
	}
	
}






