package de.hrw.zoo.nfc.reader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hrw.zoo.dialog.LoginDialog;
import de.hrw.zoo.model.Player;
import de.hrw.zoo.view.PlayerView;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;


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
        	
        	for(int i = 0; i < LoginDialog.getPlayerViews().length;i++){
        		
        		if(pv[i].getPlayer() == null)
        		{
        			player = new Player(data.get(1));
        			player.setAvatar(data.get(2));
        			player.setToken(data.get(0));
        			player.setPoints(Integer.parseInt(data.get(3)));
        			pv[i].setPlayer(player);
        			pv[i].updateData();
        			break;
        		}
        		
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