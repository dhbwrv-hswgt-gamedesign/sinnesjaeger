package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import de.hrw.zoo.R;
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
	
	public void updatePlayers() {
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.player_layout);
		layout.removeAllViews();
		
		RelativeLayout.LayoutParams params; 
        int rotation = 360/5;
        int offset = 450;
        
		for(int i=0; i<MAX_PLAYERS; i++) {
	        params = new RelativeLayout.LayoutParams(300, 300);
            params.leftMargin = (int) (mCenter.x + Math.sin((rotation*(i))*Math.PI/180)*offset)-150;
            params.topMargin = (int) (mCenter.y - Math.cos((rotation*(i))*Math.PI/180)*offset)-125;
            
            layout.addView(playerViews[i], params);
		}
	}
	
	public void setOnGoClickListener(View.OnClickListener listener) {
		mStartButton.setOnClickListener(listener);
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
	
	
