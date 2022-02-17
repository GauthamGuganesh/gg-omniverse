package home.spring.springmvc;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import home.spring.springmvc.dao.AlienDao;
import home.spring.springmvc.model.Alien;

@Controller
public class HomeController {
	
	@Autowired
	private AlienDao alienDao;
	
	@ModelAttribute
	public void modelData(Model m)
	{
		m.addAttribute("name", "Aliens"); 
	}
	
	@RequestMapping("/")
	public String home()
	{
		System.out.println("Home page requested");
		return "index";
	}
	
	@GetMapping("getAliens")
	public String getAliens(Model m)
	{
		m.addAttribute("result",alienDao.getAliens());
		return "showAliens";
	}
	
	@GetMapping("getAlien")
	public String getAlien(@RequestParam int aid, Model m)
	{
		m.addAttribute("result", Collections.singletonList(alienDao.getAlien(aid)));
		return "showAliens";
	}
	
	@PostMapping("addAlien")
	public String add(@ModelAttribute Alien a, Model m)
	{	
		alienDao.addAlien(a);
		m.addAttribute("result", Collections.singletonList(a));
		
		return "showAliens";
	}
}
