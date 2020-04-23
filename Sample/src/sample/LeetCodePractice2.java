package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import sample.ListNode;

/*
 * Following is a simple stack based iterative process to print Preorder traversal.
1) Create an empty stack nodeStack and push root node to stack.
2) Do following while nodeStack is not empty.
….a) Pop an item from stack and print it.
….b) Push right child of popped item to stack
….c) Push left child of popped item to stack


Below is an algorithm for traversing binary tree using stack. See this for step wise step execution of the algorithm.

1) Create an empty stack S.
2) Initialize current node as root
3) Push the current node to S and set current = current->left until current is NULL
4) If current is NULL and stack is not empty then 
     a) Pop the top item from stack.
     b) Print the popped item, set current = popped_item->right 
     c) Go to step 3.
5) If current is NULL and stack is empty then we are done.

 */

public class LeetCodePractice2 
{
	public static void main(String[] args) 
	{
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		
	//	inorderTraversal(root);
	//	inorderIterativeTraversal(root);
		
		// K-most frequent elements
		// nums = [1,1,1,2,2,3], k = 2
		//odd-even linkedlist
		//container with most water
		
		
	//	System.out.println(topKFrequent(new int[] {1, 2 }, 2).toString());
		
		
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		node.next.next.next.next = new ListNode(5);
		node.next.next.next.next.next = new ListNode(6);
		node.next.next.next.next.next.next = new ListNode(7);
		node.next.next.next.next.next.next.next = new ListNode(8);

		
		ListNode curr = node;
					
//		curr = oddEvenList(node);
//		
//		while(curr != null)
//		{
//			System.out.println(curr.val + " ");
//			curr = curr.next;
//		}
		
		
//		int[] height = new int[] {1,8,6,2,5,4,8,3,7};
//		System.out.println(maxArea(height));
//		
//		 ListNode samp = new ListNode("hello");
//		System.out.println(samp.getName());
//		System.out.println(samp.getName());
		
		//Given a non-empty array of integers, every element appears twice except for one. Find that single one
		
//		int[] nums = new int[]{4,1,2,1,2};
//		
//		System.out.println(singleNumber(nums));
		
//		System.out.println(isHappy(12));
		
//		largestSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4});
		
//		int[] nums = new int[] {2,1,2,1,0,1,2};
//		System.out.println(maxProfit(nums));
//		moveZeroes(nums);
//		System.out.println(Arrays.toString(nums));
		
//		List<List<String>> groupAnagrams = groupAnagramss(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"});
		
	}

	public static int maxArea(int[] height) 
    {
        int maxArea = 0;
        int numberOfLines = height.length;
        
        for(int i = 0 ; i < numberOfLines ; i++)
        {
        	int j = 0;
        	int area = 0;
        	while(j < numberOfLines)
        	{
        		if(i != j)
        		{
        			if(height[i] <= height[j])
        			{
        				area = ( j > i ) ? (height[i] * (j - i)) : (height[i] * (i - j));
        				System.out.println("##Areas for line " + i + " :: " + area);
        				if(area > maxArea)
        					maxArea = area;
        			}
        		}
        		
        		j++;
        	}
        }
        
        return maxArea;
    }
	
	public static ListNode oddEvenList(ListNode head) 
    {
		if(head == null)
			return null; // 1  2  3  4  5
		
        ListNode oddHead = head;
        ListNode evenHead = head.next;
               
        ListNode curr = oddHead;
        ListNode curr2 = evenHead;
        
        while(curr.next != null && curr2.next != null)
        {        	
        	curr.next = curr.next.next;
        	curr2.next = curr.next.next;
        	
        	curr = curr.next;
        	curr2 = curr.next;  	
        }
        
        curr.next = evenHead;
                
        return oddHead;
    }
	
	public static List<Integer> topKFrequent(int[] nums, int k) 
   {
	   List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());
	   List<Integer> answer = new ArrayList<Integer>(k);
	   
	   Map<Integer, Integer> elementToFrequency = new HashMap<Integer, Integer>();
	   
