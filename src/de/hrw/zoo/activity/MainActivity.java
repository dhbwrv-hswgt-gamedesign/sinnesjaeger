package de.hrw.zoo.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.listener.OnZooItemSelectedListener;

public class MainActivity extends Activity {
	
	private List<Map<String, Object> > mZooList;
	private File mStorePath;
	private Point mAppSize;
	private Point mAppCenter;
	private SelectionWheel mWheel;

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
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);

        mWheel = new SelectionWheel(getBaseContext(), mAppCenter);
        layout.addView(mWheel);

        mZooList = new ArrayList<Map<String, Object> >();
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_stuttgart);
		map.put("zoo_name", "Stuttgart");
		map.put("zoo_map", R.drawable.zoo_stuttgart);
		mZooList.add(map);
		map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_berlin);
		map.put("zoo_name", "Berlin");
		map.put("zoo_map", R.drawable.zoo_berlin);
		mZooList.add(map);
		
		Spinner s = (Spinner)findViewById(R.id.location_spinner);
		SimpleAdapter adapter = new SimpleAdapter(this, 
				(List<? extends Map<String, ?>>) mZooList, 
				R.layout.zoo_list_item,
				new String[] { "zoo_name" },
				new int[] { R.id.player_name });
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnZooItemSelectedListener(this));

		TextView text = (TextView) findViewById(R.id.circle_text);
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
						Intent i = new Intent(getBaseContext(), HomeActivity.class);
						startActivity(i);
					}
				});
		    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
		    	dlg.show();
			}
		});
    }
	
	public void onContentChanged() {
		super.onContentChanged();
		
		if(mWheel != null) {
			mWheel.update();
		}
	}
}

class SelectionWheel extends View {
	
	//private List<MyGraphicsView> parts = new ArrayList<MyGraphicsView>();
	//private RectF mRect;
	private Point mCenter;
	private RectF innerCircle;
	//private RectF grassCircle;
	//private RectF outerCircle;
	private RectF outerStroke;
	//private float offset = 0;
	private Paint mPaintText;
	private ValueAnimator animation;

	public SelectionWheel(Context context, Point center) {
		super(context);

		this.mCenter = center;
		this.innerCircle = new RectF(mCenter.x-250, mCenter.y-250, mCenter.x+250, mCenter.y+250);
		//this.grassCircle = new RectF(mCenter.x-400, mCenter.y-400, mCenter.x+400, mCenter.y+400);
		//this.outerCircle = new RectF(mCenter.x-450, mCenter.y-450, mCenter.x+450, mCenter.y+450);
		this.outerStroke = new RectF(mCenter.x-250, mCenter.y-250, mCenter.x+250, mCenter.y+250);
		mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		/*
		MyGraphicsView v1 = new MyGraphicsView(getContext(), "URWALD");
		parts.add(v1);
		ScaleAnimation s = new ScaleAnimation(0f, 1f, 0f, 1f);
		s.setDuration(2000);
		s.setStartOffset(3000);
		v1.setAnimation(s);
		s.start();
		
		parts.add(new MyGraphicsView(getContext(), "EIS"));
		parts.add(new MyGraphicsView(getContext(), "WASSER"));
		parts.add(new MyGraphicsView(getContext(), "SAVANNE"));
		parts.add(new MyGraphicsView(getContext(), "LUFT"));
		parts.add(new MyGraphicsView(getContext(), "BERG & WALD"));
		*/
		
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
		mPaintText.setColor(Color.parseColor("#4082c3"));
		mPaintText.setAlpha((int) ((1-animation.getAnimatedFraction())*255));
		canvas.drawArc(outerStroke, 0, 360, true, mPaintText);
		mPaintText.setAlpha(255);
		
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setColor(Color.parseColor("#c1e3ea"));
		canvas.drawArc(innerCircle, 0, 360, true, mPaintText);
	}
}

class MyGraphicsView extends View implements OnClickListener {
    private String text;
    private Path mArc;
    private RectF rect;
    private Paint mPaintText;
    private float startAngle = 0;
    private float sweepAngle = 0;

    public MyGraphicsView(Context context, String text) {
		super(context);     
		
		this.text = text;
		mArc = new Path();

		mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setTextSize(40f);
    }
    
    public void update(float startAngle, float sweepAngle, RectF rect) {
    	this.startAngle = startAngle;
    	this.sweepAngle = sweepAngle;
    	this.rect = rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	mArc.addArc(rect, startAngle, sweepAngle);
    	mPaintText.setColor(Color.DKGRAY);
    	canvas.drawArc(rect, startAngle, sweepAngle, true, mPaintText);
    	mPaintText.setColor(Color.WHITE);
    	mPaintText.setTextAlign(Paint.Align.CENTER);
    	canvas.drawTextOnPath(text, mArc, 0, 90, mPaintText);      
    	invalidate();
    }

	@Override
	public void onClick(View v) {
		Log.d("Zoo", "Click");
	}
}


