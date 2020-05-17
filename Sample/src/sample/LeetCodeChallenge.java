package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sample.ListNode;
import sample.TreeNode;
import sample.LeetCode.LRUCacheImp.LRUCache;

//Happy number
//Single number
//Largest sub-array sum
//Move Zeroes
//Buy and sell stocks - peak and valley algorithm
//Group anagrams together
//count Elements
//Middle of linked list
//Backspace String Compare
//Min-Stack
//Diameter of Binary Tree - longest path is number of edges between nodes. (Usually number of nodes in a longest path)
//Last Stone Weight
//Longest Contiguous Sub-Array with equal number of 0s and 1s
//Perform String Shifts
//Product of Array Except Self-without division and in O(n) time.
//Search in Rotated Sorted Array
//Construct Binary Search Tree from Preorder Traversal
//Number of continous sub-arrays with sum = k.
//Bitwise AND of Numbers Range
//LRU Cache

//**Dynamic Programming**
//Valid Parenthesis String
//Number of Islands
//Minimum Path Sum 
//**Dynamic Programming**


//Optimized diameter do tomorrow #################

public class LeetCodeChallenge 
{
	public static void main(String[] args) 
	{
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		node.next.next.next.next = new ListNode(5);
		node.next.next.next.next.next = new ListNode(6);
		
	//	System.out.println(middleNode(node).val);
		
	//	System.out.println(backspaceCompare("ab#c", "ad#c"));
		
//		MinStack minStack = new MinStack();
//		minStack.push(-2);
//		minStack.push(0);
//		minStack.push(-3);
//		minStack.traverseStack();
		
//		minStack.pop();
		
		TreeNode tree = new TreeNode(1);
		tree.left = new TreeNode(2);
		tree.right = new TreeNode(3);
		
		tree.left.left = new TreeNode(4);
		tree.left.right = new TreeNode(5);
		
//		System.out.println(diameterOfBinaryTree(tree));
		
//		System.out.println(lastStoneWeight(new int[] {2, 7, 4, 1, 8, 1}));
		
//		System.out.println(findMaxLength(new int[] {0, 1})); //Subtract indexes having same sum
		
//		System.out.println(stringShift("abcdefg", new int[][]{ {1,1}, {1,1}, {0,2}, {1,3}} ));
		
//		System.out.println(Arrays.toString(productExceptSelf(new int[] {5, 6, 7, 8} )));
		
//		System.out.println(checkValidString("(*()")); //Have to use dynamic programming or greedy algorithm.
		
//		System.out.println(numIslands(new char[][] { {'1','1','1','1','0'}, {'1','1','0','1','0'}, {'1','1','0','0','0'} , {'0','0','0','0','0'} }));

//		System.out.println(search(new int[] {3,1}, 1));
		
//		TreeNode bstFromPreorder = bstFromPreorder(new int[] {8,5,1,7,10,12});
		
//		preorderTraverse(bstFromPreorder);
		
//		levelOrderTraverse(bstFromPreorder);
		
//		test();
		
//		System.out.println(rangeBitwiseAnd(2, 6));
		
		LRUCache cache = new LRUCache(2);
		
		cache.put(2, 1);
		cache.printCache();
		
		cache.put(1, 1);		
		cache.printCache();
		
		cache.put(2, 3);
		cache.printCache();

		cache.put(4, 1);
		
		cache.printCache();
		
		System.out.println("Retreiving key :: 1 : " + cache.get(1));      
		
		System.out.println("Retreiving key :: 2 : " + cache.get(2));       
		
		cache.printCache();
	}
	
	public static int rangeBitwiseAnd(int m, int n) 
	{
		if(m == n)
			return m & n;
		
        int andWise = m & (m + 1);
        
        for(int i = m+1 ; i <= n ; i++)
        {
        	andWise = andWise & i;
        }
        
        return andWise;
    }
	
	public static void test()
	{
//		String a = "hello";  //If used "new" for creating string object, no internalization (ie) no string pool.
		                     // use "intern" to add it to the pool. If second string also created with "new", no string pool.
//		String b = "hello";
		
		String a = new String("hello");
		String b = a.toUpperCase();
		
		System.out.println(b);
		
		if(a==b)
			System.out.println("Same Objects");
	}
	
