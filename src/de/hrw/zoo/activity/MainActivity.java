package de.hrw.zoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import de.hrw.zoo.R;

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
		s.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
				ImageView root = (ImageView)findViewById(R.id.abstract_zoo);
				root.setBackgroundResource((Integer)map.get("zoo_map"));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), 
				(List<? extends Map<String, ?>>) list, R.layout.zoo_list_item, 
				new String[] { "zoo_icon", "zoo_name" },
				new int[] { R.id.zoo_icon, R.id.zoo_name });
		s.setAdapter(adapter);
    }
	
}
