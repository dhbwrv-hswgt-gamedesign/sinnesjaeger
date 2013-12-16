package de.hrw.zoo.activity;

import java.io.File;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.PlayerListAdapter;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.dialog.PlayerDialog;
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
        final RelativeLayout composite = (RelativeLayout) findViewById(R.id.abstract_composite);
        final ImageButton logoutButton = (ImageButton) findViewById(R.id.logout_button);
        final RelativeLayout wheel = (RelativeLayout) findViewById(R.id.wheel_layout);
        final TextView button = (TextView) findViewById(R.id.dummy_button);
        final ListView playersList = (ListView) findViewById(R.id.players_list);
        final TextView wheelText = (TextView) findViewById(R.id.wheel_part_text);
        
        final ImageButton filter_sehen = (ImageButton) findViewById(R.id.filter_sehen);
        final ImageButton filter_hoeren = (ImageButton) findViewById(R.id.filter_hoeren);
        final ImageButton filter_spueren = (ImageButton) findViewById(R.id.filter_spueren);
        final ImageButton filter_sixthsense = (ImageButton) findViewById(R.id.filter_sixthsense);
        final ImageButton filter_all = (ImageButton) findViewById(R.id.filter_all);
        
        filter_all.setOnClickListener(new OnClickListener() {
        	boolean active = true;
			@Override
			public void onClick(View v) {
				if(active) {
					filter_sehen.setAlpha(0.3f);
					filter_hoeren.setAlpha(0.3f);
					filter_spueren.setAlpha(0.3f);
					filter_sixthsense.setAlpha(0.3f);
				} else {
					filter_sehen.setAlpha(1.0f);
					filter_hoeren.setAlpha(1.0f);
					filter_spueren.setAlpha(1.0f);
					filter_sixthsense.setAlpha(1.0f);
				}
				active = !active;
			}
		});
        
        final ImageView animal_dummy = (ImageView) findViewById(R.id.animal_example);
        ObjectAnimator ani = ObjectAnimator.ofFloat(animal_dummy, "translationX", 0, 20, 0);
		ani.setDuration(2000);
		ani.setInterpolator(new LinearInterpolator());
		ani.setRepeatMode(Animation.RESTART);
		ani.setRepeatCount(Animation.INFINITE);
		ani.start();
        
        Typeface miso = Typeface.createFromAsset(getAssets(), "fonts/miso.otf");
        wheelText.setTypeface(miso);

        players = PlayerList.Load(new File(mStorePath, "players"));        
        mPlayerAdapter = new PlayerListAdapter(this, R.layout.player_list_item, players);
        playersList.setAdapter(mPlayerAdapter);
        playersList.setClickable(true);
        
        playersList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				if(players.get(position) != null) {
					View view = getLayoutInflater().inflate(R.layout.fragment_player, null);
					final PlayerDialog dlg = new PlayerDialog(v.getContext(), view, mAppCenter, players.get(position));
			    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
			    	dlg.show();
				}
			}
		});
        
        logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

				alertDialog.setTitle("Beenden?");
				alertDialog.setMessage("Wollen Sie das Spiel wirklich beenden?");
				//alertDialog.setIcon(R.drawable.delete_icon);

				alertDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						HomeActivity.this.finish();
						Intent intent = new Intent(Intent.ACTION_MAIN);
					    intent.addCategory(Intent.CATEGORY_HOME);
					    startActivity(intent);
					}
				});

				alertDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				alertDialog.show();
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
					wheel.setRotation((float) (wheel.getRotation()+(lastRot-rot)));
					
					double tmp = Math.abs(wheel.getRotation() % 360);
					Log.d("Zoo", ""+tmp);
					if(tmp > 330 || tmp < 30) {
						wheelText.setText("Luft");
					} else if(tmp > 30 && tmp < 90) {
						wheelText.setText("Berg & Wald");
					} else if(tmp > 90 && tmp < 150) {
						wheelText.setText("Urwald");
					} else if(tmp > 150 && tmp < 210) {
						wheelText.setText("Eis");
					} else if(tmp > 210 && tmp < 270) {
						wheelText.setText("Wasser");
					} else if(tmp > 270 && tmp < 330) {
						wheelText.setText("Savanne");
					}
					
					lastRot = rot;
					break;
				case MotionEvent.ACTION_UP:
					float start = wheel.getRotation();
					float end = Math.round(start / 60) * 60;
					Log.d("Zoo", start+" | "+end);
					ani = ObjectAnimator.ofFloat(wheel, "rotation", start, end);
					ani.setDuration(700);
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

