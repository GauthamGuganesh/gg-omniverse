package sample.SetChallenge;

public class Planet extends HeavenlyBody
{

	public Planet(String name, double orbitalPeriod)
	{
		super(name, orbitalPeriod, HeavenlyBody.BodyType.PLANET);
	}
	
	@Override
	public boolean addMoon(HeavenlyBody moon) 
	{
		if(moon.getBodyType().equals(HeavenlyBody.BodyType.MOON.toString()))
		{
			super.addMoon(moon);
			return true;
		}
		else
			System.out.println(moon.getName() + " :: Not a moon. It's a " + moon.getBodyType());
		
		return false;
	}

}
