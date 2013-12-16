package de.hrw.zoo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hrw.zoo.R;
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
		
		Typeface miso = Typeface.createFromAsset(context.getAssets(), "fonts/miso.otf");
		
		data = (Player) player;
		
		avatar = new ImageView(context);
		avatar.setImageResource(R.drawable.icon_user);
		
		LayoutParams params;
		
		name = new TextView(context);
		name.setTextSize(24);
		name.setTextColor(Color.WHITE);
		name.setGravity(TEXT_ALIGNMENT_CENTER);
		name.setTypeface(miso);
		
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
		
		params = new LayoutParams(300, 30);
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(name, params);
		
		params = new LayoutParams(230, 230);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(avatar, params);
		
		clear.bringToFront();
		
		updateData();
	}
	
	public void setPlayer(Player player) {
		this.data = player;
	}
	
	public Player getPlayer() {
		return data;
	}
	
	public void updateData() {
		if(this.data == null) {
			name.setVisibility(INVISIBLE);
			avatar.setImageResource(R.drawable.avatar_empty);
			clear.setVisibility(INVISIBLE);
		} else {
			name.setText(data.getName());
			name.setVisibility(VISIBLE);
			switch(data.getAvatar()) {
			case 0:
				avatar.setImageResource(R.drawable.avatar_adler);
				break;
			case 1:
				avatar.setImageResource(R.drawable.avatar_baer);
				break;
			case 2:
				avatar.setImageResource(R.drawable.avatar_elefant);
				break;
			case 3:
				avatar.setImageResource(R.drawable.avatar_eule);
				break;
			case 4:
				avatar.setImageResource(R.drawable.avatar_fuchs);
				break;
			default:
				avatar.setImageResource(R.drawable.avatar_empty);
				break;
			}
			clear.setVisibility(VISIBLE);
		}
	}

}
