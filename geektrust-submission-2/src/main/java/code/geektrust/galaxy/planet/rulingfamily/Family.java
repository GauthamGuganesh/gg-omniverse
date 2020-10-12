package code.geektrust.galaxy.planet.rulingfamily;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import code.geektrust.galaxy.planet.rulingfamily.utils.FamilyTreeInitializer;
import code.geektrust.galaxy.planet.rulingfamily.utils.GenderConstants;
import code.geektrust.galaxy.planet.rulingfamily.utils.RelationShipFetcher;
import code.geektrust.galaxy.planet.rulingfamily.utils.RelationshipFetcherFactory;
import code.geektrust.galaxy.planet.rulingfamily.utils.Result;

public class Family 
{
	private List<Person> familyMembers = new ArrayList<>();
	private static Family family;
	
	private Family()
	{
		FamilyTreeInitializer initializer = new FamilyTreeInitializer(this);
		
		try 
		{
			initializer.initializeFamilyTree();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
	
	public static Family createFamily()
	{
		if(family == null)
			family = new Family();
		
		return family;
	}
	
	public String addChildrenToFamily(String parentName, String childName, String childGender)
	{
		Person parent = findFamilyMember(parentName);
		
		if(parent == null) return Result.PERSON_NOT_FOUND;
		else if(parent.getGender().equals(GenderConstants.MALE)) return Result.CHILD_ADDITION_FAILED;
		else
		{
			Person child = parent.createChildren(childName, childGender);
			if(child != null) 
			{
				familyMembers.add(child);
				return Result.CHILD_ADDITION_SUCCEEDED;
			}
			else return Result.CHILD_ADDITION_FAILED;
		}
			
	}
	
	public String getRelationship(String name, String relationship)
	{
		Person person = findFamilyMember(name);
		if(person == null) return Result.PERSON_NOT_FOUND;
		else 
		{
			RelationShipFetcher fetcher = RelationshipFetcherFactory.getRelationShip(person, relationship);
			List<String> relations = fetcher.fetch();
			
			if(relations.isEmpty()) return Result.NONE;
			
			return String.join(" ", relations);
		}
	}
	
	public Person findFamilyMember(String name)
	{
		Person familyMember = null;
		
		for(Person member : familyMembers)
			if(name.equals(member.getPersonName()))
			{
				familyMember = member;
				break;
			}
		
		return familyMember;
	}
	
	public void addFamilyMember(Person person)
	{
		familyMembers.add(person);
	}
}
