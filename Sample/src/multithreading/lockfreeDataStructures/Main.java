package multithreading.lockfreeDataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class StandardStack<T>
{
	StackNode<T> top;
	int count = 0;
	
	public synchronized void push(T value)
	{
		StackNode<T> node = new StackNode<T>(value);
		node.next = top;
		top = node;
		
		count++;
	}
	
	public synchronized T pop()
	{
		if(top == null)
		{
			count++;
			return null;
		}
		else
		{
			count++;
			T poppedValue = top.value;
			top = top.next;
			return poppedValue;
		}
	}
}

class LockFreeStack<T>
{
	StackNode<T> top;
	AtomicReference<StackNode<T>> atomicTop = new AtomicReference<StackNode<T>>(top);
	AtomicInteger counter = new AtomicInteger(0);
	
	//Implementation from course.
	
	 public void push(T value) {
         StackNode<T> newHeadNode = new StackNode<>(value);

         while (true) {
             StackNode<T> currentHeadNode = atomicTop.get();
             newHeadNode.next = currentHeadNode;
             if (atomicTop.compareAndSet(currentHeadNode, newHeadNode)) {
                 break;
             } else {
             }
         }
         counter.incrementAndGet();
     }

     public T pop() {
         StackNode<T> currentHeadNode = atomicTop.get();
         StackNode<T> newHeadNode;

         while (currentHeadNode != null) {
             newHeadNode = currentHeadNode.next;
             if (atomicTop.compareAndSet(currentHeadNode, newHeadNode)) {
                 break;
             } else {
                 currentHeadNode = atomicTop.get();
             }
         }
         counter.incrementAndGet();
         return currentHeadNode != null ? currentHeadNode.value : null;
     }
}

class StackNode<T>
{
	T value;
	StackNode<T> next;
	
	StackNode(T value)
	{
		this.value = value;
	}
}

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
	   LockFreeStack<Integer> stack = new LockFreeStack<Integer>();
	   Random random = new Random();	
	   
	   for (int i = 0; i < 100000; i++) {
           stack.push(random.nextInt());
       }
	   
	   int pushingThreads = 2;
	   int poppingThreads = 2;
	   
	   List<Thread> threads = new ArrayList<>();
	   
	   for(int i = 0 ; i < pushingThreads ; i++)
	   {
		   Thread pushingThread = new Thread(() -> {
			 while(true)
			   stack.push(random.nextInt());
		   });
		   
		   pushingThread.setDaemon(true);
		   threads.add(pushingThread);
	   }
		
	   for(int i = 0 ; i < poppingThreads ; i++)
	   {
		   Thread poppingThread = new Thread(() -> {
			 while(true)
			   stack.pop();
		   });
		   
		   poppingThread.setDaemon(true);
		   threads.add(poppingThread);
	   }
	   
	   
	   for(Thread t : threads)
		   t.start();
	   
	   Thread.sleep(1000);
	   
	   System.out.println("Number of operations :: " + stack.counter);
  }
}












