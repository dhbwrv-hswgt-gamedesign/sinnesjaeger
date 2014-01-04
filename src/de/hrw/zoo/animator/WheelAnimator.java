package de.hrw.zoo.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WheelAnimator {
	
	private AnimatorSet set;
	private RelativeLayout target;
	private LinearLayout filters;
	
	private ObjectAnimator scaleX;
	private ObjectAnimator scaleY;
	private ObjectAnimator transY;
	private ObjectAnimator alpha;

	public WheelAnimator(RelativeLayout target, LinearLayout filters) {
		set = new AnimatorSet();
		
		this.target = target;
		this.filters = filters;
		
		scaleX = ObjectAnimator.ofFloat(target, "scaleX", target.getScaleX(), 1f);
		scaleX.setDuration(2000);
		scaleY = ObjectAnimator.ofFloat(target, "scaleY", target.getScaleY(), 1f);
		scaleY.setDuration(2000);
		transY = ObjectAnimator.ofFloat(target, "translationY", target.getTranslationY(), 0f);
		transY.setDuration(2000);
		alpha = ObjectAnimator.ofFloat(filters, "alpha", 0f, 1f);
		alpha.setDuration(2000);
		
		set.playTogether(scaleX, scaleY, transY, alpha);
	}
	
	public void zoomIn() {
		scaleX.setFloatValues(target.getScaleX(), 1f);
		scaleY.setFloatValues(target.getScaleY(), 1f);
		transY.setFloatValues(target.getTranslationY(), 0f);
		alpha.setFloatValues(0f, 1f);
		set.start();
	}
	
	public void zoomOut() {
		scaleX.setFloatValues(target.getScaleX(), 3f);
		scaleY.setFloatValues(target.getScaleY(), 3f);
		transY.setFloatValues(0f, target.getHeight());
		alpha.setFloatValues(1f, 0f);
		set.start();
	}

}
