package de.hrw.zoo.view;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import de.hrw.zoo.listener.OnAnimalClickListener;

public class AnimalView extends ImageView {

	public AnimalView(Context context) {
		super(context);
	}
	
	public AnimalView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
	
	public void setAnimator(Animator animation) {
		setOnClickListener(new OnAnimalClickListener(animation));
	}

}
