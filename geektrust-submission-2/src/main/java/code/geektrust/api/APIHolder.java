package code.geektrust.api;

import code.geektrust.galaxy.planet.rulingfamily.Family;

public class APIHolder 
{
	public static final String ADD_CHILD_COMMAND = "ADD_CHILD";
	public static final String GET_RELATIONSHIP_COMMAND = "GET_RELATIONSHIP";
	
	public static ApplicationAPI<Family> getFamilyApplicationAPI(String[] input) throws InvalidCommandException, IllegalArgumentException
	{
		String[] sanitizedInput = new InputSanitizer(input).sanitizeInput();
		Family family = Family.createFamily();
		
		ApplicationAPI<Family> familyApplicationAPI = () -> {
			
			String executionCommand = sanitizedInput[0];
			switch(executionCommand)
			{
				case ADD_CHILD_COMMAND:
					String parent = sanitizedInput[1];
					String child  = sanitizedInput[2];
					String childGender = sanitizedInput[3];
					return family.addChildrenToFamily(parent, child, childGender);
					
				case GET_RELATIONSHIP_COMMAND:
					String name = sanitizedInput[1];
					String relationship  = sanitizedInput[2];
					
					return family.getRelationship(name, relationship);
				default:
					return "Invalid Command";
			}			
		};
		
		return familyApplicationAPI;		
	}
}

@SuppressWarnings("serial")
class InvalidCommandException extends Exception
{
	InvalidCommandException(String message)
	{
		super("InvalidCommandException :: " + message);
	}
}

@SuppressWarnings("serial")
class IllegalArgumentException extends Exception
{
	IllegalArgumentException(String message) {
		super("IllegalArgumentException :: " + message);
	}
}