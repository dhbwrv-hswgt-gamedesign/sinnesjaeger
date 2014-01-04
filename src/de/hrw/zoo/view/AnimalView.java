package de.hrw.zoo.view;

import android.animation.Animator;
import android.content.Context;
import android.widget.ImageView;
import de.hrw.zoo.listener.OnAnimalClickListener;

public class AnimalView extends ImageView {

	public AnimalView(Context context) {
		super(context);
	}
	
	public void setAnimator(Animator animation) {
		setOnClickListener(new OnAnimalClickListener(animation));
	}

}
