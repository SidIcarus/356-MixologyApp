package com.drank.mixology;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewIngredientsFrag extends Fragment {
	private View v;
	private DatabaseHandler database;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.view_ingredients, container, false);
		this.v = v;
		return v;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		database = new DatabaseHandler(this.getActivity());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		// Please ignore horrible code
		super.onActivityCreated(savedInstanceState);
		long row = ((ViewRecipeActivity)getActivity()).getRowId();
		database.open();
		Cursor c = database.getRecipe(row);
		c.moveToFirst();
		
		//Recipe Name
		TextView nameView = (TextView)v.findViewById(R.id.view_recipe_name);
		String name = c.getString(c.getColumnIndex(DatabaseHandler.COL_NAME));
		nameView.setText(name);
		
		//Volume
		TextView volume = (TextView)v.findViewById(R.id.view_recipe_volume);
		String vol = "Total Volume: " + c.getString(c.getColumnIndex(DatabaseHandler.COL_TOTAL_VOLUME)) + " " + c.getString(c.getColumnIndex(DatabaseHandler.COL_VOLUME_UNITS));
		volume.setText(vol);

		//Difficulty
		TextView diffView = (TextView)v.findViewById(R.id.view_recipe_difficulty);
		String difficulty = "Difficulty: " + c.getInt(c.getColumnIndex(DatabaseHandler.COL_DIFFICULTY));
		diffView.setText(difficulty);
		
		//Content
		TextView contView = (TextView)v.findViewById(R.id.view_recipe_content);
		String content = "Alcohol Content: " + c.getInt(c.getColumnIndex(DatabaseHandler.COL_ALCOHOL_CONTENT)) + "%";
		contView.setText(content);
		
		//Image
		ImageView i = (ImageView)v.findViewById(R.id.drinkImage);
		i.setImageURI(Uri.parse(c.getString(c.getColumnIndex(DatabaseHandler.COL_IMAGE_FILE))));
	}

}
