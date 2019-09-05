package de.fhdw.hackstreetboys.calculator.shared;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBCalculator extends SQLiteOpenHelper {

    //database data
    private static final int DATABASE_VERSION = 1;

    // tables data
    public static final String LAYOUT_TABLE_NAME = "layout";
    public static final String HISTORY_TABLE_NAME = "history";


    //sql statements to create tables in the database
    static final String DATABASE_LAYOUT_CREATE = "create table if not exists layout" +
            "(position String primary key , width text, height text, value text); ";
    static final String DATABASE_HISTORY_CREATE = "create table if not exists history" +
            "( term text, result text); ";

    public DBCalculator (Context context){
        super(context, LAYOUT_TABLE_NAME, null, DATABASE_VERSION);
    }

    // creates the two tables called "history" and "layout" on creation of the database
    @Override
    public void onCreate(SQLiteDatabase _db) {
        try {
            _db.execSQL(DATABASE_LAYOUT_CREATE);
            _db.execSQL(DATABASE_HISTORY_CREATE);
        }catch(Exception er){
            Log.e("Error","exception");
        }
    }

    /*
    method to upgrade the database,
    not really intended to be used since changes and complexity of data are limited
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Method in order to save layout data for as many tiles as necessary

    public void SaveLayoutData (List<String[]> layoutdata)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + LAYOUT_TABLE_NAME);

        for (int i=0; i < layoutdata.size(); i++){
            String[] tempData = layoutdata.get(i);
            try {
                String sqlStatement = "Insert into " + LAYOUT_TABLE_NAME + " values('" + i +"','" +
                         tempData[0] + "','" + tempData[1] + "','" + tempData[2] + "')";
                db.execSQL(sqlStatement);
            }catch(Exception ex) {
                System.out.println("Exceptions " +ex);
            }
        }
        db.close();
    }

    /* Since returning a String-List didn't work, it is being
     retrieved without a distinct return value */

    public /* List<String[]>*/ void RetrieveLayoutData(List<String[]> dataList){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select *  from " + LAYOUT_TABLE_NAME, null);
        //System.out.println("LOGTEXT DATABASE =" + "Select *  from " + LAYOUT_TABLE_NAME);
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String[] tempElem = {cursor.getString(1),cursor.getString(2),cursor.getString(3)};
                dataList.add(tempElem);
            }
        }
        db.close();
    }

    //Method to save Data into the history table. Does not require indexing for our purposes

    public void SaveHistoryData (List<String> termData, String resultData)
    {
        String historyString = "";
        for(int i=0; i < termData.size(); i++){
            String tempDataAnnex = termData.get(i);
            historyString += tempDataAnnex;
            if (i != termData.size() - 1) {
                historyString += ",";
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String sqlStatementHistory = "insert into " + HISTORY_TABLE_NAME + " values('"
                + historyString + "','" + resultData + "')";
        db.execSQL(sqlStatementHistory);

        db.close();
    }

    //method to retrieve data from the history table

    public void RetrieveHistoryData(List<String[]> dataList){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select *  from " + HISTORY_TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                String[] tempElem = {cursor.getString(0),cursor.getString(1)};
                dataList.add(tempElem);
            }
        }
        db.close();
    }
}
