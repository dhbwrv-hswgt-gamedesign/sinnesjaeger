package de.hrw.zoo.listener;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Point;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class PlayerCircleAnimator implements AnimatorUpdateListener {
	
	private ImageView image;
	private RelativeLayout.LayoutParams params;
	private Point center;
	private int offset;
	
	public PlayerCircleAnimator(ImageView image, Point center, int offset) {
		super();
		
		this.image = image;
		params = (LayoutParams) image.getLayoutParams();
		
		this.center = center;
		this.offset = offset;
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {		
		int value = (Integer) animation.getAnimatedValue();

		params.leftMargin = (int) (center.x + Math.cos(value*Math.PI/180)*offset);
		params.topMargin = (int) (center.y + Math.sin(value*Math.PI/180)*offset);
		
		image.setLayoutParams(params);
	}
}
