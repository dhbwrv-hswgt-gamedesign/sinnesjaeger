package de.hrw.zoo.adapter;

import java.io.File;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;
import de.hrw.zoo.activity.HomeActivity;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.list.PlayerList;
import de.hrw.zoo.model.Player;

public class PlayerListAdapter extends ArrayAdapter<Player> {
	
	private final Context context;
    private PlayerList players;

	public PlayerListAdapter(Context context, int resource, PlayerList objects) {
		super(context, resource, objects);
		
		this.context = context;
	    this.players = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.player_list_item, parent, false);

		ImageView avatar = (ImageView) rowView.findViewById(R.id.zoo_icon);
		TextView name = (TextView) rowView.findViewById(R.id.menu_title);
		//TextView points = (TextView) rowView.findViewById(R.id.zoo_comment);

		if (players.get(position) != null) {
			avatar.setImageResource(R.drawable.icon_user);
			name.setText(players.get(position).getName());
			//points.setText(players.get(position).getPoints() + " Punkte");
		} else {
			avatar.setImageResource(R.drawable.icon_user_empty);
			name.setText("");
			//points.setText("");
		}

		return rowView;
	}
	
	public void notifyDataSetChanged(PlayerList list) {
		players = list;
		
		notifyDataSetChanged();
	}
}
