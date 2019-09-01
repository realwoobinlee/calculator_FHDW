package de.fhdw.hackstreetboys.calculator.shared;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LAYOUT_TABLE_NAME = "layout";
    public static final String LAYOUT_COLUMN_ID = "_id";
    public static final String LAYOUT_COLUMN_WIDTH = "width";
    public static final String PERSON_COLUMN_HEIGHT = "height";
    public static final String PERSON_COLUMN_VALUE = "value";

    static final String DATABASE_LAYOUT_CREATE = "create table if not exists LAYOUT( ID String primary key , " +
            "Width text, Height text, Value text); ";

    public SQLiteDBHelper (Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    // erstellt eine Datenbank falls noch keine existiert, code stark angelehnt an https://javatutorial.net/android-sqlite-database-example
    @Override
    public void onCreate(SQLiteDatabase _db) {
        try {
            _db.execSQL(CalculatorDB.DATABASE_LAYOUT_CREATE);
        }catch(Exception er){
            Log.e("Error","exception");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
