package de.hrw.zoo.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import de.hrw.zoo.R;

public class HomeFragment extends Fragment {
	
	List<Map<String, Object> > list = new ArrayList<Map<String, Object> >();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        
        return rootView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
    }
}