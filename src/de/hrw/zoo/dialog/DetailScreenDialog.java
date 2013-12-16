package de.hrw.zoo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.Window;
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
		
		this.mCenter = center;
		this.animal = animal;
		setView(view);
		
		if(animal != null) {
			
		}
	}
	
	public void setView(View v) {
		view = v;
		super.setContentView(v);
	}
}
	
	
