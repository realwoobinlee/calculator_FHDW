package de.fhdw.shared;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InternalStorage {

    public ArrayList<String> ERROR = new ArrayList<>();
    /* JSON
        {
            Column: Int,
            Row: int,
            ColumnWeight: Int(1-10),
            RowWeight: Int(1-7),
            Value: String ex) "+", "-", "1", "2", "Output", "Graph"
        }
     */

    // checks if the given File exists in internal Storage
    public boolean isFilePresent(Context context, String FileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + FileName;
        File file = new File(path);
        return file.exists();
    }

    // it returns true if deleted, otherwise false when the file is not found or not deleted
    public boolean deleteFile(Context context, String FileName) {
        if(isFilePresent(context, FileName)) {
            String path = context.getFilesDir().getAbsolutePath() + "/" + FileName;
            File file = new File(path);
            if(file.delete()){
                Log.d("LOGTEXT","File(" + FileName + ") has been deleted");
                return true;
            } else {
                Log.d("LOGTEXT", "File(" + FileName + ") has not been deleted");
                return false;
            }
        }
        return false;
    }

    public boolean writeJSONFile(Context context, String JSONFileName, JSONArray JSONInput) {
        // when the function is being used, the 'ERROR' Variable gets
        ERROR.clear();

        String JSONInputText = JSONInput.toString();

        if (!this.isJSONFile(JSONFileName)) {
            ERROR.add("The Type of File is not json");
            return false;
        } else {
            try {

                FileOutputStream fileOutputStream = context.openFileOutput(JSONFileName, context.MODE_PRIVATE);

                if(JSONInputText != null) {
                    fileOutputStream.write(JSONInputText.getBytes());
                } else {
                    ERROR.add("JSONInputText does not have value");
                    return false;
                }

                fileOutputStream.close();

                return true;
            } catch (FileNotFoundException e) {
                ERROR.add("File is not found");
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                ERROR.add("IOException");
                e.printStackTrace();
                return false;
            }

        }
    }

    public JSONArray readJSONFile(Context context, String JSONFileName) {
        ERROR.clear();

        String JSONText;
        JSONArray OutputJSON;

        if(!this.isJSONFile(JSONFileName)) {
            ERROR.add("This JSONFile does not exist in internal storage");
            return null;
        } else {
            FileInputStream fileInputStream;
            try {
                fileInputStream = context.openFileInput(JSONFileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                OutputJSON = new JSONArray(stringBuilder.toString());
                return OutputJSON;

            } catch (FileNotFoundException e) {
                ERROR.add("File is not found");
                e.printStackTrace();
            } catch (IOException e) {
                ERROR.add("IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                ERROR.add("Output is not JSON Array");
                e.printStackTrace();
            }
        }

        return null;
    }

    //check if the FileName ends with 'json'
    public Boolean isJSONFile(String FileName) {
        String[] splitedFileName;
        splitedFileName = FileName.split("\\.");

        String TypeOfFile = splitedFileName[1].toLowerCase();

        return TypeOfFile.equals("json");
    }
}
