package de.hrw.zoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import de.hrw.zoo.listener.PlayerCircleAnimator;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class MainActivity extends Activity {
	
	final List<Player> players = new ArrayList<Player>();
	final Map<Player, View> drawnPlayers = new HashMap<Player, View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button= (Button) findViewById(R.id.player_add_button);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Spieler erstellen");

				// Set up the input
				final EditText input = new EditText(v.getContext());
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	players.add(new Player(input.getText().toString()));
				    	update();
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});
				
				builder.show();
		    	
		    	Log.i("Zoo", "new");
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
		Point center = new Point((rl.getWidth()/2)-100,(rl.getHeight()/2)-100);
		
		int rotation = 0;
		int offset = 500;
		
		Log.d("Zoo", "update: "+players.size()+" ("+drawnPlayers.size()+")");
		
		if(players.size() > 0)
			rotation = 360/players.size();
	
		for(final Player p: players) {
			View v = drawnPlayers.get(p);
			
			params = new RelativeLayout.LayoutParams(200, 200);
			params.leftMargin = (int) (center.x - Math.sin(rotation*i*Math.PI/180)*offset);
			params.topMargin = (int) (center.y - Math.cos(rotation*i*Math.PI/180)*offset);
			
			if(v == null) {
				v = new PlayerView(this, p.getName());
				v.setOnClickListener(new View.OnClickListener() {
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
	            AnimatorUpdateListener listener = new PlayerCircleAnimator(v, center, offset);
	            animation.addUpdateListener(listener);
	            animation.start();
				
				Log.d("Zoo", "update: "+p);
			}
			
			v.setRotation(rotation*i);
			
			drawnPlayers.put(p, v);
			
			i++;
		}
		
		Button button= (Button) findViewById(R.id.player_add_button);
		if(players.size() > 8) {
			button.setEnabled(false);
		} else {
			button.setEnabled(true);
		}
		
	}
	
}
