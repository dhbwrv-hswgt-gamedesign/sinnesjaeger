package de.hrw.zoo.model;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 2151564957284704571L;
	
	private String name;
	private long token;
	private int avatar;
	private int points;
	
	public Player(String name) {
		this.name = name;
		this.setPoints(0);
		this.avatar = 0;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoints(int points) {
		this.setPoints(this.getPoints() + points);
	}
}
