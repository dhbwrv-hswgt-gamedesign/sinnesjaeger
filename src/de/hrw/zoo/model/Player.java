package de.hrw.zoo.model;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 2151564957284704571L;
	
	private String name;
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
