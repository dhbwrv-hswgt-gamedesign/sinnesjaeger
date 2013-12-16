package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.model.Player;

public class PlayerDialog extends Dialog {
	
	private static Context context;
	private static View view;
	private Point mCenter;
	private Player player;

	public PlayerDialog(Context context, View view, Point center, Player player) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(R.color.filter_background);
		
		this.mCenter = center;
		this.player = player;
		setView(view);
		
		Typeface miso = Typeface.createFromAsset(context.getAssets(), "fonts/miso.otf");
		Typeface miso_bold = Typeface.createFromAsset(context.getAssets(), "fonts/miso-bold.otf");
		Typeface baskerville = Typeface.createFromAsset(context.getAssets(), "fonts/baskerv.ttf");
		
		if(player != null) {
			TextView points = (TextView) findViewById(R.id.circle_points_text);
			points.setTypeface(miso, Typeface.BOLD);
			TextView text = (TextView) findViewById(R.id.player_text);
			text.setTypeface(miso_bold);
			TextView name = (TextView) findViewById(R.id.player_name);
			name.setText(player.getName());
			name.setTypeface(baskerville);
			
			ImageView avatar = (ImageView) findViewById(R.id.player_avatar);
			switch(player.getAvatar()) {
			case 0:
				avatar.setImageResource(R.drawable.player_adler);
				break;
			case 1:
				avatar.setImageResource(R.drawable.player_bear);
				break;
			case 2:
				avatar.setImageResource(R.drawable.player_elefant);
				break;
			case 3:
				avatar.setImageResource(R.drawable.player_eule);
				break;
			case 4:
				avatar.setImageResource(R.drawable.player_fuchs);
				break;
			}
		}
		
		ImageView back = (ImageView) findViewById(R.id.circle_background);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
}
	
	
