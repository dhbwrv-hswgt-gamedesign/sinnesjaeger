package de.hrw.zoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.listener.OnSwipeTouchListener;

public class MainActivity extends Activity {
	
	private List<Map<String, Object> > list = new ArrayList<Map<String, Object> >();
	private Point size = new Point();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	getWindowManager().getDefaultDisplay().getSize(size);
        
        RelativeLayout lay = (RelativeLayout) findViewById(R.id.main_layout);
        
        size = new Point();
    	getWindowManager().getDefaultDisplay().getSize(size);
    	RectF rect = new RectF(size.x/2-400, size.y/2-400, size.x/2+400, size.y/2+400);
        final SelectionWheel a = new SelectionWheel(getBaseContext(), rect);
        lay.addView(a);
        /*
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(2000);
        a.setAnimation(alpha);
        alpha.start();
        */
        
        lay.setOnTouchListener(new OnSwipeTouchListener() {
            public void onSwipeTop() {
                Log.d("Zoo", "top");
            }
            public void onSwipeRight() {
            	Log.d("Zoo", "right");
            }
            public void onSwipeLeft() {
            	Log.d("Zoo", "left");
            }
            public void onSwipeBottom() {
            	Log.d("Zoo", "bottom");
            }
        });
        
        //View a = new MyGraphicsView(getBaseContext());
        //lay.addView(a);
        //ScaleAnimation zoom = new ScaleAnimation(0, 1, 0, 1,550,550);
        //zoom.setDuration(2000);
        //a.setAnimation(zoom);
        //zoom.start();        
        
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_stuttgart);
		map.put("zoo_name", "Stuttgart");
		map.put("zoo_map", R.drawable.zoo_stuttgart);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.zoo_berlin);
		map.put("zoo_name", "Berlin");
		map.put("zoo_map", R.drawable.zoo_berlin);
		list.add(map);
		
		Spinner s = (Spinner)findViewById(R.id.location_spinner);
		
		SimpleAdapter adapter = new ZooAdapter(getBaseContext(), 
				(List<? extends Map<String, ?>>) list, 
				R.layout.zoo_list_item,
				new String[] { "zoo_name" },
				new int[] { R.id.zoo_name });
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new ZooItemSelectedListener());
		
		Button button = (Button) findViewById(R.id.next_button);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {		    	
		    	View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
		    	final LoginDialog dlg = new LoginDialog(v.getContext(), view, size);
		    	dlg.getWindow().setLayout(size.x, size.y);
		    	dlg.show();
		    	/*
		    	a.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_dark);
		    	Point size = new Point();
		    	getWindowManager().getDefaultDisplay().getSize(size);
		    	a.getWindow().setLayout(size.x, size.y);
		    	a.getWindow().setBackgroundDrawableResource(R.drawable.main_background);
		    	*/
		    }
		});
    }
	
	private class ZooAdapter extends SimpleAdapter {
		public ZooAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
		}
		
	}
	
	private class ZooItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
			TextView root = (TextView) findViewById(R.id.circle_text);
			root.bringToFront();
			root.setText((String)map.get("zoo_name"));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}

class SelectionWheel extends View {
	
	private List<MyGraphicsView> parts = new ArrayList<MyGraphicsView>();
	private RectF mRect;
	private RectF innerCircle;
	private RectF grassCircle;
	private RectF outerCircle;
	private RectF outerStroke;
	private float offset = 0;
	private Paint mPaintText;

	public SelectionWheel(Context context, RectF rect) {
		super(context);
		
		this.mRect = rect;
		this.innerCircle = new RectF(rect.left+150, rect.top+150, rect.right-150, rect.bottom-150);
		this.grassCircle = new RectF(rect.left-40, rect.top-40, rect.right+40, rect.bottom+40);
		this.outerCircle = new RectF(rect.left-100, rect.top-100, rect.right+100, rect.bottom+100);
		this.outerStroke = new RectF(rect.left-110, rect.top-110, rect.right+110, rect.bottom+110);
		mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		
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
		
		update();
	}
	
	public void update() {
		float size = 360/parts.size();
		
		for(int i=0; i<parts.size(); i++) {
			parts.get(i).update(offset+(size*i), size-0.5f, mRect);
			Log.d("Zoo", "Update "+i);
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		mPaintText.setStyle(Paint.Style.STROKE);
		mPaintText.setColor(Color.parseColor("#1c5b7b"));
		canvas.drawArc(outerStroke, 0, 360, true, mPaintText);
		
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setColor(Color.parseColor("#063137"));
		canvas.drawArc(outerCircle, 0, 360, true, mPaintText);
		
		/*
		mPaintText.setColor(Color.parseColor("#246837"));
		canvas.drawArc(grassCircle, 0, 360, true, mPaintText);
		
		for(MyGraphicsView g: parts) {
			g.draw(canvas);
			Log.d("Zoo", "Draw");
		}
		*/

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
		//mArc.addCircle(600, 600, 400, Path.Direction.CW);
		
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


