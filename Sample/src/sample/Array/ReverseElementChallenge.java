package sample.Array;

import java.util.Arrays;

public class ReverseElementChallenge 
{
	public static void main(String[] args) 
	{
		int[] array = {1, 2, 3, 4 , 5, 6, 7 ,8 ,9 , 10};
		System.out.println(Arrays.toString(array));
		reverseArray(array);
		System.out.println(Arrays.toString(array));
	}

	private static void reverseArray(int[] array)
	{
		int j = 0;
		int t = 0;
		for(int i = array.length-1 ; (i >= (int)array.length / 2); i--)
		{
			t = array[j];
			array[j] = array[i];
			array[i] = t;
			j++;
		}
			
	}
}
