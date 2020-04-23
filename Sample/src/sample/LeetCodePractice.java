package sample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




		
public class LeetCodePractice 
{
	public static void main(String[] args) 
	{
		int[] num = {2, 7, 11, 15};
		int target = 9;
		
		
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);
		
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);
		//twoSumMap
		//twoSum
		//addTwoNumbers
		//LengthOfLongestSubString
		//Merge two intervals
		//ThreeSum
		
	//	System.out.println(threeSum(new int[] {-1, 0, 1, 2, -1, -4}).toString());
		
	//	System.out.println(lengthOfLongestSubstring("abcabcbb"));
		
	//	int[][] intervals = { {1,6}, {0,3}, {8,10}, {15,18} };
		
	//	System.out.println(Arrays.deepToString(merge(intervals)));
	}
	
   public ListNode removeNthFromEnd(ListNode head, int n) 
   {
	   if(head == null)
       	return head;
       
       int size = 0;
       
       ListNode curr = head;
       
       while(curr != null)
       {
       	size++;
       	curr = curr.next;
       }
       if(n > size)
       	return null;
       
       curr = head;
       
       if(size == 2)
       {
       	if(n == 1)
       	{
       		curr.next = null;
       		return head;
       	}
       }
       else if(size == 1)
           return null;
       else if(size == n)
       {
           head = head.next;
       	return head;
       }
       
       int removePos = size - n + 1;
       
       
       curr = head;
       
       for(int i = 1 ; i < removePos - 1 ; i++) //go to the node before the Node to be removed to delete it.
       	curr = curr.next;
       
       curr.next = curr.next.next;
       return head;   
   }
	
    public static List<List<Integer>> threeSum(int[] nums)
    {
    	int n = nums.length;
    	int i = 0,j = i + 1, k = j + 1;
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        
        List<TripletResult> result = new ArrayList<TripletResult>();
        TripletResult tripletResult;
        
        while(i < n)
        {
        	if(j < n)
        	{
        		if(k < n)
        			{
	        			if(numsCopy[i] + numsCopy[j] + numsCopy[k] == 0)
	        			{
	        				tripletResult = new TripletResult();
	        				tripletResult.add(numsCopy[i]);
	        				tripletResult.add(numsCopy[j]);
	        				tripletResult.add(numsCopy[k]);
	        				Collections.sort(tripletResult.triplet);
	        				result.add(tripletResult);
	        			}
	        			
	        			k++;
        			}
        		else
        			{
        				System.out.println("else 1");
        				System.out.println(i);
        				System.out.println(j);
        				System.out.println(k);
        				
        				j++;
        				k = j + 1;
        			}
        	}
        	else
        	{
        		i++;
        		j = i + 1;
        		k = j + 1;
        	}
        }
        
        List<TripletResult> threeSumTriplet = new ArrayList<TripletResult>();
        List<List<Integer>> answer = new ArrayList<List<Integer>>();
        
        for(TripletResult res : result)
        {
        	if(!threeSumTriplet.contains(res))
        	{
        		threeSumTriplet.add(res);
        		answer.add(res.triplet);
        	}
        }
          
        return answer;
    } 
	
	public static int[][] merge(int[][] intervals)
	{
		if(intervals.length == 0)
			return intervals;
		
		List<Interval> intervalList = new LinkedList<Interval>();

		for(int i = 0; i < intervals.length ; i++)
		{
			intervalList.add(new Interval(intervals[i][0], intervals[i][1]));
		}
		
		Collections.sort(intervalList);
		
		Interval interval = new Interval(intervalList.get(0).value1, intervalList.get(0).value2);
		Interval header = interval;
		Interval curr = header;
		
		
		for(int i = 1; i < intervalList.size(); i++)
		{
			interval.next = new Interval(intervalList.get(i).value1, intervalList.get(i).value2);
			interval = interval.next;
		}
		
		curr = header;
		
		int i = 0;
		while(curr.next != null && i < intervals.length)
		{
			int innerbound = curr.value1;
			int outerbound = curr.value2;
			int nextInnerbound = curr.next.value1;
			int nextOuterBound = curr.next.value2;
			
			if(outerbound >= nextInnerbound)
			{
				if(outerbound <= nextOuterBound)
					curr.value2 = nextOuterBound;
				
				if(innerbound >= nextInnerbound)
					curr.value1 = nextInnerbound;
				
				curr.next = curr.next.next;
			}
			else
				curr = curr.next;
			
			i++;
		}
		
		curr = header;
		int size = 0;
		
		while(curr != null)
		{
			size++;
			curr = curr.next;
		}
		
		int[][] result = new int[size][2];
		
		int k = 0;
		while(header != null)
		{
			result[k][0] = header.value1;
			result[k][1] = header.value2;
			k++;
			
			header = header.next;
		}
		
		return result;
	}
	
	public static int lengthOfLongestSubstring(String s)
    {
		if(s.isEmpty())
			return 0;
		
		int lengthOfLongestString = -23;
		char[] charArray = s.toCharArray();
		
		String[] longestSubString = new String[s.length()];
		
		for(int i = 0 ; i < charArray.length ; i++)
		{
			longestSubString[i] = charArray[i] + "";
			
			for(int j = i+1 ; j < charArray.length ; j++)
			{
				if(!longestSubString[i].contains(charArray[j] + ""))
					longestSubString[i] = longestSubString[i] + charArray[j] + "";
				else
					break;
			}
		}
		
		System.out.println(Arrays.deepToString(longestSubString));
		
		for(int i = 0; i < longestSubString.length ; i++)
			if(longestSubString[i].length() > lengthOfLongestString)
				lengthOfLongestString = longestSubString[i].length();
		
		return lengthOfLongestString;
    }
	
	public static int lengthOfLongestSubstringSlidingWindow(String s)
    {
		if(s.isEmpty())
			return 0;
		
		char[] charArray = s.toCharArray();
		int i = 0,j = 0;
		
		HashSet<Character> set = new HashSet<Character>();
		
		while(i < s.length())
		{
			if(!set.contains(s.charAt(j)))
			{
				set.add(s.charAt(j));
				j++;
			}
			else
			{
				set.remove(s.charAt(i));
				i++;
			}
		}
		
		return j-i;		
    }

	 
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) 
    {
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        
        while(l1 != null)
        {
        	list1.add(Integer.toString(l1.val));
        	l1 = l1.next;
        }
        
        while(l2 != null)
        {
        	list2.add(Integer.toString(l2.val));
        	l2 = l2.next;
        }
        
        Collections.reverse(list1);
        Collections.reverse(list2);
        
        String num1 = "";
        String num2 = "";
        
        for(int i = 0; i < list1.size(); i++)
        {
        	num1 = num1 + list1.get(i);
        }
        
        for(int i = 0; i < list2.size(); i++)
        {
        	num2 = num2 + list2.get(i);
        }
        
        BigInteger number1 = new BigInteger(num1);
        BigInteger number2 = new BigInteger(num2);
        
        BigInteger sum = number1.add(number2);
        
        String sumOfTwo = sum.toString();
        
        List<Integer> list3 = new ArrayList<Integer>(sumOfTwo.length());
        for(int i = sumOfTwo.length() - 1; i >= 0; i--)
        {
        	list3.add(Integer.parseInt(sumOfTwo.charAt(i) + ""));
        }
        
        ListNode l3 = new ListNode(list3.get(0));
        ListNode header = l3;
        
        for(int i = 1 ; i < list3.size() ; i++)
        {
        	l3.next = new ListNode(list3.get(i));
        	l3 = l3.next;
        }
        
        return header;
    }
	
	private static int[] twoSumMap(int[] nums, int target)
	{
		int[] result = new int[2];
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < nums.length; i++)
		{
			indexMap.put(nums[i], i);
			int complement = target - nums[i];
			
			if(indexMap.containsKey(complement) && complement != nums[i])
			{
				result[0] = indexMap.get(nums[i]);
				result[1] = indexMap.get(complement);
			}
		}
		
		return result;
	}

	private static int[] twoSum(int[] num, int target) 
	{
		int[] result = new int[2];
		int i = 0;
		while(i < num.length)
		{
			int j = i + 1;
			while(j < num.length)
			{
				if( num[i] + num[j] == target)
				{
					result[0] = i;
					result[1] = j;
				}
				
				j++;
			}
			
			i++;
		}
		
		return result;
	}
}

