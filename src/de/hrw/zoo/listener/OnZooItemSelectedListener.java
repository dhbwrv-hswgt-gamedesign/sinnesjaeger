package de.hrw.zoo.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;
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
		
		TextView circleText = (TextView) activity.findViewById(R.id.circle_text);
		circleText.bringToFront();
		circleText.setText((String)map.get("zoo_name"));
		
		List<String> filters = (List<String>) map.get("zoo_filters");
		ImageView sehen = (ImageView) activity.findViewById(R.id.filter_sehen);
		ImageView hoeren = (ImageView) activity.findViewById(R.id.filter_hoeren);
		ImageView spueren = (ImageView) activity.findViewById(R.id.filter_spueren);
		ImageView sixthsense = (ImageView) activity.findViewById(R.id.filter_sixthsense);
		
		if(filters.contains("sehen")) {
			sehen.setAlpha(1.0f);
		} else {
			sehen.setAlpha(0.3f);
		}
		if(filters.contains("hoeren")) {
			hoeren.setAlpha(1.0f);
		} else {
			hoeren.setAlpha(0.3f);
		}
		if(filters.contains("spueren")) {
			spueren.setAlpha(1.0f);
		} else {
			spueren.setAlpha(0.3f);
		}
		if(filters.contains("sixthsense")) {
			sixthsense.setAlpha(1.0f);
		} else {
			sixthsense.setAlpha(0.3f);
		}
		
		activity.onContentChanged();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}