package de.fhdw.calculator;

import android.graphics.Rect;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.mariuszgromada.math.mxparser.*;
import com.arasthel.spannedgridlayoutmanager.*;

import java.util.ArrayList;
import java.util.Arrays;

import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    InternalStorage internalStorage = new InternalStorage();
    SpannedGridLayoutManager spannedGridLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    String[][] fdataset = {{"1","2","+"},{"2","1","+"},{"3","1","view"}};
    String[] felements = {"sizex","sizey","value"};
    String[][] dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internalStorage.writeJSONFile(this,"layoutformat.json",internalStorage.ConvertToJsonArray(fdataset,felements));
        JSONArray jarray = internalStorage.readJSONFile(this,"layoutformat.json");
        String[][] fake = internalStorage.ConvertToStringArrayArray(jarray,
                felements);
        dataset = fake;
        Log.i("LOGTEXT","length:"+ dataset[1][2].length());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        spannedGridLayoutManager.setItemOrderIsStable(true);
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        // mRecyclerView gets LayoutManager
        mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        // Space Decorator
        mRecyclerView.addItemDecoration(spaceItemDecorator);
        //mRecyclerView.addOnItemTouchListener();
        recyclerViewAdapter = new RecyclerViewAdapter(dataset);

        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                if(dataset[position]!=null) {
                    return new SpanSize(Integer.valueOf(dataset[position][0]),Integer.valueOf(dataset[position][1]));
                } else {
                    return new SpanSize(1,1);
                }
            }
        }));

        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    /*
    void _init() {
        String[][] dataset = {{"1","2","+"},{"2","1","+"},{"3","1","view"}};
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        spannedGridLayoutManager.setItemOrderIsStable(true);
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        // mRecyclerView gets LayoutManager
        //mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        // Space Decorator
        mRecyclerView.addItemDecoration(spaceItemDecorator);
        //mRecyclerView.addOnItemTouchListener();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(dataset);
        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                if (recyclerViewAdapter.clickedItems[position]) {
                    Log.d("LOGTEXT","changed"+ position);
                    return new SpanSize(2, 2);
                } else {
                    return new SpanSize(1,1);
                }
            }
        }));

        mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }  
    */
}
