package sample.SetChallenge;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dev on 12/01/2016.
 */
public class HeavenlyBody {
    private final String name;
    private final String heavenlyBodyIdentifier;
    private final double orbitalPeriod;
    private final Set<HeavenlyBody> satellites;
    private final String bodyType;
    
    enum BodyType
    {
    	MOON,
    	STAR,
    	PLANET
    }
   
    public HeavenlyBody(String name, double orbitalPeriod, BodyType bodyType) 
    {
        this.name = name;
        this.orbitalPeriod = orbitalPeriod;
        this.bodyType = bodyType.toString();
        this.heavenlyBodyIdentifier = this.name + this.bodyType;
        this.satellites = new HashSet<>();
    }
    
    public String getHeavenlyBodyIdentifier()
    {
    	return heavenlyBodyIdentifier;
    }

    public String getName() {
        return name;
    }
    
    public String getBodyType()
    {
    	return bodyType;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public boolean addMoon(HeavenlyBody moon) {
        return this.satellites.add(moon);
    }

    public Set<HeavenlyBody> getSatellites() {
        return new HashSet<>(this.satellites);
    }


    @Override
    public final boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        
        HeavenlyBody hb = (HeavenlyBody) obj;
        
        if ((obj == null) || this.bodyType != hb.bodyType) {
            return false;
        }

        String objName = ((HeavenlyBody) obj).getName();
        return this.name.equals(objName);
    }

    @Override
    public final int hashCode() {
        return this.heavenlyBodyIdentifier.hashCode() + 57;
    }
}
