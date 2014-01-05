package de.hrw.zoo.list;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.util.Log;
import de.hrw.zoo.model.Player;

public class PlayerList extends ArrayList<Player> {

	private static final long serialVersionUID = 4250119629878039283L;
	
	public PlayerList() {
	}
	
	public void resetPoints() {
		for(Player p: this) {
			if(p != null) {
				p.setPoints(0);
			}
		}
	}
	
	public boolean save(String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			for(Player p: this) {
				Log.d("Zoo", p.getName());
			}
			oos.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	static public PlayerList Load(String path) {
		PlayerList pl = new PlayerList();
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fis);              
			pl = (PlayerList) in.readObject();
			for(Player p: pl) {
				Log.d("Zoo", p.getName());
			}
			in.close();
		} catch (Exception e) {
		}
		
		return pl;
	}

}
