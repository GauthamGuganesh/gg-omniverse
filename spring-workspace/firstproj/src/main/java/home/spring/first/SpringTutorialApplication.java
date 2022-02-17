package home.spring.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringTutorialApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringTutorialApplication.class, args);
		
		Alien a = context.getBean(Alien.class);
		a.code();
	}

}
