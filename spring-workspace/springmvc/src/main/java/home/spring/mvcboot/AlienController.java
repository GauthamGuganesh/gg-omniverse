package home.spring.mvcboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import home.spring.mvcboot.domain.Alien;

@RestController
public class AlienController {

	@Autowired
	private AlienRepo repo;
	
	@GetMapping(path="aliens", produces= {"application/xml"})
	public List<Alien> getAliens() //This list will get converted into JSON at client.
	{
		return repo.findAll();
	}
	
	@GetMapping("aliens/{aid}")
	public Alien getAlien(@PathVariable("aid") int aid)
	{
		return repo.findById(aid).orElseGet(Alien::new);
	}
	
	@PostMapping(path="aliens", consumes= {"application/json"})
	public Alien addAlien(@RequestBody Alien a)
	{
		repo.save(a);
		return a;
	}
}
