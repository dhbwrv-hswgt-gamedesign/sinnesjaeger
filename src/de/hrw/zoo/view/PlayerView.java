package de.hrw.zoo.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hrw.zoo.R;

public class PlayerView extends RelativeLayout {
	
	private TextView name;
	private ImageView avatar;
	private ImageView remove;
	private float rotation;
	
	public PlayerView(Context context) {
		super(context);
		
		//setBackgroundColor(Color.CYAN);
	}

	public PlayerView(Context context, String name, OnClickListener listener) {
		this(context);
		
		this.name = new TextView(context);
		this.name.setText(name);
		this.name.setTextSize(20);
		this.name.setGravity(TEXT_ALIGNMENT_CENTER);
		
		this.avatar = new ImageView(context);
		this.avatar.setImageResource(R.drawable.user_icon);
		
		this.remove = new ImageView(context);
		this.remove.setImageResource(R.drawable.delete_icon);
		this.remove.setOnClickListener(listener);
		
		this.rotation = 0;
		
		LayoutParams params = new LayoutParams(250, 250);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(avatar, params);
		
		params = new LayoutParams(75, 75);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(remove, params);
		
		params = new LayoutParams(300, 50);
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(this.name, params);
	}
	
	public void setRotation(float rot) {
		this.rotation = rot;
	}
	
	public float getRotation() {
		return this.rotation;
	}

}