	public static TreeNode bstFromPreorder(int[] preorder)
	{
		//Find last element that is smaller(for left sub-tree)/greater(for right sub-tree) than root then divide the array at its index. Post-Order --> Last element is root.
		//Pre-order --> First element is root.
		
		int i, size = preorder.length;
		if(size <= 0)
			return null;

		TreeNode root = new TreeNode(preorder[0]);
        
        if(size == 1)
        	return root;
        
        for(i = 1 ; i < size ; i++)
        {
    		if(preorder[i] > root.val)
    			break;
        }
        
        int indexOfLastElementLargerThanRoot = i - 1;
        
        int[] leftTree = new int[indexOfLastElementLargerThanRoot];
        int[] rightTree = new int[size - indexOfLastElementLargerThanRoot - 1];
        
        for(int j = 0 ; j < leftTree.length ; j++)
        	leftTree[j] = preorder[j+1];
        
        for(int k = 0 ; k < rightTree.length ; k++)
        	rightTree[k] = preorder[indexOfLastElementLargerThanRoot + k + 1];
        
        System.out.println("Root Value :: *********************** " + root.val);
        System.out.println("Left Tree :: " + Arrays.toString(leftTree));
        System.out.println("Right Tree :: " + Arrays.toString(rightTree));
        
        root.left = bstFromPreorder(leftTree);
        root.right = bstFromPreorder(rightTree);
        
        System.out.println("Root Value :: *********************** " + root.val);
        
        return root;       
    }
	
	private static void preorderTraverse(TreeNode bstFromPreorder)
	{
		if(bstFromPreorder == null)
			return;
		
		System.out.print(bstFromPreorder.val + " , ");
		preorderTraverse(bstFromPreorder.left);
		preorderTraverse(bstFromPreorder.right);
	}

	private static void levelOrderTraverse(TreeNode bstFromPreorder)
	{
		int height = findHeight(bstFromPreorder);
		
		System.out.println("");
		System.out.println("Tree Height :: " + height);
		
		for(int i = 0 ; i < height ; i++)
		{
			System.out.println("Level :: " + (i+1));
			System.out.println("*******************");
			printLevelOrderTraverse(bstFromPreorder, i);
			System.out.println("");
		}
	}

	private static void printLevelOrderTraverse(TreeNode bstFromPreorder, int height)
	{
		if(height < 0 || bstFromPreorder == null)
			return;
		
		if(height == 0)
			System.out.print(bstFromPreorder.val + ",");
		
		printLevelOrderTraverse(bstFromPreorder.left, height - 1);
		printLevelOrderTraverse(bstFromPreorder.right, height - 1);
	}

	private static int findHeight(TreeNode bstFromPreorder) 
	{
		int lheight;
		int rheight;
		
		if(bstFromPreorder == null)
			return 0;
		
		lheight = findHeight(bstFromPreorder.left);
		rheight = findHeight(bstFromPreorder.right);
	
		return Math.max(lheight, rheight) + 1;
	}
	
	public static int search(int[] nums, int target) 
	{
		//Normal binary search, we would check with the middle element of a ascending sorted array. 
		//This is a pivoted array, so check with 1st element since its rotated.
		
		int pivotIndex = 0;
		
        for(int i = 0 ; i < nums.length - 1 ; i++)
        {
        	if(nums[i] > nums[i+1])
        		pivotIndex = i;
        }
        
        if(nums.length == 0)
        	return -1;
        
        if(target > nums[0])
        {
        	pivotIndex = (pivotIndex == 0) ? nums.length - 1 : pivotIndex ;
        	
        	for(int i = 0 ; i <= pivotIndex ; i++)
        		if(nums[i] == target)
        			return i;
        }
        else if(target < nums[0])
        {
        	for(int i = pivotIndex + 1 ; i < nums.length ; i++)
        		if(nums[i] == target)
        			return i;
        }
        else if(target == nums[0])
        	return 0;

        
        return -1;
              	       
    }
	
	public static boolean checkValidString(String s)
	{
		//"(*()"
		for(int i = 0 ; i < s.length(); i++)
		{
			if(i == s.length() - 1)
				return isValid(s);
			else if(s.charAt(i) == '*')
			{
				for(char temp : "() ".toCharArray())
				{
					StringBuilder stb = new StringBuilder(s);
					stb.setCharAt(i, temp);
					if(checkValidString(stb.toString()))
						return true;
				}
			}
			else
				continue;
		}
		
		return false;
	}

