package sample.Array;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayMain
{
	public static void main(String[] args) 
	{
// ways to initialize		int[] array2 = { 1,2,34,5,6,7,78,7 };
		int[] newArray = new int[5];
		System.out.println(Arrays.toString(newArray));
		int[] tempArray = newArray;
		tempArray[4] = 234;
		System.out.println(Arrays.toString(tempArray));
		System.out.println(Arrays.toString(newArray));
		tempArray = new int[6];
		System.out.println(Arrays.toString(tempArray));
		System.out.println(Arrays.toString(newArray));
//		int[] sortedArray = new int[5];	
//		getIntegers(newArray);
//		sortedArray = getSortedArray(newArray);
//		printArray(sortedArray);
	}

	private static void printArray(int[] sortedArray) 
	{
		System.out.println("Numbers in descending order :: ");
		for(int i = 0 ; i < sortedArray.length ; i++)
			System.out.println(sortedArray[i]);
	}

	private static int[] getSortedArray(int[] newArray) 
	{
		int t = 0;
		int[] sortedArray = new int[5];
		
		for(int i = 0; i < newArray.length; i++)
			for(int j = 0; j < newArray.length - i - 1; j++)
			{
				if(newArray[j] < newArray[j+1])
				{
					t = newArray[j];
					newArray[j] = newArray[j+1];
					newArray[j+1] = t;
				}
			}
		
		sortedArray = Arrays.copyOf(newArray, newArray.length);
		
		printArray(newArray);
		return sortedArray;
	}

	private static void getIntegers(int[] newArray)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 5 integers \n");
		for(int i = 0 ; i < newArray.length ; i++)
		{
			newArray[i] = scanner.nextInt();
		}
		
		scanner.close();
		return;
	}
}
