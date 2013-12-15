package de.hrw.zoo.dialog;

import de.hrw.zoo.listener.OnCreatePlayerListener;
import de.hrw.zoo.model.Player;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

public class NewPlayerDialog extends AlertDialog.Builder {
	
	private OnCreatePlayerListener listener;
	
	private EditText nameEdit;

	public NewPlayerDialog(Context context) {
		super(context);
		
		setTitle("Neuer Spieler");
		
		nameEdit = new EditText(context);
		nameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
		
		setView(nameEdit);
		
		setNeutralButton("Weiter", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	if(!nameEdit.getText().toString().isEmpty()) {
			    	Player newPlayer = new Player(nameEdit.getText().toString());
			    	listener.onCreate(newPlayer);
		    	}
		    }
		});
	}
	
	// NewPlayerDiaglog for NFC Reader
	public NewPlayerDialog(Context context, final String name){
		super(context);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("Neuer Spieler");
        builder.setMessage(name + " hinzufügen?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ja",
                new DialogInterface.OnClickListener() {
        	@Override
            public void onClick(DialogInterface dialog, int id) {
            	if(!name.toString().isEmpty()){
					Player newPlayer = new Player(name);
			    	listener.onCreate(newPlayer);
				}
            }
        });
        builder.setNegativeButton("Nein",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        if(!alert.isShowing())
        	alert.show();
        
	}

	public void setOnCreatePlayerListener(OnCreatePlayerListener listener) {
		this.listener = listener;
	}

}
	
	
