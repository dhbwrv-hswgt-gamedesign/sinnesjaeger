package de.hrw.zoo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.PlayerListAdapter;
import de.hrw.zoo.model.Player;

public class HomeActivity extends Activity {
	
	private Player[] players = new Player[5];

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        Log.d("Zoo", getIntent().getExtras().getSerializable("players").toString());
        
        ListView players_list = (ListView) findViewById(R.id.players_list);
        
        Object objects[] = (Object[]) getIntent().getExtras().getSerializable("players");
        players = new Player[objects.length];
        for(int i=0; i<objects.length; i++) {
        	players[i] = (Player) objects[i];
        	if(players[i] != null) {
        		Log.d("Zoo", players[i].getName());
        	}
        }

        ListAdapter pa = new PlayerListAdapter(this, R.layout.player_list_item, players);
        players_list.setAdapter(pa);
    }
}

