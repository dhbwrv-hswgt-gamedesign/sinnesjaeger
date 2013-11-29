package de.hrw.zoo.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.RelativeLayout;
import de.hrw.zoo.R;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class LoginDialog extends AlertDialog.Builder {
	
	private final int MAX_PLAYERS = 5;
	
	private View view;
	private PlayerView[] playerViews = new PlayerView[MAX_PLAYERS];

	public LoginDialog(Context context, View view) {
		super(context);
		
		setView(view);

        for(int i=0; i<MAX_PLAYERS; i++) {
            playerViews[i] = new PlayerView(view.getContext(), null);
        }
	}
	
	public Builder setView(View v) {
		view = v;
		return super.setView(v);
	}
	
	public AlertDialog show() {
		updatePlayers();
		return super.show();
	}
	
	public void updatePlayers() {
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.player_layout);
		layout.removeAllViews();
		
		RelativeLayout.LayoutParams params; 
		Point center = new Point(1060, 570);
        int rotation = 360/5;
        int offset = 500;
        
		for(int i=0; i<MAX_PLAYERS; i++) {
	        params = new RelativeLayout.LayoutParams(300, 300);
            params.leftMargin = (int) (center.x - Math.sin(rotation*(i+1)*Math.PI/180)*(offset+50));
            params.topMargin = (int) (center.y - Math.cos(rotation*(i+1)*Math.PI/180)*offset);
            
            layout.addView(playerViews[i], params);
		}
	}
	
	public Player[] getPlayers() {
		Player[] players = new Player[MAX_PLAYERS];
		
		for(int i=0; i<MAX_PLAYERS; i++) {
			players[i] = playerViews[i].getPlayer();
		}
		
		return players;
	}
	
	public void setPlayer(Player[] players) {
		for(int i=0; i<MAX_PLAYERS; i++) {
			playerViews[i] = new PlayerView(view.getContext(), players[i]);
		}
	}
}
	
	
