package de.hrw.zoo.model;

public class Animal {
	
	private String art;
	private String name;
	
	
	
	public Animal(String art, String name){
		setArt(art);
		setName(name);
	}



	public String getArt() {
		return art;
	}



	public void setArt(String art) {
		this.art = art;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	

}