	private static boolean isValid(String st) 
	{
		int validityCount = 0;
		
		for(int i = 0 ; i < st.length() ; i++)
		{
			if(st.charAt(i) == '(')
				validityCount++;
			else if(st.charAt(i) == ')')
				validityCount--;
			
			if(validityCount < 0)
				return false;
		}
		
		System.out.println("Validity count :: " + validityCount );
		return (validityCount == 0) ? true : false;
	}

	public static int[] productExceptSelf(int[] nums) 
	{
		//without division and in O(n) time.
		//use two arrays. Product of left elements- one array. product of right elements-one array. multiply it for answer;
		int size = nums.length;
		
		int[] prodLeft = new int[size];
		int[] prodRight = new int[size];
		int[] answer = new int[size];

		for(int i = 0 ; i < size ; i++)
		{
			prodLeft[i] = 1;
			prodRight[i] = 1;
		}
		
		for(int i = 1 ; i < size ; i++)
			prodLeft[i] = nums[i-1] * prodLeft[i-1];
				
		for(int i = size - 2 ; i >=0 ; i--)
			prodRight[i] = nums[i+1] * prodRight[i+1];
		
		for(int i = 0 ; i < size ; i++)
			answer[i] = prodLeft[i] * prodRight[i];
		
		System.out.println(Arrays.toString(prodLeft));
		System.out.println(Arrays.toString(prodRight));
		System.out.println(Arrays.toString(answer));
		
		return answer;
	}
	
	public static String stringShift(String s, int[][] shift) // s = "abc"
	{
        //shift[direction, amount]
		int leftShift = 0;
		int rightShift = 0;
		int shiftDiff = 0;
		
		StringBuilder st = new StringBuilder(s);
		
		for(int i = 0 ; i < shift.length ; i++)
		{
			if(shift[i][0] == 0)
				leftShift += shift[i][1];
			else 
				rightShift += shift[i][1];
		}
		
		shiftDiff = leftShift - rightShift;
		
		System.out.println("$$$$$$$$$$$$$$");
		System.out.println(leftShift);
		System.out.println(rightShift);
		System.out.println(shiftDiff);
		if(shiftDiff == 0)
			return s;
		else if(shiftDiff > 0)
		{
			//Net-LeftShift
			while(shiftDiff > 0)
			{
				String firstChar = st.charAt(0)+"";
				
				st.deleteCharAt(0);
				st.append(firstChar);
				
				--shiftDiff;
			}			
		}
		else
		{
			//Net-RightShift
			shiftDiff = -shiftDiff;
			while(shiftDiff > 0)
			{
				String lastChar = st.charAt(s.length()-1)+"";
				
				st.deleteCharAt(s.length()-1);
				st.insert(0, lastChar);
				
				--shiftDiff;
			}		
		}
		
		return st.toString();
    }
		        	
	public static int findMaxLengthAlt(int[] nums)
	{
		//Brute force
		 int zeroes = 0;
	     int ones = 0;
	     int contigousSubArrayLength = 0;
	     int maxContigousLength = (nums.length == 0) ? 0 : -200;
	     
	     for(int i = 0 ; i < nums.length ; i++)
	     {
	    	 contigousSubArrayLength = 0;
	    	 zeroes = 0;
	    	 ones = 0;
	    	 
	    	 for(int j = i ; j < nums.length ; j++)
	    	 {
	    		if(nums[j] == 0)
	         		zeroes++;
	         	
	         	if(nums[j] == 1)
	         		ones++;
	         	
	         	if(zeroes == ones)
	         		contigousSubArrayLength = j - i + 1;
	    	 }
	    	 
	    	 if(contigousSubArrayLength > maxContigousLength)
	    		 maxContigousLength = contigousSubArrayLength;
	    	 
	    	 System.out.println("Round :: :: " + (i + 1));
	    	 System.out.println("Zeroes :: " + zeroes);
	    	 System.out.println("Ones :: " + ones);
	    	 System.out.println("Sub Array Length :: " + contigousSubArrayLength);
	    	 System.out.println("##################################################");
	     }
	     
	     return maxContigousLength;
	}
	
