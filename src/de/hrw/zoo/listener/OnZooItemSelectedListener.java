package de.hrw.zoo.listener;

import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import de.hrw.zoo.R;

public class OnZooItemSelectedListener implements OnItemSelectedListener {
	
	private Activity activity;
	
	public OnZooItemSelectedListener(Activity view) {
		activity = view;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
		TextView root = (TextView) activity.findViewById(R.id.circle_text);
		root.bringToFront();
		root.setText((String)map.get("zoo_name"));
		activity.onContentChanged();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}