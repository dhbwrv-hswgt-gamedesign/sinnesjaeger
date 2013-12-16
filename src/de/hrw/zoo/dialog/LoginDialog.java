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
	
	private static Context context;

	private final static int MAX_PLAYERS = 5;
	
	private static View view;
	private static PlayerView[] playerViews = new PlayerView[MAX_PLAYERS];
	private Point mCenter;
	private Button mStartButton;
	private static OnLongClickListener listener;

	public LoginDialog(Context context, View view, Point center) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.color.filter_background);
		
		this.mCenter = center;
		setView(view);
		setContext(view.getContext());
				
		ImageView desc = (ImageView) findViewById(R.id.circle_background);
		
		listener = new OnLongClickListener() {
			
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
		};
				
		desc.setOnLongClickListener(listener);
		
		mStartButton = (Button) findViewById(R.id.next_button);

        for(int i=0; i<MAX_PLAYERS; i++) {
            playerViews[i] = new PlayerView(view.getContext(), null);
        }
	}
	
	public static PlayerView[] getPlayerViews(){
		return playerViews;
		
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
	
	
	public static View getViewFromHere()
	{
		return view;
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
	

	public static Context getContextView() {
		return context;
	}

	public static void setContextView(Context context) {
		LoginDialog.context = context;
	}
	
	public static Context getContextFromHere() {
		return getContextView();
	}

	public void setContext(Context context) {
		LoginDialog.setContextView(context);
	}
	
	public static OnLongClickListener getOnLongClickListener(){
		return listener;
	}
}
	
	
