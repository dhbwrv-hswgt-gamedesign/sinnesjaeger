package de.hrw.zoo.dialog;

import de.hrw.zoo.listener.NewPlayerListener;
import de.hrw.zoo.model.Player;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

public class NewPlayerDialog extends AlertDialog.Builder {
	
	private NewPlayerListener listener;
	
	private EditText nameEdit;
	private Player newPlayer;

	public NewPlayerDialog(Context context) {
		super(context);
		
		setTitle("Neuer Spieler");
		
		nameEdit = new EditText(context);
		nameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
		
		setView(nameEdit);
		
		setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	newPlayer = new Player(nameEdit.getText().toString());
		    	listener.onEvent();
		    }
		});
		setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
	
	public void setNewPlayerEventListener(NewPlayerListener listener) {
		this.listener = listener;
	}

}
	
	
