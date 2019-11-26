package edu.fsu.cs.easyaspie;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class RecipesProvider extends ContentProvider {
    public static final int DBVERSION = 1;
    public final static String DBNAME = "RecipeProvider";
    public final static String TABLENAME_1 = "Recipes";
    public final static String TABLENAME_2 = "Steps";

    //Most of this code is from Lecture 10 slides

    private static MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_TABLE_1 =
            "CREATE TABLE Recipes (" +
                    " _ID INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "ingredients TEXT)";

    private static final String SQL_CREATE_TABLE_2 =
            "CREATE TABLE Steps (" +
                    " _ID INTEGER PRIMARY KEY, " +
                    "directions TEXT, " +
                    "time INTEGER, " +
                    "recipeID REFERENCES Recipes(_ID))";


    public static final Uri RecipesURI = Uri.parse("content://edu.fsu.cs.easyaspie.RecipeProvider/recipes");
    public static final Uri StepsURI = Uri.parse("content://edu.fsu.cs.easyaspie.RecipeProvider/steps");

    protected static final class MainDatabaseHelper
            extends SQLiteOpenHelper {

        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE_1);
            db.execSQL(SQL_CREATE_TABLE_2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0,
                              int arg1, int arg2) {}
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        String uriString = uri.toString();
        if(uriString.contains("recipes")) id = mOpenHelper
                .getWritableDatabase()
                .insert(TABLENAME_1, null, values);
        else if(uriString.contains("steps")) id = mOpenHelper
                .getWritableDatabase()
                .insert(TABLENAME_2, null, values);
        else id = -1;
        uri = Uri.withAppendedPath(uri, "" + id);
        return uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String uriString = uri.toString();
        if(uriString.contains("recipes"))
            return mOpenHelper
                    .getWritableDatabase()
                    .update(TABLENAME_1, values, selection, selectionArgs);
        else if(uriString.contains("steps"))
            return mOpenHelper
                    .getWritableDatabase()
                    .update(TABLENAME_2, values, selection, selectionArgs);
        else return -1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String uriString = uri.toString();
        if(uriString.contains("recipes"))
            return mOpenHelper
                    .getWritableDatabase()
                    .delete(TABLENAME_1, selection, selectionArgs);
        else if(uriString.contains("steps"))
            return mOpenHelper
                    .getWritableDatabase()
                    .delete(TABLENAME_2, selection, selectionArgs);
        else return -1;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String uriString = uri.toString();
        if(uriString.contains("recipes"))
            return mOpenHelper
                    .getReadableDatabase()
                    .query(TABLENAME_1, projection, selection, selectionArgs, null,
                            null, sortOrder);
        else if(uriString.contains("steps"))
            return mOpenHelper
                    .getReadableDatabase()
                    .query(TABLENAME_2, projection, selection, selectionArgs, null,
                            null, sortOrder);
        else return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
