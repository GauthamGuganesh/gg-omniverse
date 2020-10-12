package code.geektrust.api;

public class InputSanitizer 
{
	private String[] input;
	
	public InputSanitizer(String[] args)
	{
		this.input = args;
	}
	
	public String[] sanitizeInput() throws InvalidCommandException, IllegalArgumentException
	{
		if(input.length == 0)
			throw new InvalidCommandException("No command given to execute. Valid commands are "
					+ APIHolder.ADD_CHILD_COMMAND + " "
					+ APIHolder.GET_RELATIONSHIP_COMMAND);
		
		String command = input[0];
		int inputSize  = input.length;
		
		switch(command)
		{
		 	case APIHolder.ADD_CHILD_COMMAND:
		 	{
		 		if(inputSize != 4)
		 			throw new IllegalArgumentException("Invalid number of arguments. "
		 					+ "Correct usage " + APIHolder.ADD_CHILD_COMMAND + " \"Mother's Name\" \"Child's Name\" \"Gender\" ");
		 		
		 		String gender = input[3];
		 		if(!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))
		 			throw new IllegalArgumentException("Invalid Gender. "
		 					+ "Correct usage " + APIHolder.ADD_CHILD_COMMAND + " \"Mother's Name\" \"Child's Name\" \"Gender(Male or Female)\"");
		 		
		 		return input;
		 	}
		 	case APIHolder.GET_RELATIONSHIP_COMMAND:
		 	{
		 		if(inputSize != 3)
		 			throw new IllegalArgumentException("Invalid number of arguments."
		 					+ "Correct usage " + APIHolder.GET_RELATIONSHIP_COMMAND + " \"Name\" \"Relationship\"");
		 		
		 		return input;	 		
		 	}
		 	default:
		 		throw new InvalidCommandException("Invalid Command. Use " + APIHolder.ADD_CHILD_COMMAND + " OR " + APIHolder.GET_RELATIONSHIP_COMMAND);
		}		
	}
}
