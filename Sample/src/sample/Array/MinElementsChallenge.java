package sample.Array;

import java.util.Arrays;
import java.util.Scanner;

public class MinElementsChallenge 
{
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) 
	{
		int[] array = readIntegers();
		int minElement = findMinimumElement(array);
		System.out.println("Minimum Element " + "in" + Arrays.toString(array) + " is " + minElement);
	}

	private static int findMinimumElement(int[] array) 
	{
		int minInt = Integer.MAX_VALUE;
		for(int i = 0 ; i < array.length ; i++)
		{
			if(array[i] < minInt)
				minInt = array[i];
		}
		
		return minInt;
	}

	private static int[] readIntegers()
	{
		int size = 0;
		System.out.println("Enter total number of elements to input");
		size = scanner.nextInt();
		int[] newArray = new int[size];
		System.out.println("Input elements ");
		for(int i = 0 ; i < size ; i++)
			newArray[i] = scanner.nextInt();
		return newArray;
	}
}
