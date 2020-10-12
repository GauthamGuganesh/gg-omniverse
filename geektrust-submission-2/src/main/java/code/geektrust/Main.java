package code.geektrust;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import code.geektrust.api.APIHolder;
import code.geektrust.api.ApplicationAPI;

public class Main 
{
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) 
	{
		if(args.length == 0)
		{
			System.out.println("FileName empty. Provide file location as argument. Exiting...");
			System.exit(0);
		}
		
		File file = new File(args[0]);
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
		{
			String fileInput = null;			
			ApplicationAPI api = null;
			
			while((fileInput = br.readLine()) != null)
			{
				if(fileInput.isEmpty())
				{					
					System.out.println(" ");
					continue;
				}
				try 
				{
					String[] parameters = fileInput.split(" ");
					api = APIHolder.getFamilyApplicationAPI(parameters);
				} 
				catch (Exception e) 
				{
					System.out.println(e.getMessage());
					continue;
				} 
				
				System.out.println(api.executeCommand());
			}
		} 
		catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} 
		catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
	}
}




