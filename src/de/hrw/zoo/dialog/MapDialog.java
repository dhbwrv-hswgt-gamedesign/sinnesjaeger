package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import de.hrw.zoo.R;

public class MapDialog extends Dialog {

	public MapDialog(Context context, View view) {
		super(context, R.style.PlayersDialog);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setBackgroundDrawableResource(R.color.trans_background);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		setContentView(view);
	}
}
	
	
