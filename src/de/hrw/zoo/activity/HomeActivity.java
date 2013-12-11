package de.hrw.zoo.activity;

import java.io.File;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.PlayerListAdapter;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.list.PlayerList;

public class HomeActivity extends Activity {
	
	private PlayerList players = new PlayerList();
	private File mStorePath;
	private Point mAppSize;
	private Point mAppCenter;
	private PlayerListAdapter mPlayerAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mStorePath = new File(getFilesDir(),"zoo");
        if(!mStorePath.exists()) {
        	mStorePath.mkdir();
        }
        
        mAppSize = new Point();
    	getWindowManager().getDefaultDisplay().getSize(mAppSize);
    	mAppCenter = new Point(mAppSize.x/2, mAppSize.y/2);
        
    	final LinearLayout filtersLayout = (LinearLayout) findViewById(R.id.filters_layout);
    	final LinearLayout compassLayout = (LinearLayout) findViewById(R.id.compass_layout);
        final RelativeLayout composite = (RelativeLayout) findViewById(R.id.abstract_composite);
        final TextView button = (TextView) findViewById(R.id.dummy_button);
        final ListView playersList = (ListView) findViewById(R.id.players_list);
        final ImageView playersEdit = (ImageView) findViewById(R.id.edit_players);

        players = PlayerList.Load(new File(mStorePath, "players"));        
        mPlayerAdapter = new PlayerListAdapter(this, R.layout.player_list_item, players);
        playersList.setAdapter(mPlayerAdapter);        
        
        playersEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
				final LoginDialog dlg = new LoginDialog(v.getContext(), view, mAppCenter);
				dlg.setPlayers(PlayerList.Load(new File(mStorePath, "players")));

				dlg.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
						mPlayerAdapter.notifyDataSetChanged();
					}
				});
		    	dlg.setOnGoClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
						dlg.cancel();
						mPlayerAdapter.notifyDataSetChanged(dlg.getPlayers());
					}
				});
		    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
		    	dlg.show();
			}
		});
        
        
        button.setOnClickListener(new OnClickListener() {
        	boolean inZoom = false;
        	AnimatorSet set;

			@Override
			public void onClick(View v) {
				if(inZoom) {
					set = new AnimatorSet();
					
					ObjectAnimator aniScaleX = ObjectAnimator.ofFloat(composite, "scaleX", composite.getScaleX(), 1f);
					aniScaleX.setDuration(2000);
					ObjectAnimator aniScaleY = ObjectAnimator.ofFloat(composite, "scaleY", composite.getScaleY(), 1f);
					aniScaleY.setDuration(2000);
					ObjectAnimator aniTransY = ObjectAnimator.ofFloat(composite, "translationY", composite.getTranslationY(), 0f);
					aniTransY.setDuration(2000);
					ObjectAnimator aniAlpha = ObjectAnimator.ofFloat(filtersLayout, "alpha", 0f, 1f);
					aniAlpha.setStartDelay(1000);
					set.play(aniAlpha);
					aniAlpha.setDuration(1000);
					
					set.playTogether(aniScaleX, aniScaleY, aniTransY, aniAlpha);
					set.start();
					
					inZoom = false;
				} else {
					set = new AnimatorSet();
					
					ObjectAnimator aniScaleX = ObjectAnimator.ofFloat(composite, "scaleX", composite.getScaleX(), 3f);
					aniScaleX.setDuration(2000);
					ObjectAnimator aniScaleY = ObjectAnimator.ofFloat(composite, "scaleY", composite.getScaleY(), 3f);
					aniScaleY.setDuration(2000);
					ObjectAnimator aniTransY = ObjectAnimator.ofFloat(composite, "translationY", 0f, composite.getHeight());
					aniTransY.setDuration(2000);
					ObjectAnimator aniAlpha = ObjectAnimator.ofFloat(filtersLayout, "alpha", 1f, 0f);
					aniAlpha.setDuration(1000);
					
					set.playTogether(aniScaleX, aniScaleY, aniTransY, aniAlpha);
					set.start();
					
					inZoom = true;
				}
			}
		});
        RelativeLayout l1 = (RelativeLayout) findViewById(R.id.abstract_layout);
        l1.setOnTouchListener(new OnTouchListener() {
        	float diffX = 0;
			float diffY = 0;
        	double rot = 0;
        	double lastRot = 0;
        	ObjectAnimator ani;
        	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					diffX = event.getX() - (composite.getX()+composite.getWidth()/2);
					diffY = (composite.getY()+composite.getHeight()/2) - event.getY();
					rot = Math.abs(Math.toDegrees(Math.atan(diffY/diffX)));
					if(diffX>0 && diffY>0) {
						rot = 0+rot;
					} else if(diffX<0 && diffY>0) {
						rot = 180-rot;
					} else if(diffX<0 && diffY<0) {
						rot = 180+rot;
					} else if(diffX>0 && diffY<0) {
						rot = 360-rot;
					}
					lastRot = rot;
					break;
				case MotionEvent.ACTION_MOVE:
					diffX = event.getX() - (composite.getX()+composite.getWidth()/2);
					diffY = (composite.getY()+composite.getHeight()/2) - event.getY();
					rot = Math.abs(Math.toDegrees(Math.atan(diffY/diffX)));
					if(diffX>0 && diffY>0) {
						rot = 0+rot;
					} else if(diffX<0 && diffY>0) {
						rot = 180-rot;
					} else if(diffX<0 && diffY<0) {
						rot = 180+rot;
					} else if(diffX>0 && diffY<0) {
						rot = 360-rot;
					}
					composite.setRotation((float) (composite.getRotation()+(lastRot-rot)));
					lastRot = rot;
					break;
				case MotionEvent.ACTION_UP:
					float start = composite.getRotation();
					float end = Math.round(start / 60) * 60;
					Log.d("Zoo", start+" | "+end);
					ani = ObjectAnimator.ofFloat(composite, "rotation", start, end);
					ani.setDuration(300);
					ani.start();
					break;
				}
				return true;
			}
		});
    }
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle("Beenden?");
		alertDialog.setMessage("Wollen Sie das Spiel wirklich beenden?");
		//alertDialog.setIcon(R.drawable.delete_icon);

		alertDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				HomeActivity.this.finish();
			}
		});

		alertDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alertDialog.show();
	}
}

