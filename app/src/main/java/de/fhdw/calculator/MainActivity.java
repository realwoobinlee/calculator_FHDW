package de.fhdw.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.mariuszgromada.math.mxparser.*;

import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.InternalStorage.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GridLayout mGridLayout;
    Button mEXButton1, mEXButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _init();

        final LayoutInflater inflater = LayoutInflater.from(this);


        /*
        final LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < NBR_ITEMS; i++) {
            final View itemView = inflater.inflate(R.layout.grid_item, mGrid, false);
            final TextView text = (TextView) itemView.findViewById(R.id.text);
            text.setText(String.valueOf(i + 1));
            mGrid.addView(itemView);
        }
         */
    }

    void _init() {
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout);
        mEXButton1 = findViewById(R.id.exbutton1);
        mEXButton2 = findViewById(R.id.exbutton2);
        mEXButton1.setOnClickListener(this);
        mEXButton2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        InternalStorage internalStorage = new InternalStorage();

        switch (v.getId()) {
            case R.id.exbutton1:
                try {
                    JSONArray test = new JSONArray("[{hallo: '1'}, {hallo:'2'}]");
                    //Log.d("LOGTEXT",test.get(0).toString());
                    internalStorage.writeJSONFile(this,"test.json",test);
                    if(internalStorage.isFilePresent(this,"test.json")) {
                        Log.d("LOGTEXT", "test.json exists");
                    } else {
                        Log.d("LOGTEXT", "test.json does not exist");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exbutton2:
                JSONArray test2 = internalStorage.readJSONFile(this,"test.json");
                Log.d("LOGTEXT",test2.toString());
                internalStorage.deleteFile(this,"test.json");
                if(internalStorage.isFilePresent(this,"test.json")) {
                    Log.d("LOGTEXT", "test.json exists");
                } else {
                    Log.d("LOGTEXT", "test.json does not exist");
                }
                break;
        }

        Expression e = new Expression("2 + ( 4 +3 )*4");
        String result = String.valueOf(e.calculate());

    }
}
