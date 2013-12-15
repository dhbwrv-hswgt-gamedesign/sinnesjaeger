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

public class ZooListAdapter extends ArrayAdapter<Map<String, Object> > {

	private Typeface font;
    
    public ZooListAdapter(Context context, int resource, Typeface font) {
		super(context, resource);
		
		this.font = font;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	private View getCustomView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();  // we get a reference to the activity
            row = inflater.inflate(R.layout.zoo_spinner_item, parent, false);
        }

        Map<String, Object> map = getItem(position);
        TextView name = (TextView) row.findViewById (R.id.menu_title);
        name.setText((String)map.get("zoo_name"));
        name.setTypeface(font);
        
        TextView comment = (TextView) row.findViewById (R.id.zoo_comment);
        comment.setText((String)map.get("zoo_comment"));
        comment.setTypeface(font);
        
        ImageView icon = (ImageView) row.findViewById (R.id.zoo_icon);
        icon.setImageResource((Integer) map.get("zoo_icon"));

        return row;
	}
}
