package de.hrw.zoo.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class AnimalAnimator extends ValueAnimator {
	
	private ObjectAnimator animation;

	public AnimalAnimator(Object target, String property, float... values) {
		animation = ObjectAnimator.ofFloat(target, property, values);
		animation.setDuration(2000);
		animation.setInterpolator(new LinearInterpolator());
		animation.setRepeatMode(Animation.RESTART);
		animation.setRepeatCount(2);
	}
	
	public void start() {
		if(!animation.isRunning()) {
			animation.start();
		}
	}
	
}
