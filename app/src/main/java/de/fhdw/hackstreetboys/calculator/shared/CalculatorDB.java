package de.fhdw.hackstreetboys.calculator.shared;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class CalculatorDB {
    public static SQLiteDatabase db;
    public static final String DATABASE_NAME = "DB.db";
    static final int DATABASE_VERSION = 1;
    public static final String LAYOUT_TABLE_NAME = "layout";
    public static final String LAYOUT_COLUMN_POS = "position";
    public static final String LAYOUT_COLUMN_WIDTH = "width";
    public static final String LAYOUT_COLUMN_HEIGHT = "height";
    public static final String LAYOUT_COLUMN_VALUE = "value";
    Context context;
    private static SQLiteDBHelper dbHelper;

    static final String DATABASE_LAYOUT_CREATE = "create table if not exists LAYOUT( position String primary key , " +
            "width text, height text, value text); ";
    public void CalculatorDB(Context _context) {
        context = _context;
        dbHelper = new SQLiteDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public  CalculatorDB open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();        return this;
    }
    // Method to close the Database
    public void close()
    {
        db.close();
    }
    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void SaveLayoutData (List<String[]> layoutdata)
    {
        db = dbHelper.getWritableDatabase();
        db.delete(LAYOUT_TABLE_NAME,null,null);

        for (int i=0; i < layoutdata.size(); i++){
            String[] tempData = layoutdata.get(i);
            try {
                ContentValues newValues = new ContentValues();
                // Assign values for each column.
                newValues.put(LAYOUT_COLUMN_POS, i);
                newValues.put(LAYOUT_COLUMN_WIDTH, tempData[0]);
                newValues.put(LAYOUT_COLUMN_HEIGHT, tempData[1]);
                newValues.put(LAYOUT_COLUMN_VALUE, tempData[2]);

                // Insert the row into your table
                long result=db.insert(LAYOUT_TABLE_NAME, null, newValues);
            }catch(Exception ex) {
                System.out.println("Exceptions " +ex);
            }
        }
    }

    public List<String[]> RetrieveLayoutData (){
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select *  from " + LAYOUT_TABLE_NAME, null);
        List<String[]> tempList = new ArrayList<String[]>();
        if (cursor != null) {
            do {
                String[] tempElem = {cursor.getString(1),cursor.getString(2),cursor.getString(3)};
                tempList.add(tempElem);
            }while(cursor.moveToNext());
        }
        return tempList;
    }

}
