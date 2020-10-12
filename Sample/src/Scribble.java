import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Scribble {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		
		Runnable r = () -> {
			
			String myString = "Let's split this up into an array";
			String[] parts = myString.split(" ");
			for(String part : parts) System.out.println(part);
		};
		
		UnaryOperator<String> everySecondChar = (source) -> {
		
			StringBuilder returnVal = new StringBuilder();
			for(int i = 0 ; i < source.length() ; i++)
				if(i%2 == 1) returnVal.append(source.charAt(i));
			
			return returnVal.toString();
		};
		
		System.out.println(everySecondCharacter(everySecondChar, "1234567890"));
		
		Supplier<String> supplier = () -> "I Love Java";
		System.out.println(supplier.get());
		
		List<String> list = List.of("Amelia", "Olivia", "emily", "Isla", "Ava", "oliver", "Jack", "Charlie", "harry", "Jacob");
		
		long count = list.stream()
							.map(Scribble::capitalizeFirstLetter)
							.peek(System.out::println)
							.filter(s -> s.split("")[0].equalsIgnoreCase("A"))
							.count();
		
		System.out.println(count);
	}
	
	public static String capitalizeFirstLetter(String token)
	{
		String startLetter = token.split("")[0];
		if(!startLetter.equals(startLetter.toUpperCase()))
			return startLetter.toUpperCase().concat(token.substring(1, token.length()));
		
		return token;
	}
	
	public static String everySecondCharacter(Function<String, String> function, String argument)
	{
		return function.apply(argument);
	}
	
	
}
