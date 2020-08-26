package design.pattern.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

interface Event<T>
{
	
}

class PropertyChangeEvent<T> implements Event<T>
{
	public String propertyName;
	public T source;
	public Object newValue;
	
	PropertyChangeEvent(String propertyName, T object, Object newValue)
	{
		this.propertyName = propertyName;
		this.source = object;
		this.newValue = newValue;
	}
}

interface Observer<T>
{
	public void handle(Event<T> event);
}

class Observable<T>
{
	List<Observer<T>> observers = new ArrayList<>(); //Observable should have the list of observers subscribed to it.
	
	public void subscribe(Observer<T> observer)
	{
		observers.add(observer); //Adding subscribers
	}
	
	public void onPropertyChange(String propertyName, T object, Object newValue)
	{
		for(Observer<T> observer : observers)
			observer.handle(new PropertyChangeEvent<T>(propertyName, object, newValue)); //notifying subscribers.
	}
}

class Person extends Observable<Person> 
{
	int age;
	
	Person(int age)
	{
		this.age = age;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
		onPropertyChange("age", this, age); //Generates events
	}
}

public class ObserverDemo implements Observer<Person> //Listener
{
	public static void main(String[] args) {
		new ObserverDemo();
	}
	
	ObserverDemo()
	{
		Person p = new Person(21);
		p.subscribe(this);
		
		for(int i = 20 ; i < 25 ; i++)
			p.setAge(i);
	}
	
	@Override
	public void handle(Event<Person> event) {
		
		PropertyChangeEvent<Person> pEvent = (PropertyChangeEvent<Person>) event; //Getting events
		System.out.println("Person "+ pEvent.propertyName + " is set to :: " + pEvent.newValue);
	}

}











