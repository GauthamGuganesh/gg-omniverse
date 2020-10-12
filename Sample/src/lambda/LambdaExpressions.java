package lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaExpressions {

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Inside runnable");
			}
		}).start();
		
		//We need only sysout statement. we can pass that to thread constructor as lambda.
		
		Runnable r = () -> System.out.println("Inside runnable");
		new Thread(r).start();
		int i = 0; //Effectively final
		Runnable r1 = () -> {
			//i++ not allowed. 
			System.out.println(i);
		};
		
		Employee e1 = new Employee("Dfs", "3242");
		Employee e2 = new Employee("Erwf", "55334");
		List<Employee> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);
		
		Employee e ;
		for(Employee e3 : list)
		{
			e = new Employee("erer", "32424");
			new Thread(() -> System.out.println(e.name)); //Here e is effectively final. since doesnt change.
		}
	}

}

class Employee
{
	String name;
	String salary;
	
	Employee(String name, String salary)
	{
		this.name = name;
		this.salary = salary;
	}
}
