package sample.SetChallenge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main
{
    private static Map<String, HeavenlyBody> solarSystem = new HashMap<>();
    private static Set<HeavenlyBody> planets = new HashSet<>();
    
    public static void main(String[] args) {
        HeavenlyBody temp = new Planet("Mercury", 88);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        temp = new Planet("Venus", 225);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        temp = new Planet("Earth", 365);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        HeavenlyBody tempMoon = new Moon("Moon", 27);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon);

        temp = new Planet("Mars", 687);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        tempMoon = new Moon("Deimos", 1.3);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Mars

        tempMoon = new Moon("Phobos", 0.3);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Mars

        temp = new Planet("Jupiter", 4332);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        tempMoon = new Moon("Io", 1.8);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Jupiter

        tempMoon = new Moon("Europa", 3.5);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Jupiter

        tempMoon = new Moon("Ganymede", 7.1);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Jupiter

        tempMoon = new Moon("Callisto", 16.7);
        solarSystem.put(tempMoon.getHeavenlyBodyIdentifier(), tempMoon);
        temp.addMoon(tempMoon); // temp is still Jupiter

        temp = new Planet("Saturn", 10759);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        temp = new Planet("Uranus", 30660);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        temp = new Planet("Neptune", 165);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);

        temp = new Planet("Pluto", 248);
        solarSystem.put(temp.getHeavenlyBodyIdentifier(), temp);
        planets.add(temp);
        
        HeavenlyBody pluto2 = new Moon("Pluto", 842);
        HeavenlyBody neptune = solarSystem.get("NeptunePLANET");
        neptune.addMoon(pluto2);
        solarSystem.put(pluto2.getHeavenlyBodyIdentifier(), pluto2);
        
        Planet pluto3 = new Planet("Pluto", 453);
        planets.add(pluto3);
        solarSystem.put(pluto3.getHeavenlyBodyIdentifier(), pluto3);

        System.out.println("Planets");
        for(HeavenlyBody planet : planets) {
            System.out.println("\t" + planet.getName());
        }

        Set<HeavenlyBody> moons = new HashSet<>();
        moons.add(pluto3);
        Moon pluto4 = new Moon("Pluto", 345);
        moons.add(pluto4);
        for(HeavenlyBody planet : planets) {
            moons.addAll(planet.getSatellites());
        }

       
        
        System.out.println("All Moons");
        for(HeavenlyBody moon : moons) {
            System.out.println("\t" + moon.getName());
        }

        for(HeavenlyBody body : planets)
        {
        	System.out.println("Planet Name :: " + body.getName());
	        for(HeavenlyBody satellite: body.getSatellites()) {
	            System.out.println("\t" + satellite.getName());
	        }
        }
        
        System.out.println("All bodies in solar system");
        
        Set<String> keySet = solarSystem.keySet();
        
        for(String key : keySet)
        {
        	System.out.println("## "+ key + " : " + solarSystem.get(key).getName());
        }
        
        HeavenlyBody a = new Planet("A", 5);
        HeavenlyBody ab = new Planet("B", 5);
        System.out.println(a.equals(ab));
        System.out.println(ab.equals(a));
    }
}
