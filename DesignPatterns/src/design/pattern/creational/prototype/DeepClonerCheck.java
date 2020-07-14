package design.pattern.creational.prototype;


import com.rits.cloning.Cloner;

class Sam 
{
	Ram ram = new Ram();
	Bam bam = new Bam();
	Pam pam = new Pam();
}

class Ram
{	
	
}

class Bam
{
	
}

class Pam
{
	
}

public class DeepClonerCheck {
	
	public static void main(String[] args) {
		
		Cloner cloner = new Cloner();
		System.out.println(cloner.deepClone(new Sam()).ram);
	}

}
