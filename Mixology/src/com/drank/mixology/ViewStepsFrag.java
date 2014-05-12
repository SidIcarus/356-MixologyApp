package com.drank.mixology;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewStepsFrag extends Fragment {
	private DatabaseHandler database;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.view_steps, container, false);
		return v;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		database = new DatabaseHandler(this.getActivity());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		// fill out steps
		super.onActivityCreated(savedInstanceState);
	}

}
