package design.pattern.behavioural.ChainOfResponsibility;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class NotificationEngine<T> //This is the observable interface
{
	int count = 0;
	Map<Integer, Consumer<T>> subscribers = new HashMap<Integer, Consumer<T>>(); 
	
	public int subscribe(Consumer<T> subscriber)
	{
		subscribers.put(count++, subscriber);
		return count;
	}
	
	public void unsubscribe(int key)
	{
		subscribers.remove(key);
	}
	
	public void fire(T object)
	{
		for(Consumer<T> sub : subscribers.values())
			sub.accept(object);
	}
}

class Query //Command pattern - We ask the value of attack or defense of a creature in a game.
 		    //Query object accomplishes the 'retrieval' action for creature statistics in a game.
{
	Goblin goblin;
	Argument arg;
	int result;
	
	public enum Argument
	{
		ATTACK,
		DEFENCE
	}
	
	Query(Goblin goblin, Argument arg, int result)
	{
		this.goblin = goblin;
		this.arg = arg;
		this.result = result;
	}
	
	public int getResult()
	{
		return result;
	}
}

class Game //This is the mediator- The Event Broker
{
	NotificationEngine<Query> channel = new NotificationEngine<>(); //Channel where modifiers are registered.
}

class Goblin
{
	Game game;
	String name;
	int baseAttack;
	int baseDefense;
	
	Goblin(Game game, String name, int baseAttack, int baseDefense)
	{
		this.game = game;
		this.name = name;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}
	
	public int getAttack()
	{
		//Command-Query Separation - Separate means of querying rather than directly sending BaseAttack through getAttack
		//Listeners can process the query before sending result.
		Query q = new Query(this, Query.Argument.ATTACK, baseAttack);
		game.channel.fire(q); //Before returning attack value, checking channel if any modifiers registered.
		return q.result;
	}
	
	public int getDefense()
	{
		Query q = new Query(this, Query.Argument.DEFENCE, baseDefense);
		game.channel.fire(q); //Before returning defense value, checking channel if any modifiers registered.
		return q.result;
	}

	@Override
	public String toString() {
		return "Goblin [name=" + name + ", Attack=" + getAttack() + ", Defense=" + getDefense() + "]";
	}
	
	
}

//Subscribed modifiers to the query channel are fired (Broken chain). So any retrieval of attack/defense, passing through
//the chain of modifiers is gets modified. Implementing without modifiers accessing each others references like in 
//methodChain demo. EventBroker establishes the chain here. More flexible.

class DoubleAttackModifier
{
	Goblin goblin;
	
	public DoubleAttackModifier(Goblin goblin) {
		this.goblin = goblin; //Modifier being applied to the goblin.
		Game game = goblin.game;
		
		//Whenver a query requesting attack is asked over a creature, the game(event broker) fires it 	
		//to all modifiers that are subscribed. The modifier applied over the goblin makes the changes on the result.
		game.channel.subscribe(x -> {
			if(x.arg == Query.Argument.ATTACK && x.goblin.equals(goblin))
			{
				x.result *= 2;
			}
		});
	}
	
}

class DoubleDefenseModifier
{
	Goblin goblin;
	
	public DoubleDefenseModifier(Goblin goblin) {
		this.goblin = goblin; //Modifier being applied to the goblin.
		Game game = goblin.game;
		
		//Whenver a query requesting defense is asked over a creature, the game(event broker) fires it 	
		//to all modifiers that are subscribed. The modifier applied over the goblin makes the changes on the result.
		game.channel.subscribe(x -> {
			if(x.arg == Query.Argument.DEFENCE && x.goblin.equals(goblin))
			{
				x.result *= 2;
			}
		});
	}
	
}

public class BrokerChain {
	public static void main(String[] args) {
	}
}






