package com.drank.mixology;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drank.mixology.model.Ingredient;
import com.drank.mixology.model.Recipe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 3/21/14.
 */
public class DatabaseHandler {

    public static final int DB_VERSION = 1;

    public static final String COL_ID = "_id";
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
    public static final String TYPE_NAME = "TEXT NOT NULL";
    public static final String TYPE_IMAGE_FILE = "TEXT";
    public static final String TYPE_TOTAL_VOLUME = "DECIMAL(4,2)";
    public static final String TYPE_VOLUME_UNITS = "TEXT";
    public static final String TYPE_ALCOHOL_CONTENT = "DECIMAL(2,2)";
    public static final String TYPE_RATING = "DECIMAL(1,2)";
    public static final String TYPE_DIFFICULTY = "TINYINT";
    public static final String TYPE_FAVORITE = "BOOLEAN";
    public static final String TYPE_DEFAULT = "BOOLEAN";
    public static final String TYPE_LAST_MADE = "DATE";
    public static final String TYPE_CATEGORY = "TEXT";

    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseHandler(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void insertIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, ingredient.getName());
        values.put(COL_IMAGE_FILE, ingredient.getImageFile());
        values.put(COL_TOTAL_VOLUME, ingredient.getTotalVolume());
        values.put(COL_VOLUME_UNITS, ingredient.getVolumeUnits());
        values.put(COL_ALCOHOL_CONTENT, ingredient.getAlcoholContent());
        values.put(COL_DEFAULT, ingredient.isDefault());
        values.put(COL_CATEGORY, ingredient.getCategory());

        open();
        database.insert(TABLE_INGREDIENTS, null, values);
        close();
    }

    public void insertRecipe(Recipe recipe) {
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
        values.put(COL_LAST_MADE, "2000-01-01");

        open();
        database.insert(TABLE_RECIPES, null, values);
        close();
    }

    public void updateRecipe(long id, Recipe recipe) {
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
        values.put(COL_LAST_MADE, "2000-01-01");

        open();
        database.update(TABLE_RECIPES, values, "_id=" + id, null);
        close();
    }

    public Cursor getAllRecipes() {
        return database.query(TABLE_RECIPES, new String[] {"_id", COL_NAME, COL_TOTAL_VOLUME,
                COL_ALCOHOL_CONTENT, COL_DIFFICULTY},
                null, null, null, null, COL_NAME);
    }

    public Cursor getRecipe(long id) {
        return database.query(TABLE_RECIPES, null, "_id=?",new String[]{""+id}, null, null, null, null);
    }

    public void deleteRecipe(long id) {
        open();
        database.delete(TABLE_RECIPES, "_id=" + id, null);
        close();
    }


    public Cursor getAllIngredients() {
        return database.query(TABLE_INGREDIENTS, new String[] {"_id", COL_NAME}, null, null, null, null,
                COL_NAME);
    }

    public Cursor getIngredient(long id) {
        return database.query(TABLE_INGREDIENTS, null, "_id=" + id, null, null, null, null);
    }

    public void deleteIngredient(long id) {
        open();
        database.delete(TABLE_INGREDIENTS, "_id=" + id, null);
        close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createRecipesTable = "";
            createRecipesTable += "CREATE TABLE " + TABLE_RECIPES + " (";
            createRecipesTable += "_id integer primary key autoincrement, ";
            createRecipesTable += String.format("%s %s, ", COL_NAME, TYPE_NAME);
            createRecipesTable += String.format("%s %s, ", COL_IMAGE_FILE, TYPE_IMAGE_FILE);
            createRecipesTable += String.format("%s %s, ", COL_TOTAL_VOLUME, TYPE_TOTAL_VOLUME);
            createRecipesTable += String.format("%s %s, ", COL_VOLUME_UNITS, TYPE_VOLUME_UNITS);
            createRecipesTable += String.format("%s %s, ", COL_ALCOHOL_CONTENT, TYPE_ALCOHOL_CONTENT);
            createRecipesTable += String.format("%s %s, ", COL_RATING, TYPE_RATING);
            createRecipesTable += String.format("%s %s, ", COL_DIFFICULTY, TYPE_DIFFICULTY);
            createRecipesTable += String.format("%s %s, ", COL_FAVORITE, TYPE_FAVORITE);
            createRecipesTable += String.format("%s %s, ", COL_DEFAULT, TYPE_DEFAULT);
            createRecipesTable += String.format("%s %s", COL_LAST_MADE, TYPE_LAST_MADE);
            createRecipesTable += ");";
            db.execSQL(createRecipesTable);

            String createIngredientsTable = "";
            createIngredientsTable += "CREATE TABLE " + TABLE_INGREDIENTS + " (";
            createIngredientsTable += "_id integer primary key autoincrement, ";
            createIngredientsTable += String.format("%s %s, ", COL_NAME, TYPE_NAME);
            createIngredientsTable += String.format("%s %s, ", COL_IMAGE_FILE, TYPE_IMAGE_FILE);
            createIngredientsTable += String.format("%s %s, ", COL_TOTAL_VOLUME, TYPE_TOTAL_VOLUME);
            createIngredientsTable += String.format("%s %s, ", COL_VOLUME_UNITS, TYPE_VOLUME_UNITS);
            createIngredientsTable += String.format("%s %s, ", COL_ALCOHOL_CONTENT, TYPE_ALCOHOL_CONTENT);
            createIngredientsTable += String.format("%s %s, ", COL_DEFAULT, TYPE_DEFAULT);
            createIngredientsTable += String.format("%s %s", COL_CATEGORY, TYPE_CATEGORY);
            createIngredientsTable += ");";
            db.execSQL(createIngredientsTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