	public static int findMaxLength(int[] nums) 
	{
		//HashMap optimize
		int maxlen = 0, count = 0;

		Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
		
        for (int i = 0; i < nums.length; i++) 
        {
            count = count + (nums[i] == 1 ? 1 : -1);
            
            if (map.containsKey(count))
                maxlen = Math.max(maxlen, i - map.get(count));
            else
                map.put(count, i);
            
            System.out.println("Round :: " + (i+1));
            System.out.println("Count :: " + count);
            System.out.println("Max Length :: " + maxlen);
            System.out.println(map.toString());
	    	System.out.println("##################################################");

        }
        
        return maxlen;
    }
	
	public static int lastStoneWeight(int[] stones) 
	{
		if(stones.length <= 1)
			return (stones.length == 1) ? stones[0] : 0;
		
		List<Integer> stoneList = new LinkedList<Integer>();
		
		for(int i = 0 ; i < stones.length ; i++)
			stoneList.add(stones[i]);
        
        while(stoneList.size() > 1)
        {
        	Collections.sort(stoneList);
        	
        	int stoneX = stoneList.get(stoneList.size()-2);
        	int stoneY = stoneList.get(stoneList.size()-1);
        	
        	stoneList.remove(stoneList.size()-2);
        	stoneList.remove(stoneList.size()-1);

        	if(stoneX != stoneY)
        		stoneList.add(stoneY - stoneX);
        }
        
        return stoneList.isEmpty() ? 0 : stoneList.get(0);
    }

	 public static int diameterOfBinaryTree(TreeNode root)
	 {
	        if(root == null)
	        	return 0;
	        
	        int rheight = height(root.right);
	        int lheight = height(root.left);
	        
	        System.out.println("Rheight :: " + rheight + " Lheight :: " + lheight);
	        
	        int ldiameterOfBinaryTree = diameterOfBinaryTree(root.left);
	        int rdiameterOfBinaryTree = diameterOfBinaryTree(root.right);
	        
	        return Math.max((Math.max(ldiameterOfBinaryTree, rdiameterOfBinaryTree)), /* +1 */ rheight + lheight);
	        //Usually longest path between nodes passing through root is (rheight + lheight +1), but since 
	        //the question here is "NUMBER OF EDGES", we avoid +1 and use just rheight + lheight.
	 }
	
	private static int height(TreeNode node) 
	{
		if(node == null)
			return 0;
		
		return 1 + Math.max(height(node.left), height(node.right));
	}

	public static boolean backspaceCompare(String S, String T)
	{		
		S = (S.contains("#")) ? getTextEditedString(S) : S;
		T = (T.contains("#")) ? getTextEditedString(T) : T;
 		
		return S.equals(T);
    }
	
	private static String getTextEditedString(String R)
	{
		List<String> linkedList = new LinkedList<String>();
		
		for(int i = 0 ; i < R.length() ; i++)
		{
			linkedList.add(R.charAt(i)+"");
		}
		
		int i = 0;
		
		while(linkedList.contains("#"))
		{
			if(linkedList.get(i).equals("#"))
			{
				linkedList.remove(i);
				if(i == 0)
					--i;
				else 
				{
					linkedList.remove(i-1);
					i-=2;
				}
			}						
			i++;			
		}
		
		StringBuilder stringbuilder = new StringBuilder();
		
		for(int j = 0 ; j < linkedList.size() ; j++)
			stringbuilder = stringbuilder.append(linkedList.get(j));
		
		System.out.println(stringbuilder);
		return new String(stringbuilder);
		
	}
	
    public static ListNode middleNode(ListNode head) 
    {
    	int size = 0, i = 0;
    	
    	if(head == null)
    		return head;
    	
    	ListNode curr = head;
    	
    	while(curr != null)
    	{
    		++size;
    		curr = curr.next;
    	}
    	
    	if(size == 1)
    		return head;
    	
    	curr = head;
    	
    	if(size % 2 == 0)
    	{
    		while(i != (size / 2))
    		{
    			curr = curr.next;
    			++i;
    		}
    		
    		return curr;
    	}
    	else 
    	{
    		while(i != (size / 2))
    		{
    			curr = curr.next;
    			++i;
    		}
    		
    		return curr;
    	}
    	
    }
	
    public static int countElements(int[] arr) 
    {
        int count = 0;
        
        for(int i = 0 ; i < arr.length ; i++)
        {
        	int elementX1 = arr[i] + 1;
        	
        	if(isPresent(arr[i], arr) && isPresent(elementX1, arr))
        		count++;
        }
        
        return count;
    }
	
