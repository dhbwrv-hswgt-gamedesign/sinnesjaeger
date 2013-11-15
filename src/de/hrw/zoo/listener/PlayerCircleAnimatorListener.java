package de.hrw.zoo.listener;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Point;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class PlayerCircleAnimatorListener implements AnimatorUpdateListener {
	
	private View image;
	private RelativeLayout.LayoutParams params;
	private Point center;
	private int offset;
	
	public PlayerCircleAnimatorListener(View image, Point center, int offset) {
		super();
		
		this.image = image;
		params = (LayoutParams) image.getLayoutParams();
		
		this.center = center;
		this.offset = offset;
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {		
		float value = (Float) animation.getAnimatedValue();

		params.leftMargin = (int) (center.x - Math.sin(value*Math.PI/180)*(offset+100));
		params.topMargin = (int) (center.y - Math.cos(value*Math.PI/180)*offset);
		
		image.setLayoutParams(params);
	}
}
