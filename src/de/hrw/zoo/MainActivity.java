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
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import de.hrw.zoo.listener.PlayerCircleAnimator;
import de.hrw.zoo.model.Player;

public class MainActivity extends Activity {
	
	final List<Player> players = new ArrayList<Player>();
	final Map<Player, ImageView> drawnPlayers = new HashMap<Player, ImageView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button= (Button) findViewById(R.id.player_add_button);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Title");

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
		int i = 1;
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.player_layout);
		RelativeLayout.LayoutParams params;
		Point center = new Point((rl.getWidth()/2)-75,(rl.getHeight()/2)-75);
		
		int rot = 0;
		int oldRot = 0;
		int offset = 500;
		
		if(players.size() > 0) {
			rot = 360/players.size();
			
			if(players.size() > 1) 
				oldRot = 360/(players.size()-1);
		
			for(final Player p: players) {
				ImageView iv = drawnPlayers.get(p);
				
				params = new RelativeLayout.LayoutParams(150, 150);
				params.leftMargin = (int) (center.x + Math.cos(rot*i*Math.PI/180)*offset);
				params.topMargin = (int) (center.y + Math.sin(rot*i*Math.PI/180)*offset);
				
				if(iv == null) {
					iv = new ImageView(this);
					iv.setImageResource(R.drawable.ic_launcher);
					iv.setBackgroundColor(Color.YELLOW);
					iv.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							players.remove(p);
							ImageView i = drawnPlayers.get(p);
							RelativeLayout rl = (RelativeLayout)findViewById(R.id.player_layout);
							rl.removeViewInLayout(i);
							update();
							
							Log.d("Zoo", "delete: "+p);
						}
					});
						
					Animation animation = new AlphaAnimation(0.0f, 1.0f);
					animation.setDuration(1000);
					animation.setStartOffset(2000);
					animation.setFillAfter(true);
					iv.setAnimation(animation);
					rl.addView(iv, params);	
					
					Log.d("Zoo", "new player: "+p);
				} else {
					ValueAnimator animation = ValueAnimator.ofInt(oldRot*i, rot*i);
		            animation.setDuration(2000);
		            AnimatorUpdateListener listener = new PlayerCircleAnimator(iv, center, offset);
		            animation.addUpdateListener(listener);
		            animation.start();
					
					Log.d("Zoo", "update: "+p);
				}
				
				drawnPlayers.put(p, iv);
				
				i++;
			}
		}
		
		Button button= (Button) findViewById(R.id.player_add_button);
		if(players.size() > 8) {
			button.setEnabled(false);
		} else {
			button.setEnabled(true);
		}
	}

}
