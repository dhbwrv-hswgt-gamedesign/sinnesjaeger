package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.Window;
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
		
		if(player != null) {
			TextView name = (TextView) findViewById(R.id.player_name);
			name.setText(player.getName());
		}
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
}
	
	
