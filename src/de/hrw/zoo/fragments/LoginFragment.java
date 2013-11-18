package de.hrw.zoo.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.NewPlayerDialog;
import de.hrw.zoo.listener.NewPlayerListener;
import de.hrw.zoo.listener.PlayerCircleAnimatorListener;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;

public class LoginFragment extends Fragment {
	
	List<Player> players;
	Map<Player, View> drawnPlayers;
	Point center = new Point(1130, 602);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        
        if(savedInstanceState != null) {
	        players = (List<Player>) savedInstanceState.getSerializable("players");
        } else {
        	players = new ArrayList<Player>();
        }
        drawnPlayers = new HashMap<Player, View>();
        update(false);
        
        final ViewPager mPager = (ViewPager) container.findViewById(R.id.pager);
        
        Button button_back = (Button) rootView.findViewById(R.id.back_button);
		button_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
		});
		
		Button button_next = (Button) rootView.findViewById(R.id.next_button);
		button_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			}
		});
        
        Button button = (Button) rootView.findViewById(R.id.add_button);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final NewPlayerDialog dlg = new NewPlayerDialog(v.getContext());
		    	dlg.setNewPlayerEventListener(new NewPlayerListener() {
					@Override
					public void onEvent() {
						players.add(dlg.getNewPlayer());
						update(true);
						
						Log.i("Zoo", "new player: "+dlg.getNewPlayer());
					}
				});
		    	dlg.show();
		    }
		});

        return rootView;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	update(false);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putSerializable("players", (Serializable) players);
    }

    public void update(boolean animate) {
    	if(getView() == null) {
    		Log.d("Zoo", "null");
    		return;
    	}
    	
		int i = 0;
		
		RelativeLayout rl = (RelativeLayout)getView().findViewById(R.id.player_layout);
		RelativeLayout.LayoutParams params;	
		int rotation = 0;
		int offset = 500;
		
		Log.d("Zoo", "update: "+players.size()+" ("+drawnPlayers.size()+")");
		
		if(players.size() > 0)
			rotation = 360/players.size();
	
		for(final Player p: players) {
			View v = drawnPlayers.get(p);
			
			params = new RelativeLayout.LayoutParams(300, 300);
			params.leftMargin = (int) (center.x - Math.sin(rotation*i*Math.PI/180)*(offset+100));
			params.topMargin = (int) (center.y - Math.cos(rotation*i*Math.PI/180)*offset);
			
			if(v == null) {
				v = new PlayerView(rl.getContext(), p.getName(), new OnClickListener() {
					@Override
					public void onClick(View v) {
						players.remove(p);
						View view = drawnPlayers.get(p);
						RelativeLayout rl = (RelativeLayout)getView().findViewById(R.id.player_layout);
						rl.removeView(view);
						update(true);
						
						Log.d("Zoo", "delete: "+p);
					}
				});
					
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				if(animate)
					animation.setDuration(1000);
				else
					animation.setDuration(0);
				if(players.size() > 2)
					animation.setStartOffset(1000);
				animation.setFillAfter(true);
				v.setAnimation(animation);
				
				rl.addView(v, params);
				
				Log.d("Zoo", "new player: "+p);
			} else {
				ValueAnimator animation = ValueAnimator.ofFloat(v.getRotation(), rotation*i);
	            animation.setDuration(2000);
	            AnimatorUpdateListener listener = new PlayerCircleAnimatorListener(v, center, offset);
	            animation.addUpdateListener(listener);
	            animation.start();
				
				Log.d("Zoo", "update: "+p);
			}
			
			v.setRotation(rotation*i);
			
			drawnPlayers.put(p, v);
			
			i++;
		}
		
		Button button = (Button)getView().findViewById(R.id.add_button);
		if(players.size() >= 8) {
			button.setEnabled(false);
		} else {
			button.setEnabled(true);
		}
		
	}
}