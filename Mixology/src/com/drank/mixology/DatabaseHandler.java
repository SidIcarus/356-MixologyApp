package com.drank.mixology;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drank.mixology.model.Ingredient;
import com.drank.mixology.model.Recipe;

import java.util.Date;

/**
 * Created by Chris on 3/21/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_IMAGE_FILE = "imageFile";
    public static final String COL_TOTAL_VOLUME = "totalVolume";
    public static final String COL_VOLUME_UNITS = "volumeUnits";
    public static final String COL_ALCOHOL_CONTENT = "alcoholContent";
    public static final String COL_RATING = "rating";
    public static final String COL_DIFFICULTY = "difficulty";
    public static final String COL_FAVORITE = "favorite";
    public static final String COL_DEFAULT = "isDefault";
    public static final String COL_LAST_MADE = "lastMade";
    public static final String COL_CATEGORY = "category";
    public static final String DB_NAME = "mixology";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TAG = "Database-Handler";
    public static final String TYPE_ID = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String TYPE_NAME = "VARCHAR(100) NOT NULL";
    public static final String TYPE_IMAGE_FILE = "VARCHAR(50)";
    public static final String TYPE_TOTAL_VOLUME = "DECIMAL(4,2)";
    public static final String TYPE_VOLUME_UNITS = "VARCHAR(20)";
    public static final String TYPE_ALCOHOL_CONTENT = "DECIMAL(2,2)";
    public static final String TYPE_RATING = "DECIMAL(1,2)";
    public static final String TYPE_DIFFICULTY = "TINYINT";
    public static final String TYPE_FAVORITE = "BOOLEAN";
    public static final String TYPE_DEFAULT = "BOOLEAN";
    public static final String TYPE_LAST_MADE = "DATE";
    public static final String TYPE_CATEGORY = "VARCHAR(50)";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRecipesTable = "";
        createRecipesTable += "CREATE TABLE " + TABLE_RECIPES + " (";
        createRecipesTable += String.format("%s %s, ", COL_ID, TYPE_ID);
        createRecipesTable += String.format("%s %s, ", COL_NAME, TYPE_NAME);
        createRecipesTable += String.format("%s %s, ", COL_IMAGE_FILE, TYPE_IMAGE_FILE);
        createRecipesTable += String.format("%s %s, ", COL_TOTAL_VOLUME, TYPE_TOTAL_VOLUME);
        createRecipesTable += String.format("%s %s, ", COL_VOLUME_UNITS, TYPE_VOLUME_UNITS);
        createRecipesTable += String.format("%s %s, ", COL_ALCOHOL_CONTENT, TYPE_ALCOHOL_CONTENT);
        createRecipesTable += String.format("%s %s, ", COL_RATING, TYPE_RATING);
        createRecipesTable += String.format("%s %s, ", COL_DIFFICULTY, TYPE_DIFFICULTY);
        createRecipesTable += String.format("%s %s, ", COL_FAVORITE, TYPE_FAVORITE);
        createRecipesTable += String.format("%s %s, ", COL_DEFAULT, TYPE_DEFAULT);
        createRecipesTable += String.format("%s %s, ", COL_LAST_MADE, TYPE_LAST_MADE);
        createRecipesTable += ");";
        db.execSQL(createRecipesTable);
        
        String createIngredientsTable = "";
        createIngredientsTable += "CREATE TABLE " + TABLE_INGREDIENTS + " (";
        createIngredientsTable += String.format("%s %s, ", COL_ID, TYPE_ID);
        createIngredientsTable += String.format("%s %s, ", COL_NAME, TYPE_NAME);
        createIngredientsTable += String.format("%s %s, ", COL_IMAGE_FILE, TYPE_IMAGE_FILE);
        createIngredientsTable += String.format("%s %s, ", COL_TOTAL_VOLUME, TYPE_TOTAL_VOLUME);
        createIngredientsTable += String.format("%s %s, ", COL_VOLUME_UNITS, TYPE_VOLUME_UNITS);
        createIngredientsTable += String.format("%s %s, ", COL_ALCOHOL_CONTENT, TYPE_ALCOHOL_CONTENT);
        createIngredientsTable += String.format("%s %s, ", COL_DEFAULT, TYPE_DEFAULT);
        createIngredientsTable += String.format("%s %s, ", COL_CATEGORY, TYPE_CATEGORY);
        createIngredientsTable += ");";
        db.execSQL(createIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }
    
    public void insertIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, ingredient.getName());
        values.put(COL_IMAGE_FILE, ingredient.getImageFile());
        values.put(COL_TOTAL_VOLUME, ingredient.getTotalVolume());
        values.put(COL_VOLUME_UNITS, ingredient.getVolumeUnits());
        values.put(COL_ALCOHOL_CONTENT, ingredient.getAlcoholContent());
        values.put(COL_DEFAULT, ingredient.isDefault());
        values.put(COL_CATEGORY, ingredient.getCategory());

        db.insert(TABLE_INGREDIENTS, null, values);
        db.close();
    }

    public void insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, recipe.getName());
        values.put(COL_IMAGE_FILE, recipe.getImageFile());
        values.put(COL_TOTAL_VOLUME, recipe.getTotalVolume());
        values.put(COL_VOLUME_UNITS, recipe.getVolumeUnits());
        values.put(COL_ALCOHOL_CONTENT, recipe.getAlcoholContent());
        values.put(COL_RATING, recipe.getRating());
        values.put(COL_DIFFICULTY, recipe.getDifficulty());
        values.put(COL_FAVORITE, recipe.isFavorite());
        values.put(COL_DEFAULT, recipe.isDefault());
        values.put(COL_LAST_MADE, "2000-01-01");//recipe.getLastMade().toString()); // #XXX YYYY-MM-DD

        db.insert(TABLE_RECIPES, null, values);
        db.close();
    }

    public void selectIngredient() {
        Log.d(TAG, "Select Ingredient");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INGREDIENTS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String imageFile = cursor.getString(1);
            double totalVolume = cursor.getDouble(2);
            String volumeUnits = cursor.getString(3);
            double alcoholContent = cursor.getDouble(4);
            boolean isDefault = (cursor.getInt(5) == 1) ? true : false;
            String category = cursor.getString(6);

            Ingredient ingredient = new Ingredient(name, imageFile, totalVolume, volumeUnits,
                    alcoholContent, isDefault, category);

            Log.d(TAG, ingredient.getName());
        }
    }

    public void selectRecipe() {
        Log.d(TAG, "Select Recipe");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RECIPES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String imageFile = cursor.getString(1);
            double totalVolume = cursor.getDouble(2);
            String volumeUnits = cursor.getString(3);
            double alcoholContent = cursor.getDouble(4);
            double rating = cursor.getDouble(5);
            int difficulty = cursor.getInt(6);
            boolean favorite = (cursor.getInt(7) == 1) ? true : false;
            boolean isDefault = (cursor.getInt(8) == 1) ? true : false;
            Date lastMade = null;

            Recipe recipe = new Recipe(name, imageFile, totalVolume, volumeUnits, alcoholContent,
                    rating, difficulty, favorite, isDefault, lastMade);

            Log.d(TAG, recipe.getName());
        }
    }
}
