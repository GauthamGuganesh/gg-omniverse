package design.pattern.behavioural.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

//Without need to implement any interface.

//This is like NotificationChannel in NMSWorks. Has many subscribers. We fire the respective events in respective channels.
class Notification<T> //Since the name 'Event' already taken in previous demo using 'notification'.
{
	int count;
	Map<Integer, Consumer<T>> observers = new HashMap<>(); //We can Subscribe and unsubscribe also.
	
	public Subscription subscribe(Consumer<T> observer) //Memento pattern. Returning subscription id, so that can unsubscribe when needed.
	{
		observers.put(++count, observer);
		return new Subscription(this, count);
	}
	
	public void unSubscribe(int id)
	{
		observers.remove(id);
	}
	
	public class Subscription
	{
		Notification<T> event;
		int id;
		
		Subscription(Notification<T> event, int id)
		{
			this.event = event;
			this.id = id;
		}
	}
	
	public void fire(T object)
	{
		for(Consumer<T> observer : observers.values())
			observer.accept(object); //Consumer function in listener gets the 'PropertyChangeNotification' object.
	}
}

class PropertyChangeNotification
{
	String propertyName;
	Object source;
	
	PropertyChangeNotification(String propertyName, Object source)
	{
		this.propertyName = propertyName;
		this.source = source;
	}
}

class Student
{
	//Respective channel.
	Notification<PropertyChangeNotification> notifChannel = new Notification<>(); //Having a event reference inside.
	//Avoiding implementing or extending anything.
	
	int age;
	
	public int getAge() { return age ; }
	
	public void setAge(int age) { 
		this.age = age; 
		
		//Respective event.
		notifChannel.fire(new PropertyChangeNotification("age", this)); // Firing event. 
	}
}

public class ObserverDemo2 {

	public static void main(String[] args) {
		
		Student student = new Student();
		
		Notification<PropertyChangeNotification>.Subscription sub = student.notifChannel.subscribe(x -> {
			System.out.println("Student's " + x.propertyName + " changed");
		});
		
		student.setAge(20);
		student.setAge(21);
		
		student.notifChannel.unSubscribe(sub.id);
		
		student.setAge(23);
	}
}








