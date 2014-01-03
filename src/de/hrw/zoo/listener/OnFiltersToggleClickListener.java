package de.hrw.zoo.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class OnFiltersToggleClickListener implements OnClickListener {
	
	private boolean active = true;
	private ImageButton[] buttons;
	private ImageView[] animals;
	
	public OnFiltersToggleClickListener(ImageButton[] buttons, ImageView[] animals) {
		this.buttons = buttons;
		this.animals = animals;
	}

	@Override
	public void onClick(View view) {
		if(active) {
			for(int i=0; i<buttons.length; i++) {
				if(buttons[i].getAlpha() == 1f) {
					buttons[i].callOnClick();
				}
			}
			for(int i=0; i<animals.length; i++) {
				animals[i].setAlpha(0f);
			}
			
			view.setAlpha(0.3f);
		} else {
			for(int i=0; i<buttons.length; i++) {
				if(buttons[i].getAlpha() == 0.3f) {
					buttons[i].callOnClick();
				}
			}
			for(int i=0; i<animals.length; i++) {
				animals[i].setAlpha(1f);
			}
			
			view.setAlpha(1f);
		}
		active = !active;
	}

}
