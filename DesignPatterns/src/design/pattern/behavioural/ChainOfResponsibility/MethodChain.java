package design.pattern.behavioural.ChainOfResponsibility;

class Creature
{
	public String name;
	public int attack;
	public int defense;
	
	Creature(String name, int attack, int defense){
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}
	
	@Override
	public String toString()
	{
		return "name = " + name + "[attack = " + attack + " , defense = " + defense + "]";
	}
}

class CreatureModifier
{
	protected Creature creature;
	private CreatureModifier next;
	
	CreatureModifier(Creature creature) {
		this.creature = creature;
	}
	
	public void add(CreatureModifier cm)
	{
		if(next != null) next.add(cm);
		else next = cm;
	}
	
	public void handle()
	{
		if(next != null) next.handle(); //Traversing the chain. Client interacts with this interface which traverses the chain
	}
}

class NoModifier extends CreatureModifier
{
	NoModifier(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public void handle()
	{
		System.out.println("No bonuses for you!"); //No super.handle() -> Halting chain.
	}
}

class AttackModifer extends CreatureModifier
{
    AttackModifer(Creature creature) {
		super(creature);
	}
	
	@Override
	public void handle()
	{
		creature.attack *= 2;
		System.out.println("Doubling Attack...");
		super.handle(); // Calling to traverse to next element in chain.
	}
	
}

class DefenseModifier extends CreatureModifier
{
    DefenseModifier(Creature creature) {
    	super(creature);
	}
    
    @Override
    public void handle()
    {
    	creature.defense += 3;
    	System.out.println("Increasing defense by 3...");
    	super.handle();
    }
}

public class MethodChain {
	
	public static void main(String[] args) {
		Creature creature = new Creature("Goblin", 5,5);
		System.out.println(creature);
		CreatureModifier root = new CreatureModifier(creature);
		
		root.add(new NoModifier(creature));
		root.add(new AttackModifer(creature));
		root.add(new DefenseModifier(creature));
		
		root.handle();
		System.out.println(creature);
	}

}







