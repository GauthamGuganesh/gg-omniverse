package home.spring.springmvc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import home.spring.springmvc.model.Alien;

@Component
public class AlienDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Alien> getAliens()
	{
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.createQuery("from Alien", Alien.class).list();
	}
	
	@Transactional
	public void addAlien(Alien a)
	{
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(a);
	}
	
	@Transactional
	public Alien getAlien(int aid)
	{
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.get(Alien.class, aid);
	}
}
