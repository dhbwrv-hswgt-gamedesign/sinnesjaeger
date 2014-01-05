package de.hrw.zoo.util;


public abstract class Storage {
	public abstract void init();
	public abstract String get(String fileName);
	public abstract void delete(String fileName);
}
