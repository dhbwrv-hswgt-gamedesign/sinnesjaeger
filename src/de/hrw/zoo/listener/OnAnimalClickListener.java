package de.hrw.zoo.listener;

import android.animation.Animator;
import android.view.View;
import android.view.View.OnClickListener;

public class OnAnimalClickListener implements OnClickListener {
	
	Animator animation;
	
	public OnAnimalClickListener(Animator animation) {
		this.animation = animation;
	}

	@Override
	public void onClick(View arg0) {
		animation.start();
	}

}
