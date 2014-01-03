package de.hrw.zoo.dialog;

import android.app.AlertDialog;
import android.content.Context;

public class LogoutDialog extends AlertDialog.Builder {

	public LogoutDialog(Context context) {
		super(context);
		
		setTitle("Beenden?");
		setMessage("Wollen Sie das Spiel wirklich beenden?");
	}

}
