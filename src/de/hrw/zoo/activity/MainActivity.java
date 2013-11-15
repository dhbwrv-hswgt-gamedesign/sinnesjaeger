package de.hrw.zoo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import de.hrw.zoo.R;

public class MainActivity extends Activity {
	
	List<Map> list = new ArrayList<Map>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Map map = new HashMap();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Stuttgart");
		list.add(map);
		map = new HashMap();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Berlin");
		list.add(map);
		
		Spinner s = (Spinner)findViewById(R.id.location_spinner);
		SimpleAdapter adapter = new SimpleAdapter(this, 
				(List<? extends Map<String, ?>>) list, R.layout.zoo_list_item, 
				new String[] { "zoo_icon", "zoo_name" },
				new int[] { R.id.zoo_icon, R.id.zoo_name });
		s.setAdapter(adapter);
		
		Button v = (Button)findViewById(R.id.next_button);
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
}
