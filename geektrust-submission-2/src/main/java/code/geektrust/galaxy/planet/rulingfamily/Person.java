package code.geektrust.galaxy.planet.rulingfamily;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import code.geektrust.galaxy.planet.rulingfamily.utils.GenderConstants;

public class Person 
{
	private String personName;
	private String gender;
	private Person father;
	private Person mother;
	private Person spouse;
	private boolean isMale;
	private final List<Person> children = new ArrayList<Person>();
	
	public Person(String personName, String gender)
	{
	    this(null, null, personName, gender);
	}
	
	public Person(Person father, Person mother, String personName, String gender)
	{
		this.father = father;
		this.mother = mother;
		this.personName = personName;
		this.gender = gender;
		this.isMale = gender.equals(GenderConstants.MALE) ? true : false;
	}
	
	public void marry(Person spouse)
	{
		this.spouse = spouse;
		spouse.spouse = this;
	}
	
	public Person createChildren(String name, String childGender)
	{
		if(spouse == null)
			return null;
		
		Person child = new Person(spouse, this, name, childGender);
		children.add(child);
		spouse.children.add(child);
		return child;
	}
	
	public String getPersonName()
	{
		return personName;
	}
	
	public boolean isMale()
	{
		return isMale;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public Person getFather()
	{
		return father;
	}
	
	public Person getMother()
	{
		return mother;
	}
	
	public Person getSpouse()
	{
		return spouse;
	}
	
	public List<Person> getSiblings()
	{
		if(father == null) return Collections.emptyList();
		
		List<Person> siblings = new ArrayList<Person>();		
		List<Person> allChildren = father.getChildren();
		
		for(Person child : allChildren)
			if(!child.equals(this))
				siblings.add(child);
		
		return Collections.unmodifiableList(siblings);
	}
	
	public List<Person> getChildren()
	{
		return Collections.unmodifiableList(children);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personName == null) ? 0 : personName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (personName == null) {
			if (other.personName != null)
				return false;
		} else if (!personName.equals(other.personName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String fatherName = Optional.ofNullable(father).isPresent() ? father.getPersonName() : "";
		String motherName = Optional.ofNullable(mother).isPresent() ? mother.getPersonName() : "";
		String spouseName = Optional.ofNullable(spouse).isPresent() ? spouse.getPersonName() : "";
		
		return getClass().getSimpleName() + " = [Name = " + personName + ", Father = " + fatherName + ", Mother = " + motherName + ", Spouse = " + spouseName + ", Gender = " + gender + "]"; 
	}
}
