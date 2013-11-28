package de.hrw.zoo.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.NewPlayerDialog;
import de.hrw.zoo.listener.OnCreatePlayerListener;
import de.hrw.zoo.model.Player;

public class PlayerView extends RelativeLayout {
	
	private Player data;
	private TextView name;
	private ImageView avatar;
	private ImageView clear;
	
	public PlayerView(Context context) {
		super(context);
	}

	public PlayerView(Context context, Player player) {
		this(context);
		
		data = (Player) player;
		
		avatar = new ImageView(context);
		avatar.setImageResource(R.drawable.user_icon);
		avatar.setOnLongClickListener(new OnLongClickListener() {
		    @Override
		    public boolean onLongClick(View v) {
		    	final NewPlayerDialog dlg = new NewPlayerDialog(v.getContext());
		    	dlg.setOnCreatePlayerListener(new OnCreatePlayerListener() {
					@Override
					public void onCreate(Player player) {
						data = player;
						Log.i("Zoo", "new player: "+data);
						updateData();
					}
				});
		    	dlg.show();
				return false;
		    }
		});
		
		LayoutParams params;
		
		name = new TextView(context);
		name.setTextSize(20);
		name.setGravity(TEXT_ALIGNMENT_CENTER);
		
		clear = new ImageView(context);
		clear.setImageResource(R.drawable.delete_icon);
		clear.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Log.i("Zoo", "remove player: "+data);
		    	data = null;
		    	updateData();
		    }
		});
		
		params = new LayoutParams(75, 75);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(clear, params);
		
		params = new LayoutParams(300, 50);
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(name, params);
		
		params = new LayoutParams(250, 250);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(avatar, params);
		
		clear.bringToFront();
		
		updateData();
	}
	
	public Player getPlayer() {
		return data;
	}
	
	private void updateData() {
		if(this.data == null) {
			name.setVisibility(INVISIBLE);
			avatar.setImageResource(R.drawable.user_icon_empty);
			clear.setVisibility(INVISIBLE);
		} else {
			name.setText(data.getName());
			name.setVisibility(VISIBLE);
			avatar.setImageResource(R.drawable.user_icon);
			clear.setVisibility(VISIBLE);
		}
	}

}