class Interval implements Comparable<Interval>
{
	int value1;
	int value2;
	
	public Interval next;
	
	public Interval(int v1, int v2)
	{
		value1 = v1;
		value2 = v2;
	}
	
	public String toString()
	{
		return value1 + " " + value2;
	}

	@Override
	public int compareTo(Interval intrval) 
	{
		if(value1 < intrval.value1)
			return -1;
		else if(value1 > intrval.value1)
			return 1;
		else
			return 0;
	}
}

class TripletResult
{
	public List<Integer> triplet;
	
	public TripletResult()
	{
		this.triplet = new ArrayList<Integer>();
	}
	
	public void add(int element)
	{
		triplet.add(element);
	}

	@Override
	public int hashCode() 
	{
		int result = 57 + triplet.get(0) + triplet.get(1) + triplet.get(2);
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj == null)
			return false;
		
		TripletResult tripletObj = (TripletResult) obj;
			
		if(this.triplet.get(0) == tripletObj.triplet.get(0) && this.triplet.get(1) == tripletObj.triplet.get(1) && 
		   this.triplet.get(2) == tripletObj.triplet.get(2)){
			return true;
		}
		else
			return false;
	}
}

class ListNode {
    int val;
    String name;
    ListNode next;
    ListNode(String name)
    {
    	this.name = name;
    }
    ListNode(int x) { val = x; }
    
    public void setName(String name)
    {
    	this.name = name;
    }
    
    public String getName()
    {
    	String temp = name;
    	name = "xxx";

    	return temp;
    }
}
