package sample.LeetCode.LRUCacheImp;

import java.util.HashMap;
import java.util.Map;


//Brute Force
//class Node 
//{
//	int key;
//	int value;
//	int timestamp;
//	
//	/*
//	 * For every node insertion, save timestamp. Addition, removal typical.
//	 * When cache has reached its capacity, remove least-recently used node.(Node with less timestamp).
//	 * Whenever a node is used in operations like 'get' or 'put', change value of timestamp.
//	 * Find the node with less timestamp and remove it. 
//	 * 
//	 * Time Complexity :O(n)
//	 */
//}

//Using a hashmap and doubly linked list;

public class LRUCache 
{
	//Key-to-CacheEntry map
	private Map<Integer, CacheEntry> cacheMap; 
	private LRUCacheElementMaintenance lru;
	private int capacity;
	
	public LRUCache(int maxSize) 
	{
		cacheMap = new HashMap<Integer, CacheEntry>();
		lru = new LRUCacheElementMaintenance();
		capacity = maxSize;
    }
    
    public int get(int key) 
    {
        CacheEntry cacheEntry = cacheMap.get(key);
        
        if(cacheEntry != null)
        {
        	lru.maintainOrder(cacheEntry); // Re-arranging cache elements with recently used at the head 
        								   // and least recently used at the tail.----------> Order Change
        	
        	System.out.println("Reordering list since element used " );
        	lru.traverseList();
        	return cacheEntry.value;
        }
        
        return -1;
    }
    
    public void put(int key, int value) 
    {
    	if(cacheMap.containsKey(key))
    	{
    		CacheEntry cacheEntry = cacheMap.get(key);
    		cacheEntry.value = value;
    		cacheMap.replace(key, cacheEntry);
    		lru.maintainOrder(cacheEntry); //-------------> Order change.
    	}
    	else
    	{
    		if(cacheMap.size() == capacity)
    		{
    			int leastRecentlyUsedKey = lru.deleteElement(lru.tail);   			
    			cacheMap.remove(leastRecentlyUsedKey);
    			
    			System.out.println("Removed Element Key :: " + leastRecentlyUsedKey);
    		}

    		CacheEntry cacheEntry = new CacheEntry(key, value);
    		cacheMap.put(key, cacheEntry);
    		lru.addToHead(cacheEntry); //-------------->Order Change. Added to head, order change taken care of implicitly.
    	}
    }
    
    public void printCache()
    {
    	for(int key : cacheMap.keySet())
    	{
    		System.out.println("Key :: " + key + " :: " + cacheMap.get(key).value );
    	}
    	
    	lru.traverseList();
    }
}

/**
 * 
 * The doubly linked list that maintains the order of cache elements. 
 * Least recently used entries(in methods like "get" or "put" or "replace") at the tail.
 * Recently used ones at the head.
 *
 */

class LRUCacheElementMaintenance
{
	private CacheEntry head;
	public CacheEntry tail;
	
	public LRUCacheElementMaintenance()
	{
		head = null;
		tail = null;		
	}
	
	public void addToHead(CacheEntry cacheEntry)
	{
		if(head == null)
		{
			head = cacheEntry;
			tail = cacheEntry;
			return;
		}
		
		head.previous = cacheEntry;
		cacheEntry.next = head;
		head = cacheEntry;
	}
	
	public int deleteLeastRecentlyUsed()
	{
		if(tail != null)
		{
			if(tail.previous != null)
			{
				System.out.print("Removing :: " + "(" + tail.key + "," + tail.value + ")");
				int leastRecentlyUsedKey = tail.key;
				tail.previous.next = null;
				tail = tail.previous;
				
				return leastRecentlyUsedKey;
			}
			else
			{
				int leastRecentlyUsedKey = tail.key;
				tail = null;
				head = null;
				return leastRecentlyUsedKey;
			}
		}
		
		return -1;
	}
	
	public int deleteElement(CacheEntry cacheEntry)
	{
		int deleteKey = cacheEntry.key;
		
		if(cacheEntry.previous != null && cacheEntry.next != null)
		{
			cacheEntry.previous.next = cacheEntry.next;
			cacheEntry.next.previous = cacheEntry.previous;
		}
		else
		{
			if(cacheEntry == head && cacheEntry == tail)
			{
				head = null;
				tail = null;
			}
			else if(cacheEntry == head)
			{
				cacheEntry.next.previous = null;
				head = cacheEntry.next;
			}
			else if(cacheEntry == tail)
			{
				cacheEntry.previous.next = null;
				tail = cacheEntry.previous;
			}
		}
		
		return deleteKey;
	}
	
	//Moves recent entries to head.
	public void maintainOrder(CacheEntry cacheEntry)
	{
		deleteElement(cacheEntry);
		addToHead(cacheEntry);
	}
	
	public void traverseList()
	{
		CacheEntry curr = head;
		
		while(curr != null)
		{
			System.out.print("(" + curr.key + "," + curr.value + ")" + "-->");
			curr = curr.next;
		}
		
		System.out.println();
	}
}

//Node of doubly linked list
class CacheEntry 
{
	public int key;
	public int value;
	public CacheEntry next;
	public CacheEntry previous;
	
	public CacheEntry(int key, int val)
	{
		this.key = key;
		this.value = val;
	}
}
