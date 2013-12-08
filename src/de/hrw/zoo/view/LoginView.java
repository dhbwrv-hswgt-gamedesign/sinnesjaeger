package de.hrw.zoo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.RelativeLayout;
import de.hrw.zoo.R;

public class LoginView extends RelativeLayout {

	public LoginView(Context context) {
		super(context);
		
		addView(inflate(context, R.layout.fragment_login, null));
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
