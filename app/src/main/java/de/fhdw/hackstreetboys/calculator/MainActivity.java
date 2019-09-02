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
import java.util.Arrays;
import java.util.List;

import de.fhdw.hackstreetboys.calculator.shared.InternalStorage;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    InternalStorage internalStorage = new InternalStorage();
    SpannedGridLayoutManager spannedGridLayoutManager;
    List<String[]> dataset = new ArrayList<String[]>();
    DBCalculator db = new DBCalculator(this);
    Button mResetButton;
    Button mHistoryActivityButton;
    public void Init() {
        mResetButton = (Button) findViewById(R.id.resetlayoutbutton);
        mHistoryActivityButton = (Button) findViewById(R.id.historyactivity);
        mResetButton.setOnClickListener(this);
        mHistoryActivityButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        System.out.println("LOGTEXT: " + v.getId());
        System.out.println("LOGTEXT: " + R.id.resetlayoutbutton);
        System.out.println("LOGTEXT: " + R.id.historyactivity);
        System.out.println("LOGTEXT: " + "reset");

        switch (v.getId()) {
            case R.id.resetlayoutbutton:
                System.out.println("LOGTEXT: " + "reset");
                db.SaveLayoutData(this.InitialLayout());
                dataset = this.InitialLayout();
                finish();
                startActivity(getIntent());
                break;
            case R.id.historyactivity:
                System.out.println("LOGTEXT: " + "history");
                Intent ToHistoryIntent = new Intent(MainActivity.this,History.class);
                MainActivity.this.startActivity(ToHistoryIntent);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.RetrieveLayoutData(dataset);
        if (dataset.size() == 0) {
            dataset = this.InitialLayout();
        }
        Init(); // initialization of two Buttons on the top of Layout with OnClick Funktions
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        spannedGridLayoutManager.setItemOrderIsStable(true);
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        mRecyclerView.addItemDecoration(spaceItemDecorator);
        recyclerViewAdapter = new RecyclerViewAdapter(dataset, db);

        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                return new SpanSize(Integer.valueOf(dataset.get(position)[0]),Integer.valueOf(dataset.get(position)[1]));
            }
        }));

        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

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
