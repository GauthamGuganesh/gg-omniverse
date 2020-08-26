package design.pattern.behavioural.ChainOfResponsibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

abstract class Creatur
{
  Gam game;
  Creatur(Gam game)
  {
	  this.game = game;
	  game.creatures.add(this);
	  game.enterGame(this);
  }
  
  public abstract int getAttack();
  public abstract int getDefense();
}

class Event<T>
{
    private int count;
    private Map<Integer, Consumer<T>> subscribers = 
                        new HashMap<>();
                        
    public int subscribe(Consumer<T> subscriber)
    {
        subscribers.put(count++, subscriber);
        return count;
    }
    
    public void unSubscribe(int key)
    {
        subscribers.remove(key);
    }
    
    public void fire(T object)
    {
        for(Consumer<T> con : subscribers.values())
            con.accept(object);
    }
}

class Quer
{
    Creatur creature;
    Statistic statistic;
    int result;
    
    Quer(Creatur creature, Statistic statistic, int result)
    {
        this.creature = creature;
        this.statistic = statistic;
        this.result = result;
    }
    
    public int getResult() { return result; }
}

class Gobln extends Creatur
{
  public Gobln(Gam game)
  {
    super(game);
  }

  @Override
  public int getAttack()
  {
    Quer q = new Quer(this, Statistic.ATTACK, 1);
    game.channel.fire(q);
    return q.result;
  }

  @Override
  public int getDefense()
  {
    Quer q = new Quer(this, Statistic.DEFENSE, 1);
    game.channel.fire(q);
    return q.result;
  }
  
  @Override
  public String toString()
  {
	  return "Attack = " + getAttack() + " Defense = " + getDefense();
  }
}

class GoblinKing extends Gobln
{
  public GoblinKing(Gam game)
  {
    super(game);
  }
}

enum Statistic
{
  ATTACK, DEFENSE
}

class Gam
{
  public List<Creatur> creatures = new ArrayList<>();
  public Event<Quer> channel = new Event<>();
  
  public void enterGame(Creatur creature)
  {
	  creature.game.channel.subscribe(q -> {
	    	 
    	  String creatureType = q.creature.getClass().getSimpleName();
    	  Statistic statistic = q.statistic;
    	  boolean isKingPresent	  = doesGameHaveGoblinKing();
    	  
    	  if(statistic.equals(Statistic.ATTACK) && isKingPresent && q.creature.equals(creature))
    	  {
    		 if(creatureType.equals("GoblinKing"))
    			 q.result = 3;
    		 else
    			 q.result += 1;
    	  }
    	  else if(statistic.equals(Statistic.DEFENSE) && q.creature.equals(creature))
    	  {
    		  if(creatureType.equals("GoblinKing"))
    			  q.result = 3 + (creatures.size() - 1); //Goblin king has 3 defense points to begin with
    		  else
    			  q.result += (creatures.size() - 1);
    	  }
    			 
      });
  }
  
  private boolean doesGameHaveGoblinKing()
  {
	  for(Creatur cr : creatures)
		  if(cr.getClass().getSimpleName().equals("GoblinKing"))
			  return true;
	  
	  return false;
  }
}

public class GoblinGame
{
	public static void main(String[] args) {
		Gam game = new Gam();
		Gobln goblin = new Gobln(game);
		
		Gobln goblin2 = new Gobln(game);
		
		Gobln goblin3 = new Gobln(game);
		
	//	Gobln goblin4 = new Gobln(game);
		
		GoblinKing king = new GoblinKing(game);
		
		
		System.out.println(goblin3);
		System.out.println(goblin);
		System.out.println(goblin2);
		
		System.out.println(king);
		
	}
}




