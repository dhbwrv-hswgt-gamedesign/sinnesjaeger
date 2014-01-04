package de.hrw.zoo.activity;

import java.io.File;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Point;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import de.hrw.zoo.animator.AnimalAnimator;
import de.hrw.zoo.animator.WheelAnimator;
import de.hrw.zoo.dialog.LogoutDialog;
import de.hrw.zoo.dialog.MapDialog;
import de.hrw.zoo.dialog.PlayerDialog;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.listener.OnFilterClickListener;
import de.hrw.zoo.listener.OnFiltersToggleClickListener;
import de.hrw.zoo.nfc.reader.NdefReaderTask;
import de.hrw.zoo.view.AnimalView;

public class HomeActivity extends Activity {
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "ZooApp";
	
    private static Point mAppSize;
	private static Point mAppCenter;
	
	private File mStorePath;
	private PlayerList mPlayers;
	private PlayerListAdapter mPlayerAdapter;
	private boolean mNfcActive;
	private NfcAdapter mNfcAdapter;
	
	private Typeface mFontMiso;

	public static Point getAppSize(){
		return mAppSize;
	}
	
	public static Point getAppCenter(){
		return mAppCenter;
	}
	
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
    	
        mFontMiso = Typeface.createFromAsset(getAssets(), "fonts/miso.otf");
        
    	final LinearLayout filtersLayout = (LinearLayout) findViewById(R.id.filters_layout);
        final RelativeLayout composite = (RelativeLayout) findViewById(R.id.abstract_composite);
        final ImageButton logoutButton = (ImageButton) findViewById(R.id.logout_button);
        final RelativeLayout wheel = (RelativeLayout) findViewById(R.id.wheel_layout);
        final TextView zoomButton = (TextView) findViewById(R.id.dummy_button);
        final ListView playersList = (ListView) findViewById(R.id.players_list);
        final TextView wheelText = (TextView) findViewById(R.id.wheel_part_text);
        
        final ImageButton filterSehen = (ImageButton) findViewById(R.id.filter_sehen);
        final ImageButton filterHoeren = (ImageButton) findViewById(R.id.filter_hoeren);
        final ImageButton filterSpueren = (ImageButton) findViewById(R.id.filter_spueren);
        final ImageButton filterSixthSense = (ImageButton) findViewById(R.id.filter_sixthsense);
        final ImageButton filterAll = (ImageButton) findViewById(R.id.filter_all);
        
        final AnimalView animalFledermaus = (AnimalView) findViewById(R.id.animal_fledermaus);
        final AnimalView animalPinguin = (AnimalView) findViewById(R.id.animal_pinguin);
        final AnimalView animalSchlange = (AnimalView) findViewById(R.id.animal_schlange);
        final AnimalView animalAlder = (AnimalView) findViewById(R.id.animal_adler);
        final AnimalView animalSchmetterling = (AnimalView) findViewById(R.id.animal_schmetterling);
        
        final ImageView mapIcon = (ImageView) findViewById(R.id.map_icon);
        
        mapIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getLayoutInflater().inflate(R.layout.fragment_map, null);
				final MapDialog dlg = new MapDialog(v.getContext(), view);
		    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
		    	dlg.show();
			}
		});
        
        
        ImageView[] animalsSehen = {animalAlder, animalSchmetterling};
        filterSehen.setOnClickListener(new OnFilterClickListener(animalsSehen));
        
        ImageView[] animalsHoeren = {animalFledermaus};
        filterHoeren.setOnClickListener(new OnFilterClickListener(animalsHoeren));
        
        ImageView[] animalsSpueren = {animalSchlange};
        filterSpueren.setOnClickListener(new OnFilterClickListener(animalsSpueren));
        
        ImageView[] animalsSixthSense = {};
        filterSixthSense.setOnClickListener(new OnFilterClickListener(animalsSixthSense));

        ImageButton[] buttonsFilter = {filterSehen, filterHoeren, filterSpueren, filterSixthSense};
        ImageView[] animalsOthers = {animalPinguin};
        filterAll.setOnClickListener(new OnFiltersToggleClickListener(buttonsFilter, animalsOthers));
        filterAll.callOnClick();
       
        wheelText.setTypeface(mFontMiso);

        mPlayers = new PlayerList();
        mPlayers = PlayerList.Load(new File(mStorePath, "players"));
        mPlayerAdapter = new PlayerListAdapter(this, R.layout.player_list_item, mPlayers);
        playersList.setAdapter(mPlayerAdapter);
        playersList.setClickable(true);
        
        playersList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				if(mPlayers.get(position) != null) {
					View view = getLayoutInflater().inflate(R.layout.fragment_player, null);
					final PlayerDialog dlg = new PlayerDialog(v.getContext(), view, mAppCenter, mPlayers.get(position));
			    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
			    	dlg.show();
				}
			}
		});
        
        logoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogoutDialog dialog = new LogoutDialog(v.getContext());

				dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						HomeActivity.this.finish();
						backToHomeScreen();
					}
				});

				dialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				dialog.show();
			}
		});
        
        animalFledermaus.setAnimator(new AnimalAnimator(animalFledermaus, "translationX", 0, 20, 0));
        animalPinguin.setAnimator(new AnimalAnimator(animalPinguin, "translationY", 0, 20, 0));
        animalSchlange.setAnimator(new AnimalAnimator(animalSchlange, "ScaleY", 1f, 0.5f, 1f));
        animalAlder.setAnimator(new AnimalAnimator(animalAlder, "TranslationY", 0, 20, 0));
        animalSchmetterling.setAnimator(new AnimalAnimator(animalSchmetterling, "ScaleX", 1f, 0.5f, 1f));
    	
    	animalFledermaus.setEnabled(false);
		animalPinguin.setEnabled(false);
		animalSchlange.setEnabled(false);
		animalAlder.setEnabled(false);
		animalSchmetterling.setEnabled(false);
        
        zoomButton.setOnClickListener(new OnClickListener() {
        	boolean inZoom = false;
        	WheelAnimator ani;

			@Override
			public void onClick(View v) {
				if(inZoom) {
					ani = new WheelAnimator(composite, filtersLayout);
					ani.zoomIn();
					
					animalFledermaus.setEnabled(false);
					animalPinguin.setEnabled(false);
					animalSchlange.setEnabled(false);
					animalAlder.setEnabled(false);
					animalSchmetterling.setEnabled(false);
					
					inZoom = false;
				} else {
					ani = new WheelAnimator(composite, filtersLayout);
					ani.zoomIn();
					
					animalFledermaus.setEnabled(true);
					animalPinguin.setEnabled(true);
					animalSchlange.setEnabled(true);
					animalAlder.setEnabled(true);
					animalSchmetterling.setEnabled(true);
					
					inZoom = true;
				}
			}
		});
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.abstract_layout);
        layout.setOnTouchListener(new OnTouchListener() {
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
					rot = getRotation(diffY, diffX);
					lastRot = rot;
					break;
				case MotionEvent.ACTION_MOVE:
					diffX = event.getX() - (composite.getX()+composite.getWidth()/2);
					diffY = (composite.getY()+composite.getHeight()/2) - event.getY();
					rot = getRotation(diffY, diffX);
					wheel.setRotation((float) (wheel.getRotation()+(lastRot-rot)));
					double tmp = Math.abs(wheel.getRotation() % 360);
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
					ani = ObjectAnimator.ofFloat(wheel, "rotation", start, end);
					ani.setDuration(700);
					ani.start();
					break;
				}
				return true;
			}
		});
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			// Stop here, we definitely need NFC
			Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
			//finish();
			return;
		}
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this, "NFC is disabled", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "NFC is enabled.", Toast.LENGTH_LONG).show();
			mNfcActive = true;
		}
    }
	
	private void handleIntent(Intent intent) {
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			String type = intent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask(this).execute(tag);
			} else {
				Log.d(TAG, "Wrong mime type: " + type);
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			// In case we would still use the Tech Discovered Intent
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();
			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask(this).execute(tag);
					break;
				}
			}
		}

	}

	private void setupForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {

		final Intent intent = new Intent(activity.getApplicationContext(),
				activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		final PendingIntent pendingIntent = PendingIntent.getActivity(
				activity.getApplicationContext(), 0, intent, 0);
		IntentFilter[] filters = new IntentFilter[1];
		String[][] techList = new String[][] {};
		// Notice that this is the same filter as in our manifest.
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}
		adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
	}

	private void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
		if(mNfcActive) {
			adapter.disableForegroundDispatch(activity);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        if(mNfcActive) {
        	setupForegroundDispatch(this, mNfcAdapter);
        }
    }

	@Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
		if(mNfcActive) {
			stopForegroundDispatch(this, mNfcAdapter);
		}
        super.onPause();
    }
	
	@Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
		if(mNfcActive) {
			handleIntent(intent);
		}
    }
	
	@Override
	public void onBackPressed() {
		LogoutDialog dialog = new LogoutDialog(this);

		dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				HomeActivity.this.finish();
			}
		});

		dialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		dialog.show();
	}
	
	private double getRotation(float diffX, float diffY) {
		double rot = Math.toDegrees(Math.atan(diffY/diffX));
		if(diffX>0 && diffY>0) {
			rot = 0+rot;
		} else if(diffX<0 && diffY>0) {
			rot = 180+rot;
		} else if(diffX<0 && diffY<0) {
			rot = 180+rot;
		} else if(diffX>0 && diffY<0) {
			rot = 360+rot;
		}
		
		return rot;
	}
	
	private void backToHomeScreen() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    startActivity(intent);
	}
	
}

