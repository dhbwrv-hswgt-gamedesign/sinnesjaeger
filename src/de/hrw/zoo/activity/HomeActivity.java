package de.hrw.zoo.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.PlayerListAdapter;
import de.hrw.zoo.list.PlayerList;

public class HomeActivity extends Activity {
	
	private PlayerList players = new PlayerList();
	private File mStorePath;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mStorePath = new File(getFilesDir(),"zoo");
        if(!mStorePath.exists()) {
        	mStorePath.mkdir();
        }
        
        ListView players_list = (ListView) findViewById(R.id.players_list);

        players = PlayerList.Load(new File(mStorePath, "players"));        
        ListAdapter pa = new PlayerListAdapter(this, R.layout.player_list_item, players);
        players_list.setAdapter(pa);
    }
}