	private static boolean isPresent(int i, int[] arr)
	{
		for(int j = 0 ; j < arr.length ; j++)
		{
			if(i == arr[j])
				return true;
		}
		
		return false;
	}

	public static List<List<String>> groupAnagrams(String[] strs)
	{
		List<String> anagramList = new ArrayList<String>();
    	List<List<String>> anagramGroup = new ArrayList<List<String>>();
    	
    	List<Integer> comparedIndex = new ArrayList<Integer>(strs.length);
    	
    	int i = 0, j = i + 1;
    	
    	while(i < strs.length)
    	{
    		j = i + 1;
    		boolean hasAnagram = false;
    		String compareValue = strs[i];
			anagramList = new ArrayList<String>();

    		System.out.println("##################");
    		System.out.println(i + " :: " + compareValue);
    		
    		while(j < strs.length)
    		{
    			String value2 = strs[j];
    			System.out.println(j + " :: " + value2);
    			
    			System.out.println("%%%%% " + compareValue + " " + value2);
    			
    			if(compareValue.length() == value2.length())
	    		{
    				boolean isAnagram;
    				
    				if(compareValue.equals(value2))
        				isAnagram = true;
    				else
    					isAnagram = checkAnagram(compareValue, value2);
    				
	    			if(isAnagram)
	    			{
	    				if(!comparedIndex.contains(j))
	    				{
	    					anagramList.add(value2);
	    					hasAnagram = true;
	    					comparedIndex.add(j);
	    				}
	    			}
	    		}
    			
    			j++;
    		}
    		
    		if(hasAnagram)
    		{
    			anagramList.add(compareValue);
    			comparedIndex.add(i);
    		}
    		
    		i++;
    		
    		System.out.println(comparedIndex.toString());
    		System.out.println("Round " + i + " List " + anagramList);
    		if(!anagramList.isEmpty())
    			anagramGroup.add(anagramList);
    	}
    	
    	for(int g = 0 ; g < strs.length ; g++)
    	{
    		if(!comparedIndex.contains(g))
    		{
    			List<String> tempList = new ArrayList<String>();
    			tempList.add(strs[g]);
    			
    			anagramGroup.add(tempList);
    		}
    	}
    	
    	return anagramGroup;
	}
	
	private static boolean checkAnagram(String compareValue, String value2) 
	{
		char[] sortedCompareValue = compareValue.toCharArray();
		char[] sortedValue2 = value2.toCharArray();
		
		Arrays.sort(sortedCompareValue);
		Arrays.sort(sortedValue2);
		
		String temp1 = new String(sortedCompareValue);
		String temp2 = new String(sortedValue2);
		
		return temp1.equals(temp2);
	}	
	
	public static int maxProfit(int[] prices)
	{
		//Peak & Valley algorithm
		
		int i = 0, valleySum = 0, peakSum = 0;
		int size = prices.length;
				
		List<Integer> valleySet = new ArrayList<Integer>();
		List<Integer> peakSet = new ArrayList<Integer>();
		
		int buyPrice = prices[i];
		int sellPrice = prices[i+1];

		while(i < size - 1)
		{		
			if(buyPrice == sellPrice)
			{
				i++;
				buyPrice = prices[i];
				sellPrice = (i < size -1) ? prices[i+1] : 0;
			}
			
			while(buyPrice > sellPrice && i < size - 1)
			{
				i++;
				buyPrice = prices[i];
				sellPrice = (i < size -1) ? prices[i+1] : 0;
			}
			
			valleySet.add(buyPrice);
			System.out.println("Valley " + valleySet.toString());
			
			while(buyPrice < sellPrice && i < size - 1)
			{
				i++;
				buyPrice = prices[i];
				sellPrice = (i < size -1) ? prices[i+1] : 0;
			}
			
			peakSet.add(buyPrice);
			System.out.println("Peak " + peakSet.toString());
		}
		
		
		for(Integer vall : valleySet)
			valleySum += vall;
		
		for(Integer peakVal : peakSet)
			peakSum += peakVal;
		
		return peakSum - valleySum;
			
	}
	   
