package de.hrw.zoo.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import de.hrw.zoo.R;

public class MainFragment extends Fragment {
	
	List<Map<String, Object> > list = new ArrayList<Map<String, Object> >();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Stuttgart");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("zoo_icon", R.drawable.ic_launcher);
		map.put("zoo_name", "Berlin");
		list.add(map);
		
		Spinner s = (Spinner)rootView.findViewById(R.id.location_spinner);
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), 
				(List<? extends Map<String, ?>>) list, R.layout.zoo_list_item, 
				new String[] { "zoo_icon", "zoo_name" },
				new int[] { R.id.zoo_icon, R.id.zoo_name });
		s.setAdapter(adapter);
		
        return rootView;
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
    }
}