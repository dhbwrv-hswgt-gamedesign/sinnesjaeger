package de.hrw.zoo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.hrw.zoo.R;
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
		Typeface miso = Typeface.createFromAsset(context.getAssets(), "fonts/miso.otf");
		
		View rowView = inflater.inflate(R.layout.player_list_item, parent, false);

		ImageView avatar = (ImageView) rowView.findViewById(R.id.zoo_icon);
		TextView name = (TextView) rowView.findViewById(R.id.menu_title);
		name.setTypeface(miso);

		if (players.get(position) != null) {
			name.setText(players.get(position).getName());
			switch(players.get(position).getAvatar()) {
			case 0:
				avatar.setImageResource(R.drawable.avatar_adler);
				break;
			case 1:
				avatar.setImageResource(R.drawable.avatar_baer);
				break;
			case 2:
				avatar.setImageResource(R.drawable.avatar_elefant);
				break;
			case 3:
				avatar.setImageResource(R.drawable.avatar_eule);
				break;
			case 4:
				avatar.setImageResource(R.drawable.avatar_fuchs);
				break;
			default:
				avatar.setImageResource(R.drawable.avatar_empty);
				break;
			}
		} else {
			avatar.setImageResource(R.drawable.avatar_empty);
			name.setText("");
		}

		return rowView;
	}
	
	public void notifyDataSetChanged(PlayerList list) {
		players = list;
		
		notifyDataSetChanged();
	}
}
