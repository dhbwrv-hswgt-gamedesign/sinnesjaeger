package de.hrw.zoo.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import de.hrw.zoo.R;
import de.hrw.zoo.adapter.PlayerListAdapter;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.list.PlayerList;

public class HomeActivity extends Activity {
	
	private PlayerList players = new PlayerList();
	private File mStorePath;
	private Point mAppSize;
	private Point mAppCenter;
	private ListView mPlayerList;
	private PlayerListAdapter mPlayerAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mStorePath = new File(getFilesDir(),"zoo");
        if(!mStorePath.exists()) {
        	mStorePath.mkdir();
        }
        
        mAppSize = new Point();
    	getWindowManager().getDefaultDisplay().getSize(mAppSize);
    	mAppCenter = new Point(mAppSize.x/2, mAppSize.y/2);
        
        mPlayerList = (ListView) findViewById(R.id.players_list);

        players = PlayerList.Load(new File(mStorePath, "players"));        
        mPlayerAdapter = new PlayerListAdapter(this, R.layout.player_list_item, players);
        mPlayerList.setAdapter(mPlayerAdapter);        
        
        ImageView edit = (ImageView) findViewById(R.id.edit_players);
        edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getLayoutInflater().inflate(R.layout.fragment_login, null);
				final LoginDialog dlg = new LoginDialog(v.getContext(), view, mAppCenter);
				dlg.setPlayers(PlayerList.Load(new File(mStorePath, "players")));

				dlg.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
						mPlayerAdapter.notifyDataSetChanged();
					}
				});
		    	dlg.setOnGoClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dlg.getPlayers().save(new File(mStorePath, "players"));
						dlg.cancel();
						mPlayerAdapter.notifyDataSetChanged(dlg.getPlayers());
					}
				});
		    	dlg.getWindow().setLayout(mAppSize.x, mAppSize.y);
		    	dlg.show();
			}
		});
    }
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle("Beenden?");
		alertDialog.setMessage("Wollen Sie das Spiel wirklich beenden?");
		alertDialog.setIcon(R.drawable.delete_icon);

		alertDialog.setPositiveButton("JA", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				HomeActivity.this.finish();
			}
		});

		alertDialog.setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alertDialog.show();
	}
}

