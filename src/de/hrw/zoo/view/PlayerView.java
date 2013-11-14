package de.hrw.zoo.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hrw.zoo.R;

public class PlayerView extends LinearLayout {
	
	private ImageView image;
	private TextView name;
	private float rotation;
	
	public PlayerView(Context context) {
		super(context);
		
		setOrientation(VERTICAL);
		setBackgroundColor(Color.CYAN);
	}

	public PlayerView(Context context, String name) {
		this(context);
		
		this.name = new TextView(context);
		this.name.setText(name);
		this.name.setGravity(Gravity.CENTER_HORIZONTAL);
		
		this.image = new ImageView(context);
		this.image.setLayoutParams(new RelativeLayout.LayoutParams(200, 150));
		this.image.setImageResource(R.drawable.ic_launcher);
		this.image.setBackgroundColor(Color.GREEN);
		
		this.rotation = 0;
		
		addView(image);
		addView(this.name);
	}
	
	public void setRotation(float rot) {
		this.rotation = rot;
	}
	
	public float getRotation() {
		return this.rotation;
	}

}
