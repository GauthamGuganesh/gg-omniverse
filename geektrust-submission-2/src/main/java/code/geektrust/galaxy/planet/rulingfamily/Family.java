package code.geektrust.galaxy.planet.rulingfamily;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import code.geektrust.galaxy.planet.rulingfamily.utils.FamilyTreeInitializer;
import code.geektrust.galaxy.planet.rulingfamily.utils.RelationShipFetcher;
import code.geektrust.galaxy.planet.rulingfamily.utils.RelationshipFetcherFactory;
import code.geektrust.galaxy.planet.rulingfamily.utils.Result;

//If has only private constructor, the class should be declared "final".
//Since, if having private constructor means, there is no scope for inheritance. (derived classes need 'super()' access.
//Hence by declaring final we ensure classes with private constructors alone are not subclassed.

public final class Family 
{
	private final List<Person> familyMembers = new ArrayList<>();
	private static Family family = new Family();
	
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
	
	public static Family retrieve()
	{
		return family;
	}
	
	public String addChildrenToFamily(String parentName, String childName, String childGender)
	{
		Optional<String> result = Optional.ofNullable(findFamilyMember(parentName))
										   .flatMap(value -> {
												return value.isMale() ? Optional.of(Result.CHILD_ADDITION_FAILED) :
																		Optional.of("");
											});
		
		return result.isEmpty() ? Result.PERSON_NOT_FOUND 
								: result.get().isBlank() ? createAndAddChild(parentName, childName, childGender)
														 : result.get();
		
	}

	private String createAndAddChild(String parentName, String childName, String childGender) 
	{
		Optional<Person> child = Optional.ofNullable(findFamilyMember(parentName).createChildren(childName, childGender));
		child.ifPresent(value -> family.addFamilyMember(value));
		
		return child.isEmpty() ? Result.CHILD_ADDITION_FAILED : Result.CHILD_ADDITION_SUCCEEDED;
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
