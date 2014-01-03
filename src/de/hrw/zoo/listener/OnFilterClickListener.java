package de.hrw.zoo.listener;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class OnFilterClickListener implements OnClickListener {
	
	private boolean active = true;
	private ImageView[] animals;
	
	public OnFilterClickListener(ImageView[] animals) {
		this.animals = animals;
	}

	@Override
	public void onClick(View btn) {
		if(active) {
			btn.setAlpha(0.3f);
			
			for(int i=0; i<animals.length; i++) {
				animals[i].setAlpha(0f);
				Log.d("Zoo", ""+i);
			}
		} else {
			btn.setAlpha(1.0f);
			for(int i=0; i<animals.length; i++) {
				animals[i].setAlpha(1f);
				Log.d("Zoo", ""+i);
			}
			btn.setAlpha(1.0f);
		}
		active = !active;
	}

}
