package de.hrw.zoo.model;

import java.io.Serializable;

public class Animal implements Serializable {

	private static final long serialVersionUID = -9103325515673105594L;
	
	private String art;
	private String name;
	private String id;
	private String game;
	private String info;
	private String points;
	private String avatar;

	public Animal(){
		
	}

	public String getArt() {
		return art;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
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

	public void setAvatar(String string) {
		// TODO Auto-generated method stub
		avatar = string;
	}
	
	public String getAvatar(){
		return avatar;
	}
	
}
