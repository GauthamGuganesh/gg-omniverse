package home.spring.springdemo;

public class Laptop implements Computer
{
	@Override
	public void compile() {
		System.out.println("Code compiled...in Laptop");
	}
}
