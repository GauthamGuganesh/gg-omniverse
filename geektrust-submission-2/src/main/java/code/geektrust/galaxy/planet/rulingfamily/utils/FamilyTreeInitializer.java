package code.geektrust.galaxy.planet.rulingfamily.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import code.geektrust.galaxy.planet.rulingfamily.Family;
import code.geektrust.galaxy.planet.rulingfamily.Person;

public class FamilyTreeInitializer 
{
	private final String FILE_SEPARATOR = System.getProperty("file.separator");
	private final String PROJECT_PATH = "src/main/resources";

	private Family family;
	
	public FamilyTreeInitializer(Family family)
	{
		this.family = family;
		family.addFamilyMember(new Person("King Shan", GenderConstants.MALE));
	}
	
	public void initializeFamilyTree() throws IOException
	{
		String familyInitializerFilePath = new File(".").getCanonicalPath() + FILE_SEPARATOR + PROJECT_PATH + FILE_SEPARATOR + "initializeFamily.txt";
		File familyFile = new File(familyInitializerFilePath);
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(familyFile))))
		{
			String input = br.readLine();
			Scanner inputScanner = new Scanner(input);
			
			while(input != null)
			{
				input = br.readLine();
				if(input == null) break;
				
				inputScanner = new Scanner(input);
				inputScanner.useDelimiter(",");
				
				while(inputScanner.hasNext())
				{
					String personName = inputScanner.next();
					String spouseName = inputScanner.next();
					String childName  = inputScanner.next();
					String childGender = inputScanner.next();
					
					initializeSingleFamily(personName, spouseName, childName, childGender);
				}
				
				inputScanner.close();
			}
		}
	}

	private void initializeSingleFamily(String personName, String spouseName, String childName, String childGender) 
	{
		if(family.findFamilyMember(personName) != null)
		{
			Person person = family.findFamilyMember(personName);
			Person spouse = family.findFamilyMember(spouseName);
			
			if(spouse == null)
			{
				String spouseGender = person.getGender().equals(GenderConstants.MALE) ? GenderConstants.FEMALE : GenderConstants.MALE;
				spouse = new Person(spouseName, spouseGender);
				family.addFamilyMember(spouse);
				person.marry(spouse);
			}
			
			Person mother = spouse.getGender().equals(GenderConstants.MALE) ? person : spouse;
			Person child  = null;
			switch(childGender)
			{
				case GenderConstants.MALE:
					child = mother.createChildren(childName, childGender);
					family.addFamilyMember(child);
					break;
				case GenderConstants.FEMALE:
					child = mother.createChildren(childName, childGender);
					family.addFamilyMember(child);
					break;
			}
		}
	}
}
