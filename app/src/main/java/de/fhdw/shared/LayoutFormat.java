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

import de.fhdw.calculator.R;


public class LayoutFormat {
    /* JSON
        {
            ID: Int serial(Unique) (0 - 69),
            PosX: Int,
            PosY: int,
            Width: Int(1-10),
            Height: Int(1-7),
            Value: String ex) "+", "-", "1", "2", "Output", "Graph"
        }
     */
    //public int[][] GridlayoutFormat = new int[7][10];
    public HashMap<Integer,String[]> MapIDValues = new HashMap<Integer,String[]>();
    public JSONArray JSONArrayOfFormat;

    private static final int MAX_COL  = 10;
    private static final int MAX_ROW  = 7;


    //Constructor
    public LayoutFormat(JSONArray _jsonArray) {
        if(_jsonArray != null && _jsonArray.length() > 0) {
            JSONArrayOfFormat = _jsonArray;
            this.fillVarsWithJSONArray(JSONArrayOfFormat);
        }
    }

    // clear the class variables and fill those with new JSONArray Value
    public void renew(JSONArray jsonArray) {
        MapIDValues.clear();
        this.fillVarsWithJSONArray(jsonArray);
    }

    public void onChangeOfSize(int ID, int RowSpan, int ColSpan) {
    }
    public void onChangeOfPlace(int ID, int Row, int Column) {

    }
    public void onChangeOfValue(int ID, String Value) {
    }
    public void onDeleteOfElement(int ID) {

    }

    public void convertMapToJSONArray() {
        JSONArrayOfFormat = new JSONArray();
        for(int key: MapIDValues.keySet()) {
            String[] tempValues = MapIDValues.get(key);
            JSONObject tempJSONObject = new JSONObject();
            try {
                tempJSONObject.put("ID",    key);
                tempJSONObject.put("Row",  Integer.valueOf(tempValues[0]));
                tempJSONObject.put("Col",  Integer.valueOf(tempValues[1]));
                tempJSONObject.put("RowS",  Integer.valueOf(tempValues[2]));
                tempJSONObject.put("ColS",  Integer.valueOf(tempValues[3]));
                tempJSONObject.put("Value", tempValues[4]);
                JSONArrayOfFormat.put(tempJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    // returns String[7][10][2]
    private void fillVarsWithJSONArray(JSONArray jsonArray) {
        //[ID,PosX,PosY,Value]
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject elem = jsonArray.getJSONObject(i);
                int elemID = elem.getInt("ID");
                int elemRow = elem.getInt("Row");
                int elemCol = elem.getInt("Col");
                int elemRows = elem.getInt("RowS");
                int elemCols = elem.getInt("Cols");
                String elemValue = elem.getString("Value");

                String[] elemItems = this.ElemsToStringArray(elemRow,elemCol,elemRows,elemCols,elemValue);

                MapIDValues.put(elemID, elemItems);
                /*
                //to fill the String[}[} result with id
                for(int j = 0; j < elemRowS ; j++ ) {
                    for(int z = 0; z < elemColS; z++) {
                        //!!!
                        GridlayoutFormat[elemRow + j][elemColumn + z] = elemID;
                    }
                }
                */
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

    private void adjustVarsWithChange(String[] firstElem, String[] secondElem) {
        int MIN_MARGIN = R.dimen.margin_layout;
        int[] IntFirstElem = this.StringArrayToIntArray(firstElem,3);
        int[] IntSecondElem = this.StringArrayToIntArray(secondElem,3);
        if((IntSecondElem[0]/*PosX_S*/< IntFirstElem[2]/*Width_F*/&& IntSecondElem[0] > IntFirstElem[0] /*PosX_F*/)
            && (IntSecondElem[1]/*PosY_S*/ < IntFirstElem[1] /*Height*/ && IntSecondElem[1] > IntFirstElem[3] /*PosY_F*/ )) {
            int tempPosX = IntFirstElem[0] + IntFirstElem[2];// + MIN_
        }

    }

    private String[] ElemsToStringArray(int posx, int posy,int width, int height, String val) {
        String[] tempStringArray = {String.valueOf(posx),String.valueOf(posy),
                String.valueOf(width),String.valueOf(height),val};
        return tempStringArray;
    }

    private int[] StringArrayToIntArray(String[] stringArray, int EndOfPos) {
        int[] tempIntArray = new int[EndOfPos];
        for(int i = 0; i <= EndOfPos; i++) {
            tempIntArray[i] = Integer.valueOf(stringArray[i]);
        }
        return tempIntArray;
    }
}
