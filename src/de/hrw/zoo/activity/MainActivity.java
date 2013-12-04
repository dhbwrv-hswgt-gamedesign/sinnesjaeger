package de.hrw.zoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import de.hrw.zoo.R;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.dialog.NewPlayerDialog;
import de.hrw.zoo.listener.OnCreatePlayerListener;

public class MainActivity extends Activity {
	
	List<Map<String, Object> > list = new ArrayList<Map<String, Object> >();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Stuttgart");
		map.put("zoo_map", R.drawable.world);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Berlin");
		map.put("zoo_map", R.drawable.ic_launcher);
		list.add(map);
		
		Spinner s = (Spinner)findViewById(R.id.location_spinner);
		
		SimpleAdapter adapter = new ZooAdapter(getBaseContext(), 
				(List<? extends Map<String, ?>>) list, 
				R.layout.zoo_list_item,
				new String[] { "zoo_icon", "zoo_name" },
				new int[] { R.id.zoo_icon, R.id.zoo_name });
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new ZooItemSelectedListener());
		
		Button button = (Button) findViewById(R.id.logout_button);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
		    	final LoginDialog dlg = new LoginDialog(v.getContext(), view);
		    	AlertDialog a = dlg.show();
		    	Point size = new Point();
		    	getWindowManager().getDefaultDisplay().getSize(size);
		    	a.getWindow().setLayout(size.x-100, size.y-100);
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
			//Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
			//ImageView root = (ImageView)findViewById(R.id.abstract_zoo);
			//root.setBackgroundResource((Integer)map.get("zoo_map"));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}


