package sample.AdventureGameChallenge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Locations implements Map<Integer, Location>
{
	private static Map<Integer, Location> locationsMap = new HashMap<Integer, Location>();
	
	@Override
	public int size() 
	{
		return locationsMap.size();
	}

	@Override
	public boolean isEmpty()
	{
		return locationsMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) 
	{
		return locationsMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) 
	{
		return locationsMap.containsValue(value);
	}

	@Override
	public Location get(Object key) 
	{
		return locationsMap.get(key);
	}

	@Override
	public Location put(Integer key, Location value)
	{
		return locationsMap.put(key, value);
	}

	@Override
	public Location remove(Object key)
	{
		return locationsMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Location> m) 
	{
		locationsMap.putAll(m);
	}

	@Override
	public void clear() 
	{
		locationsMap.clear();
	}

	@Override
	public Set<Integer> keySet() 
	{
		return locationsMap.keySet();
	}

	@Override
	public Collection<Location> values() 
	{
		return locationsMap.values();
	}

	@Override
	public Set<Entry<Integer, Location>> entrySet() 
	{
		return locationsMap.entrySet();
	}
	
	@Override
	public String toString()
	{
		for(Location loc : locationsMap.values())
		{
			System.out.println("Location ID :: " + loc.getLocationID());
			System.out.println("Description :: " + loc.getDescription());
		}
		
		return "done";
	}
	
	public void write() throws IOException
	{
		try(BufferedWriter br = new BufferedWriter(new FileWriter("locations.txt")))
		{
			for(Location loc : locationsMap.values())
			{
				int locId = loc.getLocationID();
				String description = loc.getDescription();
				
				br.write(locId + "," + description + "\n");
			}
		}	
	}
	
	public void read() throws FileNotFoundException, IOException
	{
		try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("locations.txt"))))
		{
			scanner.useDelimiter(",");
			while(scanner.hasNext())
			{
				int locId = Integer.parseInt(scanner.next());
				scanner.skip(",");
				String description = scanner.nextLine();
				locationsMap.put(locId, new Location(locId, description));
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		Locations locations = new Locations();
		locations.read();
		locations.toString();
	}

}
