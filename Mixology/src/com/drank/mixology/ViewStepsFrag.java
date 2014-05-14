package com.drank.mixology;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewStepsFrag extends Fragment {
	private DatabaseHandler database;
    private View v;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.view_steps, container, false);
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

        long row = ((ViewRecipeActivity)getActivity()).getRowId();
        database.open();
        Cursor c = database.getRecipe(row);
        c.moveToFirst();

        TextView descriptionView = (TextView)v.findViewById(R.id.descriptionTextView);
        String description = c.getString(c.getColumnIndex(DatabaseHandler.COL_DESCRIPTION));
        descriptionView.setText(description);
	}

}
