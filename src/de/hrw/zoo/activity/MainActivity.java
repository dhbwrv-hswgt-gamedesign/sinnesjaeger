package de.hrw.zoo.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.ZooListAdapter;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.listener.OnZooItemSelectedListener;
import de.hrw.zoo.nfc.reader.NdefReaderTask;

public class MainActivity extends Activity {
	
	private List<Map<String, Object> > mZooList;
	private File mStorePath;
	private Point mAppSize;
	private Point mAppCenter;
	private SelectionWheel mWheel;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "ZooApp";
    private NfcAdapter mNfcAdapter;
    private boolean mNfcActive;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mStorePath = new File(getFilesDir(),"zoo");
        if(!mStorePath.exists()) {
        	mStorePath.mkdir();
        }
        new File(mStorePath, "players").delete();

    	mAppSize = new Point();
    	getWindowManager().getDefaultDisplay().getSize(mAppSize);
    	mAppCenter = new Point(mAppSize.x/2, mAppSize.y/2);
    	
    	Typeface miso = Typeface.createFromAsset(getAssets(), "fonts/miso.otf");
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        mWheel = new SelectionWheel(getBaseContext(), mAppCenter);
        layout.addView(mWheel);

        mZooList = new ArrayList<Map<String, Object> >();
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_stuttgart);
		map.put("zoo_name", "Wilhelma");
		map.put("zoo_comment", "Zoologisch-Botanischer Garten Stuttgart");
		map.put("zoo_map", R.drawable.zoo_stuttgart);
		List<String> filters = new ArrayList<String>();
		filters.add("sehen"); filters.add("hoeren"); filters.add("spueren");
		map.put("zoo_filters", filters);
		mZooList.add(map);
		map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_berlin);
		map.put("zoo_name", "Zoo Berlin");
		map.put("zoo_comment", "Zoologischer Garten Berlin");
		map.put("zoo_map", R.drawable.zoo_berlin);
		filters = new ArrayList<String>();
		filters.add("sehen"); filters.add("spueren");
		map.put("zoo_filters", filters);
		mZooList.add(map);

		Spinner s = (Spinner)findViewById(R.id.location_spinner);
		ZooListAdapter adapter = new ZooListAdapter(this, R.layout.zoo_list_item, miso);
		adapter.addAll(mZooList);
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnZooItemSelectedListener(this));

		TextView text = (TextView) findViewById(R.id.circle_text);
		text.setTypeface(miso);
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
				final LoginDialog dlg = new LoginDialog(v.getContext(), view, mAppCenter);
				dlg.setPlayers(PlayerList.Load(new File(mStorePath, "players")));

				dlg.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
					}
				});
		    	dlg.setOnGoClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
						dlg.getPlayers().resetPoints();
						dlg.cancel();
						Intent intent = new Intent(getBaseContext(), HomeActivity.class);
						startActivity(intent);
					}
				});
		    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
		    	dlg.show();
			}
		});
		
		mNfcActive = false;
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
		
		// handleIntent(getIntent());
	}

	private void handleIntent(Intent intent) {
		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			String type = intent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);
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
					new NdefReaderTask().execute(tag);
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

	public void onContentChanged() {
		super.onContentChanged();
		
		if(mWheel != null) {
			mWheel.update();
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
}

class SelectionWheel extends View {
	
	private Point mCenter;
	private RectF innerCircle;
	private RectF outerCircle;
	private RectF outerStroke;
	private Paint mPaintText;
	private ValueAnimator animation;

	public SelectionWheel(Context context, Point center) {
		super(context);

		this.mCenter = center;
		this.innerCircle = new RectF(mCenter.x-250, mCenter.y-250, mCenter.x+250, mCenter.y+250);
		this.outerCircle = new RectF(mCenter.x-235, mCenter.y-235, mCenter.x+235, mCenter.y+235);
		this.outerStroke = new RectF(mCenter.x-250, mCenter.y-250, mCenter.x+250, mCenter.y+250);
		mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		animation = ValueAnimator.ofFloat(250f, 400f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(1000);
		animation.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				outerStroke.left = mCenter.x - ((Float) animation.getAnimatedValue());
				outerStroke.right = mCenter.x + ((Float) animation.getAnimatedValue());
				
				outerStroke.top = mCenter.y - ((Float) animation.getAnimatedValue());
				outerStroke.bottom = mCenter.y + ((Float) animation.getAnimatedValue());

				invalidate();
			}
		});
		animation.start();
	}
	
	public void update() {
		if(animation != null) {
			animation.start();
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {	
		mPaintText.setStyle(Paint.Style.STROKE);
		mPaintText.setStrokeWidth(20f-animation.getAnimatedFraction()*20);
		mPaintText.setColor(Color.parseColor("#254D4F"));
		mPaintText.setAlpha((int) ((1-animation.getAnimatedFraction())*255));
		canvas.drawArc(outerStroke, 0, 360, true, mPaintText);
		mPaintText.setAlpha(255);
		
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setColor(Color.parseColor("#c1e3ea"));
		canvas.drawArc(innerCircle, 0, 360, true, mPaintText);
		
		mPaintText.setStyle(Paint.Style.STROKE);
		mPaintText.setStrokeWidth(3f);
		mPaintText.setColor(Color.parseColor("#62AE95"));
		canvas.drawArc(outerCircle, 0, 360, true, mPaintText);
	}
}


