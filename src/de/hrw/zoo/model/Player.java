package de.hrw.zoo.model;

import java.util.UUID;

public class Player {
	private UUID id;
	private String name;
	private String avatar;
	private String token;
	
	public Player(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
