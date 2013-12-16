package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.model.Animal;
import de.hrw.zoo.model.Player;

public class DetailScreenDialog extends Dialog {
	
	private static Context context;
	private static View view;
	private Point mCenter;
	private Animal animal;

	public DetailScreenDialog(Context context, View view, Point center, Animal animal) {
		super(context, R.style.DetailScreen);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.color.filter_background);
		
		Typeface miso = Typeface.createFromAsset(context.getAssets(), "fonts/miso.otf");
		Typeface miso_bold = Typeface.createFromAsset(context.getAssets(), "fonts/miso-bold.otf");
		Typeface baskerville = Typeface.createFromAsset(context.getAssets(), "fonts/baskerv.ttf");
		
		this.mCenter = center;
		this.animal = animal;
		setView(view);
		
		if(animal != null) {
			
			TextView text = (TextView) findViewById(R.id.animal_species);
			text.setTypeface(miso_bold);
			TextView name = (TextView) findViewById(R.id.animal_name);
			name.setText(animal.getName());
			name.setTypeface(baskerville);
			ImageView game = (ImageView) findViewById(R.id.circle_game_bg);
			
			Log.d("Zoo", animal.getGame());
			
			if(animal.getGame().equals("1")){
				game.setVisibility(View.INVISIBLE);
				game.setAlpha(0);
			}
			
			ImageView avatar = (ImageView) findViewById(R.id.animal_avatar);
			switch(Integer.parseInt(animal.getAvatar())) {
			case 0:
				avatar.setImageResource(R.drawable.detail_schmetterling);
				break;
			case 1:
				avatar.setImageResource(R.drawable.detail_pinguin);
				break;
			}
		}
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
}
	
	
