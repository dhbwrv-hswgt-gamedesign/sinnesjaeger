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
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.model.Animal;

public class DetailScreenDialog extends Dialog {

	public DetailScreenDialog(Context context, View view, Point center, Animal animal) {
		super(context, R.style.DetailScreen);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.color.filter_background);
		
		Typeface miso_bold = Typeface.createFromAsset(context.getAssets(), "fonts/miso-bold.otf");
		Typeface baskerville = Typeface.createFromAsset(context.getAssets(), "fonts/baskerv.ttf");
		
		if(animal != null) {
			ImageView game = (ImageView) findViewById(R.id.circle_game_bg);
			ImageView info = (ImageView) findViewById(R.id.circle_info_bg);
			ImageView video = (ImageView) findViewById(R.id.circle_video_bg);
			TextView text = (TextView) findViewById(R.id.animal_species);
			text.setTypeface(baskerville);
			TextView name = (TextView) findViewById(R.id.animal_name);
			name.setText(animal.getName());
			text.setText(animal.getArt());
			name.setTypeface(miso_bold);
					
			Log.d("Zoo", animal.getGame());
			
			if(animal.getGame().equals("0")){
				game.setVisibility(View.INVISIBLE);
				
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
			
			info.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(v.getContext(), "Informationen anzeigen.", Toast.LENGTH_SHORT).show();
				}
			});

			video.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(v.getContext(), "Video anzeigen.", Toast.LENGTH_SHORT).show();
				}
			});
			
			game.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(v.getContext(), "Spiel starten.", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		ImageView back = (ImageView) findViewById(R.id.circle_back_2_bg);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		
	}
}
	
	
