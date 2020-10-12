package code.geektrust.galaxy.planet.rulingfamily.utils;

import java.util.List;

@FunctionalInterface
public interface RelationShipFetcher 
{
	public List<String> fetch();
}
