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
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;

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
            /*
            {
                ID: Int serial(Unique) (0 - 69),
                    Column: Int,
                    Row: int,
                ColumnWeight: Int(1-10),
                        RowWeight: Int(1-7),
                    Value: String ex) "+", "-", "1", "2", "Output", "Graph"
            }
            */
            case R.id.exbutton1:
                try {
                    JSONArray test = new JSONArray("[{ID: 1, Column:0, Row: 1, ColumnWeight:1, RowWeight: 1, Value:'+'}]");
                    LayoutFormat testlf = new LayoutFormat(test);
                    for(int i = 0; i < 7; i++) {
                        for(int j = 0; j < 10; j++) {

                            Log.d("LOGTEXT",String.valueOf(testlf.GridlayoutFormat[i][j]));
                            if(i==6 && j==9) {
                                Log.d("LOGTEXT","hallo" + String.valueOf(testlf.GridlayoutFormat[i][j]));
                            }

                        }
                    }

                    Log.d("LOGTEXT",testlf.MapIDValue.values().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exbutton2:
                Function f = new Function("At(x) = x^2-2x-8");
                Argument a1 = new Argument("x=0");

                break;
        }

        Expression e = new Expression("2 + ( 4 +3 )*4");
        String result = String.valueOf(e.calculate());

    }
}
