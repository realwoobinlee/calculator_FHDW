package de.fhdw.hackstreetboys.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import de.fhdw.hackstreetboys.calculator.shared.DBCalculator;
import de.fhdw.hackstreetboys.calculator.spannedgridlayoutmanager.*;

import java.util.ArrayList;
import java.util.List;

import de.fhdw.hackstreetboys.calculator.shared.InternalStorage;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView; // RecyclerView which will be integrated with spanned grid layout manager
    RecyclerViewAdapter recyclerViewAdapter; // recyclerViewAdapter where all the Tile functions are stored
    SpannedGridLayoutManager spannedGridLayoutManager; // Manager for spannedGridLayout
    List<String[]> dataset = new ArrayList<String[]>(); // local dataset variable which will be used to put the datas on the view
    DBCalculator db = new DBCalculator(this); // Database Config
    Button mResetButton; //Button to reset the dataset to default one
    Button mHistoryActivityButton; // redirect to HistoryActivity
    // intialize the Buttons with OnClickListener
    public void Init() {
        mResetButton = (Button) findViewById(R.id.resetlayoutbutton);
        mHistoryActivityButton = (Button) findViewById(R.id.historyactivity);
        mResetButton.setOnClickListener(this);
        mHistoryActivityButton.setOnClickListener(this);
    }
    // implement the functionalities of the buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetlayoutbutton: // reset the dataset
                System.out.println("LOGTEXT: " + "reset");
                db.SaveLayoutData(this.InitialLayout());
                dataset = this.InitialLayout();
                finish();
                startActivity(getIntent());
                break;
            case R.id.historyactivity: // redirect to the history activity
                System.out.println("LOGTEXT: " + "history");
                Intent ToHistoryIntent = new Intent(MainActivity.this,History.class);
                MainActivity.this.startActivity(ToHistoryIntent);
                break;
        }
    }
    // OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // retrieve the data set stored in the database using shallow copy to local dataset
        db.RetrieveLayoutData(dataset);
        // when the size of dataset is 0 then the initial layout will pop up
        if (dataset.size() == 0) {
            dataset = this.InitialLayout();
        }
        // Init function defined above
        Init(); // initialization of two Buttons on the top of Layout with OnClick Funktions
        //mRecylerView with the ID
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // implement the spannedGirdLayoutManager
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        // set the item order stable, which will lead to not movable layout without notify functions
        spannedGridLayoutManager.setItemOrderIsStable(true);
        // Decorator: optional but for the better config
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        mRecyclerView.setLayoutManager(spannedGridLayoutManager);// Manager inserted into mRecyclerView
        mRecyclerView.addItemDecoration(spaceItemDecorator); // adding item decorator
        //Adapter will be initialized with two Parameters
        recyclerViewAdapter = new RecyclerViewAdapter(dataset, db);
        // these lines of codes are responsible for Resizability; gets width and height from the dataset
        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                return new SpanSize(Integer.valueOf(dataset.get(position)[0]),Integer.valueOf(dataset.get(position)[1]));
            }
        }));
        // setting adapter in the RecyclerView
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }
    // returns Initial Layout
    private List<String[]> InitialLayout() {
        List<String[]> tempList = new ArrayList<String[]>();
        for (int i=0; i < 70; i++) {
            String[] tempElem = {"1","1",""};
            switch (i+1) {
                case 1:
                    tempElem[2] = "1";
                    tempList.add(tempElem);
                    break;
                case 2:
                    tempElem[2] = "2";
                    tempList.add(tempElem);
                    break;
                case 3:
                    tempElem[2] = "3";
                    tempList.add(tempElem);
                    break;
                case 4:
                    tempElem[2] = "+";
                    tempList.add(tempElem);
                    break;
                case 11:
                    tempElem[2] = "4";
                    tempList.add(tempElem);
                    break;
                case 12:
                    tempElem[2] = "5";
                    tempList.add(tempElem);
                    break;
                case 13:
                    tempElem[2] = "6";
                    tempList.add(tempElem);
                    break;
                case 14:
                    tempElem[2] = "-";
                    tempList.add(tempElem);
                    break;
                case 21:
                    tempElem[2] = "7";
                    tempList.add(tempElem);
                    break;
                case 22:
                    tempElem[2] = "8";
                    tempList.add(tempElem);
                    break;
                case 23:
                    tempElem[2] = "9";
                    tempList.add(tempElem);
                    break;
                case 24:
                    tempElem[2] = "*";
                    tempList.add(tempElem);
                    break;
                case 32:
                    tempElem[2] = "0";
                    tempList.add(tempElem);
                    break;
                case 33:
                    tempElem[2] = "=";
                    tempList.add(tempElem);
                    break;
                case 34:
                    tempElem[2] = "/";
                    tempList.add(tempElem);
                    break;
                default:
                    tempList.add(tempElem);
                    break;
            }
        }
        return tempList;
    }
}
