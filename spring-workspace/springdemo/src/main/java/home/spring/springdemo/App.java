package home.spring.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    //	BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    	
    	// Value inside 'getBean' method should match the XML entry.
    	Alien alien = (Alien) context.getBean("alien"); 
    	alien.code();
    	System.out.println(alien.getAge());
    }
}
