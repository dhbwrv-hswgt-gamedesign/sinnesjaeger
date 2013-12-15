package de.hrw.zoo.adapter;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;

public class MenuAdapter extends ArrayAdapter<Map<String, Object> > {
	
	private Typeface font;

	public MenuAdapter(Context context, int resource, Typeface font) {
		super(context, resource);
		
		this.font = font;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
		View row = inflater.inflate(R.layout.menu_list_item, parent, false);

		Map<String, Object> map = getItem(position);
		ImageView icon = (ImageView) row.findViewById(R.id.menu_icon);
		icon.setImageResource(R.drawable.users);
		TextView title = (TextView) row.findViewById(R.id.menu_title);
		title.setText("Test");
		title.setTypeface(font);

		return row;
	}
}
