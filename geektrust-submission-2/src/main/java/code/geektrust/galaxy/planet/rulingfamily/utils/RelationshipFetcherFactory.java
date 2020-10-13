package code.geektrust.galaxy.planet.rulingfamily.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import code.geektrust.galaxy.planet.rulingfamily.Person;


public class RelationshipFetcherFactory 
{
	private RelationshipFetcherFactory() {}
	
	private static BiFunction<List<Person>, String, List<String>> genderFilter = (list, gender) -> {
		
		List<String> filteredList = new ArrayList<String>();
		
		for(Person listItem : list)
			if(listItem.getGender().equals(gender))
				filteredList.add(listItem.getPersonName());
		
		return filteredList;
	};
	
	private static BiFunction<Person, String, List<String>> getParentalRelations = (parent, relationGender) -> {
		
		return genderFilter.apply(parent.getSiblings(), relationGender);
	};
	
	private static BiFunction<Person, String, List<String>> getInLaws = (person, inLawGender) -> {
		
		List<String> inLaws = new ArrayList<String>();
		
		if(person.getSpouse() != null)
			inLaws.addAll(genderFilter.apply(person.getSpouse().getSiblings(), inLawGender));
		
		inLaws.addAll(genderFilter.apply(person.getSiblings().stream()
																.map(x -> x.getSpouse())
																.filter(x -> x != null)
																.collect(Collectors.toList()), inLawGender));
		
		return inLaws;
	};
	
	public static RelationShipFetcher getRelationShip(Person person, String relationship)
	{		
		switch(relationship)
		{
			case RelationshipConstants.PATERNAL_UNCLE:
				return () -> {
					return getParentalRelations.apply(person.getFather(), GenderConstants.MALE);
				};
				
			case RelationshipConstants.MATERNAL_UNCLE:
				return () -> {
					return getParentalRelations.apply(person.getMother(), GenderConstants.MALE);
				};
				
			case RelationshipConstants.PATERNAL_AUNT:
				return () -> {
					return getParentalRelations.apply(person.getFather(), GenderConstants.FEMALE);
				};
				
			case RelationshipConstants.MATERNAL_AUNT:
				return () -> {
					return getParentalRelations.apply(person.getMother(), GenderConstants.FEMALE);
				};
				
			case RelationshipConstants.SISTER_IN_LAW:
				return () -> {
					return getInLaws.apply(person, GenderConstants.FEMALE);
				};
				
			case RelationshipConstants.BROTHER_IN_LAW:
				return () -> {
					return getInLaws.apply(person, GenderConstants.FEMALE);
				};
				
			case RelationshipConstants.SON:
				return () -> {
					return genderFilter.apply(person.getChildren(), GenderConstants.MALE);
				};
				
			case RelationshipConstants.DAUGHTER:
				return () -> {
					return genderFilter.apply(person.getChildren(), GenderConstants.FEMALE);
				};
				
			case RelationshipConstants.SIBLINGS:
				return () -> {
					return person.getSiblings().stream().map(x -> x.getPersonName()).collect(Collectors.toList());
				};
				
			default:
				return () -> {
					return Collections.singletonList("NONE");
				};
		}
	}
}

