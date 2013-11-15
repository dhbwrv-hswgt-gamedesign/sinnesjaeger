package de.hrw.zoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.NewPlayerDialog;
import de.hrw.zoo.listener.NewPlayerListener;
import de.hrw.zoo.listener.PlayerCircleAnimatorListener;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class LoginActivity extends Activity {
	
	final List<Player> players = new ArrayList<Player>();
	final Map<Player, View> drawnPlayers = new HashMap<Player, View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button button_back = (Button) findViewById(R.id.back_button);
		button_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		
		Button button = (Button) findViewById(R.id.add_button);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final NewPlayerDialog dlg = new NewPlayerDialog(v.getContext());
		    	dlg.setNewPlayerEventListener(new NewPlayerListener() {
					@Override
					public void onEvent() {
						players.add(dlg.getNewPlayer());
						update();
						
						Log.i("Zoo", "new player: "+dlg.getNewPlayer());
					}
				});
		    	dlg.show();
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void update() {
		int i = 0;
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.player_layout);
		RelativeLayout.LayoutParams params;
		Point center = new Point((rl.getWidth()/2)-150,(rl.getHeight()/2)-150);		
		int rotation = 0;
		int offset = 500;
		
		Log.d("Zoo", "update: "+players.size()+" ("+drawnPlayers.size()+")");
		
		if(players.size() > 0)
			rotation = 360/players.size();
	
		for(final Player p: players) {
			View v = drawnPlayers.get(p);
			
			params = new RelativeLayout.LayoutParams(300, 300);
			params.leftMargin = (int) (center.x - Math.sin(rotation*i*Math.PI/180)*(offset+100));
			params.topMargin = (int) (center.y - Math.cos(rotation*i*Math.PI/180)*offset);
			
			if(v == null) {
				v = new PlayerView(this, p.getName(), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						players.remove(p);
						View i = drawnPlayers.get(p);
						RelativeLayout rl = (RelativeLayout)findViewById(R.id.player_layout);
						rl.removeView(i);
						update();
						
						Log.d("Zoo", "delete: "+p);
					}
				});
					
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(1000);
				if(players.size() > 2)
					animation.setStartOffset(1000);
				animation.setFillAfter(true);
				v.setAnimation(animation);
				
				rl.addView(v, params);
				
				Log.d("Zoo", "new player: "+p);
			} else {
				ValueAnimator animation = ValueAnimator.ofFloat(v.getRotation(), rotation*i);
	            animation.setDuration(2000);
	            AnimatorUpdateListener listener = new PlayerCircleAnimatorListener(v, center, offset);
	            animation.addUpdateListener(listener);
	            animation.start();
				
				Log.d("Zoo", "update: "+p);
			}
			
			v.setRotation(rotation*i);
			
			drawnPlayers.put(p, v);
			
			i++;
		}
		
		Button button= (Button) findViewById(R.id.add_button);
		if(players.size() >= 8) {
			button.setEnabled(false);
		} else {
			button.setEnabled(true);
		}
		
	}
	
}
