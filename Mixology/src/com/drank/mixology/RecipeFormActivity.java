package com.drank.mixology;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.drank.mixology.model.Recipe;

public class RecipeFormActivity extends Activity {
    private long rowId;

    private CheckBox isDefaultCheckBox;
    private EditText alcoholContentEditText;
    private EditText categoryEditText;
    private EditText imageFileEditText;
    private EditText lastMadeEditText;
    private EditText nameEditText;
    private EditText totalVolumeEditText;
    private RatingBar difficultyRatingBar;
    private RatingBar ratingRatingBar;
    private Spinner volumeUnitsSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_form);

        alcoholContentEditText = (EditText) findViewById(R.id.alcoholContentEditText);
        difficultyRatingBar = (RatingBar) findViewById(R.id.difficultyRatingBar);
        imageFileEditText = (EditText) findViewById(R.id.imageFileEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        totalVolumeEditText = (EditText) findViewById(R.id.totalVolumeEditText);
        volumeUnitsSpinner = (Spinner) findViewById(R.id.volumeUnitsSpinner);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            rowId = extras.getLong(RecipeListActivity.ROW_ID);
            alcoholContentEditText.setText(extras.getString(DatabaseHandler.COL_ALCOHOL_CONTENT));
            difficultyRatingBar.setRating(extras.getInt(DatabaseHandler.COL_DIFFICULTY));
            nameEditText.setText(extras.getString(DatabaseHandler.COL_NAME));
            totalVolumeEditText.setText(extras.getString(DatabaseHandler.COL_TOTAL_VOLUME));
        }

        Button saveRecipeButton =
                (Button) findViewById(R.id.saveRecipeButton);
        saveRecipeButton.setOnClickListener(saveRecipeButtonClickListener);
    }

    private void saveRecipe() {
        DatabaseHandler databaseConnector = new DatabaseHandler(this);

        String name = nameEditText.getText().toString();
        double totalVolume = Double.parseDouble(totalVolumeEditText.getText().toString());
        double alcoholContent = Double.parseDouble(alcoholContentEditText.getText().toString());
        int difficulty = (int) difficultyRatingBar.getRating();

        if (getIntent().getExtras() == null) {
            databaseConnector.insertRecipe(new Recipe(name,
                    "", totalVolume, "", alcoholContent, 0, difficulty, false, false, null));
        } else {
            databaseConnector.updateRecipe(rowId, new Recipe(nameEditText.getText().toString(),
                    "", totalVolume, "", alcoholContent, 0, difficulty, false, false, null));
        }
    }

    View.OnClickListener saveRecipeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nameEditText.getText().length() != 0) {
                AsyncTask<Object, Object, Object> saveRecipeTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                saveRecipe();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                finish();
                            }
                        };

                saveRecipeTask.execute((Object[]) null);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeFormActivity.this)
                        .setTitle(R.string.error_validate_name_title)
                        .setMessage(R.string.error_validate_name_message)
                        .setPositiveButton(R.string.error_validate_name_button, null);
                builder.show();
            }
        }
    };
}
