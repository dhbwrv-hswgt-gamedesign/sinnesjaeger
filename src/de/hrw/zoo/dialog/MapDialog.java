package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.listener.OnCreatePlayerListener;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class MapDialog extends Dialog {

	public MapDialog(Context context, View view, ImageView mapIcon) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setBackgroundDrawableResource(R.color.trans_background);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		setContentView(view);
	}
}
	
	
