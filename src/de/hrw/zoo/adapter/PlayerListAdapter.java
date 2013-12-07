package de.hrw.zoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.model.Player;

public class PlayerListAdapter extends ArrayAdapter<Player> {
	
	private final Context context;
    private final Player players[];

	public PlayerListAdapter(Context context, int resource, Player[] objects) {
		super(context, resource, objects);
		
		this.context = context;
	    this.players = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.player_list_item, parent, false);

		ImageView avatar = (ImageView) rowView.findViewById(R.id.player_avatar);
		TextView name = (TextView) rowView.findViewById(R.id.player_name);
		TextView points = (TextView) rowView.findViewById(R.id.player_points);

		if (players[position] != null) {
			avatar.setImageResource(R.drawable.user_icon);
			name.setText(players[position].getName());
			points.setText(players[position].getPoints() + " Punkte");
		} else {
			avatar.setImageResource(R.drawable.user_icon_empty);
			name.setText("");
			points.setText("");
		}

		return rowView;
	}
}