	   Map<Key, Integer> elementToFrequencySort = new TreeMap<Key, Integer>(new Comparator<Key>() 
	   {

		@Override
		public int compare(Key o1, Key o2)
		{
			if(o1.frequency > o2.frequency)
				return -1;
			else if (o1.frequency < o2.frequency)
				return 1;
			else
				return 1;
		}
	
	   });
	   
	   
	   for(int i = 0; i < numsList.size() ; i++)
	   {
		   Integer element = numsList.get(i);
		   
		   if(elementToFrequency.containsKey(element)) 
		   {
			   Integer freq = elementToFrequency.get(element);
			   freq = freq + 1;
			   elementToFrequency.put(element, freq);
		   }
		   else
			   elementToFrequency.put(element, 1);
		   System.out.println("Element :: " + element + "Frequency " + elementToFrequency.get(element));
		}
	   
	   for(Integer element : elementToFrequency.keySet())
	   {
		   Key key = new Key(element, elementToFrequency.get(element));
		   Integer put = elementToFrequencySort.put(key, elementToFrequency.get(element));
		   System.out.println(put);
		   System.out.println("Element :: " + key.element + " " + elementToFrequencySort.containsKey(key) + "Frequency " + elementToFrequencySort.get(key));
	   }
	   
	   for(Key key : elementToFrequencySort.keySet())
	   {
		   answer.add(key.element);
		   k--;
		   
		   if(k <= 0)
			   break;
	   }
	   
	   return answer;
   }
	
	private static List<Integer> inorderIterativeTraversal(TreeNode root)
	{
		List<Integer> result = new ArrayList<Integer>();
		
		if(root == null)
			return Collections.EMPTY_LIST;
		
		TreeNode curr = root;
		
		Stack stack = new Stack();
		
		while(curr != null || !stack.isEmpty())
		{
			if(curr != null)
			{
				stack.push(new StackElement(curr));
			    curr = curr.left;
			}
			
			if(curr == null)
			{
				TreeNode popValue = stack.pop();
				System.out.println(popValue.val + " ");
				result.add(popValue.val);
				
				curr = popValue.right;
			}
		}
		
		return result;
	}

	private static List<Integer> inorderTraversal(TreeNode root)
	{
		if(root == null)
			return null;
		
		List<Integer> result = new ArrayList<Integer>();
		inorderTraversal(root.left);
		
		if(root != null)
			System.out.println(root.val);
		
		inorderTraversal(root.right);
		
		return Collections.EMPTY_LIST;
	}
}

class IndexInfo
{
	public int startIndex;
	public int endIndex;
	
	public IndexInfo(int startIn, int endIn)
	{
		this.startIndex = startIn;
		this.endIndex = endIn;
	}
}

class Stack
{
	public StackElement top;
	
	Stack()
	{
		top = null;
	}
	
	public void push(StackElement element)
	{
		element.next = top;
		top = element;
	}
	
	public TreeNode pop()
	{
		if(top == null)
		{
			System.out.println("Stack empty");
			return null;
		}
		
		TreeNode poppedElement = top.value;
		
		top = top.next;
		return poppedElement;
	}
	
	public void printStack()
	{
		StackElement curr = top;
		
		if(top == null)
			System.out.println("--Empty stack-- ");
		
		while(curr != null)
		{
			System.out.println(curr.value + " ");
			curr = curr.next;
		}
	}
	
	public boolean isEmpty()
	{
		return (top == null) ? true : false ;
	}
	
}

class StackElement
{
	public TreeNode value;
	public StackElement next;
	
	public StackElement(TreeNode val)
	{
		this.value = val;
		this.next = null;
	}
}

class Key
{
	int element;
	int frequency;
	
	public Key(int element, int frequency)
	{
		this.element = element;
		this.frequency = frequency;
	}
	
	public void updateFrequency(Integer freq) 
	{
		this.frequency = this.frequency + freq;
	}

	public Key(int element)
	{
		this.element = element;
		this.frequency = 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + element;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Key other = (Key) obj;
		if (element != other.element)
			return false;
		return true;
	}
}

class StringNode
{
	public String value;
	public StringNode next;
	public StringNode prev;
	
	public StringNode(String value)
	{
		this.value = value;
	}
}