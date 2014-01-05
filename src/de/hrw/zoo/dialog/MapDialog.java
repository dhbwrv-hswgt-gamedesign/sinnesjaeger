package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import de.hrw.zoo.R;

public class MapDialog extends Dialog {

	public MapDialog(Context context, Point size) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		View view = getLayoutInflater().inflate(R.layout.fragment_map, null);
		setContentView(view);
		
		getWindow().setLayout(size.x, size.y);
	}
	
}
	
	