	public static void moveZeroes(int[] nums)
	{
		int t = 0;
		
		for(int i = 0 ; i < nums.length ; i++)
			for(int j = i + 1 ; j < nums.length ; j++)
			{
				if(nums[i] == 0)
				{
					t = nums[i];
					nums[i] = nums[j];
					nums[j] = t;
				}
			}
	}
		
	public static int largestSubArray(int[] nums)
	{
		int maxSum = -200;
		
		IndexInfo indexInfo = new IndexInfo(0, 0);
		
		int i = 0, j = 0;
		
		if(nums.length == 1)
			return nums[0];
		
		if(nums.length < 1)
			return 0;
		
		while(i <= nums.length)
		{
			int sum = nums[i];
			
			if(i != nums.length)
			{
				for(j = i+1 ; j < nums.length ; j++)
				{
					sum = sum + nums[j];
					
					if(sum > maxSum)
					{
						maxSum = sum;
						indexInfo.startIndex = i;
						indexInfo.endIndex = j;				
					}
				}
			}
			else
			{
				if(sum > maxSum)
				{
					maxSum = sum;
					indexInfo.startIndex = i;
					indexInfo.endIndex = j;				
				}
			}
			
			i++;
		}
		
		System.out.println("Sub--Array with max sum");
		
		for(int k = indexInfo.startIndex ; k <= indexInfo.endIndex ; k++)
		{
			System.out.println(nums[k] + " ");
		}
		
		System.out.println("Maximum Sum " + maxSum);
		return maxSum;
	}
	
	public static boolean hasCycle(ListNode head) 
	{
		ListNode slow = head;
		
		if(slow == null)
			return false;
		
		ListNode fast = head.next;
		
		if(fast == null)
			return false;
		
		while(slow != fast)
		{
			slow = slow.next;
			
			try 
			{
				fast = fast.next.next;
			} 
			catch (Exception e)
			{
				return false;
			}
			
			if(slow == null || fast == null)
				return false;
		}
		
		return true;
		
    }
	
	public static boolean isHappy(int n)
	{
		boolean happy = false;
		int count = 0;
		
		happy = isItHappy(n, count);
		
		return happy;
	}
	
	public static boolean isItHappy(int n, int count)
	{
		count++;
		
		System.out.println(count);
		if(count > 5000)
			return false;
		
		int quotient = 0;
		int digit = 0;		
		int sum = 0;
		
		while(n > 0)
		{	
			digit = n % 10;
			quotient = n / 10;
			n = quotient;
			
			sum = sum + digit * digit;
		}
		
		if(sum == 1)
			return true;
		
		if(isItHappy(sum, count))
			return true;
		else 
			return false;
	}
	
   public static int singleNumber(int[] nums) 
   {
	   Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	   
	   for(int i = 0; i < nums.length ; i++)
	   {
		   if(map.containsKey(nums[i]))
		   {
			   Integer frequency = map.get(nums[i]);
			   frequency = frequency + 1;
			   
			   map.replace(nums[i], frequency);
		   }
		   else
			   map.put(nums[i], 1);
	   }

	   for(Integer key : map.keySet())
		   if(map.get(key) == 1)
			   return key.intValue();
	   
	   return -1;
   }
   
 } 

class MinStack
{
	   	public MinStackElement top;
	   	
	    public MinStack() 
	    {
	    	
	    }
	    
	    public void push(int x) 
	    {
	    	MinStackElement element = new MinStackElement(x);
	    	element.next = top;
	    	top = element;
	    }
	    
	    public void pop()
	    {
	        top = top.next;
	    }
	    
	    public int top() 
	    {
	        return top.x;
	    }
	    
	    public int getMin()
	    {
	    	int min = Integer.MAX_VALUE;
	    	
            MinStackElement curr = top;
	    	
	    	while(curr != null)
	    	{
	    		if(curr.x < min)
	    			min = curr.x;
	    	
	    		curr = curr.next;
	    	}
	    	
	    	return min;
	    }
	    
	    public void traverseStack()
	    {
	    	MinStackElement curr = top;
	    	
	    	while(curr != null)
	    	{
	    		System.out.println(curr.x + " ---> " );
	    		curr = curr.next;
	    	}
	    }
	    
	 class MinStackElement
	 {
		 public int x;
		 public MinStackElement next;
		 
		 public MinStackElement(int x)
		 {
			 this.x = x;
		 }
	 }
}