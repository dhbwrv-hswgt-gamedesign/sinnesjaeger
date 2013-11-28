package de.hrw.zoo.dialog;

import de.hrw.zoo.R;
import de.hrw.zoo.listener.INewPlayerListener;
import de.hrw.zoo.model.Player;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginDialog extends AlertDialog.Builder {
	
	private INewPlayerListener listener;
	
	private EditText nameEdit;
	private Player newPlayer;

	public LoginDialog(Context context, View view) {
		super(context);
		
		setTitle("Neuer Spieler");
		
		nameEdit = new EditText(context);
		nameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
		
		setView(view);

		Button button = (Button) view.findViewById(R.id.add_button);
		button.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	final NewPlayerDialog dlg = new NewPlayerDialog(v.getContext());
		    	dlg.setNewPlayerEventListener(new INewPlayerListener() {
					@Override
					public void onEvent() {
						Log.i("Zoo", "new player: "+dlg.getNewPlayer());
					}
				});
		    	dlg.show();
		    }
		});
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
	
	public void setNewPlayerEventListener(INewPlayerListener listener) {
		this.listener = listener;
	}

}
	
	
