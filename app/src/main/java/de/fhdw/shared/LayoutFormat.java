package de.fhdw.shared;

import android.util.Log;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;


public class LayoutFormat {
    /* JSON
        {
            ID: Int serial(Unique) (0 - 69),
            Column: Int,
            Row: int,
            ColumnWeight: Int(1-10),
            RowWeight: Int(1-7),
            Value: String ex) "+", "-", "1", "2", "Output", "Graph"
        }
     */
    public int[][] GridlayoutFormat = new int[7][10];
    public HashMap<Integer,String> MapIDValue = new HashMap<Integer,String>();
    public JSONArray JSONArrayOfFormat;

    //Constructor
    public LayoutFormat(JSONArray _jsonArray) {
        if(_jsonArray != null && _jsonArray.length() > 0) {
            JSONArrayOfFormat = _jsonArray;
            this.fillVarsWithJSONArray(JSONArrayOfFormat);
        }
    }

    // clear the class variables and fill those with new JSONArray Value
    public void renew(JSONArray jsonArray) {
        this.intializeGridlayoutFormat();
        MapIDValue.clear();
        this.fillVarsWithJSONArray(jsonArray);
    }

    
    public void onChangeofSize(int ID, int RowWeight, int ColumnWeight) {

    }
    public void onChangeOfPlace(int ID, int Row, int Column) {

    }
    public void onChangeOfValue(int ID, String Value) {

    }

    // returns String[7][10][2]
    private void fillVarsWithJSONArray(JSONArray jsonArray) {

        this.intializeGridlayoutFormat();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject elem = jsonArray.getJSONObject(i);
                int elemID = elem.getInt("ID");
                int elemColumn = elem.getInt("Column");
                int elemRow = elem.getInt("Row");
                int elemColW = elem.getInt("ColumnWeight");
                int elemRowW = elem.getInt("RowWeight");
                String elemValue = elem.getString("Value");

                //to fill the String[}[} result with id
                for(int j = 0; j < elemRowW ; j++ ) {
                    for(int z = 0; z < elemColW; z++) {
                        GridlayoutFormat[elemRow + j][elemColumn + z] = elemID;
                    }
                }

                Iterator<String> keys = elem.keys();
                while(keys.hasNext()){
                    String key = keys.next();
                    MapIDValue.put(elemID,elemValue);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /*
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j < 10; j++) {
                GridlayoutFormat[i][j] = 0;
            }
        }
        */
    }

    private void intializeGridlayoutFormat() {
        for(int i = 0; i < 7; i++) {
            Arrays.fill(GridlayoutFormat[i],0);
        }
    }
}
