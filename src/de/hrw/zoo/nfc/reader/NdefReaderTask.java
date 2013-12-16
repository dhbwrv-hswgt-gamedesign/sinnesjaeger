package de.hrw.zoo.nfc.reader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hrw.zoo.R;
import de.hrw.zoo.activity.HomeActivity;
import de.hrw.zoo.dialog.DetailScreenDialog;
import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.dialog.PlayerDialog;
import de.hrw.zoo.model.Animal;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


public class NdefReaderTask extends AsyncTask<Tag, Void, String> {
    public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "ZooApp";
	private static String result;
	
    @Override
    protected String doInBackground(Tag... params) {
        Tag tag = params[0];
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return null;
        }
        NdefMessage ndefMessage = ndef.getCachedNdefMessage();
        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    return readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "Unsupported Encoding", e);
                }
            }
        }
        return null;
    }
    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */
        byte[] payload = record.getPayload();
        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"
        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }
    
    @Override
    protected void onPostExecute(final String result) {
    	ArrayList<String> data;
    	Player player;
    	if (result != null) {

    		PlayerView[] pv = LoginDialog.getPlayerViews();
    		setStringFromNFC(result); 
    		data = generateStringArray(result);

    		switch(Integer.parseInt(data.get(0))){

    		case 10:
    			for(int i = 0; i < LoginDialog.getPlayerViews().length;i++){

    				if(pv[i].getPlayer() == null)
    				{
    					player = new Player(data.get(2));
    					player.setAvatar(Integer.parseInt(data.get(3)));
    					player.setToken(Integer.parseInt(data.get(1)));
    					player.setPoints(Integer.parseInt(data.get(4)));
    					pv[i].setPlayer(player);
    					pv[i].updateData();
    					break;
    				}	
    			}  
    			break;

    		case 20:
    			Animal animal;
    			
    			if(data != null){
    				animal = new Animal();
    				
    				animal.setArt(data.get(5));
    				animal.setGame(data.get(6));
    				animal.setId(data.get(1));
    				animal.setInfo(data.get(4));
    				animal.setName(data.get(2));
    				animal.setAvatar(data.get(3));
    				LayoutInflater li = LayoutInflater.from(HomeActivity.getActivityViewHomeActivity()); 		 
        			View view = li.inflate(R.layout.fragment_detailscreen, null);

        			final DetailScreenDialog dlg = new DetailScreenDialog( HomeActivity.getThis(), view, HomeActivity.getAppCenter(), animal);
        			dlg.getWindow().setLayout(HomeActivity.getAppSize().x, HomeActivity.getAppSize().y);
        			dlg.show();
    			}
    			
    			

    			break;

    		default:
    			break;

    		}

    	}
    }
    
    private ArrayList<String> generateStringArray(String result){
    	ArrayList<String> data = new ArrayList<String>();
    	
    	String[]test=(result.split(";"));
    	
    	for(int i = 0; i < test.length; i++)
    	{
    		data.add(test[i]);
    	}
    	
    	return data;
    }
    
    private void setStringFromNFC(String result2) {
		NdefReaderTask.setResult(result2);
		
	}
	public static String getStringFomNFC(){
    	return getResult();
    }
	public static String getResult() {
		return result;
	}
	public static void setResult(String result) {
		NdefReaderTask.result = result;
	}
}