package de.hrw.zoo.listener;

import de.hrw.zoo.animator.AnimalAnimator;
import android.view.View;
import android.view.View.OnClickListener;

public class OnAnimalClickListener implements OnClickListener {
	
	AnimalAnimator animation;
	
	public OnAnimalClickListener(AnimalAnimator animation) {
		this.animation = animation;
	}

	@Override
	public void onClick(View arg0) {
		animation.start();
	}

}
