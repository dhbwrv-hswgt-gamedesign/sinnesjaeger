package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.listener.OnCreatePlayerListener;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class LoginDialog extends Dialog {
	
	private final int MAX_PLAYERS = 5;
	
	private View view;
	private PlayerView[] playerViews = new PlayerView[MAX_PLAYERS];
	private Point mCenter;
	private Button mStartButton;

	public LoginDialog(Context context, View view, Point center) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		this.mCenter = center;
		setView(view);
		
		ImageView desc = (ImageView) findViewById(R.id.description);
		desc.setOnLongClickListener(new OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View v) {
		    	if(count() < MAX_PLAYERS) {
			    	final NewPlayerDialog dlg = new NewPlayerDialog(v.getContext());
			    	dlg.setOnCreatePlayerListener(new OnCreatePlayerListener() {
						@Override
						public void onCreate(Player player) {
							Log.i("Zoo", "new player: "+player);
							for(int i=0; i<playerViews.length; i++) {
								if(playerViews[i].getPlayer() == null) {
									playerViews[i].setPlayer(player);
									playerViews[i].updateData();
									break;
								}
							}
						}
					});
			    	dlg.show();
		    	} else {
		    		Toast.makeText(v.getContext(), "Maximale Anzahl Spieler erreicht!", Toast.LENGTH_SHORT).show();
		    	}
				return false;
		    }
		});
		
		mStartButton = (Button) findViewById(R.id.next_button);

        for(int i=0; i<MAX_PLAYERS; i++) {
            playerViews[i] = new PlayerView(view.getContext(), null);
        }
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
	
	public void show() {
		updatePlayers();
		super.show();
	}
	
	public int count() {
		int count = 0;
		
		for(int i=0; i<MAX_PLAYERS; i++) {
	        if(playerViews[i].getPlayer() != null) {
	        	count++;
	        }
		}
		
		return count;
	}
	
	public void updatePlayers() {
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.player_layout);
		layout.removeAllViews();
		
		RelativeLayout.LayoutParams params; 
        int rotation = 360/5;
        int offset = 450;
        
		for(int i=0; i<MAX_PLAYERS; i++) {
	        params = new RelativeLayout.LayoutParams(350, 300);
            params.leftMargin = (int) (mCenter.x + Math.sin((rotation*(i))*Math.PI/180)*offset)-175;
            params.topMargin = (int) (mCenter.y - Math.cos((rotation*(i))*Math.PI/180)*offset)-110;
            
            layout.addView(playerViews[i], params);
		}
	}
	
	public void setOnGoClickListener(View.OnClickListener listener) {
		mStartButton.setOnClickListener(listener);
	}
	
	public PlayerList getPlayers() {
		PlayerList players = new PlayerList();
		
		for(int i=0; i<MAX_PLAYERS; i++) {
			players.add(playerViews[i].getPlayer());
		}
		
		return players;
	}
	
	public void setPlayers(PlayerList players) {
		if(players.size() <= 5) {
			for(int i=0; i<players.size(); i++) {
				playerViews[i].setPlayer(players.get(i));
				playerViews[i].updateData();
			}
		}
	}
}
	
	
