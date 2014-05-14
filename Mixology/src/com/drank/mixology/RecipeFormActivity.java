package com.drank.mixology;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

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
    
    private String imagePath;

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
            imageFileEditText.setText(extras.getString(DatabaseHandler.COL_IMAGE_FILE));
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
        String imageLocation = imageFileEditText.getText().toString();

        if (getIntent().getExtras() == null) {
            databaseConnector.insertRecipe(new Recipe(name,
                    imageLocation, totalVolume, "oz", alcoholContent, 0, difficulty, false, false, null));
        } else {
            databaseConnector.updateRecipe(rowId, new Recipe(nameEditText.getText().toString(),
                    imageLocation, totalVolume, "oz", alcoholContent, 0, difficulty, false, false, null));
        }
    }
    
    public void captureImage(View view){
    	Log.i("Image", "listener");
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	Uri fileUri = CameraHelper.getOutputMediaFile(1);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    	imagePath = fileUri.getPath();
    	startActivityForResult(intent, 100);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == 100){
    		if(resultCode == RESULT_OK){
    			final EditText image = (EditText)findViewById(R.id.imageFileEditText);
    			image.setText(imagePath);
    		}else if(resultCode == RESULT_CANCELED){
    			Log.i("Camera", "Canceled");
    			imagePath = "";
    		}else{
    			Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
    		}
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
