package de.fhdw.hackstreetboys.calculator.shared;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBLayout extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LAYOUT_TABLE_NAME = "layout";
    public static final String LAYOUT_COLUMN_POS = "position";
    public static final String LAYOUT_COLUMN_WIDTH = "width";
    public static final String LAYOUT_COLUMN_HEIGHT = "height";
    public static final String LAYOUT_COLUMN_VALUE = "value";

    static final String DATABASE_LAYOUT_CREATE = "create table if not exists layout(position String primary key , " +
            "width text, height text, value text); ";

    public DBLayout (Context context){
        super(context, LAYOUT_TABLE_NAME, null, DATABASE_VERSION);
    }

    // erstellt eine Datenbank falls noch keine existiert, code stark angelehnt an https://javatutorial.net/android-sqlite-database-example
    @Override
    public void onCreate(SQLiteDatabase _db) {
        try {
            _db.execSQL(DATABASE_LAYOUT_CREATE);
        }catch(Exception er){
            Log.e("Error","exception");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void SaveLayoutData (List<String[]> layoutdata)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + LAYOUT_TABLE_NAME);

        for (int i=0; i < layoutdata.size(); i++){
            String[] tempData = layoutdata.get(i);
            try {
                String sqlStatement = "Insert into " + LAYOUT_TABLE_NAME + " values('" + i +"','"
                        + tempData[0] + "','" + tempData[1] + "','" + tempData[2] + "')";
                db.execSQL(sqlStatement);
            }catch(Exception ex) {
                System.out.println("Exceptions " +ex);
            }
        }
        db.close();
    }

    public /* List<String[]>*/ void RetrieveLayoutData(List<String[]> datalist){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select *  from " + LAYOUT_TABLE_NAME, null);
        //System.out.println("LOGTEXT DATABASE =" + "Select *  from " + LAYOUT_TABLE_NAME);
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String[] tempElem = {cursor.getString(1),cursor.getString(2),cursor.getString(3)};
                datalist.add(tempElem);
            }
        }
        db.close();
    }
}
