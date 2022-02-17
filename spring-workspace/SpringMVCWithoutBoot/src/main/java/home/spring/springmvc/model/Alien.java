package home.spring.springmvc.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alien {
	
	@Id
	private int aid;	
	private String aname;
	
	public Alien() { }
	
	public int getAid() {
		return aid;
	}
	public void setAid(int id) {
		this.aid = id;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String name) {
		this.aname = name;
	}
	
	@Override
	public String toString() {
		return "Alien [id=" + aid + ", name=" + aname + "]";
	}
}
