package com.drank.mixology;

import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class RecipeListActivity extends ListActivity {
    public static final String ROW_ID = "row_id";

    private CursorAdapter recipeAdapter;
    private ListView recipeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeListView = getListView();
        recipeListView.setOnItemClickListener(viewRecipeListener);

        TextView emptyText = (TextView) View.inflate(this,
                R.layout.list_empty_recipes_item, null);
        emptyText.setVisibility(View.GONE);
        ((ViewGroup) recipeListView.getParent()).addView(emptyText);
        recipeListView.setEmptyView(emptyText);

        String[] from = new String[] {
                DatabaseHandler.COL_NAME,
                DatabaseHandler.COL_TOTAL_VOLUME,
                DatabaseHandler.COL_ALCOHOL_CONTENT
        };

        int[] to = new int[] {
                R.id.recipeName,
                R.id.totalVolumeTextView,
                R.id.alcoholContentTextView
        };

        recipeAdapter = new SimpleCursorAdapter(RecipeListActivity.this,
                R.layout.list__drink_item, null, from, to, 0);
        setListAdapter(recipeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.actionAddRecipe:
    		Intent addRecipe = new Intent(RecipeListActivity.this, RecipeFormActivity.class);
    		startActivity(addRecipe);
    		break;
    	case R.id.actionRandomRecipe:
    		Intent randomRecipe = new Intent(RecipeListActivity.this, ViewRecipeActivity.class);
    		long randomId = getRandomId();
    		randomRecipe.putExtra(ROW_ID, randomId);
    		startActivity(randomRecipe);
    		break;
    	}
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetRecipesTask().execute((Object[]) null);
    }

    @Override
    protected void onStop() {
        Cursor cursor = recipeAdapter.getCursor();
        if (cursor != null) {
            cursor.close();
        }

        recipeAdapter.changeCursor(null);
        super.onStop();
    }
    
    private long getRandomId(){
    	DatabaseHandler db = new DatabaseHandler(this);
    	db.open();
    	Cursor c = db.getAllRecipes();
    	int total = c.getCount();
    	Random r = new Random();
    	int randInt = r.nextInt(total)+1; // Add one so the range is 1(inclusive) to total (inclusive) 
    	Log.i("Random", ""+randInt);
    	c.move(randInt);
    	int theRandomOne = c.getInt(c.getColumnIndex(DatabaseHandler.COL_ID));    	
    	c.close();
    	db.close();
    	return (long)theRandomOne;
    	
    }
    
    private AdapterView.OnItemClickListener viewRecipeListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(RecipeListActivity.this, ViewRecipeActivity.class);
                    intent.putExtra(ROW_ID, id);
                    startActivity(intent);
                }
            };


    private class GetRecipesTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseHandler databaseHandler = new DatabaseHandler(RecipeListActivity.this);

        @Override
        protected Cursor doInBackground(Object... params) {
            databaseHandler.open();
            return databaseHandler.getAllRecipes();
        }

        @Override
        protected void onPostExecute(Cursor result) {
            recipeAdapter.changeCursor(result);
            databaseHandler.close();
        }
    }
}
