package com.drank.mixology;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.drank.mixology.model.Recipe;

public class RecipeFormActivity extends Activity {
    private long rowId;

    private EditText nameEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_form);

        nameEditText = (EditText) findViewById(R.id.nameEditText);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            rowId = extras.getLong(RecipeListActivity.ROW_ID);
            nameEditText.setText(extras.getString(DatabaseHandler.COL_NAME));
        }

        Button saveRecipeButton =
                (Button) findViewById(R.id.saveRecipeButton);
        saveRecipeButton.setOnClickListener(saveRecipeButtonClickListener);
    }

    private void saveRecipe() {
        DatabaseHandler databaseConnector = new DatabaseHandler(this);

        if (getIntent().getExtras() == null) {
            databaseConnector.insertRecipe(new Recipe(nameEditText.getText().toString(),
                    "", 0, "", 0, 0, 0, false, false, null));
        } else {
            databaseConnector.updateRecipe(rowId, new Recipe(nameEditText.getText().toString(),
                    "", 0, "", 0, 0, 0, false, false, null));
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
